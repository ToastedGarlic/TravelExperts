package com.example.travelexperts;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

public class MyApplication extends Application {
    Intent intent;

    public MyApplication() {
        Log.d("application", "in Application Constructor()");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("applicaiton", "in Application onCreate()");

        intent = new Intent(this, MyService.class);
        startService(intent);

        intent = new Intent(this, MessengerService.class);
        startService(intent);

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.d("application", "in Application onTerminate()");
    }
}
