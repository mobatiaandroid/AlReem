package com.nas.alreem.activity.parent_engagement

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.nas.alreem.activity.parent_engagement.model.ParentAssociationEventsModel
import com.nas.alreem.constants.ConstantFunctions
import com.nas.alreem.constants.DialogFunctions
import com.nas.alreem.constants.HeaderManager
import com.nas.alreem.constants.PDFViewerActivity
import com.nas.alreem.constants.WebLinkActivity
import com.nas.alreem.recyclermanager.DividerItemDecoration
import com.nas.alreem.recyclermanager.ItemOffsetDecoration
import com.nas.alreem.recyclermanager.RecyclerItemListener
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/*
class ParentsAssociationSignUpActivity :AppCompatActivity(){
    lateinit var mContext: Context
    lateinit var relativeHeader: RelativeLayout
    lateinit var signRelative: RelativeLayout
    lateinit var headermanager: HeaderManager
    lateinit var back: ImageView
    lateinit var home:ImageView
    lateinit var imageViewSlotInfo:ImageView
    lateinit var sendEmail:ImageView
    lateinit var mRecyclerView: RecyclerView
    var extras: Bundle? = null
    var tab_type: String? = null
    var signUpModule: TextView? = null
    var descriptionTV: TextView? = null
    var descriptionTitle: TextView? = null
    var bannerUrlImageArray: ArrayList<String>? = null
    var mtitle: RelativeLayout? = null
    var bannerImagePager: ImageView? = null

    //    ViewPager bannerImagePager;
    var text_content: TextView? = null
    var text_dialog: TextView? = null
    private var progressDialog: ProgressBar? = null

    var description = ""
    var contactEmail = ""
    var parentAssociationEventsModelsArrayList: ArrayList<ParentAssociationEventsModel> =
        ArrayList<ParentAssociationEventsModel>()

   // var progressBarDialog: ProgressBarDialog? = null
    private val EMAIL_PATTERN =
        "^[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-z0-9])?\\.)+[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?$"
    private val pattern = "^([a-zA-Z ]*)$"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parents_association_firstpage)
        initUI()

        if (ConstantFunctions.internetCheck(mContext)) {
            callStaffDirectoryList()
        } else {
            DialogFunctions.showInternetAlertDialog(mContext)
        }

    }


    private fun initUI() {
        extras = intent.extras
        if (extras != null) {
            tab_type = extras!!.getString("tab_type")
        }
        relativeHeader = findViewById<View>(R.id.relativeHeader) as RelativeLayout
        signRelative = findViewById<View>(R.id.signRelative) as RelativeLayout
        mtitle = findViewById<View>(R.id.title) as RelativeLayout
        //        bannerImagePager= (ViewPager) findViewById(R.id.bannerImageViewPager);
        bannerImagePager = findViewById<View>(R.id.bannerImageViewPager) as ImageView
        mRecyclerView = findViewById<View>(R.id.mStaffDirectoryListView) as RecyclerView
        signUpModule = findViewById<View>(R.id.signUpModule) as TextView
        descriptionTV = findViewById<View>(R.id.descriptionTV) as TextView
        descriptionTitle = findViewById<View>(R.id.descriptionTitle) as TextView
        sendEmail = findViewById<View>(R.id.sendEmail) as ImageView
        headermanager = HeaderManager(this@ParentsAssociationSignUpActivity, tab_type)
        headermanager.getHeader(relativeHeader, 0)
        back = headermanager.leftButton!!
      //  progressBarDialog = ProgressBarDialog(mContext, R.drawable.spinner)
        //mRecyclerView.addItemDecoration(new DividerItemDecoration(getResources().getDrawable(R.drawable.list_divider)));
        //mRecyclerView.setHasFixedSize(true);
        */
