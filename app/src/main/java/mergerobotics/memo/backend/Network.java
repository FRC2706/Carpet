package mergerobotics.memo.backend;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import mergerobotics.memo.backend.DataObjects.*;
import mergerobotics.memo.backend.Database;

public class Network {
	public static final int API_VERSION_MAJOR = 0;
	public static final int API_VERSION_MINOR = 0;
	
	public static char ENDL = 0;
	
	public static final int PEERSCAN_THREADS = 512;
	public static final int PORT = 31465;
	
	private static ArrayList<Peer> peers;
	private static ServerSocket ss;
	private static Thread listenThread;
	private static boolean listen;
	private static WifiManager.WifiLock wifilock;
	
	
	// PUBLIC METHODS
	public static void init(Context c)
	{
		try {
			wifilock  = ((WifiManager) c.getApplicationContext().getSystemService(Context.WIFI_SERVICE)).createWifiLock(WifiManager.WIFI_MODE_FULL, "WifiLock");
			peers = new ArrayList<>();
			ss = new ServerSocket(PORT);
			listen = false;
			listenThread = new Thread(new Runnable() {
				@Override
				public void run() {
					server();
				}
			});
		} catch (Exception e) {
			Log.e("Network", "Error while initializing Network", e);
		}
	}
	
	public static void start_scan() {
		// This is for starting the peerscan threads.
	}
	
	public static void add_peer(Peer peer) {
		// TODO: Create method
		for (Peer p : peers) {
			if (p.address.equals(peer.address)) return;
		}
		peers.add(peer);
	}
	
	public static void sync() {
		// TODO: Create method
	}
	
	public static void remove_peer(String peer, String reason) {
		// TODO: Create method
	}
	
	public static void start() {
		if (!listen) {
			listen = true;
			listenThread.start();
			wifilock.acquire();
		}
	}
	
	public static void stop() {
		if (listen) {
			listen = false;
			wifilock.release();
		}
	}
	
	// PRIVATE METHODS
	private static void server() {
		Log.d("Network", "Starting network server!");
		while (listen)
		{
			try {
				//Log.d("Network", "Waiting for client...");
				Socket s = ss.accept();
				//Log.d("Network", "Accepting socket from " + s.getInetAddress().toString());
				handle_request(s);
				s.close();
			} catch (Exception e) {
				Log.e("Network", "Error while accepting socket", e);
			}
		}
		Log.d("Network", "Stopped network server!");
	}
	private static void push_all(Peer peer, int year) {
		for (Competition c : Database.getCompetitions(year)) {
			push(peer, c.name);
		}
	}
	
	private static void push(Peer peer, String competition) {
		// TODO: Create method
	}
	
	private static void pull(String peer, String competition) {
		// TODO: Create method
	}
	
	public static void request_all_matches(String competition) {
		for (Peer p : peers) {
			request_matches(p, competition);
		}
	}
	
	private static void request_matches(Peer peer, String competition) {
		try {
			String message = construct_message(Message.DUMP_MATCHES, new JSONObject().put("competition", competition));
			peer.open();
			peer.send(message);
			Message resp = new Message(peer.read());
			Match[] matches = Match.fromJSONArray(resp.data.getJSONArray("matches"));
			Database.insertMatches(matches);
			message = construct_message(Message.OK, new JSONObject("{}"));
			peer.send(message);
		} catch (Exception e) {
			Log.e("Network", "Error while requesting matches from peer", e);
		}
	}
	
	private static void request_season(String peer, int year) {
		// TODO: Create method
	}
	
	private static String construct_message(String type, JSONObject data) {
		try {
			data.put("version_major", API_VERSION_MAJOR);
			data.put("version_minor", API_VERSION_MINOR);
			data.put("type", type);
			data.put("team", 2706);
			data.put("sn", Crypto.getSignatureRow(data));
			return data.toString() + "\00";
		} catch (Exception e) {
			Log.e("Network", "Error while constructing message", e);
			return null;
		}
	}
	
