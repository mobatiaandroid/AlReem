package com.nas.alreem.activity.cca.adapter

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nas.alreem.R
import com.nas.alreem.activity.ProgressBarDialog
import com.nas.alreem.activity.cca.model.*
import com.nas.alreem.appcontroller.AppController
import com.nas.alreem.constants.ConstantFunctions
import com.nas.alreem.constants.DialogFunctions
import com.nas.alreem.constants.PreferenceManager
import com.nas.alreem.recyclermanager.RecyclerItemListener
import com.nas.alreem.rest.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CCAsActivityAdapter : RecyclerView.Adapter<CCAsActivityAdapter.MyViewHolder> {
    var mContext: Context
    var recyclerViewLayoutManager: GridLayoutManager? = null
    var recyclerViewLayoutManager2: GridLayoutManager? = null
    var mCCAmodelArrayList: ArrayList<CCADetailModel>? = null
    var mCCAchoiceModel2: ArrayList<CCAchoiceModel>? = null
    var mCCAchoiceModel1: ArrayList<CCAchoiceModel>? = null
    var weekList: ArrayList<WeekListModel>? = null
    var recyclerWeek: RecyclerView? = null
    var dayPosition = 0
    var count = 2
    var ccaedit = 0
    var ccaDetailpos = 0
    var submitBtn: Button? = null
    var nextBtn: Button? = null
    var filled: Boolean? = null
    var keyvl:Boolean? = null
    var msgRelative: RelativeLayout? = null
    var CCADetailModelArrayList: ArrayList<CCADetailModel>? = ArrayList()
    var mCCAsActivityAdapter2: CCAsChoiceListActivityAdapter? = null
    var mCCAsActivityAdapter1: CCAsChoiceListActivityAdapter? = null
    var selectedChoice1 = ""
    var selectedChoice2 = ""

    lateinit var progressDialogP: ProgressBarDialog

    constructor(
        mContext: Context,
        mCCAchoiceModel1: ArrayList<CCAchoiceModel>?,
        mCCAchoiceModel2: ArrayList<CCAchoiceModel>?,
        mdayPosition: Int,
        mWeekList: ArrayList<WeekListModel>?,
        recyclerView: RecyclerView?,
        ccaedit: Int,
        CCADetailModelArrayList: ArrayList<CCADetailModel>?,
        nextBtn: Button?,
        submitBtn: Button?,
        filled: Boolean?,
        ccaDetailpos: Int,
        msgRelative: RelativeLayout?
    ) {
        this.mContext = mContext
        this.mCCAchoiceModel1 = mCCAchoiceModel1
        this.mCCAchoiceModel2 = mCCAchoiceModel2
        dayPosition = mdayPosition
        weekList = mWeekList
        recyclerWeek = recyclerView
        count = 2
        this.ccaedit = ccaedit
        this.CCADetailModelArrayList = CCADetailModelArrayList
        this.filled = filled
        this.submitBtn = submitBtn
        this.nextBtn = nextBtn
        this.ccaDetailpos = ccaDetailpos
        this.msgRelative = msgRelative
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var listTxtView: TextView
        var recycler_review: RecyclerView
        var recycler_review2: RecyclerView

        init {
            listTxtView = view.findViewById<View>(R.id.textViewCCAaItem) as TextView
            recycler_review =
                view.findViewById<View>(R.id.recycler_view_adapter_cca) as RecyclerView
            recycler_review2 =
                view.findViewById<View>(R.id.recycler_view_adapter_cca2) as RecyclerView
        }
    }

    constructor(mContext: Context, mCcaArrayList: ArrayList<CCADetailModel>?) {
        this.mContext = mContext
        mCCAmodelArrayList = mCcaArrayList
    }

    constructor(
        mContext: Context,
        mCCAchoiceModel1: ArrayList<CCAchoiceModel>?,
        mCCAchoiceModel2: ArrayList<CCAchoiceModel>?,
        mdayPosition: Int
    ) {
        this.mContext = mContext
        this.mCCAchoiceModel1 = mCCAchoiceModel1
        this.mCCAchoiceModel2 = mCCAchoiceModel2
        dayPosition = mdayPosition
    }

    constructor(
        mContext: Context,
        mCCAchoiceModel1: ArrayList<CCAchoiceModel>?,
        mCCAchoiceModel2: ArrayList<CCAchoiceModel>?,
        mdayPosition: Int,
        mWeekList: ArrayList<WeekListModel>?,
        recyclerView: RecyclerView?
    ) {
        this.mContext = mContext
        this.mCCAchoiceModel1 = mCCAchoiceModel1
        this.mCCAchoiceModel2 = mCCAchoiceModel2
        dayPosition = mdayPosition
        weekList = mWeekList
        recyclerWeek = recyclerView
        count = 2
    }

    constructor(mContext: Context, mcount: Int) {
        this.mContext = mContext
        mCCAchoiceModel1 = ArrayList<CCAchoiceModel>()
        mCCAchoiceModel2 = ArrayList<CCAchoiceModel>()
        count = mcount
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_cca_activity, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        progressDialogP= ProgressBarDialog(mContext)

        if (count != 0) {
            holder.recycler_review.setHasFixedSize(true)
            holder.recycler_review2.setHasFixedSize(true)
            recyclerViewLayoutManager = GridLayoutManager(mContext, 1)
            recyclerViewLayoutManager2 = GridLayoutManager(mContext, 1)
            holder.recycler_review.layoutManager = recyclerViewLayoutManager
            holder.recycler_review2.layoutManager = recyclerViewLayoutManager2
            if (position == 0) {
                if (mCCAchoiceModel1!!.size > 0) {
                    if (mCCAchoiceModel2!!.size <= 0) {
                        AppController.weekList.get(dayPosition).choiceStatus1=("1")
                    }
                    holder.listTxtView.text = "Choice : " // + (position + 1)

                    if (ccaedit == 1) {
                        for (k in CCADetailModelArrayList!!.indices) {
                            for (i in mCCAchoiceModel1!!.indices) {
                                if (mCCAchoiceModel1!![i].status.equals("1")) {
                                    selectedChoice1 = mCCAchoiceModel1!![i].cca_item_name!!
                                    PreferenceManager.setdetailvalue(mContext,mCCAchoiceModel1!![i].cca_details_id!!)

                                    if (mCCAchoiceModel2 != null) {
                                        for (j in mCCAchoiceModel2!!.indices) {
                                            if (mCCAchoiceModel2!![j].cca_item_name
                                                    .equals(selectedChoice1)
                                            ) {
                                                if (!mCCAchoiceModel2!![j].cca_details_id
                                                        .equals("-541")
                                                ) {
                                                    mCCAchoiceModel2!![j].disableCccaiem=(true)
                                                } else {
                                                    mCCAchoiceModel2!![j].disableCccaiem=(false)
                                                }
                                                break
                                            } else {
                                                mCCAchoiceModel2!![j].disableCccaiem=(false)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    mCCAsActivityAdapter1 = CCAsChoiceListActivityAdapter(
                        mContext,
                        mCCAchoiceModel1!!,
                        dayPosition,
                        weekList,
                        0,
                        recyclerWeek,
                        CCADetailModelArrayList,
                        submitBtn,
                        nextBtn,
                        filled,
                        ccaDetailpos,
                        msgRelative
                    )
                    holder.recycler_review.adapter = mCCAsActivityAdapter1
                }
            } /*else {
                if (mCCAchoiceModel2!!.size > 0) {
                    if (mCCAchoiceModel1!!.size <= 0) {
                        AppController.weekList.get(dayPosition).choiceStatus=("1")
                    }
                    holder.listTxtView.text = "Second Choice : " // + (position + 1)
                    if (ccaedit == 1) {
                        for (k in CCADetailModelArrayList!!.indices) {
                            for (i in mCCAchoiceModel2!!.indices) {
                            //    System.out.println("Status2::" + mCCAchoiceModel2!![position].status)
                                if (mCCAchoiceModel2!![i].status.equals("1")) {
                                    selectedChoice2 = mCCAchoiceModel2!![i].cca_item_name!!
                                    if (mCCAchoiceModel1 != null) {
                                        for (j in mCCAchoiceModel1!!.indices) {
                                            if (mCCAchoiceModel1!![j].cca_item_name
                                                    .equals(selectedChoice2)
                                            ) {
                                                if (!mCCAchoiceModel1!![j].cca_details_id
                                                        .equals("-541")
                                                ) {
                                                    mCCAchoiceModel1!![j].disableCccaiem=(true)
                                                } else {
                                                    mCCAchoiceModel1!![j].disableCccaiem=(false)
                                                }
                                                break
                                            } else {
                                                mCCAchoiceModel1!![j].disableCccaiem=(false)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    mCCAsActivityAdapter2 = CCAsChoiceListActivityAdapter(
                        mContext,
                        mCCAchoiceModel2!!,
                        dayPosition,
                        weekList,
                        1,
                        recyclerWeek,
                        CCADetailModelArrayList,
                        submitBtn,
                        nextBtn,
                        filled,
                        ccaDetailpos,
                        msgRelative
                    )
                    holder.recycler_review2.adapter = mCCAsActivityAdapter2
                    mCCAsActivityAdapter1!!.notifyDataSetChanged()
                    mCCAsActivityAdapter2!!.notifyDataSetChanged()
                }
            }*/
            holder.recycler_review.addOnItemTouchListener(
                RecyclerItemListener(mContext, holder.recycler_review,
                    object : RecyclerItemListener.RecyclerTouchListener {
                        override fun onClickItem(v: View?, pos: Int) {
                            if (!mCCAchoiceModel1!![pos].disableCccaiem!!) {
                                for (i in mCCAchoiceModel1!!.indices) {
                                    if (pos == i) {
                                        if(ccaedit==0)
                                        {
                                            if (mCCAchoiceModel1!![pos].slot_remaining_count!!>0) {
                                                if (ConstantFunctions.internetCheck(mContext))
                                                {

                                                    var model= CCAReservetRequestModel(
                                                        PreferenceManager.getStudIdForCCA(mContext).toString(),
                                                        PreferenceManager.getCCAItemId(mContext).toString(), mCCAchoiceModel1!![i].cca_details_id!!
                                                    )
                                                    val token = PreferenceManager.getaccesstoken(mContext)
                                                    val call: Call<CCASubmitResponseModel> =
                                                        ApiClient.getClient.ccareserve( model,"Bearer $token")
                                                    progressDialogP.show()
                                                    call.enqueue(object :
                                                        Callback<CCASubmitResponseModel> {
                                                        override fun onResponse(
                                                            call: Call<CCASubmitResponseModel>,
                                                            response: Response<CCASubmitResponseModel>
                                                        ) {
                                                            progressDialogP.hide()
                                                            if (response.isSuccessful){
                                                                if (response.body() != null){
                                                                    if (response.body()!!.status!!.equals(100)){

//                            val survey: Int = secobj.optInt("survey")
                                                                        mCCAchoiceModel1!![i].status=("1")
                                                                        selectedChoice1 = mCCAchoiceModel1!![i].cca_item_name!!
                                                                        mCCAsActivityAdapter1 = CCAsChoiceListActivityAdapter(
                                                                            mContext,
                                                                            mCCAchoiceModel1!!,
                                                                            dayPosition,
                                                                            weekList,
                                                                            0,
                                                                            recyclerWeek,
                                                                            CCADetailModelArrayList,
                                                                            submitBtn,
                                                                            nextBtn,
                                                                            filled,
                                                                            ccaDetailpos,
                                                                            msgRelative
                                                                        )
                                                                        mCCAsActivityAdapter1!!.notifyDataSetChanged()
                                                                        holder.recycler_review.adapter = mCCAsActivityAdapter1

                                                                    }
                                                                    else if (response.body()!!.status!!.equals(109))
                                                                    {


                                                                    }
                                                                    else{
                                                                        if (response.body()!!.status!!.equals(124))
                                                                        {
                                                                            ConstantFunctions.showDialogueWithOk(mContext,response.body()!!.message!!.toString(),"Alert")

                                                                        }
                                                                        else{
                                                                            Toast.makeText(mContext, "Cannot continue. Please try again later", Toast.LENGTH_SHORT).show()
                                                                        }


                                                                    }

                                                                }else{

                                                                    ConstantFunctions.showDialogueWithOk(mContext,"Cannot continue. Please try again later","Alert")
                                                                }
                                                            }else{

                                                                ConstantFunctions.showDialogueWithOk(mContext,"Cannot continue. Please try again later","Alert")
                                                            }
                                                        }

                                                        override fun onFailure(call: Call<CCASubmitResponseModel>, t: Throwable) {
                                                            progressDialogP.hide()
                                                            ConstantFunctions.showDialogueWithOk(mContext,"Cannot continue. Please try again later","Alert")
                                                        }

                                                    })
                                                }
                                                else
                                                {
                                                    DialogFunctions.showInternetAlertDialog(mContext)
                                                }

                                            }
                                            else
                                            {
                                                Toast.makeText(mContext, "Activity fully Booked", Toast.LENGTH_SHORT).show()
                                            }

                                        }
                                        else
                                        {
                                            if (mCCAchoiceModel1!![pos].slot_remaining_count!!>0) {


                                                Log.e("Condition work","Slot greatrer")

                                                mCCAchoiceModel1!![i].status=("1")
                                                selectedChoice1 = mCCAchoiceModel1!![i].cca_item_name!!
                                                Log.e("pos",mCCAchoiceModel1!![i].cca_details_id!!)
                                                PreferenceManager.setdetailvalue(mContext,mCCAchoiceModel1!![i].cca_details_id!!)
                                                System.out.println("Choicere1:" + mCCAchoiceModel1!![i].cca_item_name)

// if (mCCAchoiceModel1!![pos].cca_details_id.equals(mCCAchoiceModel1!![i].cca_details_id!!)) {
//
// Log.e("WORK","TRUE POS")
// }
// else
// {
// Log.e("Work","False POP")
// mCCAchoiceModel1!![i].status=("1")
// selectedChoice1 = mCCAchoiceModel1!![i].cca_item_name!!
// Log.e("pos",mCCAchoiceModel1!![i].cca_details_id!!)
// PreferenceManager.setdetailvalue(mContext,mCCAchoiceModel1!![i].cca_details_id!!)
// System.out.println("Choicere1:" + mCCAchoiceModel1!![i].cca_item_name)
//
// }
                                            }
                                            else
                                            {
                                                if ( mCCAchoiceModel1!![i].attending_status.equals("1"))
                                                {
                                                    mCCAchoiceModel1!![i].status=("1")
                                                    selectedChoice1 = mCCAchoiceModel1!![i].cca_item_name!!
                                                    Log.e("pos",mCCAchoiceModel1!![i].cca_details_id!!)
                                                    PreferenceManager.setdetailvalue(mContext,mCCAchoiceModel1!![i].cca_details_id!!)
                                                    System.out.println("Choicere1:" + mCCAchoiceModel1!![i].cca_item_name)
                                                }
                                                else{
                                                    Toast.makeText(mContext, "Activity fully Booked", Toast.LENGTH_SHORT).show()

                                                }


                                            }

                                        }
                                        /*if (mCCAchoiceModel2 != null) {
                                            for (j in mCCAchoiceModel2!!.indices) {
                                                if (mCCAchoiceModel2!![j].cca_item_name
                                                        .equals(
                                                            mCCAchoiceModel1!![pos].cca_item_name
                                                        )
                                                ) {
                                                    if (!mCCAchoiceModel2!![j].cca_details_id.equals("-541")
                                                    ) {
                                                        mCCAchoiceModel2!![j].disableCccaiem=(true)
                                                    } else {
                                                        mCCAchoiceModel2!![j].disableCccaiem=(false)
                                                    }

                                                    if (mCCAsActivityAdapter2 != null) {
                                                        mCCAsActivityAdapter2!!.notifyDataSetChanged()
                                                    }
//                                                    break;
                                                } else {
                                                    mCCAchoiceModel2!![j].disableCccaiem=(false)
                                                    if (mCCAsActivityAdapter2 != null) {
                                                        mCCAsActivityAdapter2!!.notifyDataSetChanged()
                                                    }

                                                }
                                            }
                                        }*/
                                    } else {
                                        mCCAchoiceModel1!![i].status=("0")
                                        mCCAchoiceModel1!![i].disableCccaiem=(false)
                                        System.out.println("Choicere1 Else:" + mCCAchoiceModel1!![i].cca_item_name)
                                        if (mCCAchoiceModel1 != null) {
                                            for (j in mCCAchoiceModel1!!.indices) {
                                                if (mCCAchoiceModel1!![j].cca_item_name
                                                        .equals(selectedChoice2)
                                                ) {
                                                    if (!mCCAchoiceModel1!![j].cca_details_id.equals("-541")
                                                    ) {
                                                        mCCAchoiceModel1!![j].disableCccaiem=(true)
                                                    } else {
                                                        mCCAchoiceModel1!![j].disableCccaiem=(false)
                                                    }

                                                    if (mCCAsActivityAdapter1 != null) {
                                                        mCCAsActivityAdapter1!!.notifyDataSetChanged()
                                                    }
//                                                    break;
                                                } else {
                                                    mCCAchoiceModel1!![j].disableCccaiem=(false)
                                                    if (mCCAsActivityAdapter1 != null) {
                                                        mCCAsActivityAdapter1!!.notifyDataSetChanged()
                                                    }

                                                }
                                            }
                                        }
                                    }
                                }
                                System.out.println("choicere1 text" + mCCAchoiceModel1!![pos].cca_item_name)
                                mCCAsActivityAdapter1 = CCAsChoiceListActivityAdapter(
                                    mContext,
                                    mCCAchoiceModel1!!,
                                    dayPosition,
                                    weekList,
                                    0,
                                    recyclerWeek,
                                    CCADetailModelArrayList,
                                    submitBtn,
                                    nextBtn,
                                    filled,
                                    ccaDetailpos,
                                    msgRelative
                                )
                                mCCAsActivityAdapter1!!.notifyDataSetChanged()
                                holder.recycler_review.adapter = mCCAsActivityAdapter1
                            }


                        }

                        override fun onLongClickItem(v: View?, position: Int) {
                            println("On Long Click Item interface")
                        }
                    })
            )
            /*holder.recycler_review2.addOnItemTouchListener(
                RecyclerItemListener(mContext, holder.recycler_review2,
                    object : RecyclerItemListener.RecyclerTouchListener {
                        override fun onClickItem(v: View?, pos: Int) {
                            if (!mCCAchoiceModel2!![pos].disableCccaiem!!) {
                                for (i in mCCAchoiceModel2!!.indices) {
                                    if (pos == i) {
                                        mCCAchoiceModel2!![i].status=("1")
                                        System.out.println("Choicere2:" + mCCAchoiceModel2!![i].cca_item_name)
                                        selectedChoice2 = mCCAchoiceModel2!![i].cca_item_name!!
                                        if (mCCAchoiceModel1 != null) {
                                            for (j in mCCAchoiceModel1!!.indices) {
                                                if (mCCAchoiceModel1!![j].cca_item_name.equals(mCCAchoiceModel2!![pos].cca_item_name)
                                                ) {
                                                    if (!mCCAchoiceModel1!![j].cca_details_id.equals("-541")
                                                    ) {
                                                        mCCAchoiceModel1!![j].disableCccaiem=(true)
                                                    } else {
                                                        mCCAchoiceModel1!![j].disableCccaiem=(false)
                                                    }

                                                    if (mCCAsActivityAdapter1 != null) {
                                                        mCCAsActivityAdapter1!!.notifyDataSetChanged()
                                                    }
//                                                    break;
                                                } else {
                                                    mCCAchoiceModel1!![j].disableCccaiem=(false)
                                                    if (mCCAsActivityAdapter1 != null) {
                                                        mCCAsActivityAdapter1!!.notifyDataSetChanged()
                                                    }

                                                }
                                            }
                                        }
                                    } else {
                                        mCCAchoiceModel2!![i].status=("0")
                                        mCCAchoiceModel2!![i].disableCccaiem=(false)
                                        System.out.println("Choicere2 else:" + mCCAchoiceModel2!![i].cca_item_name)
                                        if (mCCAchoiceModel2 != null) {
                                            for (j in mCCAchoiceModel2!!.indices) {
                                                if (mCCAchoiceModel2!![j].cca_item_name.equals(selectedChoice1)) {
                                                    if (!mCCAchoiceModel2!![j].cca_details_id.equals("-541")) {
                                                        mCCAchoiceModel2!![j].disableCccaiem=(true)
                                                    } else {
                                                        mCCAchoiceModel2!![j].disableCccaiem=(false)
                                                    }

                                                    if (mCCAsActivityAdapter2 != null) {
                                                        mCCAsActivityAdapter2!!.notifyDataSetChanged()
                                                    }
//                                                    break;
                                                } else {
                                                    mCCAchoiceModel2!![j].disableCccaiem=(false)
                                                    if (mCCAsActivityAdapter2 != null) {
                                                        mCCAsActivityAdapter2!!.notifyDataSetChanged()
                                                    }

                                                }
                                            }
                                        }
                                    }
                                }
                                System.out.println("choicere2 text" + mCCAchoiceModel2!![pos].cca_item_name)
                                mCCAsActivityAdapter2 = CCAsChoiceListActivityAdapter(
                                    mContext,
                                    mCCAchoiceModel2!!,
                                    dayPosition,
                                    weekList,
                                    1,
                                    recyclerWeek,
                                    CCADetailModelArrayList,
                                    submitBtn,
                                    nextBtn,
                                    filled,
                                    ccaDetailpos,
                                    msgRelative
                                )
                                mCCAsActivityAdapter2!!.notifyDataSetChanged()
                                holder.recycler_review2.adapter = mCCAsActivityAdapter2
                            }
                        }

                        override fun onLongClickItem(v: View?, position: Int) {
                            println("On Long Click Item interface")
                        }
                    })
            )*/
        }
    }

    override fun getItemCount(): Int {
        return count
    }
}