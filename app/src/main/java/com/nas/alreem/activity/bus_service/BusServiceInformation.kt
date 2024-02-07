package com.nas.alreem.activity.bus_service

import android.content.Context
import android.content.Intent
import android.os.Bundle
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
import com.nas.alreem.R
import com.nas.alreem.activity.cca.adapter.InformationRecyclerAdapter
import com.nas.alreem.activity.cca.model.CCAInfoRequestModel
import com.nas.alreem.activity.cca.model.CCAInfoResponseModel
import com.nas.alreem.activity.cca.model.CCaInformationList
import com.nas.alreem.activity.home.HomeActivity
import com.nas.alreem.constants.ApiClient
import com.nas.alreem.constants.ConstantFunctions
import com.nas.alreem.constants.DialogFunctions
import com.nas.alreem.constants.OnItemClickListener
import com.nas.alreem.constants.PDFViewerActivity
import com.nas.alreem.constants.PreferenceManager
import com.nas.alreem.constants.WebLinkActivity
import com.nas.alreem.constants.addOnItemClickListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BusServiceInformation : AppCompatActivity() {
    lateinit var mContext: Context
    lateinit var titleTextView: TextView
    lateinit var back: ImageView
    lateinit var backRelative: RelativeLayout
    lateinit var logoclick: ImageView
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
    private val mListViewArray: ArrayList<CCaInformationList> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_information_ccaactivity)
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

        if (ConstantFunctions.internetCheck(mContext))
        {
            getList()
        }
        else
        {
            DialogFunctions.showInternetAlertDialog(mContext)
        }

    }
    private fun getList() {

        val body = CCAInfoRequestModel("0","10")
        val token = PreferenceManager.getaccesstoken(mContext)
        val call: Call<CCAInfoResponseModel> =
            ApiClient.getClient.getCCAInfo(body, "Bearer $token")
        progressBar.visibility = View.VISIBLE
        call.enqueue(object : Callback<CCAInfoResponseModel> {
            override fun onResponse(
                call: Call<CCAInfoResponseModel>,
                response: Response<CCAInfoResponseModel>
            ) {
                progressBar.visibility = View.GONE

                if (response.isSuccessful) {
                    if (response.body() != null) {
                        if (response.body()!!.status.toString() == "100") {

                            if (response.body()!!.data.isNotEmpty()) {
                                for (i in response.body()!!.data.indices) {
                                    mListViewArray.add(response.body()!!.data!![i]!!)
                                }
                                mnewsLetterListView!!.adapter =
                                    InformationRecyclerAdapter(mContext, mListViewArray)

                            } else {
                                ConstantFunctions.showDialogueWithOk(
                                    mContext,
                                    "No Data Found!",
                                    "Alert"
                                )
                            }
                        } else {
                            ConstantFunctions.showDialogueWithOk(
                                mContext,
                                getString(R.string.common_error),
                                "Alert"
                            )
                        }
                    } else {
                        ConstantFunctions.showDialogueWithOk(
                            mContext,
                            getString(R.string.common_error),
                            "Alert"
                        )
                    }
                }
            }

            override fun onFailure(call: Call<CCAInfoResponseModel>, t: Throwable) {
                progressBar.visibility = View.GONE

                ConstantFunctions.showDialogueWithOk(
                    mContext,
                    getString(R.string.common_error),
                    "Alert"
                )
            }


        })
    }
    private fun initilaiseUI() {
        extras = intent.extras
        if (extras != null) {
            tab_type = extras!!.getString("tab_type")
        }
        progressBar = findViewById(R.id.progress)
        logoclick = findViewById(R.id.logoClickImgView)
        backRelative = findViewById(R.id.backRelative)
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
                if (mListViewArray[position].url!!.endsWith(".pdf")) {
                    val intent = Intent(mContext, PDFViewerActivity::class.java)
                    intent.putExtra("Url", mListViewArray[position].url)
                    intent.putExtra("title", mListViewArray[position].title)
                    startActivity(intent)
                } else {
                    val intent = Intent(mContext, WebLinkActivity::class.java)
                    intent.putExtra("url", mListViewArray[position].url)
                    intent.putExtra("heading", mListViewArray[position].title)
                    mContext.startActivity(intent)
                }
            }

        })

    }
}