package com.example.a4ia2.zadanie01.helpers;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import com.example.a4ia2.zadanie01.activities.CameraActivity;

/**
 * Created by 4ia2 on 2017-11-13.
 */
public class Kolo extends View{
    public Kolo(Context context) {
        super(context);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);
        paint.setColor(Color.argb(150, 255, 255, 255)); // lub paint.setColor(Color.RED);
        canvas.drawCircle(getResources().getSystem().getDisplayMetrics().widthPixels/2, getResources().getSystem().getDisplayMetrics().heightPixels/2, 250, paint);
    }
}
