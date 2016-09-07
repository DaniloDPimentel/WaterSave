package com.example.les.watersave.models;

import android.content.Context;
import android.content.res.Resources;

/**
 * Created by Nicolas on 07/09/2016.
 */
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


            final int categoryImageResource = mResources.getIdentifier(
                    getImage(), DRAWABLE, mPackageName);
            return  categoryImageResource;
        }

    public String getImage(){
        return this.Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