/* GridLayoutManager recyclerViewLayout= new GridLayoutManager(mContext, 4);
        int spacing = 5; // 50px
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(mContext,spacing);
        mRecyclerView.addItemDecoration(itemDecoration);
        mRecyclerView.setLayoutManager(recyclerViewLayout);*//*
mRecyclerView!!.setHasFixedSize(true)
        val llm = LinearLayoutManager(mContext)
        llm.orientation = LinearLayoutManager.VERTICAL
        val spacing = 5 // 50px
        val itemDecoration = ItemOffsetDecoration(mContext, spacing)
        mRecyclerView!!.addItemDecoration(itemDecoration)
        mRecyclerView!!.layoutManager = llm
        mRecyclerView!!.addItemDecoration(DividerItemDecoration(resources.getDrawable(R.drawable.list_divider_teal)))
        headermanager.setButtonLeftSelector(
            R.drawable.back,
            R.drawable.back
        )
        back!!.setOnClickListener {

            finish()
        }
        home = headermanager.logoButton!!
        home.setOnClickListener(View.OnClickListener {
            val `in` = Intent(
                mContext,
                HomeActivity::class.java
            )
            `in`.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(`in`)
        })
        signRelative!!.setOnClickListener {
            val intent = Intent(
                mContext,
                ParentsAssociationListActivity::class.java
            )
            intent.putExtra("tab_type", "Volunteer Sign Up")
            startActivity(intent)
        }
        mRecyclerView!!.addOnItemTouchListener(
            RecyclerItemListener(
                applicationContext, mRecyclerView,
                object : RecyclerItemListener.RecyclerTouchListener {
                    override fun onClickItem(v: View?, position: Int) {
                        if (parentAssociationEventsModelsArrayList.size > 0) {
                            if (parentAssociationEventsModelsArrayList[position].getPdfUrl()
                                    .endsWith(".pdf")
                            ) {
                                val intent = Intent(
                                    mContext,
                                    PDFViewerActivity::class.java
                                )
                                intent.putExtra(
                                    "pdf_url",
                                    parentAssociationEventsModelsArrayList[position].getPdfUrl()
                                )
                                startActivity(intent)
                            } else {
                                val intent = Intent(
                                    mContext,
                                    WebLinkActivity::class.java
                                )
                                intent.putExtra(
                                    "url",
                                    parentAssociationEventsModelsArrayList[position].getPdfUrl()
                                )
                                intent.putExtra("tab_type", tab_type)
                                startActivity(intent)
                            }
                        }
                    }

                    override fun    onLongClickItem(v: View?, position: Int) {
//                        System.out.println("On Long Click Item interface");
                    }
                })
        )
        sendEmail.setOnClickListener(View.OnClickListener {
            val dialog = Dialog(mContext)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.alert_send_email_dialog)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val dialogCancelButton = dialog.findViewById<View>(R.id.cancelButton) as Button
            val submitButton = dialog.findViewById<View>(R.id.submitButton) as Button
            text_dialog = dialog.findViewById<View>(R.id.text_dialog) as EditText
            text_content = dialog.findViewById<View>(R.id.text_content) as EditText
           // progressDialog = dialog.findViewById<View>(R.id.progressdialogg) as ProgressBar
            dialogCancelButton.setOnClickListener { dialog.dismiss() }
            submitButton.setOnClickListener {
                if (text_dialog!!.text.toString().trim { it <= ' ' } == "") {
                    val toast = Toast.makeText(
                        mContext, mContext.resources.getString(
                            R.string.enter_subject
                        ), Toast.LENGTH_SHORT
                    )
                    toast.show()
                } else {
                    if (text_content!!.text.toString().trim { it <= ' ' } == "") {
                        val toast = Toast.makeText(
                            mContext, mContext.resources.getString(
                                R.string.enter_content
                            ), Toast.LENGTH_SHORT
                        )
                        toast.show()
                    } else if (contactEmail.matches(EMAIL_PATTERN.toRegex())) {
                        if (text_dialog!!.text.toString().trim { it <= ' ' }
                                .matches(pattern.toRegex())) {
                            if (text_content!!.text.toString().trim { it <= ' ' }
                                    .matches(pattern.toRegex())) {
                                if (ConstantFunctions.internetCheck(mContext)) {
                                    sendEmailToStaff(dialog)
                                } else {
                                    DialogFunctions.showInternetAlertDialog(mContext)
                                }

                            } else {
                                val toast = Toast.makeText(
                                    mContext, mContext.resources.getString(
                                        R.string.enter_valid_contents
                                    ), Toast.LENGTH_SHORT
                                )
                                toast.show()
                            }
                        } else {
                            val toast = Toast.makeText(
                                mContext, mContext.resources.getString(
                                    R.string.enter_valid_subjects
                                ), Toast.LENGTH_SHORT
                            )
                            toast.show()
                        }
                    } else {
                        val toast = Toast.makeText(
                            mContext, mContext.resources.getString(
                                R.string.enter_valid_email
                            ), Toast.LENGTH_SHORT
                        )
                        toast.show()
                    }
                }
            }
            dialog.show()
        })
    }

    private fun callStaffDirectoryList() {
        */
