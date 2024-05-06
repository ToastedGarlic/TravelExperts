package com.example.travelexperts;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

// code by jack, modified by Michael to work in fragment
public class ProfileFragment extends Fragment {
    private EditText edtFirstName, edtLastName, edtAddress, edtCity, edtProv, edtPostal,
            edtCountry, edtHomePhone, edtBusPhone, edtEmail, edtAgentId;
    SharedPreferences preferences;
    public ProfileFragment() {
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
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        edtFirstName = view.findViewById(R.id.edtFirstName);
        edtLastName = view.findViewById(R.id.edtLastName);
        edtAddress = view.findViewById(R.id.edtAddress);
        edtCity = view.findViewById(R.id.edtCity);
        edtProv = view.findViewById(R.id.edtProv);
        edtPostal = view.findViewById(R.id.edtPostal);
        edtCountry = view.findViewById(R.id.edtCountry);
        edtHomePhone = view.findViewById(R.id.edtHomePhone);
        edtBusPhone = view.findViewById(R.id.edtBusPhone);
        edtEmail = view.findViewById(R.id.edtEmail);
        edtAgentId = view.findViewById(R.id.edtAgentId);
        preferences = getActivity().getSharedPreferences("myprefs", Context.MODE_PRIVATE);
        loadCustomer();
        return view;
    }
    private void loadCustomer() {
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