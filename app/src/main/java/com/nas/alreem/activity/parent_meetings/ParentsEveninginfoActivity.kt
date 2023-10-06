package com.nas.alreem.activity.parent_meetings

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.nas.alreem.R
import com.nas.alreem.activity.settings.adpter.TutorialViewPagerAdapter
import java.util.*

class ParentsEveninginfoActivity:AppCompatActivity() {
    lateinit var mContext: Context
    lateinit  var mImgCircle: Array<ImageView?>
    lateinit var mLinearLayout: LinearLayout
    var mTutorialViewPager: ViewPager? = null
    var mTutorialViewPagerAdapter: TutorialViewPagerAdapter? = null
    var mPhotoList = ArrayList(
        Arrays.asList<Int>(R.drawable.tut_pe_1, R.drawable.tut_pe_2, R.drawable.tut_pe_3,
        R.drawable.tut_pe_4, R.drawable.tut_pe_5, R.drawable.tut_pe_6, R.drawable.tut_pe_7,
            R.drawable.tut_pe_8,R.drawable.tut_pe_9))
    var dataType = 0
    lateinit var imageSkip: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial)
mContext=this
        initfn()

    }
    private fun initfn(){
        mTutorialViewPager = findViewById<View>(R.id.tutorialViewPager) as ViewPager
        mLinearLayout=findViewById(R.id.linear)
        imageSkip=findViewById(R.id.imageSkip)
        mImgCircle = arrayOfNulls(mPhotoList.size)
        mTutorialViewPagerAdapter = TutorialViewPagerAdapter(mContext!!, mPhotoList)
        mTutorialViewPager!!.currentItem = 0
        mTutorialViewPager!!.adapter = mTutorialViewPagerAdapter
        addShowCountView(0)

        imageSkip.setOnClickListener(View.OnClickListener {
            finish()
            /* if (dataType == 0) {
                 finish()
             } else {
                 val loginIntent = Intent(mContext, LoginActivity::class.java)
                 startActivity(loginIntent)
                 finish()
             }*/
        })
        mTutorialViewPager!!.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageSelected(position: Int) {

//                for (int i = 0; i < mPhotoList.size(); i++) {
//                    mImgCircle[i].setBackgroundDrawable(getResources()
//                            .getDrawable(R.drawable.blackround));
//                }
//                if (position < mPhotoList.size()) {
//                    mImgCircle[position].setBackgroundDrawable(getResources()
//                            .getDrawable(R.drawable.redround));
//                    mLinearLayout.removeAllViews();
//                    addShowCountView(position);
//                } else {
////                    mLinearLayout.removeAllViews();
////                    finish();
//                    mLinearLayout.removeAllViews();
//                    if (dataType==0)
//                    {
//                        finish();
//
//                    }
//                    else
//                    {
//                        Intent intent = new Intent(mContext, ParentsAssociationListActivity.class);
//                        intent.putExtra("tab_type", "Parents Association");
//                        mContext.startActivity(intent);
//                        finish();
//                    }
//
//                }
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
                    mLinearLayout.removeAllViews()
                    addShowCountView(position)
                } else {
                    mLinearLayout.removeAllViews()
                    if (dataType == 0) {
                        finish()
                    } else {

//                        Intent intent = new Intent(mContext, ParentsEveningTimeSlotActivity.class);
//                        intent.putExtra(JTAG_STAFF_ID,mStaffid);
//                        intent.putExtra(JTAG_STUDENT_ID,mStudentId);
//                        intent.putExtra("studentName",mStudentName);
//                        intent.putExtra("staffName",mStaffName);
//                        intent.putExtra("studentClass", mClass);
//                        intent.putExtra("selectedDate", selectedDate);
//                        mContext.startActivity(intent);
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
            val layoutParams = LinearLayout.LayoutParams(resources
                .getDimension(R.dimen.home_circle_width).toInt(), resources.getDimension(
                R.dimen.home_circle_height).toInt())
            mImgCircle[i]!!.layoutParams = layoutParams
            if (i == count) {
                mImgCircle[i]!!.setBackgroundResource(R.drawable.redround)
            } else {
                mImgCircle[i]!!.setBackgroundResource(R.drawable.blackround)
            }
            mLinearLayout.addView(mImgCircle[i])
        }
    }
}