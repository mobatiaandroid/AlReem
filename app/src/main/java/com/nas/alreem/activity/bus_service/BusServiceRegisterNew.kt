package com.nas.alreem.activity.bus_service

import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.nas.alreem.R
import com.nas.alreem.activity.payments.model.InfoListModel
import com.nas.alreem.constants.ConstantFunctions
import com.nas.alreem.constants.DialogFunctions

class BusServiceRegisterNew : AppCompatActivity() {
    lateinit var mContext: Context
    private lateinit var logoClickImg: ImageView
    lateinit var recyclerview: RecyclerView
    lateinit var back: ImageView
    lateinit var informationlist: ArrayList<InfoListModel>
    lateinit var heading: TextView
    lateinit var logoClickImgView: ImageView
    lateinit var backRelative: RelativeLayout
    lateinit var progressDialogAdd: ProgressBar
    lateinit var parentsdetailslinear : LinearLayout
    lateinit var parentsdetailslinear1 : LinearLayout
    lateinit var checkPassportLinear : LinearLayout
    lateinit var addresslinear : RelativeLayout
    lateinit var parentlinear : RelativeLayout
    lateinit var addressinfo_linear : LinearLayout
    lateinit var downarrowImage : ImageView
    lateinit var downarrowAddress : ImageView
    var flag:Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bus_reg_details_forms)
        mContext=this
        initfn()

        if (ConstantFunctions.internetCheck(mContext)) {

        } else {

        }

    }

    private fun initfn() {
        parentsdetailslinear=findViewById(R.id.parentsdetailslinear)
        parentsdetailslinear1=findViewById(R.id.parentsdetailslinear1)
        checkPassportLinear=findViewById(R.id.checkPassportLinear)
        addresslinear=findViewById(R.id.addresslinear)
        addressinfo_linear=findViewById(R.id.addressinfo_linear)
        parentlinear = findViewById(R.id.parentlinear)
        downarrowImage = findViewById(R.id.downarrowImage)
        downarrowAddress = findViewById(R.id.downarrowAddress)
        parentlinear.setOnClickListener {

            if(flag)
            {
                parentsdetailslinear.visibility=View.VISIBLE
                parentsdetailslinear1.visibility=View.VISIBLE
                checkPassportLinear.visibility=View.VISIBLE
                downarrowImage.rotation= 180F
              //  parentsdetailslinear1.visibility=View.VISIBLE


            }
            else
            {
                parentsdetailslinear.visibility=View.GONE
                parentsdetailslinear1.visibility=View.GONE
                checkPassportLinear.visibility=View.GONE
                downarrowImage.rotation= 0F

            }
            flag = !flag
        }
        addresslinear.setOnClickListener {
            if(flag)
            {
                addressinfo_linear.visibility=View.VISIBLE
                downarrowAddress.rotation=180f


            }
            else
            {
                addressinfo_linear.visibility=View.GONE
                downarrowAddress.rotation=0f

            }
            flag = !flag
        }






    }
}