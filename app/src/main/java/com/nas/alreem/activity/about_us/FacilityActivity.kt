package com.nas.alreem.activity.about_us

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nas.alreem.R
import com.nas.alreem.activity.about_us.adapter.FacilityRecyclerAdapterNew
import com.nas.alreem.activity.home.HomeActivity
import com.nas.alreem.activity.login.model.SignUpResponseModel
import com.nas.alreem.constants.ApiClient
import com.nas.alreem.constants.ConstantFunctions
import com.nas.alreem.constants.DialogFunctions
import com.nas.alreem.constants.PDFViewerActivity
import com.nas.alreem.constants.PreferenceManager
import com.nas.alreem.constants.WebLinkActivity
import com.nas.alreem.fragment.about_us.model.AboutUsDataModel
import com.nas.alreem.fragment.about_us.model.AboutusList
import com.nas.alreem.fragment.payments.model.SendEmailApiModel
import com.nas.alreem.recyclermanager.DividerItemDecoration
import com.nas.alreem.recyclermanager.ItemOffsetDecoration
import com.nas.alreem.recyclermanager.RecyclerItemListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FacilityActivity : Activity(){
    var extras: Bundle? = null
    var aboutusModelArrayList: ArrayList<AboutUsDataModel> = ArrayList<AboutUsDataModel>()
    lateinit var  mContext: Context
    lateinit var heading: TextView

    //    private GridView mAboutUsList;
    private var mAboutUsList: RecyclerView? = null
    private val mBannerImage: ImageView? = null
    private var sendEmail: ImageView? = null
    var mTitleTextView: TextView? = null
    var weburl: TextView? = null
    var description: TextView? = null
    var descriptionTitle: TextView? = null
    lateinit var logoClickImgView: ImageView

    //	private CustomAboutUsAdapter mAdapter;
    private var mAboutUsListArray: ArrayList<AboutusList>? = null
    var relMain: RelativeLayout? = null
    var desc: String? = null
    var title: String? = null
    var bannerimg: String? = null
    lateinit var bannerImagePager: ImageView
    lateinit var backRelative: RelativeLayout

    //    ViewPager bannerImagePager;
    var relativeHeader: RelativeLayout? = null

    var back: ImageView? = null
    var home: ImageView? = null
    var bannerUrlImageArray = ArrayList<String?>()
    lateinit var contactEmail: String
    var text_dialog: EditText? = null
    var text_content: EditText? = null
    private val EMAIL_PATTERN =
        "^[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-z0-9])?\\.)+[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?$"
    private val pattern = "^([a-zA-Z ]*)$"
    lateinit var activity: Activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_facility_grid)
        mContext=this
        activity = this

        extras = intent.extras
        initUI()
    }

    private fun initUI() {
        if (extras != null) {
            mAboutUsListArray = PreferenceManager.getAboutsArrayList(mContext)
            desc = extras!!.getString("desc")
            title = extras!!.getString("title")
            bannerimg = extras!!.getString("banner_image")
            if (bannerimg != "") {
                bannerUrlImageArray.add(bannerimg)
            }
        }
        relativeHeader = findViewById<View>(R.id.relativeHeader) as RelativeLayout
        bannerImagePager = findViewById<View>(R.id.bannerImagePager) as ImageView
        //        bannerImagePager= (ViewPager) findViewById(R.id.bannerImagePager);
        heading=findViewById(R.id.heading)
        heading.text= title


        backRelative=findViewById(R.id.backRelative)
        logoClickImgView=findViewById(R.id.logoClickImgView)

        backRelative!!.setOnClickListener {
            //AppUtils.hideKeyBoard(mContext)
            finish()
        }

        logoClickImgView!!.setOnClickListener {
            val `in` = Intent(
                mContext,
                HomeActivity::class.java
            )
            `in`.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(`in`)
        }
        //        mAboutUsList = (GridView) findViewById(R.id.mAboutUsListView);
        mAboutUsList = findViewById<View>(R.id.mAboutUsListView) as RecyclerView
        description = findViewById<View>(R.id.descriptionTV) as TextView
        descriptionTitle = findViewById<View>(R.id.descriptionTitle) as TextView
        if (desc.equals("", ignoreCase = true)) {
            description!!.visibility = View.GONE
            descriptionTitle!!.visibility = View.GONE
        } else {
            description!!.text = desc
            descriptionTitle!!.visibility = View.GONE
            //			descriptionTitle.setVisibility(View.VISIBLE);
            description!!.visibility = View.VISIBLE
        }
        weburl = findViewById<View>(R.id.weburl) as TextView
        sendEmail = findViewById<View>(R.id.sendEmail) as ImageView
        sendEmail!!.visibility = View.GONE

//		mBannerImage = (ImageView) mRootView.findViewById(R.id.bannerImageView);
        /*relMain = (RelativeLayout) findViewById(R.id.relMain);
        relMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/

/* mAbsenceListView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(mContext);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mAbsenceListView.setLayoutManager(llm);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(mContext, 2);
        mAbsenceListView.addItemDecoration(itemDecoration);*/mAboutUsList!!.setHasFixedSize(true)
        //        mNewsLetterListView.addItemDecoration(new DividerItemDecoration(getResources().getDrawable(R.drawable.list_divider)));
        /*    GridLayoutManager recyclerViewLayout= new GridLayoutManager(mContext, 4);
        int spacing = 5; // 50px
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(mContext,spacing);
        mAboutUsList.addItemDecoration(itemDecoration);
        mAboutUsList.setLayoutManager(recyclerViewLayout);*/
        val llm = LinearLayoutManager(mContext)
        llm.orientation = LinearLayoutManager.VERTICAL
        mAboutUsList!!.layoutManager = llm
        mAboutUsList!!.addItemDecoration(DividerItemDecoration(resources.getDrawable(R.drawable.list_divider_teal)))
        val itemDecoration = ItemOffsetDecoration(mContext, 2)
        mAboutUsList!!.addItemDecoration(itemDecoration)
        if (bannerimg != "") {
//            bannerImagePager.setAdapter(new ImagePagerDrawableAdapter(bannerUrlImageArray, mContext));
            Glide.with(mContext).load(ConstantFunctions.replace(bannerimg!!)).centerCrop()
                .into(bannerImagePager)
        }
        val mFacilityRecyclerAdapter = FacilityRecyclerAdapterNew(mContext, mAboutUsListArray!!)
        mAboutUsList!!.adapter = mFacilityRecyclerAdapter
        mAboutUsList!!.addOnItemTouchListener(
            RecyclerItemListener(
                applicationContext, mAboutUsList!!,
                object : RecyclerItemListener.RecyclerTouchListener {
                    override fun onClickItem(v: View?, position: Int) {
                        if (mAboutUsListArray!!.size <= 1) {
                            if (mAboutUsListArray!![position].url.endsWith(".pdf")) {
                                val intent = Intent(
                                    mContext,
                                    PDFViewerActivity::class.java
                                )
                                intent.putExtra("Url", mAboutUsListArray!![position].url)
                                intent.putExtra("title", mAboutUsListArray!![position].title)
                                startActivity(intent)
                            } else {
                                val intent = Intent(
                                    mContext,
                                    WebLinkActivity::class.java
                                )
                                intent.putExtra("url", mAboutUsListArray!![position].url)
                                intent.putExtra("heading",  mAboutUsListArray!![position].title)
                                startActivity(intent)
                            }
                        } else {
                            if (mAboutUsListArray!![position].url.endsWith(".pdf")) {
                                val intent = Intent(
                                    mContext,
                                    PDFViewerActivity::class.java
                                )
                                intent.putExtra("Url", mAboutUsListArray!![position].url)
                                intent.putExtra("title", mAboutUsListArray!![position].title)
                                startActivity(intent)
                            } else {
                                val intent = Intent(
                                    mContext,
                                    WebLinkActivity::class.java
                                )
                                intent.putExtra("url", mAboutUsListArray!![position].url)
                                intent.putExtra("heading",  mAboutUsListArray!![position].title)
                                startActivity(intent)
                            }
                        }
                    }

                    override fun onLongClickItem(v: View?, position: Int) {
                    }
                })
        )
        //        mAboutUsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//               if(mAboutUsListArray.get(position).getItemPdfUrl().endsWith(".pdf")){
//                   Intent intent = new Intent(mContext, PdfReaderActivity.class);
//                   intent.putExtra("pdf_url",mAboutUsListArray.get(position).getItemPdfUrl());
//                   startActivity(intent);
//               }
//               else
//               {
//                   Intent intent = new Intent(mContext, LoadUrlWebViewActivity.class);
//                   intent.putExtra("url",mAboutUsListArray.get(position).getItemPdfUrl());
//                   intent.putExtra("tab_type",mAboutUsListArray.get(position).getItemTitle());
//                   startActivity(intent);
//               }
//            }
//        });
        sendEmail!!.setOnClickListener {
            showSendEmailDialog(mContext!!)
        }
    }


    private fun showSendEmailDialog(mContext: Context)
    {
        val dialog = Dialog(mContext)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dialog_send_email)
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        val btn_submit = dialog.findViewById<Button>(R.id.submitButton)
        val btn_cancel = dialog.findViewById<Button>(R.id.cancelButton)
        val text_dialog = dialog.findViewById<EditText?>(R.id.text_dialog)
        val text_content = dialog.findViewById<EditText>(R.id.text_content)

        btn_cancel.setOnClickListener(View.OnClickListener {
            dialog.dismiss()
        })

        btn_submit.setOnClickListener {
            if (text_dialog.text.toString().trim().equals("")) {
                DialogFunctions.commonErrorAlertDialog(
                    mContext.resources.getString(R.string.alert), resources.getString(R.string.enter_subject),
                    mContext
                )


            } else {
                if (text_content.text.toString().trim().equals("")) {
                    DialogFunctions.commonErrorAlertDialog(
                        mContext.resources.getString(R.string.alert), resources.getString(R.string.enter_content),
                        mContext
                    )

                } else if (contactEmail.matches(EMAIL_PATTERN.toRegex())) {
                    if (text_dialog.text.toString().trim ().matches(pattern.toRegex())) {
                        if (text_content.text.toString().trim ()
                                .matches(pattern.toRegex())) {

                            if (ConstantFunctions.internetCheck(mContext))
                            {
                                callSendEmailToStaffApi(text_dialog.text.toString().trim(), text_content.text.toString().trim(),
                                    contactEmail!!, dialog)
                            }
                            else
                            {
                                DialogFunctions.showInternetAlertDialog(mContext)
                            }

                        } else {
                            val toast: Toast = Toast.makeText(mContext, mContext.getResources().getString(R.string.enter_valid_contents), Toast.LENGTH_SHORT)
                            toast.show()
                        }
                    } else {
                        val toast: Toast = Toast.makeText(
                           mContext,
                           mContext.getResources()
                                .getString(
                                    R.string.enter_valid_subjects
                                ),
                            Toast.LENGTH_SHORT
                        )
                        toast.show()
                    }
                } else {
                    val toast: Toast = Toast.makeText(
                        mContext,
                       mContext.getResources()
                            .getString(
                                R.string.enter_valid_email
                            ),
                        Toast.LENGTH_SHORT
                    )
                    toast.show()
                }
            }
        }
        dialog.show()
    }
    fun callSendEmailToStaffApi(
        title: String, message: String, staffEmail: String, dialog: Dialog)
    {
        val sendMailBody = SendEmailApiModel( staffEmail, title, message)
        val call: Call<SignUpResponseModel> = ApiClient(mContext).getClient.sendEmailStaff(sendMailBody, "Bearer " + PreferenceManager.getaccesstoken(mContext!!))
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
    override fun onResume() {
        super.onResume()
        if (!ConstantFunctions.runMethod.equals("Dev")) {
            if (ConstantFunctions().isDeveloperModeEnabled(mContext)) {
                ConstantFunctions().showDeviceIsDeveloperPopUp(activity)
            }
        }
    }
}