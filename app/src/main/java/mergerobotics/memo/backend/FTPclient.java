package mergerobotics.memo.backend;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Path;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;

import org.apache.commons.net.ftp.FTPFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Arrays;


import mergerobotics.memo.backend.interfaces.FTPrequester;

import static java.lang.System.in;
import static java.lang.Thread.sleep;

public class FTPclient {
    org.apache.commons.net.ftp.FTPClient ftpClient = new org.apache.commons.net.ftp.FTPClient();

    // for FTP server credentials
    private String hostname;
    private String password;
    private String username;

    //port for connection
    private int port;

    //Local and remote directory on device for files being downloaded.
    private File localPath;
    private String remotePath;
    private String DocumentsPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath();

    //Is the client connected?
    private Object connectedThreadLock = new Object();
    private boolean connected = false;
    private boolean syncing = false;
    private boolean initilized = false;

    /**
     * Constructor without port option
     * @param hostname: Server IP Adress
     * @param username: Login Credential
     * @param password: Login Credential
     * @param localPath: Local path for saving to the device
     */


    public FTPclient(String hostname, String username, String password, String localPath, String remotePath){
        this.hostname = hostname;
        this.password = password;
        this.username = username;
        this.localPath = new File(localPath);
        this.remotePath = remotePath;
        this.port = 21;
        Log.d("FTPClient|init", "Local Path: " + localPath + "\nRemote Path: " + remotePath);
        this.initilized = true;
    }


    public FTPclient (String localPath){
        this.localPath = new File(localPath);
        this.initilized = false;
    }

    /**
     * Constructor with port option
     * @param hostname: Server IP Adress
     * @param username: Login Credential
     * @param password: Login Credential
     * @param localPath: Local path for saving to the device
     * @param port: Custom port connection
     */

    public FTPclient(String hostname, String username, String password, String localPath, int port){
        this.hostname = hostname;
        this.password = password;
        this.username = username;
        this.localPath = new File(localPath);
        this.port = port;
        Log.d("FTPClient|init", "Local Path: " + localPath + "\nRemote Path: " + remotePath);
        this.initilized = true;
    }

    /**
     * Tells you whether the FTPClient is connected or not.
     *
     * @return whether the FTPClient is connected or not.
     **/
    public boolean isConnected() {
        synchronized (connectedThreadLock) {
            return connected;
        }
    }

    /**
     * Tries to connect to a server with the parameters supplied with constructor
     *
     * @throws ConnectException
     */
    public void connect() throws ConnectException {
        if(!initilized) return;
        ConnectivityManager cm = (ConnectivityManager) App.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork == null) { // not connected to the internet
            return;
        }

        AsyncTask.execute( new Runnable() {
            @Override
            public void run() {
                synchronized (connectedThreadLock) {
                    try {
                        ftpClient.connect(hostname, port);
                        Log.i("FTPClient|INFO", ftpClient.getReplyString());
                        ftpClient.enterLocalPassiveMode();
                        ftpClient.login(username, password);
                        Log.i("FTPClient|INFO", ftpClient.getReplyString());
                        connected = true;
                    } catch (Exception e) {
                        Log.e("FTPClient|connect", e.toString());
                        connected = false;
                    }
                }
            }
        });
        try {
            sleep(5);  //multi-threaded voodoo. Give the AsyncTask 5 ms to get started and get the lock.
        } catch (InterruptedException e) {
            // do nothing.
        }
    }

}