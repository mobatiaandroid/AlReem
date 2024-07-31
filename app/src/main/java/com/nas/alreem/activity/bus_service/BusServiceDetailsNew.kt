package com.nas.alreem.activity.bus_service

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.nas.alreem.R
import com.nas.alreem.constants.ConstantFunctions

class BusServiceDetailsNew : AppCompatActivity() {
    lateinit var mContext: Context


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bus_service_details_page)
        mContext = this

        initfn()
        // progressDialogAdd.visibility = View.VISIBLE
        if (ConstantFunctions.internetCheck(mContext)) {
            //  callPaymentInformation()
        } else {
            //  DialogFunctions.showInternetAlertDialog(mContext)
        }
    }

    private fun initfn() {

    }
}
