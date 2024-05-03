package com.example.travelexperts;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.travelexperts.Model.Customer;
import com.example.travelexperts.ui.login.LoginActivity;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.Executors;

public class RegisterActivity extends AppCompatActivity {

    private EditText edtFirstName, edtLastName, edtAddress, edtCity, edtProv, edtPostal,
            edtCountry, edtHomePhone, edtBusPhone, edtEmail, edtAgentId, edtUsername, edtPassword;
    private Button btnRegister;

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        bindViews();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInputs()) {
                    registerCustomer();
                }
            }
        });
    }

    private void bindViews() {
        edtFirstName = findViewById(R.id.edtFirstName);
        edtLastName = findViewById(R.id.edtLastName);
        edtAddress = findViewById(R.id.edtAddress);
        edtCity = findViewById(R.id.edtCity);
        edtProv = findViewById(R.id.edtProv);
        edtPostal = findViewById(R.id.edtPostal);
        edtCountry = findViewById(R.id.edtCountry);
        edtHomePhone = findViewById(R.id.edtHomePhone);
        edtBusPhone = findViewById(R.id.edtBusPhone);
        edtEmail = findViewById(R.id.edtEmail);
        edtAgentId = findViewById(R.id.edtAgentId);
        edtUsername = findViewById(R.id.edtUsername);  // Assuming you have these EditTexts in your layout
        edtPassword = findViewById(R.id.edtPassword);
        btnRegister = findViewById(R.id.btnRegister);
    }

    private boolean validateInputs() {
        // Example validation for empty fields
        if (edtFirstName.getText().toString().trim().isEmpty()) {
            edtFirstName.setError("First name is required");
            return false;
        }
        // Add other validations as necessary
        return true;
    }

    private void registerCustomer() {
        Customer customer = new Customer(
                0,
                edtFirstName.getText().toString(),
                edtLastName.getText().toString(),
                edtAddress.getText().toString(),
                edtCity.getText().toString(),
                edtProv.getText().toString(),
                edtPostal.getText().toString(),
                edtCountry.getText().toString(),
                edtHomePhone.getText().toString(),
                edtBusPhone.getText().toString(),
                edtEmail.getText().toString(),
                Integer.parseInt(edtAgentId.getText().toString()),
                edtUsername.getText().toString(),
                edtPassword.getText().toString()
        );

        Gson gson = new Gson();
        String jsonCustomer = gson.toJson(customer);

        Log.d("RegisterActivity", "JSON being sent: " + jsonCustomer); // Log the JSON data

        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                sendCustomerData(jsonCustomer);
            } catch (Exception e) {
                runOnUiThread(() -> Toast.makeText(RegisterActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show());
            }
        });
    }

    private void sendCustomerData(String jsonData) throws Exception {
        URL url = new URL("http://10.0.2.2:8080/TravelExpertsRESTJPA-1.0-SNAPSHOT/api/customer/register");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        connection.setDoOutput(true);

        try (OutputStream os = connection.getOutputStream()) {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(jsonData);
            writer.flush();
            int responseCode = connection.getResponseCode();
            runOnUiThread(() -> {
                if (responseCode == HttpURLConnection.HTTP_CREATED) {  // Check for HTTP_CREATED (201)
                    Toast.makeText(RegisterActivity.this, "Registered Successfully", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));

                } else {
                    Toast.makeText(RegisterActivity.this, "Registration failed: " + responseCode, Toast.LENGTH_LONG).show();
                }
            });
        } finally {
            connection.disconnect();
        }
    }


    private class SaveCustomerId implements Runnable {
        @Override
        public void run() {
            int userid;
            String stringid = null;
            StringBuffer sb = new StringBuffer();
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput("user.json")));
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                br.close();
                JSONArray jsonArray = new JSONArray(sb.toString());
                ArrayList<Customer> list = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    stringid = jsonObject.getString("customerId");

                }
                userid = Integer.parseInt(stringid);

                preferences = getSharedPreferences("myprefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("customerid", userid);
                editor.commit();


            } catch (IOException | JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }
}