package com.nas.alreem.fragment.nae_programme

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
import com.nas.alreem.activity.sixth_form.SixthFormActivity
import com.nas.alreem.activity.sixth_form.SixthFormComingUpActivity
import com.nas.alreem.constants.*
import com.nas.alreem.fragment.primary.adapter.PrimaryAdapter
import com.nas.alreem.fragment.primary.model.PrimaryDataModel
import com.nas.alreem.fragment.primary.model.PrimaryResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NAEProgrammeFragment  : Fragment() {

    lateinit var mContext: Context
    lateinit var mEarlyYearsRecycler: RecyclerView
    lateinit var titleTextView: TextView
    lateinit var progressDialogAdd: ProgressBar
    lateinit var primaryArrayList:ArrayList<PrimaryDataModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.frament_early_years, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mContext=requireContext()
        initializeUI()
        if (ConstantFunctions.internetCheck(mContext))
        {
            callPrimaryApi()
        }
        else
        {
            DialogFunctions.showInternetAlertDialog(mContext)
        }

    }
    private fun initializeUI()
    {
        mEarlyYearsRecycler=requireView().findViewById(R.id.mEarlyYearsRecycler)
        progressDialogAdd=requireView().findViewById(R.id.progressDialogAdd)
        titleTextView=requireView().findViewById(R.id.titleTextView)
        titleTextView.text= ConstantWords.early_years
        var linearLayoutManager = LinearLayoutManager(mContext)
        mEarlyYearsRecycler.layoutManager = linearLayoutManager
        mEarlyYearsRecycler.itemAnimator = DefaultItemAnimator()

        mEarlyYearsRecycler.addOnItemClickListener(object : OnItemClickListener {
            @SuppressLint("SimpleDateFormat", "SetTextI18n")
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onItemClicked(position: Int, view: View)
            {
                if (position==0)
                {
                    val intent = Intent(mContext, SixthFormComingUpActivity::class.java)
                    startActivity(intent)
                }
                else
                {
                    val intent = Intent(mContext, SixthFormActivity::class.java)
                    intent.putExtra("list",primaryArrayList.get(position-1).file)
                    intent.putExtra("title",primaryArrayList.get(position-1).name)
                    startActivity(intent)
                }

            }
        })

    }
    private fun callPrimaryApi()
    {
        progressDialogAdd.visibility= View.VISIBLE
        primaryArrayList= ArrayList()
        val call: Call<PrimaryResponseModel> = ApiClient.getClient.earlyYearsList()
        call.enqueue(object : Callback<PrimaryResponseModel> {
            override fun onFailure(call: Call<PrimaryResponseModel>, t: Throwable) {
                Log.e("Failed", t.localizedMessage)
                progressDialogAdd.visibility= View.GONE
            }
            override fun onResponse(call: Call<PrimaryResponseModel>, response: Response<PrimaryResponseModel>) {
                val responsedata = response.body()
                progressDialogAdd.visibility= View.GONE
                if (responsedata != null)
                {
                    try
                    {

                        if (response.body()!!.status==100)
                        {

                            primaryArrayList=response.body()!!.responseArray!!.data!!
                            if (primaryArrayList.size>0)
                            {
                                var primaryAdapter= PrimaryAdapter(primaryArrayList,mContext)
                                mEarlyYearsRecycler.adapter=primaryAdapter
                            }

                        }
                        else
                        {
                            DialogFunctions.commonErrorAlertDialog(mContext.resources.getString(R.string.alert), ConstantFunctions.commonErrorString(response.body()!!.status), mContext)
                        }


                    }
                    catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

        })
    }
}