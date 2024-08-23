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
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.nas.alreem.R
import com.nas.alreem.activity.bus_service.requestservice.RequestServiceListActivity
import com.nas.alreem.activity.home.HomeActivity
import com.nas.alreem.activity.payments.adapter.StudentListAdapter
import com.nas.alreem.activity.payments.model.StudentList
import com.nas.alreem.activity.payments.model.StudentListModel
import com.nas.alreem.constants.ApiClient
import com.nas.alreem.constants.ConstantFunctions
import com.nas.alreem.constants.OnItemClickListener
import com.nas.alreem.constants.PreferenceManager
import com.nas.alreem.constants.addOnItemClickListener
import com.nas.alreem.fragment.bus_service.model.BannerModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BusServiceRegisterupdate : AppCompatActivity() {
    lateinit var mContext: Context
    var ccaOption: RelativeLayout? = null
    var externalCCA: RelativeLayout? = null
    var informationCCA: RelativeLayout? = null
    var studentListArrayList = ArrayList<StudentList>()
    lateinit var studImg: ImageView
    lateinit var studentName: String
    lateinit var studentId: String
    lateinit var studentImg: String
    lateinit var studentClass: String
    lateinit var studentNameTxt: TextView
    lateinit var progressDialogAdd: ProgressBar
    lateinit var studentSpinner: LinearLayout
    lateinit var bannerImagePager:ImageView
    private var description = ""
    var contactEmail = ""
    lateinit var heading: TextView
    lateinit var btn_left:ImageView
    lateinit var logoClickImgView:ImageView
    var descriptionTV: TextView? = null
    var mtitleRel: LinearLayout? = null
    var mailImageView: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bus_reg_new)
        mContext=this
        initfn()

        if (ConstantFunctions.internetCheck(mContext)) {
            callStudentListApi()
            getList()

        } else {

        }

    }

    private fun initfn() {
        heading=findViewById(R.id.heading)
        heading.text= "Registration Bus Service"
        studentSpinner = findViewById(R.id.studentSpinner)
        progressDialogAdd=findViewById(R.id.progressDialogAdd)
        studImg = findViewById<ImageView>(R.id.imagicon)
        studentNameTxt = findViewById<TextView>(R.id.studentName)
        externalCCA = findViewById<View>(R.id.myOrderRelative) as RelativeLayout
        ccaOption = findViewById<View>(R.id.addOrderRelative) as RelativeLayout
        informationCCA = findViewById<View>(R.id.informationRelative) as RelativeLayout
        btn_left=findViewById(R.id.btn_left)
        logoClickImgView=findViewById(R.id.logoClickImgView)
       mailImageView = findViewById<View>(R.id.mailImageView) as ImageView
        mtitleRel =findViewById<View>(R.id.title) as LinearLayout
       descriptionTV =findViewById<View>(R.id.descriptionTitle) as TextView


        bannerImagePager = findViewById(R.id.bannerImagePager)
        btn_left.setOnClickListener(View.OnClickListener {
            finish()
        })
        logoClickImgView.setOnClickListener(View.OnClickListener {
            val intent = Intent(mContext, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        })
        ccaOption!!.setOnClickListener {
            val intent = Intent(mContext, BusServiceRegisterNew::class.java)
            intent.putExtra("tab_type", "Bus Service")
            startActivity(intent)
        }
        studentSpinner.setOnClickListener(){
            showStudentList(mContext,studentListArrayList)
        }
        informationCCA!!.setOnClickListener {
            val intent = Intent(mContext, BusServiceSummery::class.java)
            intent.putExtra("tab_type", "Information")
            startActivity(intent)
        }
        externalCCA!!.setOnClickListener {

            PreferenceManager.setStudIdForCCA(mContext!!, "")
            // PreferenceManager.setStudentID(mContext!!, "")

            val intent = Intent(mContext, BusServiceEapRegister::class.java)
            intent.putExtra("tab_type", "ECA Options")
            startActivity(intent)

        }
    }
    private fun getList() {
       // progress.visibility = View.VISIBLE
        val token = PreferenceManager.getaccesstoken(mContext!!)
        val call: Call<BannerModel> =
            ApiClient.getClient.bus_service_form_banner( "Bearer $token")
        call.enqueue(object : Callback<BannerModel> {
            override fun onResponse(
                call: Call<BannerModel>,
                response: Response<BannerModel>
            ) {
               // progress.visibility = View.GONE

                if (response.isSuccessful){
                    if (response.body() != null){
                        if (response.body()!!.status.toString() == "100"){
                            val bannerImage: String = response.body()!!.responseArray!!.banner_image
                            description = response.body()!!.responseArray!!.description!!
                            contactEmail = response.body()!!.responseArray!!.contact_email!!
//

                            if (!bannerImage.equals("", ignoreCase = true)) {
                                Glide.with(mContext!!).load(ConstantFunctions.replace(bannerImage)).fitCenter()

                                    .centerCrop().into(bannerImagePager!!)

//											bannerUrlImageArray = new ArrayList<>();
//											bannerUrlImageArray.add(bannerImage);
//											bannerImagePager.setAdapter(new ImagePagerDrawableAdapter(bannerUrlImageArray, getActivity()));
                            } else {
                                bannerImagePager!!.setBackgroundResource(R.drawable.default_banner)
//											bannerImagePager.setBackgroundResource(R.drawable.ccas_banner);
                            }
                            println("contact mail$contactEmail")
                            if (description.equals("", ignoreCase = true) && contactEmail.equals(
                                    "",
                                    ignoreCase = true
                                )
                            )
                            {
                                mtitleRel!!.visibility = View.GONE
                            }
                            else {
                                mtitleRel!!.visibility = View.VISIBLE
                            }
                            if (description.equals("", ignoreCase = true)) {
                                descriptionTV!!.visibility = View.GONE
                            } else {
                                descriptionTV!!.text = description
                                descriptionTV!!.visibility = View.VISIBLE
                                mtitleRel!!.visibility = View.VISIBLE
                            }
                            if (contactEmail.equals("", ignoreCase = true)) {
                                println("contact mail1")
                                mailImageView!!.visibility = View.GONE
                            } else {
                                println("contact mail2")
                                mtitleRel!!.visibility = View.VISIBLE
                                mailImageView!!.visibility = View.VISIBLE
                            }
                            // CCAFRegisterRel.setVisibility(View.VISIBLE);
                            // CCAFRegisterRel.setVisibility(View.VISIBLE);

                        }else{
                            ConstantFunctions.showDialogueWithOk(mContext!!,getString(R.string.common_error),"Alert")
                        }
                    }else{
                        ConstantFunctions.showDialogueWithOk(mContext!!,getString(R.string.common_error),"Alert")
                    }
                }else{
                    ConstantFunctions.showDialogueWithOk(mContext!!,getString(R.string.common_error),"Alert")
                }
            }

            override fun onFailure(call: Call<BannerModel>, t: Throwable) {
                //progress.visibility = View.GONE

                ConstantFunctions.showDialogueWithOk(mContext!!,getString(R.string.common_error),"Alert")
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
        val call: Call<StudentListModel> = ApiClient.getClient.studentList("Bearer "+ PreferenceManager.getaccesstoken(mContext))
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
}