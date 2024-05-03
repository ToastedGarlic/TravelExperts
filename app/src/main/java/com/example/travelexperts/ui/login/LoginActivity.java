package com.example.travelexperts.ui.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.travelexperts.Model.Customer;
import com.example.travelexperts.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
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

                            Executors.newSingleThreadExecutor().execute(new SaveCustomerId());

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
            int userid;
            String stringid = null;
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