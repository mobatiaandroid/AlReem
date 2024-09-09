package com.nas.alreem.activity.cca

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import android.view.Window
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nas.alreem.R
import com.nas.alreem.activity.cca.adapter.CCAsActivityAdapter
import com.nas.alreem.activity.cca.adapter.CCAsWeekListAdapter
import com.nas.alreem.activity.cca.model.CCACancelModel
import com.nas.alreem.activity.cca.model.CCADetailModel
import com.nas.alreem.activity.cca.model.CCASubmitResponseModel
import com.nas.alreem.activity.cca.model.WeekListModel
import com.nas.alreem.activity.home.HomeActivity
import com.nas.alreem.activity.notifications.player
import com.nas.alreem.appcontroller.AppController
import com.nas.alreem.constants.*
import com.nas.alreem.recyclermanager.ItemOffsetDecoration
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CCASelectionActivity : AppCompatActivity() {
    lateinit var mContext: Context
    lateinit var titleTextView: TextView
    lateinit var back: ImageView
    lateinit var backRelative: RelativeLayout
    lateinit var logoclick: ImageView
    lateinit var progress: ProgressBar

    var CCADetailModelArrayList: ArrayList<CCADetailModel>? = null

    //    ArrayList<String> weekList;
    var relativeHeader: RelativeLayout? = null
    lateinit var msgRelative: RelativeLayout

    var tab_type = "ECAs"
    var extras: Bundle? = null

    //    ArrayList<String> mCcaArrayList;
    var recycler_review: RecyclerView? = null
    var weekRecyclerList: RecyclerView? = null
    var recyclerViewLayoutManager: GridLayoutManager? = null
    var recyclerweekViewLayoutManager: GridLayoutManager? = null
    var pos = 0
    var ccaDetailpos = 0
    var submitBtn: Button? = null
    var nextBtn: Button? = null
    var filled = false
    var weekSelected = false
    var weekPosition = 0
    var flag = 0
    var ccaedit = 0
    var keyy=""
    var mCCAsWeekListAdapter: CCAsWeekListAdapter? = null
    var TVselectedForWeek: TextView? = null
    var textViewCCAaSelect: TextView? = null
    var textViewStudName: TextView? = null
    var messageTxt: TextView? = null
    var mCCAsActivityAdapter: CCAsActivityAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_ccaselection)
        mContext = this
        initialiseUI()

    }

    private fun initialiseUI() {
        titleTextView = findViewById(R.id.heading)
        back = findViewById(R.id.btn_left)
        backRelative = findViewById(R.id.backRelative)
        logoclick = findViewById(R.id.logoClickImgView)
        progress = findViewById(R.id.progress)
        extras = intent.extras
        logoclick.setOnClickListener {

            if(AppController.keyy.equals("1"))
            {
                if(ccaedit==0)
                {
                    showApiLogoAlert(mContext,"Leaving this page will cancel all reserved activities for this schedule. Do you want to continue?","Confirm")

                }
                else
                {
                    val mIntent = Intent(mContext, HomeActivity::class.java)
                    mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(mIntent)
                }
            }
            else
            {
                val mIntent = Intent(mContext, HomeActivity::class.java)
                mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(mIntent)
            }

        }
        backRelative.setOnClickListener {
            if(ccaedit==0)
            {
                Log.e("keyvalue", AppController.keyy!!)
                if(AppController.keyy.equals("1"))
                {
                    showApiAlert(mContext,"Leaving this page will cancel all reserved activities for this schedule. Do you want to continue?","Confirm")

                }
                else
                {
                    val mIntent = Intent(mContext, CCA_Activity_New::class.java)
                    mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

                    startActivity(mIntent)
                }
            }
            else
            {
                finish()
            }


        }
        if (extras != null) {
            tab_type = extras!!.getString("tab_type")!!
            ccaedit = extras!!.getInt("ccaedit", 0)
            // keyy=extras!!.getString("keyvalue")!!
            //            pos = extras.getInt("pos");
            CCADetailModelArrayList =
                PreferenceManager.getDetailsArrayList(mContext)
        }


        AppController.weekList = ArrayList()
        AppController.weekListWithData = ArrayList()
//        weekList.add("Sunday");
//        weekList.add("Monday");
//        weekList.add("Tuesday");
//        weekList.add("Wednesday");
//        weekList.add("Thursday");
//        weekList.add("Friday");
//        weekList.add("Saturday");
        //        weekList.add("Sunday");
//        weekList.add("Monday");
//        weekList.add("Tuesday");
//        weekList.add("Wednesday");
//        weekList.add("Thursday");
//        weekList.add("Friday");
//        weekList.add("Saturday");
        for (i in 0..6) {
            val mWeekListModel = WeekListModel()
            mWeekListModel.weekDay=(getWeekday(i))
            mWeekListModel.weekDayMMM=(getWeekdayMMM(i))
            if (ccaedit == 0) {
                mWeekListModel.choiceStatus=("0")
                // mWeekListModel.choiceStatus1=("0")
            } else {
                mWeekListModel.choiceStatus=("1")
                //mWeekListModel.choiceStatus1=("1")
            }
            AppController.weekList!!.add(mWeekListModel)
        }
        relativeHeader = findViewById<View>(R.id.relativeHeader) as RelativeLayout
        msgRelative = findViewById<View>(R.id.msgRelative) as RelativeLayout
        recycler_review = findViewById<View>(R.id.recycler_view_cca) as RecyclerView
        weekRecyclerList = findViewById<View>(R.id.weekRecyclerList) as RecyclerView
        TVselectedForWeek = findViewById<View>(R.id.TVselectedForWeek) as TextView
        textViewCCAaSelect = findViewById<View>(R.id.textViewCCAaSelect) as TextView
        textViewStudName = findViewById<View>(R.id.textViewStudName) as TextView
        messageTxt = findViewById<View>(R.id.messageTxt) as TextView
        submitBtn = findViewById<View>(R.id.submitBtn) as Button
        nextBtn = findViewById<View>(R.id.nextBtn) as Button
        nextBtn!!.getBackground().setAlpha(255)
        submitBtn!!.getBackground().setAlpha(150)

        val startAnimation = AnimationUtils.loadAnimation(
            applicationContext, R.anim.blinking_animation
        )
        messageTxt!!.startAnimation(startAnimation)
        if (PreferenceManager.getStudClassForCCA(mContext).equals("")) {
            // textViewStudName!!.setText(PreferenceManager.getStudNameForCCA(mContext))

            textViewStudName!!.text = Html.fromHtml(PreferenceManager.getStudNameForCCA(
                mContext)+"<br/>Year Group : " + PreferenceManager.getStudClassForCCA( mContext))
        } else {
            textViewStudName!!.text = Html.fromHtml(
                PreferenceManager.getStudNameForCCA(mContext)
                    .toString() + "<br/>Year Group : " + PreferenceManager.getStudClassForCCA(
                    mContext
                )
            )
        }
        if (ccaedit == 0) {
            ConstantFunctions.showDialogueWithOk(
                mContext,
                "Please select a Enrichment choice foreach day",
                "Info"
            )

            submitBtn!!.getBackground().setAlpha(150)
            submitBtn!!.setVisibility(View.INVISIBLE)
            AppController.filledFlag = 1
        } else {
            submitBtn!!.getBackground().setAlpha(255)
            submitBtn!!.setVisibility(View.VISIBLE)
            nextBtn!!.getBackground().setAlpha(255)
            nextBtn!!.setVisibility(View.GONE)
            AppController.filledFlag = 1
        }

        submitBtn!!.setOnClickListener(View.OnClickListener { //              for (int i=0;i<CCADetailModelArrayList.size();i++)
            //              {
            //                  System.out.println("Choice1 "+CCADetailModelArrayList.get(i).getDay()+":"+CCADetailModelArrayList.get(i).getChoice1());
            //                  System.out.println("Choice2 "+CCADetailModelArrayList.get(i).getDay()+":"+CCADetailModelArrayList.get(i).getChoice2());
            //              }
//            if (flag == 1) {
//                filled = true
//            }

            if(AppController.filledFlag == 1){
//            if (filled) {
                val mInent = Intent(mContext, CCAsReviewActivity::class.java)
                intent.putExtra("ccaedit",ccaedit)
                //intent.putExtra("keyvalue", keyy)

                //Log.e("ccaedit", keyy)

                AppController.CCADetailModelArrayList.clear()
                for (i in CCADetailModelArrayList!!.indices){
                    AppController.CCADetailModelArrayList.add(CCADetailModelArrayList!![i])
                }
                //  intent.putExtra("ccaedit", ccaedit)
                intent.putExtra("detail_array", CCADetailModelArrayList)
                startActivity(mInent)
            } else {
                ConstantFunctions.showDialogueWithOk(mContext,"Select choice for all available days","Alert")

            }
        })


        recycler_review!!.setHasFixedSize(true)
        recyclerViewLayoutManager = GridLayoutManager(mContext, 1)
        val spacing = 5 // 50px

        val itemDecoration = ItemOffsetDecoration(mContext, spacing)
        recycler_review!!.addItemDecoration(itemDecoration)
        recycler_review!!.layoutManager = recyclerViewLayoutManager
//        for (int i = 0; i < CCADetailModelArrayList.size(); i++)
//            if (CCADetailModelArrayList.get(i).getDay().equalsIgnoreCase("Sunday")) {
//                {
//                    ccaDetailpos=i;
//                    CCAsActivityAdapter mCCAsActivityAdapter = new CCAsActivityAdapter(mContext, CCADetailModelArrayList.get(i).getCcaChoiceModel(), CCADetailModelArrayList.get(i).getCcaChoiceModel2(),0,AppController.weekList);
//                    recycler_review.setAdapter(mCCAsActivityAdapter);
//                    break;
//                }
//            }

        //        for (int i = 0; i < CCADetailModelArrayList.size(); i++)
//            if (CCADetailModelArrayList.get(i).getDay().equalsIgnoreCase("Sunday")) {
//                {
//                    ccaDetailpos=i;
//                    CCAsActivityAdapter mCCAsActivityAdapter = new CCAsActivityAdapter(mContext, CCADetailModelArrayList.get(i).getCcaChoiceModel(), CCADetailModelArrayList.get(i).getCcaChoiceModel2(),0,AppController.weekList);
//                    recycler_review.setAdapter(mCCAsActivityAdapter);
//                    break;
//                }
//            }
        TVselectedForWeek!!.text = "Sunday"
//        for (int j = 0; j < AppController.weekList.size(); j++) {
//            for (int i = 0; i < CCADetailModelArrayList.size(); i++) {
//                if (!AppController.weekList.get(j).getWeekDay().equalsIgnoreCase(CCADetailModelArrayList.get(i).getDay())) {
//                    AppController.weekList.get(j).setChoiceStatus("2");
//                    AppController.weekList.get(j).setChoiceStatus1("2");
//                }
//                else
//                {
//                    AppController.weekList.get(j).setChoiceStatus("0");
//                    AppController.weekList.get(j).setChoiceStatus1("0");
//                }
//            }
//        }

        //        for (int j = 0; j < AppController.weekList.size(); j++) {
//            for (int i = 0; i < CCADetailModelArrayList.size(); i++) {
//                if (!AppController.weekList.get(j).getWeekDay().equalsIgnoreCase(CCADetailModelArrayList.get(i).getDay())) {
//                    AppController.weekList.get(j).setChoiceStatus("2");
//                    AppController.weekList.get(j).setChoiceStatus1("2");
//                }
//                else
//                {
//                    AppController.weekList.get(j).setChoiceStatus("0");
//                    AppController.weekList.get(j).setChoiceStatus1("0");
//                }
//            }
//        }
        for (i in 0 until AppController.weekList!!.size) {
            AppController.weekList!!.get(i).choiceStatus=("2")
            // AppController.weekList!!.get(i).choiceStatus1=("2")
            AppController.weekList!!.get(i).dataInWeek=("0")
        }


        for (i in 0 until AppController.weekList!!.size) {
            Log.e("arraysize", CCADetailModelArrayList!!.size.toString())

            for (j in CCADetailModelArrayList!!.indices) {
                Log.e("weekarray", AppController.weekList!!.get(i).weekDay!!)
                Log.e("ccadetailsarray", CCADetailModelArrayList!!.get(j).day!!)

                if (AppController.weekList!!.get(i).weekDay.equals(
                        CCADetailModelArrayList!!.get(j).day)
                ) {
                    if (ccaedit == 0) {
                        AppController.weekList!!.get(i).choiceStatus=("0")
                        //  AppController.weekList!!.get(i).choiceStatus1=("0")
                    } else {
                        AppController.weekList!!.get(i).choiceStatus=("1")
                        // AppController.weekList!!.get(i).choiceStatus1=("1")
                    }
                    AppController.weekList!!.get(i).dataInWeek=("1")
                    AppController.weekListWithData!!.add(i)
                }
            }
        }
        for (i in this.CCADetailModelArrayList!!.indices) {
            if (CCADetailModelArrayList!!.get(i).day.equals("Sunday")) {
                ccaDetailpos = i
                textViewCCAaSelect!!.visibility = View.VISIBLE
                TVselectedForWeek!!.visibility = View.VISIBLE
                mCCAsActivityAdapter = CCAsActivityAdapter(
                    mContext,
                    CCADetailModelArrayList!!.get(i).ccaChoiceModel,
                    CCADetailModelArrayList!!.get(i).ccaChoiceModel2,
                    0,
                    AppController.weekList,
                    weekRecyclerList,ccaedit,CCADetailModelArrayList,nextBtn, submitBtn, filled,ccaDetailpos,msgRelative
                )
                recycler_review!!.adapter = mCCAsActivityAdapter
                break
            } else if (i == CCADetailModelArrayList!!!!.size - 1) {
                if (!CCADetailModelArrayList!!.get(i).day
                        .equals("Sunday",ignoreCase = true)
                ) {
                    mCCAsActivityAdapter = CCAsActivityAdapter(mContext, 0)
                    recycler_review!!.adapter = mCCAsActivityAdapter
                    textViewCCAaSelect!!.visibility = View.GONE
                    TVselectedForWeek!!.visibility = View.GONE
                    AppController.weekList!!.get(0).choiceStatus=("2")
                    //   AppController.weekList!!.get(0).choiceStatus1=("2")
                    //                    Toast.makeText(mContext, "ECA choice not available.", Toast.LENGTH_SHORT).show();
                }
            }
        }


//        CCAsActivityAdapter mCCAsActivityAdapter = new CCAsActivityAdapter(mContext, CCADetailModelArrayList.get(0).getCcaChoiceModel(), CCADetailModelArrayList.get(0).getCcaChoiceModel2());
//        recycler_review.setAdapter(mCCAsActivityAdapter);


//        CCAsActivityAdapter mCCAsActivityAdapter = new CCAsActivityAdapter(mContext, CCADetailModelArrayList.get(0).getCcaChoiceModel(), CCADetailModelArrayList.get(0).getCcaChoiceModel2());
//        recycler_review.setAdapter(mCCAsActivityAdapter);
        weekRecyclerList!!.setHasFixedSize(true)
//        recyclerweekViewLayoutManager = new GridLayoutManager(mContext, 7);
        //        recyclerweekViewLayoutManager = new GridLayoutManager(mContext, 7);
        val llm = LinearLayoutManager(mContext)
        llm.orientation = LinearLayoutManager.HORIZONTAL
//        weekRecyclerList.addItemDecoration(
//                new DividerItemDecoration(mContext.getResources().getDrawable(R.drawable.list_divider)));
//        weekRecyclerList.addItemDecoration(itemDecoration);
        //        weekRecyclerList.addItemDecoration(
//                new DividerItemDecoration(mContext.getResources().getDrawable(R.drawable.list_divider)));
//        weekRecyclerList.addItemDecoration(itemDecoration);
        weekRecyclerList!!.layoutManager = llm
//        weekRecyclerList.setLayoutManager(recyclerweekViewLayoutManager);
        //        weekRecyclerList.setLayoutManager(recyclerweekViewLayoutManager);
        mCCAsWeekListAdapter = CCAsWeekListAdapter(mContext, AppController.weekList, weekPosition, msgRelative)
        weekRecyclerList!!.adapter = mCCAsWeekListAdapter
        weekRecyclerList!!.addOnItemClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                for (i in CCADetailModelArrayList!!.indices) {
                    if (AppController.weekList!!.get(position).weekDay.equals(
                            CCADetailModelArrayList!!.get(i).day)) {
                        pos = i
                        ccaDetailpos = i
                        weekSelected = true
                        break
                    } else {
                        weekSelected = false
                    }
                    if (weekSelected) {
                        break
                    }
                }
                if (!weekSelected) {
                    textViewCCAaSelect!!.visibility = View.GONE
                    TVselectedForWeek!!.visibility = View.GONE
                    msgRelative!!.setVisibility(View.GONE)
                    val mCCAsActivityAdapter = CCAsActivityAdapter(mContext, 0)
                    recycler_review!!.adapter = mCCAsActivityAdapter
                    mCCAsActivityAdapter.notifyDataSetChanged()
                    AppController.weekList!!.get(position).choiceStatus=("2")
                    //AppController.weekList!!.get(position).choiceStatus1=("2")
                    Toast.makeText(mContext, "EAP choice not available", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    textViewCCAaSelect!!.visibility = View.VISIBLE
                    TVselectedForWeek!!.visibility = View.VISIBLE
                    msgRelative!!.setVisibility(View.VISIBLE)
                    val mCCAsActivityAdapter = CCAsActivityAdapter(
                        mContext,
                        CCADetailModelArrayList!!.get(pos).ccaChoiceModel,
                        CCADetailModelArrayList!!.get(pos).ccaChoiceModel2,
                        position,
                        AppController.weekList,
                        weekRecyclerList,ccaedit,CCADetailModelArrayList,nextBtn, submitBtn, filled,ccaDetailpos,msgRelative
                    )
                    recycler_review!!.adapter = mCCAsActivityAdapter
                    mCCAsActivityAdapter.notifyDataSetChanged()
                }
                for (j in 0 until AppController.weekList!!.size) {
                    if (AppController.weekList!!.get(j).choiceStatus
                            .equals("0")
                    ) {
                        filled = false
                        break
                    } else {
                        filled = true
                    }
                    if (!filled) {
                        break
                    }
                }
                if (filled) {
                    submitBtn!!.background.alpha = 255
                    submitBtn!!.setVisibility(View.VISIBLE)
                    nextBtn!!.getBackground().setAlpha(255)
                    nextBtn!!.setVisibility(View.GONE)
                    AppController.filledFlag = 1
                } else {
                    submitBtn!!.getBackground().setAlpha(150)
                    submitBtn!!.setVisibility(View.INVISIBLE)
                    nextBtn!!.getBackground().setAlpha(255)
                    nextBtn!!.setVisibility(View.VISIBLE)
                }
                weekPosition = position
                mCCAsWeekListAdapter =
                    CCAsWeekListAdapter(mContext, AppController.weekList, weekPosition,msgRelative)
                weekRecyclerList!!.adapter = mCCAsWeekListAdapter
                TVselectedForWeek!!.setText(AppController.weekList!!.get(position).weekDay)
                //                        horizontalScrollView
                if (weekPosition == 6) weekRecyclerList!!.scrollToPosition(6) else weekRecyclerList!!.scrollToPosition(
                    0
                )
            }

        })


        for (j in 0 until AppController.weekList!!.size) {
            if (AppController.weekList!!.get(j).dataInWeek.equals("1")) {
                for (i in CCADetailModelArrayList!!.indices) {
                    if (AppController.weekList!!.get(j).weekDay.equals(
                            CCADetailModelArrayList!!.get(i).day
                        )
                    ) {
                        pos = i
                        ccaDetailpos = i
                        weekSelected = true
                        break
                    } else {
                        weekSelected = false
                    }
                    if (weekSelected) {
                        break
                    }
                }
                if (!weekSelected) {
                    textViewCCAaSelect!!.visibility = View.GONE
                    TVselectedForWeek!!.visibility = View.GONE
                    msgRelative!!.setVisibility(View.VISIBLE)
                    val mCCAsActivityAdapter = CCAsActivityAdapter(mContext, 0)
                    recycler_review!!.adapter = mCCAsActivityAdapter
                    mCCAsActivityAdapter.notifyDataSetChanged()
                    AppController.weekList!!.get(j).choiceStatus=("2")
                    //  AppController.weekList!!.get(j).choiceStatus1=("2")
                    Toast.makeText(mContext, "EAP choice not available", Toast.LENGTH_SHORT).show()
                } else {
                    textViewCCAaSelect!!.visibility = View.VISIBLE
                    TVselectedForWeek!!.visibility = View.VISIBLE
                    msgRelative!!.setVisibility(View.VISIBLE)
                    val mCCAsActivityAdapter = CCAsActivityAdapter(
                        mContext,
                        CCADetailModelArrayList!!.get(pos).ccaChoiceModel,
                        CCADetailModelArrayList!!.get(pos).ccaChoiceModel2,
                        j,
                        AppController.weekList,
                        weekRecyclerList,ccaedit,CCADetailModelArrayList,nextBtn, submitBtn, filled,ccaDetailpos,msgRelative
                    )
                    recycler_review!!.adapter = mCCAsActivityAdapter
                    mCCAsActivityAdapter.notifyDataSetChanged()
                }
                for (k in 0 until AppController.weekList!!.size) {
                    if (AppController.weekList!!.get(k).choiceStatus
                            .equals("0")
                    ) {
                        filled = false
                        msgRelative!!.setVisibility(View.VISIBLE)
                        break
                    } else {
                        filled = true
                    }
                    if (!filled) {
                        break
                    }
                }
                if (filled) {
                    submitBtn!!.getBackground().setAlpha(255)
                    submitBtn!!.setVisibility(View.VISIBLE)
                    nextBtn!!.getBackground().setAlpha(255)
                    nextBtn!!.setVisibility(View.GONE)
                    msgRelative!!.setVisibility(View.GONE)
                    AppController.filledFlag = 1
//                    msgRelative.setVisibility(View.GONE);
                } else {
                    msgRelative!!.setVisibility(View.VISIBLE)
                    submitBtn!!.getBackground().setAlpha(150)
                    submitBtn!!.setVisibility(View.INVISIBLE)
                    nextBtn!!.getBackground().setAlpha(255)
                    nextBtn!!.setVisibility(View.VISIBLE)
                }
                weekPosition = j
                mCCAsWeekListAdapter =
                    CCAsWeekListAdapter(mContext, AppController.weekList, weekPosition, msgRelative)
                weekRecyclerList!!.adapter = mCCAsWeekListAdapter
                TVselectedForWeek!!.setText(AppController.weekList!!.get(j).weekDay)
                break
            }
        }

        if (AppController.weekListWithData!!.size > 0) {
            nextBtn!!.getBackground().setAlpha(255)
            nextBtn!!.setVisibility(View.VISIBLE)
        } else {
            nextBtn!!.getBackground().setAlpha(255)
            nextBtn!!.setVisibility(View.GONE)
        }

        nextBtn!!.setOnClickListener(View.OnClickListener {
            weekPosition = weekPosition + 1
            if (AppController.weekListWithData!!.contains(weekPosition)) {
                for (a in 0 until AppController.weekListWithData!!.size) {
                    if (AppController.weekListWithData!!.get(a) === weekPosition) {
                        //weekPosition = a;
                        weekPosition = AppController.weekListWithData!!.get(a)
                        break
                    }
                }

                /*           for (int a=0;a<AppController.weekListWithData.size();a++)
                        {
                            if (weekPosition==AppController.weekListWithData.get(a)) {
                                weekPosition = AppController.weekListWithData.get(a);
                            }
                        }
                        weekPosition = AppController.weekListWithData.get(weekPosition);*/
            } else {
                if (weekPosition >= AppController.weekList!!.size - 1) {
                    weekPosition = 0
                }
                if (AppController.weekListWithData!!.contains(weekPosition)) {
                    //                        weekPosition = AppController.weekListWithData.get(weekPosition);
                    for (a in 0 until AppController.weekListWithData!!.size) {
                        //                            if (AppController.weekListWithData.contains(weekPosition)) {
                        if (AppController.weekListWithData!!.get(a) === weekPosition) {
                            //                                weekPosition = a;
                            weekPosition = AppController.weekListWithData!!.get(a)
                            break
                        }
                    }
                } else {
                    for (m in weekPosition until AppController.weekList!!.size) {
                        if (AppController.weekListWithData!!.contains(m)) {
                            weekPosition = m
                            println("weekposition:m:$weekPosition")
                            break
                        }
                    }
                    if (!AppController.weekListWithData!!.contains(weekPosition)) {
                        weekPosition = 0
                    }
                }
            }
            for (j in weekPosition until AppController.weekList!!.size) {
                if (AppController.weekList!!.get(j).dataInWeek.equals("1")) {
                    for (i in CCADetailModelArrayList!!.indices) {
                        if (AppController.weekList!!.get(j).weekDay.equals(
                                CCADetailModelArrayList!!.get(i).day,ignoreCase = true
                            )
                        ) {
                            pos = i
                            ccaDetailpos = i
                            weekSelected = true
                            break
                        } else {
                            weekSelected = false
                        }
                        if (weekSelected) {
                            break
                        }
                    }
                    if (!weekSelected) {
                        textViewCCAaSelect!!.visibility = View.GONE
                        TVselectedForWeek!!.visibility = View.GONE
                        val mCCAsActivityAdapter = CCAsActivityAdapter(mContext, 0)
                        recycler_review!!.adapter = mCCAsActivityAdapter
                        mCCAsActivityAdapter.notifyDataSetChanged()
                        AppController.weekList!!.get(j).choiceStatus = "2"
                        //  AppController.weekList!!.get(j).choiceStatus1 = "2"
                        //                            Toast.makeText(mContext, "ECA choice not available.", Toast.LENGTH_SHORT).show();
                    } else {
                        textViewCCAaSelect!!.visibility = View.VISIBLE
                        TVselectedForWeek!!.visibility = View.VISIBLE
                        val mCCAsActivityAdapter = CCAsActivityAdapter(
                            mContext,
                            CCADetailModelArrayList!!.get(pos).ccaChoiceModel,
                            CCADetailModelArrayList!!.get(pos).ccaChoiceModel2,
                            j,
                            AppController.weekList,
                            weekRecyclerList,ccaedit,CCADetailModelArrayList,nextBtn, submitBtn, filled,ccaDetailpos,msgRelative
                        )
                        recycler_review!!.adapter = mCCAsActivityAdapter
                        mCCAsActivityAdapter.notifyDataSetChanged()
                    }
                    for (k in 0 until AppController.weekList!!.size) {
                        if (AppController.weekList!!.get(k).choiceStatus
                                .equals("0")
                        ) {
                            filled = false
                            msgRelative!!.setVisibility(View.VISIBLE)
                            break
                        } else {
                            filled = true
                        }
                        if (!filled) {
                            break
                        }
                    }
                    if (filled) {
                        submitBtn!!.getBackground().setAlpha(255)
                        submitBtn!!.setVisibility(View.VISIBLE)
                        nextBtn!!.getBackground().setAlpha(255)
                        nextBtn!!.setVisibility(View.GONE)
                        msgRelative!!.setVisibility(View.GONE)
                        nextBtn!!.setVisibility(View.GONE)
                        AppController.filledFlag = 1
                    } else {
                        msgRelative!!.setVisibility(View.VISIBLE)
                        submitBtn!!.getBackground().setAlpha(150)
                        submitBtn!!.setVisibility(View.INVISIBLE)
                        nextBtn!!.getBackground().setAlpha(255)
                        nextBtn!!.setVisibility(View.VISIBLE)
                    }
                    weekPosition = j
                    mCCAsWeekListAdapter =
                        CCAsWeekListAdapter(mContext, AppController.weekList, weekPosition,msgRelative)
                    weekRecyclerList!!.adapter = mCCAsWeekListAdapter
                    TVselectedForWeek!!.text = AppController.weekList!![j].weekDay
                    break
                }
            }
            if (weekPosition == 6) {
                weekRecyclerList!!.layoutManager!!.scrollToPosition(weekPosition)
            } else {
                weekRecyclerList!!.layoutManager!!.scrollToPosition(0)
            }
        })
    }
    private fun showApiAlert(
        context: Context,
        message: String,
        msgHead: String,
    ){
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_ok_cancel)
        val icon = dialog.findViewById(R.id.iconImageView) as ImageView
        /* icon.setBackgroundResource(bgIcon)
         icon.setImageResource(ico)*/
        val text = dialog.findViewById(R.id.text_dialog) as TextView
        val textHead = dialog.findViewById(R.id.alertHead) as TextView
        text.text = message
        textHead.text = msgHead
        val dialogButton = dialog.findViewById(R.id.btn_Ok) as Button
        dialogButton.setOnClickListener {
            if (ConstantFunctions.internetCheck(mContext))
            {
                ccacancelAPI()
            }
            else
            {
                DialogFunctions.showInternetAlertDialog(mContext)
            }
            dialog.dismiss()
        }
        val dialogButtonCancel = dialog.findViewById(R.id.btn_Cancel) as Button
        dialogButtonCancel.setOnClickListener {

            dialog.dismiss() }
        dialog.show()
    }

    private fun showApiLogoAlert(
        context: Context,
        message: String,
        msgHead: String,
    ){
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_ok_cancel)
        val icon = dialog.findViewById(R.id.iconImageView) as ImageView
        /* icon.setBackgroundResource(bgIcon)
         icon.setImageResource(ico)*/
        val text = dialog.findViewById(R.id.text_dialog) as TextView
        val textHead = dialog.findViewById(R.id.alertHead) as TextView
        text.text = message
        textHead.text = msgHead
        val dialogButton = dialog.findViewById(R.id.btn_Ok) as Button
        dialogButton.setOnClickListener {
            if (ConstantFunctions.internetCheck(mContext))
            {
                ccacancelLogoAPI()
            }
            else
            {
                DialogFunctions.showInternetAlertDialog(mContext)
            }
            dialog.dismiss()
        }
        val dialogButtonCancel = dialog.findViewById(R.id.btn_Cancel) as Button
        dialogButtonCancel.setOnClickListener {

            dialog.dismiss() }
        dialog.show()
    }

    private fun ccacancelAPI() {

        var model= CCACancelModel(PreferenceManager.getStudIdForCCA(mContext).toString(),
            PreferenceManager.getCCAItemId(mContext).toString()
        )
        val token = PreferenceManager.getaccesstoken(mContext)
        val call: Call<CCASubmitResponseModel> =
            ApiClient.getClient.ccareservecancel( model,"Bearer $token")
        progress.visibility = View.VISIBLE
        call.enqueue(object : Callback<CCASubmitResponseModel> {
            override fun onResponse(
                call: Call<CCASubmitResponseModel>,
                response: Response<CCASubmitResponseModel>
            ) {
                progress.visibility = View.GONE
                if (response.isSuccessful){
                    if (response.body() != null){
                        if (response.body()!!.status!!.equals(100)){

//                            val survey: Int = secobj.optInt("survey")
                            val mIntent = Intent(mContext, CCA_Activity_New::class.java)
                            mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            startActivity(mIntent)

                        }
                        else if (response.body()!!.status!!.equals(109))
                        {


                        }
                        else{

                            Toast.makeText(mContext, "Failure", Toast.LENGTH_SHORT).show()
                        }

                    }else{

                        ConstantFunctions.showDialogueWithOk(mContext,getString(R.string.common_error),"Alert")
                    }
                }else{

                    ConstantFunctions.showDialogueWithOk(mContext,getString(R.string.common_error),"Alert")
                }
            }

            override fun onFailure(call: Call<CCASubmitResponseModel>, t: Throwable) {
                progress.visibility = View.GONE
                ConstantFunctions.showDialogueWithOk(mContext,getString(R.string.common_error),"Alert")
            }

        })
    }


    private fun ccacancelLogoAPI() {

        var model= CCACancelModel(PreferenceManager.getStudIdForCCA(mContext).toString(),
            PreferenceManager.getCCAItemId(mContext).toString()
        )
        val token = PreferenceManager.getaccesstoken(mContext)
        val call: Call<CCASubmitResponseModel> =
            ApiClient.getClient.ccareservecancel( model,"Bearer $token")
        progress.visibility = View.VISIBLE
        call.enqueue(object : Callback<CCASubmitResponseModel> {
            override fun onResponse(
                call: Call<CCASubmitResponseModel>,
                response: Response<CCASubmitResponseModel>
            ) {
                progress.visibility = View.GONE
                if (response.isSuccessful){
                    if (response.body() != null){
                        if (response.body()!!.status!!.equals(100)){

//                            val survey: Int = secobj.optInt("survey")
                            val mIntent = Intent(mContext, HomeActivity::class.java)
                            mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            startActivity(mIntent)

                        }
                        else if (response.body()!!.status!!.equals(109))
                        {


                        }
                        else{

                            Toast.makeText(mContext, "Failure", Toast.LENGTH_SHORT).show()
                        }

                    }else{

                        ConstantFunctions.showDialogueWithOk(mContext,getString(R.string.common_error),"Alert")
                    }
                }else{

                    ConstantFunctions.showDialogueWithOk(mContext,getString(R.string.common_error),"Alert")
                }
            }

            override fun onFailure(call: Call<CCASubmitResponseModel>, t: Throwable) {
                progress.visibility = View.GONE
                ConstantFunctions.showDialogueWithOk(mContext,getString(R.string.common_error),"Alert")
            }

        })
    }


    fun getWeekday(weekDay: Int): String? {
        var day = ""
        when (weekDay) {
            0 -> day = "Sunday"
            1 -> day = "Monday"
            2 -> day = "Tuesday"
            3 -> day = "Wednesday"
            4 -> day = "Thursday"
            5 -> day = "Friday"
            6 -> day = "Saturday"
        }
        return day
    }

    fun getWeekdayMMM(weekDay: Int): String? {
        var day = ""
        when (weekDay) {
            0 -> day = "SUN"
            1 -> day = "MON"
            2 -> day = "TUE"
            3 -> day = "WED"
            4 -> day = "THU"
            5 -> day = "FRI"
            6 -> day = "SAT"
        }
        return day
    }

    override fun onBackPressed() {
        if(ccaedit==0)
        {
            if(AppController.keyy.equals("1"))
            {
                showApiAlert(mContext,"Leaving this page will cancel all reserved activities for this schedule. Do you want to continue?","Confirm")

            }
            else
            {
                val mIntent = Intent(mContext, CCA_Activity_New::class.java)
                mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

                startActivity(mIntent)
            }
            // showApiAlert(mContext,"Leaving this page will cancel all reserved activities for this schedule. Do you want to continue?","Confirm")
        }
        else
        {
            finish()
        }


    }


}