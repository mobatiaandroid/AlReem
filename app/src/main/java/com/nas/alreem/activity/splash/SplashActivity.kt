package com.nas.alreem.activity.splash

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Debug
import android.os.Handler
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.nas.alreem.R
import com.nas.alreem.activity.home.HomeActivity
import com.nas.alreem.activity.login.LoginActivity
import com.nas.alreem.activity.settings.TutorialActivity
import com.nas.alreem.constants.ConstantFunctions
import com.nas.alreem.constants.DebuggingChecker
import com.nas.alreem.constants.PreferenceManager
import com.scottyab.rootbeer.RootBeer

class SplashActivity : AppCompatActivity() {
    lateinit var mContext: Context
    private val SPLASH_TIME_OUT: Long = 3000
    lateinit var activity: Activity


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        mContext = this
        activity = this
        val isDebuggingEnabled: Boolean = DebuggingChecker().isUsbDebuggingEnabled(mContext)
        val rootBeer = RootBeer(mContext)
        if (ConstantFunctions().isDeviceRootedOrEmulator(mContext)) {
            showDeviceIsRootedPopUp(mContext)
        } else if (rootBeer.isRooted()) {
            Toast.makeText(this, "Root detected! App will close.", Toast.LENGTH_LONG).show()
            finish()
        }
        else if (isDebuggingEnabled) {

            Toast.makeText(mContext, "This app does not support debugging", Toast.LENGTH_SHORT).show();
        } else if (!ConstantFunctions.runMethod.equals("Dev")) {
            if (ConstantFunctions().isDeveloperModeEnabled(mContext)) {
                ConstantFunctions().showDeviceIsDeveloperPopUp(activity)
            } else {
                Handler().postDelayed({

                    if (PreferenceManager.getFirstTime(mContext).equals("")) {
                        val intent = Intent(mContext, TutorialActivity::class.java)
                        PreferenceManager.setFirtTime(mContext, "First")
                        startActivity(intent)
                        finish()
                    } else {
                        if (PreferenceManager.getaccesstoken(mContext).equals("")) {
                            PreferenceManager.setNoticeFirtTime(mContext, "")
                            PreferenceManager.setIsSurveyHomeVisible(mContext, false)
                            PreferenceManager.setIsEnrollmentHomeVisible(mContext, false)
                            startActivity(Intent(this, LoginActivity::class.java))
                            finish()

                        } else {
                            PreferenceManager.setNoticeFirtTime(mContext, "")
                            PreferenceManager.setIsSurveyHomeVisible(mContext, false)
                            PreferenceManager.setIsEnrollmentHomeVisible(mContext, false)
                            startActivity(Intent(this, HomeActivity::class.java))
                            finish()

                        }
                    }


                }, SPLASH_TIME_OUT)
            }
        } else {
            Handler().postDelayed({

                if (PreferenceManager.getFirstTime(mContext).equals("")) {
                    val intent = Intent(mContext, TutorialActivity::class.java)
                    PreferenceManager.setFirtTime(mContext, "First")
                    startActivity(intent)
                    finish()
                } else {
                    if (PreferenceManager.getaccesstoken(mContext).equals("")) {
                        PreferenceManager.setNoticeFirtTime(mContext, "")
                        PreferenceManager.setIsSurveyHomeVisible(mContext, false)
                        PreferenceManager.setIsEnrollmentHomeVisible(mContext, false)
                        startActivity(Intent(this, LoginActivity::class.java))
                        finish()

                    } else {
                        PreferenceManager.setNoticeFirtTime(mContext, "")
                        PreferenceManager.setIsSurveyHomeVisible(mContext, false)
                        PreferenceManager.setIsEnrollmentHomeVisible(mContext, false)
                        startActivity(Intent(this, HomeActivity::class.java))
                        finish()

                    }
                }


            }, SPLASH_TIME_OUT)
        }

    }

    private fun showDeviceIsRootedPopUp(mContext: Context) {
        val dialog = Dialog(mContext)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_common_error_alert)
        val icon = dialog.findViewById<View>(R.id.iconImageView) as ImageView
        icon.setBackgroundResource(R.drawable.round)
        icon.setImageResource(R.drawable.exclamationicon)
        val text = dialog.findViewById<View>(R.id.text_dialog) as TextView
        val textHead = dialog.findViewById<View>(R.id.alertHead) as TextView
        text.text = "This app does not support rooted devices for security reasons."
        textHead.text = "Rooted Device Detected"
        val dialogButton = dialog.findViewById<View>(R.id.btn_Ok) as Button
        dialogButton.setOnClickListener { dialog.dismiss() }
        //		Button dialogButtonCancel = (Button) dialog.findViewById(R.id.btn_Cancel);
//		dialogButtonCancel.setVisibility(View.GONE);
//		dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				dialog.dismiss();
//			}
//		});
        dialog.show()
    }

    fun isDebuggerConnected(): Boolean {
        return Debug.isDebuggerConnected()
    }
}