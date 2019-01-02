package com.example.a4ia2.zadanie01.helpers;

/**
 * Created by Admin on 16.12.2017.
 */

public class ImageData {
    private String imageName;

    public String getImageName() {
        return imageName;
    }

    public String getImageSaveTime() {
        return imageSaveTime;
    }

    private String imageSaveTime;

    public ImageData(String imageName, String imageSaveTime) {
        this.imageName = imageName;
        this.imageSaveTime = imageSaveTime;
    }
}
