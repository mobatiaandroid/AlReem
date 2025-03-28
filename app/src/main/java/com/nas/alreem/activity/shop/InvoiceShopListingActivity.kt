package com.nas.alreem.activity.shop

import android.app.Activity
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
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nas.alreem.R
import com.nas.alreem.activity.home.HomeActivity
import com.nas.alreem.activity.payments.adapter.StudentListAdapter
import com.nas.alreem.activity.payments.model.StudentList
import com.nas.alreem.constants.ConstantFunctions
import com.nas.alreem.constants.PreferenceManager
import com.nas.alreem.recyclermanager.DividerItemDecoration
import com.nas.alreem.recyclermanager.RecyclerItemListener
import com.nas.alreem.recyclermanager.RecyclerItemListener.RecyclerTouchListener

class InvoiceShopListingActivity :AppCompatActivity() {
    var relativeHeader: RelativeLayout? = null

    // HeaderManager headermanager;
    var back: ImageView? = null
    var home: ImageView? = null
    lateinit var mContext: Context
    var studImg: ImageView? = null
    var stud_img: String? = ""
    var extras: Bundle? = null
    var studentNameStr: String? = ""
    var studentClassStr = ""
    var studentIdStr: String? = ""
    var studentName: TextView? = null
    var mStudentSpinner: LinearLayout? = null

    //  ArrayList<MusicPaymentHistoryModel> newsLetterModelArrayList = new ArrayList<>();
    var studentsModelArrayList: ArrayList<StudentList>? = ArrayList()

    // ArrayList<MusicPaymentHistoryModel> paymentHistoryList = new ArrayList<>();
    var mNewsLetterListView: RecyclerView? = null
    // ProgressBarDialog progressBarDialog;

