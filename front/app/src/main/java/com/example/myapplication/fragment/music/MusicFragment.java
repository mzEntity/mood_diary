package com.example.myapplication.fragment.music;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.fragment.discovery.DiscoveryFragment;
import com.example.myapplication.main.MediaManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MusicFragment extends Fragment {
    private static MediaManager mediaManager = new MediaManager();
    private static HashMap<String, Integer> musicSrc;
    private static List<String> musicList;

    private static int index;

    static {
        musicSrc = new HashMap<>();
        musicSrc.put("all my days", R.raw.all_my_days);
        musicSrc.put("la la la", R.raw.la_la_la);
        musicSrc.put("river flows in you", R.raw.river_flows_in_you);
        musicSrc.put("stay alive", R.raw.stay_alive);

        musicList = new ArrayList<>();
        for(String name: musicSrc.keySet()){
            musicList.add(name);
        }

        index = 0;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.music_fragment_layout, container, false);

        Button mediaPrev = view.findViewById(R.id.media_left_switch);
        Button mediaNext = view.findViewById(R.id.media_right_switch);

        mediaPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MusicFragment.index = (MusicFragment.index - 1 + MusicFragment.musicList.size()) % MusicFragment.musicList.size();
                setTitle(view);
                if(MusicFragment.mediaManager.hasMusicLoaded()){
                    MusicFragment.mediaManager.stopAudio();
                    MusicFragment.mediaManager.clearSource();
                }
            }
        });
        mediaNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MusicFragment.index = (MusicFragment.index + 1) % MusicFragment.musicList.size();
                setTitle(view);
                if(MusicFragment.mediaManager.hasMusicLoaded()){
                    MusicFragment.mediaManager.stopAudio();
                    MusicFragment.mediaManager.clearSource();
                }
            }
        });

        Button mediaStart = view.findViewById(R.id.media_start);
        Button mediaStop = view.findViewById(R.id.media_stop);

        mediaStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!MusicFragment.mediaManager.hasMusicLoaded()){
                    MediaPlayer mediaPlayer = MediaPlayer.create(getContext(),
                            MusicFragment.musicSrc.get(MusicFragment.musicList.get(MusicFragment.index)));
                    MusicFragment.mediaManager.setPlayer(mediaPlayer);
                }
                MusicFragment.mediaManager.switchAudioState();
            }
        });
        mediaStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MusicFragment.mediaManager.stopAudio();
            }
        });

        return view;
    }

    private void setTitle(View view){
        TextView titleView = view.findViewById(R.id.music_title_text);
        Log.i("MusicFragment", "" + titleView);
        Log.i("MusicFragment", index + ": " + MusicFragment.musicList.get(index));
        titleView.setText(MusicFragment.musicList.get(index));
    }

}
