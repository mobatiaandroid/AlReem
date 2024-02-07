package com.nas.alreem.activity.bus_service

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.nas.alreem.R
import com.nas.alreem.activity.absence.model.EarlyPickupListArray
import com.nas.alreem.activity.absence.model.ListAbsenceApiModel
import com.nas.alreem.activity.payments.adapter.StudentListAdapter
import com.nas.alreem.activity.payments.model.StudentList
import com.nas.alreem.activity.payments.model.StudentListModel
import com.nas.alreem.constants.ApiClient
import com.nas.alreem.constants.ConstantFunctions
import com.nas.alreem.constants.ConstantWords
import com.nas.alreem.constants.DialogFunctions
import com.nas.alreem.constants.OnItemClickListener
import com.nas.alreem.constants.PreferenceManager
import com.nas.alreem.constants.addOnItemClickListener
import com.nas.alreem.fragment.bus_service.adapter.RequestBusserviceRecyclerAdapter
import com.nas.alreem.fragment.bus_service.model.BusServiceDetail
import com.nas.alreem.fragment.bus_service.model.BusserviceResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BusServiceActivity : AppCompatActivity() {
    lateinit var mContext: Context
    private lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var progressDialogAdd: ProgressBar
    lateinit var studentSpinner: LinearLayout
    var studentListArrayList = ArrayList<StudentList>()
    lateinit var studImg: ImageView
    lateinit var studentName: String
    lateinit var studentId: String
    lateinit var studentImg: String
    lateinit var studentClass: String
    lateinit var studentNameTxt: TextView
    lateinit var newRequestAbsence: TextView
    lateinit var newRequestPickup: TextView
    lateinit var mAbsenceListView: RecyclerView
    lateinit var mPickupListView: RecyclerView
    lateinit var pickup_list:ArrayList<EarlyPickupListArray>
    lateinit var pickupListSort:ArrayList<EarlyPickupListArray>
    lateinit var studentAbsenceCopy :ArrayList<BusServiceDetail>
    var studentAbsenceArrayList = ArrayList<BusServiceDetail>()
    lateinit var heading:TextView
    lateinit var titleTextView:TextView

    var select_val:Int=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bus_service_fragment)

        mContext=this
        initfn()
        if (ConstantFunctions.internetCheck(mContext))
        {
            callStudentList()
        }
        else
        {
            DialogFunctions.showInternetAlertDialog(mContext)
        }

    }

    private fun initfn() {
        progressDialogAdd = findViewById(R.id.progressDialogAdd)
        progressDialogAdd.visibility= View.VISIBLE
        studentSpinner =findViewById<LinearLayout>(R.id.studentSpinner)
        studImg = findViewById<ImageView>(R.id.imagicon)
        studentNameTxt = findViewById<TextView>(R.id.studentName)
        titleTextView = findViewById(R.id.titleTextView)
        titleTextView.text= ConstantWords.bus_service
        newRequestAbsence = findViewById(R.id.newRequestAbsence)
        newRequestPickup =findViewById(R.id.newRequestEarly)
        mAbsenceListView = findViewById(R.id.mAbsenceListView) as RecyclerView
        mPickupListView=findViewById(R.id.mPickupListView)
        pickup_list= ArrayList()
        pickupListSort=ArrayList()
        heading=findViewById(R.id.appregisteredHint)

        linearLayoutManager = LinearLayoutManager(mContext)
        mAbsenceListView.layoutManager = linearLayoutManager
        mAbsenceListView.itemAnimator = DefaultItemAnimator()
        // absence_btn.setBackgroundResource(R.drawable.event_spinnerfill)
        studentSpinner.setOnClickListener(){
            showStudentsList(mContext,studentListArrayList)


        }
        mAbsenceListView.addOnItemClickListener(object: OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                // Your logic
                val intent = Intent(mContext, BusServiceDetailActivity::class.java)
                intent.putExtra("studentName", PreferenceManager.getStudentName(mContext))
                intent.putExtra("studentClass", PreferenceManager.getStudentClass(mContext))
                intent.putExtra("date",studentAbsenceCopy.get(position).pickup_date)
                intent.putExtra("time",studentAbsenceCopy.get(position).requested_time)
                intent.putExtra("pickupby",studentAbsenceCopy.get(position).requested_on)
                intent.putExtra("reason",studentAbsenceCopy.get(position).reason)
                intent.putExtra("status",studentAbsenceCopy.get(position).status)
                intent.putExtra("reason_for_rejection",studentAbsenceCopy.get(position).reason_for_rejection)
                startActivity(intent)
            }
        })
        newRequestAbsence.setOnClickListener {
            val intent = Intent(mContext, RequestBusServiceActivity::class.java)
            // intent.putExtra("studentClass",PreferenceManager.getStudentClass(mContext))
           startActivity(intent)
        }
    }
    private fun showStudentsList(mContext: Context, mStudentArray: ArrayList<StudentList>) {
        val dialog = Dialog(mContext)
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
        if(mStudentArray.size>0)
        {
            val studentAdapter = StudentListAdapter(mContext,mStudentArray)
            studentListRecycler.adapter = studentAdapter
        }

        btn_dismiss.setOnClickListener()
        {
            dialog.dismiss()
        }
        studentListRecycler.addOnItemClickListener(object: OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                // Your logic
                progressDialogAdd.visibility=View.VISIBLE

                studentName=studentListArrayList.get(position).name
                studentImg=studentListArrayList.get(position).photo
                studentId=studentListArrayList.get(position).id
                studentClass=studentListArrayList.get(position).section
                PreferenceManager.setStudentID(mContext,studentId)
                PreferenceManager.setStudentName(mContext,studentName)
                PreferenceManager.setStudentPhoto(mContext,studentImg)
                PreferenceManager.setStudentClass(mContext,studentClass)

                studentNameTxt.text=studentName
                studentAbsenceArrayList.clear()
                pickupListSort.clear()
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
                progressDialogAdd.visibility = View.VISIBLE
                getBusServiceListAPI()
                dialog.dismiss()
            }
        })
        dialog.show()
    }
    private fun getBusServiceListAPI()
    {
        studentAbsenceCopy=ArrayList<BusServiceDetail>()
        studentAbsenceArrayList.clear()
        mAbsenceListView.visibility=View.GONE
        progressDialogAdd.visibility=View.VISIBLE
        // val studentInfoAdapter = RequestAbsenceRecyclerAdapter(studentAbsenceArrayList)
        // mAbsenceListView.adapter = studentInfoAdapter
        val token = PreferenceManager.getaccesstoken(mContext)
        val pickupSuccessBody = ListAbsenceApiModel(PreferenceManager.getStudentID(mContext).toString(),0,20)
        val call: Call<BusserviceResponseModel> =
            ApiClient.getClient.listbusservice(pickupSuccessBody, "Bearer " + token)
        call.enqueue(object : Callback<BusserviceResponseModel> {
            override fun onFailure(call: Call<BusserviceResponseModel>, t: Throwable) {

                progressDialogAdd.visibility=View.GONE
                //mProgressRelLayout.visibility=View.INVISIBLE
            }

            override fun onResponse(call: Call<BusserviceResponseModel>, response: Response<BusserviceResponseModel>) {
                val responsedata = response.body()
                //progressDialog.visibility = View.GONE

                progressDialogAdd.visibility=View.GONE
                if (responsedata != null) {
                    try {

                        if (response.body()!!.status==100) {
                            studentAbsenceCopy.addAll(response.body()!!.bus_services)
                            studentAbsenceArrayList=studentAbsenceCopy

                            if (studentAbsenceArrayList.size>0)
                            {
                                mAbsenceListView.visibility=View.VISIBLE
                                val studentInfoAdapter = RequestBusserviceRecyclerAdapter(studentAbsenceArrayList)
                                mAbsenceListView.adapter = studentInfoAdapter
                            }
                            else{
                                Toast.makeText(mContext, "No Registered Bus Service Found", Toast.LENGTH_SHORT).show()
                                mAbsenceListView.visibility=View.GONE
                            }


                        }else if(response.body()!!.status==103){
                            // callStudentLeaveInfo()
                        }
                        else
                        {

                            DialogFunctions.commonErrorAlertDialog(mContext.resources.getString(R.string.alert), ConstantFunctions.commonErrorString(response.body()!!.status), mContext)
                        }

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

        })
    }
    private fun callStudentList()
    {
        progressDialogAdd.visibility=View.VISIBLE
        studentListArrayList= ArrayList()
        val call: Call<StudentListModel> = ApiClient.getClient.studentList("Bearer "+PreferenceManager.getaccesstoken(mContext))
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
                            getBusServiceListAPI()
                        }
                        else
                        {

                            DialogFunctions.commonErrorAlertDialog(mContext.resources.getString(R.string.alert), ConstantFunctions.commonErrorString(response.body()!!.status), mContext)
                        }


                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

        })
    }
    override fun onResume() {
        super.onResume()

        mPickupListView.visibility = View.GONE
        mAbsenceListView.visibility = View.GONE
        studentNameTxt.text = PreferenceManager.getStudentName(mContext)
        studentId = PreferenceManager.getStudentID(mContext).toString()
        studentImg = PreferenceManager.getStudentPhoto(mContext)!!
        if (!studentImg.equals("")) {
            Glide.with(mContext) //1
                .load(studentImg)
                .placeholder(R.drawable.student)
                .error(R.drawable.student)
                .skipMemoryCache(true) //2
                .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                .transform(CircleCrop()) //4
                .into(studImg)
        } else {
            studImg.setImageResource(R.drawable.student)
        }

        progressDialogAdd.visibility = View.VISIBLE
        if (ConstantFunctions.internetCheck(mContext))
        {
            getBusServiceListAPI()
        }
        else
        {
            DialogFunctions.showInternetAlertDialog(mContext)
        }



    }
}