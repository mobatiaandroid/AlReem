package com.nas.alreem.activity.intentions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.nas.alreem.R
import com.nas.alreem.activity.home.HomeActivity
import com.nas.alreem.constants.ConstantFunctions
import com.nas.alreem.constants.ConstantWords
import com.nas.alreem.constants.PreferenceManager
import com.nas.alreem.fragment.intention.model.IntentionListAPIResponseModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date

class IntentionDetailedView : AppCompatActivity() {
    lateinit var mContext: Context
    lateinit var backRelative: RelativeLayout
    lateinit var heading: TextView
    lateinit var logoClickImgView: ImageView
    lateinit var stud_name: TextView
    lateinit var stud_class: TextView
    lateinit var pickup_name: TextView
    lateinit var reasonTxt: TextView
    lateinit var linear_Option_question: LinearLayout
    lateinit var linear_answer: LinearLayout
    lateinit var option_question: TextView
    lateinit var answeroption: TextView
    var studname_pickup: String = ""
    var studcls_pickup: String = ""
    var pickby_pickup: String = ""
    var reason_pickup: String = ""
    var titlle: String = ""
    var descptn: String = ""
    var parent_name: String = ""
    var selectedoptionanswer: String = ""
    var options:String =""
    var receivedOptions: ArrayList<IntentionListAPIResponseModel.Option> = ArrayList()
    lateinit var title: TextView
    lateinit var desc: TextView
    lateinit var sub_btn: Button
    lateinit var activity: Activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intention_detail_view)

        mContext = this
        activity=this
        initfn()

    }

    private fun initfn() {
        heading = findViewById(R.id.heading)
        heading.text = "Intention Details"
        backRelative = findViewById(R.id.backRelative)
        logoClickImgView = findViewById(R.id.logoClickImgView)
        //  timeofPickup=findViewById(R.id.leaveDateToValue)
        option_question = findViewById(R.id.option_question)
        title = findViewById(R.id.title)
        desc = findViewById(R.id.desc)
        linear_Option_question = findViewById(R.id.linear_Option_question)
        linear_answer = findViewById(R.id.linear_answer)
        answeroption = findViewById(R.id.answeroption)
        sub_btn = findViewById(R.id.sub_btn)
        studname_pickup = intent.getStringExtra("student").toString()
        titlle = intent.getStringExtra("title").toString()
        descptn = intent.getStringExtra("description").toString()
        studcls_pickup = intent.getStringExtra("classs").toString()
        pickby_pickup = intent.getStringExtra("question").toString()
        reason_pickup = intent.getStringExtra("options").toString()
        parent_name = intent.getStringExtra("parent_name").toString()
        selectedoptionanswer = intent.getStringExtra("selectedchoice").toString()
        receivedOptions = intent.getParcelableArrayListExtra("optionsarray")!!

        sub_btn.setOnClickListener {
            finish()
        }
        var flag = 1

        for (i in receivedOptions.indices) {

            if (reason_pickup.equals(receivedOptions.get(i).option)) {

                flag = 2
                options =receivedOptions.get(i).optionQuestion!!

            }

            /*if (receivedOptions.get(i).optionQuestion.isNullOrEmpty()) {
                linear_Option_question.visibility = View.GONE
                linear_answer.visibility = View.GONE
            } else {
                if (reason_pickup.equals(receivedOptions.get(i).option)) {


                    linear_Option_question.visibility = View.VISIBLE
                    linear_answer.visibility = View.VISIBLE
                    option_question.text = receivedOptions.get(i).optionQuestion
                    answeroption.text = selectedoptionanswer


                }

            }*/

        }

         if (flag==2)
        {

if (selectedoptionanswer.equals(""))
{
    linear_Option_question.visibility = View.GONE
    linear_answer.visibility = View.GONE
}
            else
{
    linear_Option_question.visibility = View.VISIBLE
    linear_answer.visibility = View.VISIBLE
    option_question.text = options
    answeroption.text = selectedoptionanswer
}

        }
        else
         {

             linear_Option_question.visibility = View.GONE
             linear_answer.visibility = View.GONE
         }
        stud_name = findViewById(R.id.stnameValue)
        stud_name.text = parent_name
        stud_class = findViewById(R.id.studClassValue)
        stud_class.text = PreferenceManager.getEmailId(mContext)

        title.text = titlle
        desc.setText(Html.fromHtml(descptn));
        pickup_name = findViewById(R.id.mailtxt)
        pickup_name.text = pickby_pickup
        reasonTxt = findViewById(R.id.statustxt)
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
    override fun onResume() {
        super.onResume()
        if (!ConstantFunctions.runMethod.equals("Dev")) {
            if (ConstantFunctions().isDeveloperModeEnabled(com.nas.alreem.fragment.home.mContext)) {
                ConstantFunctions().showDeviceIsDeveloperPopUp(activity)
            }
        }
    }
}