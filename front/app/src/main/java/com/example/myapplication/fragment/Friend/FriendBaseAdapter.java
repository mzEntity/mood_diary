package com.example.myapplication.fragment.Friend;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.R;
import com.example.myapplication.fragment.Friend.response.FriendRequestFragment;
import com.example.myapplication.fragment.ownSpace.UserSpaceFragment;

import java.util.List;

public class FriendBaseAdapter extends BaseAdapter {

    private List<FriendItem> itemList;
    private LayoutInflater layoutInflater;
    private Context context;

    private FragmentManager fragmentManager;

    public FriendBaseAdapter(FragmentManager fragmentManager, Context context, List<FriendItem> itemList) {
        this.fragmentManager = fragmentManager;
        this.itemList = itemList;
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getCount() {
        return this.itemList.size();
    }

    @Override
    public Object getItem(int i) {
        return this.itemList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.friend_list_item, null);

            viewHolder.avatarView = (ImageView) convertView.findViewById(R.id.friend_list_item_avatar);
            viewHolder.nameView = (TextView) convertView.findViewById(R.id.friend_list_item_name);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        FriendItem bean = itemList.get(position);
        viewHolder.nameView.setText(bean.getName());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserSpaceFragment userSpaceFragment = new UserSpaceFragment();
                Bundle args = new Bundle();
                args.putInt("friendId", bean.getUserId());
                args.putString("friendName", bean.getName());
                userSpaceFragment.setArguments(args);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().replace(R.id.fragment_container, userSpaceFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return convertView;
    }

    private class ViewHolder{

        public ImageView avatarView;
        public TextView nameView;
    }
}
