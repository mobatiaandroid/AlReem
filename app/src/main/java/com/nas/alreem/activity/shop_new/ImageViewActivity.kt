package com.nas.alreem.activity.shop_new

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.nas.alreem.R
import com.nas.alreem.constants.ConstantFunctions
import com.nas.alreem.fragment.home.PageView
import com.nas.alreem.fragment.home.bannerarray
import com.nas.alreem.fragment.home.currentPage
import java.util.Timer
import java.util.TimerTask
import kotlin.math.max
import kotlin.math.min

class ImageViewActivity : AppCompatActivity() {

    lateinit var mContext: Context
    lateinit var image: ImageView
    lateinit var image_url: String
    private lateinit var scaleGestureDetector: ScaleGestureDetector
    private var scaleFactor = 1.0f
    private lateinit var imageView: ImageView
    lateinit var back:ImageView
    lateinit var activity: Activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.shop_item_view_image)
        mContext = this
        activity=this
        initfn()
    }

    private fun initfn() {
        image_url = intent.getStringExtra("currentImg").toString()
        image = findViewById(R.id.imageView)
        back = findViewById(R.id.back)
        back.setOnClickListener {
            finish()
        }
        mContext.let {
            Glide.with(it)
                .load(image_url)
                .into(image)
        }
        scaleGestureDetector = ScaleGestureDetector(this, ScaleListener())
    }

    override fun onTouchEvent(motionEvent: MotionEvent): Boolean {
        scaleGestureDetector.onTouchEvent(motionEvent)
        return true
    }

    private inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(scaleGestureDetector: ScaleGestureDetector): Boolean {
            scaleFactor *= scaleGestureDetector.scaleFactor
            scaleFactor = max(0.1f, min(scaleFactor, 10.0f))
            image.scaleX = scaleFactor
            image.scaleY = scaleFactor
            return true
        }

    }
    override fun onResume() {
        super.onResume()
        if (!ConstantFunctions.runMethod.equals("Dev")) {
            if (ConstantFunctions().isDeveloperModeEnabled(mContext)) {
                ConstantFunctions().showDeviceIsDeveloperPopUp(activity)
            }
        }
    }
}