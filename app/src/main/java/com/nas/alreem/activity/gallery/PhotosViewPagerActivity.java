package com.nas.alreem.activity.gallery;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;


import com.nas.alreem.R;
import com.nas.alreem.activity.gallery.adapter.ImagePagerAdapter;
import com.nas.alreem.activity.gallery.model.PhotosModel;

import java.util.ArrayList;

/**
 * Created by Rijo on 25/1/17.
 */
public class PhotosViewPagerActivity extends AppCompatActivity {
    Context mContext;
//    RelativeLayout relativeHeader;
//    HeaderManager headermanager;
    ImageView back;
//    ImageView home;
    Intent intent;
    ArrayList<PhotosModel> mPhotosModelArrayList;
  Bundle extras;
    ViewPager bannerImageViewPager;
    int pos=0;
    //RelativeLayout relativeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_viewpager);
        mContext = this;
        initUI();

    }

    private void initUI() {
//        relativeHeader = (RelativeLayout) findViewById(R.id.relativeHeader);
        back = (ImageView) findViewById(R.id.back);
        bannerImageViewPager = (ViewPager) findViewById(R.id.bannerImageViewPager);
        extras=getIntent().getExtras();
        if(extras!=null){
            mPhotosModelArrayList= (ArrayList<PhotosModel>) extras.getSerializable("photo_array");
            pos= extras.getInt("pos");
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        bannerImageViewPager.setAdapter(new ImagePagerAdapter(mContext, mPhotosModelArrayList,"portrait"));
        bannerImageViewPager.setCurrentItem(pos);
        bannerImageViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
//                headermanager.setTitle((position + 1) + " Of " + mPhotosModelArrayList.size());
                pos=position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            relativeHeader.setVisibility(View.GONE);
            back.setVisibility(View.GONE);
            bannerImageViewPager.setAdapter(new ImagePagerAdapter(mContext, mPhotosModelArrayList, "landscape"));
            bannerImageViewPager.setCurrentItem(pos);


            //Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            //Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
//            relativeHeader.setVisibility(View.VISIBLE);
            back.setVisibility(View.GONE);
            bannerImageViewPager.setAdapter(new ImagePagerAdapter(mContext, mPhotosModelArrayList, "portrait"));
            bannerImageViewPager.setCurrentItem(pos);


        }
    }



}
