package com.example.travelexperts.ui.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.travelexperts.MainActivity;
import com.example.travelexperts.Model.Customer;
import com.example.travelexperts.R;
import com.example.travelexperts.RegisterActivity;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.Executors;

public class LoginActivity extends AppCompatActivity {

    EditText etUsername, etPassword;
    Button btnLogin;
    TextView tvSignUp;
    SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvSignUp = findViewById(R.id.tvSignUp);


        tvSignUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }

        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                Thread t1 = new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        URI uri = null;
                        try {

                            uri = new URI("http://10.0.2.2:8080/TravelExpertsRESTJPA-1.0-SNAPSHOT/api/login/getcustomer/" + username + "/" + password);
                            URL url = uri.toURL();
                            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
                            String line;
                            StringBuffer sb = new StringBuffer();
                            while ((line = br.readLine()) != null)
                            {
                                sb.append(line);
                            }
                            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(openFileOutput("user.json", MODE_PRIVATE)));
                            bw.write(sb.toString());
                            bw.close();
                            br.close();

                            // check to see if return username and password matched
                            StringBuffer sb2 = new StringBuffer();
                            try {
                                BufferedReader br2 = new BufferedReader(new InputStreamReader(openFileInput("user.json")));
                                String line2;
                                while ((line2 = br2.readLine()) != null) {
                                    sb.append(line2);
                                }
                                br2.close();
                                JSONArray jsonArray = new JSONArray(sb.toString());
                                if (jsonArray.length() == 1) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast toast = Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_SHORT);
                                            toast.show();
                                        }
                                    });
                                    Executors.newSingleThreadExecutor().execute(new SaveCustomerId());

                                }
                                else
                                {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast toast = Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT);
                                            toast.show();
                                        }
                                    });

                                }


                            } catch (IOException | JSONException e) {
                                throw new RuntimeException(e);
                            }

                        } catch (URISyntaxException | IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                };
                t1.start();

            }
        });
    }
    private class SaveCustomerId implements Runnable {
        @Override
        public void run() {
            int customerId = 0;
            String custFirstName = null;
            String custLastName = null;
            String custAddress = null;
            String custCity = null;
            String custProv = null;
            String custPostal = null;
            String custCountry = null;
            String custHomePhone = null;
            String custBusPhone = null;
            String custEmail = null;
            int agentId = 0;
            StringBuffer sb = new StringBuffer();
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput("user.json")));
                String line;
                while ((line = br.readLine()) != null)
                {
                    sb.append(line);
                }
                br.close();
                JSONArray jsonArray = new JSONArray(sb.toString());
                ArrayList<Customer> list = new ArrayList<>();
                for (int i = 0; i <jsonArray.length(); i++)
                {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    customerId = Integer.parseInt(jsonObject.getString("customerId"));
                    custFirstName = jsonObject.getString("custFirstName");
                    custLastName = jsonObject.getString("custLastName");
                    custAddress = jsonObject.getString("custAddress");
                    custCity = jsonObject.getString("custCity");
                    custProv = jsonObject.getString("custProv");
                    custPostal = jsonObject.getString("custPostal");
                    custCountry = jsonObject.getString("custCountry");
                    custHomePhone = jsonObject.getString("custHomePhone");
                    custBusPhone = jsonObject.getString("custBusPhone");
                    custEmail = jsonObject.getString("custEmail");
                    agentId = Integer.parseInt(jsonObject.getString("agentId"));

                }


                preferences = getSharedPreferences("myprefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("customerId", customerId);
                editor.putString("custFirstName", custFirstName);
                editor.putString("custLastName", custLastName);
                editor.putString("custAddress", custAddress);
                editor.putString("custCity", custCity);
                editor.putString("custProv", custProv);
                editor.putString("custPostal", custPostal);
                editor.putString("custCountry", custCountry);
                editor.putString("custHomPhone", custHomePhone);
                editor.putString("custBusPhone", custBusPhone);
                editor.putString("custEmail", custEmail);
                editor.putInt("agentId", agentId);
                editor.commit();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));



            } catch (IOException | JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private static JsonObject readJsonFromFile(String filePath) {
        try {
            Gson gson = new Gson();
            JsonParser parser = new JsonParser();
            JsonElement jsonElement = parser.parse(new FileReader(filePath));
            return gson.fromJson(jsonElement, JsonObject.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}