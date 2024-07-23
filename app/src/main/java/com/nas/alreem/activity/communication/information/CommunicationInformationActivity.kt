package com.nas.alreem.activity.communication.information

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nas.alreem.R
import com.nas.alreem.activity.communication.information.model.InformationResponseModel
import com.nas.alreem.activity.home.HomeActivity
import com.nas.alreem.constants.ApiClient
import com.nas.alreem.constants.ConstantFunctions
import com.nas.alreem.constants.ConstantWords
import com.nas.alreem.constants.DialogFunctions
import com.nas.alreem.constants.OnItemClickListener
import com.nas.alreem.constants.PDFViewerActivity
import com.nas.alreem.constants.PreferenceManager
import com.nas.alreem.constants.WebLinkActivity
import com.nas.alreem.constants.addOnItemClickListener
import com.nas.alreem.fragment.communication.adapter.CommunicationInformationRecyclerAdapter
import com.nas.alreem.fragment.communication.model.CommunicationDataModel
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommunicationInformationActivity : AppCompatActivity() {
    lateinit var mContext: Context
    private lateinit var logoClickImg: ImageView
    lateinit var recyclerview: RecyclerView
    lateinit var back: ImageView
    lateinit var informationlist: ArrayList<
            InformationResponseModel.CommunicationInfo>
    lateinit var heading: TextView
    lateinit var logoClickImgView: ImageView
    lateinit var backRelative: RelativeLayout
    lateinit var progressDialogAdd: ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.canteen_information)
        initfn()
        progressDialogAdd.visibility= View.VISIBLE
        if (ConstantFunctions.internetCheck(mContext))
        {
            getList()
        }
        else
        {
            DialogFunctions.showInternetAlertDialog(mContext)
        }

    }

    private fun initfn() {
        mContext = this
        informationlist = ArrayList()
        recyclerview = findViewById(R.id.canteen_info_list)
        var linearLayoutManager = LinearLayoutManager(mContext)
        recyclerview.layoutManager = linearLayoutManager
        recyclerview.itemAnimator = DefaultItemAnimator()
        heading=findViewById(R.id.heading)
        backRelative=findViewById(R.id.backRelative)
        logoClickImgView=findViewById(R.id.logoClickImgView)
        progressDialogAdd=findViewById(R.id.progressDialogAdd)
        heading.text=ConstantWords.information
        backRelative.setOnClickListener(View.OnClickListener {
            finish()
        })

        logoClickImgView.setOnClickListener(View.OnClickListener {
            val intent = Intent(mContext, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        })

        recyclerview.addOnItemClickListener(object : OnItemClickListener {
            @SuppressLint("SimpleDateFormat", "SetTextI18n")
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onItemClicked(position: Int, view: View)
            {
                if (informationlist[position].filename?.endsWith(".pdf") == true) {
                    val intent = Intent(mContext, PDFViewerActivity::class.java)
                    intent.putExtra("Url", informationlist[position].filename)
                    intent.putExtra("title", informationlist[position].submenu)
                    startActivity(intent)
                } else {
                    val intent = Intent(
                        mContext,
                        WebLinkActivity::class.java
                    )
                    intent.putExtra("url", informationlist[position].filename)
                    intent.putExtra("heading",informationlist.get(position).submenu)
                    startActivity(intent)
                }

            }


        })

    }
    private fun  getList() {
        val token = PreferenceManager.getaccesstoken(mContext)
        val call: Call<InformationResponseModel> =
            ApiClient.getClient.communication_info("Bearer " + token)
        call.enqueue(object : Callback<InformationResponseModel> {
            override fun onFailure(call: Call<InformationResponseModel>, t: Throwable) {

                progressDialogAdd.visibility = View.GONE
            }

            override fun onResponse(
                call: Call<InformationResponseModel>,
                response: Response<InformationResponseModel>
            ) {
                val responsedata = response.body()
                progressDialogAdd.visibility = View.GONE

                if (responsedata!!.status == 100) {


                    val bannerImage = responsedata.responseArray.bannerImage

                    if (responsedata.responseArray.data.size > 0) {
                        for (i in 0 until responsedata.responseArray.data.size) {
                            informationlist.add(response.body()!!.responseArray.data[i])
                            Log.e("arraydata", informationlist.toString())
                            //  Log.e("array", String.valueOf(mCCAmodelArrayList));
                            //  } catch (e: JSONException) {
                            //    e.printStackTrace()
                            //   }
                        }
                        Log.e("success","sucess")
                        var customStaffDirectoryAdapter =
                            CommunicationInformationRecyclerAdapter(mContext, informationlist)
                        recyclerview.adapter=customStaffDirectoryAdapter

//											mListView.setAdapter(new CustomSecondaryAdapter(getActivity(), mListViewArray));
                        // mnewsLetterListView.setAdapter(new InformationRecyclerAdapter(mContext, mListViewArray));
                    } else {
                        Toast.makeText(mContext, "No data found", Toast.LENGTH_SHORT).show()
                    }



                }
                else {
                    DialogFunctions.commonErrorAlertDialog(mContext.resources.getString(R.string.alert), ConstantFunctions.commonErrorString(response.body()!!.status), mContext)

                }
            }

        })


    }
    @Throws(JSONException::class)
    private fun getSearchValues(Object: JSONObject): CommunicationDataModel {
        val mSecondaryModel = CommunicationDataModel()
        mSecondaryModel.id=(Object.getString("id"))
        mSecondaryModel.submenu=(Object.getString("submenu"))
        mSecondaryModel.filename=(Object.getString("filename"))
        return mSecondaryModel
    }
}