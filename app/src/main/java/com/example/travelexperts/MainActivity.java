package com.example.travelexperts;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.travelexperts.Model.Packages;
import com.example.travelexperts.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private Button btnRegister; // Button for registering


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        setupDrawerNavigation();

        initiatePackageRetrieval();

    }

    private void setupDrawerNavigation() {
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        navigationView.setNavigationItemSelectedListener(this);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.nav_register, R.id.nav_login, R.id.nav_profile)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    private void initiatePackageRetrieval() {
        PackageRetrieval packageRetrieval = new PackageRetrieval();
        new Thread(packageRetrieval).start();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_register) {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
            DrawerLayout drawer = binding.drawerLayout;
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }

        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
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
           //     ArrayAdapter<Packages> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, list);
           //     runOnUiThread(() -> lvPackages.setAdapter(adapter));
            } catch (IOException | JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
