package com.androstock.galleryapp;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.os.Environment;

import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

public class Edit extends AppCompatActivity implements RotationGestureDetector.OnRotationGestureListener {

    private String path;
    private float x,y;
    ImageView imageView;
    Bitmap bitmap;
    Bitmap reDrawnBitmap;
    float rotate=0;
    float rotate2=0;
    private RotationGestureDetector mRotationDetector;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        path=getIntent().getStringExtra("path");
        mRotationDetector = new RotationGestureDetector(this);
        ActivityCompat.requestPermissions(this,new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, 3);

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/Edit");
//           for(File f:myDir.listFiles()){
        //Log.d(f.getName(),"");

         //    System.out.println(f.getPath());
          // }
        myDir.mkdirs();
        imageView = (ImageView) findViewById(R.id.ivEdit);

        bitmap = BitmapFactory.decodeFile(path);
        reDrawnBitmap=bitmap;
        imageView.setImageBitmap(bitmap);

    }


    private void updateRotation(float rot){

        rotate2=(rotate+rot)%360;


        Matrix matrix=new Matrix();
        reDrawnBitmap=rotateBitmap(bitmap,(int)rotate2);
       // matrix.postRotate(rotate2,bitmap.getWidth(),bitmap.getHeight());

        // precompute some trig functions



     //   matrix.setRotate(rotate2);
       //     reDrawnBitmap=Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);

        imageView.setImageBitmap(reDrawnBitmap);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        mRotationDetector.onTouchEvent(event);

        switch (event.getAction()){
            case MotionEvent.ACTION_UP:
                rotate=rotate2;
           //     SaveIamge(reDrawnBitmap);

                break;

        }

        return super.onTouchEvent(event);
    }

    @Override
    public void OnRotation(RotationGestureDetector rotationDetector) {
        float angle = rotationDetector.getAngle();
        updateRotation(-angle);

    }


    private Bitmap rotateBitmap(Bitmap bitmap, int rotationAngleDegree){

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        int newW=w, newH=h;
        if (rotationAngleDegree==90 || rotationAngleDegree==270){
            newW = h;
            newH = w;
        }
        Bitmap rotatedBitmap = Bitmap.createBitmap(newW,newH, bitmap.getConfig());
        Canvas canvas = new Canvas(rotatedBitmap);

        Rect rect = new Rect(0,0,newW, newH);
        Matrix matrix = new Matrix();
        float px = rect.exactCenterX();
        float py = rect.exactCenterY();
        matrix.postTranslate(-bitmap.getWidth()/2, -bitmap.getHeight()/2);
        matrix.postRotate(rotationAngleDegree);
        matrix.postTranslate(px, py);
        canvas.drawBitmap(bitmap, matrix, new Paint( Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG | Paint.FILTER_BITMAP_FLAG ));
        matrix.reset();

        return rotatedBitmap;
    }
    private void SaveIamge(Bitmap finalBitmap) {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/Edit");
        if(!myDir.exists())
            myDir.mkdirs();

        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "image"+ n +".jpg";
        File file = new File (myDir, fname);
        if (file.exists ()) file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit,menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.save) {
           SaveIamge(reDrawnBitmap);
        }



        return super.onOptionsItemSelected(item);
    }

}
