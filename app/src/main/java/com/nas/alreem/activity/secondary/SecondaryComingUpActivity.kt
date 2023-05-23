package com.nas.alreem.activity.secondary

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nas.alreem.R
import com.nas.alreem.activity.home.HomeActivity
import com.nas.alreem.activity.primary.adapter.ComingUpAdapter
import com.nas.alreem.activity.primary.adapter.PrimaryDataAdapter
import com.nas.alreem.activity.primary.model.ComingUpDataModell
import com.nas.alreem.activity.primary.model.ComingUpResponseModel
import com.nas.alreem.constants.ConstantFunctions
import com.nas.alreem.constants.ConstantWords
import com.nas.alreem.constants.DialogFunctions
import com.nas.alreem.fragment.primary.adapter.PrimaryAdapter
import com.nas.alreem.fragment.primary.model.PrimaryFileModel
import com.nas.alreem.fragment.primary.model.PrimaryResponseModel
import com.nas.alreem.rest.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SecondaryComingUpActivity : AppCompatActivity(){
    lateinit var mContext: Context
    lateinit var mListView: RecyclerView
    lateinit var progressDialogAdd: ProgressBar
    lateinit var heading: TextView
    lateinit var backRelative: RelativeLayout
    lateinit var logoClickImgView: ImageView
    lateinit var comingUpArrayList:ArrayList<ComingUpDataModell>
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coming_up)
        mContext=this
        initUI()
        callComingUpApi()

    }
    fun initUI()
    {

        mListView=findViewById(R.id.mListView)
        progressDialogAdd=findViewById(R.id.progressDialogAdd)
        var linearLayoutManager = LinearLayoutManager(mContext)
        mListView.layoutManager = linearLayoutManager
        mListView.itemAnimator = DefaultItemAnimator()
        heading=findViewById(R.id.heading)
        backRelative=findViewById(R.id.backRelative)
        logoClickImgView=findViewById(R.id.logoClickImgView)
        heading.text="Coming Up"
        backRelative.setOnClickListener(View.OnClickListener {
            finish()
        })

        logoClickImgView.setOnClickListener(View.OnClickListener {
            val intent = Intent(mContext, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        })
    }
    fun callComingUpApi()
    {
        progressDialogAdd.visibility=View.VISIBLE
        comingUpArrayList= ArrayList()
        val call: Call<ComingUpResponseModel> = ApiClient.getClient.secondaryComingUp()
        call.enqueue(object : Callback<ComingUpResponseModel> {
            override fun onFailure(call: Call<ComingUpResponseModel>, t: Throwable) {
                Log.e("Failed", t.localizedMessage)
                progressDialogAdd.visibility=View.GONE
            }
            override fun onResponse(call: Call<ComingUpResponseModel>, response: Response<ComingUpResponseModel>) {
                val responsedata = response.body()
                progressDialogAdd.visibility=View.GONE
                if (responsedata != null) {
                    try {

                        if (response.body()!!.status==100)
                        {
                            comingUpArrayList=response.body()!!.responseArray!!.data!!
                            if (comingUpArrayList.size>0)
                            {
                                var adapterComing= ComingUpAdapter(comingUpArrayList,mContext)
                                mListView.adapter=adapterComing
                            }
                            else
                            {
                                DialogFunctions.commonErrorAlertDialog(mContext.resources.getString(R.string.alert),ConstantWords.status_132, mContext)

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
}