package com.example.a4ia2.zadanie01.helpers;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Path;
import android.os.AsyncTask;
import android.util.Log;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 4ia2 on 2017-12-04.
 */
public class UploadFoto extends AsyncTask<String,Void,String> {

    private Context context;
    private ProgressDialog pDialog;
    private String filename;
    private boolean scale;

    public UploadFoto(Context context, String filename, boolean scale) {
        this.context = context;
        this.scale = scale;
        this.filename = filename;
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Sending");
        pDialog.setCancelable(false); // nie da się zamknąć klikając w ekran
    }

    @Override
    protected String doInBackground(String... params) {
        RandomAccessFile f = null;
        byte[] bytes;
        try {
            File file = new File(filename);
            f = new RandomAccessFile(file, "r");
             bytes = new byte[(int)f.length()];
            f.readFully(bytes);

            if(scale) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                Bitmap smallBmp = Bitmap.createScaledBitmap(bitmap, 100, 100, false);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                smallBmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                bytes = stream.toByteArray();
            }

            /*
            HttpPost httpPost = new HttpPost(Settings.srv_url); // URL_SERWERA proponuję zapisać w osobnej klasie np Settings w postaci stałej
            httpPost.setEntity(new ByteArrayEntity(bytes)); // bytes - nasze zdjęcie przekonwertowane na byte[]
            DefaultHttpClient httpClient = new DefaultHttpClient(); // klient http
            HttpResponse httpResponse = null; // obiekt odpowiedzi z serwera
            httpResponse = httpClient.execute(httpPost); // wykonanie wysłania
            String result = EntityUtils.toString(httpResponse.getEntity(), HTTP.UTF_8); // odebranie odpowiedzi z serwera, którą potem wyświetlimy w onPostExecute
            */
        } catch (Exception e) {
            e.printStackTrace();
        }


        SimpleDateFormat dFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String d = dFormat.format(new Date());
        RequestBody requestBody = new MultipartBuilder().type(MultipartBuilder.FORM)
                .addFormDataPart("fileToUpload", "zdjecie"+d+".png", RequestBody.create(MediaType.parse("image/png"), new File(filename)))
                .build();
        com.squareup.okhttp.Request request = new com.squareup.okhttp.Request.Builder()
                .url("http://czasami4.cba.pl/subpage/upload_script.php")
                .post(requestBody)
                .build();

        OkHttpClient okhttpClient = new OkHttpClient();
        try {
            Response response = okhttpClient.newCall(request).execute();
            Log.e("resp", response.message());
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        pDialog.dismiss();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog.show();
    }
}
