package com.androstock.galleryapp;

import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class ImagePagerView extends AppCompatActivity implements
         OnPageChangeListener {



	private ViewPager viewPage;
	private List<String> itemData;
	private FragmentPagerAdapter adapter;
	private String path;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.imageview_page);

		viewPage = (ViewPager) findViewById(R.id.viewPager);
		Intent intent=getIntent();

		itemData=intent.getStringArrayListExtra("paths");
		path=intent.getStringExtra("path");



		adapter = new FragmentPagerAdapter(getSupportFragmentManager(),
				itemData);

		viewPage.setAdapter(adapter);
		viewPage.setCurrentItem(intent.getIntExtra("startPosition",1));


		viewPage.setOnPageChangeListener(ImagePagerView.this);



	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.options,menu);
		return super.onCreateOptionsMenu(menu);
	}
	@RequiresApi(api = Build.VERSION_CODES.O)
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id=item.getItemId();
		if(id==R.id.wallpaper)
			setWallpaper();
		else if(id==R.id.share){
			Intent intent =new Intent(Intent.ACTION_SEND);
			Bitmap bitmap= BitmapFactory.decodeFile(path);
			intent.setType("image/jpeg");

			ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
			File file =new File(Environment.getExternalStorageDirectory()+File.separator+"ImageDemo.jpg");//poprawić


			try {
				file.createNewFile();
				FileOutputStream fileOutputStream=new FileOutputStream(file);
				fileOutputStream.write(byteArrayOutputStream.toByteArray());

			} catch (IOException e) {
				e.printStackTrace();
			}

			intent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///sdcard/ImageDemo.jpg"));
			startActivity(Intent.createChooser(intent,"Share"));
		}
		else if(id==R.id.info){



			File file = new File(path);



			Intent intencja =new Intent(ImagePagerView.this, ImageInfo.class);

			intencja.putExtra("name",file.getName());
			intencja.putExtra("path",file.getPath());
			intencja.putExtra("size"," "+((file.length()/1024>=1024)?(file.length()/1024/12024)+"MB":(file.length()/1024)+"KB"));
			startActivity(intencja);

		}
		else if(id==R.id.edit){






			Intent intencja =new Intent(ImagePagerView.this, Edit.class);

			intencja.putExtra("path",path);
			startActivity(intencja);

		}

		return super.onOptionsItemSelected(item);
	}

	private void setWallpaper(){
		Bitmap bitmap= BitmapFactory.decodeFile(path);
		WallpaperManager manager=WallpaperManager.getInstance(getApplicationContext());
		try{
			manager.setBitmap(bitmap);
			Toast.makeText(this,"Wallpaper set!",Toast.LENGTH_SHORT).show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}



	@Override
	public void onPageScrollStateChanged(int arg0) {
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	@Override
	public void onPageSelected(int position) {
		path=itemData.get(position);
	}



}
