package com.nas.alreem.activity.intentions

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.nas.alreem.R
import com.nas.alreem.activity.home.HomeActivity
import com.nas.alreem.constants.ConstantWords
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date

class IntentionDetailedView : AppCompatActivity(){
    lateinit var mContext: Context
    lateinit var backRelative: RelativeLayout
    lateinit var heading: TextView
    lateinit var logoClickImgView: ImageView
    lateinit var stud_name: TextView
    lateinit var stud_class: TextView
    lateinit var pickup_name: TextView
    lateinit var reasonTxt: TextView
    lateinit var reasonRejectionLinear: LinearLayout
    lateinit var reasonRejectionScroll: ScrollView

    var studname_pickup:String=""
    var studcls_pickup:String=""
    var pickby_pickup:String=""
    var reason_pickup:String=""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intention_detail_view)

        mContext=this
        initfn()

    }
    private fun initfn() {
        heading=findViewById(R.id.heading)
        heading.text= "Intention Details"
        backRelative=findViewById(R.id.backRelative)
        logoClickImgView=findViewById(R.id.logoClickImgView)
      //  timeofPickup=findViewById(R.id.leaveDateToValue)



        studname_pickup=intent.getStringExtra("student").toString()
        studcls_pickup=intent.getStringExtra("classs").toString()
        pickby_pickup=intent.getStringExtra("question").toString()
        reason_pickup=intent.getStringExtra("options").toString()



        stud_name=findViewById(R.id.stnameValue)
        stud_name.text = studname_pickup
        stud_class=findViewById(R.id.studClassValue)
        stud_class.text = studcls_pickup



        pickup_name=findViewById(R.id.mailtxt)
        pickup_name.text = pickby_pickup
        reasonTxt=findViewById(R.id.statustxt)
        reasonTxt.text = reason_pickup

        backRelative.setOnClickListener(View.OnClickListener {
            finish()
        })
        //heading.text= ConstantWords.bus_service
        logoClickImgView.setOnClickListener(View.OnClickListener {
            val intent = Intent(mContext, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        })
    }
}