package com.example.travelexperts.ui.customerinfo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.travelexperts.R;

public class CustomerInfoActivity extends AppCompatActivity {

    private EditText edtFirstName, edtLastName, edtAddress, edtCity, edtProv, edtPostal,
            edtCountry, edtHomePhone, edtBusPhone, edtEmail, edtAgentId;
    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_customerinfo);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;


        });
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

        preferences = getSharedPreferences("myprefs", Context.MODE_PRIVATE);
        loadCustomer();

    }

    private void loadCustomer() {
        int num = 105;
        edtFirstName.setText(preferences.getString("custFirstName", null));
        edtLastName.setText(preferences.getString("custLastName", null));
        edtAddress.setText(preferences.getString("custAddress", null));
        edtCity.setText(preferences.getString("custCity", null));
        edtProv.setText(preferences.getString("custProv", null));
        edtPostal.setText(preferences.getString("custPostal", null));
        edtCountry.setText(preferences.getString("custCountry", null));
        edtHomePhone.setText(preferences.getString("custHomPhone", null));
        edtBusPhone.setText(preferences.getString("custBusPhone", null));
        edtEmail.setText(preferences.getString("custEmail", null));
        edtAgentId.setText(preferences.getInt("agentId", 0) + "");

    }
}