package com.nas.alreem.fragment.performing_arts

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.nas.alreem.R
import com.nas.alreem.activity.ProgressBarDialog
import com.nas.alreem.constants.ConstantFunctions
import com.nas.alreem.constants.DialogFunctions
import com.nas.alreem.constants.PDFViewerActivity
import com.nas.alreem.constants.PreferenceManager
import com.nas.alreem.constants.WebLinkActivity
import com.nas.alreem.fragment.performing_arts.adapter.PerformingArtsListAdapter
import com.nas.alreem.fragment.performing_arts.model.PerformingResponseModel
import com.nas.alreem.fragment.performing_arts.model.SecondaryModel
import com.nas.alreem.recyclermanager.DividerItemDecoration
import com.nas.alreem.recyclermanager.ItemOffsetDecoration
import com.nas.alreem.recyclermanager.RecyclerItemListener
import com.nas.alreem.rest.ApiClient
import com.nas.alreem.rest.ApiInterface
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PerformingArtsFragment : Fragment
     {
    lateinit var mTitleTextView: TextView
    lateinit var descriptionTV: TextView
    lateinit var descriptionTitle: TextView
    lateinit var text_content: TextView
    lateinit var text_dialog: TextView

    //	ViewPager bannerImagePager;
    lateinit var bannerImagePager: ImageView
    lateinit var bannerUrlImageArray: ArrayList<String>
    lateinit var mailImageView: ImageView
    var description = ""
    var contactEmail = ""
    lateinit var progressBarDialog: ProgressBarDialog
    private var mRootView: View? = null
     lateinit var mContext: Context

    //	private ListView mListView;
    private lateinit var mListView: RecyclerView
    private var mTitle: String? = null
    private var mTabId: String? = null
    private lateinit var relMain: RelativeLayout
    private lateinit var mtitleRel: RelativeLayout
     lateinit var mListViewArray: ArrayList<SecondaryModel>

    constructor()
    constructor(title: String?, tabId: String?) {
        mTitle = title
        mTabId = tabId
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * android.support.v4.app.Fragment#onCreateOptionsMenu(android.view.Menu,
     * android.view.MenuInflater)
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mRootView = inflater.inflate(
            R.layout.fragment_performingart_list, container,
            false
        )
        //		setHasOptionsMenu(true);
        //		Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(mContext));
        mContext = requireContext()
        initialiseUI()


        //		GetAboutUsListAsyncTask aboutUsTask = new GetAboutUsListAsyncTask(
//				URL_ABOUTUS_LIST, ABOUT_US_DIR, 1, mTabId);
//		aboutUsTask.execute();
        return mRootView
    }

    /*******************************************************
     * Method name : initialiseUI Description : initialise UI elements
     * Parameters : nil Return type : void Date : Jan 12, 2015 Author : Vandana
     * Surendranath
     */
    private fun initialiseUI() {
        mTitleTextView = mRootView!!.findViewById<View>(R.id.titleTextView) as TextView
        mTitleTextView.setText("Performing Arts")
        //		mListView = (ListView) mRootView.findViewById(R.id.mPerformingArtListView);
        mListView = mRootView!!.findViewById<View>(R.id.mPerformingArtListView) as RecyclerView
        bannerImagePager = mRootView!!.findViewById<View>(R.id.bannerImagePager) as ImageView
        //		bannerImagePager= (ViewPager) mRootView.findViewById(R.id.bannerImagePager);
        descriptionTV = mRootView!!.findViewById<View>(R.id.descriptionTV) as TextView
        descriptionTitle = mRootView!!.findViewById<View>(R.id.descriptionTitle) as TextView
        mailImageView = mRootView!!.findViewById<View>(R.id.mailImageView) as ImageView
        mtitleRel = mRootView!!.findViewById<View>(R.id.title) as RelativeLayout
        relMain = mRootView!!.findViewById<View>(R.id.relMain) as RelativeLayout
       // progressBarDialog = ProgressBarDialog(mContext, R.drawable.spinner)
        relMain!!.setOnClickListener { }
        //		mListView.setOnItemClickListener(this);
        mailImageView!!.setOnClickListener {
            val dialog = Dialog(mContext!!)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.alert_send_email_dialog)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val dialogCancelButton =
                dialog.findViewById<View>(R.id.cancelButton) as Button
            val submitButton =
                dialog.findViewById<View>(R.id.submitButton) as Button
            text_dialog =
                dialog.findViewById<View>(R.id.text_dialog) as EditText
            text_content =
                dialog.findViewById<View>(R.id.text_content) as EditText
            dialogCancelButton.setOnClickListener { dialog.dismiss() }
            submitButton.setOnClickListener {
                //sendEmailToStaff()
            }
            dialog.show()
        }

