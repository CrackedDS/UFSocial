package com.example.deepa.ufsocial;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.URLEncoder;

public class MyService extends Service {
    private final IBinder mBinder = new LocalBinder();
    private Socket socket;
    private static final int SERVERPORT = 8000;
    private static final String SERVER_IP = "70.171.55.255";

    public MyService() {
           /*
           HttpURLConnection urlConnection = null;
            URL url = new URL("http://10.0.2.2/Sanguine-Web/postdetails.php");
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(CONNECTION_TIMEOUT);
            urlConnection.setDoOutput(true);
            urlConnection.setChunkedStreamingMode(0);

            OutputStream os = urlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

            writer.write("");
            writer.flush();
            writer.close();
            os.close();
            urlConnection.disconnect();
            Log.d("fls","ksdf");
*/
    }

    @Override
    public void onCreate() {
        super.onCreate();
        try{
            InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
            socket = new Socket(serverAddr, SERVERPORT);

            OutputStream os = socket.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            StringBuilder sb = new StringBuilder();
            sb.append(URLEncoder.encode("Name", "UTF-8"));
            writer.write(sb.toString());
            writer.flush();
            writer.close();
            os.close();
            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class LocalBinder extends Binder {
        MyService getService() {
            return MyService.this;
        }
    }
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
