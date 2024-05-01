package com.example.travelexperts;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.travelexperts.Model.Customer;
import com.google.gson.Gson;
import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class RegisterActivity extends AppCompatActivity {

    private EditText edtFirstName, edtLastName, edtAddress, edtCity, edtProv, edtPostal, edtCountry, edtHomePhone, edtBusPhone, edtEmail, edtAgentId;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Bind views
        edtFirstName = findViewById(R.id.edtFirstName);
        edtLastName = findViewById(R.id.edtLastName);
        edtAddress = findViewById(R.id.edtAddress);
        edtCity = findViewById(R.id.edtCity);
        edtProv = findViewById(R.id.edtProvince);
        edtPostal = findViewById(R.id.edtPostalCode);
        edtCountry = findViewById(R.id.edtCountry);
        edtHomePhone = findViewById(R.id.edtHomePhone);
        edtBusPhone = findViewById(R.id.edtBusPhone);
        edtEmail = findViewById(R.id.edtEmail);
        edtAgentId = findViewById(R.id.edtAgentId);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerCustomer();
            }
        });
    }

    private void registerCustomer() {
        Customer customer = new Customer(
                0, // Assuming ID is auto-generated and will be replaced by the backend
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
                Integer.parseInt(edtAgentId.getText().toString())
        );

        Gson gson = new Gson();
        String jsonCustomer = gson.toJson(customer);

        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL("http://10.0.2.2:8080/TravelExpertsRESTJPA/api/customer/register");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                    connection.setDoOutput(true);

                    try (OutputStream os = connection.getOutputStream()) {
                        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                        writer.write(jsonCustomer);
                        writer.flush();
                        writer.close();

                        int responseCode = connection.getResponseCode();
                        runOnUiThread(() -> {
                            if (responseCode == HttpURLConnection.HTTP_OK) {
                                Toast.makeText(RegisterActivity.this, "Registered Successfully", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(RegisterActivity.this, "Registration failed: " + responseCode, Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                } catch (Exception e) {
                    runOnUiThread(() -> Toast.makeText(RegisterActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show());
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }
}
