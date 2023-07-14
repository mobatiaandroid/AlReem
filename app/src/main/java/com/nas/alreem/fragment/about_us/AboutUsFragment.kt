package com.nas.alreem.fragment.about_us

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.nas.alreem.R
import com.nas.alreem.activity.absence.AbsenceDetailActivity
import com.nas.alreem.activity.primary.PrimaryActivity
import com.nas.alreem.activity.primary.PrimaryComingUpActivity
import com.nas.alreem.activity.staff_directory.StaffDirectoryActivity
import com.nas.alreem.constants.*
import com.nas.alreem.fragment.about_us.adapter.AboutUsAdapter
import com.nas.alreem.fragment.about_us.model.AboutUsDataModel
import com.nas.alreem.fragment.about_us.model.AboutUsResponseModel
import com.nas.alreem.rest.ApiClient

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AboutUsFragment  : Fragment() {

    lateinit var mContext: Context
    lateinit var aboutUsRecycler: RecyclerView
    lateinit var progressDialogAdd: ProgressBar
    lateinit var titleTextView: TextView
    lateinit var aboutUsArrayList:ArrayList<AboutUsDataModel>
    lateinit var staff_rel:RelativeLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_about_us, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mContext=requireContext()
        initializeUI()
        callAboutUsApi()
    }
    private fun initializeUI()
    {
        aboutUsRecycler=requireView().findViewById(R.id.aboutUsRecycler)
        progressDialogAdd=requireView().findViewById(R.id.progressDialogAdd)
        titleTextView=requireView().findViewById(R.id.titleTextView)
        var linearLayoutManager = LinearLayoutManager(mContext)
        aboutUsRecycler.layoutManager = linearLayoutManager
        aboutUsRecycler.itemAnimator = DefaultItemAnimator()
        titleTextView.text=ConstantWords.about_us
        staff_rel=requireView().findViewById(R.id.relSub)
        staff_rel.setOnClickListener {
            val intent =Intent(activity, StaffDirectoryActivity::class.java)
            activity?.startActivity(intent)
        }
        aboutUsRecycler.addOnItemClickListener(object : OnItemClickListener {
            @SuppressLint("SimpleDateFormat", "SetTextI18n")
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onItemClicked(position: Int, view: View)
            {

//
//                    val intent = Intent(mContext, WebLinkActivity::class.java)
//                    intent.putExtra("url",aboutUsArrayList.get(position).url)
//                    intent.putExtra("heading",aboutUsArrayList.get(position).tab_type)
//                    startActivity(intent)



                if (aboutUsArrayList.get(position).url.contains(".pdf"))
                {
                    val intent = Intent(mContext, PDFViewerActivity::class.java)
                    intent.putExtra("Url",aboutUsArrayList.get(position).url)
                    intent.putExtra("title",aboutUsArrayList.get(position).name)
                    startActivity(intent)
                }
                else{
                    val intent = Intent(mContext, WebLinkActivity::class.java)
                    intent.putExtra("url",aboutUsArrayList.get(position).url)
                    intent.putExtra("heading",aboutUsArrayList.get(position).name)
                    startActivity(intent)
                }
            }


        })

    }
    private fun callAboutUsApi()
    {
        aboutUsArrayList= ArrayList()
        progressDialogAdd.visibility=View.VISIBLE
        val call: Call<AboutUsResponseModel> = ApiClient.getClient.aboutUs()
        call.enqueue(object : Callback<AboutUsResponseModel> {
            override fun onFailure(call: Call<AboutUsResponseModel>, t: Throwable) {
                Log.e("Failed", t.localizedMessage)
                progressDialogAdd.visibility=View.GONE
            }
            override fun onResponse(call: Call<AboutUsResponseModel>, response: Response<AboutUsResponseModel>) {
                val responsedata = response.body()
                progressDialogAdd.visibility=View.GONE
                if (responsedata != null) {
                    try {

                        if (response.body()!!.status==100)
                        {
                            aboutUsArrayList=response.body()!!.responseArray!!.data
                            if (aboutUsArrayList.size>0)
                            {
                                staff_rel.visibility=View.VISIBLE
                                var adapterAboutUs=AboutUsAdapter(aboutUsArrayList,mContext)
                                aboutUsRecycler.adapter=adapterAboutUs
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