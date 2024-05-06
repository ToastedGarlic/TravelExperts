package com.example.travelexperts.ui.mybooking;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.travelexperts.Model.Bookings;
import com.example.travelexperts.Model.Packages;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

// code by Jack
public class BookingFragment extends Fragment {
    ListView lvBooking;
    SharedPreferences preferences;
    public BookingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        getCustomerBooking();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booking, container, false);
        lvBooking = view.findViewById(R.id.lvBooking);
        ArrayList<Bookings> list = new ArrayList<>();

        StringBuffer sb = new StringBuffer();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(requireContext().openFileInput("bookingdata.json")));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            JSONArray jsonArray = new JSONArray(sb.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                int bookingId = jsonObject.getInt("id");
                String bookingDate = jsonObject.getString("bookingDate");
                String bookingNo = jsonObject.getString("bookingNo");
                double travelerCount = jsonObject.getDouble("travelerCount");
                int customerId = jsonObject.getInt("customerId");

                list.add(new Bookings(bookingId, bookingDate, bookingNo, travelerCount, customerId));
            }
            // load to listview
            ArrayAdapter<Bookings> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, list);
            lvBooking.setAdapter(adapter);
        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }
        return view;
    }

    private void getCustomerBooking() {
        preferences = getActivity().getSharedPreferences("myprefs", Context.MODE_PRIVATE);
        int customerId = preferences.getInt("customerId", 0);
        URI uri = null;
        try {
            uri = new URI("http://10.0.2.2:8080/TravelExpertsRESTJPA-1.0-SNAPSHOT/api/login/customerbooking/" + customerId);
            URL url = uri.toURL();
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            StringBuffer sb = new StringBuffer();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(requireContext().openFileOutput("bookingdata.json", Context.MODE_PRIVATE)));
            bw.write(sb.toString());
            bw.close();
            br.close();
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}