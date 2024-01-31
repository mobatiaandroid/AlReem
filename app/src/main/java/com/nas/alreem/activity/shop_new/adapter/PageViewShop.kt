package com.nas.alreem.activity.shop_new.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.nas.alreem.R
import com.nas.alreem.activity.shop_new.ImageViewActivity


class PageViewShop : PagerAdapter {
    var context: Context
    var imagesarray = ArrayList<String>()
    lateinit var inflater: LayoutInflater


    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as View

    }

    override fun getCount(): Int {
        return imagesarray.size
    }


    constructor(context: Context, imagesarray: ArrayList<String>) {
        this.context = context
        this.imagesarray = imagesarray
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        inflater = LayoutInflater.from(context)
        var pageview: View? = inflater.inflate(R.layout.swipe_homefragment, null)
        val imageView = pageview?.findViewById<View>(R.id.adImg) as ImageView
        Log.e("arrayimage",imagesarray[position])
        if (imagesarray[position] != "") {
            Glide.with(context).load(imagesarray[position]).centerCrop().into(imageView)

        } else {

        }


        pageview.setOnClickListener {
            Log.e("clicked","Clicked")
            val new_intent = Intent(context, ImageViewActivity::class.java)
            new_intent.putExtra("currentImg", imagesarray.get(position))
            context.startActivity(new_intent)
        }
        (container as ViewPager).addView(pageview, 0)
        return pageview
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as LinearLayout)
    }
}