package com.example.myapplication.fragment.space;

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
import com.example.myapplication.fragment.ownSpace.DiaryDetailFragment;
import com.example.myapplication.fragment.ownSpace.UserSpaceFragment;

import java.util.List;

public class SpaceBaseAdapter extends BaseAdapter {
    private List<SpaceItem> itemList;
    private LayoutInflater layoutInflater;
    private Context context;

    private FragmentManager fragmentManager;
    public SpaceBaseAdapter(FragmentManager fragmentManager, Context context, List<SpaceItem> itemList) {
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
            convertView = layoutInflater.inflate(R.layout.space_list_item, null);

            viewHolder.avatarView = convertView.findViewById(R.id.space_list_item_avatar);
            viewHolder.authorView = convertView.findViewById(R.id.space_list_item_author);
            viewHolder.dateView = convertView.findViewById(R.id.space_list_item_date);
            viewHolder.titleView = convertView.findViewById(R.id.space_list_item_title);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        SpaceItem bean = itemList.get(position);

        viewHolder.authorView.setText(bean.getAuthor());
        viewHolder.dateView.setText(bean.getDate());
        viewHolder.titleView.setText(bean.getTitle());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DiaryDetailFragment diaryDetailFragment = new DiaryDetailFragment();
                Bundle args = new Bundle();
                args.putInt("diaryId", bean.getDiaryId());
                diaryDetailFragment.setArguments(args);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().replace(R.id.fragment_container, diaryDetailFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return convertView;
    }


    private class ViewHolder{

        public ImageView avatarView;
        public TextView authorView;
        public TextView dateView;
        public TextView titleView;
    }
}