package com.example.a4ia2.zadanie01.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.a4ia2.zadanie01.R;

import java.io.File;
import java.util.Arrays;




public class FoldersActivity extends AppCompatActivity {

    private ListView listView;
    private static String[] fileNames;
    private ImageView imageView;
    private ImageView imageView2;

    private static int TempCounter;
    private static File picw = Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_PICTURES );
    private static File pic = new File(picw.getAbsolutePath()+"/Kulis");

    protected File[] getFiles(){
        File[] files = pic.listFiles();
        Log.e("No. Files",Integer.toString(files.length));
        Arrays.sort(files);
        return files;
    }

    protected void refreshListView(ListView listView)
    {
        File[] files = getFiles();

        fileNames = new String[files.length];

        for(int i =0;i<files.length;i++)
        {
            fileNames[i] = files[i].getName();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                FoldersActivity.this,
                R.layout.file_display,
                R.id.txtinput,
                fileNames);

        listView.setAdapter(adapter);
    }

    protected void createFolder()
    {
        String folderName = new String("Folder_"+TempCounter);
        TempCounter++;
        //Log.e("String Test", folderName);
        File newFolder = new File(pic, folderName);
        newFolder.mkdir();
    }

    protected void deleteFolder()
    {
        if(TempCounter!=0)
        {TempCounter--;}
        String folderName = new String("Folder_"+TempCounter);
        //Log.e("String Test", folderName);
        File newFolder = new File(pic, folderName);
        newFolder.delete();
    }

    protected void createFolderMk2()
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(FoldersActivity.this);
        alert.setTitle("Create");
        alert.setMessage("Enter file name to create");
        //tutaj input
        final EditText input = new EditText(this);
        input.setText("");
        alert.setView(input);
        alert.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String folderName = input.getText().toString();
                Log.e("FolderName", folderName);
                File newFolder = new File(pic, folderName);
                newFolder.mkdir();
                refreshListView(listView);
            }
        });
        alert.show();
    }

    protected void deleteFolderMk3(File[] arr)
    {
        for (File file : arr) {
            if(file.isDirectory())
            {
                deleteFolderMk3(file.listFiles());
            }
            file.delete();
        }
    }

    protected void deleteFolderMk2()
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(FoldersActivity.this);
        alert.setTitle("Create");
        alert.setMessage("Enter file name to create");
        //tutaj input
        final EditText input = new EditText(this);
        input.setText("");
        alert.setView(input);
        alert.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String folderName = input.getText().toString();
                Log.e("FolderName", folderName);
                try {
                    File folder = new File(pic, folderName);
                    deleteFolderMk3(folder.listFiles());
                    folder.delete();
                    refreshListView(listView);
                }
                catch (Exception e) {}

            }
        });
        alert.show();
    }

    protected void createStaticFolder(String path)
    {
        File zzz = new File(pic.getAbsolutePath()+"/photos");
        zzz.mkdir();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folders);

        listView = (ListView) findViewById(R.id.listview);
        imageView = (ImageView) findViewById(R.id.create_folder);
        imageView2 = (ImageView) findViewById(R.id.delete_folder);


        pic.mkdir();

        createStaticFolder(pic.getAbsolutePath()+"/photos");

        refreshListView(listView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //createFolder();
                createFolderMk2();
                //refreshListView(listView);
            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //deleteFolder();
                deleteFolderMk2();
                //refreshListView(listView);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(FoldersActivity.this,AlbumActivity.class);
                //String filename = ((TextView) view.findViewById(R.id.txtinput)).getText().toString();
                String filename = fileNames[i];
                intent.putExtra("path", pic.getPath().toString()+"/"+filename);
                startActivity(intent);
            }
        });
    }
}
