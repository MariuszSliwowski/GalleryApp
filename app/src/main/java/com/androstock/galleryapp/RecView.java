package com.androstock.galleryapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.MergeCursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;

import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.LinearLayout;

import java.io.File;
import java.util.ArrayList;

public class RecView extends AppCompatActivity  implements View.OnTouchListener {



    RecyclerView recyclerView;
    ArrayList<String> imageList = new ArrayList<String>();
    String album_name = "";
    String path_album="";
    //LoadAlbumImages loadAlbumTask;
    GalleryAdapter adapter;
    private int scala=2;
    private double scalaList=1;
    private boolean check;

    private ScaleGestureDetector mScaleGestureDetector;
    private float mScaleFactor = 1.0f;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainoptions,menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.chooseGrid) {
            adapter = new GalleryAdapter(RecView.this, imageList);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new GridLayoutManager(this, scala));

            check = !check;
            adapter.setGridView();
        }

        else if(id==R.id.chooseList){

            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            adapter = new GalleryAdapter(RecView.this, imageList);

            adapter.setScala(scalaList);

            adapter.setListView();
            recyclerView.setAdapter(adapter);

            check=!check;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rec_view);



        View view2=findViewById(R.id.recycler_view);
            view2.setOnTouchListener(this);

        mScaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());
        Intent intent = getIntent();
        album_name = intent.getStringExtra("name");
        path_album=intent.getStringExtra("path");
        setTitle(album_name);

        check=intent.getBooleanExtra("bol",true);

        recyclerView = findViewById(R.id.recycler_view);
        if(check){
            recyclerView.setLayoutManager(new GridLayoutManager(this, scala));


        }
        else
           recyclerView.setLayoutManager(new LinearLayoutManager(this));
       recyclerView.setHasFixedSize(true);
        LinearLayoutManager dd=new LinearLayoutManager(this);


    //    loadAlbumTask = new LoadAlbumImages();
        File album=new File(path_album);
      //  loadAlbumTask.execute();
        for(File f:album.listFiles()){

            imageList.add(f.getPath());
            //   f.i
        }

        adapter = new GalleryAdapter(RecView.this, imageList);
        if(!check){
            adapter.setListView();
        }
        recyclerView.setAdapter(adapter);



    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mScaleGestureDetector.onTouchEvent(event);
        return false;
    }



/*

    class LoadAlbumImages extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            imageList.clear();
        }

        protected String doInBackground(String... args) {
            String xml = "";
            String path = null;
            Uri uriExternal = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            System.out.println("kurwa"+album_name);


            String[] projection = { MediaStore.MediaColumns.DATA,
                    MediaStore.Images.Media.BUCKET_DISPLAY_NAME, MediaStore.MediaColumns.DATE_MODIFIED };
            Cursor cursorExternal = getContentResolver().query(uriExternal, projection, "bucket_display_name = \""+album_name+"\"", null, null);
            Cursor cursor = new MergeCursor(new Cursor[]{cursorExternal});
            System.out.println("FRANEK"+projection[2]);
            while (cursor.moveToNext()) {

                path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA));

                System.out.println(path);
                imageList.add(path);
            }
            cursor.close();


            return xml;
        }

        @Override
        protected void onPostExecute(String xml) {


            adapter = new GalleryAdapter(RecView.this, imageList);
            recyclerView.setAdapter(adapter);


        }
    }
    */

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector){
            mScaleFactor *= scaleGestureDetector.getScaleFactor();
            mScaleFactor = Math.max(0.4f,
                    Math.min(mScaleFactor, 1.6f));



            if(check){
                scala=(int)(mScaleFactor*5);

                recyclerView.setLayoutManager(new GridLayoutManager(RecView.this, scala));

            }
            else {
                scalaList=mScaleFactor/1.6;

                adapter.setScala(scalaList);
                recyclerView.setLayoutManager(new LinearLayoutManager(RecView.this));


            }
            return false;
        }
    }
}

