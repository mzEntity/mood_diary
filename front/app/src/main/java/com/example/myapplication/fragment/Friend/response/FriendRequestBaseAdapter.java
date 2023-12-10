package com.example.myapplication.fragment.Friend.response;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.common.Config;
import com.example.myapplication.common.HTTPCallBack;
import com.example.myapplication.common.HTTPHelper;
import com.example.myapplication.common.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class FriendRequestBaseAdapter extends BaseAdapter {

    private List<FriendRequestItem> itemList;
    private LayoutInflater layoutInflater;
    private Context context;

    private Activity activity;

    public FriendRequestBaseAdapter(Activity activity, Context context, List<FriendRequestItem> itemList) {
        this.itemList = itemList;
        this.layoutInflater = LayoutInflater.from(context);
        this.activity = activity;
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
            viewHolder.dateView = convertView.findViewById(R.id.friend_request_list_item_date);
            viewHolder.contentView = convertView.findViewById(R.id.friend_request_list_item_content);
            viewHolder.permitBtn = convertView.findViewById(R.id.friend_request_list_item_permit_button);
            viewHolder.rejectBtn = convertView.findViewById(R.id.friend_request_list_item_reject_button);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        FriendRequestItem bean = itemList.get(position);
        viewHolder.nameView.setText(bean.getSenderName());
        viewHolder.dateView.setText(bean.getUpdateTime());
        viewHolder.contentView.setText(bean.getValidation());


        viewHolder.permitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int typeId = Config.requestMapIS.get("已通过");
                responseToRequest(bean.getId(), typeId);
            }
        });

        viewHolder.rejectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int typeId = Config.requestMapIS.get("已拒绝");
                responseToRequest(bean.getId(), typeId);
            }
        });

        return convertView;
    }

    public void responseToRequest(int requestId, int typeId){
        JSONObject requestObject = new JSONObject();
        try{
            requestObject.put("request_id", requestId);
            requestObject.put("type_id", typeId);
        } catch (JSONException e){
            Utils.toastMsg(context, "Error in sending request");
        }
        String url = "/respondFriend";
        HTTPHelper.post(url, requestObject, new HTTPCallBack() {
            @Override
            public void getSuccess(JSONObject returnObject, String msg) {
                HTTPCallBack.super.getSuccess(returnObject, msg);
                activity.runOnUiThread(()->{
                    Utils.toastMsg(context, msg);
                });
            }

            @Override
            public void getNotSuccess(JSONObject returnObject, String msg) {
                HTTPCallBack.super.getNotSuccess(returnObject, msg);
                activity.runOnUiThread(()->{
                    Utils.toastMsg(context, msg);
                });
            }
        });
    }

    private class ViewHolder{

        public ImageView avatarView;
        public TextView nameView;
        public TextView contentView;

        public TextView dateView;

        public ImageButton permitBtn;

        public ImageButton rejectBtn;
    }
}
