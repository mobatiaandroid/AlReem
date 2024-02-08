package com.nas.alreem.activity.parent_engagement

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.nas.alreem.R
import com.nas.alreem.activity.calendar.adapter.TutorialViewPagerAdapter
import java.util.Arrays



class PTAinfoActivity : AppCompatActivity(){
    private lateinit var mImgCircle: Array<ImageView?>
    private var mLinearLayout: LinearLayout? = null
    var mTutorialViewPager: ViewPager? = null
    var mContext: Context? = null
    var mTutorialViewPagerAdapter: TutorialViewPagerAdapter? = null
    var mPhotoList = ArrayList<Int>(
        Arrays.asList<Int>(
            R.drawable.tut_pa_1,
            R.drawable.tut_pa_2,
            R.drawable.tut_pa_3,
            R.drawable.tut_pa_4,
            R.drawable.tut_pa_5,
            R.drawable.tut_pa_6
        )
    )
    var dataType = 0
    var imageSkip: ImageView? = null
    var mStaffid: String? = null
    var mStudentId: String? = null
    var mStudentName: String? = null
    var mStaffName: String? = null
    var mClass: String? = null
    var selectedDate: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial)
        mContext = this
        val bundle = intent.extras
        if (bundle != null) {
            dataType = bundle.getInt("type", 0)

        }
        initialiseViewPagerUI()
    }




    private fun initialiseViewPagerUI() {
        mTutorialViewPager = findViewById<View>(R.id.tutorialViewPager) as ViewPager
        mLinearLayout = findViewById<View>(R.id.linear) as LinearLayout
        imageSkip = findViewById<View>(R.id.imageSkip) as ImageView

        mImgCircle = arrayOfNulls(mPhotoList.size)
        mTutorialViewPagerAdapter = TutorialViewPagerAdapter(mContext!!, mPhotoList)
        mTutorialViewPager!!.currentItem = 0
        mTutorialViewPager!!.adapter = mTutorialViewPagerAdapter
        addShowCountView(0)
        imageSkip!!.setOnClickListener { finish() }
        mTutorialViewPager!!.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageSelected(position: Int) {

                for (i in mPhotoList.indices) {
                    mImgCircle[i]!!.setBackgroundDrawable(
                        resources
                            .getDrawable(R.drawable.blackround)
                    )
                }
                if (position < mPhotoList.size) {
                    mImgCircle[position]!!.setBackgroundDrawable(
                        resources
                            .getDrawable(R.drawable.redround)
                    )
                    mLinearLayout!!.removeAllViews()
                    addShowCountView(position)
                } else {
                    mLinearLayout!!.removeAllViews()
                    if (dataType == 0) {
                        finish()
                    } else {


                        finish()
                    }
                }
            }

            override fun onPageScrolled(arg0: Int, arg1: Float, arg2: Int) {}
            override fun onPageScrollStateChanged(arg0: Int) {}
        })
        mTutorialViewPager!!.adapter!!.notifyDataSetChanged()
    }



    private fun addShowCountView(count: Int) {
        for (i in mPhotoList.indices) {
            mImgCircle[i] = ImageView(this)
            val layoutParams = LinearLayout.LayoutParams(
                resources
                    .getDimension(R.dimen.home_circle_width).toInt(), resources.getDimension(
                    R.dimen.home_circle_height
                ).toInt()
            )
            mImgCircle[i]!!.layoutParams = layoutParams
            if (i == count) {
                mImgCircle[i]!!.setBackgroundResource(R.drawable.redround)
            } else {
                mImgCircle[i]!!.setBackgroundResource(R.drawable.blackround)
            }
            mLinearLayout!!.addView(mImgCircle[i])
        }
    }
}
