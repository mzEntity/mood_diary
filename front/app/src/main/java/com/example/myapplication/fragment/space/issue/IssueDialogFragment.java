package com.example.myapplication.fragment.space.issue;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.myapplication.R;
import com.example.myapplication.common.Config;
import com.example.myapplication.common.HTTPCallBack;
import com.example.myapplication.common.HTTPHelper;
import com.example.myapplication.common.Utils;
import com.example.myapplication.main.SessionManager;
import com.example.myapplication.main.UserInfoItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class IssueDialogFragment extends DialogFragment {

    private static HashMap<String, Integer> moodMap = new HashMap<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.issue_daily_dialog, container, false);

        SessionManager sessionManager = new SessionManager(requireContext());
        if(!sessionManager.isLoggedIn()){
            sessionManager.redirectToLogin(getParentFragmentManager());
            return view;
        }
        UserInfoItem user = sessionManager.getUserDetails();

        int userId = user.getId();

        EditText etTitle = view.findViewById(R.id.issue_title_edit);
        EditText etContent = view.findViewById(R.id.issue_content_edit);

        Spinner spinner = view.findViewById(R.id.issue_mood_spinner);
        List<String> list = new ArrayList<>();
        for(int moodId: Config.moodMap.keySet()){
            String moodName = Config.moodMap.get(moodId).getName();
            IssueDialogFragment.moodMap.put(moodName, moodId);
            list.add(moodName);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Button btnSubmit = view.findViewById(R.id.issue_submit_button);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etTitle.getText().toString().trim();
                String content = etContent.getText().toString().trim();
                String mood = spinner.getSelectedItem().toString().trim();
                if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(content)) {
                    // 处理发布逻辑
                    Toast.makeText(requireContext(), title + "(" + mood + "): " + content, Toast.LENGTH_SHORT).show();
                    int moodId = IssueDialogFragment.moodMap.get(mood);
                    commitDiary(userId, moodId, title, content);
                } else {
                    Toast.makeText(requireContext(), "Please enter an issue", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }
    @Override
    public void onStart() {
        super.onStart();
        Window win = getDialog().getWindow();
        // 一定要设置Background，如果不设置，window属性设置无效
        win.setBackgroundDrawable(new ColorDrawable(Color.WHITE));

        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);

        WindowManager.LayoutParams params = win.getAttributes();
        params.gravity = Gravity.BOTTOM;
        // 使用ViewGroup.LayoutParams，以便Dialog 宽度充满整个屏幕
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        win.setAttributes(params);
    }

    public void commitDiary(int authorId, int moodTypeId, String title, String content){
        JSONObject requestObject = new JSONObject();
        try{
            requestObject.put("author_id", authorId);
            requestObject.put("mood_type_id", moodTypeId);
            requestObject.put("title", title);
            requestObject.put("content", content);
        } catch (JSONException e){
            Utils.toastMsg(requireContext(), "Error in sending request");
        }
        String url = "/commitDiary";
        HTTPHelper.post(url, requestObject, new HTTPCallBack() {
            @Override
            public void getSuccess(JSONObject returnObject, String msg) {
                HTTPCallBack.super.getSuccess(returnObject, msg);
                getActivity().runOnUiThread(()->{
                    Utils.toastMsg(requireContext(), "commit success");
                    dismiss();
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
