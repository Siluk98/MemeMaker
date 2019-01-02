package com.example.a4ia2.zadanie01.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.a4ia2.zadanie01.helpers.GetJson;
import com.example.a4ia2.zadanie01.helpers.ImageData;
import com.example.a4ia2.zadanie01.helpers.LoadImageTask;

import com.example.a4ia2.zadanie01.R;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class NetworkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network);


        ArrayList<ImageData> lista;
        GetJson JSON = new GetJson();
        try {
            JSON.execute().get();
            Log.e("Lisata", JSON.lista.toString());
            lista = JSON.lista;

            for (int i = 0; i < lista.size(); i++) {
                //wywoływanie pobierania
                Log.e("NAME", lista.get(i).getImageName());
                //Drawable aaa = LoadImageTask.LoadImageFromWeb("http://czasami4.cba.pl/subpage/uploads/"+lista.get(i).getImageName());
                new LoadImageTask(NetworkActivity.this, null).execute("http://czasami4.cba.pl/subpage/uploads/"+lista.get(i).getImageName());

            /*
            Intent intent = new Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("adres obrazka na serwerze"));
            startActivity(intent);*/
            }

        } catch (Exception e) {
            e.printStackTrace();
        }



    }




}
