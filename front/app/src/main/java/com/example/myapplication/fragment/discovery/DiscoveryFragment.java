package com.example.myapplication.fragment.discovery;

// DiscoveryFragment.java
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.common.HTTPCallBack;
import com.example.myapplication.common.HTTPHelper;
import com.example.myapplication.common.Utils;
import com.example.myapplication.main.MediaManager;
import com.example.myapplication.main.SessionManager;
import com.example.myapplication.main.UserInfoItem;

import org.json.JSONException;
import org.json.JSONObject;

public class DiscoveryFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.discovery_fragment_layout, container, false);

        SessionManager sessionManager = new SessionManager(requireContext());
        if(!sessionManager.isLoggedIn()){
            sessionManager.redirectToLogin(getParentFragmentManager());
            return view;
        }
        UserInfoItem user = sessionManager.getUserDetails();

        int userId = user.getId();

        SeekBar seekBar = view.findViewById(R.id.mood_seekBar);
        TextView seekBarValTextView = view.findViewById(R.id.mood_rate_val_text);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekBarValTextView.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        Button submitButton = view.findViewById(R.id.mood_rate_submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int progress = seekBar.getProgress();
                rateMood(userId, progress);
            }
        });

        return view;
    }

    public void rateMood(int userId, int score){
        JSONObject requestObject = new JSONObject();
        try{
            requestObject.put("userId", userId);
            requestObject.put("score", score);
        } catch (JSONException e){
            Utils.toastMsg(requireContext(), "Error in sending request");
        }
        String url = "/updateMoodScore";
        HTTPHelper.post(url, requestObject, new HTTPCallBack() {
            @Override
            public void getSuccess(JSONObject returnObject, String msg) {
                HTTPCallBack.super.getSuccess(returnObject, msg);
                getActivity().runOnUiThread(()->{
                    Utils.toastMsg(requireContext(), "rate " + score);
                    Intent intent = new Intent("RATE_SCORE");
                    intent.putExtra("score", score);

                    getActivity().sendBroadcast(intent);
                });
            }

            @Override
            public void getNotSuccess(JSONObject returnObject, String msg) {
                HTTPCallBack.super.getNotSuccess(returnObject, msg);
                getActivity().runOnUiThread(()->{
                    Utils.toastMsg(requireContext(), msg);
                });
            }
        });
    }
}
