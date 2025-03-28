package com.nas.alreem.activity.bus_service

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.google.android.gms.location.LocationServices
import com.nas.alreem.R
import com.nas.alreem.activity.bus_service.adapter.BusEapDaysAdapter
import com.nas.alreem.activity.bus_service.adapter.BusserviceSummeryAdapter
import com.nas.alreem.activity.bus_service.model.DetailsResponseModel
import com.nas.alreem.activity.bus_service.model.StudentDetailsModel
import com.nas.alreem.activity.bus_service.model.SummeryBusResponseArray
import com.nas.alreem.activity.bus_service.model.SummeryBusResponseModel
import com.nas.alreem.activity.bus_service.reportabsence.BusServiceDetailActivity
import com.nas.alreem.activity.home.HomeActivity
import com.nas.alreem.activity.payments.adapter.StudentListAdapter
import com.nas.alreem.activity.payments.model.StudentList
import com.nas.alreem.activity.payments.model.StudentListModel
import com.nas.alreem.constants.ApiClient
import com.nas.alreem.constants.ConstantFunctions
import com.nas.alreem.constants.OnItemClickListener
import com.nas.alreem.constants.PreferenceManager
import com.nas.alreem.constants.addOnItemClickListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date

class BusServiceSummery : AppCompatActivity() {
    lateinit var mContext: Context
    lateinit var recycler_view_cca : RecyclerView
    lateinit var summerylistaary:ArrayList<SummeryBusResponseArray>
    var studentListArrayList = ArrayList<StudentList>()
    lateinit var studImg: ImageView
    lateinit var studentName: String
    lateinit var studentId: String
    lateinit var studentImg: String
    lateinit var studentClass: String
    lateinit var studentNameTxt: TextView
    lateinit var progressDialogAdd: ProgressBar
    lateinit var studentSpinner: LinearLayout
    lateinit var heading: TextView
    lateinit var btn_left:ImageView
    lateinit var logoClickImgView:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bus_service_summery_page)
        mContext=this

        initfn()

        // progressDialogAdd.visibility = View.VISIBLE
        if (ConstantFunctions.internetCheck(mContext)) {
            callStudentListApi()

        } else {
            //  DialogFunctions.showInternetAlertDialog(mContext)
        }

    }

    private fun initfn() {
        studentSpinner = findViewById(R.id.studentSpinner)
        progressDialogAdd=findViewById(R.id.progressDialogAdd)
        studImg = findViewById<ImageView>(R.id.imagicon)
        studentNameTxt = findViewById<TextView>(R.id.studentName)
        recycler_view_cca=findViewById(R.id.recycler_view_cca)
        var linearLayoutManager = LinearLayoutManager(mContext)
        recycler_view_cca.layoutManager = linearLayoutManager
        heading=findViewById(R.id.heading)
        heading.text= "Registration Summery"
        btn_left=findViewById(R.id.btn_left)
        logoClickImgView=findViewById(R.id.logoClickImgView)
        studentSpinner.setOnClickListener(){
            showStudentList(mContext,studentListArrayList)
        }
        btn_left.setOnClickListener(View.OnClickListener {
            finish()
        })
        logoClickImgView.setOnClickListener(View.OnClickListener {
            val intent = Intent(mContext, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        })
        recycler_view_cca.addOnItemClickListener(object: OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                var address:String
                 var eaparray: String=""
                var eapdatesarray = ArrayList<String>()
               if(summerylistaary[position].type.equals("EAP"))
               {
                   for (j in summerylistaary[position].eap_dates.indices)
                   {
                       eaparray=summerylistaary[position].eap_dates.get(j)
                       Log.e("eaparray",eaparray)
                       val fromDate=eaparray
                       val inputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
                       val outputFormat: DateFormat = SimpleDateFormat("dd MMM yyyy")
                       val inputDateStr = fromDate
                       val date: Date = inputFormat.parse(inputDateStr)
                       val outputDateStr: String = outputFormat.format(date)
                       Log.e("outputDateStr",outputDateStr)
                       eapdatesarray.add(outputDateStr)
                   }
               }
                else{

               }



                //address=summerylistaary[position].parent_country+" "+summerylistaary[position].parent_address
                // Your logic
                val intent = Intent(mContext, BusServiceDetailsNew::class.java)
                intent.putExtra("id",summerylistaary[position].id)
                intent.putExtra("amount",summerylistaary[position].total_amount)
                intent.putExtra("name",summerylistaary[position].student_name)
                intent.putExtra("class",summerylistaary[position].class_name)
                intent.putExtra("parentname",summerylistaary[position].parent_name)
                intent.putExtra("mobileno",summerylistaary[position].parent_mobile)
                intent.putExtra("email",summerylistaary[position].parent_email)
                intent.putExtra("pickup",summerylistaary[position].pickup_point)
                intent.putExtra("droppoint",summerylistaary[position].drop_point)
               // intent.putExtra("droppoint",summerylistaary[position].drop_point)
                intent.putExtra("address",summerylistaary[position].parent_address)
                intent.putExtra("status",summerylistaary[position].status)
                intent.putExtra("title",summerylistaary[position].title)
                intent.putStringArrayListExtra("eaparray",eapdatesarray)
                intent.putExtra("type",summerylistaary[position].type)
               // Log.e("way",summerylistaary[position].way)
                intent.putExtra("way",summerylistaary[position].way)
                intent.putExtra("landmark",summerylistaary[position].landmark)

for (i in summerylistaary[position].invoice.indices )
{
    intent.putExtra("invoice",summerylistaary[position].invoice[i].invoice_ref)
    intent.putExtra("paidDate",summerylistaary[position].invoice[i].paid_date)
    intent.putExtra("invoicenotes",summerylistaary[position].invoice[i].invoice_note)

}
                startActivity(intent)
            }
        })

    }
    fun showStudentList(context: Context ,mStudentList : ArrayList<StudentList>)
    {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialogue_student_list)
        var iconImageView = dialog.findViewById(R.id.iconImageView) as ImageView
        var btn_dismiss = dialog.findViewById(R.id.btn_dismiss) as Button
        var studentListRecycler = dialog.findViewById(R.id.studentListRecycler) as RecyclerView
        iconImageView.setImageResource(R.drawable.boy)
        //if(mSocialMediaArray.get())
        val sdk = Build.VERSION.SDK_INT
        if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
            btn_dismiss.setBackgroundDrawable(
                mContext.resources.getDrawable(R.drawable.button_new)
            )
        } else {
            btn_dismiss.background = mContext.resources.getDrawable(R.drawable.button_new)
        }

        studentListRecycler.setHasFixedSize(true)
        val llm = LinearLayoutManager(mContext)
        llm.orientation = LinearLayoutManager.VERTICAL
        studentListRecycler.layoutManager = llm
        if(mStudentList.size>0)
        {
            val studentAdapter = StudentListAdapter(mContext,mStudentList)
            studentListRecycler.adapter = studentAdapter
        }

        btn_dismiss.setOnClickListener()
        {
            dialog.dismiss()
        }
        studentListRecycler.addOnItemClickListener(object: OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                // Your logic
                //progressDialogAdd.visibility=View.VISIBLE

                studentName=studentListArrayList.get(position).name
                studentImg=studentListArrayList.get(position).photo
                studentId=studentListArrayList.get(position).id
                studentClass=studentListArrayList.get(position).section
                PreferenceManager.setStudentID(mContext,studentId)
                PreferenceManager.setStudentName(mContext,studentName)
                PreferenceManager.setStudentPhoto(mContext,studentImg)
                PreferenceManager.setStudentClass(mContext,studentClass)
                studentNameTxt.text=studentName
                if(!studentImg.equals(""))
                {
                    Glide.with(mContext) //1
                        .load(studentImg)
                        .placeholder(R.drawable.student)
                        .error(R.drawable.student)
                        .skipMemoryCache(true) //2
                        .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                        .transform(CircleCrop()) //4
                        .into(studImg)
                }
                else
                {
                    studImg.setImageResource(R.drawable.student)
                }
                callSummeryBusDetails()
                //progressDialogAdd.visibility = View.VISIBLE

                dialog.dismiss()
            }
        })
        dialog.show()
    }
    fun callStudentListApi()
    {
        progressDialogAdd.visibility=View.VISIBLE
        studentListArrayList= ArrayList()
        val call: Call<StudentListModel> = ApiClient(mContext).getClient.studentList("Bearer "+ PreferenceManager.getaccesstoken(mContext))
        call.enqueue(object : Callback<StudentListModel> {
            override fun onFailure(call: Call<StudentListModel>, t: Throwable) {
                progressDialogAdd.visibility=View.GONE
            }
            override fun onResponse(call: Call<StudentListModel>, response: Response<StudentListModel>) {
                val responsedata = response.body()
                progressDialogAdd.visibility=View.GONE
                if (responsedata != null) {
                    try {

                        if (response.body()!!.status==100)
                        {
                            studentListArrayList=ArrayList()
                            studentListArrayList.addAll(response.body()!!.responseArray.studentList)
                            if (PreferenceManager.getStudentID(mContext).equals(""))
                            {
                                studentName=studentListArrayList.get(0).name
                                studentImg=studentListArrayList.get(0).photo
                                studentId=studentListArrayList.get(0).id
                                studentClass=studentListArrayList.get(0).section
                                PreferenceManager.setStudentID(mContext,studentId)
                                PreferenceManager.setStudentName(mContext,studentName)
                                PreferenceManager.setStudentPhoto(mContext,studentImg)
                                PreferenceManager.setStudentClass(mContext,studentClass)
                                studentNameTxt.text=studentName
                                if(!studentImg.equals(""))
                                {
                                    Glide.with(mContext) //1
                                        .load(studentImg)
                                        .placeholder(R.drawable.student)
                                        .error(R.drawable.student)
                                        .skipMemoryCache(true) //2
                                        .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                                        .transform(CircleCrop()) //4
                                        .into(studImg)
                                }
                                else{
                                    studImg.setImageResource(R.drawable.student)
                                }

                            }
                            else{
                                studentName= PreferenceManager.getStudentName(mContext)!!
                                studentImg= PreferenceManager.getStudentPhoto(mContext)!!
                                studentId= PreferenceManager.getStudentID(mContext)!!
                                studentClass= PreferenceManager.getStudentClass(mContext)!!
                                studentNameTxt.text=studentName
                                if(!studentImg.equals(""))
                                {
                                    Glide.with(mContext) //1
                                        .load(studentImg)
                                        .placeholder(R.drawable.student)
                                        .error(R.drawable.student)
                                        .skipMemoryCache(true) //2
                                        .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                                        .transform(CircleCrop()) //4
                                        .into(studImg)
                                }
                                else{
                                    studImg.setImageResource(R.drawable.student)
                                }
                            }
                            callSummeryBusDetails()
                        }
                        else
                        {

                            //DialogFunctions.commonErrorAlertDialog(mContext.resources.getString(R.string.alert), ConstantFunctions.commonErrorString(response.body()!!.status), mContext)
                        }


                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

        })
    }

    private fun callSummeryBusDetails() {

        summerylistaary = ArrayList()
        progressDialogAdd.visibility=View.VISIBLE

        var studentdetailsmodel = StudentDetailsModel(PreferenceManager.getStudentID(mContext)!!)
        val call: Call<SummeryBusResponseModel> = ApiClient(mContext).getClient.bus_payment_summary("Bearer " + PreferenceManager.getaccesstoken(mContext),
            studentdetailsmodel)
        call.enqueue(object : Callback<SummeryBusResponseModel> {
            override fun onFailure(call: Call<SummeryBusResponseModel>, t: Throwable) {
                progressDialogAdd.visibility=View.GONE

            }
            override fun onResponse(call: Call<SummeryBusResponseModel>, response: Response<SummeryBusResponseModel>) {
                val responsedata = response.body()
                if (response.isSuccessful()) {
        progressDialogAdd.visibility=View.GONE
                    if (response.body()!!.status==100) {

                        if (response.body()!!.responseArray.size > 0) {
                            for (i in response.body()!!.responseArray.indices) {
                                summerylistaary.add(response.body()!!.responseArray.get(i))
                            }


                            var newsLetterAdapter =
                                BusserviceSummeryAdapter(summerylistaary, mContext)
                            recycler_view_cca.adapter = newsLetterAdapter
                        }
                        else
                        {

                            var newsLetterAdapter =
                                BusserviceSummeryAdapter(summerylistaary, mContext)
                            recycler_view_cca.adapter = newsLetterAdapter
                            showDialogueWithOkSuccess(mContext, "No Data Found", "Alert")
                        }
                    }
                }

            }

        })
    }
}
fun showDialogueWithOkSuccess(context: Context, message: String, msgHead: String)
{
    val dialog = Dialog(context)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    dialog.setCancelable(false)
    dialog.setContentView(R.layout.dialog_common_error_alert)
    var iconImageView = dialog.findViewById(R.id.iconImageView) as? ImageView
    var alertHead = dialog.findViewById(R.id.alertHead) as? TextView
    var text_dialog = dialog.findViewById(R.id.messageTxt) as? TextView
    var btn_Ok = dialog.findViewById(R.id.btn_Ok) as Button
    text_dialog?.text = message
    alertHead?.text = msgHead
    btn_Ok.setOnClickListener()
    {
        dialog.dismiss()


    }
    dialog.show()
}