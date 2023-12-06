package com.example.myapplication.main;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.fragment.app.FragmentManager;

import java.util.HashMap;

public class SessionManager {

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private static int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "Android_Session";
    private static final String KEY_IS_LOGIN = "IsLoggedIn";

    public static final String KEY_NAME = "name";
    public static final String KEY_ID = "id";


    public SessionManager(Context context) {
        this.pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        this.editor = this.pref.edit();
    }

    public void createLoginSession(String name, int id) {
        editor.putBoolean(KEY_IS_LOGIN, true);
        editor.putString(KEY_NAME, name);
        editor.putInt(KEY_ID, id);
        editor.commit();
    }

    public UserInfoItem getUserDetails() {
        if(!isLoggedIn()){
            return null;
        }
        int id = pref.getInt(KEY_ID, -1);
        String username = pref.getString(KEY_NAME, null);

        UserInfoItem user = new UserInfoItem(id, username);
        return user;
    }

    public void logoutUser() {
        editor.clear();
        editor.commit();
    }


    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGIN, false);
    }

    public void redirectToLogin(FragmentManager fragmentManager){
        LoginDialogFragment loginDialogFragment = new LoginDialogFragment();
        loginDialogFragment.show(fragmentManager, "edit_dialog");
    }
}