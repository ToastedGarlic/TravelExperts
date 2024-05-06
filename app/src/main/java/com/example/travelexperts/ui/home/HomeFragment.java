package com.example.travelexperts.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.travelexperts.MainActivity;
import com.example.travelexperts.MessageFragment;
import com.example.travelexperts.R;
import com.example.travelexperts.databinding.FragmentHomeBinding;
import com.example.travelexperts.ui.login.LoginActivity;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    private  TextView txtCust;
    private Button btnProfile;
    private Button btnBook;
    private Button btnAgent;
    private Button btnServices;
    private Button btnLogout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        txtCust = root.findViewById(R.id.txtCust);

        SharedPreferences sp1=this.getActivity().getSharedPreferences("myprefs", Context.MODE_PRIVATE);

        txtCust.setText("Good afternoon, \n" + sp1.getString("custName", "Friend"));

        btnProfile = root.findViewById(R.id.btnProfile);
        btnProfile.setOnClickListener(profileListener);
        btnBook = root.findViewById(R.id.btnBook);
        btnBook.setOnClickListener(bookListener);
        btnAgent = root.findViewById(R.id.btnAgent);
        btnAgent.setOnClickListener(agentListener);
        btnServices = root.findViewById(R.id.btnServices);
        btnServices.setOnClickListener(serviceListener);
        btnLogout = root.findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(logoutListener);

        return root;

    }
    private final View.OnClickListener profileListener= v -> {
        Navigation.findNavController(getView()).navigate(R.id.action_prof);
    };

    private final View.OnClickListener bookListener= v -> {
        Navigation.findNavController(getView()).navigate(R.id.action_booking);
    };

    private final View.OnClickListener serviceListener= v -> {
        Navigation.findNavController(getView()).navigate(R.id.action_packages);
    };
    private final View.OnClickListener agentListener= v -> {
        Navigation.findNavController(getView()).navigate(R.id.action_go);
    };

    private final View.OnClickListener logoutListener= v -> {
        SharedPreferences prefs = getActivity().getSharedPreferences("myprefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.commit();
        startActivity(new Intent(getActivity(), LoginActivity.class));


    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}