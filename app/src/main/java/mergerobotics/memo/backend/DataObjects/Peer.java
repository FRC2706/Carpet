package mergerobotics.memo.backend.DataObjects;

import android.util.Log;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import mergerobotics.memo.backend.Network;

public class Peer {
	public String address;
	public int port;
	public Socket socket;
	public InputStreamReader in;
	public OutputStreamWriter out;
	public boolean connected;
	
	public Peer(String address, int port)
	{
		try {
			this.socket = new Socket(address, port);
			if (this.socket.isConnected()) {
				// The socket connected fine.
				this.in = new InputStreamReader(this.socket.getInputStream());
				this.out = new OutputStreamWriter(this.socket.getOutputStream());
				this.port = port;
			}
			this.connected = this.socket.isConnected();
		} catch (Exception e) {
			Log.e("Network", "Error while creating Peer object from address, port ", e);
			this.connected = false;
		}
	}
	
	public Peer(Socket s)
	{
		try {
			if (s.isConnected()) {
				this.socket = s;
				this.address = s.getInetAddress().toString().replace("/", "");
				this.port = s.getLocalPort();
				this.in = new InputStreamReader(this.socket.getInputStream());
				this.out = new OutputStreamWriter(this.socket.getOutputStream());
				this.connected = true;
			}
		} catch (Exception e) {
			Log.e("Network", "Error while creating Peer object from socket", e);
		}
	}
	
	public void open()
	{
		try {
			this.socket = new Socket(address, port);
			if (this.socket.isConnected()) {
				// The socket connected fine.
				this.in = new InputStreamReader(this.socket.getInputStream());
				this.out = new OutputStreamWriter(this.socket.getOutputStream());
			}
			this.connected = this.socket.isConnected();
		} catch (Exception e) {
			Log.e("Network", "Error while creating Peer object from address, port ", e);
			this.connected = false;
		}
		
	}
	
	public void close()
	{
		try {
			this.in.close();
			this.out.close();
			this.socket.close();
			this.connected = false;
		} catch (Exception e) {
			Log.e("Network", "Error while disposing of Peer object", e);
		}
	}
	
	public void send(String message)
	{
		if (!connected) {
			Log.d("Network", "Tried to write to disconnected peer");
			return;
		}
		try {
			this.out.write(message + Network.ENDL);
			this.out.flush();
		} catch (Exception e) {
			Log.e("Network","Error while writing to Peer", e);
			this.close();
		}
		//Log.d("Network", "Wrote " + message + " to " + this.address);
	}
	
	public String read()
	{
		if (!connected) {
			Log.d("Network", "Tried to read from a disconnected peer");
			return "";
		}
		try {
			StringBuilder result = new StringBuilder();
			int current = this.in.read();
			while (current != 0 && current != -1)
			{
				result.append((char) current);
				current = this.in.read();
			}
			return result.toString();
		} catch (Exception e) {
			Log.e("Network", "Error while reading from socket", e);
			return null;
		}
	}
}
