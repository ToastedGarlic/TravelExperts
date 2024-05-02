package com.example.travelexperts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ProfileActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        // get references to
        Button btnProfile = (Button) findViewById(R.id.btnProfile);
        Button btnBook = (Button) findViewById(R.id.btnBook);
        Button btnServices = (Button) findViewById(R.id.btnServices);
        Button btnAgent = (Button) findViewById(R.id.btnAgent);
        // get the intent
        Intent intent = getIntent();

        // get data from the intent
//        int agentId = intent.getIntExtra("agentId", 0);
//        String agentFullName = intent.getStringExtra("agentFullName");

        // display data on the widgets
//        tvAgentId.setText(String.valueOf(agentId));

        btnProfile.setOnClickListener(profileListener);
        btnBook.setOnClickListener(bookListener);
        btnAgent.setOnClickListener(agentListener);
        btnServices.setOnClickListener(serviceListener);

    }
    private final View.OnClickListener profileListener= v -> {

    };

    private final View.OnClickListener bookListener= v -> {

    };

    private final View.OnClickListener serviceListener= v -> {

    };
    private final View.OnClickListener agentListener= v -> {

    };

}
