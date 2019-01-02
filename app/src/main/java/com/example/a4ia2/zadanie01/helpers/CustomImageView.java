package com.example.a4ia2.zadanie01.helpers;

import android.support.v7.widget.AppCompatImageView;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.a4ia2.zadanie01.R;

public class CustomImageView extends ImageView implements View.OnClickListener, View.OnLongClickListener {


    // konstruktor klasy CustomImageView
    public CustomImageView(Context context) {
        super(context);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(200,200);
        lp.setMargins(10,0,10,10);
        this.setLayoutParams( lp);
        this.setImageResource(R.mipmap.ic_launcher);
        this.setBackgroundColor(0xff0000ff);
        this.setScaleType(ScaleType.CENTER_CROP);

        /*
        this.setMaxWidth(60);
        this.setMinimumWidth(60);
        this.setMinimumHeight(60);
        this.setMaxHeight(60);
        */

        setOnClickListener(this);
        setOnLongClickListener(this);
    }
    // zaimplementowana metoda z interfejsu OnClickListener
    @Override
    public void onClick(View v) {
        this.setBackgroundColor(0xffffff00);
    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }
}
