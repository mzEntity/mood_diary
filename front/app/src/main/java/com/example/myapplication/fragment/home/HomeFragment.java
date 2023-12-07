package com.example.myapplication.fragment.home;

// HomeFragment.java
import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
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

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment_layout, container, false);

        renderPieChart(view);
        renderLinearChar(view);
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

    private void renderLinearChar(View view){
        LineChart lineChart = view.findViewById(R.id.mood_lineChart);

        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry(1, 10));
        entries.add(new Entry(2, 15));
        entries.add(new Entry(3, 8));
        entries.add(new Entry(4, 12));

        LineDataSet dataSet = new LineDataSet(entries, "月降雨量");

        dataSet.setColor(Color.BLUE);
        dataSet.setCircleColor(Color.BLUE);
        dataSet.setLineWidth(2f);
        dataSet.setCircleRadius(4f);
        dataSet.setMode(LineDataSet.Mode.LINEAR);
        dataSet.setDrawValues(true);

        LineData data = new LineData(dataSet);

        lineChart.setData(data);
        lineChart.getDescription().setEnabled(false);
        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        lineChart.getXAxis().setLabelCount(entries.size(), true);
        lineChart.getAxisRight().setEnabled(false);
        lineChart.getAxisLeft().setGranularity(1f);

        lineChart.invalidate();
    }


}
