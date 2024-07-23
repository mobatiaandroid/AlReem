package com.nas.alreem.activity.parent_engagement

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.nas.alreem.R
import com.nas.alreem.activity.home.HomeActivity
import com.nas.alreem.activity.login.model.SignUpResponseModel
import com.nas.alreem.activity.parent_engagement.adapter.ChatterBoxRecyclerAdapterNew
import com.nas.alreem.activity.parent_engagement.model.ChatterBoxResponseModel
import com.nas.alreem.activity.parent_engagement.model.TermsCalendarModel
import com.nas.alreem.activity.shop_new.model.StudentShopCardResponseModel
import com.nas.alreem.constants.ApiClient
import com.nas.alreem.constants.ConstantFunctions
import com.nas.alreem.constants.DialogFunctions
import com.nas.alreem.constants.FullscreenWebViewActivityNoHeader
import com.nas.alreem.constants.HeaderManager
import com.nas.alreem.constants.PDFViewerActivity
import com.nas.alreem.constants.PreferenceManager
import com.nas.alreem.constants.WebLinkActivity
import com.nas.alreem.fragment.payments.model.SendEmailApiModel
import com.nas.alreem.recyclermanager.DividerItemDecoration
import com.nas.alreem.recyclermanager.ItemOffsetDecoration
import com.nas.alreem.recyclermanager.RecyclerItemListener
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class ChatterBoxActivityNew : AppCompatActivity() {
    lateinit var mContext: Context
    var extras: Bundle? = null
    var tab_type: String? = null
    lateinit var descriptionTV: TextView
    lateinit var descriptionTitle: TextView
    lateinit var relativeHeader: RelativeLayout
    lateinit var mtitle: RelativeLayout
    lateinit var headermanager: HeaderManager
    lateinit var back: ImageView
    lateinit var home: ImageView
    lateinit var fbImageView: ImageView
    lateinit var sendEmail: ImageView
    var mTermsCalendarListArray: ArrayList<TermsCalendarModel>? = null

    //    ViewPager bannerImagePager;
    lateinit var bannerImagePager: ImageView
    var bannerUrlImageArray: ArrayList<String>? = null
    private var mChatterBoxListRecyclerView: RecyclerView? = null
    var fbLink: String? = null
    var contactEmail: String? = null
    var text_dialog: EditText? = null
    var text_content: EditText? = null
  //  var progressBarDialog: ProgressBarDialog? = null
    private val EMAIL_PATTERN =
        "^[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-z0-9])?\\.)+[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?$"
    private val pattern = "^([a-zA-Z ]*)$"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatterbox_list_new)
        //		Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this,
