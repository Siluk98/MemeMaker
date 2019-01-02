package com.example.a4ia2.zadanie01.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.BundleCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

//import com.example.admin.myapplication.R;

import com.example.a4ia2.zadanie01.R;
import com.example.a4ia2.zadanie01.adapters.SimpleArrayAdapter;
import com.example.a4ia2.zadanie01.helpers.Network;
import com.example.a4ia2.zadanie01.helpers.PreviewText;
import com.example.a4ia2.zadanie01.helpers.UploadFoto;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ImageActivity extends AppCompatActivity {

    private String path;
    private ImageView imageView;
    private ImageView linearLayout;
    private File imgFile;
    private DrawerLayout drawer;
    private ListView listView;

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        Bundle bundle = getIntent().getExtras();
        path = bundle.getString("path").toString();

        Log.e("PATH", path);

        imgFile = new File(path);
        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

        imageView = (ImageView) findViewById(R.id.photoView);
        imageView.setImageBitmap(myBitmap);

        linearLayout = (ImageView) findViewById(R.id.delete_photo);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgFile.delete();
                finish();
            }
        });

        drawer = (DrawerLayout) findViewById(R.id.drawer);
        listView = (ListView) findViewById(R.id.listview);
        ArrayList<String> list = new ArrayList();
        list.add("fonts");
        list.add("upload");
        list.add("upload 100x100");
        list.add("share");
        SimpleArrayAdapter adapter = new SimpleArrayAdapter(ImageActivity.this, R.layout.simple_array_adapter, list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position)
                {
                    case 0:
                    {
                        Intent intent = new Intent(ImageActivity.this, LetterActivity.class);
                        startActivityForResult(intent, 0);
                        break;
                    }
                    case 1:
                    {
                        if(Network.checkInternet(ImageActivity.this))
                        {
                            UploadFoto uploadFoto = new UploadFoto(ImageActivity.this, imgFile.getAbsolutePath(), true);
                            uploadFoto.execute();
                        }
                        else
                        {
                            Log.e("Erroru", "Internetu Shinderu");
                            Toast.makeText(ImageActivity.this,"Internetu Shinderu",Toast.LENGTH_LONG).show();
                            // error
                        }
                        break;
                    }
                    case 2:
                    {
                        if(Network.checkInternet(ImageActivity.this))
                        {
                            UploadFoto uploadFoto = new UploadFoto(ImageActivity.this, imgFile.getAbsolutePath(), false);
                            uploadFoto.execute();
                        }
                        else
                        {
                            Log.e("Erroru", "Internetu Shinderu");
                            Toast.makeText(ImageActivity.this,"Internetu Shinderu",Toast.LENGTH_LONG).show();
                            // error
                        }
                        break;
                    }
                    case 3:
                    {
                        if(Network.checkInternet(ImageActivity.this))
                        {

                            Intent share = new Intent(Intent.ACTION_SEND);
                            share.setType("image/jpeg"); //typ danych który chcemy współdzielić
                            SimpleDateFormat dFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
                            String d = dFormat.format(new Date());
                            String tempFileName = "tymczasowy"+d+".jpg"; // dodaj bieżąca datę do nazwy pliku
                            Log.e("d",d);

                            File picw = Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_PICTURES );
                            File pic = new File(picw.getAbsolutePath()+"/Kulis");

                            File temp = new File(pic.getAbsolutePath()+"/"+tempFileName);
                            Log.e("temp", temp.getAbsolutePath());
                            Bitmap bmp = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                            byte[] byteArray = stream.toByteArray();

                            BufferedOutputStream bos = null;
                            try {
                                bos = new BufferedOutputStream(new FileOutputStream(temp));
                                bos.write(byteArray);
                                bos.flush();
                                bos.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            //teraz utwórz tymczasowy plik (obiekt File), który potem będzie współdzielony
                            //wpisz do niego przekonwertowaną na byte[] bitmapę pobraną ze zdjęcia (patrz poprzednie lekcje)
                            //zapisz tymczasowy plik bezpośrednio na karcie SD w znanej sobie lokalizacji

                            share.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://"+temp.getAbsolutePath())); //pobierz plik i podziel się nim:
                            //share.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://"+imgFile.getAbsolutePath())); //pobierz plik i podziel się nim:
                            startActivity(Intent.createChooser(share, "Podziel się plikiem!")); //pokazanie okna share





                        }
                        else
                        {
                            Log.e("Erroru", "Internetu Shinderu");
                            Toast.makeText(ImageActivity.this,"Internetu Shinderu",Toast.LENGTH_LONG).show();
                            // error
                        }
                        break;
                    }
                    default:
                    {
                        break;
                    }
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Bundle bundle = data.getExtras();

        int r = bundle.getInt("red");
        int g = bundle.getInt("green");
        int b = bundle.getInt("blue");


        Log.e("r", r+"");
        Log.e("g", g+"");
        Log.e("b", b+"");
        String typeface = bundle.getString("tf");
        String text = bundle.getString("text");
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/"+typeface);

        RelativeLayout rl = new RelativeLayout(ImageActivity.this);
        DisplayMetrics dm = new DisplayMetrics();
        Context context = getApplicationContext();
        WindowManager windowManager = (WindowManager) context.getSystemService(WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);
        int height = Math.round(dm.heightPixels / dm.density);
        int width = Math.round(dm.widthPixels / dm.density);

        rl.setLayoutParams(new RelativeLayout.LayoutParams(width, height));
        final PreviewText pt = new PreviewText(ImageActivity.this, tf);
        pt.setColors(r,g,b);
        pt.setText(text);
        rl.addView (pt);
        rl.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                pt.setX(motionEvent.getX());
                pt.setY(motionEvent.getY());
                return false;
            }
        });
        RelativeLayout main = (RelativeLayout) findViewById(R.id.main);
        main.addView(rl);
    }
}
