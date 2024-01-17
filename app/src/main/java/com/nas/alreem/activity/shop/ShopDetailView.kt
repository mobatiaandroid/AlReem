package com.nas.alreem.activity.shop

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonObject
import com.nas.alreem.R
import com.nas.alreem.activity.home.HomeActivity
import com.nas.alreem.activity.shop.Adapter.TermsAdapter
import com.nas.alreem.activity.shop.model.Instrument
import com.nas.alreem.activity.shop.model.Lesson
import com.nas.alreem.activity.shop.model.Term
import com.nas.alreem.activity.shop.model.TermsSubmitModel
import com.nas.alreem.recyclermanager.ItemOffsetDecoration
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ShopDetailView : AppCompatActivity() {
   // var headermanager: HeaderManager? = null
    var relativeHeader: RelativeLayout? = null
    var back: ImageView? = null
    var studImg: ImageView? = null
    var stud_img = ""
    var home: ImageView? = null
    var tab_type = "Registration"
    var text_content: TextView? = null
    var text_dialog: TextView? = null
    var instrumentsList: ArrayList<Instrument>? = null
    var extras: Bundle? = null
    var recyclerViewLayoutManager: GridLayoutManager? = null
    var termsAdapter: TermsAdapter? = null
    var isBeginner = ""
    var payButton: ConstraintLayout? = null
    var totalAmount: TextView? = null
    var totalValue = 0.0
    lateinit var instrumentSelectedObject: Instrument
    lateinit var radioGroupBeginnerOrNot: RadioGroup
    lateinit var radioYes: RadioButton
    lateinit var radioNo: RadioButton
    lateinit var termRecycler: RecyclerView
    var titleTextView: TextView? = null
    var relMain: RelativeLayout? = null
    var closeImage: ImageView? = null
    var emailHelpImg: ImageView? = null
    lateinit var submitButton: Button
    lateinit var context: Context
  //  var progressBarDialog: ProgressBarDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_items_detail_view)
        context = this
        extras = intent.extras
        if (extras != null) {
            instrumentSelectedObject = (extras!!["instrument"] as Instrument?)!!
            //            pos = extras.getInt("pos");
        }
        initialiseUI()
        //        if (AppUtils.isNetworkConnected(context)) {
//            getStudentsListAPI(URL_GET_STUDENT_LIST);
//        } else {
//            AppUtils.showDialogAlertDismiss((Activity) context, "Network Error", getString(R.string.no_internet), R.drawable.nonetworkicon, R.drawable.roundred);
//
//        }
    }

    private fun initialiseUI() {
        relativeHeader = findViewById<View>(R.id.relativeHeader) as RelativeLayout
        tab_type = instrumentSelectedObject.getInstrumentName() + " Classes"
    //    progressBarDialog = ProgressBarDialog(context, R.drawable.spinner)
     //   headermanager = HeaderManager(this@InstrumentDetailView, tab_type)
     //   headermanager.getHeader(relativeHeader, 0)
     //   back = headermanager.getLeftButton()
     /*   headermanager.setButtonLeftSelector(
            R.drawable.back,
            R.drawable.back
        )*/
        back!!.setOnClickListener {
           // AppUtils.hideKeyBoard(context)
            finish()
        }
      //  home = headermanager.getLogoButton()
        home!!.setOnClickListener {
            val `in` = Intent(
                context,
                HomeActivity::class.java
            )
            `in`.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(`in`)
        }
        if (instrumentSelectedObject.hasAtLeastOneOrderSuccessInAllTerms()) {
           /* AppUtils.showDialogAlertDismiss(
                context as Activity?,
                "Alert",
                "You have already registered for this instrument.",
                R.drawable.exclamationicon,
                R.drawable.round
            )*/
        }
        termRecycler = findViewById<RecyclerView>(R.id.termRecycler)
        radioNo = findViewById<RadioButton>(R.id.radioNo)
        radioYes = findViewById<RadioButton>(R.id.radioYes)
        radioGroupBeginnerOrNot = findViewById<RadioGroup>(R.id.radioGroup)
        //        titleTextView = findViewById(R.id.titleTextView);
        closeImage = findViewById<ImageView>(R.id.close_img)
        //        emailHelpImg = findViewById(R.id.emailHelpImg);
        submitButton = findViewById<Button>(R.id.submitButton)


//        titleTextView.setText(instrumentSelectedObject.getInstrumentName() + " Classes");
        termRecycler.setHasFixedSize(true)
        recyclerViewLayoutManager = GridLayoutManager(context, 1)
        val spacing = 5 // 50px
        val itemDecoration = ItemOffsetDecoration(context, spacing)
        termRecycler.addItemDecoration(itemDecoration)
        termRecycler.setLayoutManager(recyclerViewLayoutManager)
        termsAdapter = TermsAdapter(context, instrumentSelectedObject)
        termRecycler.setAdapter(termsAdapter)
        radioGroupBeginnerOrNot.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group: RadioGroup?, checkedId: Int ->
            // Find the selected radio button
            isBeginner = if (radioNo.isChecked()) {
                "1"
            } else if (radioYes.isChecked()) {
                "0"
            } else {
                ""
            }
        })
        submitButton.setOnClickListener(View.OnClickListener { v: View? ->
            if (instrumentSelectedObject.hasAtLeastOneOrderSuccessInAllTerms()) {
                showDialogAlertDismiss(
                    context as Activity?,
                    "Alert",
                    "You have already registered for this instrument.",
                    R.drawable.exclamationicon,
                    R.drawable.round
                )
            } else {
                if (isBeginner.equals("", ignoreCase = true)) {
                    /*AppUtils.showDialogAlertDismiss(
                        context as Activity?,
                        "Alert",
                        "Please select your child's learning level.",
                        R.drawable.exclamationicon,
                        R.drawable.round
                    )*/
                } else {
                   // val selectedLessons: ArrayList<Lesson> = selectedLessons
                    /*if (selectedLessons.isEmpty()) {
                       *//* AppUtils.showDialogAlertDismiss(
                            context as Activity?,
                            "Alert",
                            "Please select at-least one course before submission.",
                            R.drawable.exclamationicon,
                            R.drawable.round
                        )*//*
                    } *//*else {
                        if (selectedLessons.size > instrumentSelectedObject.getTermData().size()) {
                            *//*AppUtils.showDialogAlertDismiss(
                                context as Activity?,
                                "Alert",
                                "Please select only a single course for a term.",
                                R.drawable.exclamationicon,
                                R.drawable.round
                            )*//*
                        } else {
                            if (checkForDuplicates()) {
                                *//*AppUtils.showDialogAlertDismiss(
                                    context as Activity?,
                                    "Alert",
                                    "Please select only a single course for a term.",
                                    R.drawable.exclamationicon,
                                    R.drawable.round
                                )*//*
                            } else {
                                showConsentDialogPopUp(
                                    selectedLessons,
                                    instrumentSelectedObject.getInstrumentId()
                                )
                            }
                        }
                    }*/
                }
            }
        })


