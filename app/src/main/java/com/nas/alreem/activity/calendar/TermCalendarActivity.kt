package com.nas.alreem.activity.calendar

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nas.alreem.R
import com.nas.alreem.activity.home.HomeActivity
import com.nas.alreem.activity.login.LoginActivity
import com.nas.alreem.constants.*
import com.nas.alreem.fragment.calendar.model.CalendarAPIModel
import com.nas.alreem.fragment.calendar.model.TermCalendarListModel
import com.nas.alreem.fragment.calendar.model.TermCalendarResponseModel
import com.nas.naisak.fragment.calendar.adapter.TermCalendarListAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TermCalendarActivity : AppCompatActivity() {
    lateinit var mContext: Context
    private lateinit var relativeHeader: RelativeLayout
    private lateinit var backRelative: RelativeLayout
    private lateinit var progressDialog: RelativeLayout
    private lateinit var logoClickImgView: ImageView
    private lateinit var bannerImageViewPager: ImageView
    private lateinit var btn_left: ImageView
    private lateinit var heading: TextView
    lateinit var recycler_view_list: RecyclerView
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var termListArrayList:ArrayList<TermCalendarListModel>
    lateinit var activity: Activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_term_calendar)
        mContext=this
        activity=this
        relativeHeader = findViewById(R.id.relativeHeader)
        initUI()
        if (ConstantFunctions.internetCheck(mContext)) {
//            progressDialog.visibility= View.VISIBLE
            callTermCalendarList()
        } else {
            DialogFunctions.showInternetAlertDialog(mContext)
        }
    }
    fun initUI() {
        relativeHeader = findViewById(R.id.relativeHeader)
        backRelative = findViewById(R.id.backRelative)
        heading = findViewById(R.id.heading)
        btn_left = findViewById(R.id.btn_left)
        logoClickImgView = findViewById(R.id.logoClickImgView)
        bannerImageViewPager = findViewById(R.id.bannerImageViewPager)
        heading.text = "Term Calendar"
        recycler_view_list = findViewById(R.id.mTermCalendarRecycler)
//        progressDialog = findViewById(R.id.progressDialog)
        linearLayoutManager = LinearLayoutManager(mContext)
        recycler_view_list.layoutManager = linearLayoutManager
        recycler_view_list.itemAnimator = DefaultItemAnimator()
//        val aniRotate: Animation =
//            AnimationUtils.loadAnimation(mContext, R.anim.linear_interpolator)
//        progressDialog.startAnimation(aniRotate)

        btn_left.setOnClickListener(View.OnClickListener {
            finish()
        })
        backRelative.setOnClickListener(View.OnClickListener {
            finish()
        })
        logoClickImgView.setOnClickListener(View.OnClickListener {
            val intent = Intent(mContext, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        })

        recycler_view_list.addOnItemClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {

                val urltype = termListArrayList[position].url
                if (urltype.contains(".pdf")) {
                    val intent = Intent(mContext, PDFViewerActivity::class.java)
                    intent.putExtra("Url", termListArrayList[position].url)
                    intent.putExtra("title", termListArrayList[position].title)
                    startActivity(intent)
                } else {
                    val intent = Intent(mContext, WebLinkActivity::class.java)
                    intent.putExtra("url", termListArrayList[position].url)
                    intent.putExtra("heading", termListArrayList[position].title)
                    startActivity(intent)
                }

            }

        })

    }
    fun callTermCalendarList() {
        termListArrayList= ArrayList()
//        progressDialog.visibility=View.VISIBLE
        val token = PreferenceManager.getaccesstoken(mContext)
        val call: Call<TermCalendarResponseModel> = ApiClient(mContext).getClient.termCalendar("Bearer " + token)
        call.enqueue(object : Callback<TermCalendarResponseModel> {
            override fun onFailure(call: Call<TermCalendarResponseModel>, t: Throwable) {
//                progressDialog.visibility=View.GONE
            }

            override fun onResponse(
                call: Call<TermCalendarResponseModel>,
                response: Response<TermCalendarResponseModel>
            ) {
//                progressDialog.visibility=View.GONE
                if (response.body()!!.status == 100)
                {
                    val bannerImg=response.body()!!.banner_image
                    if (bannerImg.isNotEmpty()){
                        mContext.let {
                            Glide.with(it)
                                .load(bannerImg).fitCenter()

                                .into(bannerImageViewPager)
                        }
                    } else {
                        bannerImageViewPager.setBackgroundResource(R.drawable.default_banner)

                    }

                    termListArrayList.addAll(response.body()!!.lists)
                    if (termListArrayList.size > 0) {
                        recycler_view_list.visibility = View.VISIBLE
                        val rAdapter: TermCalendarListAdapter =
                            TermCalendarListAdapter(mContext, termListArrayList)
                        recycler_view_list.adapter = rAdapter
                    }
                    else
                    {
                        DialogFunctions.commonErrorAlertDialog(mContext.resources.getString(R.string.alert), ConstantWords.status_132, mContext)

                    }

                } else if (response.body()!!.status == 116) {


                    PreferenceManager.setaccesstoken(mContext, "")
                    PreferenceManager.setUserEmail(mContext, "")
                    val mIntent = Intent(this@TermCalendarActivity, LoginActivity::class.java)
                    mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    mContext.startActivity(mIntent)

                }
                else
                {

                    DialogFunctions.commonErrorAlertDialog(mContext.resources.getString(R.string.alert), ConstantFunctions.commonErrorString(response.body()!!.status), mContext)
                }
            }
        })
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