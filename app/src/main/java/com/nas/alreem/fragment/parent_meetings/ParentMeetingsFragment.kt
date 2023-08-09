package com.nas.alreem.fragment.parent_meetings

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.nas.alreem.R
import com.nas.alreem.activity.absence.model.ListAbsenceApiModel
import com.nas.alreem.activity.parent_meetings.ParentsEveningCalendarActivity
import com.nas.alreem.activity.parent_meetings.ParentsEveninginfoActivity
import com.nas.alreem.activity.parent_meetings.ReviewAppointmentsRecyclerViewActivity
import com.nas.alreem.activity.payments.adapter.StudentListAdapter
import com.nas.alreem.activity.payments.model.StudentList
import com.nas.alreem.activity.payments.model.StudentListModel
import com.nas.alreem.constants.ConstantFunctions
import com.nas.alreem.constants.ConstantWords
import com.nas.alreem.constants.DialogFunctions
import com.nas.alreem.constants.OnItemClickListener
import com.nas.alreem.constants.PreferenceManager
import com.nas.alreem.constants.addOnItemClickListener
import com.nas.alreem.fragment.parent_meetings.adapter.StaffListAdapter
import com.nas.alreem.fragment.parent_meetings.model.ListStaffPtaApiModel
import com.nas.alreem.fragment.parent_meetings.model.ListStaffPtaModel
import com.nas.alreem.fragment.parent_meetings.model.StaffInfoDetail
import com.nas.alreem.rest.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ParentMeetingsFragment:Fragment() {
    lateinit var mContext: Context
    lateinit var titleTextView: TextView
    lateinit var progressDialogAdd: ProgressBar
    lateinit var infoImg:ImageView
    lateinit var studentName: String
    lateinit var studentrelatv: RelativeLayout
    lateinit var student_image: ImageView
    //lateinit var studImg: ImageView
    lateinit var studentImg: String
    var student_class:String=""
    var staffName: String=""
    var staffrole: String=""
    var staffId: String=""
    var staffImg: String=""
    var staffEmail: String=""
    lateinit var staffRelative: RelativeLayout
    lateinit var staff_image: ImageView
    lateinit var staffNameTV: TextView
    lateinit var studentname:TextView
    var studentListArrayList = ArrayList<StudentList>()
    lateinit var staffListArray: ArrayList<StaffInfoDetail>
    lateinit var nxtbtn: ImageView
    lateinit var review_img: ImageView
    var studentId: String=""
    var apiCall:Int=0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.frament_parent_meetings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mContext=requireContext()
        Log.e("frag","PM")
        initializeUI()
    }

    private fun initializeUI(){
        progressDialogAdd=requireView().findViewById(R.id.progressDialogAdd)
        titleTextView=requireView().findViewById(R.id.titleTextView)
        titleTextView.text= ConstantWords.parentmeetings
        infoImg = requireView().findViewById(R.id.infoImg)
        studentrelatv=requireView().findViewById(R.id.studentRelative)
        student_image=requireView().findViewById(R.id.selectStudentImgView)
        staff_image=requireView().findViewById(R.id.selectStaffImgView)
        studentname=requireView().findViewById(R.id.studentNameTV)
        staffRelative=requireView().findViewById(R.id.staffRelative)
        staffListArray= ArrayList()
        staffNameTV=requireView().findViewById(R.id.staffNameTV)
        nxtbtn=requireView().findViewById(R.id.next)
        review_img=requireView().findViewById(R.id.reviewImageView)
        onclick()
        callStudentListApi()

        Log.e("time", PreferenceManager.getIsFirstTimeInPE(mContext).toString())
        if (PreferenceManager.getIsFirstTimeInPE(mContext)) {
            PreferenceManager.setIsFirstTimeInPE(mContext, false)
            val mintent = Intent(mContext, ParentsEveninginfoActivity::class.java)
            mintent.putExtra("type", 1)
            mContext.startActivity(mintent)
            Log.e("timeafter",PreferenceManager.getIsFirstTimeInPE(mContext).toString())
        }
    }
    private fun onclick(){

        infoImg.setOnClickListener(View.OnClickListener {
            val mIntent = Intent(mContext, ParentsEveninginfoActivity::class.java)
            mContext.startActivity(mIntent)
        })
        student_image.setOnClickListener {
            showStudentList(mContext, studentListArrayList)

        }
        staff_image.setOnClickListener {
            if(staffListArray.size>0)
            {
                showStaffList(mContext, staffListArray)
            }
            else
            {
                ConstantFunctions.showDialogueWithOk(
                    mContext,
                    "No Data Found!",
                    "Alert"
                )
            }
        }
        nxtbtn.setOnClickListener {
            val mIntent = Intent(mContext, ParentsEveningCalendarActivity::class.java)
            mIntent.putExtra("studId",studentId)
            mIntent.putExtra("studName",studentName)
            mIntent.putExtra("studClass",student_class)
            mIntent.putExtra("staffId",staffId)
            mIntent.putExtra("staffName",staffName)
            mContext.startActivity(mIntent)
        }
        review_img.setOnClickListener {
            val mIntent = Intent(mContext, ReviewAppointmentsRecyclerViewActivity::class.java)
            mContext.startActivity(mIntent)
        }
    }
    private fun callStudentListApi(){
        progressDialogAdd.visibility = View.VISIBLE
        val token = PreferenceManager.getaccesstoken(mContext)
        val call: Call<StudentListModel> = ApiClient.getClient.studentList("Bearer "+token)
        call.enqueue(object : Callback<StudentListModel> {
            override fun onFailure(call: Call<StudentListModel>, t: Throwable) {
                Log.e("Error", t.localizedMessage)
                progressDialogAdd.visibility = View.GONE
            }
            override fun onResponse(call: Call<StudentListModel>, response: Response<StudentListModel>) {
                progressDialogAdd.visibility = View.GONE
                //val arraySize :Int = response.body()!!.responseArray.studentList.size
                if (response.body()!!.status==100)
                {
                    studentListArrayList.addAll(response.body()!!.responseArray.studentList)


                }
                else
                {

                    DialogFunctions.commonErrorAlertDialog(mContext.resources.getString(R.string.alert), ConstantFunctions.commonErrorString(response.body()!!.status), mContext)
                }

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
            val llm = LinearLayoutManager(mContext)
            llm.orientation = LinearLayoutManager.VERTICAL
            studentListRecycler.layoutManager = llm
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
                studentName = studentListArrayList[position].name
                studentId = studentListArrayList[position].id
                studentImg = studentListArrayList[position].photo
                student_class=studentListArrayList[position].studentClass
                studentname.text = studentName
                staffRelative.visibility = View.VISIBLE
                staff_image.setImageResource(R.drawable.addiconinparentsevng)
                staffNameTV.text = "Staff Name:-"
                nxtbtn.visibility = View.GONE
                stafflistcall(studentId)
                if (studentImg != "") {
                    Glide.with(mContext) //1
                        .load(studentImg)
                        .placeholder(R.drawable.student)
                        .error(R.drawable.student)
                        .skipMemoryCache(true) //2
                        .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                        .transform(CircleCrop()) //4
                        .into(student_image)
                } else {
                    student_image.setImageResource(R.drawable.student)
                }
                dialog.dismiss()
            }
        })
        dialog.show()
    }
    private fun stafflistcall(studentId:String){
        staffListArray=ArrayList()
        progressDialogAdd.visibility = View.VISIBLE
        val token = PreferenceManager.getaccesstoken(mContext)
        val stafflist5SuccessBody = ListStaffPtaApiModel(studentId.toString())
        val call: Call<ListStaffPtaModel> = ApiClient.getClient.staff_list_pta(stafflist5SuccessBody,"Bearer "+token)
        call.enqueue(object : Callback<ListStaffPtaModel> {
            override fun onFailure(call: Call<ListStaffPtaModel>, t: Throwable) {
                Log.e("Error", t.localizedMessage)
                progressDialogAdd.visibility = View.GONE
            }
            override fun onResponse(call: Call<ListStaffPtaModel>, response: Response<ListStaffPtaModel>) {
                progressDialogAdd.visibility = View.GONE
                //val arraySize :Int = response.body()!!.responseArray.studentList.size
                if (response.body()!!.status==100)
                {
                    staffListArray.addAll(response.body()!!.data)


                } else
                {

                    DialogFunctions.commonErrorAlertDialog(mContext.resources.getString(R.string.alert), ConstantFunctions.commonErrorString(response.body()!!.status), mContext)
                }


            }

        })
    }
    fun showStaffList(context: Context, mStaffList: ArrayList<StaffInfoDetail>) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialogue_student_list)
        var iconImageView = dialog.findViewById(R.id.iconImageView) as ImageView
        var btn_dismiss = dialog.findViewById(R.id.btn_dismiss) as Button
        var studentListRecycler = dialog.findViewById(R.id.studentListRecycler) as RecyclerView
        iconImageView.setImageResource(R.drawable.staff)
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
        val staffAdapter = StaffListAdapter(mStaffList, mContext)
        studentListRecycler.adapter = staffAdapter
        btn_dismiss.setOnClickListener()
        {
            dialog.dismiss()
        }
        studentListRecycler.addOnItemClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                // Your logic
                dialog.dismiss()
                staffName = mStaffList.get(position).name
                //staffrole = mStaffList.get(position).role
                staffImg = mStaffList.get(position).staff_photo
                staffId = mStaffList.get(position).id.toString()
               // staffEmail = mStaffList.get(position).email
                staffNameTV.text = staffName
                nxtbtn.visibility = View.VISIBLE
                if (!staffImg.equals("")) {
                    Glide.with(mContext) //1
                        .load(staffImg)
                        .placeholder(R.drawable.staff)
                        .error(R.drawable.staff)
                        .skipMemoryCache(true) //2
                        .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                        .transform(CircleCrop()) //4
                        .into(staff_image)
                    dialog.dismiss()
                } else {
                    staff_image.setImageResource(R.drawable.staff)
                    dialog.dismiss()
                }
                dialog.dismiss()
            }
        })
        dialog.show()
    }
}