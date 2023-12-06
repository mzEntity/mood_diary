package com.example.myapplication.fragment.space;

// SpaceFragment.java
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.myapplication.R;
import com.example.myapplication.common.HTTPCallBack;
import com.example.myapplication.common.HTTPHelper;
import com.example.myapplication.common.Utils;
import com.example.myapplication.fragment.space.issue.IssueDialogFragment;
import com.example.myapplication.main.SessionManager;
import com.example.myapplication.main.UserInfoItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SpaceFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.space_fragment_layout, container, false);
        SessionManager sessionManager = new SessionManager(requireContext());
        if(!sessionManager.isLoggedIn()){
            sessionManager.redirectToLogin(getParentFragmentManager());
        }
        UserInfoItem user = sessionManager.getUserDetails();

        int userId = user.getId();
        getDiary(userId, view);

        ImageButton btnAdd = view.findViewById(R.id.space_add_button);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IssueDialogFragment issueDialogFragment = new IssueDialogFragment();
                issueDialogFragment.show(getParentFragmentManager(), "edit_dialog");
            }
        });

        return view;
    }

    void getDiary(int userId, View view){
        String url = "/getUserDiary";
        HashMap<String, String> params = new HashMap<>();
        params.put("userId", "" + userId);
        HTTPHelper.get(url, params, new HTTPCallBack() {
            @Override
            public void getSuccess(JSONObject returnObject, String msg) {
                HTTPCallBack.super.getSuccess(returnObject, msg);
                getActivity().runOnUiThread(()->{
                    try {
                        JSONObject responseObject = returnObject.getJSONObject("object");
                        int userId = responseObject.getInt("userId");
                        String authorName = responseObject.getString("name");
                        JSONArray array = responseObject.getJSONArray("allDiaries");
                        int length = array.length();
                        List<SpaceItem> spaceItemList = new ArrayList<>();
                        for(int i = 0; i < length; i++){
                            JSONObject diary = array.getJSONObject(i);
                            int id = diary.getInt("id");
                            int authorId = diary.getInt("authorId");
                            int moodTypeId = diary.getInt("moodTypeId");
                            String title = diary.getString("title");
                            String content = diary.getString("content");
                            String updatedAt = diary.getString("updatedAt");
                            spaceItemList.add(new SpaceItem(authorName, updatedAt, title, content));
                        }
                        ListView lv = view.findViewById(R.id.space_list);
                        SpaceBaseAdapter adapter = new SpaceBaseAdapter(view.getContext(), spaceItemList);
                        lv.setAdapter(adapter);
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
