package com.example.myapplication.fragment.home;

// HomeFragment.java

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.common.Config;
import com.example.myapplication.common.HTTPCallBack;
import com.example.myapplication.common.HTTPHelper;
import com.example.myapplication.common.Mood;
import com.example.myapplication.common.Utils;
import com.example.myapplication.fragment.Friend.request.AddFriendDialogFragment;
import com.example.myapplication.fragment.space.SpaceBaseAdapter;
import com.example.myapplication.fragment.space.SpaceItem;
import com.example.myapplication.main.SessionManager;
import com.example.myapplication.main.UserInfoItem;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeFragment extends Fragment {
    private int year_check;
    private int month_check;

    private UserInfoItem user;

    private LineChart lineChart;
    private PieChart pieChart;

    private TextView monthText;

    private TextView welcomeView;

    public HomeFragment() {
        this.year_check = Utils.getCurrentYear();
        this.month_check = Utils.getCurrentMonth();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.home_fragment_layout, container, false);
        SessionManager sessionManager = new SessionManager(requireContext());
        if(!sessionManager.isLoggedIn()){
            sessionManager.redirectToLogin(getParentFragmentManager());
            return view;
        }
        UserInfoItem user = sessionManager.getUserDetails();

        this.user = user;

        welcomeView = view.findViewById(R.id.welcome_home_text);

        lineChart = view.findViewById(R.id.mood_lineChart);
        pieChart = view.findViewById(R.id.mood_pieChart);

        monthText = view.findViewById(R.id.month_home_text);

        
        renderInfo();
        

        Button nextMonthBtn = view.findViewById(R.id.next_home_button);
        nextMonthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFragment.this.month_check += 1;
                if(HomeFragment.this.month_check == 13){
                    HomeFragment.this.month_check = 1;
                    HomeFragment.this.year_check += 1;
                }
                renderInfo();
            }
        });
        Button previousMonthBtn = view.findViewById(R.id.previous_home_button);
        previousMonthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFragment.this.month_check -= 1;
                if(HomeFragment.this.month_check == 0){
                    HomeFragment.this.month_check = 12;
                    HomeFragment.this.year_check -= 1;
                }
                renderInfo();
            }
        });

        Button logoutBtn = view.findViewById(R.id.logout_home_button);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.logoutUser();
                sessionManager.redirectToLogin(getParentFragmentManager());
            }
        });
        return view;
    }

    private BroadcastReceiver loginSuccessReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.i("HomeFragment", "receive message " + action);
            if ("LOGIN_SUCCESS".equals(action)) {
                // 执行刷新操作，更新数据
                UserInfoItem user = new SessionManager(context).getUserDetails();
                HomeFragment.this.user = user;
                Log.i("HomeFragment", "do render");
                renderInfo();
                Log.i("HomeFragment", "render finish");
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        // 在onResume方法中注册广播接收器
        IntentFilter intentFilter = new IntentFilter("LOGIN_SUCCESS");
        getActivity().registerReceiver(loginSuccessReceiver, intentFilter);
        Log.i("HomeFragment", "bind receiver");
    }

    private void setHeader(){
        welcomeView.setText("welcome " + user.getUsername());
        StringBuilder text = new StringBuilder(this.year_check + "-");
        if(this.month_check < 10){
            text.append("0");
        }
        text.append(this.month_check);
        monthText.setText(text);
    }

    private void renderInfo(){
        setHeader();
        getMonthlyMoodScore();
        getMonthlyDiaryStats();
    }

    private void renderPieChart(List<DiaryStatsItem> stats){
        List<PieEntry> entries = new ArrayList<>();

        for(DiaryStatsItem diaryStatsItem: stats){
            if(diaryStatsItem.getCount() == 0) continue;
            entries.add(new PieEntry(diaryStatsItem.getCount(), diaryStatsItem.getMoodName()));
        }

        PieDataSet dataSet = new PieDataSet(entries, "月日志统计");

        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        PieData data = new PieData(dataSet);

        pieChart.setData(data);
        pieChart.setEntryLabelColor(R.color.black);
        pieChart.setEntryLabelTextSize(12f);
        pieChart.getDescription().setEnabled(false);
        pieChart.setDrawHoleEnabled(false);
        pieChart.animateXY(1000, 1000);

        pieChart.invalidate();
    }

    private void renderLinearChart(List<MoodScoreItem> scores){

        List<Entry> entries = new ArrayList<>();
        for(MoodScoreItem moodScoreItem: scores){
            entries.add(new Entry(moodScoreItem.getDay(), moodScoreItem.getScore()));
        }

        LineDataSet dataSet = new LineDataSet(entries, "月心情打分表");

        dataSet.setColor(Color.BLUE);
        dataSet.setCircleColor(Color.BLUE);
        dataSet.setLineWidth(2f);
        dataSet.setCircleRadius(4f);
        dataSet.setMode(LineDataSet.Mode.LINEAR);
        dataSet.setDrawValues(true);

        LineData data = new LineData(dataSet);

        lineChart.setData(data);
        lineChart.getDescription().setEnabled(false);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        xAxis.setValueFormatter(new ValueFormatter() {
            private final DecimalFormat decimalFormat = new DecimalFormat("###");
            @Override
            public String getFormattedValue(float value) {
                return decimalFormat.format(value);
            }
        });

        lineChart.getAxisRight().setEnabled(false);
        lineChart.getAxisLeft().setGranularity(1f);

        lineChart.invalidate();
    }

    void getMonthlyMoodScore(){
        String url = "/getMonthlyMoodScore";
        HashMap<String, String> params = new HashMap<>();
        params.put("userId", "" + user.getId());
        params.put("year", "" + year_check);
        params.put("month", "" + month_check);
        HTTPHelper.get(url, params, new HTTPCallBack() {
            @Override
            public void getSuccess(JSONObject returnObject, String msg) {
                HTTPCallBack.super.getSuccess(returnObject, msg);
                getActivity().runOnUiThread(()->{
                    try {
                        JSONObject responseObject = returnObject.getJSONObject("object");
                        JSONArray array = responseObject.getJSONArray("moodScores");
                        int length = array.length();
                        List<MoodScoreItem> scores = new ArrayList<>();
                        for(int i = 0; i < length; i++){
                            JSONObject scoreObject = array.getJSONObject(i);
                            int score = scoreObject.getInt("moodScore");
                            String date = scoreObject.getString("date");
                            int[] dateArr = Utils.getDateOfStr(date);

                            scores.add(new MoodScoreItem(
                                    dateArr[0], dateArr[1], dateArr[2], score
                            ));
                        }
                        renderLinearChart(scores);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                });
            }

            @Override
            public void getNotSuccess(JSONObject responseObject, String msg) {
                HTTPCallBack.super.getNotSuccess(responseObject, msg);
                getActivity().runOnUiThread(()->{
                    Utils.toastMsg(requireContext(), msg);
                });
            }
        });
    }

    void getMonthlyDiaryStats(){
        String url = "/getMonthlyDiaryStats";
        HashMap<String, String> params = new HashMap<>();
        params.put("userId", "" + user.getId());
        params.put("year", "" + year_check);
        params.put("month", "" + month_check);
        HTTPHelper.get(url, params, new HTTPCallBack() {
            @Override
            public void getSuccess(JSONObject returnObject, String msg) {
                HTTPCallBack.super.getSuccess(returnObject, msg);
                getActivity().runOnUiThread(()->{
                    try {
                        JSONObject responseObject = returnObject.getJSONObject("object");
                        JSONObject countMap = responseObject.getJSONObject("diaryCount");
                        HashMap<Integer, Mood> allMoods = Config.moodMap;
                        List<DiaryStatsItem> stats = new ArrayList<>();
                        for(int moodId: allMoods.keySet()){
                            String moodName = allMoods.get(moodId).getName();
                            int count = countMap.getInt(moodName);
                            stats.add(new DiaryStatsItem(moodId, moodName, count, year_check, month_check));
                        }

                        renderPieChart(stats);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                });
            }

            @Override
            public void getNotSuccess(JSONObject responseObject, String msg) {
                HTTPCallBack.super.getNotSuccess(responseObject, msg);
                getActivity().runOnUiThread(()->{
                    Utils.toastMsg(requireContext(), msg);
                });
            }
        });

    }
}
