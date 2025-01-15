package com.nas.alreem.activity.shop

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.nas.alreem.R
import com.nas.alreem.activity.home.HomeActivity
import com.nas.alreem.constants.ConstantFunctions
import com.nas.alreem.constants.PDFViewerActivity
import com.nas.alreem.constants.WebLinkActivity
import com.nas.alreem.fragment.primary.model.PrimaryFileModel
import com.nas.alreem.recyclermanager.RecyclerItemListener
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShopInformationActivity : AppCompatActivity(){
    var stud_id = ""
    var extras: Bundle? = null
    var tab_type: String? = "Information"
    var relativeHeader: RelativeLayout? = null
    var mStudentSpinner: LinearLayout? = null
    var studImg: ImageView? = null
    var studName: TextView? = null
    var mnewsLetterListView: RecyclerView? = null
   // var studentsModelArrayList: ArrayList<TeamModel>? = null
    var textViewYear: TextView? = null
    var stud_class = ""
    var stud_name = ""
    var stud_img = ""
    var section = ""
   // var headermanager: HeaderManager? = null
    var back: ImageView? = null
    var home: ImageView? = null
    private lateinit var mContext: Context
    private var mListViewArray: ArrayList<PrimaryFileModel>? = null
   // var progressBarDialog: ProgressBarDialog? = null
   lateinit var activity: Activity


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_information)
        mContext = this
        activity =this
        initUI()
       // if (AppUtils.checkInternet(mContext)) {
            getMusicAcademyInfoList()
            //            getList();
     //   } else {
            /*AppUtils.showDialogAlertDismiss(
                mContext as Activity?,
                "Network Error",
                getString(R.string.no_internet),
                R.drawable.nonetworkicon,
                R.drawable.roundred
            )*/
     //   }
    }


    private fun initUI() {
        extras = intent.extras
        if (extras != null) {
            tab_type = extras!!.getString("tab_type")
        }
      //  progressBarDialog = ProgressBarDialog(mContext, R.drawable.spinner)
        relativeHeader = findViewById<View>(R.id.relativeHeader) as RelativeLayout
        mStudentSpinner = findViewById<View>(R.id.studentSpinner) as LinearLayout
        studImg = findViewById<View>(R.id.imagicon) as ImageView
        studName = findViewById<View>(R.id.studentName) as TextView
        textViewYear = findViewById<View>(R.id.textViewYear) as TextView
        mnewsLetterListView = findViewById<View>(R.id.mnewsLetterListView) as RecyclerView
        mnewsLetterListView!!.setHasFixedSize(true)
       // mnewsLetterListView!!.addItemDecoration(DividerItemDecoration(resources.getDrawable(R.drawable.list_divider_teal)))
      //  headermanager = HeaderManager(this@MusicAcademyInformationActivity, tab_type)
       // headermanager.getHeader(relativeHeader, 0)
      //  back = headermanager.getLeftButton()
      /*  headermanager.setButtonLeftSelector(
            R.drawable.back,
            R.drawable.back
        )*/
        back!!.setOnClickListener {
           // AppUtils.hideKeyBoard(mContext)
            finish()
        }
      //  home = headermanager.getLogoButton()
        home!!.setOnClickListener {
            val `in` = Intent(
                mContext,
                HomeActivity::class.java
            )
            `in`.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(`in`)
        }
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        mnewsLetterListView!!.layoutManager = llm
        mnewsLetterListView!!.addOnItemTouchListener(
            RecyclerItemListener(
                applicationContext, mnewsLetterListView!!,
                object : RecyclerItemListener.RecyclerTouchListener {
                    override fun onClickItem(v: View?, position: Int) {
                        if (mListViewArray!![position].file.endsWith(".pdf")) {
                            val intent = Intent(
                                mContext,
                                PDFViewerActivity::class.java
                            )
                            intent.putExtra("pdf_url", mListViewArray!![position].title)
                            startActivity(intent)
                        } else {
                            val intent = Intent(
                                mContext,
                                WebLinkActivity::class.java
                            )
                            intent.putExtra("url", mListViewArray!![position].title)
                            intent.putExtra("tab_type", mListViewArray!![position].file)
                            mContext!!.startActivity(intent)
                        }
                    }

                    override fun onLongClickItem(v: View?, position: Int) {}
                })
        )
    }

    private fun getMusicAcademyInfoList() {

    }
    override fun onResume() {
        super.onResume()
        if (!ConstantFunctions.runMethod.equals("Dev")) {
            if (ConstantFunctions().isDeveloperModeEnabled(mContext)) {
                ConstantFunctions().showDeviceIsDeveloperPopUp(activity)
            }
        }
    }

   /* @Throws(JSONException::class)
    private fun getSearchValues(Object: JSONObject): SecondaryModel {
        val mSecondaryModel = SecondaryModel()
        mSecondaryModel.setmId(Object.getString("id"))
        mSecondaryModel.setmFile(Object.getString("submenu"))
        mSecondaryModel.setmName(Object.getString("filename"))
        if (Object.has("status")) {
            mSecondaryModel.setStatus(Object.optString("status"))
        } else {
            mSecondaryModel.setStatus("1")
        }
        return mSecondaryModel
    }*/
}