//        emailHelpImg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final Dialog dialog = new Dialog(context);
//                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                dialog.setContentView(R.layout.alert_send_email_dialog);
//                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                Button dialogCancelButton = (Button) dialog.findViewById(R.id.cancelButton);
//                Button submitButton = (Button) dialog.findViewById(R.id.submitButton);
//                text_dialog = (EditText) dialog.findViewById(R.id.text_dialog);
//                text_content = (EditText) dialog.findViewById(R.id.text_content);
//
//
//                dialogCancelButton.setOnClickListener(new View.OnClickListener() {
//
//                    @Override
//
//                    public void onClick(View v) {
//
//                        dialog.dismiss();
//
//                    }
//
//                });
//
//                submitButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//// TODO                       sendEmail
//                        dialog.dismiss();
//                    }
//                });
//
//
//                dialog.show();
//
//
//            }
//        });
    }

    /*private fun checkForDuplicates(): Boolean {
      //  val selectedLessonInfoArray = selectedLessonInfo

        // Create a Set to store encountered term_ids
        val encounteredTermIds: MutableSet<String?> = HashSet()
        var hasDuplicates = false
        for (i in 0 until selectedLessonInfoArray.length()) {
            var lessonInfo: JSONObject? = null
            lessonInfo = try {
                selectedLessonInfoArray.getJSONObject(i)
            } catch (e: JSONException) {
                throw RuntimeException(e)
            }
            var termId: String? = null
            termId = try {
                lessonInfo!!.getString("term_id")
            } catch (e: JSONException) {
                throw RuntimeException(e)
            }
            if (encounteredTermIds.contains(termId)) {
                hasDuplicates = true
                break // Exit loop early since duplicates were found
            } else {
                encounteredTermIds.add(termId)
            }
        }
        return hasDuplicates
    }*/

    private fun calculateDisabledCoursesCount(instrumentObject: Instrument): Int {
        var disabledTermCount = 0
        val termData: ArrayList<Term> = instrumentObject.getTermData()
        for (term in termData) {
            var isTermDisabled = true // Assume all lessons are disabled by default
            val lessonData: ArrayList<Lesson> = term.getLessonData()
            for (lesson in lessonData) {
                if (lesson.getOrderSuccess() !== 1) {
                    // If any lesson has orderSuccess != 1, the term is not disabled
                    isTermDisabled = false
                    break
                }
            }
            if (isTermDisabled) {
                disabledTermCount++
            }
        }
        return disabledTermCount
    }

   // private val selectedLessonInfo: JSONArray
        //    private ArrayList<TermsSubmitModel> getSelectedLessonInfo() {
      /*  private get() {
            val selectedLessonInfoArray = JSONArray()
            for (term in termsAdapter.getTerms()) {
                for (lesson in term.getLessonData()) {
                    if (lesson.isSelected()) {
                        val lessonInfo = JSONObject()
                        try {
                            lessonInfo.put("term_id", term.getTermId())
                        } catch (e: JSONException) {
                            throw RuntimeException(e)
                        }
                        try {
                            lessonInfo.put("lesson_id", lesson.getId())
                        } catch (e: JSONException) {
                            throw RuntimeException(e)
                        }
                        selectedLessonInfoArray.put(lessonInfo)
                    }
                }
            }
            return selectedLessonInfoArray
        }*/

    private fun showConsentDialogPopUp(selectedLessons: ArrayList<Lesson>, instrumentID: String) {
        val dialog = Dialog(context!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_shop_consent)
        val agreeButton1: Button
        val agreeButton2: Button
        agreeButton1 = dialog.findViewById<Button>(R.id.agreeButton1)
        agreeButton2 = dialog.findViewById<Button>(R.id.agreeButton2)
        val closeButton: ImageView
        closeButton = dialog.findViewById<ImageView>(R.id.closeButton)
        closeButton.setOnClickListener { dialog.dismiss() }
        agreeButton1.setOnClickListener {
            val selectedIDs: ArrayList<TermsSubmitModel> =
                ArrayList<TermsSubmitModel>()
            //                submitToCart(instrumentID, dialog, "0");
            submitToCartAPI(instrumentID, dialog, "0")
        }
        agreeButton2.setOnClickListener {
            val selectedIDs: ArrayList<TermsSubmitModel> =
                ArrayList<TermsSubmitModel>()
            submitToCartAPI(instrumentID, dialog, "1")
            //                submitToCartAPI( instrumentID, dialog, "1");
        }
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
    }

    private fun submitToCartAPI(instrumentID: String, dialog: Dialog, consent: String) {
        /*val selectedLessonInfoArray = selectedLessonInfo
        val service: APIInterface = APIClient.getRetrofitInstance().create(APIInterface::class.java)
        val paramObject = JsonObject()
        paramObject.addProperty("student_id", PreferenceManager.getStudentID(context))
        paramObject.addProperty("terms", selectedLessonInfoArray.toString())
        paramObject.addProperty("learning_level", isBeginner)
        paramObject.addProperty("instrument_id", instrumentID)
        paramObject.addProperty("instrument_consent", consent)
        val call: Call<MusicBaseResponseModel> = service.postMusicSubmitToCart(
            "Bearer " + PreferenceManager.getAccessToken(context),
            paramObject
        )
        progressBarDialog.show()
        call.enqueue(object : Callback<MusicBaseResponseModel?> {
            override fun onResponse(
                call: Call<MusicBaseResponseModel?>,
                response: Response<MusicBaseResponseModel?>
            ) {
                progressBarDialog.hide()
                if (response.body() != null) {
                    val responseCode = response.code().toString()
                    if (response.body().getResponseCode().equalsIgnoreCase("200")) {
                        if (response.body().getResponse().getStatusCode().equalsIgnoreCase("303")) {
                            isBeginner = ""
                            dialog.dismiss()
                            showRefreshPopUp(
                                context as Activity?,
                                "Alert",
                                "Submission Successful",
                                R.drawable.tick,
                                R.drawable.round
                            )
                        }
                    } else if (response.body().getResponseCode()
                            .equalsIgnoreCase(RESPONSE_ACCESSTOKEN_MISSING) ||
                        response.body().getResponseCode()
                            .equalsIgnoreCase(RESPONSE_ACCESSTOKEN_EXPIRED) ||
                        response.body().getResponseCode().equalsIgnoreCase(RESPONSE_INVALID_TOKEN)
                    ) {
                        AppUtils.postInitParam(context, object : GetAccessTokenInterface() {
                            val accessToken: Unit
                                get() {}
                        })
                        submitToCartAPI(instrumentID, dialog, consent)
                    } else if (response.body().getResponseCode().equals(RESPONSE_ERROR)) {
//								CustomStatusDialog(RESPONSE_FAILURE);
                        //Toast.makeText(mContext,"Failure", Toast.LENGTH_SHORT).show();
                        AppUtils.showDialogAlertDismiss(
                            context as Activity?,
                            "Alert",
                            getString(R.string.common_error),
                            R.drawable.exclamationicon,
                            R.drawable.round
                        )
                    } else {
                        AppUtils.showDialogAlertDismiss(
                            context as Activity?,
                            "Alert",
                            getString(R.string.common_error),
                            R.drawable.exclamationicon,
                            R.drawable.round
                        )
                    }
                }
            }

            override fun onFailure(call: Call<MusicBaseResponseModel?>, t: Throwable) {
                progressBarDialog.hide()
                AppUtils.showNotifyAlertDismiss(
                    context as Activity?,
                    "Alert",
                    "Submission Failed",
                    R.drawable.remove,
                    R.drawable.round
                )
            }
        }
        )*/
    }

   /* private val selectedLessons: ArrayList<Any>
        private get() {
            val selectedLessons: ArrayList<Lesson> = ArrayList<Lesson>()
            for (term in termsAdapter.getTerms()) {
                for (lesson in term.getLessonData()) {
                    if (lesson.isSelected()) {
                        selectedLessons.add(lesson)
                    }
                }
            }
            return selectedLessons
        }*/

    private fun showRefreshPopUp(
        activity: Activity?,
        msgHead: String,
        msg: String,
        ico: Int,
        bgIcon: Int
    ) {
        val dialog = Dialog(activity!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_common_error_alert)
        val icon = dialog.findViewById<View>(R.id.iconImageView) as ImageView
        icon.setBackgroundResource(bgIcon)
        icon.setImageResource(ico)
        val text = dialog.findViewById<View>(R.id.text_dialog) as TextView
        val textHead = dialog.findViewById<View>(R.id.alertHead) as TextView
        text.text = msg
        textHead.text = msgHead
        val dialogButton = dialog.findViewById<View>(R.id.btn_Ok) as Button
        dialogButton.setOnClickListener {
            val intent = Intent(
                context,
                ShopRegisterActivity::class.java
            )
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
        //		Button dialogButtonCancel = (Button) dialog.findViewById(R.id.btn_Cancel);
//		dialogButtonCancel.setVisibility(View.GONE);
//		dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				dialog.dismiss();
//			}
//		});
        dialog.show()
    }

    companion object {
        fun showDialogAlertDismiss(
            activity: Activity?,
            msgHead: String?,
            msg: String?,
            ico: Int,
            bgIcon: Int
        ) {
            val dialog = Dialog(activity!!)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.dialog_common_error_alert)
            val icon = dialog.findViewById<View>(R.id.iconImageView) as ImageView
            icon.setBackgroundResource(bgIcon)
            icon.setImageResource(ico)
            val text = dialog.findViewById<View>(R.id.text_dialog) as TextView
            val textHead = dialog.findViewById<View>(R.id.alertHead) as TextView
            text.text = msg
            textHead.text = msgHead
            val dialogButton = dialog.findViewById<Button>(R.id.btn_Ok)
            dialogButton.setOnClickListener { v: View? ->
                dialog.dismiss()
                val `in` = Intent(
                    activity,
                    ShopRegisterActivity::class.java
                )
                `in`.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                activity.startActivity(`in`)
                activity.finish()
            }
            dialog.show()
        }
    }
}