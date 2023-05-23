package com.nas.alreem.constants

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.Window
import android.view.inputmethod.EditorInfo
import android.widget.*
import com.nas.alreem.R
import com.nas.alreem.activity.login.LoginActivity
import com.nas.alreem.activity.login.model.ForgetPasswordResponseModel
import com.nas.alreem.activity.login.model.SignUpResponseModel
import com.nas.alreem.fragment.settings.model.ChangePasswordApiModel
import com.nas.alreem.rest.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DialogFunctions {
    companion object
    {
        fun showSignUpDialog(context: Context)
        {

            val dialog = Dialog(context)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.dialog_sign_up)
            var text_dialog = dialog.findViewById(R.id.text_dialog) as EditText
            var btn_signup = dialog.findViewById(R.id.btn_signup) as Button
            var btn_maybelater = dialog.findViewById(R.id.btn_maybelater) as Button
            var progressDialogAdd = dialog.findViewById(R.id.progressDialogAdd) as ProgressBar
            text_dialog.setOnEditorActionListener { v, actionId, event ->
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    text_dialog.isFocusable =false
                    text_dialog.isFocusableInTouchMode =false
                    false
                } else {
                    text_dialog.isFocusable =false
                    text_dialog.isFocusableInTouchMode =false
                    false
                }
            }

            text_dialog.setOnTouchListener(object : View.OnTouchListener {
                override fun onTouch(v: View, m: MotionEvent): Boolean {
                    // Perform tasks here
                    text_dialog.isFocusable =true
                    text_dialog.isFocusableInTouchMode =true
                    return false
                }
            })
            btn_signup.setOnClickListener()
            {
                if (ValidationFunctions.SignUpForgetValidation(text_dialog.text.toString().trim(),context))
                {

                    if (ConstantFunctions.internetCheck(context))
                    {
                        progressDialogAdd.visibility= View.VISIBLE
                        signUp(text_dialog.text.toString().trim(),progressDialogAdd,context,dialog)

                    }
                    else
                    {
                       showInternetAlertDialog(context)
                    }


                }

            }

            btn_maybelater.setOnClickListener()
            {
                dialog.dismiss()
            }
            dialog.show()
        }


        fun showForgetPasswordDialog(context: Context)
        {
            val dialog = Dialog(context)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.dialog_forget_password)

            var text_dialog = dialog.findViewById(R.id.text_dialog) as EditText
            var btn_signup = dialog.findViewById(R.id.btn_signup) as Button
            var btn_maybelater = dialog.findViewById(R.id.btn_maybelater) as Button
            var progressDialogAdd = dialog.findViewById(R.id.progressDialogAdd) as ProgressBar
            text_dialog.setOnEditorActionListener { v, actionId, event ->
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    text_dialog.isFocusable =false
                    text_dialog.isFocusableInTouchMode =false
                    false
                } else {
                    text_dialog.isFocusable =false
                    text_dialog.isFocusableInTouchMode =false
                    false
                }
            }

            text_dialog.setOnTouchListener(object : View.OnTouchListener {
                override fun onTouch(v: View, m: MotionEvent): Boolean {
                    // Perform tasks here
                    text_dialog.isFocusable =true
                    text_dialog.isFocusableInTouchMode =true
                    return false
                }
            })
            btn_signup.setOnClickListener()
            {
                if (ValidationFunctions.SignUpForgetValidation(text_dialog.text.toString().trim(),context))
                {
                    if (ConstantFunctions.internetCheck(context))
                    {
                        progressDialogAdd.visibility=View.VISIBLE
                        forgetPassword(text_dialog.text.toString().trim(),progressDialogAdd,context,dialog)
                    }
                    else
                    {
                        showInternetAlertDialog(context)
                    }


                }
            }

            btn_maybelater.setOnClickListener()
            {
                dialog.dismiss()
            }
            dialog.show()
        }


        fun commonErrorAlertDialog(heading:String,Message:String,context:Context)
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
            btn_Ok.setOnClickListener()
            {
                dialog.dismiss()
            }
            dialog.show()

        }




        fun showInternetAlertDialog(context: Context)
        {

            val dialog = Dialog(context)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.dialog_internet_failure_alert)
            var btn_Ok = dialog.findViewById(R.id.btn_Ok) as Button
            btn_Ok.setOnClickListener()
            {
                dialog.dismiss()
            }
            dialog.show()
        }


        fun signUp(email:String,progressDialogAdd:ProgressBar,context: Context,dialog:Dialog)
        {
            progressDialogAdd.visibility=View.VISIBLE
            val call: Call<SignUpResponseModel> = ApiClient.getClient.signup(email)
            call.enqueue(object : Callback<SignUpResponseModel> {
                override fun onFailure(call: Call<SignUpResponseModel>, t: Throwable) {
                    Log.e("Failed", t.localizedMessage)
                    progressDialogAdd.visibility=View.GONE
                }
                override fun onResponse(call: Call<SignUpResponseModel>, response: Response<SignUpResponseModel>) {
                    val responsedata = response.body()
                    progressDialogAdd.visibility=View.GONE
                    if (responsedata != null) {
                        try {

                            if (response.body()!!.status==100)
                            {
                                commonErrorAlertDialog(context.resources.getString(R.string.success),ConstantWords.status_100_signup,context)
                                dialog.dismiss()
                            }
                            else
                            {

                                commonErrorAlertDialog(context.resources.getString(R.string.alert),ConstantFunctions.commonErrorString(response.body()!!.status),context)
                            }


                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }

            })
        }

        fun forgetPassword(email:String,progressDialogAdd:ProgressBar,context: Context,dialog:Dialog)
        {
            progressDialogAdd.visibility=View.VISIBLE
            val call: Call<ForgetPasswordResponseModel> = ApiClient.getClient.forgetPassword(email)
            call.enqueue(object : Callback<ForgetPasswordResponseModel> {
                override fun onFailure(call: Call<ForgetPasswordResponseModel>, t: Throwable) {
                    Log.e("Failed", t.localizedMessage)
                    progressDialogAdd.visibility=View.GONE
                }
                override fun onResponse(call: Call<ForgetPasswordResponseModel>, response: Response<ForgetPasswordResponseModel>) {
                    val responsedata = response.body()
                    progressDialogAdd.visibility=View.GONE
                    if (responsedata != null) {
                        try {

                            if (response.body()!!.status==100)
                            {
                                commonErrorAlertDialog(context.resources.getString(R.string.success),ConstantWords.status_100_forget_password,context)
                                dialog.dismiss()
                            }
                            else
                            {

                                commonErrorAlertDialog(context.resources.getString(R.string.alert),ConstantFunctions.commonErrorString(response.body()!!.status),context)
                            }


                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }

            })
        }


        fun logoutDialog(context: Context)
        {
            val dialog = Dialog(context)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.dialog_logout)
            var btn_Ok = dialog.findViewById(R.id.btn_Ok) as Button
            var btn_Cancel = dialog.findViewById(R.id.btn_Cancel) as Button
            btn_Ok.setOnClickListener()
            {
                PreferenceManager.setaccesstoken(context,"")
                dialog.dismiss()
                val mIntent = Intent(context, LoginActivity::class.java)
                mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                context.startActivity(mIntent)
            }
            btn_Cancel.setOnClickListener()
            {
                dialog.dismiss()
            }
            dialog.show()
        }

        fun changePasswordDialog(context: Context)
        {
            val dialog = Dialog(context)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.dialog_change_password)
            var btn_changepassword = dialog.findViewById(R.id.btn_changepassword) as Button
            var btn_cancel = dialog.findViewById(R.id.btn_cancel) as Button
            var text_currentpassword = dialog.findViewById(R.id.text_currentpassword) as EditText
            var text_currentnewpassword = dialog.findViewById(R.id.text_currentnewpassword) as EditText
            var text_confirmpassword = dialog.findViewById(R.id.text_confirmpassword) as EditText
            var progressDialogAdd = dialog.findViewById(R.id.progressDialogAdd) as ProgressBar
            text_currentpassword.setOnTouchListener(object : View.OnTouchListener {
                override fun onTouch(v: View, m: MotionEvent): Boolean {
                    // Perform tasks here
                    text_currentpassword.isFocusable =true
                    text_currentpassword.isFocusableInTouchMode =true
                    return false
                }
            })
            text_currentnewpassword.setOnTouchListener(object : View.OnTouchListener {
                override fun onTouch(v: View, m: MotionEvent): Boolean {
                    // Perform tasks here
                    text_currentnewpassword.isFocusable =true
                    text_currentnewpassword.isFocusableInTouchMode =true
                    return false
                }
            })
            text_confirmpassword.setOnTouchListener(object : View.OnTouchListener {
                override fun onTouch(v: View, m: MotionEvent): Boolean {
                    // Perform tasks here
                    text_confirmpassword.isFocusable =true
                    text_confirmpassword.isFocusableInTouchMode =true
                    return false
                }
            })
            btn_changepassword.setOnClickListener()
            {

               if (text_currentpassword.text.toString().trim().equals(""))
               {
                   // Enter Current Password
                   commonErrorAlertDialog(context.resources.getString(R.string.alert),context.resources.getString(R.string.enter_current_password),context)
               }
                else{
                   if (text_currentnewpassword.text.toString().trim().equals(""))
                   {
                       //Enter new password
                       commonErrorAlertDialog(context.resources.getString(R.string.alert),context.resources.getString(R.string.enter_new_password),context)
                   }
                   else
                   {
                       if (text_confirmpassword.text.toString().trim().equals(""))
                       {
                           // Kindly Confirm your password
                           commonErrorAlertDialog(context.resources.getString(R.string.alert),context.resources.getString(R.string.confirm_password),context)
                       }
                       else
                       {
                           if (text_currentnewpassword.text.toString().trim().equals(text_confirmpassword.text.toString().trim()))
                           {
                               // callChangePasswordApi()
                               if (ConstantFunctions.internetCheck(context))
                               {
                                   progressDialogAdd.visibility= View.VISIBLE
                                   changePassword(text_currentpassword.text.toString().trim(),text_currentnewpassword.text.toString().trim(),text_confirmpassword.text.toString().trim(),progressDialogAdd,context,dialog)

                               }
                               else
                               {
                                   showInternetAlertDialog(context)
                               }
                           }
                           else
                           {
                               // New Password and Confirm Password Doesn't match
                               commonErrorAlertDialog(context.resources.getString(R.string.alert),context.resources.getString(R.string.password_not_match),context)

                           }
                       }
                   }
               }
               // dialog.dismiss()

            }
            btn_cancel.setOnClickListener()
            {
                dialog.dismiss()
            }
            dialog.show()
        }

        fun deleteAccountDialog(context: Context)
        {
            val dialog = Dialog(context)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.dialog_delete_account)
            var btn_Ok = dialog.findViewById(R.id.btn_Ok) as Button
            var btn_Cancel = dialog.findViewById(R.id.btn_Cancel) as Button
            var progressDialogAdd = dialog.findViewById(R.id.progressDialogAdd) as ProgressBar
            btn_Ok.setOnClickListener()
            {
                if (ConstantFunctions.internetCheck(context))
                {
                    progressDialogAdd.visibility= View.VISIBLE
                    callDeleteAccountApi(progressDialogAdd,context,dialog)
                }
                else
                {
                    showInternetAlertDialog(context)
                }

            }
            btn_Cancel.setOnClickListener()
            {
                dialog.dismiss()
            }
            dialog.show()
        }



        fun changePassword(current:String,newpass:String,confirmpass:String,progressDialogAdd:ProgressBar,context: Context,dialog:Dialog)
        {
            progressDialogAdd.visibility=View.VISIBLE
            var model=ChangePasswordApiModel(current,newpass,confirmpass)
            val call: Call<ForgetPasswordResponseModel> = ApiClient.getClient.changePassword(model,"Bearer "+PreferenceManager.getaccesstoken(context))
            call.enqueue(object : Callback<ForgetPasswordResponseModel> {
                override fun onFailure(call: Call<ForgetPasswordResponseModel>, t: Throwable) {
                    Log.e("Failed", t.localizedMessage)
                    progressDialogAdd.visibility=View.GONE
                }
                override fun onResponse(call: Call<ForgetPasswordResponseModel>, response: Response<ForgetPasswordResponseModel>) {
                    val responsedata = response.body()
                    progressDialogAdd.visibility=View.GONE
                    if (responsedata != null) {
                        try {

                            if (response.body()!!.status==100)
                            {
                                commonErrorAlertDialog(context.resources.getString(R.string.success),ConstantWords.status_100_change_password,context)
                                dialog.dismiss()
                            }
                            else
                            {

                                commonErrorAlertDialog(context.resources.getString(R.string.alert),ConstantFunctions.commonErrorString(response.body()!!.status),context)
                            }


                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }

            })
        }


        fun callDeleteAccountApi(progressDialogAdd:ProgressBar,context: Context,dialog:Dialog)
        {
            progressDialogAdd.visibility=View.VISIBLE

            val call: Call<ForgetPasswordResponseModel> = ApiClient.getClient.deleteAccount("Bearer "+PreferenceManager.getaccesstoken(context))
            call.enqueue(object : Callback<ForgetPasswordResponseModel> {
                override fun onFailure(call: Call<ForgetPasswordResponseModel>, t: Throwable) {
                    Log.e("Failed", t.localizedMessage)
                    progressDialogAdd.visibility=View.GONE
                }
                override fun onResponse(call: Call<ForgetPasswordResponseModel>, response: Response<ForgetPasswordResponseModel>) {
                    val responsedata = response.body()
                    progressDialogAdd.visibility=View.GONE
                    if (responsedata != null) {
                        try {

                            if (response.body()!!.status==100)
                            {

                                PreferenceManager.setaccesstoken(context,"")
                                dialog.dismiss()
                                val mIntent = Intent(context, LoginActivity::class.java)
                                mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                context.startActivity(mIntent)
                            }
                            else
                            {

                                commonErrorAlertDialog(context.resources.getString(R.string.alert),ConstantFunctions.commonErrorString(response.body()!!.status),context)
                            }


                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }

            })
        }
    }




}