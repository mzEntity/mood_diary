package com.example.myapplication.fragment.Friend;

// FriendFragment.java
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.myapplication.R;
import com.example.myapplication.common.HTTPCallBack;
import com.example.myapplication.common.HTTPHelper;
import com.example.myapplication.common.Utils;
import com.example.myapplication.fragment.Friend.request.AddFriendDialogFragment;
import com.example.myapplication.fragment.Friend.response.FriendRequestFragment;
import com.example.myapplication.fragment.space.SpaceBaseAdapter;
import com.example.myapplication.fragment.space.SpaceItem;
import com.example.myapplication.main.SessionManager;
import com.example.myapplication.main.UserInfoItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FriendFragment extends Fragment {
    private ListView listView;
    private int userId;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.friend_fragment_layout, container, false);
        SessionManager sessionManager = new SessionManager(requireContext());
        if(!sessionManager.isLoggedIn()){
            sessionManager.redirectToLogin(getParentFragmentManager());
            return view;
        }
        UserInfoItem user = sessionManager.getUserDetails();

        listView = view.findViewById(R.id.friend_list);
        userId = user.getId();

        getFriendList(userId, listView);

        ImageButton btnAdd = view.findViewById(R.id.friend_add_button);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddFriendDialogFragment addFriendDialogFragment = new AddFriendDialogFragment();
                addFriendDialogFragment.show(getParentFragmentManager(), "friend_dialog");
            }
        });

        ImageButton btnShowRequest = view.findViewById(R.id.request_list_button);

        btnShowRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FriendRequestFragment friendRequestFragment = new FriendRequestFragment();
                FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, friendRequestFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getFriendList(userId, listView);
    }

    void getFriendList(int userId, ListView lv){
        String url = "/getAllFriends";
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
                        JSONArray array = returnObject.getJSONArray("object");
                        int length = array.length();
                        List<FriendItem> friendItemList = new ArrayList<>();
                        for(int i = 0; i < length; i++){
                            JSONObject friend = array.getJSONObject(i);
                            int id = friend.getInt("id");
                            String name = friend.getString("name");
                            friendItemList.add(new FriendItem(id, name));
                        }
                        FriendBaseAdapter adapter = new FriendBaseAdapter(requireActivity().getSupportFragmentManager(),
                                getContext(), friendItemList);
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