//				LoginActivity.class));
        mContext = this
        initialiseUI()
    }

    /*******************************************************
     * Method name : initialiseUI Description : initialise UI elements
     * Parameters : nil Return type : void Date : March 8, 2017 Author : RIJO K JOSE
     */
    @Suppress("deprecation")
    fun initialiseUI() {
        extras = intent.extras
        if (extras != null) {
            tab_type = extras!!.getString("tab_type")
        }
        relativeHeader = findViewById<View>(R.id.relativeHeader) as RelativeLayout
        mChatterBoxListRecyclerView =
            findViewById<View>(R.id.mChatterBoxListRecyclerView) as RecyclerView
        bannerImagePager = findViewById<View>(R.id.bannerImageViewPager) as ImageView
        descriptionTV = findViewById<View>(R.id.descriptionTV) as TextView
        descriptionTitle = findViewById<View>(R.id.descriptionTitle) as TextView
        sendEmail = findViewById<View>(R.id.sendEmail) as ImageView
        mtitle = findViewById<View>(R.id.title) as RelativeLayout
       // progressBarDialog = ProgressBarDialog(mContext, R.drawable.spinner)
        headermanager = HeaderManager(this@ChatterBoxActivityNew, tab_type)
        headermanager.getHeader(relativeHeader, 1)
        back = headermanager.leftButton!!
        fbImageView = headermanager.rightButton!!
        headermanager.setButtonLeftSelector(
            R.drawable.back,
            R.drawable.back
        )
        back!!.setOnClickListener {

            finish()
        }
        home = headermanager.logoButton!!
        home!!.setOnClickListener {
            val `in` = Intent(
                mContext,
                HomeActivity::class.java
            )
            `in`.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(`in`)
        }
        sendEmail!!.setOnClickListener {
            val dialog = Dialog(mContext!!)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.alert_send_email_dialog)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val dialogCancelButton =
                dialog.findViewById<View>(R.id.cancelButton) as Button
            val submitButton =
                dialog.findViewById<View>(R.id.submitButton) as Button
            text_dialog =
                dialog.findViewById<View>(R.id.text_dialog) as EditText
            text_content =
                dialog.findViewById<View>(R.id.text_content) as EditText
         /*   ChatterBoxActivityNew.Companion.progressDialog =
                dialog.findViewById<View>(R.id.progressdialogg) as ProgressBar*/
            dialogCancelButton.setOnClickListener { dialog.dismiss() }
            submitButton.setOnClickListener {
                if (text_dialog!!.text.toString().trim { it <= ' ' } == "") {
                    val toast = Toast.makeText(
                        mContext, mContext!!.resources.getString(
                            R.string.enter_subject
                        ), Toast.LENGTH_SHORT
                    )
                    toast.show()
                } else {
                    if (text_content!!.text.toString().trim { it <= ' ' } == "") {
                        val toast = Toast.makeText(
                            mContext, mContext!!.resources.getString(
                                R.string.enter_content
                            ), Toast.LENGTH_SHORT
                        )
                        toast.show()
                    } else if (contactEmail!!.matches(EMAIL_PATTERN.toRegex())) {
                        if (text_dialog!!.text.toString().trim { it <= ' ' }
                                .matches(pattern.toRegex())) {
                            if (text_content!!.text.toString().trim { it <= ' ' }
                                    .matches(pattern.toRegex())) {
                                if (ConstantFunctions.internetCheck(mContext)) {
                                    sendEmailToStaff(dialog)
                                } else {
                                    DialogFunctions.showInternetAlertDialog(mContext)
                                }
                              /*  if (AppUtils.isNetworkConnected(mContext)) {
                                    sendEmailToStaff(dialog)
                                } else {
                                    AppUtils.showDialogAlertDismiss(
                                        mContext as Activity?,
                                        "Network Error",
                                        getString(R.string.no_internet),
                                        R.drawable.nonetworkicon,
                                        R.drawable.roundred
                                    )
                                }*/
                            } else {
                                val toast = Toast.makeText(
                                    mContext, mContext!!.resources.getString(
                                        R.string.enter_valid_contents
                                    ), Toast.LENGTH_SHORT
                                )
                                toast.show()
                            }
                        } else {
                            val toast = Toast.makeText(
                                mContext, mContext!!.resources.getString(
                                    R.string.enter_valid_subjects
                                ), Toast.LENGTH_SHORT
                            )
                            toast.show()
                        }
                    } else {
                        val toast = Toast.makeText(
                            mContext, mContext!!.resources.getString(
                                R.string.enter_valid_email
                            ), Toast.LENGTH_SHORT
                        )
                        toast.show()
                    }
                }
            }
            dialog.show()
        }
        fbImageView!!.setOnClickListener {
            val mintent = Intent(
                this@ChatterBoxActivityNew,
                FullscreenWebViewActivityNoHeader::class.java
            )
            mintent.putExtra("url", fbLink)
            startActivity(mintent)
        }
        mChatterBoxListRecyclerView!!.setHasFixedSize(true)
        val llm = LinearLayoutManager(mContext)
        llm.orientation = LinearLayoutManager.VERTICAL
        val spacing = 5 // 50px
        val itemDecoration = ItemOffsetDecoration(mContext, spacing)
        mChatterBoxListRecyclerView!!.addItemDecoration(itemDecoration)
        mChatterBoxListRecyclerView!!.layoutManager = llm
        mChatterBoxListRecyclerView!!.addItemDecoration(
            DividerItemDecoration(
                resources.getDrawable(
                    R.drawable.list_divider
                )
            )
        )
        mChatterBoxListRecyclerView!!.addOnItemTouchListener(
            RecyclerItemListener(
                applicationContext, mChatterBoxListRecyclerView!!,
                object : RecyclerItemListener.RecyclerTouchListener {
                    override fun onClickItem(v: View?, position: Int) {
                        if (mTermsCalendarListArray!![position].getmFileName().endsWith(".pdf")) {
                            val intent = Intent(mContext, PDFViewerActivity::class.java)
                            intent.putExtra("Url", mTermsCalendarListArray!![position].getmFileName())
                            intent.putExtra("title", mTermsCalendarListArray!![position].getmTitle())
                            startActivity(intent)
                        } else {
                            val intent = Intent(mContext, WebLinkActivity::class.java)
                            intent.putExtra("url", mTermsCalendarListArray!![position].getmFileName())
                            intent.putExtra("heading", mTermsCalendarListArray!![position].getmTitle())
                            startActivity(intent)
                        }
                    }

                    override fun onLongClickItem(v: View?, position: Int) {}
                })
        )
        if (ConstantFunctions.internetCheck(mContext)) {
            chatterBoxList()
        } else {
            DialogFunctions.showInternetAlertDialog(mContext)
        }
       /* if (AppUtils.checkInternet(mContext)) {
            chatterBoxList
            //            getList();
        } else {
            AppUtils.showDialogAlertDismiss(
                mContext as Activity?,
                "Network Error",
                getString(R.string.no_internet),
                R.drawable.nonetworkicon,
                R.drawable.roundred
            )
        }*/
    }

    private fun chatterBoxList()
       {

           val call: Call<ChatterBoxResponseModel> = ApiClient.getClient.chatter_box("Bearer " + PreferenceManager.getaccesstoken(mContext))
           call.enqueue(object : Callback<ChatterBoxResponseModel> {
               override fun onFailure(call: Call<ChatterBoxResponseModel>, t: Throwable) {
                  // progressDialogAdd.visibility=View.GONE
               }
               override fun onResponse(call: Call<ChatterBoxResponseModel>, response: Response<ChatterBoxResponseModel>) {
                   val responsedata = response.body()
                 //  progressDialogAdd.visibility=View.GONE
                   if (response.isSuccessful()) {
                       Log.e("res", response.toString())
                       val apiResponse: ChatterBoxResponseModel? = response.body()
                       Log.e("response", java.lang.String.valueOf(apiResponse))
                       println("response$apiResponse")
                       val response_code: Int = (apiResponse!!.getResponseCode())
                       if (response_code == 100) {
                           val statuscode: String = java.lang.String.valueOf(
                               response.body()!!.getResponse().getStatusCode()
                           )
                           Log.e("statuscode", statuscode)
                               val bannerImage: String =
                                   response.body()!!.getResponse().getBannerImage()
                               fbLink = response.body()!!.getResponse().getFacebookUrl()
                               val description: String =
                                   response.body()!!.getResponse().getDescription()
                               contactEmail = response.body()!!.getResponse().getContactEmail()
                               if (fbLink != "") {
                                   headermanager.setButtonRightSelector(
                                       R.drawable.facebookshare,
                                       R.drawable.facebookshare
                                   )
                               } else {
                                   fbImageView!!.visibility = View.GONE
                               }
                               if (!bannerImage.equals("", ignoreCase = true)) {
                                   Glide.with(mContext!!).load(ConstantFunctions.replace(bannerImage))
                                       .placeholder(R.drawable.default_banner).centerCrop()
                                       .into(bannerImagePager)
                               } else {
                                   bannerImagePager!!.setBackgroundResource(R.drawable.chatterbox_banner)
                               }
                               if (description.equals(
                                       "",
                                       ignoreCase = true
                                   ) && contactEmail.equals("", ignoreCase = true)
                               ) {
                                   mtitle!!.visibility = View.GONE
                               } else {
                                   mtitle!!.visibility = View.VISIBLE
                               }
                               if (description.equals("", ignoreCase = true)) {
                                   descriptionTV!!.visibility = View.GONE
                                   descriptionTitle!!.visibility = View.GONE
                               } else {
                                   descriptionTV!!.text = description
                                   descriptionTitle!!.visibility = View.GONE
                                   descriptionTV!!.visibility = View.VISIBLE
                                   mtitle!!.visibility = View.VISIBLE
                               }
                               if (contactEmail.equals("", ignoreCase = true)) {
                                   sendEmail!!.visibility = View.GONE
                               } else {
                                   mtitle!!.visibility = View.VISIBLE
                                   sendEmail!!.visibility = View.VISIBLE
                               }
                               mTermsCalendarListArray = ArrayList<TermsCalendarModel>()
                               if (response.body()!!.getResponse().getEventDataList().size > 0) {
                                   for (i in 0 until response.body()!!.getResponse()
                                       .getEventDataList().size) {
                                       val item: ChatterBoxResponseModel.EventData =
                                           response.body()!!.getResponse().getEventDataList().get(i)
                                       val gson = Gson()
                                       val eventJson = gson.toJson(item)
                                       Log.e("item", eventJson)
                                       try {
                                           val jsonObject = JSONObject(eventJson)
                                           mTermsCalendarListArray!!.add(getSearchValues(jsonObject))
                                           //  Log.e("array", String.valueOf(mCCAmodelArrayList));
                                       } catch (e: JSONException) {
                                           e.printStackTrace()
                                       }
                                   }
                                   mChatterBoxListRecyclerView!!.adapter =
                                       ChatterBoxRecyclerAdapterNew(
                                           mContext,
                                           mTermsCalendarListArray!!
                                       )
                               } else {
                                  /* AppUtils.showDialogAlertDismiss(
                                       mContext as Activity?,
                                       "Alert",
                                       getString(R.string.nodatafound),
                                       R.drawable.exclamationicon,
                                       R.drawable.round
                                   )*/
                               }

                       }   else {
                           /*AppUtils.showDialogAlertDismiss(
                               mContext as Activity?,
                               "Alert",
                               getString(R.string.common_error),
                               R.drawable.exclamationicon,
                               R.drawable.round
                           )*/
                       }
                   }
               }

           })
            /*val service: APIInterface =
                APIClient.getRetrofitInstance().create(APIInterface::class.java)
            val call: Call<ChatterBoxResponseModel> =
                service.postChatterBoxList("Bearer " + PreferenceManager.getAccessToken(mContext))
            progressBarDialog.show()
            call.enqueue(object : Callback<ChatterBoxResponseModel> {
                override fun onResponse(
                    call: Call<ChatterBoxResponseModel>,
                    response: Response<ChatterBoxResponseModel>
                ) {
                    progressBarDialog.hide()
                    if (response.isSuccessful()) {
                        Log.e("res", response.toString())
                        val apiResponse: ChatterBoxResponseModel? = response.body()
                        Log.e("response", java.lang.String.valueOf(apiResponse))
                        println("response$apiResponse")
                        val response_code: String =
                            java.lang.String.valueOf(apiResponse.getResponseCode())
                        if (response_code == "200") {
                            val statuscode: String = java.lang.String.valueOf(
                                response.body().getResponse().getStatusCode()
                            )
                            Log.e("statuscode", statuscode)
                            if (statuscode == "303") {
                                val bannerImage: String =
                                    response.body().getResponse().getBannerImage()
                                fbLink = response.body().getResponse().getFacebookUrl()
                                val description: String =
                                    response.body().getResponse().getDescription()
                                contactEmail = response.body().getResponse().getContactEmail()
                                if (fbLink != "") {
                                    headermanager.setButtonRightSelector(
                                        R.drawable.facebookshare,
                                        R.drawable.facebookshare
                                    )
                                } else {
                                    fbImageView!!.visibility = View.GONE
                                }
                                if (!bannerImage.equals("", ignoreCase = true)) {
                                    Glide.with(mContext!!).load(AppUtils.replace(bannerImage))
                                        .placeholder(R.drawable.default_banner).centerCrop()
                                        .into(bannerImagePager)
                                } else {
                                    bannerImagePager!!.setBackgroundResource(R.drawable.chatterbox_banner)
                                }
                                if (description.equals(
                                        "",
                                        ignoreCase = true
                                    ) && contactEmail.equals("", ignoreCase = true)
                                ) {
                                    mtitle!!.visibility = View.GONE
                                } else {
                                    mtitle!!.visibility = View.VISIBLE
                                }
                                if (description.equals("", ignoreCase = true)) {
                                    descriptionTV!!.visibility = View.GONE
                                    descriptionTitle!!.visibility = View.GONE
                                } else {
                                    descriptionTV!!.text = description
                                    descriptionTitle!!.visibility = View.GONE
                                    descriptionTV!!.visibility = View.VISIBLE
                                    mtitle!!.visibility = View.VISIBLE
                                }
                                if (contactEmail.equals("", ignoreCase = true)) {
                                    sendEmail!!.visibility = View.GONE
                                } else {
                                    mtitle!!.visibility = View.VISIBLE
                                    sendEmail!!.visibility = View.VISIBLE
                                }
                                mTermsCalendarListArray = ArrayList<TermsCalendarModel>()
                                if (response.body().getResponse().getEventDataList().size() > 0) {
                                    for (i in 0 until response.body().getResponse()
                                        .getEventDataList().size()) {
                                        val item: ChatterBoxResponseModel.EventData =
                                            response.body().getResponse().getEventDataList().get(i)
                                        val gson = Gson()
                                        val eventJson = gson.toJson(item)
                                        Log.e("item", eventJson)
                                        try {
                                            val jsonObject = JSONObject(eventJson)
                                            mTermsCalendarListArray!!.add(getSearchValues(jsonObject))
                                            //  Log.e("array", String.valueOf(mCCAmodelArrayList));
                                        } catch (e: JSONException) {
                                            e.printStackTrace()
                                        }
                                    }
                                    mChatterBoxListRecyclerView!!.adapter =
                                        ChatterBoxRecyclerAdapterNew(
                                            mContext,
                                            mTermsCalendarListArray
                                        )
                                } else {
                                    AppUtils.showDialogAlertDismiss(
                                        mContext as Activity?,
                                        "Alert",
                                        getString(R.string.nodatafound),
                                        R.drawable.exclamationicon,
                                        R.drawable.round
                                    )
                                }
                            }
                        } else if (response.body().getResponseCode()
                                .equalsIgnoreCase(RESPONSE_ACCESSTOKEN_MISSING) ||
                            response.body().getResponseCode()
                                .equalsIgnoreCase(RESPONSE_ACCESSTOKEN_EXPIRED) ||
                            response.body().getResponseCode()
                                .equalsIgnoreCase(RESPONSE_INVALID_TOKEN)
                        ) {
                            AppUtils.postInitParam(mContext, object : GetAccessTokenInterface() {
                                val accessToken: Unit
                                    get() {}
                            })
                            chatterBoxList
                        } else if (response.body().getResponseCode().equals(RESPONSE_ERROR)) {
                            //								CustomStatusDialog(RESPONSE_FAILURE);
                            //Toast.makeText(mContext,"Failure", Toast.LENGTH_SHORT).show();
                            AppUtils.showDialogAlertDismiss(
                                mContext as Activity?,
                                "Alert",
                                getString(R.string.common_error),
                                R.drawable.exclamationicon,
                                R.drawable.round
                            )
                        } else {
                            AppUtils.showDialogAlertDismiss(
                                mContext as Activity?,
                                "Alert",
                                getString(R.string.common_error),
                                R.drawable.exclamationicon,
                                R.drawable.round
                            )
                        }
                    }
                }

                override fun onFailure(call: Call<ChatterBoxResponseModel>, t: Throwable) {
                    AppUtils.showDialogAlertDismiss(
                        mContext as Activity?,
                        "Alert",
                        getString(R.string.common_error),
                        R.drawable.exclamationicon,
                        R.drawable.round
                    )
                }
            }
            )*/
        }


    @Throws(JSONException::class)
    private fun getSearchValues(Object: JSONObject): TermsCalendarModel {
        val mTermsCalendarModel = TermsCalendarModel()
        mTermsCalendarModel.setmId(Object.getString("id"))
        mTermsCalendarModel.setmFileName(Object.getString("file"))
        mTermsCalendarModel.setmTitle(Object.getString("title"))
        return mTermsCalendarModel
    }

    private fun appInstalledOrNot(packagename: String): Boolean {
        return try {
            val info = packageManager.getApplicationInfo(packagename, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

    private fun sendEmailToStaff(dialog: Dialog) {
        val sendMailBody = SendEmailApiModel(contactEmail!!, text_dialog!!.text.toString(), text_content!!.text.toString())
        val call: Call<SignUpResponseModel> = ApiClient.getClient.sendEmailStaff(sendMailBody, "Bearer " + PreferenceManager.getaccesstoken(mContext!!))
        call.enqueue(object : Callback<SignUpResponseModel> {
            override fun onFailure(call: Call<SignUpResponseModel>, t: Throwable) {
                //progressDialog.visibility = View.GONE
            }

            override fun onResponse(call: Call<SignUpResponseModel>, response: Response<SignUpResponseModel>) {
                val responsedata = response.body()
                //progressDialog.visibility = View.GONE
                if (responsedata != null) {
                    try {


                        if (response.body()!!.status==100) {
                            dialog.dismiss()
                            showSuccessAlert(
                                mContext!!,
                                "Email sent Successfully ",
                                "Success",
                                dialog
                            )
                        }else {
                            DialogFunctions.commonErrorAlertDialog(
                                mContext!!.resources.getString(R.string.alert),
                                ConstantFunctions.commonErrorString(response.body()!!.status), mContext!!
                            )

                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

        })
       /* ChatterBoxActivityNew.Companion.progressDialog.setVisibility(View.VISIBLE)
        val service: APIInterface = APIClient.getRetrofitInstance().create(APIInterface::class.java)
        val paramObject = JsonObject()
        paramObject.addProperty("email", contactEmail)
        paramObject.addProperty("title", text_dialog!!.text.toString())
        paramObject.addProperty("message", text_content!!.text.toString())
        val call: Call<SendMailStaffResponseModel> = service.sendmailtostaff(
            "Bearer " + PreferenceManager.getAccessToken(mContext),
            paramObject
        )
        call.enqueue(object : Callback<SendMailStaffResponseModel?> {
            override fun onResponse(
                call: Call<SendMailStaffResponseModel?>,
                response: Response<SendMailStaffResponseModel?>
            ) {
                if (response.isSuccessful()) {
                    ChatterBoxActivityNew.Companion.progressDialog.setVisibility(View.GONE)
                    val apiResponse: SendMailStaffResponseModel? = response.body()
                    // Log.e("response", String.valueOf(apiResponse));
                    //  System.out.println("response" + apiResponse);
                    val statuscode: String = apiResponse.getResponse().getStatuscode()
                    if (statuscode == "303") {
                        dialog.dismiss()
                        val toast = Toast.makeText(
                            mContext, mContext!!.resources.getString(
                                R.string.successfully_send_email_staff
                            ), Toast.LENGTH_SHORT
                        )
                        toast.show()
                    } else {
                        val toast = Toast.makeText(
                            mContext, mContext!!.resources.getString(
                                R.string.email_not_staff
                            ), Toast.LENGTH_SHORT
                        )
                        toast.show()
                    }
                }
            }

            override fun onFailure(call: Call<SendMailStaffResponseModel?>, t: Throwable) {
                //  Log.e("toadst", String.valueOf(t));
                ChatterBoxActivityNew.Companion.progressDialog.setVisibility(View.GONE)
                Toast.makeText(
                    mContext, mContext!!.resources.getString(
                        R.string.common_error
                    ), Toast.LENGTH_SHORT
                )
            }
        })*/
    }
    fun showSuccessAlert(context: Context, message: String, msgHead: String, mdialog: Dialog) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_common_error_alert)
        var iconImageView = dialog.findViewById(R.id.iconImageView) as ImageView
        var alertHead = dialog.findViewById(R.id.alertHead) as TextView
        var text_dialog = dialog.findViewById(R.id.messageTxt) as TextView
        var btn_Ok = dialog.findViewById(R.id.btn_Ok) as Button
        text_dialog.text = message
        alertHead.text = msgHead
        iconImageView.setImageResource(R.drawable.tick)
        btn_Ok.setOnClickListener()
        {
            dialog.dismiss()
            mdialog.dismiss()
        }
        dialog.show()
    }
    companion object {
        private val progressDialog: ProgressBar? = null
    }
}
