
package com.example.a4ia2.zadanie01.helpers;
/*
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.widget.ImageView;


public class Miniatura extends ImageView {
    private Bitmap bitmap;
    private int sx;
    private int sy;
    private int x;
    private int y;
    private String name;
    private int lp;
    private static int counter=0;

    public Miniatura(Context context) {
        super(context);
    }

    public Miniatura(Context context, Bitmap bitmap, int x, int y)
    {
        super(context);
        this.bitmap = Bitmap.createScaledBitmap(bitmap , x, y, false);
        this.setImageBitmap(this.bitmap);
        this.setMaxWidth(x);
        this.setMaxHeight(y);
        this.sx = x;
        this.sy = y;
        this.lp=counter;
        counter++;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        paint.setColor(Color.WHITE); // lub paint.setColor(Color.RED);
        canvas.drawRect(x,y,x,y,paint);
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void setSx(int sx) {
        this.sx = sx;
    }

    public void setSy(int sy) {
        this.sy = sy;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public int getSx() {
        return sx;
    }

    public int getSy() {
        return sy;
    }

    public int getx() {
        return x;
    }

    public int gety() {
        return y;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public String getName() {
        return name;
    }
}
*/



import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


public class Miniatura extends ImageView implements View.OnClickListener, View.OnLongClickListener, View.OnTouchListener {
    private Bitmap bitmap;
    private Rect r;
    private Paint paint;

    public Miniatura(Context context) {
        super(context);
    }

    public Miniatura(Context context, Bitmap bitmap, int x, int y)
    {
        super(context);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        paint.setColor(Color.RED);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(x, y);
        this.setLayoutParams(lp);
        this.setImageBitmap(bitmap);
        this.setBackgroundColor(0xffff0000);
        this.setScaleType(ScaleType.CENTER_CROP);
        this.bitmap = bitmap;
        this.r = new Rect();
        r.set(0,0,x,y);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(this.r,this.paint);
    }

    @Override
    public void onClick(View v) {

    }


    @Override
    public boolean onLongClick(View v) {
        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        return false;
    }
}


