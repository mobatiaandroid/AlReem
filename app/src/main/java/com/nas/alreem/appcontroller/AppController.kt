package com.nas.alreem.appcontroller

import android.app.Application
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
}