/*val service: APIInterface = APIClient.getRetrofitInstance().create(APIInterface::class.java)
        val call: Call<ParentAssociationResponseModel> =
            service.postParentAssociation("Bearer " + PreferenceManager.getAccessToken(mContext))
        progressBarDialog.show()
        call.enqueue(object : Callback<ParentAssociationResponseModel> {
            override fun onResponse(
                call: Call<ParentAssociationResponseModel>,
                response: Response<ParentAssociationResponseModel>
            ) {
                progressBarDialog.hide()
                if (response.isSuccessful()) {
//                    Log.e("res", response.toString());
                    val apiResponse: ParentAssociationResponseModel? = response.body()
                    //                    Log.e("response", String.valueOf(apiResponse));
//                    System.out.println("response" + apiResponse);
                    val response_code: String =
                        java.lang.String.valueOf(apiResponse.getResponseCode())
                    if (response_code == "200") {
                        val statuscode: String =
                            java.lang.String.valueOf(response.body().getResponse().getStatusCode())
                        //                        Log.e("statuscode", statuscode);
                        if (statuscode == "303") {
                            val bannerImage: String = response.body().getResponse().getBannerImage()
                            description = response.body().getResponse().getDescription()
                            contactEmail = response.body().getResponse().getContactEmail()
                            //                            System.out.println("banner img---" + bannerImage);
                            if (!bannerImage.equals("", ignoreCase = true)) {
//                                bannerUrlImageArray = new ArrayList<>();
//                                bannerUrlImageArray.add(bannerImage);
//                                bannerImagePager.setAdapter(new ImagePagerDrawableAdapter(bannerUrlImageArray, mContext));
                                Glide.with(mContext).load(AppUtils.replace(bannerImage))
                                    .centerCrop().into(bannerImagePager)
                            } else {
                                bannerImagePager!!.setBackgroundResource(R.drawable.pabanner)
                            }
                            parentAssociationEventsModelsArrayList =
                                ArrayList<ParentAssociationEventsModel>()
                            if (response.body().getResponse().getEventDataList().size() > 0) {
                                for (i in 0 until response.body().getResponse().getEventDataList()
                                    .size()) {
                                    val item: ParentAssociationResponseModel.EventData =
                                        response.body().getResponse().getEventDataList().get(i)
                                    val gson = Gson()
                                    val eventJson = gson.toJson(item)
                                    //                                    Log.e("item", eventJson);
                                    try {
                                        val jsonObject = JSONObject(eventJson)
                                        val parentAssociationEventsModel =
                                            ParentAssociationEventsModel()
                                        parentAssociationEventsModel.setPdfId(jsonObject.optString("id"))
                                        parentAssociationEventsModel.setPdfTitle(
                                            jsonObject.optString(
                                                "title"
                                            )
                                        )
                                        parentAssociationEventsModel.setPdfUrl(
                                            jsonObject.optString(
                                                "file"
                                            )
                                        )
                                        parentAssociationEventsModelsArrayList.add(
                                            parentAssociationEventsModel
                                        )
                                        //  Log.e("array", String.valueOf(mCCAmodelArrayList));
                                    } catch (e: JSONException) {
                                        e.printStackTrace()
                                    }
                                }
                                val adapter = ParentsAssociationAdapter(
                                    mContext,
                                    parentAssociationEventsModelsArrayList
                                )
                                mRecyclerView!!.adapter = adapter
                            } else {
                                Toast.makeText(
                                    this@ParentsAssociationSignUpActivity,
                                    "No data found",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            if (description.equals("", ignoreCase = true) && contactEmail.equals(
                                    "",
                                    ignoreCase = true
                                )
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
                                //			descriptionTitle.setVisibility(View.VISIBLE);
                                descriptionTV!!.visibility = View.VISIBLE
                                mtitle!!.visibility = View.VISIBLE
                            }
                            //                            System.out.println("contact email" + contactEmail);
                            if (contactEmail.equals("", ignoreCase = true)) {
                                sendEmail.setVisibility(View.GONE)
                            } else {
                                mtitle!!.visibility = View.VISIBLE
                                sendEmail.setVisibility(View.VISIBLE)
                            }
                        } else {
                            AppUtils.showDialogAlertDismiss(
                                mContext as Activity,
                                "Alert",
                                getString(R.string.common_error),
                                R.drawable.exclamationicon,
                                R.drawable.round
                            )
                        }
                    } else if (response.body().getResponseCode()
                            .equalsIgnoreCase(RESPONSE_ACCESSTOKEN_MISSING) ||
                        response.body().getResponseCode()
                            .equalsIgnoreCase(RESPONSE_ACCESSTOKEN_EXPIRED) ||
                        response.body().getResponseCode().equalsIgnoreCase(RESPONSE_INVALID_TOKEN)
                    ) {
                        AppUtils.postInitParam(mContext, object : GetAccessTokenInterface() {
                            val accessToken: Unit
                                get() {}
                        })
                        callStaffDirectoryList()
                    } else if (response.body().getResponseCode().equals(RESPONSE_ERROR)) {
//								CustomStatusDialog(RESPONSE_FAILURE);
                        //Toast.makeText(mContext,"Failure", Toast.LENGTH_SHORT).show();
                        AppUtils.showDialogAlertDismiss(
                            mContext as Activity,
                            "Alert",
                            getString(R.string.common_error),
                            R.drawable.exclamationicon,
                            R.drawable.round
                        )
                    } else {
                        AppUtils.showDialogAlertDismiss(
                            mContext as Activity,
                            "Alert",
                            getString(R.string.common_error),
                            R.drawable.exclamationicon,
                            R.drawable.round
                        )
                    }
                }
            }

            override fun onFailure(call: Call<ParentAssociationResponseModel>, t: Throwable) {
                AppUtils.showDialogAlertDismiss(
                    mContext as Activity,
                    "Alert",
                    getString(R.string.common_error),
                    R.drawable.exclamationicon,
                    R.drawable.round
                )
            }
        })*//*

    }

    private fun sendEmailToStaff(dialog: Dialog) {
       */
