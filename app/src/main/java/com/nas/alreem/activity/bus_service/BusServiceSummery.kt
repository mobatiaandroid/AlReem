package com.nas.alreem.activity.bus_service

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.LocationServices
import com.nas.alreem.R
import com.nas.alreem.activity.bus_service.adapter.BusEapDaysAdapter
import com.nas.alreem.activity.bus_service.adapter.BusserviceSummeryAdapter
import com.nas.alreem.activity.bus_service.reportabsence.BusServiceDetailActivity
import com.nas.alreem.constants.ConstantFunctions
import com.nas.alreem.constants.OnItemClickListener
import com.nas.alreem.constants.PreferenceManager
import com.nas.alreem.constants.addOnItemClickListener

class BusServiceSummery : AppCompatActivity() {
    lateinit var mContext: Context
    lateinit var recycler_view_cca : RecyclerView
    lateinit var summerylistaary:ArrayList<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bus_service_summery_page)
        mContext=this

        initfn()
        // progressDialogAdd.visibility = View.VISIBLE
        if (ConstantFunctions.internetCheck(mContext)) {
            //  callPaymentInformation()
        } else {
            //  DialogFunctions.showInternetAlertDialog(mContext)
        }

    }

    private fun initfn() {
        summerylistaary = ArrayList()

        recycler_view_cca=findViewById(R.id.recycler_view_cca)
        var linearLayoutManager = LinearLayoutManager(mContext)
        recycler_view_cca.layoutManager = linearLayoutManager
        summerylistaary.add("FirstList")

        var newsLetterAdapter= BusserviceSummeryAdapter(summerylistaary,mContext)
        recycler_view_cca.adapter=newsLetterAdapter

        recycler_view_cca.addOnItemClickListener(object: OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                // Your logic
                val intent = Intent(mContext, BusServiceDetailsNew::class.java)

                startActivity(intent)
            }
        })

    }
}