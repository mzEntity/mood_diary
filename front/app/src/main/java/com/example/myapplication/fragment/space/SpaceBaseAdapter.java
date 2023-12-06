package com.example.myapplication.fragment.space;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;

import java.util.List;

public class SpaceBaseAdapter extends BaseAdapter {
    private List<SpaceItem> itemList;
    private LayoutInflater layoutInflater;
    private Context context;

    public SpaceBaseAdapter(Context context, List<SpaceItem> itemList) {
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
            convertView = layoutInflater.inflate(R.layout.space_list_item, null);

            viewHolder.avatarView = convertView.findViewById(R.id.space_list_item_avatar);
            viewHolder.authorView = convertView.findViewById(R.id.space_list_item_author);
            viewHolder.dateView = convertView.findViewById(R.id.space_list_item_date);
            viewHolder.contentView = convertView.findViewById(R.id.space_list_item_content);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        SpaceItem bean = itemList.get(position);

        viewHolder.authorView.setText(bean.getAuthor());
        viewHolder.dateView.setText(bean.getDate());
        viewHolder.contentView.setText(bean.getContent());

        return convertView;
    }


    private class ViewHolder{

        public ImageView avatarView;
        public TextView authorView;
        public TextView dateView;
        public TextView contentView;
    }
}