package com.example.a4ia2.zadanie01.helpers;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;

/**
 * Created by Admin on 03.12.2017.
 */

public class PreviewText extends View {

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG); // obiekt ustawiający kolor, czcionkę, tło i inne parametry View
    Typeface tf;
    String text = "";
    String innerColor = "#000000";
    String outerColor = "#000000";
    int r = 0;
    int g = 0;
    int b = 0;


    public float width=0;
    public float height=0;

    public void setColors(int r, int g, int b){
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public PreviewText(Context context,Typeface tf) {
        super(context);
        this.tf=tf;
        paint.reset();            // czyszczenie
        paint.setAntiAlias(true);    // wygładzanie
        paint.setTextSize(100.0f);        // wielkość fonta
        paint.setTypeface(tf);  // czcionka
    }

    public void setTypeFace(Typeface tf){
        this.tf=tf;paint.setTypeface(tf);
    }

    public Typeface getTypeFace(){
        return  tf;
    }

    public void setText(String tak){
        text=tak;
    }

    public String getText(){
        return text;
    }

    public void setInnerColor(String color){
        this.innerColor= color;
    }

    public void setOuterColor(String color) {this.outerColor = color;}

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.rgb(r,g,b));
        paint.setTextSize(200.0f);
        canvas.drawText(text, 0, 150, paint);

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        paint.setColor(Color.rgb(r,g,b));
        canvas.drawText(text,0,150,paint);

        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
        width = rect.width();

        height = rect.height();
        Log.d("tak1",height+"");
    }
}
/*
public class PreviewText extends View {
    public PreviewText(Context context) {
        super(context);
    }

    public PreviewText(Context context, Typeface tf) {
        super(context);
        paint.reset();            // czyszczenie
        paint.setAntiAlias(true);    // wygładzanie
        paint.setTextSize(100);        // wielkość fonta
        paint.setTypeface(tf);  // czcionka
        x = 100;
        y = 100;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);
        canvas.drawText("napis", x, y, paint);
    }

    private int x;
    private int y;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG); // obiekt ustawiający kolor, czcionkę, tło i inne parametry View
}
*/