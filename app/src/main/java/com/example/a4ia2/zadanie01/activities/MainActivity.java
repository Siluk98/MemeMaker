package com.example.a4ia2.zadanie01.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.example.a4ia2.zadanie01.R;
import com.example.a4ia2.zadanie01.helpers.GetJson;
import com.example.a4ia2.zadanie01.helpers.ImageData;
import com.example.a4ia2.zadanie01.helpers.LoadImageTask;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private LinearLayout camera;
    private LinearLayout folders;
    private LinearLayout collage;
    private LinearLayout net;
    private LinearLayout notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        camera = (LinearLayout)findViewById(R.id.R1C0);
        folders = (LinearLayout)findViewById(R.id.R1C1);
        collage = (LinearLayout)findViewById(R.id.R2C0);
        net = (LinearLayout)findViewById(R.id.R2C1);
        notes = (LinearLayout)findViewById(R.id.R3C0);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                startActivity(intent);
            }
        });

        folders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i_Folders = new Intent(MainActivity.this, FoldersActivity.class);
                startActivity(i_Folders);
            }
        });

        collage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        net.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i_net = new Intent(MainActivity.this, NetworkActivity.class);
                startActivity(i_net);
            }
        });

        notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i_notes = new Intent(MainActivity.this, NotesActivity.class);
                startActivity(i_notes);
            }
        });



    }
}
