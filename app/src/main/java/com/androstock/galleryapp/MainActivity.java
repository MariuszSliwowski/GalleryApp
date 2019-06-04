package com.androstock.galleryapp;


import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

import lib.folderpicker.FolderPicker;


public class MainActivity extends AppCompatActivity implements View.OnTouchListener {
    String dir;
    TextView txt_pathShow;
    Button btn_filePicker;
    String path;
    boolean check=true;
    Intent myFileIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        View view=(View)findViewById(R.id.dobre);
        view.setOnTouchListener(this);
        txt_pathShow=(TextView)findViewById(R.id.text_path);

        ActivityCompat.requestPermissions(this,new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE}, 3);



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case 10:
                if(resultCode==RESULT_OK){
                   // String folderLocation = intent.getExtras().getString("data");

                    path=data.getExtras().getString("data");
                    int k=path.length(),p=0;
                   /* for(int i=path.length()-1;i>=0;i--){
                        if(path.charAt(i)=='/'){

                            k=i;

                            break;
                        }



                    }
                    */
                    for(int i=k-1;i>=0;i--){
                        if(path.charAt(i)=='/'){
                            p=i;
                            break;
                        }
                    }

                    System.out.println(p+" "+k+" "+path.substring(0,k));
                    File fas=new File(path);
                    File homeDir = Environment.getExternalStorageDirectory();
                 //   for(File f:homeDir.listFiles()){
                        //Log.d(f.getName(),"");

                   //     System.out.println(f.getPath());
                 //   }
                    for(File f:fas.listFiles()){
                       System.out.println(f.getPath());
                    //   f.i
                    }
                   // File d = new File(Environment.getExternalStorageDirectory(), "/javastart/android/podstawy/");
                  //  d.mkdirs();
                    dir=path.substring(p+1,k);
                    System.out.println(dir+ " "+path);
                    txt_pathShow.setText(dir);
                    SharedPreferences sharedPref = this.getPreferences(MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("dane",dir);
                    editor.putString("path",path);

                    editor.commit();

                }
                break;
        }
    }
    public void wybor(View view){
        Intent intent = new Intent(this, FolderPicker.class);
        startActivityForResult(intent, 10);
    }
    public void uruchomCztery(View view){
      final Intent intencja;




        intencja =new Intent(MainActivity.this, RecView.class);




        intencja.setType("image/*");
        intencja.setAction(Intent.ACTION_GET_CONTENT);

        intencja.putExtra("name",dir);
        intencja.putExtra("bol",check);
        intencja.putExtra("path",path);
        startActivity(intencja);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainoptions,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.chooseGrid){
            check=true;
        }

        else if(id==R.id.chooseList){
            check=false;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences sharedPref = this.getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("dane",dir);
        editor.putString("path",path);
        editor.commit();

    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPref = this.getPreferences(MODE_PRIVATE);
        dir=sharedPref.getString("dane","");
        path=sharedPref.getString("path",path);


    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
}



