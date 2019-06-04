package com.androstock.galleryapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class FragmentPagerAdapter extends FragmentStatePagerAdapter {

	private List<String> itemData;
	FragmentImageView f;

	public FragmentPagerAdapter(FragmentManager fm,
								List<String> itemData) {
		super(fm);
		this.itemData = itemData;
	}

	@Override
	public int getCount() {
		return itemData.size();
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		super.destroyItem(container, position, object);
	}

	@Override
	public Fragment getItem(int position) {

		 f = FragmentImageView.newInstance();
		f.setImageList(itemData.get(position));
		return f;
	}
}