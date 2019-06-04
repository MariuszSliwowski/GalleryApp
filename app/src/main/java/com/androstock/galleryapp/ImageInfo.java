package com.androstock.galleryapp;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;


public class ImageInfo extends AppCompatActivity {

    private ViewPager mViewPager;
    private PagerAdapter mPagerAdapter;

   // private String fileSizeOnDisk;
    //private String fileSizeAfterDecompression;
    private String filePath;
    private String fileName;
    PictureDetails picture;
    Metafane meta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_info);


        mPagerAdapter = new PagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.pager);
        setupViewPager(mViewPager);

        Bundle bundle = new Bundle();
        Bundle bundle2= new Bundle();
        bundle.putString("name", getIntent().getStringExtra("name"));
        bundle.putString("path", getIntent().getStringExtra("path"));
        bundle.putString("size", getIntent().getStringExtra("size"));
        picture.setArguments(bundle);

        bundle2.putString("path", getIntent().getStringExtra("path"));

        meta.setArguments(bundle2);;

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(mViewPager);

        filePath = getIntent().getStringExtra("path");
    }

    private void setupViewPager(ViewPager viewPager) {
        PagerAdapter adapter = new PagerAdapter((getSupportFragmentManager()));
        picture= new PictureDetails();
        meta=new Metafane();
        adapter.addFragment(picture,"Info");
        adapter.addFragment(meta, "Metadata");
        viewPager.setAdapter(adapter);
    }
}
