package com.nas.alreem.activity.bus_service

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import com.nas.alreem.R
import com.nas.alreem.activity.bus_service.requestservice.RequestServiceListActivity
import com.nas.alreem.constants.ConstantFunctions
import com.nas.alreem.constants.PreferenceManager

class BusServiceRegisterupdate : AppCompatActivity() {
    lateinit var mContext: Context
    var ccaOption: RelativeLayout? = null
    var externalCCA: RelativeLayout? = null
    var informationCCA: RelativeLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bus_reg_new)
        mContext=this
        initfn()

        if (ConstantFunctions.internetCheck(mContext)) {

        } else {

        }

    }

    private fun initfn() {
        externalCCA = findViewById<View>(R.id.myOrderRelative) as RelativeLayout
        ccaOption = findViewById<View>(R.id.addOrderRelative) as RelativeLayout
        informationCCA = findViewById<View>(R.id.orderHistoryRelative) as RelativeLayout
        ccaOption!!.setOnClickListener {
            val intent = Intent(mContext, BusServiceEapRegister::class.java)
            intent.putExtra("tab_type", "Bus Service")
            startActivity(intent)
        }
        informationCCA!!.setOnClickListener {
            val intent = Intent(mContext, BusServiceSummery::class.java)
            intent.putExtra("tab_type", "Information")
            startActivity(intent)
        }
        externalCCA!!.setOnClickListener {

            PreferenceManager.setStudIdForCCA(mContext!!, "")
            // PreferenceManager.setStudentID(mContext!!, "")

            val intent = Intent(mContext, BusServiceRegisterNew::class.java)
            intent.putExtra("tab_type", "ECA Options")
            startActivity(intent)

        }
    }
}