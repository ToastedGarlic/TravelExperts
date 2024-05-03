package com.example.travelexperts;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.travelexperts.Model.Bookings;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.Executors;

public class ViewBookingsActivity extends AppCompatActivity {

    private ListView lvBookings;
    private ArrayAdapter<String> adapter;
    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bookings);

        lvBookings = findViewById(R.id.lvBookings);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        lvBookings.setAdapter(adapter);

        fetchBookings();
    }

    private void fetchBookings() {
        int customerId = 1; // This should be dynamically set based on the logged-in user

        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                URL url = new URL("http://10.0.2.2:8080/TravelExpertsRESTJPA/api/booking/getcustomerbookings/" + customerId);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Accept", "application/json");

                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    List<Bookings> bookings = gson.fromJson(new InputStreamReader(connection.getInputStream()),
                            new TypeToken<List<Bookings>>(){}.getType());

                    runOnUiThread(() -> {
                        for (Bookings booking : bookings) {
                            adapter.add(String.format("Booking ID: %d, Date: %s", booking.getBookingId(), booking.getBookingDate()));
                        }
                        adapter.notifyDataSetChanged();
                    });
                } else {
                    runOnUiThread(() -> Toast.makeText(ViewBookingsActivity.this, "Failed to retrieve bookings", Toast.LENGTH_LONG).show());
                }

                connection.disconnect();
            } catch (Exception e) {
                Log.e("ViewBookingsActivity", "Error fetching bookings", e);
                runOnUiThread(() -> Toast.makeText(ViewBookingsActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show());
            }
        });
    }
}