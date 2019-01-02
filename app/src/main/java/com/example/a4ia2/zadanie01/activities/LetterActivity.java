package com.example.a4ia2.zadanie01.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.a4ia2.zadanie01.R;
import com.example.a4ia2.zadanie01.helpers.PreviewText;

import java.io.IOException;

public class LetterActivity extends AppCompatActivity {

    private LinearLayout linearLayout;
    private RelativeLayout previewLayout;
    private EditText editText;
    private PreviewText previewText;
    private ImageView stop;
    private Typeface global_typeface;
    private String global_typeface_name ="";
    private Button finish;
    private String text = "";
    private int redValue = 0;
    private int blueValue = 0;
    private int greenValue = 0;
    private TextView cs;

    public void changeAnything(){}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_letter);

        linearLayout = (LinearLayout) findViewById(R.id.scroll);
        previewLayout = (RelativeLayout) findViewById(R.id.preview);
        editText = (EditText) findViewById(R.id.edit);
        stop = (ImageView) findViewById(R.id.finito);
        finish = (Button) findViewById(R.id.finish);


        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(global_typeface!=null) {


                    AlertDialog.Builder alert = new AlertDialog.Builder(LetterActivity.this);
                    alert.setTitle("Pick color");
                    alert.setCancelable(false);                    //nie zamyka sie po kliknieciu poza
                    View t_view = View.inflate(LetterActivity.this, R.layout.color_picker, null);
                    alert.setView(t_view);
                    ImageView palet = (ImageView) t_view.findViewById(R.id.picker);
                    Log.e("paleta",palet.toString());
                    final Bitmap bitmap = ((BitmapDrawable)palet.getDrawable()).getBitmap();
                    palet.setOnTouchListener(new View.OnTouchListener(){
                        @Override
                        public boolean onTouch(View v, MotionEvent event){
                            int x = (int)event.getX();
                            int y = (int)event.getY();
                            int pixel = bitmap.getPixel(x,y);

                            //then do what you want with the pixel data, e.g
                            redValue = Color.red(pixel);
                            blueValue = Color.blue(pixel);
                            greenValue = Color.green(pixel);
                            Log.e("R",redValue+"");
                            Log.e("G",blueValue+"");
                            Log.e("B",greenValue+"");
                            //cs.setTextColor(Color.rgb(redValue,greenValue,blueValue));
                            return false;
                        }
                    });

                    alert.setNeutralButton("OK",  new AlertDialog.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            previewText.setText(text);
                            previewText.setColors(redValue, greenValue, blueValue);
                            previewLayout.removeAllViews();
                            previewLayout.addView (previewText);
                        }
                    }).show();     // null to pusty click
                }
            }
        });

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("tf", global_typeface_name);
                intent.putExtra("text", text);
                intent.putExtra("red", redValue);
                intent.putExtra("green", greenValue);
                intent.putExtra("blue", blueValue);
                setResult(300, intent);
                finish();
            }
        });


        //previewText = new PreviewText(LetterActivity.this);
        previewText = new PreviewText(LetterActivity.this, Typeface.DEFAULT);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //Log.e("textChange",charSequence.toString());
                text = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                previewText.setText(text);
                //Color c = new Color();
                //c.rgb(redValue, greenValue,redValue);
                //Log.e("kolor", );

                //previewText.setInnerColor(1);
                //previewText.setOuterColor(1);
                previewText.setColors(redValue, greenValue, blueValue);
                previewLayout.removeAllViews();
                previewLayout.addView (previewText);
            }
        };

        editText.addTextChangedListener(textWatcher);

        AssetManager assetManager = getAssets();
        try {
            String[] lista = assetManager.list("fonts"); // fonts to nazwa podfolderu w assets
            for(int i=0;i<lista.length;i++) {
                Log.e("l[i]",lista[i]);
                Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/"+lista[i]);
                TextView textView = new TextView(LetterActivity.this);
                textView.setTextColor(Color.BLACK);
                textView.setTypeface(tf);

                textView.setText(lista[i]);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.e("view", view.toString());
                        TextView tv = (TextView) view;
                        Typeface tf = tv.getTypeface();
                        global_typeface = tf;
                        global_typeface_name = tv.getText().toString();
                        //editText.setTypeface(tf);
                        previewText.setTypeFace(tf);
                        previewText.setText(text);
                        previewLayout.removeAllViews();
                        previewLayout.addView (previewText);
                    }
                });
                linearLayout.addView(textView);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
