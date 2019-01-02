package com.example.a4ia2.zadanie01.helpers;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Admin on 16.12.2017.
 */

public class GetJson extends AsyncTask {
    private JSONArray allImagesJson = null;
    public ArrayList<ImageData> lista = new ArrayList<ImageData>();

    @Override
    protected Object doInBackground(Object[] objects) {

        try {
        HttpPost httpPost = new HttpPost("http://czasami4.cba.pl/subpage/getFiles.php");
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpResponse httpResponse = null;

            httpResponse = httpClient.execute(httpPost);

        String jsonString = EntityUtils.toString(httpResponse.getEntity(), HTTP.UTF_8);

        //jesli jsonString nie jest pusty wtedy parsujemy go na obiekt JSON
            JSONObject jsonObj = null;
            if(!jsonString.isEmpty()) {
                 jsonObj = new JSONObject(jsonString);
            }
            else
            {
                throw new Exception("No JSON in here");
            }

        //a potem rozbijamy na tablicę obiektów
        allImagesJson = jsonObj.getJSONArray("ImagesList");

        //teraz mogę pobierać dane for-em z elementów tej tablicy

        for (int i = 0; i < allImagesJson.length(); i++) {

            // obiekty po kolei
            JSONObject image = allImagesJson.getJSONObject(i);
            Log.e("image", image.toString());
            // poszczególne pola
            String imageName = image.getString("imageName");
            String imageSaveTime = image.getString("imageSaveTime");

            //tutaj dodaj do ArrayList-y obiekt klasy ImageData

            lista.add(new ImageData(imageName, imageSaveTime));

        }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }
}
