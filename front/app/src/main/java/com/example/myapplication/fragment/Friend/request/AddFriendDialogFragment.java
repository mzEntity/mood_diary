package com.example.myapplication.fragment.Friend.request;

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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.myapplication.R;
import com.example.myapplication.common.HTTPCallBack;
import com.example.myapplication.common.HTTPHelper;
import com.example.myapplication.common.Utils;
import com.example.myapplication.main.SessionManager;
import com.example.myapplication.main.UserInfoItem;

import org.json.JSONException;
import org.json.JSONObject;

public class AddFriendDialogFragment extends DialogFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_friend_dialog, container, false);

        SessionManager sessionManager = new SessionManager(requireContext());
        if(!sessionManager.isLoggedIn()){
            sessionManager.redirectToLogin(getParentFragmentManager());
            return view;
        }
        UserInfoItem user = sessionManager.getUserDetails();

        EditText etFriendName = view.findViewById(R.id.friend_name_edit);
        EditText etIssue = view.findViewById(R.id.validation_message_edit);
        Button btnSubmit = view.findViewById(R.id.add_friend_submit_button);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String issue = etIssue.getText().toString().trim();
                String name = etFriendName.getText().toString().trim();
                if (!TextUtils.isEmpty(issue) && !TextUtils.isEmpty(name)) {
                    requestFriend(user.getId(), name, issue);
                } else {
                    Toast.makeText(requireContext(), "Please enter username and message", Toast.LENGTH_SHORT).show();
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

    public void requestFriend(int userId, String friendName, String issue){
        JSONObject requestObject = new JSONObject();
        try{
            requestObject.put("user_id", userId);
            requestObject.put("friend_name", friendName);
            requestObject.put("validation_msg", issue);
        } catch (JSONException e){
            Utils.toastMsg(requireContext(), "Error in sending request");
        }
        String url = "/requestFriend";
        HTTPHelper.post(url, requestObject, new HTTPCallBack() {
            @Override
            public void getSuccess(JSONObject returnObject, String msg) {
                HTTPCallBack.super.getSuccess(returnObject, msg);
                if(getActivity() == null){
                    return;
                }
                getActivity().runOnUiThread(()->{
                    Utils.toastMsg(requireContext(), "request success");
                    dismiss();
                });
            }

            @Override
            public void getNotSuccess(JSONObject returnObject, String msg) {
                HTTPCallBack.super.getNotSuccess(returnObject, msg);
                if(getActivity() == null){
                    return;
                }
                getActivity().runOnUiThread(()->{
                    Utils.toastMsg(requireContext(), msg);
                });
            }
        });
    }
}
