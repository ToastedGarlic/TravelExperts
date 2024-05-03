package com.example.travelexperts.ui.vacationpackage;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.travelexperts.MainActivity;
import com.example.travelexperts.Model.Packages;
import com.example.travelexperts.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.Executors;

public class VacationPackageActivity extends AppCompatActivity {

    ListView lvPackages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_vacationpackage);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        lvPackages = findViewById(R.id.lvPackages);
        Executors.newSingleThreadExecutor().execute(new PackageRetrieval());


    }

    private class PackageRetrieval implements Runnable {
        @Override
        public void run() {
            StringBuffer sb = new StringBuffer();
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput("packagedata.json")));
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                br.close();
                JSONArray jsonArray = new JSONArray(sb.toString());
                ArrayList<Packages> list = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    int packageId = jsonObject.getInt("id");
                    String pkgName = jsonObject.getString("pkgName");
                    String pkgStartDate = jsonObject.getString("pkgStartDate");
                    String pkgEndDate = jsonObject.getString("pkgEndDate");
                    String pkgDesc = jsonObject.getString("pkgDesc");
                    double pkgBasePrice = jsonObject.getDouble("pkgBasePrice");
                    double pkgAgencyCommission = jsonObject.getDouble("pkgAgencyCommission");
                    list.add(new Packages(packageId, pkgName, pkgStartDate, pkgEndDate, pkgDesc, pkgBasePrice, pkgAgencyCommission));
                }
                ArrayAdapter<Packages> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, list);
                runOnUiThread(() -> lvPackages.setAdapter(adapter));
            } catch (IOException | JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }
}