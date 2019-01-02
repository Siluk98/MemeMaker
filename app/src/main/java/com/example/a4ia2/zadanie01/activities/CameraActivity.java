package com.example.a4ia2.zadanie01.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.a4ia2.zadanie01.helpers.CameraPreview;
import com.example.a4ia2.zadanie01.helpers.Kolo;
import com.example.a4ia2.zadanie01.helpers.Miniatura;
import com.example.a4ia2.zadanie01.R;


import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.OrientationEventListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class CameraActivity extends AppCompatActivity {
    private OrientationEventListener orientationEventListener;
    private Camera camera;
    private int cameraId = -1,deviceRZ;
    private CameraPreview cameraPreview;
    private FrameLayout frameLayout;
    private ImageView takephoto,bright,flash,color,size;//,savephoto;
    private Spinner spinner;
    private String folder;
    private String option;
    private File dir;
    private Camera.Parameters camParams;
    private float x1,x2;
    private Kolo kolo;
    private Miniatura lastMin=null;
    private ArrayList<Miniatura> miniatury = new ArrayList<>();
    private ArrayList<Bitmap> bitmapy = new ArrayList<>();
    private ArrayList<byte[]> bytes = new ArrayList<>();
    private static String[] fileNames, options;
    private byte[] fdata;
    private boolean hor,ver;
    private boolean photoactive = true;

    private void refresh(){
        int x0 = CameraActivity.this.getResources().getDisplayMetrics().widthPixels/2;
        int y0 = CameraActivity.this.getResources().getDisplayMetrics().heightPixels/2;
        int r= 200;
        int x,y;
        float alpha=0;
        for(int i=0; i<miniatury.size();i++)
        {
            x=(int)(x0+r*Math.cos(alpha)-(r/2));
            y=(int)(y0+r*Math.sin(alpha)-(r/2));
            miniatury.get(i).setX(x+25);
            miniatury.get(i).setY(y+25);
            alpha +=2*Math.PI/miniatury.size();
        }
    }
    private void savePhoto(final Bitmap bitM)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(CameraActivity.this);
        alert.setTitle("ZAPISZ ZDJĘCIE");
        //alert.setMessage("Choose wisely");
        final File pic = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        dir = new File(pic, "Kulis");
        File[] files = dir.listFiles();//getFiles();

        fileNames = new String[files.length];

        for (int i = 0; i < files.length; i++) {
            fileNames[i] = files[i].getName();
        }
        alert.setItems(fileNames, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // wyswietl opcje[which]);
                Toast.makeText(CameraActivity.this, "Zapisuję", Toast.LENGTH_SHORT).show();
                folder = fileNames[which];
                dir = new File(pic, "Kulis/" + folder);
                SimpleDateFormat dFormat = new SimpleDateFormat("yy-MM-dd:HHmmss");
                String d = dFormat.format(new Date());
                try {
                    FileOutputStream fs = new FileOutputStream(dir.getAbsolutePath() + "/aba_" + d + ".png");
                    bitM.compress(Bitmap.CompressFormat.PNG, 100, fs);
                    fs.close();
                    Context context = getApplicationContext();
                    CharSequence text = "Zdjecie zapisano";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();  // null to pusty click
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        alert.setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //wyswietl which
                AlertDialog.Builder alert2 = new AlertDialog.Builder(CameraActivity.this);
                alert2.setCancelable(true);                    //nie zamyka sie po kliknieciu poza
                alert2.setMessage("Zdjęcie nie zostało zapisane");
                alert2.setNeutralButton("Oh no", null).show();     // nu
            }
        });
        alert.show();
    }
    private void delPhoto(Miniatura element)
    {
        bitmapy.remove(miniatury.indexOf(element));
        bytes.remove(miniatury.indexOf(element));
        miniatury.remove(miniatury.indexOf(element));
        frameLayout.removeView(element);
    }
    private Camera.PictureCallback camPictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            camera.startPreview();
            Matrix matrix = new Matrix();
            if(deviceRZ<315 && deviceRZ>180 )
                matrix.postRotate(0);
            else
                matrix.postRotate(90);
            fdata = data;
            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            Bitmap smallbit = Bitmap.createScaledBitmap(bitmap , 150, 150, false);
            //smallbit = Bitmap.createBitmap(smallbit, 0, 0, smallbit.getWidth(), smallbit.getHeight(), matrix, true);
            Miniatura min = new Miniatura(CameraActivity.this, smallbit, 150, 150);
            bitmapy.add(bitmap);
            frameLayout.addView(min);
            miniatury.add(min);
            bytes.add(fdata);
            if(deviceRZ<315 && deviceRZ>180 )
                min.setRotation(90);

            refresh();
            lastMin=min;
            min.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    //Log.d("XX", "pos x: "+event.getRawX()+" "+event.getX());
                    //Log.d("XX", "pos y: "+event.getRawY());
                    Miniatura el = (Miniatura)v;
                    v.setX(event.getRawX()-100);
                    v.setY(event.getRawY()-200);


                    switch(event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            //Log.d("XX", "down");
                            x1 = event.getX();
                            break;
                        case MotionEvent.ACTION_MOVE:
                            //Log.d("XX", "move");
                            x2 = event.getX();
                            float deltaX = x2 - x1;
                            if (Math.abs(deltaX) > 75)
                            {
                                delPhoto(el);
                            }
                            break;
                        case MotionEvent.ACTION_UP:
                            //Log.d("XX", "up");
                            break;

                    }
                    return false;
                }
            });

            min.setOnLongClickListener(new View.OnLongClickListener(){
                @Override
                public boolean onLongClick(View v) {
                    final Miniatura el = (Miniatura)v;
                    AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());
                    alert.setTitle("Zdjęcie");
                    //nie może mieć setMessage!!!
                    String[] opcje = {"podglad","usuń","zapisz"};
                    alert.setItems(opcje, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which)
                            {
                                case 0:
                                    break;
                                case 1:
                                    delPhoto(el);
                                    refresh();
                                    break;
                                case 2:
                                    savePhoto(bitmapy.get(miniatury.indexOf(el)));
                                    delPhoto(el);
                                    refresh();
                                    break;

                            }

                        }
                    });
                    //

                    alert.show();

                    return false;
                }
            });
            photoactive=true;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        initCamera();
        initPreview();
        deviceRZ=0;
        camParams = camera.getParameters();
        takephoto = (ImageView) findViewById(R.id.photo_take);
        //savephoto = (ImageButton) findViewById(R.id.savephoto);
        bright = (ImageView) findViewById(R.id.photo_brightness);
        flash = (ImageView) findViewById(R.id.photo_flash);
        color = (ImageView) findViewById(R.id.photo_color);
        size = (ImageView) findViewById(R.id.photo_size);
        spinner =(Spinner)findViewById(R.id.spinner);
        String[] array = new String[]{" Spinner ","zapisz ostatnie","zapisz wszystkie","usuń wszystkie"};
        ArrayAdapter<String> adapterO = new ArrayAdapter<String>(
                CameraActivity.this,     // Context
                android.R.layout.simple_spinner_dropdown_item,// id pola txt w wierszu
                array );         // tablica przechowująca dane
        spinner.setAdapter(adapterO);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

                switch (position) {
                    case 1:
                        // Whatever you want to happen when the first item gets selected
                        if(lastMin!=null) {
                            savePhoto(bitmapy.get(miniatury.indexOf(lastMin)));
                            delPhoto(lastMin);

                            lastMin=null;
                        }
                        break;
                    case 2:
                        // Whatever you want to happen when the second item gets selected
                        while(miniatury.size()!=0)
                        {
                            savePhoto(bitmapy.get(0));
                            delPhoto(miniatury.get(0));
                            lastMin=null;
                        }

                        break;
                    case 3:
                        while(miniatury.size()!=0)
                        {
                            delPhoto(miniatury.get(0));
                            lastMin=null;
                        }
                        // Whatever you want to happen when the thrid item gets selected
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });


        bright.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                if (camParams.getSupportedWhiteBalance() != null) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(CameraActivity.this);
                    alert.setTitle("Brightness");
                    List list = camParams.getSupportedWhiteBalance();
                    options = new String[list.size()];
                    for(int i = 0; i < list.size(); i++) options[i] = list.get(i).toString();


                    alert.setItems(options, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            option=options[which];
                            camParams.setWhiteBalance(option);
                            camera.setParameters(camParams);
                        }
                    });

                    alert.show();
                }
            }
        });

        color.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                if (camParams.getSupportedColorEffects() != null) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(CameraActivity.this);
                    alert.setTitle("Color Effects");
                    List list = camParams.getSupportedColorEffects();
                    options = new String[list.size()];
                    for(int i = 0; i < list.size(); i++) options[i] = list.get(i).toString();


                    alert.setItems(options, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            option=options[which];
                            camParams.setColorEffect(option);
                            camera.setParameters(camParams);
                        }
                    });

                    alert.show();
                }
            }
        });
        size.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                if (camParams.getSupportedPictureSizes() != null) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(CameraActivity.this);
                    alert.setTitle("Color Effects");
                    List list = camParams.getSupportedPictureSizes();
                    options = new String[list.size()];
                    for(int i = 0; i < list.size(); i++) options[i] = Integer.toString(camParams.getSupportedPictureSizes().get(i).width)+"x"+Integer.toString(camParams.getSupportedPictureSizes().get(i).height);


                    alert.setItems(options, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            camParams.setPictureSize(camParams.getSupportedPictureSizes().get(which).width,camParams.getSupportedPictureSizes().get(which).height);
                            camera.setParameters(camParams);
                        }
                    });

                    alert.show();
                }
            }
        });
        flash.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alert = new AlertDialog.Builder(CameraActivity.this);
                alert.setTitle("Color Effects");
                //Log.e("flash", Integer.toString(camParams.getMinExposureCompensation()));
                int d=  camParams.getMaxExposureCompensation()-camParams.getMinExposureCompensation();
                options = new String[d+1];
                for(int i =camParams.getMinExposureCompensation(); i<=camParams.getMaxExposureCompensation();i++)
                {
                    options[i-camParams.getMinExposureCompensation()]=Integer.toString(i);
                }

                alert.setItems(options, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        camParams.setExposureCompensation(Integer.parseInt(options[which]));
                        camera.setParameters(camParams);
                    }
                });
