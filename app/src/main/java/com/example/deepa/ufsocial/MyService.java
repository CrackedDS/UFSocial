package com.example.deepa.ufsocial;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import org.json.JSONObject;
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
    private static final String SERVER_IP = "70.171.55.225";

    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

 /*   @Override
    public int onStartCommand(Intent intent,int flags, int startId){
        super.onStartCommand(intent, flags, startId);

        return START_STICKY;
    }*/

    class connectSocket implements Runnable {

        JSONObject obj;
        connectSocket() {}
        connectSocket(JSONObject object) {
            obj = object;
        }

        @Override
        public void run() {
            try{
                InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
                socket = new Socket(serverAddr, SERVERPORT);
                OutputStream os = socket.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                switch(obj.getString("header")) {
                    case "testAuth":
                        writer.write(obj.toString());
                        writer.flush();
                        writer.close();
                        os.close();
                        
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            socket.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        socket = null;
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

    public String logIn(JSONObject obj) {
        Runnable connect1 = new connectSocket(obj);
        new Thread(connect1).start();
        return null;
    }
}
