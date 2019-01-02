package com.example.a4ia2.zadanie01.helpers;

/**
 * Created by 4ia2 on 2017-10-16.
 */
public class Note {
    public int id_counter=0;
    private int id;
    private String title;
    private String path;
    private String text;
    private String color;

    public Note() {
    }

    public Note(String title, String path, String text, String color) {
        this.title = title;
        this.path = path;
        this.text = text;
        this.color = color;
        id_counter++;
        this.id=id_counter;
    }

    public Note(int id, String title, String path, String text, String color) {
        this.title = title;
        this.path = path;
        this.text = text;
        this.color = color;
        this.id=id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPath() {
        return path;
    }

    public String getText() {
        return text;
    }

    public String getColor() {
        return color;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
