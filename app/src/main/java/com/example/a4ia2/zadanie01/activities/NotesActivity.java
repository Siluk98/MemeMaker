package com.example.a4ia2.zadanie01.activities;

import android.content.DialogInterface;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.a4ia2.zadanie01.R;
import com.example.a4ia2.zadanie01.adapters.MyArrayAdapter;
import com.example.a4ia2.zadanie01.helpers.DatabaseManager;
import com.example.a4ia2.zadanie01.helpers.Note;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class NotesActivity extends AppCompatActivity {

    private ListView listView;
    private DatabaseManager dbm;
    private MyArrayAdapter adapter;
    private ArrayList<Note> notes;
    private String g_color;
    private EditText e_title;
    private EditText e_text;
    private String g_id;
    private int sort = 0;

    protected void refresh()
    {
        listView = (ListView) findViewById(R.id.main_notes);
        notes = dbm.getAll();

        switch(sort)
        {
            case 0:
            {
                Collections.sort(notes, new Comparator<Note>() {
                    @Override
                    public int compare(Note a, Note b) {
                        return a.getTitle().compareTo(b.getTitle());
                    }
                });
                break;
            }
            case 1:
            {
                Collections.sort(notes, new Comparator<Note>() {
                    @Override
                    public int compare(Note a, Note b) {
                        return a.getText().compareTo(b.getText());
                    }
                });
                break;
            }
            case 2:
            {
                Collections.sort(notes, new Comparator<Note>() {
                    @Override
                    public int compare(Note a, Note b) {
                        return a.getColor().compareTo(b.getColor());
                    }
                });
                break;
            }
            default:
            {
                break;
            }
        }


        adapter = new MyArrayAdapter(NotesActivity.this,R.layout.array_adapter, notes);

        listView.setAdapter(adapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                final View v = view;

                AlertDialog.Builder alert = new AlertDialog.Builder(NotesActivity.this);
                alert.setTitle("Uwaga!");
                //nie może mieć setMessage!!!
                final String[] opcje = {"usuń","sortuj wg tytulu","sortuj wg textu", "sortuj wg coloru"};
                alert.setItems(opcje, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch(which)
                        {
                            case 0:
                            {
                                String aaaa = ((TextView) v.findViewById(R.id.idnr)).getText().toString();
                                dbm.delete(aaaa);
                                refresh();

                                break;
                            }
                            case 1:
                            {
                                sort = 0;
                                refresh();
                                break;
                            }
                            case 2:
                            {
                                sort = 1;
                                refresh();
                                break;
                            }
                            case 3:
                            {
                                sort = 2;
                                refresh();
                                break;
                            }
                            default:
                            {
                                break;
                            }
                        }


                    }
                });
                alert.show();

                return true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder alert = new AlertDialog.Builder(NotesActivity.this);

                g_id = ((TextView) view.findViewById(R.id.idnr)).getText().toString();
                String g_title = ((TextView) view.findViewById(R.id.title)).getText().toString();
                String g_text = ((TextView) view.findViewById(R.id.text)).getText().toString();
                g_color = "#000000";

                alert.setTitle("Edit Note");
                alert.setCancelable(true);
                View g_view = View.inflate(NotesActivity.this, R.layout.notes_edit, null);
                alert.setView(g_view);
                e_title = (EditText)g_view.findViewById(R.id.title);
                e_title.setText(g_title);
                e_text = (EditText)g_view.findViewById(R.id.text);
                e_text.setText(g_text);

                View[] arr = new View[3];
                arr[0]= g_view.findViewById(R.id.click_red);
                arr[1] = g_view.findViewById(R.id.click_green);
                arr[2] = g_view.findViewById(R.id.click_blue);

                for(int n=0;n<arr.length;n++)
                {
                    final View z=arr[n];
                    arr[n].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            g_color = z.getTag().toString();
                            Log.e("Color",g_color);
                        }
                    });
                }


                alert.setNeutralButton("OK", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dbm.update(g_id, e_title.getText().toString(), e_text.getText().toString(), g_color);
                        refresh();
                    }
                });
                alert.show();


            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);



        dbm = new DatabaseManager(NotesActivity.this, "NotatkiKulisArek.db", null, 4);

        refresh();

    }
}
