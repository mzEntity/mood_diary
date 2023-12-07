package com.example.myapplication.main;

// MainActivity.java
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.myapplication.R;
import com.example.myapplication.fragment.discovery.DiscoveryFragment;
import com.example.myapplication.fragment.home.HomeFragment;
import com.example.myapplication.fragment.Friend.FriendFragment;
import com.example.myapplication.fragment.space.SpaceFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setItemIconTintList(null);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int id = item.getItemId();
            if (id == R.id.menu_item_discovery) {
                selectedFragment = new DiscoveryFragment();
            } else if (id == R.id.menu_item_space) {
                selectedFragment = new SpaceFragment();
            } else if(id == R.id.menu_item_friend){
                selectedFragment = new FriendFragment();
            } else if (id == R.id.menu_item_home){
                selectedFragment = new HomeFragment();
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            return true;
        });

        FragmentManager fragmentManager = getSupportFragmentManager();
        SessionManager sessionManager = new SessionManager(this);
//        if(!sessionManager.isLoggedIn()){
            sessionManager.redirectToLogin(fragmentManager);
//        }

        fragmentManager.beginTransaction().replace(R.id.fragment_container, new DiscoveryFragment()).commit();
    }
}

