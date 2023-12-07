package com.example.myapplication.fragment.ownSpace;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.common.HTTPCallBack;
import com.example.myapplication.common.HTTPHelper;
import com.example.myapplication.common.Utils;
import com.example.myapplication.fragment.space.SpaceBaseAdapter;
import com.example.myapplication.fragment.space.SpaceItem;
import com.example.myapplication.fragment.space.issue.IssueDialogFragment;
import com.example.myapplication.main.SessionManager;
import com.example.myapplication.main.UserInfoItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DiaryDetailFragment extends Fragment {
    private DiaryDetailFragment.DiaryDetail diaryDetail;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.diary_detail_fragment_layout, container, false);
        SessionManager sessionManager = new SessionManager(requireContext());
        if(!sessionManager.isLoggedIn()){
            sessionManager.redirectToLogin(getParentFragmentManager());
        }

        Bundle args = getArguments();
        int diaryId = -1;
        if (args != null) {
            diaryId = args.getInt("diaryId");
        }
        getDiaryDetail(diaryId, view);

        return view;
    }

    void getDiaryDetail(int diaryId, View view){
        String url = "/getDiaryDetail";
        HashMap<String, String> params = new HashMap<>();
        params.put("diaryId", "" + diaryId);
        HTTPHelper.get(url, params, new HTTPCallBack() {
            @Override
            public void getSuccess(JSONObject returnObject, String msg) {
                HTTPCallBack.super.getSuccess(returnObject, msg);
                getActivity().runOnUiThread(()->{
                    try {
                        JSONObject responseObject = returnObject.getJSONObject("object");
                        int diaryId = responseObject.getInt("diaryId");
                        int authorId = responseObject.getInt("authorId");
                        String authorName = responseObject.getString("authorName");
                        String issueDate = responseObject.getString("issueDate");
                        String title = responseObject.getString("title");
                        String content = responseObject.getString("content");

                        DiaryDetailFragment.this.diaryDetail = new DiaryDetail(
                                diaryId, authorId, authorName,
                                issueDate, title, content
                        );

                        setView(view, DiaryDetailFragment.this.diaryDetail);
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

    private void setView(View view, DiaryDetail diaryDetail){
        TextView authorView = view.findViewById(R.id.diary_detail_author);
        TextView dateView = view.findViewById(R.id.diary_detail_date);
        TextView titleView = view.findViewById(R.id.diary_detail_title);
        TextView contentView = view.findViewById(R.id.diary_detail_content);

        authorView.setText(diaryDetail.authorName);
        dateView.setText(diaryDetail.issueDate);
        titleView.setText(diaryDetail.title);
        contentView.setText(diaryDetail.content);
    }

    private class DiaryDetail {
        public int diaryId;
        public int authorId;
        public String authorName;
        public String issueDate;
        public String title;
        public String content;

        public DiaryDetail(int diaryId, int authorId, String authorName, String issueDate, String title, String content) {
            this.diaryId = diaryId;
            this.authorId = authorId;
            this.authorName = authorName;
            this.issueDate = issueDate;
            this.title = title;
            this.content = content;
        }
    }
}
