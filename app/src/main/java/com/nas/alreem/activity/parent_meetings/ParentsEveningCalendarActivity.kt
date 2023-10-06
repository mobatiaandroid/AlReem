package com.nas.alreem.activity.parent_meetings

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.nas.alreem.R
import com.nas.alreem.activity.home.HomeActivity
import com.nas.alreem.activity.parent_meetings.model.PtaDatesApiModel
import com.nas.alreem.activity.parent_meetings.model.PtaDatesModel
import com.nas.alreem.constants.ConstantFunctions
import com.nas.alreem.constants.ConstantWords
import com.nas.alreem.constants.DialogFunctions
import com.nas.alreem.constants.PreferenceManager
import com.nas.alreem.fragment.parent_meetings.model.ListStaffPtaApiModel
import com.nas.alreem.fragment.parent_meetings.model.ListStaffPtaModel
import com.nas.alreem.rest.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.util.Date

class ParentsEveningCalendarActivity:AppCompatActivity() {
    lateinit var mContext: Context
    lateinit var backRelative: RelativeLayout
    lateinit var heading: TextView
    lateinit var header: TextView
    lateinit var logoClickImgView: ImageView
    lateinit var progressDialogAdd: ProgressBar
    lateinit var arrow_prev: ImageView
    lateinit var arrow_nxt: ImageView
    lateinit var monthlist:Array<String>
    lateinit var week_day:Array<String>
    lateinit var nums_Array:ArrayList<String>
    var studentId:String=""
    var studentName:String=""
    var studentClass:String=""
    var staffId:String=""
    var staffName:String=""
    var month_total_days:Int?=null
    var count_month:Int?=null
    var count_year:Int?=null
    val dateTextView = arrayOfNulls<TextView>(42)
    lateinit var datesToPlot:ArrayList<String>

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.parent_meetings_calendar_activity)

        mContext=this
        initfn()
        headerfnc()
        daysinweek()
        onclick()

    }
    private fun initfn(){
        progressDialogAdd=findViewById(R.id.progressDialogAdd)
        studentId=intent.getStringExtra("studId").toString()
        studentName=intent.getStringExtra("studName").toString()
        studentClass=intent.getStringExtra("studClass").toString()
        staffId=intent.getStringExtra("staffId").toString()
        staffName=intent.getStringExtra("staffName").toString()
        PreferenceManager.setstaffId(mContext,staffId)
        heading=findViewById(R.id.heading)
        header=findViewById(R.id.Header)
        nums_Array= ArrayList()
        datesToPlot= ArrayList()
        arrow_prev=findViewById(R.id.arrow_back)
        arrow_nxt=findViewById(R.id.arrow_nxt)
        monthlist= resources.getStringArray(R.array.Months)
        week_day= resources.getStringArray(R.array.Weeks)
        backRelative=findViewById(R.id.backRelative)
        logoClickImgView=findViewById(R.id.logoClickImgView)
        backRelative.setOnClickListener(View.OnClickListener {
            finish()
        })
        heading.text=ConstantWords.parentmeetings
        logoClickImgView.setOnClickListener(View.OnClickListener {
            val intent = Intent(mContext, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        })
        setTextview()
        if (ConstantFunctions.internetCheck(mContext))
        {
            allotedDatedApi()
        }
        else
        {
            DialogFunctions.showInternetAlertDialog(mContext)
        }

    }
    private fun setTextview(){
        for (i in 0..41)
        {
            val resID: Int = mContext.resources.getIdentifier("textview_$i", "id", mContext.packageName)
            dateTextView[i]=findViewById(resID)
            dateTextView[i]!!.setBackgroundColor(Color.WHITE)
            dateTextView[i]!!.setTextColor(Color.BLACK)
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun headerfnc(){
        var x=9
        var currentMonth=monthlist[x]
        var month= Calendar.getInstance()
        var simpleDateFormat = SimpleDateFormat("MM")
        var s=SimpleDateFormat("MMMM")
        var month_tt=s.format(month.time)
        var dateMonth = simpleDateFormat.format(month.time).toString()
        var simpleDateFormat2 = SimpleDateFormat("yyyy")
        var dateYear = simpleDateFormat2.format(month.time).toString()
        count_month=dateMonth.toInt()-1
        count_year=dateYear.toInt()
        header.text = month_tt +" " +count_year

        arrow_prev.setOnClickListener {
            nums_Array= ArrayList()
            if (count_month!=0)
            {
                count_month= count_month!! -1
                val m = monthlist[count_month!!]
                header.text = m + count_year
                daysinweek()
                setTextview()
                if (ConstantFunctions.internetCheck(mContext))
                {
                    allotedDatedApi()
                }
                else
                {
                    DialogFunctions.showInternetAlertDialog(mContext)
                }

                //holiday()
            }
            else
            {
                count_year= count_year!! -1
                val m = monthlist[11]
                count_month=11
                header.text = m + count_year
                daysinweek()
                setTextview()
                if (ConstantFunctions.internetCheck(mContext))
                {
                    allotedDatedApi()
                }
                else
                {
                    DialogFunctions.showInternetAlertDialog(mContext)
                }

                // holiday()
            }
        }
        arrow_nxt.setOnClickListener {

            nums_Array= ArrayList()
            if (count_month!=11)
            {
                count_month= count_month!! +1
                val m = monthlist[count_month!!]
                header.text = m + count_year
                daysinweek()
                setTextview()
                if (ConstantFunctions.internetCheck(mContext))
                {
                    allotedDatedApi()
                }
                else
                {
                    DialogFunctions.showInternetAlertDialog(mContext)
                }

                //holiday()
            }
            else
            {
                count_year= count_year!! +1
                val m = monthlist[0]
                count_month=0
                header.text = m + count_year
                daysinweek()
                setTextview()
                if (ConstantFunctions.internetCheck(mContext))
                {
                    allotedDatedApi()
                }
                else
                {
                    DialogFunctions.showInternetAlertDialog(mContext)
                }

                // holiday()
            }
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun daysinweek(){

        var count_month2= count_month!!+1
        val yearMonthObject: YearMonth = YearMonth.of(count_year!!, count_month2)
        val firstday_date: LocalDate? =yearMonthObject.atDay(1)
        val day_name: DayOfWeek = firstday_date!!.dayOfWeek
        month_total_days=yearMonthObject.lengthOfMonth()


        if (day_name.toString().equals("MONDAY"))
        {
            for (i in 1..month_total_days!!)
            {
                nums_Array.add(i.toString())
            }
        }

        else if (day_name.toString().equals("TUESDAY"))
        {
            nums_Array.add("0")
            for (i in 1..month_total_days!!)
            {
                nums_Array.add(i.toString())
            }
        }

        else if (day_name.toString().equals("WEDNESDAY"))
        {
            nums_Array.add("0")
            nums_Array.add("0")
            for (i in 1..month_total_days!!)
            {
                nums_Array.add(i.toString())
            }
        }

        else if (day_name.toString().equals("THURSDAY"))
        {
            nums_Array.add("0")
            nums_Array.add("0")
            nums_Array.add("0")
            for (i in 1..month_total_days!!)
            {
                nums_Array.add(i.toString())
            }
        }

        else if (day_name.toString().equals("FRIDAY"))
        {
            nums_Array.add("0")
            nums_Array.add("0")
            nums_Array.add("0")
            nums_Array.add("0")
            for (i in 1..month_total_days!!)
            {
                nums_Array.add(i.toString())
            }
        }

        else if (day_name.toString().equals("SATURDAY"))
        {
            nums_Array.add("0")
            nums_Array.add("0")
            nums_Array.add("0")
            nums_Array.add("0")
            nums_Array.add("0")
            for (i in 1..month_total_days!!)
            {
                nums_Array.add(i.toString())
            }
        }

        else if (day_name.toString().equals("SUNDAY"))
        {
            nums_Array.add("0")
            nums_Array.add("0")
            nums_Array.add("0")
            nums_Array.add("0")
            nums_Array.add("0")
            nums_Array.add("0")
            for (i in 1..month_total_days!!)
            {
                nums_Array.add(i.toString())
            }
        }

        for (i in 0..41){
            if (i<=nums_Array.size-1)
            {
                dateTextView[i]!!.visibility= View.VISIBLE
                var value=nums_Array.get(i).toString()

                if (value.equals("0"))
                {
                    dateTextView[i]!!.visibility= View.INVISIBLE
                }
                else
                {
                    dateTextView[i]!!.visibility= View.VISIBLE
                    dateTextView[i]!!.text = value.toString()
                }
            }
            else
            {
                dateTextView[i]!!.visibility= View.INVISIBLE
            }
        }
    }
    private fun onclick(){

        for (i in 0..41) {
            dateTextView[i]!!.setBackgroundColor(Color.WHITE)
            dateTextView[i]!!.setOnClickListener {

                var c_day=nums_Array.get(i)
                var c_month= count_month!! +1
                var c_year=count_year
                var c_date=c_day+"/"+c_month+"/"+c_year

                for (i in 0..datesToPlot.size-1){

                    if (c_date.equals(datesToPlot[i])){
                        val intent = Intent(mContext, ParentMeetingDetailActivity::class.java)
                        intent.putExtra("date",c_date)
                        intent.putExtra("studentName",studentName)
                        intent.putExtra("studentId",studentId)
                        intent.putExtra("studentClass",studentClass)
                        intent.putExtra("staffName",staffName)
                        intent.putExtra("staffId",staffId)
                        startActivity(intent)
                        break
                    }

                }
            }
        }
    }
    private fun allotedDatedApi(){
        //datesToPlot=ArrayList()
        progressDialogAdd.visibility = View.VISIBLE
        val token = PreferenceManager.getaccesstoken(mContext)
        val ptadatesSuccessBody = PtaDatesApiModel(studentId,staffId)
        val call: Call<PtaDatesModel> = ApiClient.getClient.pta_allotted_dates(ptadatesSuccessBody,"Bearer "+token)
        call.enqueue(object : Callback<PtaDatesModel> {
            override fun onFailure(call: Call<PtaDatesModel>, t: Throwable) {
                Log.e("Error", t.localizedMessage)
                progressDialogAdd.visibility = View.GONE
            }
            override fun onResponse(call: Call<PtaDatesModel>, response: Response<PtaDatesModel>) {
                progressDialogAdd.visibility = View.GONE
                //val arraySize :Int = response.body()!!.responseArray.studentList.size
                if (response.body()!!.status==100) {
                    val datelistSize = response.body()!!.data.size - 1
                    if (response.body()!!.data.size > 0){
                        for (i in 0..datelistSize) {
                            var dates = response.body()!!.data[i]
                            val inputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
                            val outputFormat: DateFormat = SimpleDateFormat("d/M/yyyy")
                            val inputDateStr = dates
                            val date: Date = inputFormat.parse(inputDateStr)
                            val outputDateStr: String = outputFormat.format(date)
                            Log.e("dt", outputDateStr)
                            datesToPlot.add(i, outputDateStr)
                        }
                    for (i in 0..datesToPlot.size - 1) {
                        var days_s = datesToPlot[i]

                        Log.e("days_s", days_s)

                        for (i in 0..nums_Array.size - 1) {

                            var c_day = nums_Array.get(i)
                            var c_month = count_month!! + 1
                            var c_year = count_year
                            var c_date = c_day + "/" + c_month + "/" + c_year
                            Log.e("c_date", c_date)

                            if (days_s.equals(c_date)) {

                                Log.e("match", "match")
                                dateTextView[i]!!.setBackgroundResource(R.drawable.roundred)
                                dateTextView[i]!!.setTextColor(Color.WHITE)

                            } else {
                                //Log.e("no_match","no_match")

                            }
                        }
                    }
                }else{
                        Toast.makeText(
                            mContext,
                            "No dates available " ,
                            Toast.LENGTH_SHORT
                        ).show()
                }
                } else
                {

                    DialogFunctions.commonErrorAlertDialog(mContext.resources.getString(R.string.alert), ConstantFunctions.commonErrorString(response.body()!!.status), mContext)
                }


            }

        })
    }

}