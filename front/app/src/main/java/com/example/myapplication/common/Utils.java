package com.example.myapplication.common;

import android.content.Context;
import android.widget.Toast;

public class Utils {
    public static void toastMsg(Context context, String s) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }
}
