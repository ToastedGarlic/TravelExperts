package com.example.travelexperts.ui.vacationpackage;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.travelexperts.Model.Packages;
import com.example.travelexperts.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.concurrent.Executors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PackageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PackageFragment extends Fragment {

    private ListView lvPackage;

    public PackageFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_package, container, false);
        lvPackage = view.findViewById(R.id.lvPackage);
        ArrayList<Packages> list = new ArrayList<>();

        StringBuffer sb = new StringBuffer();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(requireContext().openFileInput("packagedata.json")));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            JSONArray jsonArray = new JSONArray(sb.toString());
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
            // load to listview
            ArrayAdapter<Packages> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, list);
            lvPackage.setAdapter(adapter);
        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }
        return view;
    }



}