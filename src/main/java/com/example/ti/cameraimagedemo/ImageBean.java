package com.example.ti.cameraimagedemo;

import android.net.Uri;

/**
 * Created by samyak.shah on 6/20/2016.
 */
public class ImageBean {

    public String getImageName() {
        return imageName;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public String getImageLength() {
        return imageLength;
    }

    private String imageName;
    private Uri imageUri;
    private String imageLength;

    public ImageBean(String imageName, Uri imageUri, String imageLength) {
        this.imageName = imageName;
        this.imageUri = imageUri;
        this.imageLength = imageLength;
    }
}
