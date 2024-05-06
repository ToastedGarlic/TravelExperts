package com.example.travelexperts;

import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.SystemClock;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.travelexperts.Model.Messages;
import com.example.travelexperts.Model.Packages;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Executors;

//MessageFragment.java
//author: Michael Chessall
// fragment_message.xml
//author: Michael Chessall

public class MessageFragment extends Fragment {

    private MessageViewModel mViewModel;

    public static MessageFragment newInstance() {
        return new MessageFragment();
    }

    private Button btnSend;
    private EditText txtMsg;

    private MessageRetrieval messageRetrieval;
    private TextView txtContent;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        btnSend = view.findViewById(R.id.btnSend);
        txtMsg = view.findViewById(R.id.txtMsg);
        txtContent = view.findViewById(R.id.txtContent);
        txtMsg.setOnEditorActionListener(new TextView.OnEditorActionListener() {
             @Override
             public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                 boolean handled = false;
                 if (actionId == EditorInfo.IME_ACTION_SEND) {
                     if(txtMsg.getText().toString().equals("")){return false;}
                     SharedPreferences pref = v.getContext().getSharedPreferences("myprefs", Context.MODE_PRIVATE);
                     int custid = pref.getInt("customerid", 0);
                     String custname = pref.getString("custName", "Friend");
                     sendMessage(custid,custname);
                     txtMsg.setText("");
                     InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                     imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                     handled = true;
                 }
                 return handled;
             }
         });
        btnSend.setOnClickListener(sendListener);
//        Intent intent = new Intent(view.getContext(), MessengerService.class);
//        view.getContext().startService(intent);
        messageRetrieval = new MessageRetrieval();
        messageRetrieval.activity = getActivity();
        messageRetrieval.target = txtContent;
        new Thread(messageRetrieval).start();
        return view;
    }

    private final View.OnClickListener sendListener= v -> {
        SharedPreferences pref = v.getContext().getSharedPreferences("myprefs", Context.MODE_PRIVATE);
        int custid = pref.getInt("customerid", 0);
        String custname = pref.getString("custName", "Friend");
        sendMessage(custid,custname);
        txtMsg.setText("");
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    };


    private void sendMessage(int custId, String custname) {
        if(txtMsg.getText().toString().equals("")){return;}
        Date date = new Date();
        Messages message = new Messages(
                0,
                null,
                custname + ": "+txtMsg.getText().toString(),
                0,
                custId
        );

        Gson gson = new Gson();
        String jsonMessage = gson.toJson(message);

        Log.d("MessageFragment", "JSON being sent: " + jsonMessage); // Log the JSON data

        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                sendMessageData(jsonMessage);
            } catch (Exception e) {
                getActivity().runOnUiThread(() -> Toast.makeText(getActivity(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show());
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MessageViewModel.class);
        // TODO: Use the ViewModel
    }

    private void sendMessageData(String jsonData) throws Exception {
        URL url = new URL("http://10.0.2.2:8080/TravelExpertsRESTJPA-1.0-SNAPSHOT/api/message/new");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        connection.setDoOutput(true);

        try (OutputStream os = connection.getOutputStream()) {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(jsonData);
            writer.flush();
            int responseCode = connection.getResponseCode();

        } finally {
            connection.disconnect();

        }
    }
    private class MessageRetrieval implements Runnable {
        TextView target;
        Activity activity;
        @Override
        public void run() {
            while(true){
                SystemClock.sleep(2000);
                String fullMsg = "";
                StringBuffer sb = new StringBuffer();
                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader(activity.openFileInput("messagedata.json")));
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line);
                    }
                    br.close();
                    JSONArray jsonArray = new JSONArray(sb.toString());
                    ArrayList<Packages> list = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String msgContent = jsonObject.getString("msgContent");
                        String msgDate = "";
                        fullMsg += msgDate + "\n" + msgContent + "\n\n";
                    }
                    String finalFullMsg = fullMsg;

                    activity.runOnUiThread(() -> {target.setText(finalFullMsg);
                    ScrollView scroll = (ScrollView)target.getParent().getParent();
                    scroll.fullScroll(View.FOCUS_DOWN);});

                    //     ArrayAdapter<Packages> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, list);
                    //     runOnUiThread(() -> lvPackages.setAdapter(adapter));
                } catch (IOException | JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }
    }

}