/* progressDialog!!.visibility = View.VISIBLE
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
                    progressDialog!!.visibility = View.GONE
                    val apiResponse: SendMailStaffResponseModel? = response.body()
                    //                    Log.e("response", String.valueOf(apiResponse));
//                    System.out.println("response" + apiResponse);
                    val statuscode: String = apiResponse.getResponse().getStatuscode()
                    if (statuscode == "303") {
                        dialog.dismiss()
                        val toast = Toast.makeText(
                            mContext, mContext.resources.getString(
                                R.string.successfully_send_email_staff
                            ), Toast.LENGTH_SHORT
                        )
                        toast.show()
                    } else {
                        val toast = Toast.makeText(
                            mContext, mContext.resources.getString(
                                R.string.email_not_staff
                            ), Toast.LENGTH_SHORT
                        )
                        toast.show()
                    }
                }
            }

            override fun onFailure(call: Call<SendMailStaffResponseModel?>, t: Throwable) {
//                Log.e("toadst", String.valueOf(t));
                progressDialog!!.visibility = View.GONE
                Toast.makeText(
                    mContext, mContext.resources.getString(
                        R.string.email_not_staff
                    ), Toast.LENGTH_SHORT
                )
            }
        })*//*

    }


    internal class ParentsAssociationAdapter(
        private val mContext: Context,
        mnNewsLetterModelArrayList: ArrayList<ParentAssociationEventsModel>
    ) :
        RecyclerView.Adapter<ParentsAssociationAdapter.MyViewHolder>() {
        var dept: String? = null
        private val mnNewsLetterModelArrayList: ArrayList<ParentAssociationEventsModel>

        init {
            this.mnNewsLetterModelArrayList = mnNewsLetterModelArrayList
        }

        override fun getItemCount(): Int {
            return mnNewsLetterModelArrayList.size
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val itemView: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.custom_pdf_adapter_row_new, parent, false)
            return MyViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        holder.submenu.setText(mnNewsLetterModelArrayList.get(position).getSubmenu());
            holder.pdfTitle.setText(mnNewsLetterModelArrayList[position].getPdfTitle())
            holder.imageIcon.visibility = View.GONE

        }

        inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            var imageIcon: ImageView
            var pdfTitle: TextView

            init {
                imageIcon = view.findViewById<View>(R.id.imageIcon) as ImageView
                pdfTitle = view.findViewById<View>(R.id.pdfTitle) as TextView
            }
        }
    }


}*/
