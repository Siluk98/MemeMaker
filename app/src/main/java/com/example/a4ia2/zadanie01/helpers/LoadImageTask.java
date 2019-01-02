package com.example.a4ia2.zadanie01.helpers;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.RecoverySystem;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by Admin on 16.12.2017.
 */

public class LoadImageTask extends AsyncTask{
    private Drawable loadedImage;
    private ProgressDialog pDialog;

    static public Drawable LoadImageFromWeb(String url) {

        InputStream inputStream = null;
        try {
            inputStream = (InputStream) new URL(url).getContent();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Drawable.createFromStream(inputStream, "src name");
    }

    public LoadImageTask(Context context, Drawable loadedImage) {
        this.loadedImage = loadedImage;
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading");
        pDialog.setCancelable(false); // nie da się zamknąć klikając w ekran
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        Log.e("obj", objects[0].toString());
        loadedImage = LoadImageFromWeb(objects[0].toString());
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog.show();
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        pDialog.dismiss();
        //iv.setImageDrawable(loadedImage);
    }
}