//
                alert.show();

            }
        });
        takephoto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(photoactive) {
                    photoactive = false;
                    camera.takePicture(null, null, camPictureCallback);

                    Context context = getApplicationContext();
                    CharSequence text = "Zdjecie zrobiono";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
        });
        /*savephoto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                if(fdata!=null) {
                AlertDialog.Builder alert = new AlertDialog.Builder(CameraActivity.this);
                alert.setTitle("ZAPISZ ZDJĘCIE");
                //alert.setMessage("Choose wisely");
                final File pic = Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_PICTURES );
                dir = new File(pic,"JakubNiechaj");
                File[] files = getFiles();

                fileNames = new String[files.length];

                for(int i =0;i<files.length;i++)
                {
                    fileNames[i] = files[i].getName();
                }
                alert.setItems(fileNames, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // wyswietl opcje[which]);
                        folder=fileNames[which];
                        dir = new File(pic,"JakubNiechaj/"+folder);
                        SimpleDateFormat dFormat = new SimpleDateFormat("yy-MM-dd:HHmmss");
                        String d = dFormat.format(new Date());
                            try {
                                FileOutputStream fs = new FileOutputStream(dir.getAbsolutePath()+"/aba_"+d+".png");
                                fs.write(fdata);
                                fs.close();
                                fdata = null;
                                AlertDialog.Builder alert2 = new AlertDialog.Builder(CameraActivity.this);
                                alert2.setCancelable(true);                    //nie zamyka sie po kliknieciu poza
                                alert2.setMessage("Zapisano zdjęcie");
                                alert2.setNeutralButton("OK", null).show();     // null to pusty click
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                    }
                });

                alert.setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        //wyswietl which
                        fdata = null;
                        AlertDialog.Builder alert2 = new AlertDialog.Builder(CameraActivity.this);
                        alert2.setCancelable(true);                    //nie zamyka sie po kliknieciu poza
                        alert2.setMessage("Zdjęcie nie zostało zapisane");
                        alert2.setNeutralButton("OK", null).show();     // nu
                    }
                });
                //
                alert.show();

                }else{
                    AlertDialog.Builder alert2 = new AlertDialog.Builder(CameraActivity.this);
                    alert2.setCancelable(true);                    //nie zamyka sie po kliknieciu poza
                    alert2.setMessage("Brak zdjęcia do zapisu");
                    alert2.setNeutralButton("OK", null).show();     // null to pusty click
                }
            }

        });*/
        hor=false;
        ver=false;
        deviceRZ=0;
        orientationEventListener = new OrientationEventListener(CameraActivity.this) {
            @Override
            public void onOrientationChanged(int i) {
                // i zwraca kąt 0 - 360 stopni podczas obracania ekranem w osi Z
                // tutaj wykonaj animacje butonów i miniatur zdjęć
                //Log.d("orientation","kąt Z: "+ i);
                deviceRZ=i;
                int dur = 200;
                if(i<315 && i>180 && !hor  ) {
                    hor=true;
                    ver=false;

                    int angle = 90;
                    ObjectAnimator.ofFloat(flash, View.ROTATION, angle).setDuration(dur).start();
                    ObjectAnimator.ofFloat(takephoto, View.ROTATION, angle).setDuration(dur).start();
                    ObjectAnimator.ofFloat(color, View.ROTATION, angle).setDuration(dur).start();
                    ObjectAnimator.ofFloat(bright, View.ROTATION, angle).setDuration(dur).start();
                    ObjectAnimator.ofFloat(size, View.ROTATION, angle).setDuration(dur).start();
                    for(int j=0; j<miniatury.size();j++)
                    {
                        ObjectAnimator.ofFloat( miniatury.get(j), View.ROTATION, angle).setDuration(dur).start();
                    }
                }
                if((i>315||i<45) && !ver ) {
                    hor=false;
                    ver=true;
                    int angle = 0;

                    ObjectAnimator.ofFloat(flash, View.ROTATION, angle).setDuration(dur).start();
                    ObjectAnimator.ofFloat(takephoto, View.ROTATION, angle).setDuration(dur).start();
                    ObjectAnimator.ofFloat(color, View.ROTATION, angle).setDuration(dur).start();
                    ObjectAnimator.ofFloat(bright, View.ROTATION, angle).setDuration(dur).start();
                    ObjectAnimator.ofFloat(size, View.ROTATION, angle).setDuration(dur).start();
                    for(int j=0; j<miniatury.size();j++)
                    {
                        ObjectAnimator.ofFloat( miniatury.get(j), View.ROTATION, angle).setDuration(dur).start();
                    }
                }


            }
        };
    }

    public int getCameraId(){
        int cid = 0;
        int camerasCount = Camera.getNumberOfCameras(); // gdy więcej niż jedna kamera

        for (int i = 0; i < camerasCount; i++) {
            Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
            Camera.getCameraInfo(i, cameraInfo);

            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                cid = i;
            }
            /*
            if (cameraInfo.facing == CameraInfo.CAMERA_FACING_FRONT) {
               cid = i;
            }
            */
        }

        return cid;
    }

    public void initCamera() {
        boolean cam = getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);

        if (!cam) {
            // uwaga - brak kamery

        } else {

            // wykorzystanie danych zwróconych przez kolejną funkcję getCameraId()

            int cameraId = getCameraId();
            // jest jakaś kamera!
            if (cameraId < 0) {
                // brak kamery z przodu!
            } else {
                camera = Camera.open(cameraId);
            }
        }
    }

    public void initPreview(){
        cameraPreview = new CameraPreview(CameraActivity.this, camera);
        frameLayout = (FrameLayout) findViewById(R.id.camera);
        frameLayout.addView(cameraPreview);
        kolo = new Kolo(CameraActivity.this);
        frameLayout.addView(kolo);


    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("zd","pause");

        if (camera != null) {
            camera.stopPreview();
            //linijka nieudokumentowana w API, bez niej jest crash przy wznawiamiu kamery
            cameraPreview.getHolder().removeCallback(cameraPreview);
            camera.release();
            camera = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("zd","resume");
        if (orientationEventListener.canDetectOrientation()) {
            // Log - listener działa
            orientationEventListener.enable();
            //Log.d("orientation","dzial");

        } else {
            // Log - listener nie działa
            //Log.d("orientation","niedzial");
        }
        if (camera == null) {
            //zainicjalizuj kamerę od nowa
            // czyli uruchom funkcje initCamera() i initPreview()
            initCamera();
            initPreview();
        }
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("zd","restart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("zd","stop");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("zd","start");
    }

    protected File[] getFiles(){
        File[] files = dir.listFiles();
        Log.e("No. Files",Integer.toString(files.length));
        Arrays.sort(files);
        return files;
    }

}

