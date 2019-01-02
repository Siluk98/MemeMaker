package com.example.a4ia2.zadanie01.adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a4ia2.zadanie01.R;
import com.example.a4ia2.zadanie01.helpers.Note;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by 4ia2 on 2017-10-23.
 */
public class SimpleArrayAdapter extends ArrayAdapter{

    private ArrayList<String> _list;
    private Context _context;
    private int _resource;



    public SimpleArrayAdapter(Context context, int resource, ArrayList<String> objects) {
        super(context, resource, objects);
        this._list = objects;
        this._context = context;
        this._resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //inflater - klasa konwertująca xml na kod javy
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.simple_array_adapter, null);
        //convertView = inflater.inflate(_resource, null);
        //szukamy każdego TextView w layoucie

        ArrayList<String> note = this._list;

        TextView tv_text = (TextView) convertView.findViewById(R.id.text);
        ImageView iv_pic = (ImageView) convertView.findViewById(R.id.pic);

        tv_text.setText(note.get(position));

        switch (position)
        {
            case 0: {
                iv_pic.setImageResource(R.drawable.fonts);
                break;
            }
            case 1: {
                iv_pic.setImageResource(R.drawable.upload);
                break;
            }
            case 2: {
                iv_pic.setImageResource(R.drawable.share);
                break;
            }
        }
        return convertView;
    }


}
