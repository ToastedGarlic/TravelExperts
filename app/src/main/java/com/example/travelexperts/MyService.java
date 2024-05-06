package com.example.travelexperts;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

public class MyService extends Service {
    TimerTask timerTask;
    Timer timer;


    public MyService() {
        Log.d("application", "In MyService Constructor");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("application", "in Service onCreate()");
        startTimer();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("jack", "in Service onDestroy()");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("jack", "in Service onStartCommand()");
        return super.onStartCommand(intent, flags, startId);
    }

    private void startTimer() {
        Log.d("jack", "in Service startTimer");
        timerTask = new TimerTask() {
            @Override
            public void run() {
                URI uri = null;
                try {
                    uri = new URI("http://10.0.2.2:8080/TravelExpertsRESTJPA-1.0-SNAPSHOT/api/package/getallpackages");
                    URL url = uri.toURL();
                    BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
                    String line;
                    StringBuffer sb = new StringBuffer();
                    while ((line = br.readLine()) != null) {
                        sb.append(line);
                    }
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(openFileOutput("packagedata.json", MODE_PRIVATE)));
                    bw.write(sb.toString());
                    bw.close();
                    br.close();
                } catch (URISyntaxException | IOException e) {
                    throw new RuntimeException(e);
                }



            }
        };
        timer = new Timer();
        timer.schedule(timerTask, 1, 1000 * 60 * 5);
    }



}