package com.example.myapplication.fragment.space.issue;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.example.myapplication.R;
import com.example.myapplication.common.Config;
import com.example.myapplication.common.HTTPCallBack;
import com.example.myapplication.common.HTTPHelper;
import com.example.myapplication.common.Utils;
import com.example.myapplication.main.SessionManager;
import com.example.myapplication.main.UserInfoItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class IssueDialogFragment extends DialogFragment {
    private ImageView imageView;

    private EditText etTitle;
    private EditText etContent;

    private Spinner spinner;

    private int userId;

    private ActivityResultLauncher<String> imagePickerLauncher;

    private static HashMap<String, Integer> moodMap = new HashMap<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.issue_daily_dialog, container, false);

        SessionManager sessionManager = new SessionManager(requireContext());
        if(!sessionManager.isLoggedIn()){
            sessionManager.redirectToLogin(getParentFragmentManager());
            return view;
        }

        tryToGetImage();
        UserInfoItem user = sessionManager.getUserDetails();

        userId = user.getId();

        etTitle = view.findViewById(R.id.issue_title_edit);
        etContent = view.findViewById(R.id.issue_content_edit);

        spinner = view.findViewById(R.id.issue_mood_spinner);
        List<String> list = new ArrayList<>();
        for(int moodId: Config.moodMap.keySet()){
            String moodName = Config.moodMap.get(moodId).getName();
            IssueDialogFragment.moodMap.put(moodName, moodId);
            list.add(moodName);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Button btnSubmit = view.findViewById(R.id.issue_submit_button);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etTitle.getText().toString().trim();
                String content = etContent.getText().toString().trim();
                String mood = spinner.getSelectedItem().toString().trim();
                if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(content)) {
                    // 处理发布逻辑
                    Toast.makeText(requireContext(), title + "(" + mood + "): " + content, Toast.LENGTH_SHORT).show();
                    int moodId = IssueDialogFragment.moodMap.get(mood);
                    if(IssueDialogFragment.this.file != null){
                        Utils.toastMsg(requireContext(), "upload file");
                        uploadFile(IssueDialogFragment.this.file);
                    }
                    else {
                        Utils.toastMsg(requireContext(), "issue directly");
                        commitDiary(userId, moodId, title, content, null);
                    }
                } else {
                    Toast.makeText(requireContext(), "Please enter an issue", Toast.LENGTH_SHORT).show();
                }
            }
        });

        imageView = view.findViewById(R.id.issue_image_preview);
        imagePickerLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                Log.i("IssueDialogFragment", "" + result);
                String filePath = getRealPathFromUri(result);

                File file = new File(filePath);
                IssueDialogFragment.this.file = file;
                displayImage(filePath);
            }
        });

        Button pickImageButton = view.findViewById(R.id.issue_choose_image);
        pickImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imagePickerLauncher.launch("image/*");
            }
        });

        return view;
    }

    private void displayImage(String filePath){
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
        imageView.setImageBitmap(bitmap);
    }

    private File file;
    private String getRealPathFromUri(Uri uri) {
        String filePath = "";
        String scheme = uri.getScheme();

        if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            String documentId = DocumentsContract.getDocumentId(uri);
            String[] split = documentId.split(":");
            String type = split[0];
            Uri contentUri = null;

            if ("image".equals(type)) {
                contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            }

            String selection = "_id=?";
            String[] selectionArgs = new String[]{split[1]};

            String[] projection = {MediaStore.Images.Media.DATA};
            try (Cursor cursor = getContext().getContentResolver().query(contentUri, projection, selection, selectionArgs, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    filePath = cursor.getString(columnIndex);
                }
            }
        } else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            filePath= uri.getPath();
        }

        return filePath;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }
    @Override
    public void onStart() {
        super.onStart();
        Window win = getDialog().getWindow();
        win.setBackgroundDrawable(new ColorDrawable(Color.WHITE));

        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);

        WindowManager.LayoutParams params = win.getAttributes();
        params.gravity = Gravity.BOTTOM;
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        win.setAttributes(params);
    }

    public void commitDiary(int authorId, int moodTypeId, String title, String content, String avatar){
        JSONObject requestObject = new JSONObject();
        try{
            requestObject.put("author_id", authorId);
            requestObject.put("mood_type_id", moodTypeId);
            requestObject.put("title", title);
            requestObject.put("content", content);
            Log.i("IssueDialogFragment", "" + avatar);
            if(avatar != null){
                requestObject.put("avatar", avatar);
            }
        } catch (JSONException e){
            Utils.toastMsg(requireContext(), "Error in sending request");
        }
        String url = "/commitDiary";
        HTTPHelper.post(url, requestObject, new HTTPCallBack() {
            @Override
            public void getSuccess(JSONObject returnObject, String msg) {
                HTTPCallBack.super.getSuccess(returnObject, msg);
                if(getActivity() == null){
                    return;
                }
                getActivity().runOnUiThread(()->{
                    Utils.toastMsg(requireContext(), "commit success");
                    dismiss();
                });
            }

            @Override
            public void getNotSuccess(JSONObject returnObject, String msg) {
                HTTPCallBack.super.getNotSuccess(returnObject, msg);
                if(getActivity() == null){
                    return;
                }
                getActivity().runOnUiThread(()->{
                    Utils.toastMsg(requireContext(), msg);
                });
            }
        });
    }

    private static final int READ_EXTERNAL_STORAGE_PERMISSION = 2;
    void tryToGetImage(){
        if(ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_EXTERNAL_STORAGE_PERMISSION);
        }
    }

    public void uploadFile(File file){
        HTTPHelper.postFile("/upload", file, new HTTPCallBack() {
            @Override
            public void getSuccess(JSONObject returnObject, String msg) {
                if(getActivity() == null){
                    return;
                }
                getActivity().runOnUiThread(()->{
                    Utils.toastMsg(getContext(), msg);
                    try {
                        JSONObject responseObject = returnObject.getJSONObject("object");
                        String title = etTitle.getText().toString().trim();
                        String content = etContent.getText().toString().trim();
                        String mood = spinner.getSelectedItem().toString().trim();
                        int moodId = IssueDialogFragment.moodMap.get(mood);
                        String avatar = responseObject.getString("avatar");
                        commitDiary(userId, moodId, title, content, avatar);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                });

            }

            @Override
            public void getNotSuccess(JSONObject returnObject, String msg) {
                if(getActivity() == null){
                    return;
                }
                getActivity().runOnUiThread(()->{
                    Utils.toastMsg(getContext(), msg);
                });
            }
        });
    }
}