	private static boolean handshake(Peer peer) {
		try {
			String message = construct_message(Message.HANDSHAKE, new JSONObject("{\"peers\":[]}"));
			peer.send(message);
			
			String resp = peer.read();
			if (resp==null) return false;
			Message response = new Message(resp);
			if (response.type.equals(Message.OK)) {
				// Peer is good.
				peers.add(peer);
				Log.d("Network", "Added peer '" + peer.address + "'");
				return true;
			}
		} catch (Exception e) {
			Log.e("Network", "Error while handshaking with peer", e);
		}
		return false;
	}
	
	private static void handle_request(Socket s) {
		Peer p = new Peer(s);
		Message req = new Message(p.read());
		
		try {
			Team t = Database.getTeam(req.data.getInt("team"));
			String message = "";
			if (t==null){
				Log.w("Network", "Received message from unknown team!");
			} else if (!Crypto.verifySignatureRow(req.data, t.pubkey)) {
				Log.w("Network", "Failed to verify received row!");
				message = construct_message(Message.UNAUTHORIZED, new JSONObject("{}"));
			} else {
				switch (req.type) {
					case Message.HANDSHAKE:
						message = construct_message(Message.OK, new JSONObject("{\"peers\":[]}"));
						break;
					case Message.DUMP_SEASON:
						// Peer is requesting we dump everything
						// TODO: Return list of competitions and teams
						
						JSONObject msg = new JSONObject();
						Competition[] comps = Database.getCompetitions(req.data.getInt("year"));
						JSONArray competitions = new JSONArray();
						for (Competition c : comps)
							competitions.put(c.name);
						msg.put("competitions", competitions);
						JSONArray teams = new JSONArray();
						for (Team team : Database.getTeams()) {
							JSONObject tem = new JSONObject();
							tem.put("number", team.number);
							tem.put("public_key", team.pubkey);
							tem.put("name", team.name);
							teams.put(tem);
						}
						msg.put("teams", teams);
						message = construct_message(Message.OK, msg);
						break;
					case Message.DUMP_MATCHES:
						// Peer is either dumping or requesting matches
						if (req.data.has("matches")) {
							// dumping matches
							Match[] matches = Match.fromJSONArray(req.data.getJSONArray("matches"));
							Database.insertMatches(matches);
							message = construct_message(Message.OK, new JSONObject("{}"));
						} else if (req.data.has("competition")) {
							// requesting matches for a particular competition
							JSONObject jsn = new JSONObject();
							jsn.put("matches", Match.toJSONArray(Database.getMatchesFor(req.data.getString("competition"))));
							message = construct_message(Message.DUMP_MATCHES, jsn);
						} else {
							// Unknown
							message = construct_message(Message.UNKNOWN, new JSONObject("{}"));
						}
						break;
						
					case Message.PUSH:
						// Peer is sending us all competitions and events
						Database.insertCompetition(req.data.getString("competition"), Integer.parseInt(req.data.getString("competition").substring(0, 4)));
						JSONArray events = req.data.getJSONArray("events");
						Database.insertEvents(Event.fromJSONArray(events));
						message = construct_message(Message.OK, new JSONObject("{}"));
						break;
					case Message.PULL:
						// Peer is requesting stuff from us
						if (req.data.has("competition")){
							Event[] evts = Database.getEventsFrom(req.data.getString("competition"));
							JSONObject ret = new JSONObject();
							ret.put("events", Event.toJSONArray(evts));
							message = construct_message(Message.OK, ret);
							
						} else {
							message = construct_message(Message.INVALID, new JSONObject());
						}
						break;
					default:
						message = construct_message(Message.UNKNOWN, new JSONObject("{}"));
				}
			}
			if (message != "")
				p.send(message);
		} catch (Exception e) {
			Log.e("Network", "Error while handling request", e);
		}
		
		p.close();
		add_peer(p);
	}
	
	private static void peerscan() {
		// TODO: Create method
	}
	
}
