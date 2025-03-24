package com.nas.alreem.activity.login

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.provider.Settings
import android.view.MotionEvent
import android.view.View
import android.view.Window
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import com.nas.alreem.R
import com.nas.alreem.activity.home.HomeActivity
import com.nas.alreem.activity.login.model.LoginResponseModel
import com.nas.alreem.constants.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity(),View.OnTouchListener{
    lateinit var mContext: Context
    lateinit var loginBtn:Button
    lateinit var signUpButton:Button
    lateinit var forgotPasswordButton:Button
    lateinit var guestButton:Button
    lateinit var helpButton:Button
    lateinit var userEditText:EditText
    lateinit var passwordEditText:EditText
    lateinit var progressDialogAdd:ProgressBar
    var tokenM:String=""
    lateinit var activity: Activity


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mContext=this
        activity=this
        initUI()

    }
    fun initUI()
    {
        loginBtn=findViewById(R.id.loginBtn)
        signUpButton=findViewById(R.id.signUpButton)
        userEditText=findViewById(R.id.userEditText)
        passwordEditText=findViewById(R.id.passwordEditText)
        forgotPasswordButton=findViewById(R.id.forgotPasswordButton)
        helpButton=findViewById(R.id.helpButton)
        progressDialogAdd=findViewById(R.id.progressDialogAdd)
        guestButton=findViewById(R.id.guestButton)

        userEditText.setOnTouchListener(this)
        passwordEditText.setOnTouchListener(this)
        //Keyboard done button click username
        userEditText.setOnEditorActionListener { v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_DONE){
                userEditText?.isFocusable=false
                userEditText?.isFocusableInTouchMode=false
                false
            } else {
                userEditText?.isFocusable=false
                userEditText?.isFocusableInTouchMode=false
                false
            }
        }

        //Keyboard done button click password
        passwordEditText.setOnEditorActionListener { v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_DONE){
                passwordEditText.isFocusable =false
                passwordEditText.isFocusableInTouchMode =false
                false
            } else {
                userEditText?.isFocusable=false
                userEditText?.isFocusableInTouchMode=false
                false
            }
        }

        loginBtn.setOnClickListener(View.OnClickListener {

            if (ValidationFunctions.loginValidation(userEditText.text.toString().trim(), passwordEditText.text.toString().trim(),mContext))
            {
                if (ConstantFunctions.internetCheck(mContext))
                {
                    FirebaseApp.initializeApp(mContext)
                    FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                        if (task.isComplete) {
                            val token: String = task.getResult().toString()
                            tokenM = token
                            callLoginApi(userEditText.text.toString().trim(), passwordEditText.text.toString().trim())
                            //callChangePasswordStaffAPI(URL_STAFF_CHANGE_PASSWORD, token)
                        }
                    }

                }
                else
                {
                    DialogFunctions.showInternetAlertDialog(mContext)
                }


            }

        })

        signUpButton.setOnClickListener(View.OnClickListener {

            DialogFunctions.showSignUpDialog(mContext)
        })

        forgotPasswordButton.setOnClickListener(View.OnClickListener {

            DialogFunctions.showForgetPasswordDialog(mContext)
        })
        guestButton.setOnClickListener(View.OnClickListener {

            startActivity(Intent(mContext,HomeActivity::class.java))
        })

        helpButton.setOnClickListener(View.OnClickListener {

            if (ConstantFunctions.internetCheck(mContext))
            {
                ConstantFunctions.showEmailView(ConstantWords.emailHelp,mContext,helpButton)
            }
            else
            {
                DialogFunctions.showInternetAlertDialog(mContext)
            }
        })

    }

    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
        when (view) {
            userEditText -> {
                when (motionEvent.action){
                    MotionEvent.ACTION_DOWN -> {
                        userEditText?.isFocusable=true
                        userEditText?.isFocusableInTouchMode=true
                    }
                    MotionEvent.ACTION_UP -> {
                        view.performClick()
                        userEditText?.isFocusable=true
                        userEditText?.isFocusableInTouchMode=true
                    }
                }
            }
            passwordEditText -> {
                when (motionEvent.action){
                    MotionEvent.ACTION_DOWN -> {
                        passwordEditText?.isFocusable=true
                        passwordEditText?.isFocusableInTouchMode=true
                    }
                    MotionEvent.ACTION_UP -> {
                        view.performClick()
                        passwordEditText?.isFocusable=true
                        passwordEditText?.isFocusableInTouchMode=true
                    }
                }
            }
        }
        return false
    }


    fun callLoginApi(emailId:String,password:String)
    {
        progressDialogAdd.visibility=View.VISIBLE
        var androidID = Settings.Secure.getString(this.contentResolver,
            Settings.Secure.ANDROID_ID)
        val call: Call<LoginResponseModel> = ApiClient(mContext).getClient.login(emailId,password,"2",tokenM,androidID)
        call.enqueue(object : Callback<LoginResponseModel> {
            override fun onFailure(call: Call<LoginResponseModel>, t: Throwable) {
                progressDialogAdd.visibility=View.GONE
            }
            override fun onResponse(call: Call<LoginResponseModel>, response: Response<LoginResponseModel>) {
                val responsedata = response.body()
                progressDialogAdd.visibility=View.GONE
                if (responsedata != null) {
                    try {

                        if (response.body()!!.status==100)
                        {
                            PreferenceManager.setEmailId(mContext,emailId)
                            PreferenceManager.setaccesstoken(mContext,response.body()!!.token)
                            PreferenceManager.setUserCode(mContext,response.body()!!.user_code)
                            loginSuccessAlert(
                                resources.getString(R.string.success), ConstantWords.status_100_login, mContext)

                        } else if (response.body()!!.status==510) {
                            DialogFunctions.commonErrorAlertDialog(mContext.resources.getString(R.string.alert), ConstantFunctions.commonErrorString(response.body()!!.status), mContext)


//						startCountdown(60000L);
                        }
                        else if (response.body()!!.status==111) {
                            DialogFunctions.commonErrorAlertDialog(mContext.resources.getString(R.string.alert), ConstantFunctions.commonErrorString(response.body()!!.status), mContext)


//						startCountdown(60000L);
                        }
                        else
                        {

                            DialogFunctions.commonErrorAlertDialog(mContext.resources.getString(R.string.alert), ConstantFunctions.commonErrorString(response.body()!!.status), mContext)
                        }


                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

        })
    }


    fun loginSuccessAlert(heading:String,Message:String,context:Context)
    {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_common_error_alert)
        var iconImageView = dialog.findViewById(R.id.iconImageView) as ImageView
        var alertHead = dialog.findViewById(R.id.alertHead) as TextView
        var messageTxt = dialog.findViewById(R.id.messageTxt) as TextView
        var btn_Ok = dialog.findViewById(R.id.btn_Ok) as Button
        messageTxt.text = Message
        alertHead.text = heading
        iconImageView.setImageResource(R.drawable.tick)
        btn_Ok.setOnClickListener()
        {

            startActivity(Intent(context,HomeActivity::class.java))
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