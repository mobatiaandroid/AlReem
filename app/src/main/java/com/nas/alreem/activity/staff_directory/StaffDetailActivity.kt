package com.nas.alreem.activity.staff_directory

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nas.alreem.R

import com.nas.alreem.activity.home.HomeActivity
import com.nas.alreem.activity.staff_directory.adapter.StaffCategoryAdapter
import com.nas.alreem.activity.staff_directory.adapter.StaffDetailAdapter
import com.nas.alreem.activity.staff_directory.model.DepartmentStaffsModel
import com.nas.alreem.activity.staff_directory.model.ListStaffDetailApiModel
import com.nas.alreem.activity.staff_directory.model.ListStaffDetailModel
import com.nas.alreem.activity.staff_directory.model.StaffDeptListModel
import com.nas.alreem.constants.ConstantFunctions
import com.nas.alreem.constants.ConstantWords
import com.nas.alreem.constants.DialogFunctions
import com.nas.alreem.constants.PreferenceManager
import com.nas.alreem.fragment.parent_meetings.adapter.RequestAbsenceRecyclerAdapter
import com.nas.alreem.rest.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Locale

class StaffDetailActivity : AppCompatActivity() {
    lateinit var mContext: Context
    lateinit var backRelative: RelativeLayout
    lateinit var heading: TextView
    lateinit var logoClickImgView: ImageView
    lateinit var progressDialogAdd: ProgressBar
    lateinit var staff_detail_rec: RecyclerView
    lateinit var searchtxt: EditText
    lateinit var searchbtn: ImageView

    lateinit var staffDetailList:ArrayList<DepartmentStaffsModel>
    lateinit var filtered:ArrayList<DepartmentStaffsModel>
    var cat_id:Int=0
    var cat_name:String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_staff_directory_detail)

        mContext=this
        initfn()
        if (ConstantFunctions.internetCheck(mContext))
        {
            callStaffDetailListApi()
        }
        else
        {
            DialogFunctions.showInternetAlertDialog(mContext)
        }


    }
    private fun initfn() {
        staffDetailList= ArrayList()
        filtered= ArrayList()
        heading = findViewById(R.id.heading)
        heading.text = ConstantWords.earlypickup
        backRelative = findViewById(R.id.backRelative)
        logoClickImgView = findViewById(R.id.logoClickImgView)
        progressDialogAdd = findViewById(R.id.progressDialogAdd)
        staff_detail_rec=findViewById(R.id.mStaffListView)
        searchbtn = findViewById(R.id.btnImgsearch)
        searchtxt =findViewById(R.id.searchEditText)
        cat_id=intent.getIntExtra("cat_id",0)
        cat_name= intent.getStringExtra("cat_name").toString()

        searchbtn.setOnClickListener(View.OnClickListener {
            if (staffDetailList.size > 0) {
                filtered = ArrayList<DepartmentStaffsModel>()
                for (i in staffDetailList.indices) {
                    if (staffDetailList.get(i).name
                            .lowercase(Locale.getDefault())
                            .contains(searchtxt.text.toString().lowercase(Locale.getDefault())))
                    {
                        filtered.add(staffDetailList.get(i))
                    }
                }
                staff_detail_rec.visibility=View.VISIBLE
                staff_detail_rec.layoutManager= LinearLayoutManager(mContext)
                var staff_detail_adapter= StaffDetailAdapter(mContext,filtered)
                staff_detail_rec.adapter=staff_detail_adapter

            }
            // AppUtils.hideKeyBoard(mContext)
        })
        searchtxt.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                // TODO Auto-generated method stub
                if (staffDetailList.size > 0) {
                    filtered = ArrayList<DepartmentStaffsModel>()
                    for (i in staffDetailList.indices) {
                        if ((staffDetailList.get(i).name
                                .lowercase(Locale.getDefault()).contains(s.toString().lowercase(
                                    Locale.getDefault()))
                                    ))
                        {
                            filtered.add(staffDetailList.get(i))
                        }
                    }
                    staff_detail_rec.visibility=View.VISIBLE
                    staff_detail_rec.layoutManager= LinearLayoutManager(mContext)
                    var staff_detail_adapter= StaffDetailAdapter(mContext,filtered)
                    staff_detail_rec.adapter=staff_detail_adapter

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
    private fun callStaffDetailListApi(){
        staffDetailList=ArrayList()
        val token = PreferenceManager.getaccesstoken(mContext)
        progressDialogAdd.visibility=View.VISIBLE
        val staffDetailSuccessBody = ListStaffDetailApiModel(cat_id)
        val call: Call<ListStaffDetailModel> =
            ApiClient.getClient.staff_detail_list(staffDetailSuccessBody, "Bearer " + token)
        call.enqueue(object : Callback<ListStaffDetailModel> {
            override fun onFailure(call: Call<ListStaffDetailModel>, t: Throwable) {
                Log.e("Failed", t.localizedMessage)
                progressDialogAdd.visibility=View.GONE
                //mProgressRelLayout.visibility=View.INVISIBLE
            }

            override fun onResponse(call: Call<ListStaffDetailModel>, response: Response<ListStaffDetailModel>) {
                val responsedata = response.body()
                progressDialogAdd.visibility=View.GONE
                staff_detail_rec.visibility=View.VISIBLE
                //progressDialog.visibility = View.GONE
                Log.e("Response Signup", responsedata.toString())
                if (responsedata != null) {
                        if (response.body()!!.status==100) {
                            Log.e("st",responsedata.responseArray.department_staffs.size.toString())
                            staffDetailList=responsedata.responseArray.department_staffs
                            //staffDetailList.addAll(responsedata.responseArray.department_staffs)

                            if(staffDetailList.size>0)
                            {
                                Log.e("listsize",staffDetailList.size.toString())
                                staff_detail_rec.layoutManager= LinearLayoutManager(mContext)
                                var staff_detail_adapter= StaffDetailAdapter(mContext,staffDetailList)
                                staff_detail_rec.adapter=staff_detail_adapter
                            }

                            else
                            {
                                DialogFunctions.commonErrorAlertDialog(mContext.resources.getString(R.string.alert), "No Data Found", mContext)
                            }
                        }
                        else
                        {

                            DialogFunctions.commonErrorAlertDialog(mContext.resources.getString(R.string.alert), ConstantFunctions.commonErrorString(response.body()!!.status), mContext)
                        }


                }
            }

        })
    }
}