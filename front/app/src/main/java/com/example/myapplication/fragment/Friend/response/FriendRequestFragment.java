package com.example.myapplication.fragment.Friend.response;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.common.Config;
import com.example.myapplication.common.HTTPCallBack;
import com.example.myapplication.common.HTTPHelper;
import com.example.myapplication.common.Utils;
import com.example.myapplication.fragment.Friend.FriendBaseAdapter;
import com.example.myapplication.fragment.Friend.FriendItem;
import com.example.myapplication.main.SessionManager;
import com.example.myapplication.main.UserInfoItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FriendRequestFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.friend_request_fragment_layout, container, false);

        SessionManager sessionManager = new SessionManager(requireContext());
        if(!sessionManager.isLoggedIn()){
            sessionManager.redirectToLogin(getParentFragmentManager());
            return view;
        }
        UserInfoItem user = sessionManager.getUserDetails();
        getRequestList(user.getId(), view);
        return view;
    }

    void getRequestList(int userId, View view){
        String url = "/getAllRequestsReceived";
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
                        JSONArray array = responseObject.getJSONArray("allRequests");
                        int length = array.length();
                        List<FriendRequestItem> requestItemList = new ArrayList<>();
                        for(int i = 0; i < length; i++){
                            JSONObject request = array.getJSONObject(i);
                            int id = request.getInt("requestId");
                            int senderId = request.getInt("senderId");
                            int receiverId = request.getInt("receiverId");
                            String senderName = request.getString("senderName");
                            String validation = request.getString("validation");
                            String updateTime = request.getString("updateTime");
                            requestItemList.add(new FriendRequestItem(id, senderId, receiverId, senderName, validation, updateTime));
                        }

                        ListView lv = view.findViewById(R.id.friend_request_list);
                        FriendRequestBaseAdapter adapter = new FriendRequestBaseAdapter(requireActivity(), view.getContext(), requestItemList);

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
