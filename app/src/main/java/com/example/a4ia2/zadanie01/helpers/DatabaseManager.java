package com.example.a4ia2.zadanie01.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseManager extends SQLiteOpenHelper
{
    //private SQLiteDatabase db;
    public DatabaseManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE tabela1 (_id INTEGER PRIMARY KEY AUTOINCREMENT NOT  NULL, 'title' TEXT, 'text' TEXT, 'path' TEXT, 'color' TEXT )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tabela1");
        onCreate(db);
    }

    public boolean insert(String title, String text, String path ,String color){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("text", text);
        contentValues.put("color", color);
        contentValues.put("path", path);

        db.insertOrThrow("tabela1", null, contentValues); // gdy insert się nie powiedzie, będzie błąd
        db.close();
        return true;
    }

    public int delete(String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete("tabela1","_id = ? ",new String[]{id});

    }

    public void update(String id, String title, String text, String color)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("text",text);
        contentValues.put("color",color);

        db.update("tabela1", contentValues, "_id = ? ", new String[]{id});
        db.close();
    }

    public ArrayList<Note> getAll(){

        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Note> notes= new ArrayList<>();
        Cursor result = db.rawQuery("SELECT * FROM tabela1" , null);
        while(result.moveToNext()){
            notes.add( new Note(
                    Integer.parseInt(result.getString(result.getColumnIndex("_id"))),
                    result.getString(result.getColumnIndex("title")),
                    result.getString(result.getColumnIndex("path")),
                    result.getString(result.getColumnIndex("text")),
                    result.getString(result.getColumnIndex("color"))
            ));

        }
        return notes;
    }

}
