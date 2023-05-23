package com.nas.alreem.fragment.primary

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nas.alreem.R
import com.nas.alreem.activity.login.model.SignUpResponseModel
import com.nas.alreem.activity.primary.PrimaryActivity
import com.nas.alreem.activity.primary.PrimaryComingUpActivity
import com.nas.alreem.constants.*
import com.nas.alreem.fragment.notifications.adapter.NotificationListAdapter
import com.nas.alreem.fragment.notifications.model.NotificationApiModel
import com.nas.alreem.fragment.notifications.model.NotificationResponseModel
import com.nas.alreem.fragment.primary.adapter.PrimaryAdapter
import com.nas.alreem.fragment.primary.model.PrimaryDataModel
import com.nas.alreem.fragment.primary.model.PrimaryResponseModel
import com.nas.alreem.rest.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PrimaryFragment : Fragment() {

    lateinit var mContext: Context
    lateinit var mPrimaryRecycler: RecyclerView
    lateinit var progressDialogAdd: ProgressBar
    lateinit var titleTextView: TextView
    lateinit var primaryArrayList:ArrayList<PrimaryDataModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_primary, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mContext=requireContext()
        initializeUI()
        callPrimaryApi()
    }
    private fun initializeUI()
    {
        mPrimaryRecycler=requireView().findViewById(R.id.mPrimaryRecycler)
        progressDialogAdd=requireView().findViewById(R.id.progressDialogAdd)
        titleTextView=requireView().findViewById(R.id.titleTextView)
        titleTextView.text=ConstantWords.primary
        var linearLayoutManager = LinearLayoutManager(mContext)
        mPrimaryRecycler.layoutManager = linearLayoutManager
        mPrimaryRecycler.itemAnimator = DefaultItemAnimator()

        mPrimaryRecycler.addOnItemClickListener(object : OnItemClickListener {
            @SuppressLint("SimpleDateFormat", "SetTextI18n")
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onItemClicked(position: Int, view: View)
            {
                if (position==0)
                {
                    val intent = Intent(mContext, PrimaryComingUpActivity::class.java)
                    startActivity(intent)
                }
                else
                {
                    val intent = Intent(mContext, PrimaryActivity::class.java)
                    intent.putExtra("list",primaryArrayList.get(position-1).file)
                    intent.putExtra("title",primaryArrayList.get(position-1).name)
                    startActivity(intent)
                }

            }


        })

    }
    private fun callPrimaryApi()
    {
        progressDialogAdd.visibility=View.VISIBLE
        primaryArrayList= ArrayList()
        val call: Call<PrimaryResponseModel> = ApiClient.getClient.primaryList()
        call.enqueue(object : Callback<PrimaryResponseModel> {
            override fun onFailure(call: Call<PrimaryResponseModel>, t: Throwable) {
                Log.e("Failed", t.localizedMessage)
                progressDialogAdd.visibility=View.GONE
            }
            override fun onResponse(call: Call<PrimaryResponseModel>, response: Response<PrimaryResponseModel>) {
                val responsedata = response.body()
                progressDialogAdd.visibility=View.GONE
                if (responsedata != null) {
                    try {

                        if (response.body()!!.status==100)
                        {

                            primaryArrayList=response.body()!!.responseArray!!.data!!
                            if (primaryArrayList.size>0)
                            {
                                var primaryAdapter= PrimaryAdapter(primaryArrayList,mContext)
                                mPrimaryRecycler.adapter=primaryAdapter
                            }
                            else
                            {
                                DialogFunctions.commonErrorAlertDialog(mContext.resources.getString(R.string.alert), ConstantWords.status_132, mContext)

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