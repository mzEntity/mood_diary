package com.example.myapplication.fragment.space.issue;

public class ImageItem {
    private long id;
    private String filePath;
    private String displayName;
    private long size;
    private String mimeType;
    private int orientation;

    public ImageItem(long id, String filePath, String displayName, long size, String mimeType, int orientation) {
        this.id = id;
        this.filePath = filePath;
        this.displayName = displayName;
        this.size = size;
        this.mimeType = mimeType;
        this.orientation = orientation;
    }

    public long getId() {
        return id;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getDisplayName() {
        return displayName;
    }

    public long getSize() {
        return size;
    }

    public String getMimeType() {
        return mimeType;
    }

    public int getOrientation() {
        return orientation;
    }

    public String info(){
        return this.id + ": " + this.getDisplayName();
    }
}