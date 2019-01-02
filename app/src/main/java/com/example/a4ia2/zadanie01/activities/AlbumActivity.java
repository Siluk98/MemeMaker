package com.example.a4ia2.zadanie01.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;

//import com.example.admin.myapplication.R;

import com.example.a4ia2.zadanie01.helpers.CustomImageView;
import com.example.a4ia2.zadanie01.R;
import com.example.a4ia2.zadanie01.helpers.DatabaseManager;

import java.io.File;

public class AlbumActivity extends AppCompatActivity {

    private ScrollView scrollView;
    private LinearLayout linearLayout;
    private LinearLayout[] linearLayouts;
    //private HorizontalScrollView horizontalScrollView;
    private String path;
    private File[] fileArr;
    String color = "#000000";
    String img_path = "";
    EditText et_text;
    EditText et_title;

    private Bitmap betterImageDecode(String filePath) {

        Bitmap myBitmap;
        BitmapFactory.Options options = new BitmapFactory.Options();    //opcje przekształcania bitmapy
        options.inSampleSize = 4; // zmniejszenie jakości bitmapy 4x
        //
        myBitmap = BitmapFactory.decodeFile(filePath, options);
        return myBitmap;
    }

    protected void refresh()
    {
        scrollView = (ScrollView)findViewById(R.id.scroll);
        linearLayout = new LinearLayout(this);
        File folder = new File(path);
        fileArr = folder.listFiles();
        final CustomImageView[] civArr = new CustomImageView[fileArr.length];
        //Bitmap[] bmp = new Bitmap[fileArr.length];

        for(int i=0;i<fileArr.length;i++)
        {
            if(!fileArr[i].isDirectory())
            {
                civArr[i] = new CustomImageView(AlbumActivity.this);
                //bmp[i] = betterImageDecode(fileArr[i].getPath());
                civArr[i].setImageBitmap(betterImageDecode(fileArr[i].getPath()));
                final int l = i;
                civArr[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(AlbumActivity.this, ImageActivity.class);
                        intent.putExtra("path", fileArr[l].getAbsolutePath());
                        startActivity(intent);
                    }
                });
                civArr[i].setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        AlertDialog.Builder alert = new AlertDialog.Builder(AlbumActivity.this);
                        alert.setTitle("New Note");
                        alert.setCancelable(false);
                        View view = View.inflate(AlbumActivity.this, R.layout.notes_edit, null);
                        alert.setView(view);
                        et_title = (EditText)view.findViewById(R.id.title);
                        et_title.setText("Title");
                        et_text = (EditText)view.findViewById(R.id.text);
                        et_text.setText("Text");
                        img_path = fileArr[l].getAbsolutePath();

                        View[] arr = new View[3];
                        arr[0]= view.findViewById(R.id.click_red);
                        arr[1] = view.findViewById(R.id.click_green);
                        arr[2] = view.findViewById(R.id.click_blue);

                        for(int i=0;i<arr.length;i++)
                        {
                            final View z=arr[i];
                            arr[i].setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    color = z.getTag().toString();
                                    Log.e("Color",color);
                                }
                            });
                        }


                        alert.setNeutralButton("OK", new AlertDialog.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                DatabaseManager db = new DatabaseManager(
                                        AlbumActivity.this, // activity z galerią zdjęć
                                        "NotatkiKulisArek.db", // nazwa bazy
                                        null,
                                        4 //wersja bazy, po zmianie schematu bazy należy ją zwiększyć
                                );

                                db.insert(et_title.getText().toString(), et_text.getText().toString(), img_path, color);
                            }
                        });
                        alert.show();

                        return false;
                    }
                });
                linearLayout.addView(civArr[i]);
                Log.e("Iterator", Integer.toString(i));
            }

        }
        scrollView.addView(linearLayout);

        /*
        linearLayouts = new LinearLayout[(fileArr.length+1)/2];
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        int iter = 0;
        while(fileArr.length!=0 && fileArr[0] != null)
        {
            linearLayouts[iter] = new LinearLayout(this);
            linearLayouts[iter].setOrientation(LinearLayout.VERTICAL);
            civArr[2*iter] = new CustomImageView(AlbumActivity.this);
            civArr[2*iter].setImageBitmap(betterImageDecode(fileArr[0].getPath()));
            if(fileArr.length>1) {
                civArr[2 * iter + 1] = new CustomImageView(AlbumActivity.this);
                civArr[2*iter+1].setImageBitmap(betterImageDecode(fileArr[1].getPath()));
                if(iter%2==0)
                {
                    civArr[2*iter].setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 2f));
                    civArr[2*iter+1].setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1f));
                }
                else
                {
                    civArr[2*iter].setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1f));
                    civArr[2*iter+1].setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 2f));
                }
                linearLayouts[iter].addView(civArr[2*iter]);
                linearLayouts[iter].addView(civArr[2*iter+1]);
                File[] temp = new File[fileArr.length-2];
                for(int i=0;i<fileArr.length-2;i++)
                {
                    temp[i] = fileArr[i+2];
                }
                fileArr = temp;
                iter++;
            }
            else
            {
                civArr[2*iter].setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                linearLayouts[iter].addView(civArr[2 * iter]);
                break;
            }
        }
        for(int i=0;i<linearLayouts.length;i++)
        {
            linearLayout.addView(linearLayouts[i]);
        }
        scrollView.addView(linearLayout);
*/
    }



    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_album);
        refresh();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        //this.getResources().getConfiguration().orientation == ORIENTATION_PORTRAIT

        Bundle bundle = getIntent().getExtras();
        path = bundle.getString("path").toString();

        refresh();

    }
}
