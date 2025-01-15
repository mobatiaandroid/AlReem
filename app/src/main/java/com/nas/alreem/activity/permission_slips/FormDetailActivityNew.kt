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
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RelativeLayout
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
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
import com.nas.alreem.fragment.intention.model.IntentionSubmitModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FormDetailActivityNew : AppCompatActivity(){
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
    var selectedoption : String=""
    var slip_id:Int=0
    var student_name:String=""
    var student_class:String=""
    var declrtn_txt:String=""
    var status_slip:String=""

    lateinit var optionsArray : ArrayList<String>
    lateinit var optionArray: ArrayList<String>
    var receivedOptions: ArrayList<String> = ArrayList()
    var selectedItem = ""
    lateinit var sub_btn:Button
    lateinit var constraintLayout5 : ConstraintLayout
    lateinit var constraintLayout6 : ConstraintLayout
    lateinit var con_txt : ConstraintLayout
    lateinit var con_txt3 : ConstraintLayout
    lateinit var con_txt2 : ConstraintLayout
    lateinit var choose_option : TextView
    lateinit var arrowimg : ImageView
    lateinit var selected_option : TextView
    lateinit var activity: Activity



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission_slip_detail_new)
        // sharedprefs = PreferenceData()
        init()

    }
    @RequiresApi(Build.VERSION_CODES.P)
    private fun init() {
        mContext = this
        activity=this
        optionArray = ArrayList()
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
        sub_btn=findViewById(R.id.sub_btn)
        constraintLayout5 = findViewById(R.id.constraintLayout5)
        constraintLayout6 = findViewById(R.id.constraintLayout6)
        choose_option = findViewById(R.id.choose_option)
        arrowimg = findViewById(R.id.arrowimg)
        selected_option = findViewById(R.id.selected_option)
        con_txt = findViewById(R.id.con_txt)
        con_txt3 = findViewById(R.id.con_txt3)
        con_txt2 = findViewById(R.id.con_txt2)
        val spinnerList = findViewById<Spinner>(R.id.spinnerlist)
        val option_txt = findViewById<TextView>(R.id.option_txt)
        var dropDownList: java.util.ArrayList<String> = ArrayList<String>()





        progressDialog = findViewById<RelativeLayout>(R.id.progressDialog)
        title_txt=intent.getStringExtra("title").toString()
        descrptn_txt=intent.getStringExtra("description").toString()
        status_txt=intent.getStringExtra("status").toString()
        selectedoption=intent.getStringExtra("selectedoption").toString()
        slip_id=intent.getIntExtra("slip_id",0)
        receivedOptions = intent.getStringArrayListExtra("options")!!

        for (i in receivedOptions.indices){
            optionArray.add(receivedOptions[i])
        }

        optionsArray = ArrayList()
        val reEnrollSubmit = IntentionSubmitModel("", "")

        optionsArray.addAll(optionArray)
        Log.e("arraysizeoption", optionsArray.size.toString())

        dropDownList = ArrayList()

        dropDownList.add(0, "Please Select")
        for (i in 1..optionsArray.size) {
//        for (i in optionsArray.indices) {
            dropDownList.add(optionsArray.get(i-1).toString())
        }

        val sp_adapter: ArrayAdapter<*> =
            ArrayAdapter<Any?>(mContext, R.layout.spinner_textview, dropDownList as List<Any?>)
        spinnerList.adapter = sp_adapter
//        spinnerList.setSelection(0)
        val finalDropDownList: java.util.ArrayList<*> = dropDownList
        spinnerList.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                selectedItem = parent.getItemAtPosition(position).toString()

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        option_txt.setOnClickListener {
            option_txt.visibility = View.GONE
            spinnerList.visibility = View.VISIBLE
        }
        titletext.text = title_txt

        descriptn.setClickable(true);
        descriptn.setMovementMethod(LinkMovementMethod.getInstance());
        descriptn.setText(Html.fromHtml(descrptn_txt));

        student_name= PreferenceManager.getStudentName(mContext).toString()
        student_class= PreferenceManager.getStudentClass(mContext).toString()
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
        sub_btn.setOnClickListener {
            if (selectedItem.equals("") || selectedItem.equals("Please Select"))
            {


                Toast.makeText(mContext, " Please Select Options and Submit", Toast.LENGTH_SHORT).show()
                /* showReEnrollNoData(
                     mContext,
                     "You didn't enter any data of your child. Please Enter data and Submit", "Alert")*/
            } else {

                callpermissionresponseApi(selectedItem)
            }

        }

        fnctn2()

    }
    private fun fnctn2() {
Log.e("Success","Success")
        if (selectedoption.equals("")) {
            Log.e("0","0")

            //linear_status.visibility = View.GONE
            //linear_btn.visibility = View.VISIBLE
            con_txt2.visibility = View.VISIBLE
            con_txt3.visibility = View.GONE
            choose_option.visibility = View.VISIBLE
            choose_option.setText("Please Choose Option  :")
            sub_btn.visibility = View.VISIBLE
            constraintLayout5.visibility = View.VISIBLE
            constraintLayout6.visibility = View.GONE
            arrowimg.visibility = View.VISIBLE



        }
        else {
            con_txt2.visibility = View.GONE
            con_txt3.visibility = View.VISIBLE
            choose_option.visibility = View.VISIBLE
            constraintLayout6.visibility = View.VISIBLE
            constraintLayout5.visibility = View.GONE
            choose_option.setText("Selected Option  :")
            sub_btn.visibility = View.GONE
            arrowimg.visibility = View.GONE
            selected_option.setText(selectedoption)

            /* linear_status.visibility = View.VISIBLE
             linear_btn.visibility = View.GONE
             linear_declrtn.visibility = View.GONE
             image_status.setBackgroundResource(R.drawable.ic_baseline_check_24)
             text_status.text = "Accepted"*/

        }

    }

    private fun  callpermissionresponseApi(selectedItem: String) {

        progressDialog.visibility = View.VISIBLE
        var devicename: String = (Build.MANUFACTURER
                + " " + Build.MODEL + " " + Build.VERSION.RELEASE
                + " " + Build.VERSION_CODES::class.java.fields[Build.VERSION.SDK_INT]
            .name)
        val token = PreferenceManager.getaccesstoken(mContext)
        val permsnresBody = PermissionResApiModel(
            slip_id,
            PreferenceManager.getStudentID(mContext)!!,
            status_slip,
            selectedItem,
            "2",
            devicename,
            "1.0"
        )
        val call: Call<PermissionResponseModel> =
            ApiClient(mContext).getClient.permsnlistResponse(permsnresBody, "Bearer " + token)
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
                            callpermissionresponseApi(this@FormDetailActivityNew.selectedItem)
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

        })
    }

    private fun showSuccessAlert(context: Context, message : String, msgHead : String)
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