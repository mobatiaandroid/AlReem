package com.nas.alreem.fragment.absence

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
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.nas.alreem.R
import com.nas.alreem.activity.absence.AbsenceDetailActivity
import com.nas.alreem.activity.absence.EarlyPickupDetailActivity
import com.nas.alreem.activity.absence.RequestabsenceActivity
import com.nas.alreem.activity.absence.RequestearlypickupActivity
import com.nas.alreem.activity.absence.model.AbsenceListModel
import com.nas.alreem.activity.absence.model.AbsenceRequestListModel
import com.nas.alreem.activity.absence.model.EarlyPickupListArray
import com.nas.alreem.activity.absence.model.EarlyPickupModel
import com.nas.alreem.activity.absence.model.ListAbsenceApiModel
import com.nas.alreem.activity.absence.model.ListPickupApiModel
import com.nas.alreem.activity.absence.model.PickupListModel
import com.nas.alreem.activity.absence.model.RequestPickupApiModel
import com.nas.alreem.activity.payments.adapter.StudentListAdapter
import com.nas.alreem.activity.payments.model.StudentList
import com.nas.alreem.activity.payments.model.StudentListModel
import com.nas.alreem.constants.*
import com.nas.alreem.fragment.absence.adapter.PickuplistAdapter
import com.nas.alreem.fragment.absence.model.AbsenceRequestListDetailModel
import com.nas.alreem.fragment.absence.model.EarlyPickupListModel
import com.nas.alreem.fragment.parent_meetings.adapter.RequestAbsenceRecyclerAdapter
import com.nas.alreem.rest.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AbsenceFragment  : Fragment() {

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
    lateinit var studentAbsenceCopy :ArrayList<AbsenceRequestListModel>
    var studentAbsenceArrayList = ArrayList<AbsenceRequestListModel>()

    lateinit var absence_btn:TextView
    lateinit var pickup_btn:TextView
    lateinit var heading:TextView
    lateinit var titleTextView:TextView

    var select_val:Int=0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_absence_pickup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mContext=requireContext()
        initializeUI()
        callStudentListApi()
        selectCategory()
        //callStudentListApi()
    }
    private fun initializeUI()
    {
        progressDialogAdd = requireView().findViewById(R.id.progressDialogAdd)
        studentSpinner = requireView().findViewById<LinearLayout>(R.id.studentSpinner)
        studImg = requireView().findViewById<ImageView>(R.id.imagicon)
        studentNameTxt =requireView().findViewById<TextView>(R.id.studentName)
        titleTextView=requireView().findViewById(R.id.titleTextView)
        titleTextView.text= ConstantWords.absence_earlypickup
        newRequestAbsence = requireView().findViewById(R.id.newRequestAbsence)
        newRequestPickup = requireView().findViewById(R.id.newRequestEarly)
        mAbsenceListView = requireView().findViewById(R.id.mAbsenceListView) as RecyclerView
        mPickupListView=requireView().findViewById(R.id.mPickupListView)
        pickup_list= ArrayList()
        pickupListSort=ArrayList()
        heading=requireView().findViewById(R.id.appregisteredHint)
        absence_btn=requireView().findViewById(R.id.absenc_btn)
        absence_btn.setBackgroundResource(R.color.colorAccent)
        pickup_btn=requireView().findViewById(R.id.earlypickup_btn)
        linearLayoutManager = LinearLayoutManager(mContext)
        mAbsenceListView.layoutManager = linearLayoutManager
        mAbsenceListView.itemAnimator = DefaultItemAnimator()
        absence_btn.setBackgroundResource(R.drawable.event_spinnerfill)
        studentSpinner.setOnClickListener(){
            showStudentList(mContext,studentListArrayList)
        }
        mAbsenceListView.addOnItemClickListener(object: OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                // Your logic
                val intent =Intent(activity, AbsenceDetailActivity::class.java)
                intent.putExtra("studentName",PreferenceManager.getStudentName(mContext))
                intent.putExtra("studentClass",PreferenceManager.getStudentClass(mContext))
                intent.putExtra("fromDate",studentAbsenceArrayList.get(position).from_date)
                intent.putExtra("toDate",studentAbsenceArrayList.get(position).to_date)
                intent.putExtra("reason",studentAbsenceArrayList.get(position).reason)
                activity?.startActivity(intent)
            }
        })
        mPickupListView.addOnItemClickListener(object: OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                // Your logic
                val intent =Intent(activity, EarlyPickupDetailActivity::class.java)
                intent.putExtra("studentName",PreferenceManager.getStudentName(mContext))
                intent.putExtra("studentClass",PreferenceManager.getStudentClass(mContext))
                intent.putExtra("date",pickupListSort.get(position).pickup_date)
                intent.putExtra("time",pickupListSort.get(position).pickup_time)
                intent.putExtra("pickupby",pickupListSort.get(position).pickup_by_whom)
                intent.putExtra("reason",pickupListSort.get(position).reason)
                intent.putExtra("status",pickupListSort.get(position).status)
                intent.putExtra("reason_for_rejection",pickupListSort.get(position).reason_for_rejection)
                activity?.startActivity(intent)
            }
        })
        newRequestAbsence.setOnClickListener(View.OnClickListener {
            showSuccessmailAlert(mContext,
                "For planned absences please email your head of school","Alert")


        })
    }
    fun callStudentListApi()
    {
        progressDialogAdd.visibility=View.VISIBLE
        studentListArrayList= ArrayList()
        val call: Call<StudentListModel> = ApiClient.getClient.studentList("Bearer "+PreferenceManager.getaccesstoken(mContext))
        call.enqueue(object : Callback<StudentListModel> {
            override fun onFailure(call: Call<StudentListModel>, t: Throwable) {
                Log.e("Failed", t.localizedMessage)
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
                                Log.e("Empty Img","Empty")
                                studentName=studentListArrayList.get(0).name
                                studentImg=studentListArrayList.get(0).photo
                                studentId=studentListArrayList.get(0).id
                                studentClass=studentListArrayList.get(0).section
                                PreferenceManager.setStudentID(mContext,studentId)
                                PreferenceManager.setStudentName(mContext,studentName)
                                PreferenceManager.setStudentPhoto(mContext,studentImg)
                                PreferenceManager.setStudentClass(mContext,studentClass)
                                studentNameTxt.text=studentName
                                Log.e("studid(0)", PreferenceManager.getStudentID(mContext).toString())
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
                progressDialogAdd.visibility=View.VISIBLE

                studentName=studentListArrayList.get(position).name
                studentImg=studentListArrayList.get(position).photo
                studentId=studentListArrayList.get(position).id
                studentClass=studentListArrayList.get(position).section
                PreferenceManager.setStudentID(mContext,studentId)
                PreferenceManager.setStudentName(mContext,studentName)
                PreferenceManager.setStudentPhoto(mContext,studentImg)
                PreferenceManager.setStudentClass(mContext,studentClass)
                Log.e("studidclick",PreferenceManager.getStudentID(mContext).toString())
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
                if (select_val==0){
                    callStudentLeaveInfo()
                }
                else if (select_val==1) {
                    callpickuplist_api()
                }
                dialog.dismiss()
            }
        })
        dialog.show()
    }
    private fun  selectCategory(){
        absence_btn.setOnClickListener {
            select_val=0
            callStudentLeaveInfo()
            absence_btn.setBackgroundResource(R.drawable.event_spinnerfill)
            absence_btn.setTextColor(Color.BLACK)
            pickup_btn.setBackgroundResource(R.drawable.event_greyfill)
            pickup_btn.setTextColor(Color.BLACK)
            heading.text = "App Registered Absences"
            mAbsenceListView.visibility = View.VISIBLE
            newRequestAbsence.visibility = View.VISIBLE
            mPickupListView.visibility = View.GONE
            newRequestPickup.visibility = View.GONE

        }
        pickup_btn.setOnClickListener {
            select_val=1
            callpickuplist_api()
            absence_btn.setBackgroundResource(R.drawable.event_greyfill)
            absence_btn.setTextColor(Color.BLACK)
            pickup_btn.setBackgroundResource(R.drawable.event_spinnerfill)
            pickup_btn.setTextColor(Color.BLACK)
            heading.text = "App Registered Early Pickup"
            mAbsenceListView.visibility = View.GONE
            newRequestAbsence.visibility = View.GONE
            mPickupListView.visibility = View.VISIBLE
            newRequestPickup.visibility = View.VISIBLE
            mPickupListView.layoutManager=LinearLayoutManager(mContext)
            var pickuplistAdapter= PickuplistAdapter(mContext,pickup_list)
            mPickupListView.adapter=pickuplistAdapter
        }
        newRequestPickup.setOnClickListener {
            val intent = Intent(activity, RequestearlypickupActivity::class.java)
            activity?.startActivity(intent)
        }
    }
    private fun callStudentLeaveInfo(){
        studentAbsenceCopy=ArrayList<AbsenceRequestListModel>()
        studentAbsenceArrayList.clear()
        mAbsenceListView.visibility=View.GONE
        progressDialogAdd.visibility=View.VISIBLE
        val studentInfoAdapter = RequestAbsenceRecyclerAdapter(studentAbsenceArrayList)
        mAbsenceListView.adapter = studentInfoAdapter
        val token = PreferenceManager.getaccesstoken(mContext)
        val pickupSuccessBody = ListAbsenceApiModel(PreferenceManager.getStudentID(mContext).toString(),0,20)
        val call: Call<AbsenceListModel> =
            ApiClient.getClient.absencelist(pickupSuccessBody, "Bearer " + token)
        call.enqueue(object : Callback<AbsenceListModel> {
            override fun onFailure(call: Call<AbsenceListModel>, t: Throwable) {
                Log.e("Failed", t.localizedMessage)
                progressDialogAdd.visibility=View.GONE
                //mProgressRelLayout.visibility=View.INVISIBLE
            }

            override fun onResponse(call: Call<AbsenceListModel>, response: Response<AbsenceListModel>) {
                val responsedata = response.body()
                //progressDialog.visibility = View.GONE
                Log.e("Response Signup", responsedata.toString())
                progressDialogAdd.visibility=View.GONE
                if (responsedata != null) {
                    try {

                        if (response.body()!!.status==100) {
                            studentAbsenceCopy.addAll(response.body()!!.responseArray.request)
                            studentAbsenceArrayList=studentAbsenceCopy

                            if (studentAbsenceArrayList.size>0)
                            {
                                mAbsenceListView.visibility=View.VISIBLE
                                val studentInfoAdapter = RequestAbsenceRecyclerAdapter(studentAbsenceArrayList)
                                mAbsenceListView.adapter = studentInfoAdapter
                            }
                            else{
                                Toast.makeText(mContext, "No Registered Absence Found", Toast.LENGTH_SHORT).show()
                                mAbsenceListView.visibility=View.GONE
                            }


                        }else if(response.body()!!.status==103){
                            callStudentLeaveInfo()
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

       /* studentAbsenceArrayList= ArrayList()
        var nmodel=AbsenceRequestListDetailModel("1","2023-07-30","2023-07-31","fever")
        studentAbsenceArrayList.add(nmodel)
        mAbsenceListView.visibility=View.VISIBLE
        val studentAbsenceAdapter = RequestAbsenceRecyclerAdapter(studentAbsenceArrayList)
        mAbsenceListView.adapter = studentAbsenceAdapter*/


    }
    private fun callpickuplist_api(){
        pickupListSort= ArrayList()
        pickup_list= ArrayList()
        progressDialogAdd.visibility=View.VISIBLE
        val token = PreferenceManager.getaccesstoken(mContext)
        val pickupSuccessBody = ListPickupApiModel(PreferenceManager.getStudentID(mContext).toString(),"0","20")
        val call: Call<PickupListModel> =
            ApiClient.getClient.pickuplist(pickupSuccessBody, "Bearer " + token)
        call.enqueue(object : Callback<PickupListModel> {
            override fun onFailure(call: Call<PickupListModel>, t: Throwable) {
                Log.e("Failed", t.localizedMessage)
                progressDialogAdd.visibility=View.GONE
                //mProgressRelLayout.visibility=View.INVISIBLE
            }

            override fun onResponse(call: Call<PickupListModel>, response: Response<PickupListModel>) {
                val responsedata = response.body()
                //progressDialog.visibility = View.GONE
                Log.e("Response Signup", responsedata.toString())
                progressDialogAdd.visibility=View.GONE
                if (responsedata != null) {
                    try {

                        if (response.body()!!.status==100) {

                            pickup_list.addAll(response.body()!!.pickupListArray)
                            mPickupListView.visibility = View.VISIBLE
                            var list_size=pickup_list.size-1
                            pickupListSort=ArrayList()
                            for (i in pickup_list.indices){
                                pickupListSort.add(pickup_list[list_size-i])
                            }
                            if (pickupListSort.size>0){
                                mPickupListView.layoutManager=LinearLayoutManager(mContext)
                                var pickuplistAdapter= PickuplistAdapter(mContext,pickupListSort)
                                mPickupListView.adapter=pickuplistAdapter
                            }else{
                                mPickupListView.layoutManager=LinearLayoutManager(mContext)
                                var pickuplistAdapter= PickuplistAdapter(mContext,pickupListSort)
                                mPickupListView.adapter=pickuplistAdapter
                                Toast.makeText(mContext, "No Registered Early Pickup Found", Toast.LENGTH_SHORT).show()
                            }

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
        /*pickupListSort= ArrayList()
        pickup_list= ArrayList()
        var nmoddel=EarlyPickupListModel(1,"2023-07-17","13:30:00","father","visit hospital",
        1,"3C","")
        pickupListSort.add(nmoddel)
        pickup_list.add(nmoddel)

        mPickupListView.visibility=View.VISIBLE
        mPickupListView.layoutManager=LinearLayoutManager(mContext)
        var pickuplistAdapter= PickuplistAdapter(mContext,pickupListSort)
        mPickupListView.adapter=pickuplistAdapter*/

    }
    fun showSuccessmailAlert(context: Context, message: String, msgHead: String) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_ok_cancel)
        var iconImageView = dialog.findViewById(R.id.iconImageView) as ImageView
        var alertHead = dialog.findViewById(R.id.alertHead) as TextView
        var text_dialog = dialog.findViewById(R.id.text_dialog) as TextView
        var btn_Ok = dialog.findViewById(R.id.btn_Ok) as Button
        var btn_Cancel = dialog.findViewById(R.id.btn_Cancel) as Button
        text_dialog.text = message
        alertHead.text = msgHead
        iconImageView.setImageResource(R.drawable.exclamationicon)
        btn_Ok.setOnClickListener()
        {
            dialog.dismiss()
            val intent =Intent(activity, RequestabsenceActivity::class.java)
            activity?.startActivity(intent)
        }
        btn_Cancel.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }
    override fun onResume() {
        super.onResume()
        studentNameTxt.text = PreferenceManager.getStudentName(mContext)
        studentId= PreferenceManager.getStudentID(mContext).toString()
        studentImg= PreferenceManager.getStudentPhoto(mContext)!!
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
        if (select_val==0){
            callStudentLeaveInfo()
        }
        else if (select_val==1){
            callpickuplist_api()
        }
    }
}