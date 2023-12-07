package com.example.myapplication.fragment.Friend.response;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;

import java.util.List;

public class FriendRequestBaseAdapter extends BaseAdapter {

    private List<FriendRequestItem> itemList;
    private LayoutInflater layoutInflater;
    private Context context;

    public FriendRequestBaseAdapter(Context context, List<FriendRequestItem> itemList) {
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
            convertView = layoutInflater.inflate(R.layout.friend_request_list_item, null);

            viewHolder.avatarView = convertView.findViewById(R.id.friend_request_list_item_avatar);
            viewHolder.nameView =  convertView.findViewById(R.id.friend_request_list_item_name);
            viewHolder.contentView = convertView.findViewById(R.id.friend_request_list_item_content);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        FriendRequestItem bean = itemList.get(position);
        viewHolder.nameView.setText(bean.getFriendName());
        viewHolder.contentView.setText(bean.getValidation());

        return convertView;
    }

    private class ViewHolder{

        public ImageView avatarView;
        public TextView nameView;
        public TextView contentView;
    }
}
