package com.example.myapplication.fragment.ownSpace;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.common.Config;
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

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DiaryDetailFragment extends Fragment {
    private DiaryDetailFragment.DiaryDetail diaryDetail;
    private String moodPrefix;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SessionManager sessionManager = new SessionManager(requireContext());
        if(!sessionManager.isLoggedIn()){
            sessionManager.redirectToLogin(getParentFragmentManager());
            return inflater.inflate(R.layout.diary_detail_fragment_layout, container, false);
        }

        Bundle args = getArguments();
        int diaryId = -1;
        int moodTypeId = -1;
        if (args != null) {
            diaryId = args.getInt("diaryId");
            moodTypeId = args.getInt("moodTypeId");
        }
        this.moodPrefix = Config.moodMap.get(moodTypeId).getEnglish();
        String layoutName = this.moodPrefix + "_diary_fragment_layout";
        int layoutId = getResources().getIdentifier(layoutName, "layout", getContext().getPackageName());
        View view = inflater.inflate(layoutId, container, false);
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
                if(getActivity() == null){
                    return;
                }
                getActivity().runOnUiThread(()->{
                    try {
                        JSONObject responseObject = returnObject.getJSONObject("object");
                        int diaryId = responseObject.getInt("diaryId");
                        int authorId = responseObject.getInt("authorId");
                        String authorName = responseObject.getString("authorName");
                        String issueDate = responseObject.getString("issueDate");
                        String title = responseObject.getString("title");
                        String content = responseObject.getString("content");
                        String avatar = responseObject.getString("avatar");
                        int moodTypeId = responseObject.getInt("moodTypeId");

                        DiaryDetailFragment.this.diaryDetail = new DiaryDetail(
                                diaryId, authorId, authorName,
                                issueDate, title, content, avatar, moodTypeId
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
                if(getActivity() == null){
                    return;
                }
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
        ImageView imageView = view.findViewById(R.id.diary_detail_image);

        authorView.setText(diaryDetail.authorName);
        dateView.setText(diaryDetail.issueDate);
        titleView.setText(diaryDetail.title);
        contentView.setText(diaryDetail.content);

        String avatar = diaryDetail.avatar;

        if(avatar == null){
            return;
        }

        new LoadImageTask(imageView).execute(Config.httpBasePath + "/" + avatar);
    }

    private static class LoadImageTask extends AsyncTask<String, Void, Bitmap> {
        private final WeakReference<ImageView> imageViewRef;

        public LoadImageTask(ImageView imageView) {
            imageViewRef = new WeakReference<>(imageView);
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            String imageUrl = strings[0];
            Bitmap bitmap = null;

            try {
                URL url = new URL(imageUrl);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setRequestMethod("GET");
                // 配置网络超时时间为 10秒
                urlConnection.setConnectTimeout(10000);

                // 获取 Http 响应码
                int responseCode = urlConnection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = urlConnection.getInputStream();
                    bitmap = BitmapFactory.decodeStream(inputStream);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            ImageView imageView = imageViewRef.get();
            if (imageView != null && bitmap != null) {
                imageView.setImageBitmap(bitmap);
            }
        }
    }

    private class DiaryDetail {
        public int diaryId;
        public int authorId;
        public String authorName;
        public String issueDate;
        public String title;
        public String content;
        public String avatar;
        public int moodTypeId;

        public DiaryDetail(int diaryId, int authorId, String authorName, String issueDate, String title, String content, String avatar, int moodTypeId) {
            this.diaryId = diaryId;
            this.authorId = authorId;
            this.authorName = authorName;
            this.issueDate = issueDate;
            this.title = title;
            this.content = content;
            this.avatar = avatar;
            this.moodTypeId = moodTypeId;
        }
    }


}
