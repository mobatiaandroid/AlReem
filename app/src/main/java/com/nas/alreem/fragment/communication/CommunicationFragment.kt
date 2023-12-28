package com.nas.alreem.fragment.communication

import android.annotation.SuppressLint
import android.app.Activity
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
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.nas.alreem.R
import com.nas.alreem.activity.communication.commingup.ComingUpWholeSchool
import com.nas.alreem.activity.communication.information.CommunicationInformationActivity
import com.nas.alreem.activity.communication.newesletters.NewsLetterActivity
import com.nas.alreem.activity.communication.socialmedia.SocialMediaActivity
import com.nas.alreem.constants.*
import com.nas.alreem.fragment.communication.adapter.CommunicationAdapter
import com.nas.alreem.fragment.performing_arts.adapter.PerformingArtsListAdapter
import com.nas.alreem.fragment.performing_arts.model.PerformingResponseModel
import com.nas.alreem.fragment.performing_arts.model.SecondaryModel
import com.nas.alreem.rest.ApiClient
import com.nas.alreem.rest.ApiInterface
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommunicationFragment : Fragment() {

    lateinit var mContext: Context
    lateinit var mEarlyYearsRecycler: RecyclerView
    lateinit var titleTextView: TextView
    lateinit var progressDialogAdd: ProgressBar
    lateinit var mCommunicationList:ArrayList<String>


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View?
    {
        return inflater.inflate(R.layout.fragment_communications, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mContext=requireContext()
        initializeUI()
        //mListView.setAdapter(new CommunicationsAdapter(getActivity(), mListViewArray));
        if (ConstantFunctions.internetCheck(mContext!!))
        {
            getCommunicationBanner()
        }
        else
        {
            DialogFunctions.showInternetAlertDialog(mContext!!)
        }

    }
    private fun initializeUI()
    {
        mEarlyYearsRecycler=requireView().findViewById(R.id.mEarlyYearsRecycler)
        progressDialogAdd=requireView().findViewById(R.id.progressDialogAdd)
        titleTextView=requireView().findViewById(R.id.titleTextView)
        titleTextView.text= ConstantWords.communications
        var linearLayoutManager = LinearLayoutManager(mContext)
        mEarlyYearsRecycler.layoutManager = linearLayoutManager
        mEarlyYearsRecycler.itemAnimator = DefaultItemAnimator()
        mCommunicationList= ArrayList()
        mCommunicationList.add("Coming Up - Whole School ")
        mCommunicationList.add("Information")
        mCommunicationList.add("Newsletters")
        mCommunicationList.add("Social Media")
        var communicationAdapter= CommunicationAdapter(mCommunicationList,mContext)
        mEarlyYearsRecycler.adapter=communicationAdapter

        mEarlyYearsRecycler.addOnItemClickListener(object : OnItemClickListener {
            @SuppressLint("SimpleDateFormat", "SetTextI18n")
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onItemClicked(position: Int, view: View)
            {

                if(position==0)
                {
                    val intent = Intent(mContext, ComingUpWholeSchool::class.java)
                    startActivity(intent)
                }
                else if (position==1)
                {
                    val intent = Intent(mContext, CommunicationInformationActivity::class.java)
                    startActivity(intent)
                }
                else if (position==2)
                {
                    val intent = Intent(mContext, NewsLetterActivity::class.java)
                    startActivity(intent)
                }
                else if (position==3)
                {
                    val intent = Intent(mContext, SocialMediaActivity::class.java)
                    startActivity(intent)

                }
            }

        })


    }
    private fun getCommunicationBanner() {
        val service: ApiInterface = ApiClient.getClient
        val call: Call<PerformingResponseModel> = service.communication_banner_images(
            "Bearer " + PreferenceManager.getaccesstoken(mContext)
        )
        call.enqueue(object : Callback<PerformingResponseModel> {
            override fun onResponse(
                call: Call<PerformingResponseModel>,
                response: Response<PerformingResponseModel>
            ) {
                // progressBarDialog.hide()
                if (response.isSuccessful()) {
                    if (response.body()!!.getResponsecode().equals("100")) {


                    } else {
                        /*AppUtils.showDialogAlertDismiss(
                            mContext as Activity?,
                            "Alert",
                            mContext!!.getString(R.string.common_error),
                            R.drawable.exclamationicon,
                            R.drawable.round
                        )*/
                    }
                } else {
                    /*AppUtils.showDialogAlertDismiss(
                        mContext as Activity?,
                        "Alert",
                        mContext!!.getString(R.string.common_error),
                        R.drawable.exclamationicon,
                        R.drawable.round
                    )*/
                }
            }

            override fun onFailure(call: Call<PerformingResponseModel>, t: Throwable) {
                // progressBarDialog.hide()
                /* AppUtils.showDialogAlertDismiss(
                     mContext as Activity?,
                     "Alert",
                     mContext!!.getString(R.string.common_error),
                     R.drawable.exclamationicon,
                     R.drawable.round
                 )*/
            }
        })
    }
}