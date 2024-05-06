package com.example.travelexperts;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;
//MessengerService.java
//author: Michael Chessall
public class MessengerService extends Service {
    TimerTask agenttimerTask;
    Timer agenttimer;
    int CustomerId;

    public MessengerService() {
        Log.d("application", "In MessengerService Constructor");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("application", "in MessengerService onCreate()");
        startAgentTimer();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("app", "in MessengerService onDestroy()");
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("jack", "in MessengerService onStartCommand()");
        if(intent != null){
            Bundle extras = intent.getExtras();

            if(extras == null) {
                Log.d("Service","null");
            } else {
                Log.d("Service","not null");
                int from = (int) extras.get("CustomerId");
                CustomerId = from;
            }
        }

        return super.onStartCommand(intent, flags, startId);
    }

    private void startAgentTimer() {
        Log.d("michael", "in Service startAgentTimer");
        agenttimerTask = new TimerTask() {
            @Override
            public void run() {
                URI uri = null;
                SharedPreferences preferences = getSharedPreferences("myprefs", Context.MODE_PRIVATE);
                int custId = preferences.getInt("customerid", 0);
                try {
                    uri = new URI("http://10.0.2.2:8080/TravelExpertsRESTJPA-1.0-SNAPSHOT/api/message/getallmessages/"+custId);
                    URL url = uri.toURL();
                    BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
                    String line;
                    StringBuffer sb = new StringBuffer();
                    while ((line = br.readLine()) != null) {
                        sb.append(line);
                    }
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(openFileOutput("messagedata.json", MODE_PRIVATE)));
                    bw.write(sb.toString());
                    bw.close();
                    br.close();
                } catch (URISyntaxException | IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        agenttimer = new Timer();
        agenttimer.schedule(agenttimerTask, 1, 2000 );
    }


}