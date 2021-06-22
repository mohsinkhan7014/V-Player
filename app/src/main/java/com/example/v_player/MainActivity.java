package com.example.v_player;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MyAdaptor myAdaptor;
    int permission=1;
    File directory;
    boolean aBoolean_permission;

    public static ArrayList<File> fileArrayList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.videolist);
        //phone and memory
        directory=new File("/mnt/");

        //read only sd card
//        directory=new File("/storage/");

        GridLayoutManager layoutManager=new GridLayoutManager(MainActivity.this,2);
        recyclerView.setLayoutManager(layoutManager);
        permissionForVideo();

    }

    private void permissionForVideo() {

        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
        {

            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE))
            {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            }
            else {



                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        permission);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        else {
            aBoolean_permission=true;
            getFile(directory);
            myAdaptor=new MyAdaptor(getApplicationContext(),fileArrayList);
            recyclerView.setAdapter(myAdaptor);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==permission)
        {
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                aBoolean_permission=true;
                getFile(directory);
                myAdaptor=new MyAdaptor(getApplicationContext(),fileArrayList);
                recyclerView.setAdapter(myAdaptor);
            }
            else
            {
                Toast.makeText(this, "Please Allow the Permission", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private ArrayList<File> getFile(File directory) {
        File files[]=directory.listFiles();
        if( files!=null)
        {
            for(int i=0;i<files.length;i++)
            {
                if(files[i].isDirectory())
                {
                    getFile(files[i]);
                }
                else
                {
                    aBoolean_permission=false;
                    if(files[i].getName().endsWith(".mp4")){
                        for(int j=0;j<fileArrayList.size();j++)
                        {
                           if(fileArrayList.get(j).getName().equals(files[j].getName()))
                           {
                               aBoolean_permission=false;
                           }
                        }
                        if(aBoolean_permission){
                            aBoolean_permission=false;
                        }
                        else
                        {
                            fileArrayList.add(files[i]);
                        }
                    }
                }
            }
        }
        return fileArrayList;
    }
}