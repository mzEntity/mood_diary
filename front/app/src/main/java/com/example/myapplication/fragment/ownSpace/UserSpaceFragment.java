package com.example.myapplication.fragment.ownSpace;

// UserSpaceFragment.java
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.common.HTTPCallBack;
import com.example.myapplication.common.HTTPHelper;
import com.example.myapplication.common.Utils;
import com.example.myapplication.fragment.Friend.FriendBaseAdapter;
import com.example.myapplication.fragment.Friend.FriendItem;
import com.example.myapplication.fragment.space.SpaceBaseAdapter;
import com.example.myapplication.fragment.space.SpaceItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserSpaceFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_space_fragment_layout, container, false);

        Bundle args = getArguments();
        String name = "Default";
        int id = -1;
        if (args != null) {
            name = args.getString("friendName");
            id = args.getInt("friendId");
        }

        getDiary(id, view);

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
                if(getActivity() == null){
                    return;
                }
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
                            String avatar = diary.getString("avatar");
                            String updatedAt = diary.getString("updatedAt");
                            spaceItemList.add(new SpaceItem(id, authorName, updatedAt, title, content, avatar, moodTypeId));
                        }
                        ListView lv = view.findViewById(R.id.user_space_list);
                        SpaceBaseAdapter adapter = new SpaceBaseAdapter(requireActivity().getSupportFragmentManager(),
                                view.getContext(), spaceItemList);
                        lv.setAdapter(adapter);
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
}