//        mNewsLetterListView.addItemDecoration(new DividerItemDecoration(getResources().getDrawable(R.drawable.list_divider)));
        /*GridLayoutManager recyclerViewLayout= new GridLayoutManager(mContext, 4);
		int spacing = 5; // 50px
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(mContext,spacing);
		mListView.addItemDecoration(itemDecoration);
		mListView.setLayoutManager(recyclerViewLayout);*/mListView!!.setHasFixedSize(true)
        val llm = LinearLayoutManager(mContext)
        llm.orientation = LinearLayoutManager.VERTICAL
        val spacing = 5 // 50px
        val itemDecoration = ItemOffsetDecoration(mContext!!, spacing)
        mListView!!.addItemDecoration(itemDecoration)
        mListView!!.layoutManager = llm
        mListView!!.addItemDecoration(DividerItemDecoration(resources.getDrawable(R.drawable.list_divider)))
        mListView!!.addOnItemTouchListener(
            RecyclerItemListener(
                activity, mListView!!,
                object : RecyclerItemListener.RecyclerTouchListener {
                    override fun onClickItem(v: View?, position: Int) {
                        if (mListViewArray.get(position).getmFile().endsWith(".pdf")) {
                            val intent = Intent(mContext, PDFViewerActivity::class.java)
                            intent.putExtra("pdf_url", mListViewArray!![position].getmFile())
                            startActivity(intent)
                        } else {
                            val intent = Intent(mContext, WebLinkActivity::class.java)
                            intent.putExtra("url", mListViewArray!![position].getmFile())
                            intent.putExtra("tab_type", mListViewArray!![position].getmFile())
                            mContext!!.startActivity(intent)
                        }
                    }

                    override fun onLongClickItem(v: View?, position: Int) {}
                }))
        var internetCheck = ConstantFunctions.internetCheck(mContext)
        if (internetCheck) {
            callPerformingArtsListAPI()

        } else {
            DialogFunctions.showInternetAlertDialog(mContext)
        }

        if (!description.equals("", ignoreCase = true)) {
            descriptionTV!!.visibility = View.VISIBLE
            descriptionTV!!.text = description
            descriptionTitle!!.visibility = View.GONE
            //			descriptionTitle.setVisibility(View.VISIBLE);
        } else {
            descriptionTV!!.visibility = View.GONE
            descriptionTitle!!.visibility = View.GONE
        }
        if (!contactEmail.equals("", ignoreCase = true)) {
            mailImageView!!.visibility = View.VISIBLE
        } else {
            mailImageView!!.visibility = View.INVISIBLE
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget
     * .AdapterView, android.view.View, int, long)
     */
     fun onItemClick(
        parent: AdapterView<*>?, view: View, position: Int,
        id: Long
    ) {
        if (mListViewArray.get(position).getmFile().endsWith(".pdf")) {
            val intent = Intent(mContext, PDFViewerActivity::class.java)
            intent.putExtra("pdf_url", mListViewArray!![position].getmFile())
            intent.putExtra("title",mListViewArray.get(position).title)

            startActivity(intent)
        } else {
            val intent = Intent(
                mContext,
                WebLinkActivity::class.java
            )
            intent.putExtra("url", mListViewArray!![position].getmFile())
            intent.putExtra("heading",mListViewArray.get(position).title)
            mContext!!.startActivity(intent)
        }
    }

    private fun callPerformingArtsListAPI() {
      //  progressBarDialog.show()
        val service: ApiInterface = ApiClient.getClient
        val call: Call<PerformingResponseModel> = service.performing_arts(
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
                        Log.e("success","success");

                        val bannerImage: String = response.body()!!.getResponse()!!.banner_image
                           // description = response.body()!!.getResponse().getDescription()
                           // contactEmail = response.body()!!.getResponse().getContactEmail()
                            if (!bannerImage.equals("", ignoreCase = true)) {
//											bannerUrlImageArray = new ArrayList<>();
//											bannerUrlImageArray.add(bannerImage);
//											bannerImagePager.setAdapter(new ImagePagerDrawableAdapter(bannerUrlImageArray, getActivity()));
                                Glide.with(mContext!!).load(ConstantFunctions.replace(bannerImage))
                                    .centerCrop().into(bannerImagePager)
                            } else {
                                bannerImagePager!!.setBackgroundResource(R.drawable.default_banner)
                                //											bannerImagePager.setBackgroundResource(R.drawable.performigart);
                            }
                            if (description.equals("", ignoreCase = true) && contactEmail.equals(
                                    "",
                                    ignoreCase = true
                                )
                            ) {
                                mtitleRel!!.visibility = View.GONE
                            } else {
                                mtitleRel!!.visibility = View.VISIBLE
                            }
                            if (description.equals("", ignoreCase = true)) {
                                descriptionTV!!.visibility = View.GONE
                                descriptionTitle!!.visibility = View.GONE
                            } else {
                                descriptionTV!!.text = description
                                descriptionTitle!!.visibility = View.GONE
                                //			descriptionTitle.setVisibility(View.VISIBLE);
                                descriptionTV!!.visibility = View.VISIBLE
                                mtitleRel!!.visibility = View.VISIBLE
                            }
                            if (contactEmail.equals("", ignoreCase = true)) {
                                mailImageView!!.visibility = View.GONE
                            } else {
                                mtitleRel!!.visibility = View.VISIBLE
                                mailImageView!!.visibility = View.VISIBLE
                            }
                            mListViewArray = ArrayList<SecondaryModel>()
                        Log.e("arraysize", response.body()!!.getResponse()!!.data!!.size.toString());

                        if (response.body()!!.getResponse()!!.data!!.size > 0) {
                                for (i in 0 until response.body()!!.getResponse()!!.data!!.size) {
                                    val item: SecondaryModel=
                                        response.body()!!.getResponse()!!.data!!.get(i)
                                    val gson = Gson()
                                    val eventJson = gson.toJson(item)
                                    try {
                                        val jsonObject = JSONObject(eventJson)
                                        mListViewArray!!.add(getSearchValues(jsonObject))
                                        //  Log.e("array", String.valueOf(mCCAmodelArrayList));
                                    } catch (e: JSONException) {
                                        e.printStackTrace()
                                    }
                                }
                                //											mListView.setAdapter(new CustomSecondaryAdapter(getActivity(), mListViewArray));
                                mListView!!.adapter =
                                    PerformingArtsListAdapter(mContext, mListViewArray)
                            } else {
                                //CustomStatusDialog();
                                Toast.makeText(mContext, "No Data Found", Toast.LENGTH_SHORT).show()
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

    /*val list: Unit
        get() {
            try {
                mListViewArray = ArrayList<SecondaryModel>()
                val manager = VolleyWrapper(URL_PERFORMING_ARTS_LIST)
                val name = arrayOf<String>(JTAG_ACCESSTOKEN)
                val value = arrayOf<String>(PreferenceManager.getAccessToken(mContext))
                manager.getResponsePOST(mContext, 11, name, value, object : ResponseListener() {
                    fun responseSuccess(successResponse: String?) {
                        var responsCode = ""
                        if (successResponse != null) {
                            try {
                                val rootObject = JSONObject(successResponse)
                                if (rootObject.optString(JTAG_RESPONSE) != null) {
                                    responsCode = rootObject.optString(JTAG_RESPONSECODE)
                                    if (responsCode == RESPONSE_SUCCESS) {
                                        val respObject = rootObject.getJSONObject(JTAG_RESPONSE)
                                        val statusCode = respObject.optString(JTAG_STATUSCODE)
                                        if (statusCode == STATUS_SUCCESS) {
                                            val bannerImage =
                                                respObject.optString(JTAG_BANNER_IMAGE)
                                            description = respObject.optString(JTAG_DESCRIPTION)
                                            contactEmail = respObject.optString(JTAG_CONTACT_EMAIL)
                                            if (!bannerImage.equals("", ignoreCase = true)) {
                                                //											bannerUrlImageArray = new ArrayList<>();
                                                //											bannerUrlImageArray.add(bannerImage);
                                                //											bannerImagePager.setAdapter(new ImagePagerDrawableAdapter(bannerUrlImageArray, getActivity()));
                                                Glide.with(mContext!!)
                                                    .load(AppUtils.replace(bannerImage))
                                                    .centerCrop().into(bannerImagePager)
                                            } else {
                                                bannerImagePager!!.setBackgroundResource(R.drawable.default_banner)
                                                //											bannerImagePager.setBackgroundResource(R.drawable.performigart);
                                            }
                                            if (description.equals(
                                                    "",
                                                    ignoreCase = true
                                                ) && contactEmail.equals("", ignoreCase = true)
                                            ) {
                                                mtitleRel!!.visibility = View.GONE
                                            } else {
                                                mtitleRel!!.visibility = View.VISIBLE
                                            }
                                            if (description.equals("", ignoreCase = true)) {
                                                descriptionTV!!.visibility = View.GONE
                                                descriptionTitle!!.visibility = View.GONE
                                            } else {
                                                descriptionTV!!.text = description
                                                descriptionTitle!!.visibility = View.GONE
                                                //			descriptionTitle.setVisibility(View.VISIBLE);
                                                descriptionTV!!.visibility = View.VISIBLE
                                                mtitleRel!!.visibility = View.VISIBLE
                                            }
                                            if (contactEmail.equals("", ignoreCase = true)) {
                                                mailImageView!!.visibility = View.GONE
                                            } else {
                                                mtitleRel!!.visibility = View.VISIBLE
                                                mailImageView!!.visibility = View.VISIBLE
                                            }
                                            val dataArray =
                                                respObject.getJSONArray("Performing_arts_array")
                                            if (dataArray.length() > 0) {
                                                for (i in 0 until dataArray.length()) {
                                                    val dataObject = dataArray.getJSONObject(i)
                                                    mListViewArray!!.add(getSearchValues(dataObject))
                                                }
                                                //											mListView.setAdapter(new CustomSecondaryAdapter(getActivity(), mListViewArray));

                                                mListView!!.adapter = PerformingArtsListAdapter(mContext, mListViewArray)
                                            } else {
                                                //CustomStatusDialog();
                                                Toast.makeText(
                                                    mContext,
                                                    "No Data Found",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        } else {
                                            //										CustomStatusDialog(RESPONSE_FAILURE);
                                            //Toast.makeText(mContext,"Failure",Toast.LENGTH_SHORT).show();
                                            AppUtils.showDialogAlertDismiss(
                                                mContext as Activity?,
                                                "Alert",
                                                getString(R.string.common_error),
                                                R.drawable.exclamationicon,
                                                R.drawable.round
                                            )
                                        }
                                    } else if (responsCode.equals(
                                            RESPONSE_ACCESSTOKEN_MISSING,
                                            ignoreCase = true
                                        ) ||
                                        responsCode.equals(
                                            RESPONSE_ACCESSTOKEN_EXPIRED,
                                            ignoreCase = true
                                        ) ||
                                        responsCode.equals(
                                            RESPONSE_INVALID_TOKEN,
                                            ignoreCase = true
                                        )
                                    ) {
                                        AppUtils.postInitParam(
                                            activity,
                                            object : GetAccessTokenInterface() {
                                                val accessToken: Unit
                                                    get() {}
                                            })
                                        list
                                    } else if (responsCode == RESPONSE_ERROR) {
                                        //								CustomStatusDialog(RESPONSE_FAILURE);
                                        //Toast.makeText(mContext,"Failure", Toast.LENGTH_SHORT).show();
                                        AppUtils.showDialogAlertDismiss(
                                            mContext as Activity?,
                                            "Alert",
                                            getString(R.string.common_error),
                                            R.drawable.exclamationicon,
                                            R.drawable.round
                                        )
                                    }
                                } else {
                                    //								CustomStatusDialog(RESPONSE_FAILURE);
                                    //Toast.makeText(mContext,"Failure", Toast.LENGTH_SHORT).show();
                                    AppUtils.showDialogAlertDismiss(
                                        mContext as Activity?,
                                        "Alert",
                                        getString(R.string.common_error),
                                        R.drawable.exclamationicon,
                                        R.drawable.round
                                    )
                                }
                            } catch (ex: Exception) {
                                ex.printStackTrace()
                            }
                        }
                    }

                    fun responseFailure(failureResponse: String?) {
                        AppUtils.showDialogAlertDismiss(
                            mContext as Activity?,
                            "Alert",
                            getString(R.string.common_error),
                            R.drawable.exclamationicon,
                            R.drawable.round
                        )
                    }
                })
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }*/

         @Throws(JSONException::class)
         private fun getSearchValues(Object: JSONObject): SecondaryModel {
             val mSecondaryModel = SecondaryModel()
             mSecondaryModel.setmId(Object.getString("id"))
             mSecondaryModel.setmFile(Object.getString("file"))
             mSecondaryModel.setmName(Object.getString("name"))
             return mSecondaryModel
         }

    private fun sendEmailToStaff(URL: String) {
       /* val volleyWrapper = VolleyWrapper(URL)
        val name = arrayOf("access_token", "email", "users_id", "title", "message")
        val value = arrayOf<String>(
            PreferenceManager.getAccessToken(mContext),
            contactEmail,
            PreferenceManager.getUserId(mContext),
            text_dialog!!.text.toString(),
            text_content!!.text.toString()
        ) //contactEmail

        //String[] value={PreferenceManager.getAccessToken(mContext),mStaffList.get(pos).getStaffEmail(),JTAG_USERS_ID_VALUE,text_dialog.getText().toString(),text_content.getText().toString()};
        volleyWrapper.getResponsePOST(mContext, 11, name, value, object : ResponseListener() {
            fun responseSuccess(successResponse: String?) {
                try {
                    val obj = JSONObject(successResponse)
                    val response_code = obj.getString(JTAG_RESPONSECODE)
                    if (response_code.equals("200", ignoreCase = true)) {
                        val secobj = obj.getJSONObject(JTAG_RESPONSE)
                        val status_code = secobj.getString(JTAG_STATUSCODE)
                        if (status_code.equals("303", ignoreCase = true)) {
                            val toast = Toast.makeText(
                                mContext,
                                "Successfully sent email to staff",
                                Toast.LENGTH_SHORT
                            )
                            toast.show()
                        } else {
                            val toast =
                                Toast.makeText(mContext, "Email not sent", Toast.LENGTH_SHORT)
                            toast.show()
                        }
                    } else if (response_code.equals("500", ignoreCase = true)) {
                    } else if (response_code.equals("400", ignoreCase = true)) {
                        AppUtils.getToken(mContext, object : GetTokenSuccess() {
                            fun tokenrenewed() {}
                        })
                        sendEmailToStaff(URL_SEND_EMAIL_TO_STAFF)
                    } else if (response_code.equals("401", ignoreCase = true)) {
                        AppUtils.getToken(mContext, object : GetTokenSuccess() {
                            fun tokenrenewed() {}
                        })
                        sendEmailToStaff(URL_SEND_EMAIL_TO_STAFF)
                    } else if (response_code.equals("402", ignoreCase = true)) {
                        AppUtils.getToken(mContext, object : GetTokenSuccess() {
                            fun tokenrenewed() {}
                        })
                        sendEmailToStaff(URL_SEND_EMAIL_TO_STAFF)
                    } else {
                        *//*CustomDialog dialog = new CustomDialog(mContext, getResources().getString(R.string.common_error)
								, getResources().getString(R.string.ok));
						dialog.show();*//*
                        AppUtils.showDialogAlertDismiss(
                            mContext as Activity?,
                            "Alert",
                            mContext!!.getString(R.string.common_error),
                            R.drawable.exclamationicon,
                            R.drawable.round
                        )
                    }
                } catch (ex: Exception) {
                }
            }

            fun responseFailure(failureResponse: String?) {
                *//*CustomDialog dialog = new CustomDialog(mContext, getResources().getString(R.string.common_error)
						, getResources().getString(R.string.ok));
				dialog.show();*//*
                AppUtils.showDialogAlertDismiss(
                    mContext as Activity?,
                    "Alert",
                    mContext!!.getString(R.string.common_error),
                    R.drawable.exclamationicon,
                    R.drawable.round
                )
            }
        })*/
    }
}
