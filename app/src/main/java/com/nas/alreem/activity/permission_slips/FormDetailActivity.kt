package com.nas.alreem.activity.permission_slips

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.nas.alreem.R
import com.nas.alreem.activity.home.HomeActivity
import com.nas.alreem.activity.permission_slip.model.PermissionResApiModel
import com.nas.alreem.activity.permission_slip.model.PermissionResponseModel
import com.nas.alreem.constants.ApiClient
import com.nas.alreem.constants.ConstantFunctions
import com.nas.alreem.constants.DialogFunctions
import com.nas.alreem.constants.PreferenceManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FormDetailActivity: AppCompatActivity()  {
    lateinit var mContext: Context
   // lateinit var sharedprefs: PreferenceData
    lateinit var titletext: TextView
    lateinit var descriptn: TextView
    lateinit var radioButton: RadioButton
    lateinit var accpt_btn: Button
    lateinit var rejct_btn: Button
    lateinit var declaration: TextView
    lateinit var linear_declrtn: LinearLayout
    lateinit var linear_btn: LinearLayout
    lateinit var linear_status: ConstraintLayout
    lateinit var image_status: ImageView
    lateinit var text_status: TextView
    lateinit var back: RelativeLayout
    lateinit var progressDialog: RelativeLayout
    lateinit var logoClickImgView: ImageView
    var title_txt:String=""
    var descrptn_txt:String=""
    var status_txt:String=""
    var slip_id:Int=0
    var student_name:String=""
    var student_class:String=""
    var declrtn_txt:String=""
    var status_slip:String=""
    lateinit var activity: Activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission_slip_detail)
       // sharedprefs = PreferenceData()
        init()

    }
    private fun init() {
        mContext = this
        activity=this
        titletext=findViewById(R.id.heading)
        logoClickImgView = findViewById(R.id.logoClickImgView)
        descriptn=findViewById(R.id.descrtn_txt)
        radioButton=findViewById(R.id.check_btn)
        accpt_btn=findViewById(R.id.accepted_btn)
        rejct_btn=findViewById(R.id.rejected_btn)
        declaration=findViewById(R.id.declrtn_txt)
        linear_btn=findViewById(R.id.button_linear)
        linear_declrtn=findViewById(R.id.declaration)
        linear_status=findViewById(R.id.status_linear)
        image_status=findViewById(R.id.image_stts)
        text_status=findViewById(R.id.text_stts)
        back=findViewById(R.id.backRelative)
        progressDialog = findViewById<RelativeLayout>(R.id.progressDialog)
        title_txt=intent.getStringExtra("title").toString()
        descrptn_txt=intent.getStringExtra("description").toString()
        status_txt=intent.getStringExtra("status").toString()

        slip_id=intent.getIntExtra("slip_id",0)
        titletext.text = title_txt

        descriptn.setClickable(true);
        descriptn.setMovementMethod(LinkMovementMethod.getInstance());
        descriptn.setText(Html.fromHtml(descrptn_txt));
        //descriptn.setText(Html.fromHtml(descrptn_txt, Html.FROM_HTML_MODE_COMPACT));
        //descriptn.text = descrptn_txt
        student_name=PreferenceManager.getStudentName(mContext).toString()
        student_class=PreferenceManager.getStudentClass(mContext).toString()
        declrtn_txt="I hereby give my permission for "+student_name+ " of class "+student_class+" to participate in this event."
        declaration.text = declrtn_txt

        logoClickImgView.setOnClickListener(View.OnClickListener {
            val intent = Intent(mContext, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        })
        back.setOnClickListener {
            finish()
        }
        //viewfnct()
        fnctn2()

    }
    private fun fnctn2() {
        rejct_btn.setBackgroundResource(R.drawable.event_spinnerbg)
        rejct_btn.setOnClickListener {
            status_slip = "2"
            if (ConstantFunctions.internetCheck(mContext))
            {
                callpermissionresponseApi()
            }
            else
            {
                DialogFunctions.showInternetAlertDialog(mContext)
            }

        }
        if (status_txt.equals("0")) {
            linear_status.visibility = View.GONE
            linear_btn.visibility = View.VISIBLE
            linear_declrtn.visibility = View.VISIBLE
            radioButton.setOnClickListener() {

                if (status_txt.equals("0")){
                    if (!radioButton.isSelected) {
                        radioButton.isChecked = true
                        radioButton.isSelected = true
                        accpt_btn.setBackgroundResource(R.drawable.event_spinnerbg)
                        rejct_btn.setBackgroundResource(R.drawable.event_spinnerbg)

                        accpt_btn.setOnClickListener {
                            if (radioButton.isSelected) {
                                status_slip = "1"
                                if (ConstantFunctions.internetCheck(mContext))
                                {
                                    callpermissionresponseApi()
                                }
                                else
                                {
                                    DialogFunctions.showInternetAlertDialog(mContext)
                                }

                            }else{
                                //showSuccessmailAlert(mContext,"Please select the checkbox","Alert")
                            }
                        }


                    } else {
                        radioButton.isChecked = false
                        radioButton.isSelected = false
                        accpt_btn.setBackgroundResource(R.drawable.event_spinner_grey)
                    }
                }
            }
        }
        else if (status_txt.equals("1")){
            linear_status.visibility = View.VISIBLE
            linear_btn.visibility = View.GONE
            linear_declrtn.visibility = View.GONE
            image_status.setBackgroundResource(R.drawable.ic_baseline_check_24)
            text_status.text = "Accepted"

        }
        else if (status_txt.equals("2")){
            linear_status.visibility = View.VISIBLE
            linear_btn.visibility = View.GONE
            linear_declrtn.visibility = View.GONE
            image_status.setBackgroundResource(R.drawable.ic_baseline_close_24)
            text_status.text = "Rejected"
        }
    }

    private fun  callpermissionresponseApi() {
       /* progressDialog.visibility = View.VISIBLE
        var devicename: String = (Build.MANUFACTURER
                + " " + Build.MODEL + " " + Build.VERSION.RELEASE
                + " " + Build.VERSION_CODES::class.java.fields[Build.VERSION.SDK_INT]
            .name)
        val token = PreferenceManager.getaccesstoken(mContext)
        val permsnresBody = PermissionResApiModel(
            slip_id,
            PreferenceManager.getStudentID(mContext)!!,
            status_slip,
            "2",
            devicename,
            "1.0"
        )
        val call: Call<PermissionResponseModel> =
            ApiClient.getClient.permsnlistResponse(permsnresBody, "Bearer " + token)
        call.enqueue(object : Callback<PermissionResponseModel> {
            override fun onFailure(call: Call<PermissionResponseModel>, t: Throwable) {
                progressDialog.visibility = View.GONE
            }

            override fun onResponse(
                call: Call<PermissionResponseModel>,
                response: Response<PermissionResponseModel>
            ) {
                val responsedata = response.body()
                progressDialog.visibility = View.GONE

                if (responsedata!!.status.toString().equals("100")) {
                    showSuccessAlert(mContext, "Successfully submitted ", "Success")

                } else {
                    if (responsedata.status == 116) {
                        //call Token Expired
                       // AccessTokenClass.getAccessToken(com.mobatia.bisad.fragment.home.mContext)
                        if (ConstantFunctions.internetCheck(mContext))
                        {
                            callpermissionresponseApi()
                        }
                        else
                        {
                            DialogFunctions.showInternetAlertDialog(mContext)
                        }

                    } else {
                        if (responsedata.status == 103) {
                            //validation check error
                        } else {
                            //check status code checks
                          //  InternetCheckClass.checkApiStatusError(responsedata.status, mContext)
                        }
                    }

                }
            }

        })*/
    }

    private fun showSuccessAlert(context: Context,message : String,msgHead : String)
    {
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
            finish()
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