package com.example.a4ia2.zadanie01.adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a4ia2.zadanie01.R;
import com.example.a4ia2.zadanie01.helpers.Note;

import java.util.ArrayList;

/**
 * Created by 4ia2 on 2017-10-23.
 */
public class MyArrayAdapter extends ArrayAdapter{

    private ArrayList<Note> _list;
    private Context _context;
    private int _resource;



    public MyArrayAdapter(Context context, int resource, ArrayList<Note> objects) {
        super(context, resource, objects);
        this._list = objects;
        this._context = context;
        this._resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //inflater - klasa konwertująca xml na kod javy
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.array_adapter, null);
        //convertView = inflater.inflate(_resource, null);
        //szukamy każdego TextView w layoucie

        ArrayList<Note> note = this._list;

        TextView tv_idnr = (TextView) convertView.findViewById(R.id.idnr);
        TextView tv_title = (TextView) convertView.findViewById(R.id.title);
        TextView tv_text = (TextView) convertView.findViewById(R.id.text);
        TextView tv_path = (TextView) convertView.findViewById(R.id.path);

        tv_idnr.setText(String.valueOf(note.get(position).getId()));
        //tv_idnr.setText(String.valueOf(1));
        tv_title.setText(note.get(position).getTitle());
        tv_title.setTextColor(Color.parseColor(note.get(position).getColor()));
        //Log.e("COLOR", note.get(position).getColor());
        tv_text.setText(note.get(position).getText());
        tv_path.setText(note.get(position).getPath());

        //gdybyśmy chcieli klikać Imageview wewnątrz wiersza:
        ImageView iv_edit = (ImageView) convertView.findViewById(R.id.edit);
        iv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // klik w obrazek
                Log.e("button", "Test");

            }
        });
        /*
        iv_edit.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View view) {
                Log.e("Long","Boi");
                return false;
            }
        });
        */
        return convertView;
    }


}