    // ProgressBarDialog progressBarDialog;
    lateinit var activity: Activity

    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invoice_listing)
        mContext = this
        activity = this
        initialiseUI()
    }

    private fun initialiseUI() {
        extras = getIntent().getExtras()
        if (extras != null) {
            studentNameStr = extras!!.getString("studentName")
            studentIdStr = extras!!.getString("studentId")
            studentsModelArrayList = extras!!
                .getSerializable("StudentModelArray") as ArrayList<StudentList>?
            stud_img = extras!!.getString("studentImage")
        }
        relativeHeader = findViewById<View>(R.id.relativeHeader) as RelativeLayout?
        mStudentSpinner = findViewById<View>(R.id.studentSpinner) as LinearLayout?
        studentName = findViewById<View>(R.id.studentName) as TextView?
        studImg = findViewById<View>(R.id.imagicon) as ImageView?
        mNewsLetterListView = findViewById<View>(R.id.mListView) as RecyclerView?
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        mNewsLetterListView!!.layoutManager = llm
        mNewsLetterListView!!.setHasFixedSize(true)
        mNewsLetterListView!!.addItemDecoration(DividerItemDecoration(getResources().getDrawable(R.drawable.list_divider_teal)))
        //   headermanager = new HeaderManager(InvoiceListingActivity.this, "Payment History");
        //   headermanager.getHeader(relativeHeader, 0);
        //  progressBarDialog = new ProgressBarDialog(mContext, R.drawable.spinner);

        //  back = headermanager.getLeftButton();

        //  home = headermanager.getLogoButton();
        home!!.setOnClickListener {
            val `in` = Intent(mContext, HomeActivity::class.java)
            `in`.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(`in`)
        }
        /*  headermanager.setButtonLeftSelector(R.drawable.back,
              R.drawable.back);*/back!!.setOnClickListener { finish() }
        /* if (AppUtils.isNetworkConnected(mContext)) {
         // TODO INVOICE LIST
         getPaymentHistory(studentIdStr);
        // getEventsListApi(studentIdStr);
     } else {
         AppUtils.showDialogAlertDismiss((Activity) mContext, "Network Error", getString(R.string.no_internet), R.drawable.nonetworkicon, R.drawable.roundred);

     }*/mStudentSpinner!!.setOnClickListener {
            if (studentsModelArrayList!!.size > 0) {
                showSocialmediaList(studentsModelArrayList)
            } else {
                //  AppUtils.showDialogAlertDismiss((Activity) mContext, "Alert", getString(R.string.student_not_available), R.drawable.exclamationicon, R.drawable.round);
            }
        }
        studentName!!.text = studentNameStr
        if (stud_img != "") {

            // Picasso.with(mContext).load(AppUtils.replace(stud_img)).placeholder(R.drawable.boy).fit().into(studImg);
        } else {
            studImg!!.setImageResource(R.drawable.boy)
        }
        //  PreferenceManager.setStudentID(mContext, studentIdStr);
        mNewsLetterListView!!.addOnItemTouchListener(
            RecyclerItemListener(mContext,
                mNewsLetterListView!!,
                object : RecyclerTouchListener {
                    override fun onClickItem(v: View?, position: Int) {
                        val intent = Intent(mContext, MusicShopInvoicePrint::class.java)
                        intent.putExtra("title", "Music Academy Registration")
                        // intent.putExtra("key", paymentHistoryList.get(position).getOrder_summery());
                        //  intent.putExtra("orderId", paymentHistoryList.get(position).getId());
                        // intent.putExtra("invoice", paymentHistoryList.get(position).getInvoice());
                        // intent.putExtra("amount", paymentHistoryList.get(position).getAmount());
                        //  intent.putExtra("paidby", paymentHistoryList.get(position).getName());
                        //  intent.putExtra("paidDate", paymentHistoryList.get(position).getDate_time());
                        //  intent.putExtra("tr_no", paymentHistoryList.get(position).getTrn_no());
                        //  intent.putExtra("payment_type", paymentHistoryList.get(position).getPayment_type());

                        /* if (paymentHistoryList.get(position).getBill_no().equalsIgnoreCase("")) {
                            intent.putExtra("billingCode", "--");

                        } else {
                            intent.putExtra("billingCode", paymentHistoryList.get(position).getBill_no());

                        }*/mContext!!.startActivity(intent)
                    }

                    override fun onLongClickItem(v: View?, position: Int) {
                        // System.out.println("On Long Click Item interface");
                    }
                })
        )
    }

    private fun getPaymentHistory(studentIdStr: String) {
        /*APIInterface service = APIClient.getRetrofitInstance().create(APIInterface.class);
        JsonObject paramObject = new JsonObject();
        paramObject.addProperty("student_id", studentIdStr);
        Call<PaymentHistoryResponseModel> call = service.postMusicPaymentHistory("Bearer "+PreferenceManager.getAccessToken(mContext),paramObject);
        progressBarDialog.show();
        call.enqueue(new Callback<PaymentHistoryResponseModel>() {
                         @Override
                         public void onResponse(Call<PaymentHistoryResponseModel> call, Response<PaymentHistoryResponseModel> response) {
                             progressBarDialog.hide();
                             if (response.isSuccessful()) {
                                 PaymentHistoryResponseModel apiResponse = response.body();
                                 String response_code = String.valueOf(apiResponse.getResponseCode());
                                 if (response_code.equalsIgnoreCase("200")) {
                                     String status_code = String.valueOf(apiResponse.getResponse().getStatusCode());

                                     if (status_code.equalsIgnoreCase("303")) {

                                         if (apiResponse.getResponse().getData().getPayment_history().size() > 0) {
                                             paymentHistoryList = new ArrayList<>();
                                             for (int i = 0; i < apiResponse.getResponse().getData().getPayment_history().size(); i++) {
                                                 MusicPaymentHistoryModel item = response.body().getResponse().getData().getPayment_history().get(i);
                                                 Gson gson = new Gson();
                                                 String eventJson = gson.toJson(item);

                                                 try {
                                                     JSONObject jsonObject = new JSONObject(eventJson);
                                                     MusicPaymentHistoryModel paymentHistoryModel = addEventsDetails(jsonObject);
                                                     paymentHistoryList.add(paymentHistoryModel);
                                                     //  Log.e("array", String.valueOf(mCCAmodelArrayList));
                                                 } catch (JSONException e) {
                                                     e.printStackTrace();
                                                 }

                                             }

                                             mNewsLetterListView.setVisibility(View.VISIBLE);
                                             MusicPaymentHistoryRecyclerAdapter newsLetterAdapter = new MusicPaymentHistoryRecyclerAdapter(mContext, paymentHistoryList);
                                             mNewsLetterListView.setAdapter(newsLetterAdapter);

                                         } else {
                                             newsLetterModelArrayList.clear();
                                             mNewsLetterListView.setVisibility(View.GONE);
                                             MusicPaymentHistoryRecyclerAdapter newsLetterAdapter = new MusicPaymentHistoryRecyclerAdapter(mContext, paymentHistoryList);
                                             mNewsLetterListView.setAdapter(newsLetterAdapter);
                                             AppUtils.showDialogAlertDismiss((Activity) mContext, "Alert", "No data available", R.drawable.exclamationicon, R.drawable.round);
                                         }
                                     } else {

                                         mNewsLetterListView.setVisibility(View.GONE);
                                         AppUtils.showDialogAlertDismiss((Activity) mContext, "Alert", getString(R.string.common_error), R.drawable.exclamationicon, R.drawable.round);

                                     }
                                 }
                             }
                         }

                         @Override
                         public void onFailure(Call<PaymentHistoryResponseModel> call, Throwable t) {

                         }
                     }
        );*/
    }

    /*private void getEventsListApi(String studentIdStr) {
        VolleyWrapper volleyWrapper = new VolleyWrapper(URLConstants.URL_MUSIC_ACADEMY_PAYMENT_HISTORY);
        String[] name = {"access_token", "student_id"};
        String[] value = {PreferenceManager.getAccessToken(mContext), studentIdStr};
        volleyWrapper.getResponsePOST(mContext, 11, name, value, new VolleyWrapper.ResponseListener() {
            @Override
            public void responseSuccess(String successResponse) {
                System.out.println("The response is" + successResponse);
                try {
                    JSONObject obj = new JSONObject(successResponse);
                    String response_code = obj.getString(JTAG_RESPONSECODE);
                    if (response_code.equalsIgnoreCase("200")) {
                        JSONObject secobj = obj.getJSONObject(JTAG_RESPONSE);
                        String status_code = secobj.getString(JTAG_STATUSCODE);
                        if (status_code.equalsIgnoreCase("303")) {
                            JSONObject responseData = secobj.getJSONObject("data");
                            JSONArray paymentHistoryArray = responseData.getJSONArray("payment_history");
                            Log.e("dast size", String.valueOf(paymentHistoryArray.length()));
                            if (paymentHistoryArray.length() > 0) {
                                paymentHistoryList = new ArrayList<>();
                                for (int i = 0; i < paymentHistoryArray.length(); i++) {
                                    JSONObject paymentObject = paymentHistoryArray.getJSONObject(i);
                                    MusicPaymentHistoryModel paymentHistoryModel = addEventsDetails(paymentObject);
                                    paymentHistoryList.add(paymentHistoryModel);
                                }
                                Log.e("payment data size", String.valueOf(paymentHistoryList.size()));
                                mNewsLetterListView.setVisibility(View.VISIBLE);
                                MusicPaymentHistoryRecyclerAdapter newsLetterAdapter = new MusicPaymentHistoryRecyclerAdapter(mContext, paymentHistoryList);
                                mNewsLetterListView.setAdapter(newsLetterAdapter);

                            } else {
                                newsLetterModelArrayList.clear();
                                mNewsLetterListView.setVisibility(View.GONE);
                                MusicPaymentHistoryRecyclerAdapter newsLetterAdapter = new MusicPaymentHistoryRecyclerAdapter(mContext, paymentHistoryList);
                                mNewsLetterListView.setAdapter(newsLetterAdapter);
                                AppUtils.showDialogAlertDismiss((Activity) mContext, "Alert", "No data available", R.drawable.exclamationicon, R.drawable.round);
                            }
                        } else {
                            mNewsLetterListView.setVisibility(View.GONE);
                            AppUtils.showDialogAlertDismiss((Activity) mContext, "Alert", getString(R.string.common_error), R.drawable.exclamationicon, R.drawable.round);

                        }
                    } else if (response_code.equalsIgnoreCase("500")) {
                        AppUtils.showDialogAlertDismiss((Activity) mContext, "Alert", getString(R.string.common_error), R.drawable.exclamationicon, R.drawable.round);

                    } else if (response_code.equalsIgnoreCase("400")) {
                        AppUtils.getToken(mContext, new AppUtils.GetTokenSuccess() {
                            @Override
                            public void tokenrenewed() {
                            }
                        });
                        getEventsListApi(studentIdStr);

                    } else if (response_code.equalsIgnoreCase("401")) {
                        AppUtils.getToken(mContext, new AppUtils.GetTokenSuccess() {
                            @Override
                            public void tokenrenewed() {
                            }
                        });
                        getEventsListApi(studentIdStr);

                    } else if (response_code.equalsIgnoreCase("402")) {
                        AppUtils.getToken(mContext, new AppUtils.GetTokenSuccess() {
                            @Override
                            public void tokenrenewed() {
                            }
                        });
                        getEventsListApi(studentIdStr);

                    } else {
						CustomDialog dialog = new CustomDialog(mContext, getResources().getString(R.string.common_error)
								, getResources().getString(R.string.ok));
						dialog.show();
                        AppUtils.showDialogAlertDismiss((Activity) mContext, "Alert", getString(R.string.common_error), R.drawable.exclamationicon, R.drawable.round);

                    }
                } catch (Exception ex) {
                    System.out.println("The Exception in edit profile is" + ex.toString());
                }

            }

            @Override
            public void responseFailure(String failureResponse) {


            }
        });
    }*/


    /*private MusicPaymentHistoryModel addEventsDetails(JSONObject obj) {
        MusicPaymentHistoryModel model = new MusicPaymentHistoryModel();
        try {
            if (obj.has("order_reference")) {
                model.setId(obj.optString("order_reference"));
            } else {
                model.setId("");
            }
            if (obj.has("created_on")) {
                model.setDate_time(obj.optString("created_on"));
            } else {
                model.setDate_time("");
            }
            if (obj.has("student_name")) {
                model.setName(obj.optString("student_name"));
            } else {
                model.setName("");
            }
            if (obj.has("order_total")) {
                model.setAmount(obj.optString("order_total"));
            } else {
                model.setAmount("");
            }
            if (obj.has("status")) {
                model.setStatus(obj.optString("status"));
            } else {
                model.setStatus("");
            }
            if (obj.has("keycode")) {
                model.setKeycode(obj.optString("keycode"));
            } else {
                model.setKeycode("");
            }
            if (obj.has("bill_no")) {
                model.setBill_no(obj.optString("bill_no"));
            } else {
                model.setBill_no("");
            }
            if (obj.has("invoice_note")) {
                model.setInvoice(obj.optString("invoice_note"));
            } else {
                model.setInvoice("");
            }
            if (obj.has("trn_no")) {
                model.setTrn_no(obj.optString("trn_no"));
            } else {
                model.setTrn_no("");
            }
            if (obj.has("payment_type")) {
                model.setPayment_type(obj.optString("payment_type"));
            } else {
                model.setPayment_type("");
            }
            if (obj.has("order_summery")) {
                JSONArray orderSummaryArray = obj.getJSONArray("order_summery");
                ArrayList<OrderSummary> orderSummaries = new ArrayList<>();
                for (int i = 0; i < orderSummaryArray.length(); i++) {
                    JSONObject orderSummaryObject = orderSummaryArray.getJSONObject(i);
                    OrderSummary orderSummary = new OrderSummary();
                    orderSummary.setId(orderSummaryObject.optString("id"));
                    orderSummary.setActual_amount(orderSummaryObject.optString("actual_amount"));
                    orderSummary.setTax_amount(orderSummaryObject.optString("tax_amount"));
                    orderSummary.setTotal_amount(orderSummaryObject.optString("total_amount"));
                    orderSummary.setInstrument_name(orderSummaryObject.optString("instrument_name"));
                    orderSummary.setTerm_name(orderSummaryObject.optString("term_name"));
                    orderSummary.setLesson_name(orderSummaryObject.optString("lesson_name"));
                    orderSummaries.add(orderSummary);
                }
                model.setOrder_summery(orderSummaries);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return model;
    }*/


    /*private void getEventsListApi(String studentIdStr) {
        VolleyWrapper volleyWrapper = new VolleyWrapper(URLConstants.URL_MUSIC_ACADEMY_PAYMENT_HISTORY);
        String[] name = {"access_token", "student_id"};
        String[] value = {PreferenceManager.getAccessToken(mContext), studentIdStr};
        volleyWrapper.getResponsePOST(mContext, 11, name, value, new VolleyWrapper.ResponseListener() {
            @Override
            public void responseSuccess(String successResponse) {
                System.out.println("The response is" + successResponse);
                try {
                    JSONObject obj = new JSONObject(successResponse);
                    String response_code = obj.getString(JTAG_RESPONSECODE);
                    if (response_code.equalsIgnoreCase("200")) {
                        JSONObject secobj = obj.getJSONObject(JTAG_RESPONSE);
                        String status_code = secobj.getString(JTAG_STATUSCODE);
                        if (status_code.equalsIgnoreCase("303")) {
                            JSONObject responseData = secobj.getJSONObject("data");
                            JSONArray paymentHistoryArray = responseData.getJSONArray("payment_history");
                            Log.e("dast size", String.valueOf(paymentHistoryArray.length()));
                            if (paymentHistoryArray.length() > 0) {
                                paymentHistoryList = new ArrayList<>();
                                for (int i = 0; i < paymentHistoryArray.length(); i++) {
                                    JSONObject paymentObject = paymentHistoryArray.getJSONObject(i);
                                    MusicPaymentHistoryModel paymentHistoryModel = addEventsDetails(paymentObject);
                                    paymentHistoryList.add(paymentHistoryModel);
                                }
                                Log.e("payment data size", String.valueOf(paymentHistoryList.size()));
                                mNewsLetterListView.setVisibility(View.VISIBLE);
                                MusicPaymentHistoryRecyclerAdapter newsLetterAdapter = new MusicPaymentHistoryRecyclerAdapter(mContext, paymentHistoryList);
                                mNewsLetterListView.setAdapter(newsLetterAdapter);

                            } else {
                                newsLetterModelArrayList.clear();
                                mNewsLetterListView.setVisibility(View.GONE);
                                MusicPaymentHistoryRecyclerAdapter newsLetterAdapter = new MusicPaymentHistoryRecyclerAdapter(mContext, paymentHistoryList);
                                mNewsLetterListView.setAdapter(newsLetterAdapter);
                                AppUtils.showDialogAlertDismiss((Activity) mContext, "Alert", "No data available", R.drawable.exclamationicon, R.drawable.round);
                            }
                        } else {
                            mNewsLetterListView.setVisibility(View.GONE);
                            AppUtils.showDialogAlertDismiss((Activity) mContext, "Alert", getString(R.string.common_error), R.drawable.exclamationicon, R.drawable.round);

                        }
                    } else if (response_code.equalsIgnoreCase("500")) {
                        AppUtils.showDialogAlertDismiss((Activity) mContext, "Alert", getString(R.string.common_error), R.drawable.exclamationicon, R.drawable.round);

                    } else if (response_code.equalsIgnoreCase("400")) {
                        AppUtils.getToken(mContext, new AppUtils.GetTokenSuccess() {
                            @Override
                            public void tokenrenewed() {
                            }
                        });
                        getEventsListApi(studentIdStr);

                    } else if (response_code.equalsIgnoreCase("401")) {
                        AppUtils.getToken(mContext, new AppUtils.GetTokenSuccess() {
                            @Override
                            public void tokenrenewed() {
                            }
                        });
                        getEventsListApi(studentIdStr);

                    } else if (response_code.equalsIgnoreCase("402")) {
                        AppUtils.getToken(mContext, new AppUtils.GetTokenSuccess() {
                            @Override
                            public void tokenrenewed() {
                            }
                        });
                        getEventsListApi(studentIdStr);

                    } else {
						CustomDialog dialog = new CustomDialog(mContext, getResources().getString(R.string.common_error)
								, getResources().getString(R.string.ok));
						dialog.show();
                        AppUtils.showDialogAlertDismiss((Activity) mContext, "Alert", getString(R.string.common_error), R.drawable.exclamationicon, R.drawable.round);

                    }
                } catch (Exception ex) {
                    System.out.println("The Exception in edit profile is" + ex.toString());
                }

            }

            @Override
            public void responseFailure(String failureResponse) {


            }
        });
    }*/
    /*private MusicPaymentHistoryModel addEventsDetails(JSONObject obj) {
        MusicPaymentHistoryModel model = new MusicPaymentHistoryModel();
        try {
            if (obj.has("order_reference")) {
                model.setId(obj.optString("order_reference"));
            } else {
                model.setId("");
            }
            if (obj.has("created_on")) {
                model.setDate_time(obj.optString("created_on"));
            } else {
                model.setDate_time("");
            }
            if (obj.has("student_name")) {
                model.setName(obj.optString("student_name"));
            } else {
                model.setName("");
            }
            if (obj.has("order_total")) {
                model.setAmount(obj.optString("order_total"));
            } else {
                model.setAmount("");
            }
            if (obj.has("status")) {
                model.setStatus(obj.optString("status"));
            } else {
                model.setStatus("");
            }
            if (obj.has("keycode")) {
                model.setKeycode(obj.optString("keycode"));
            } else {
                model.setKeycode("");
            }
            if (obj.has("bill_no")) {
                model.setBill_no(obj.optString("bill_no"));
            } else {
                model.setBill_no("");
            }
            if (obj.has("invoice_note")) {
                model.setInvoice(obj.optString("invoice_note"));
            } else {
                model.setInvoice("");
            }
            if (obj.has("trn_no")) {
                model.setTrn_no(obj.optString("trn_no"));
            } else {
                model.setTrn_no("");
            }
            if (obj.has("payment_type")) {
                model.setPayment_type(obj.optString("payment_type"));
            } else {
                model.setPayment_type("");
            }
            if (obj.has("order_summery")) {
                JSONArray orderSummaryArray = obj.getJSONArray("order_summery");
                ArrayList<OrderSummary> orderSummaries = new ArrayList<>();
                for (int i = 0; i < orderSummaryArray.length(); i++) {
                    JSONObject orderSummaryObject = orderSummaryArray.getJSONObject(i);
                    OrderSummary orderSummary = new OrderSummary();
                    orderSummary.setId(orderSummaryObject.optString("id"));
                    orderSummary.setActual_amount(orderSummaryObject.optString("actual_amount"));
                    orderSummary.setTax_amount(orderSummaryObject.optString("tax_amount"));
                    orderSummary.setTotal_amount(orderSummaryObject.optString("total_amount"));
                    orderSummary.setInstrument_name(orderSummaryObject.optString("instrument_name"));
                    orderSummary.setTerm_name(orderSummaryObject.optString("term_name"));
                    orderSummary.setLesson_name(orderSummaryObject.optString("lesson_name"));
                    orderSummaries.add(orderSummary);
                }
                model.setOrder_summery(orderSummaries);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return model;
    }*/
    fun showSocialmediaList(mStudentArray: ArrayList<StudentList>?) {
        val dialog = Dialog(mContext!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_student_media_list)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val dialogDismiss = dialog.findViewById<View>(R.id.btn_dismiss) as Button
        val iconImageView = dialog.findViewById<View>(R.id.iconImageView) as ImageView
        iconImageView.setImageResource(R.drawable.boy)
        val socialMediaList =
            dialog.findViewById<View>(R.id.recycler_view_social_media) as RecyclerView
        //if(mSocialMediaArray.get())
        val sdk = Build.VERSION.SDK_INT
        if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
            dialogDismiss.setBackgroundDrawable(mContext!!.resources.getDrawable(R.drawable.button))
        } else {
            dialogDismiss.background = mContext!!.resources.getDrawable(R.drawable.button)
        }
        socialMediaList.addItemDecoration(DividerItemDecoration(mContext!!.resources.getDrawable(R.drawable.list_divider_teal)))
        socialMediaList.setHasFixedSize(true)
        val llm = LinearLayoutManager(mContext)
        llm.orientation = LinearLayoutManager.VERTICAL
        socialMediaList.layoutManager = llm
        val studentAdapter = StudentListAdapter(mContext!!, mStudentArray!!)
        socialMediaList.adapter = studentAdapter
        dialogDismiss.setOnClickListener { dialog.dismiss() }
        socialMediaList.addOnItemTouchListener(
            RecyclerItemListener(mContext, socialMediaList,
                object : RecyclerTouchListener {
                    override fun onClickItem(v: View?, position: Int) {
                        dialog.dismiss()
                        studentName!!.text = mStudentArray[position].name
                        studentIdStr = mStudentArray[position].id
                        studentClassStr = mStudentArray[position].studentClass
                        stud_img = mStudentArray[position].photo
                        if (stud_img != "") {

                            //   Picasso.with(mContext).load(AppUtils.replace(stud_img)).placeholder(R.drawable.boy).fit().into(studImg);
                        } else {
                            studImg!!.setImageResource(R.drawable.boy)
                        }
                        PreferenceManager.setStudentID(mContext, studentIdStr)
                        PreferenceManager.setStudentName(mContext, mStudentArray[position].name)
                        /* if (AppUtils.isNetworkConnected(mContext)) {
                            // TODO INVOICE LIST
                            getPaymentHistory(PreferenceManager.getStudentID(mContext));
                            //  getEventsListApi(PreferenceManager.getStudentID(mContext));
                        } else {
                          //  AppUtils.showDialogAlertDismiss((Activity) mContext, "Network Error", getString(R.string.no_internet), R.drawable.nonetworkicon, R.drawable.roundred);

                        }*/
                    }

                    override fun onLongClickItem(v: View?, position: Int) {}
                })
        )
        dialog.show()
    }
    override fun onResume() {
        super.onResume()
        if (!ConstantFunctions.runMethod.equals("Dev")) {
            if (ConstantFunctions().isDeveloperModeEnabled(mContext)) {
                ConstantFunctions().showDeviceIsDeveloperPopUp(activity)
            }
        }
    }
}