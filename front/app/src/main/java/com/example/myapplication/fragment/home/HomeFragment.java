package com.example.myapplication.fragment.home;

// HomeFragment.java
import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
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
import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeFragment extends Fragment {
    private int year_check;
    private int month_check;

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

        TextView textView = view.findViewById(R.id.welcome_home_text);
        textView.setText("Welcome " + user.getUsername());

        
        renderInfo(user.getId(), view);
        

        Button nextMonthBtn = view.findViewById(R.id.next_home_button);
        nextMonthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFragment.this.month_check += 1;
                if(HomeFragment.this.month_check == 13){
                    HomeFragment.this.month_check = 1;
                    HomeFragment.this.year_check += 1;
                }
                renderInfo(user.getId(), view);
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
                renderInfo(user.getId(), view);
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
    
    private void setMonth(View view){
        TextView monthView = view.findViewById(R.id.month_home_text);
        StringBuilder text = new StringBuilder(this.year_check + "-");
        if(this.month_check < 10){
            text.append("0");
        }
        text.append(this.month_check);
        monthView.setText(text);
    }

    private void renderInfo(int userId, View view){
        setMonth(view);
        getMonthlyMoodScore(userId, this.year_check, this.month_check, view);
        getMonthlyDiaryStats(userId, this.year_check, this.month_check, view);
    }

    private void renderPieChart(View view, List<DiaryStatsItem> stats){
        PieChart pieChart = view.findViewById(R.id.mood_pieChart);

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

    private void renderLinearChart(View view, List<MoodScoreItem> scores){
        LineChart lineChart = view.findViewById(R.id.mood_lineChart);

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

    void getMonthlyMoodScore(int userId, int year, int month, View view){
        String url = "/getMonthlyMoodScore";
        HashMap<String, String> params = new HashMap<>();
        params.put("userId", "" + userId);
        params.put("year", "" + year);
        params.put("month", "" + month);
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
                        renderLinearChart(view, scores);
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

    void getMonthlyDiaryStats(int userId, int year, int month, View view){
        String url = "/getMonthlyDiaryStats";
        HashMap<String, String> params = new HashMap<>();
        params.put("userId", "" + userId);
        params.put("year", "" + year);
        params.put("month", "" + month);
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
                            stats.add(new DiaryStatsItem(moodId, moodName, count, year, month));
                        }

                        renderPieChart(view, stats);
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
