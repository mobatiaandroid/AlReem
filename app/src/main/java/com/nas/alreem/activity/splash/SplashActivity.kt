package com.nas.alreem.activity.splash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.nas.alreem.R
import com.nas.alreem.activity.home.HomeActivity
import com.nas.alreem.activity.login.LoginActivity
import com.nas.alreem.activity.settings.TutorialActivity
import com.nas.alreem.constants.PreferenceManager

class SplashActivity : AppCompatActivity() {
    lateinit var mContext: Context
    private val SPLASH_TIME_OUT:Long = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        mContext=this

        Handler().postDelayed({

            if (PreferenceManager.getFirstTime(mContext).equals(""))
            {
                val intent = Intent(mContext, TutorialActivity::class.java)
                PreferenceManager.setFirtTime(mContext,"First")
                startActivity(intent)
                finish()
            }
            else{
                if (PreferenceManager.getaccesstoken(mContext).equals(""))
                {
                    PreferenceManager.setNoticeFirtTime(mContext,"")
                    PreferenceManager.setIsSurveyHomeVisible(mContext, false)
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()

                }
                else
                {
                    PreferenceManager.setNoticeFirtTime(mContext,"")
                    PreferenceManager.setIsSurveyHomeVisible(mContext, false)
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()

                }
            }




        }, SPLASH_TIME_OUT)
    }


}