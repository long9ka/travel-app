package com.example.travelapp.ui.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.travelapp.R;
import com.example.travelapp.store.UserStore;
import com.example.travelapp.ui.SplashActivity;

import java.util.Objects;

public class Setting extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_setting, container, false);
        final Button logoutButton = root.findViewById(R.id.button_setting);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserStore userStore = new UserStore(root.getContext());
                userStore.removeUser();
                startActivity(new Intent(root.getContext(), SplashActivity.class));
                Objects.requireNonNull(getActivity()).finish();
            }
        });
        return root;
    }
}