package com.example.travelexperts;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.travelexperts.Model.Messages;
import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Executors;

public class MessageFragment extends Fragment {

    private MessageViewModel mViewModel;

    public static MessageFragment newInstance() {
        return new MessageFragment();
    }

    private Button btnSend;
    private EditText txtMsg;

    private TextView txtContent;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        btnSend = view.findViewById(R.id.btnSend);
        txtMsg = view.findViewById(R.id.txtMsg);
        txtContent = view.findViewById(R.id.txtContent);
        btnSend.setOnClickListener(sendListener);

        return view;
    }

    private final View.OnClickListener sendListener= v -> {

    };


    private void sendMessage() {
        Messages message = new Messages(
                0,
                "",
                txtMsg.getText().toString(),
                0,
                0
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
            getActivity().runOnUiThread(() -> {
                if (responseCode == HttpURLConnection.HTTP_CREATED) {  // Check for HTTP_CREATED (201)
             //       Toast.makeText(getActivity(), "Registered Successfully", Toast.LENGTH_LONG).show();
                } else {
             //       Toast.makeText(getActivity(), "Registration failed: " + responseCode, Toast.LENGTH_LONG).show();
                }
            });
        } finally {
            connection.disconnect();

        }
    }
}
