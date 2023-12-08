package com.example.myapplication.fragment.home;

// HomeFragment.java
import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.common.HTTPCallBack;
import com.example.myapplication.common.HTTPHelper;
import com.example.myapplication.common.Utils;
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
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment_layout, container, false);
        SessionManager sessionManager = new SessionManager(requireContext());
        if(!sessionManager.isLoggedIn()){
            sessionManager.redirectToLogin(getParentFragmentManager());
            return view;
        }
        UserInfoItem user = sessionManager.getUserDetails();
        int year = Utils.getCurrentYear();
        int month = Utils.getCurrentMonth();

        TextView textView = view.findViewById(R.id.welcome_home_text);
        textView.setText("Welcome " + user.getUsername());
        getMonthlyMoodScore(user.getId(), year, month, view);
        return view;
    }

    private void renderPieChart(View view){
        PieChart pieChart = view.findViewById(R.id.mood_pieChart);

        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(10, "下雨天"));
        entries.add(new PieEntry(15, "晴天"));
        entries.add(new PieEntry(7, "阴天"));

        PieDataSet dataSet = new PieDataSet(entries, "天气统计");

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
        xAxis.setLabelCount(entries.size(), true);
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
}
