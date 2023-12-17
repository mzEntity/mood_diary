package com.example.myapplication.main;

// MainActivity.java
import static android.app.PendingIntent.getService;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Menu;

import androidx.appcompat.widget.Toolbar;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.myapplication.R;
import com.example.myapplication.fragment.discovery.DiscoveryFragment;
import com.example.myapplication.fragment.home.HomeFragment;
import com.example.myapplication.fragment.Friend.FriendFragment;
import com.example.myapplication.fragment.music.MusicFragment;
import com.example.myapplication.fragment.space.SpaceFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.top_toolbar);
        setSupportActionBar(toolbar);

        toolbar.setOnMenuItemClickListener(item -> {
            Fragment selectedFragment = null;
            int id = item.getItemId();
            if (id == R.id.menu_item_music) {
                selectedFragment = new MusicFragment();
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            return true;
        });

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

        final Intent notificationIntent = new Intent(this, MyNotification.class);
        startService(notificationIntent);

        FragmentManager fragmentManager = getSupportFragmentManager();
        SessionManager sessionManager = new SessionManager(this);
        if(!sessionManager.isLoggedIn()){
            sessionManager.redirectToLogin(fragmentManager);
        }

        fragmentManager.beginTransaction().replace(R.id.fragment_container, new DiscoveryFragment()).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_toolbar_menu, menu);
        return true;
    }


}

