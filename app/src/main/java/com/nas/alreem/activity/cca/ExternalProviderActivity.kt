package com.nas.alreem.activity.cca

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nas.alreem.R
import com.nas.alreem.activity.cca.adapter.ExternalProviderRecyclerAdapter
import com.nas.alreem.activity.cca.model.ExternalProvidersRequestModel
import com.nas.alreem.activity.cca.model.ExternalProvidersResponseModel
import com.nas.alreem.activity.home.HomeActivity
import com.nas.alreem.constants.*

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ExternalProviderActivity : AppCompatActivity() {
    lateinit var mContext: Context
    lateinit var titleTextView: TextView
    lateinit var back: ImageView
    lateinit var backRelative: RelativeLayout
    lateinit var logoclick: ImageView
   // lateinit var bannerImage: ImageView
    lateinit var progressBar: ProgressBar
    var extras: Bundle? = null
    var tab_type: String? = null
    var relativeHeader: RelativeLayout? = null
//    var mStudentSpinner: LinearLayout? = null
//    var studImg: ImageView? = null
//    var studName: TextView? = null
    var mnewsLetterListView: RecyclerView? = null
//    var textViewYear: TextView? = null
    var stud_id = ""
    var stud_class = ""
    var stud_name = ""
    var stud_img = ""
    var section = ""
    private val mListViewArray: ArrayList<ExternalProvidersResponseModel.Data.Lists>? = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_external_provider)
        mContext = this
        initilaiseUI()
        logoclick.setOnClickListener {
            val mIntent = Intent(mContext, HomeActivity::class.java)
            mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

            startActivity(mIntent)
        }
        backRelative.setOnClickListener {
            finish()
        }

        var internetCheck = ConstantFunctions.internetCheck(mContext)
        if (internetCheck) {
            getList()

        } else {
            DialogFunctions.showInternetAlertDialog(mContext)

        }


    }

    private fun getList() {
        val token = PreferenceManager.getaccesstoken(mContext)
        val body = ExternalProvidersRequestModel("0","10")
        val call: Call<ExternalProvidersResponseModel> =
            ApiClient.getClient.getExternalProviders( body,"Bearer $token")
        progressBar.visibility = View.VISIBLE
        call.enqueue(object : Callback<ExternalProvidersResponseModel> {
            override fun onResponse(
                call: Call<ExternalProvidersResponseModel>,
                response: Response<ExternalProvidersResponseModel>
            ) {
                progressBar.visibility = View.GONE
                if (response.isSuccessful){
                    if (response.body() != null){
                        if (response.body()!!.status == 100){

                            val bannerImageUrl: String = response.body()!!.data!!.banner_image.toString()
                           /* if (!response.body()!!.data!!.banner_image!!.equals("")){
                                Glide.with(mContext!!).load(ConstantFunctions.replace(bannerImageUrl)).fitCenter()

                                    .centerCrop().into(bannerImage!!)
                            }*/



                            if (response.body()!!.data!!.lists!!.size > 0) {
                                for (i in response.body()!!.data!!.lists!!.indices) {

                                    mListViewArray!!.add(response.body()!!.data!!.lists!![i]!!)
                                }
                                mnewsLetterListView!!.adapter =
                                    ExternalProviderRecyclerAdapter(mContext, mListViewArray!!)
                            }
                            else {
                                ConstantFunctions.showDialogueWithOk(
                                    mContext,
                                    "No Data Found!",
                                    "Alert"
                                )
                            }
                        }else{
                            ConstantFunctions.showDialogueWithOk(mContext,getString(R.string.common_error),"Alert")
                        }
                    }else{
                        ConstantFunctions.showDialogueWithOk(mContext,getString(R.string.common_error),"Alert")
                    }
                }else{
                   ConstantFunctions.showDialogueWithOk(mContext,getString(R.string.common_error),"Alert")
                }
            }

            override fun onFailure(call: Call<ExternalProvidersResponseModel>, t: Throwable) {
                progressBar.visibility = View.GONE
                ConstantFunctions.showDialogueWithOk(mContext,getString(R.string.common_error),"Alert")
            }

        })
    }

    private fun initilaiseUI() {
        titleTextView = findViewById(R.id.heading)
        back = findViewById(R.id.btn_left)
        backRelative = findViewById(R.id.backRelative)
        logoclick = findViewById(R.id.logoClickImgView)
       // bannerImage = findViewById(R.id.bannerImagePager)
        progressBar = findViewById(R.id.progress)
        extras = intent.extras
        if (extras != null) {
            tab_type = extras!!.getString("tab_type")
        }
        relativeHeader = findViewById<View>(R.id.relativeHeader) as RelativeLayout
//        mStudentSpinner = findViewById<View>(R.id.studentSpinner) as LinearLayout
//        studImg = findViewById<View>(R.id.imagicon) as ImageView
//        studName = findViewById<View>(R.id.studentName) as TextView
//        textViewYear = findViewById<View>(R.id.textViewYear) as TextView
        mnewsLetterListView = findViewById<View>(R.id.mnewsLetterListView) as RecyclerView
        mnewsLetterListView!!.setHasFixedSize(true)
        val divider = DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL)
        divider.setDrawable(ContextCompat.getDrawable(mContext,R.drawable.list_divider_teal)!!)
        mnewsLetterListView!!.addItemDecoration(divider)
//        mnewsLetterListView!!.addItemDecoration(DividerItemDecoration(resources.getDrawable(R.drawable.list_divider_teal)))
        logoclick.setOnClickListener {
            val mIntent = Intent(mContext, HomeActivity::class.java)
            mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

            startActivity(mIntent)
        }
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        mnewsLetterListView!!.layoutManager = llm
        mnewsLetterListView!!.addOnItemClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                //uncomment

                if (mListViewArray!![position].file!!.endsWith(".pdf")) {
                    val intent = Intent(mContext, PDFViewerActivity::class.java)
                    intent.putExtra("Url", mListViewArray[position].file)
                    intent.putExtra("title", mListViewArray[position].title)
                    startActivity(intent)
                } else {
                    val intent = Intent(mContext, WebLinkActivity::class.java)
                    intent.putExtra("url", mListViewArray[position].file)
                    intent.putExtra("heading", mListViewArray[position].title)
                    Log.e("webview_url", mListViewArray[position].file.toString())
                    mContext.startActivity(intent)
                }
            }

        })

    }
}