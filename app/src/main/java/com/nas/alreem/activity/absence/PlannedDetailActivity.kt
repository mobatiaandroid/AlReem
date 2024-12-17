package com.nas.alreem.activity.absence

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


class PlannedDetailActivity: AppCompatActivity() {
    lateinit var mContext: Context
    lateinit var backRelative: RelativeLayout
    lateinit var heading: TextView
    lateinit var logoClickImgView: ImageView
    lateinit var stnameValue: TextView
    lateinit var studClassValue: TextView
    lateinit var leaveDateFromValue: TextView
    lateinit var leaveDateToValue: TextView
    lateinit var reasonValue: TextView
    lateinit var statusValue:TextView
    lateinit var reasonRejectionTxt:TextView
    lateinit var reasonRejectionLinear: LinearLayout
    lateinit var reasonRejectionScroll: ScrollView
    lateinit var status_txt:TextView

    var reason:String?=""
    var studentName:String?=""
    var studentClass:String?=""
    var fromDate:String?=""
    var toDate:String?=""
    var status_pickup:String?=""
    var reason_for_rejection:String=""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_planned_leaves_details)

        mContext=this
        initfn()

    }
    private fun initfn() {
        heading=findViewById(R.id.heading)
        heading.text= mContext.getString(R.string.planned_absence)
        backRelative=findViewById(R.id.backRelative)
        logoClickImgView=findViewById(R.id.logoClickImgView)
        reason=intent.getStringExtra("reason")
        studentName=intent.getStringExtra("studentName")
        studentClass=intent.getStringExtra("studentClass")
        fromDate=intent.getStringExtra("fromDate")
        toDate=intent.getStringExtra("toDate")
        status_pickup= intent.getStringExtra("status")
        reason_for_rejection=intent.getStringExtra("reason_for_rejection").toString()

        stnameValue = findViewById<TextView>(R.id.stnameValue)
        studClassValue = findViewById<TextView>(R.id.studClassValue)
        leaveDateFromValue = findViewById<TextView>(R.id.leaveDateFromValue)
        leaveDateToValue = findViewById<TextView>(R.id.leaveDateToValue)
        reasonValue = findViewById<TextView>(R.id.reasonValue)
        reasonRejectionTxt=findViewById(R.id.reasonRejectValue)
        reasonRejectionLinear=findViewById(R.id.reasonRejectlayout)
        reasonRejectionScroll=findViewById(R.id.reasonRejectionScroll)
        status_txt=findViewById(R.id.statusValue)

        stnameValue.text = studentName
        studClassValue.text = studentClass


        if (status_pickup.equals("0")){
            status_txt.text = "PENDING"
            reasonRejectionLinear.visibility= View.GONE
            reasonRejectionScroll.visibility=View.GONE
        }
        else if(status_pickup.equals("1")){
            status_txt.text = "APPROVED"
            reasonRejectionLinear.visibility= View.GONE
            reasonRejectionScroll.visibility=View.GONE
        }
        else{
            status_txt.text = "REJECTED"
            reasonRejectionLinear.visibility= View.VISIBLE
            reasonRejectionScroll.visibility=View.VISIBLE
            reasonRejectionTxt.text=reason_for_rejection

        }
        val fromdate=fromDate
        val todate=toDate
        val inputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
        val outputFormat: DateFormat = SimpleDateFormat("dd MMMM yyyy")
        val inputDateStr = fromdate
        val date: Date = inputFormat.parse(inputDateStr)
        val outputDateStr: String = outputFormat.format(date)




        if (todate!=""){
            val inputFormat1: DateFormat = SimpleDateFormat("yyyy-MM-dd")
            val outputFormat1: DateFormat = SimpleDateFormat("dd MMMM yyyy")
            val inputDateStr1 = todate
            val date1: Date = inputFormat1.parse(inputDateStr1)
            val outputDateStr1: String = outputFormat1.format(date1)
            leaveDateToValue.text = outputDateStr1
            leaveDateFromValue.text = outputDateStr
            reasonValue.text = reason

        }else{
            leaveDateFromValue.text = outputDateStr
            reasonValue.text = reason
            leaveDateToValue.text = "-"

        }


        backRelative.setOnClickListener(View.OnClickListener {
            finish()
        })
      //  heading.text= ConstantWords.absence
        logoClickImgView.setOnClickListener(View.OnClickListener {
            val intent = Intent(mContext, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        })
    }
}