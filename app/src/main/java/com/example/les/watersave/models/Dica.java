package com.example.les.watersave.models;

import android.content.Context;
import android.content.res.Resources;

public class Dica {
    private String text;
    private String Image;

    public Dica(String image, String text) {
        Image = image;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getImageResourse(Context activity) {

            final String DRAWABLE = "drawable";
            final String ICON_PRELETOR = "icon_preletor_";

            Resources mResources = activity.getResources();
            String mPackageName = activity.getPackageName();
            return mResources.getIdentifier(getImage(), DRAWABLE, mPackageName);
        }

    public String getImage(){
        return this.Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
