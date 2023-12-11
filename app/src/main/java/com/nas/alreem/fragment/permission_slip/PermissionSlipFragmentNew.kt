package com.nas.alreem.fragment.permission_slip
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
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.nas.alreem.R
import com.nas.alreem.activity.absence.RequestearlypickupActivity
import com.nas.alreem.activity.payments.adapter.StudentListAdapter
import com.nas.alreem.activity.payments.model.StudentList
import com.nas.alreem.activity.payments.model.StudentListModel
import com.nas.alreem.constants.ApiClient
import com.nas.alreem.constants.ConstantFunctions
import com.nas.alreem.constants.DialogFunctions
import com.nas.alreem.constants.OnItemClickListener
import com.nas.alreem.constants.PreferenceManager
import com.nas.alreem.constants.addOnItemClickListener
import com.nas.alreem.fragment.absence.adapter.PickuplistAdapter
import com.nas.alreem.fragment.permission_slip.adapter.FormslistAdapter
import com.nas.alreem.fragment.permission_slip.model.PermissionSlipListApiModel
import com.nas.alreem.fragment.permission_slip.model.PermissionSlipListModel
import com.nas.alreem.fragment.permission_slip.model.PermissionSlipModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PermissionSlipFragmentNew :Fragment(){
    lateinit var progressDialog: RelativeLayout
    lateinit var titleTextView: TextView
    lateinit var mContext: Context
    lateinit var sharedprefs: PreferenceManager
    lateinit var studentSpinner: LinearLayout
    var studentListArrayList = ArrayList<StudentList>()
    lateinit var formslist:ArrayList<PermissionSlipListModel>
    lateinit var studImg: ImageView
    lateinit var studentName: String
    lateinit var studentId: String
    lateinit var studentImg: String
    lateinit var studentClass: String
    lateinit var studentNameTxt: TextView
    lateinit var forms_recycler: RecyclerView
    lateinit var absence_btn:TextView
    lateinit var heading:TextView
    var select_val:Int=0
    lateinit var pickup_btn:TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_permission_slip_new, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedprefs = PreferenceManager()
        mContext = requireContext()

        initializeUI()
        var internetCheck = ConstantFunctions.internetCheck(mContext)
        if (internetCheck) {
            callStudentListApi()

        } else {
            DialogFunctions.showInternetAlertDialog(mContext)
        }
        selectCategory()
    }
    private fun initializeUI(){
        mContext=requireContext()
        titleTextView = view!!.findViewById(R.id.titleTextView) as TextView
        titleTextView.text = "Permission Forms"
        //formslist= ArrayList()
        studentSpinner = view!!.findViewById<LinearLayout>(R.id.studentSpinner)
        studImg = view!!.findViewById<ImageView>(R.id.studImg)
        studentNameTxt = view!!.findViewById<TextView>(R.id.studentName)
        forms_recycler=view!!.findViewById(R.id.forms_rec)
        progressDialog = view!!.findViewById(R.id.progressDialog)
        absence_btn=requireView().findViewById(R.id.absenc_btn)
        heading=requireView().findViewById(R.id.appregisteredHint)
        pickup_btn=requireView().findViewById(R.id.earlypickup_btn)

        val aniRotate: Animation =
            AnimationUtils.loadAnimation(mContext, R.anim.linear_interpolator)
        progressDialog.startAnimation(aniRotate)

        forms_recycler.visibility= View.GONE

        studentSpinner.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                showStudentList(mContext,studentListArrayList)

            }
        })
    }
    private fun  selectCategory(){
        absence_btn.setOnClickListener {
            progressDialog.visibility=View.VISIBLE
            select_val=0
            formslistApi()
            absence_btn.setBackgroundResource(R.drawable.event_spinnerfill)
            absence_btn.setTextColor(Color.BLACK)
            pickup_btn.setBackgroundResource(R.drawable.event_greyfill)
            pickup_btn.setTextColor(Color.BLACK)
            heading.text = "App Permission Form"
            forms_recycler.visibility = View.VISIBLE
           // mPickupListView.visibility = View.GONE


        }
        pickup_btn.setOnClickListener {
            progressDialog.visibility=View.VISIBLE
            select_val=1
            if (ConstantFunctions.internetCheck(mContext))
            {
               callgeneralForm()
            }
            else
            {
                DialogFunctions.showInternetAlertDialog(mContext)
            }

            absence_btn.setBackgroundResource(R.drawable.event_greyfill)
            absence_btn.setTextColor(Color.BLACK)
            pickup_btn.setBackgroundResource(R.drawable.event_spinnerfill)
            pickup_btn.setTextColor(Color.BLACK)
            heading.text = "App General Form"
            forms_recycler.visibility = View.GONE
            /*mPickupListView.visibility = View.VISIBLE
            mPickupListView.layoutManager=LinearLayoutManager(mContext)
            var pickuplistAdapter= PickuplistAdapter(mContext,pickup_list)
            mPickupListView.adapter=pickuplistAdapter*/
        }

    }
    fun showStudentList(context: Context, mStudentList : ArrayList<StudentList>)
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
                progressDialog.visibility= View.VISIBLE
                val aniRotate: Animation =
                    AnimationUtils.loadAnimation(mContext, R.anim.linear_interpolator)
                progressDialog.startAnimation(aniRotate)

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
                progressDialog.visibility = View.VISIBLE
                var internetCheck = ConstantFunctions.internetCheck(mContext)
                if (internetCheck) {
                    formslistApi()

                } else {
                    DialogFunctions.showInternetAlertDialog(mContext)
                }


                //  Toast.makeText(activity, mStudentList.get(position).name, Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
        })
        dialog.show()
    }
    private fun formslistApi(){
        progressDialog.visibility = View.VISIBLE
        formslist=ArrayList()

        val token = PreferenceManager.getaccesstoken(mContext)
        val list_permissionSlip= PermissionSlipListApiModel("0","20",
            PreferenceManager.getStudentID(mContext).toString())
        val call: Call<PermissionSlipModel> = ApiClient.getClient.permissnslipList(list_permissionSlip,"Bearer "+token)
        call.enqueue(object : Callback<PermissionSlipModel> {
            override fun onFailure(call: Call<PermissionSlipModel>, t: Throwable) {
                progressDialog.visibility = View.GONE
            }
            override fun onResponse(call: Call<PermissionSlipModel>, response: Response<PermissionSlipModel>) {
                progressDialog.visibility = View.GONE
                if (response.body()!!.status==100)
                {
                    forms_recycler.visibility= View.VISIBLE
                    formslist=ArrayList()
                    formslist.addAll(response.body()!!.responseArray.request)
                    if (response.body()!!.responseArray.request.size > 0){
                        forms_recycler.layoutManager= LinearLayoutManager(mContext)
                        var forms_adapter= FormslistAdapter(mContext,formslist)
                        forms_recycler.adapter=forms_adapter
                    }else{
                        formslist=ArrayList()
                        forms_recycler.layoutManager= LinearLayoutManager(mContext)
                        var forms_adapter= FormslistAdapter(mContext,formslist)
                        forms_recycler.adapter=forms_adapter
                        //showerror(mContext,"No Data Found","Alert")
                        Toast.makeText(mContext, "No Permission Forms Found", Toast.LENGTH_SHORT).show()
                    }


                }/*else if(response.body()!!.status.equals("116"))
                {
                    var internetCheck = InternetCheckClass.isInternetAvailable(com.mobatia.bisad.fragment.home.mContext)
                    if (internetCheck){
                        AccessTokenClass.getAccessToken(com.mobatia.bisad.fragment.home.mContext)
                        Log.e("TEST","call 3")

                        formslistApi()
                    }else{
                        InternetCheckClass.showSuccessInternetAlert(com.mobatia.bisad.fragment.home.mContext)
                    }

                }*/
                else {
                    if (response.body()!!.status == 132) {
                        formslist=ArrayList()
                        forms_recycler.layoutManager= LinearLayoutManager(mContext)
                        var forms_adapter= FormslistAdapter(mContext,formslist)
                        forms_recycler.adapter=forms_adapter
                        Toast.makeText(mContext, "No Permission Slips Found", Toast.LENGTH_SHORT).show()
                        //validation check error
                    }
                }
            }
        })
    }
    fun callgeneralForm()
    {
        progressDialog.visibility = View.VISIBLE
        formslist=ArrayList()

        val token = PreferenceManager.getaccesstoken(mContext)
        val list_permissionSlip= PermissionSlipListApiModel("0","20",
            PreferenceManager.getStudentID(mContext).toString())
        val call: Call<PermissionSlipModel> = ApiClient.getClient.generalforms(list_permissionSlip,"Bearer "+token)
        call.enqueue(object : Callback<PermissionSlipModel> {
            override fun onFailure(call: Call<PermissionSlipModel>, t: Throwable) {
                progressDialog.visibility = View.GONE
            }
            override fun onResponse(call: Call<PermissionSlipModel>, response: Response<PermissionSlipModel>) {
                progressDialog.visibility = View.GONE
                if (response.body()!!.status==100)
                {
                    Log.e("success","success")
                   /* forms_recycler.visibility= View.VISIBLE
                    formslist=ArrayList()
                    formslist.addAll(response.body()!!.responseArray.request)
                    if (response.body()!!.responseArray.request.size > 0){
                        forms_recycler.layoutManager= LinearLayoutManager(mContext)
                        var forms_adapter= FormslistAdapter(mContext,formslist)
                        forms_recycler.adapter=forms_adapter
                    }else{
                        formslist=ArrayList()
                        forms_recycler.layoutManager= LinearLayoutManager(mContext)
                        var forms_adapter= FormslistAdapter(mContext,formslist)
                        forms_recycler.adapter=forms_adapter
                        //showerror(mContext,"No Data Found","Alert")
                        Toast.makeText(mContext, "No Permission Forms Found", Toast.LENGTH_SHORT).show()
                    }*/


                }/*else if(response.body()!!.status.equals("116"))
                {
                    var internetCheck = InternetCheckClass.isInternetAvailable(com.mobatia.bisad.fragment.home.mContext)
                    if (internetCheck){
                        AccessTokenClass.getAccessToken(com.mobatia.bisad.fragment.home.mContext)
                        Log.e("TEST","call 3")

                        formslistApi()
                    }else{
                        InternetCheckClass.showSuccessInternetAlert(com.mobatia.bisad.fragment.home.mContext)
                    }

                }*/
                else {
                    if (response.body()!!.status == 132) {
                        formslist=ArrayList()
                        forms_recycler.layoutManager= LinearLayoutManager(mContext)
                        var forms_adapter= FormslistAdapter(mContext,formslist)
                        forms_recycler.adapter=forms_adapter
                        Toast.makeText(mContext, "No Permission Slips Found", Toast.LENGTH_SHORT).show()
                        //validation check error
                    }
                }
            }
        })
    }
    fun callStudentListApi()
    {
        progressDialog.visibility = View.VISIBLE
        val token = PreferenceManager.getaccesstoken(mContext)
        val call: Call<StudentListModel> = ApiClient.getClient.studentList("Bearer "+token)
        call.enqueue(object : Callback<StudentListModel> {
            override fun onFailure(call: Call<StudentListModel>, t: Throwable) {
                progressDialog.visibility = View.GONE
            }
            override fun onResponse(call: Call<StudentListModel>, response: Response<StudentListModel>) {
                progressDialog.visibility = View.GONE
                //val arraySize :Int = response.body()!!.responseArray.studentList.size
                if (response.body()!!.status==100)
                {
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
                    var internetCheck = ConstantFunctions.internetCheck(mContext)
                    if (internetCheck) {
                        formslistApi()

                    } else {
                        DialogFunctions.showInternetAlertDialog(mContext)
                    }

//                    var internetCheck = InternetCheckClass.isInternetAvailable(nContext)
//                    if (internetCheck)
//                    {
//                        //callStudentLeaveInfo()
//                    }
//                    else{
//                        InternetCheckClass.showSuccessInternetAlert(com.mobatia.bisad.fragment.home.mContext)
//                    }

                    //callStudentInfoApi()
                }


            }

        })
    }
    override fun onResume() {
        super.onResume()
        forms_recycler.visibility= View.GONE
        var internetCheck = ConstantFunctions.internetCheck(mContext)
        if (internetCheck) {
            formslistApi()

        } else {
            //DialogFunctions.showInternetAlertDialog(mContext)
        }

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

    }
}