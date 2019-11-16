package com.example.travelapp.ui.createTour;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.travelapp.MapsActivity;
import com.example.travelapp.R;

public class CreateTourFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_create_tour, container, false);
        startActivity(new Intent(getActivity(), MapsActivity.class));
        return root;
    }
}