package com.example.deepa.ufsocial;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.JsonReader;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
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

    class connectSocket extends Thread {

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
                InputStream is = socket.getInputStream();
                //BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                switch(obj.getString("header")) {
                    case "testAuth":
                        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                        writer.write(obj.toString());
                        writer.flush();

                        /*StringBuilder buffer = new StringBuilder();
                        String inputStr = "";
                        *//*while (() != null)
                            buffer.append(inputStr);*//*
                        inputStr = reader.readLine();
                        String finalJson = buffer.toString();
                        String json = inputStr.substring(finalJson.indexOf("{"), finalJson.lastIndexOf("}") + 1);*/
                        JsonReader reader = new JsonReader(new InputStreamReader(is, "UTF-8"));
                        reader.beginObject();
                        String response = "";
                        while (reader.hasNext()) {
                            String name = reader.nextName();
                            if(name.equals("response")) {
                                response = reader.nextString();
                            }
                        }
                        reader.endObject();
                        Log.d("dgd", response);
                        sendMessage(response);
                        reader.close();
                        is.close();
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void sendMessage(String data) {
        Intent intent = new Intent("my-event");
        intent.putExtra("message", data);
        Log.d("msdf", data);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
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

    public void logIn(JSONObject obj) throws InterruptedException {
        connectSocket connect1 = new connectSocket(obj);
        connect1.start();
        connect1.join();
    }
}