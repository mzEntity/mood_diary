package com.example.myapplication.main;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.myapplication.R;
import com.example.myapplication.common.HTTPCallBack;
import com.example.myapplication.common.Utils;
import com.example.myapplication.common.HTTPHelper;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginDialogFragment extends DialogFragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_layout, container, false);

        EditText etUsername = view.findViewById(R.id.login_username_edit);
        EditText etPassword = view.findViewById(R.id.login_password_edit);

        RadioGroup radioGroup = view.findViewById(R.id.login_radio_group);
        Button btnSubmit = view.findViewById(R.id.login_submit_button);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                    Utils.toastMsg(requireContext(), "请输入用户名和密码");
                    return;
                }
                RadioButton checkedButton = view.findViewById(radioGroup.getCheckedRadioButtonId());
                String mode = checkedButton.getText().toString().trim();
                if(mode.equals("login")){
                    login(username, password);
                } else if(mode.equals("signin")){
                    signin(username, password);
                }
            }
        });

        return view;
    }

    public void login(String username, String password){
        JSONObject requestObject = new JSONObject();
        try{
            requestObject.put("username", username);
            requestObject.put("password", password);
        } catch (JSONException e){
            Utils.toastMsg(requireContext(), "Error in sending request");
        }
        String url = "/login";
        HTTPHelper.post(url, requestObject, new HTTPCallBack() {
            @Override
            public void getSuccess(JSONObject returnObject, String msg) {
                HTTPCallBack.super.getSuccess(returnObject, msg);
                getActivity().runOnUiThread(()->{
                    try{
                        JSONObject responseObject = returnObject.getJSONObject("object");
                        int userId = responseObject.getInt("id");
                        String username = responseObject.getString("username");
                        SessionManager sessionManager = new SessionManager(requireContext());
                        sessionManager.createLoginSession(username, userId);
                        Utils.toastMsg(requireContext(), "Hello " + username);
                        dismiss();
                    }catch (JSONException e){
                    }
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

    public void signin(String username, String password){
        JSONObject requestObject = new JSONObject();
        try{
            requestObject.put("username", username);
            requestObject.put("password", password);
        } catch (JSONException e){
            Utils.toastMsg(requireContext(), "Error in sending request");
        }
        String url = "/signin";
        HTTPHelper.post(url, requestObject, new HTTPCallBack() {
            @Override
            public void getSuccess(JSONObject returnObject, String msg) {
                HTTPCallBack.super.getSuccess(returnObject, msg);
                getActivity().runOnUiThread(()->{
                    Utils.toastMsg(requireContext(), "Register " + username);
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
}