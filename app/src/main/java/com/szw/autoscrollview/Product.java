package com.szw.autoscrollview;

import android.graphics.Bitmap;

/**
 * Created by shenmegui on 2017/7/6.
 */
public class Product {
    private int id;
    private Bitmap bitmap;
    private String name;

    public Product(Bitmap bitmap, int id) {
        this.bitmap = bitmap;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
