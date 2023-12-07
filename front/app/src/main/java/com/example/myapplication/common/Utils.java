package com.example.myapplication.common;

import android.content.Context;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.YearMonth;

public class Utils {
    public static void toastMsg(Context context, String s) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }

    public static int getCurrentYear(){
        LocalDate localDate = LocalDate.now();
        return localDate.getYear();
    }

    public static int getCurrentMonth(){
        return LocalDate.now().getMonthValue();
    }

    public static int getCurrentDay(){
        return LocalDate.now().getDayOfMonth();
    }

    public static int[] getDateOfStr(String date){
        String[] dateStrArr = date.split("-");
        int[] dateNumArr = new int[3];
        for(int i = 0; i < 3; i++){
            dateNumArr[i] = Integer.parseInt(dateStrArr[i]);
        }
        return dateNumArr;
    }
}
