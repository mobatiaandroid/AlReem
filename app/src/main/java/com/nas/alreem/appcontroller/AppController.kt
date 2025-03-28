package com.nas.alreem.appcontroller

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.view.WindowManager
import android.widget.TextView
import com.nas.alreem.activity.cca.model.CCADetailModel
import com.nas.alreem.activity.cca.model.WeekListModel


class AppController : Application() {
    companion object {
        var instance: AppController? = null
        var tibCoinPoints: Int = 0
        var scratchID: Int = 0
        var question_id: String? = null
        var answer_id: String? = null
        var ccdots:TextView?=null
        var keyy:String?="0"
        var weekList: ArrayList<WeekListModel> = ArrayList()
        var weekListWithData: java.util.ArrayList<Int>? = ArrayList()
        var CCADetailModelArrayList: ArrayList<CCADetailModel> = ArrayList()
        var reciver:String="0"
        var filledFlag = 0
        fun applicationContext(): AppController {
            return instance as AppController
        }
    }

    init {
        instance = this

    }
    override fun onCreate() {
        super.onCreate()

        // Register ActivityLifecycleCallbacks
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                // Apply FLAG_SECURE to prevent screenshots and screen recordings
                activity.window.setFlags(
                    WindowManager.LayoutParams.FLAG_SECURE,
                    WindowManager.LayoutParams.FLAG_SECURE
                )
            }

            override fun onActivityStarted(activity: Activity) {}
            override fun onActivityResumed(activity: Activity) {}
            override fun onActivityPaused(activity: Activity) {}
            override fun onActivityStopped(activity: Activity) {}
            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
            override fun onActivityDestroyed(activity: Activity) {}
        })
    }
}