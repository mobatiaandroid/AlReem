package com.nas.alreem.activity.staff_directory

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.ImageView
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
import com.nas.alreem.activity.canteen.model.TimeExceedModel
import com.nas.alreem.activity.home.HomeActivity
import com.nas.alreem.activity.parent_meetings.ReviewAppointmentsRecyclerViewActivity
import com.nas.alreem.activity.staff_directory.adapter.StaffCategoryAdapter
import com.nas.alreem.activity.staff_directory.model.StaffCatListResponseModel
import com.nas.alreem.activity.staff_directory.model.StaffDeptListModel
import com.nas.alreem.constants.ConstantFunctions
import com.nas.alreem.constants.ConstantWords
import com.nas.alreem.constants.DialogFunctions
import com.nas.alreem.constants.OnItemClickListener
import com.nas.alreem.constants.PreferenceManager
import com.nas.alreem.constants.addOnItemClickListener
import com.nas.alreem.rest.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Locale

class StaffDirectoryActivity:AppCompatActivity() {
    lateinit var mContext:Context
    lateinit var backRelative: RelativeLayout
    lateinit var heading: TextView
    lateinit var logoClickImgView: ImageView
    lateinit var progressDialogAdd: ProgressBar
    lateinit var staff_cat_rec:RecyclerView
    lateinit var searchtxt: EditText
    lateinit var searchbtn: ImageView
    lateinit var staffCatList: ArrayList<StaffDeptListModel>
    lateinit var filtered: ArrayList<StaffDeptListModel>
    var cat_id:Int=0
    var cat_name:String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_staff_directory)

        mContext=this
        initfn()
        callStaffCatListApi()

    }
    private fun initfn() {
        heading=findViewById(R.id.heading)
        heading.text= ConstantWords.earlypickup
        backRelative=findViewById(R.id.backRelative)
        logoClickImgView=findViewById(R.id.logoClickImgView)
        progressDialogAdd=findViewById(R.id.progressDialogAdd)
        staff_cat_rec=findViewById(R.id.mStaffDirectoryListView)
        searchbtn = findViewById(R.id.btnImgsearch)
        searchtxt =findViewById(R.id.searchEditText)
        staffCatList= ArrayList()
        filtered= ArrayList()

        searchbtn.setOnClickListener(View.OnClickListener {
            if (staffCatList.size > 0) {
                filtered = ArrayList<StaffDeptListModel>()
                for (i in staffCatList.indices) {
                    if (staffCatList.get(i).department_name
                            .lowercase(Locale.getDefault())
                            .contains(searchtxt.text.toString().lowercase(Locale.getDefault())))
                    {
                        filtered.add(staffCatList.get(i))
                    }
                }
                staff_cat_rec.layoutManager=LinearLayoutManager(mContext)
                var staff_cat_adapter= StaffCategoryAdapter(mContext,filtered)
                staff_cat_rec.adapter=staff_cat_adapter

            }
            // AppUtils.hideKeyBoard(mContext)
        })
        searchtxt.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                // TODO Auto-generated method stub
                if (staffCatList.size > 0) {
                    filtered = ArrayList<StaffDeptListModel>()
                    for (i in staffCatList.indices) {
                        if ((staffCatList.get(i).department_name
                                .lowercase(Locale.getDefault()).contains(s.toString().lowercase(
                                    Locale.getDefault()))
                                    ))
                        {
                            filtered.add(staffCatList.get(i))
                        }
                    }
                    staff_cat_rec.layoutManager=LinearLayoutManager(mContext)
                    var staff_cat_adapter= StaffCategoryAdapter(mContext,filtered)
                    staff_cat_rec.adapter=staff_cat_adapter

                }
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
                // TODO Auto-generated method stub
            }

            override fun afterTextChanged(s: Editable) {
                // TODO Auto-generated method stub
            }
        })
        staff_cat_rec.addOnItemClickListener(object: OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                // Your logic
                //progressDialog.visibility=View.VISIBLE
                val aniRotate: Animation =
                    AnimationUtils.loadAnimation(mContext, R.anim.linear_interpolator)
                cat_id=staffCatList[position].id
                cat_name=staffCatList[position].department_name
                val intent = Intent(mContext, StaffDetailActivity::class.java)
                intent.putExtra("cat_id",cat_id)
                intent.putExtra("cat_id",cat_id)
                startActivity(intent)
            }
        })
        backRelative.setOnClickListener(View.OnClickListener {
            finish()
        })
        heading.text= ConstantWords.staff_directory
        logoClickImgView.setOnClickListener(View.OnClickListener {
            val intent = Intent(mContext, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        })
    }
    private fun callStaffCatListApi(){
        val token = PreferenceManager.getaccesstoken(mContext)
        progressDialogAdd.visibility=View.VISIBLE
        staffCatList= ArrayList()
        val call: Call<StaffCatListResponseModel> = ApiClient.getClient.staff_depatrtments("Bearer "+token)
        call.enqueue(object : Callback<StaffCatListResponseModel> {
            override fun onFailure(call: Call<StaffCatListResponseModel>, t: Throwable) {
                Log.e("Failed", t.localizedMessage)
                progressDialogAdd.visibility=View.GONE
            }
            override fun onResponse(call: Call<StaffCatListResponseModel>, response: Response<StaffCatListResponseModel>) {
                val responsedata = response.body()
                if (responsedata!!.status==100) {
                    progressDialogAdd.visibility=View.GONE
                    staffCatList=responsedata.responseArray.departments
                    staff_cat_rec.layoutManager=LinearLayoutManager(mContext)
                    var staff_cat_adapter= StaffCategoryAdapter(mContext,staffCatList)
                    staff_cat_rec.adapter=staff_cat_adapter


                } else
                {

                    DialogFunctions.commonErrorAlertDialog(mContext.resources.getString(R.string.alert), ConstantFunctions.commonErrorString(response.body()!!.status), mContext)
                }
            }

        })
    }
}