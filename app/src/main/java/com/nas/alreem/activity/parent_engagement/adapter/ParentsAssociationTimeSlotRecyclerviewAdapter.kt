package com.nas.alreem.activity.parent_engagement.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.nas.alreem.R
import com.nas.alreem.activity.parent_engagement.model.ParentAssociationEventItemsModel
import com.nas.alreem.constants.ConstantFunctions


class ParentsAssociationTimeSlotRecyclerviewAdapter :
    RecyclerView.Adapter<ParentsAssociationTimeSlotRecyclerviewAdapter.MyViewHolder> {
    private var mContext: Context
    private var mParentAssociationEventsModelArrayList: ArrayList<ParentAssociationEventItemsModel>
    var photo_id = "-1"
    var startTime = ""
    var startTimeAm = true
    var endTimeAm = true
    var endTime = ""
    var pos = 0

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var userName: TextView
        var timeFrom: TextView
        var timeTo: TextView
        var textViewTo: TextView
        var gridClickRelative: RelativeLayout
        var mLinearLayout: LinearLayout
        var card_view: CardView

        init {

//            photoImageView = (ImageView) view.findViewById(R.id.imgView);
            userName = view.findViewById<View>(R.id.userName) as TextView
            timeFrom = view.findViewById<View>(R.id.timeFrom) as TextView
            timeTo = view.findViewById<View>(R.id.timeTo) as TextView
            textViewTo = view.findViewById<View>(R.id.textViewTo) as TextView
            gridClickRelative = view.findViewById<View>(R.id.gridClickRelative) as RelativeLayout
            mLinearLayout = view.findViewById<View>(R.id.mLinearLayout) as LinearLayout
            card_view = view.findViewById<View>(R.id.card_view) as CardView
        }
    }

    constructor(
        mContext: Context,
        mParentAssociationEventsModelArrayList: ArrayList<ParentAssociationEventItemsModel>
    ) {
        this.mContext = mContext
        this.mParentAssociationEventsModelArrayList = mParentAssociationEventsModelArrayList
    }

    constructor(
        mContext: Context,
        mParentAssociationEventsModelArrayList: ArrayList<ParentAssociationEventItemsModel>,
        pos: Int
    ) {
        this.mContext = mContext
        this.mParentAssociationEventsModelArrayList = mParentAssociationEventsModelArrayList
        this.pos = pos
    }

    constructor(
        mContext: Context,
        mParentAssociationEventsModelArrayList: ArrayList<ParentAssociationEventItemsModel>,
        photo_id: String
    ) {
        this.mContext = mContext
        this.mParentAssociationEventsModelArrayList = mParentAssociationEventsModelArrayList
        this.photo_id = photo_id
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ParentsAssociationTimeSlotRecyclerviewAdapter.MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.time_slot_parents_association_adapter, parent, false)
        itemView.id = pos
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: ParentsAssociationTimeSlotRecyclerviewAdapter.MyViewHolder,
        position: Int
    ) {
//        holder.phototakenDate.setText(mPhotosModelArrayList.get(position).getMonth() + " " + mPhotosModelArrayList.get(position).getDay() + "," + mPhotosModelArrayList.get(position).getYear());

//       System.out.println("Time:::"+mParentAssociationEventsModelArrayList.get(position).getTo_time());
        if (mParentAssociationEventsModelArrayList[position].to_time.contains("a.m.")) {
            endTime =
               ConstantFunctions(). replaceamdot(mParentAssociationEventsModelArrayList[position].to_time)!!
                   .trim()
            endTime += " am"
            endTimeAm = true
        } else if (mParentAssociationEventsModelArrayList[position].to_time.contains("p.m.")) {
            endTime =
                ConstantFunctions().replacepmdot(mParentAssociationEventsModelArrayList[position].to_time)!!
                    .trim()
            endTime += " pm"
            endTimeAm = false
        } else {
            endTime = mParentAssociationEventsModelArrayList[position].to_time
        }
        if (mParentAssociationEventsModelArrayList[position].from_time.contains("a.m.")) {
            startTime =
                ConstantFunctions(). replaceamdot(mParentAssociationEventsModelArrayList[position].from_time)!!
                    .trim()
            startTime += " am"
            endTimeAm = true
        } else if (mParentAssociationEventsModelArrayList[position].from_time
                .contains("p.m.")
        ) {
            startTime =
                ConstantFunctions().replacepmdot(mParentAssociationEventsModelArrayList[position].from_time)!!
                    .trim()
            startTime += " pm"
            endTimeAm = false
        } else {
            startTime = mParentAssociationEventsModelArrayList[position].from_time
        }
        holder.userName.setText(mParentAssociationEventsModelArrayList[position].userName)
        holder.timeTo.setText(endTime)
        holder.timeFrom.setText(startTime)
        if (mParentAssociationEventsModelArrayList[position].userName.equals("")) {
            val layoutParams = holder.mLinearLayout.getLayoutParams() as RelativeLayout.LayoutParams
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE)
            holder.mLinearLayout.setLayoutParams(layoutParams)
        } else {
            val layoutParams = holder.mLinearLayout.getLayoutParams() as RelativeLayout.LayoutParams
            layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE)
            holder.mLinearLayout.setLayoutParams(layoutParams)
        }
        if (mParentAssociationEventsModelArrayList[position].status.equals("0")) {
            holder.gridClickRelative.setBackgroundResource(R.drawable.time_curved_rel_layout)
            holder.textViewTo.setTextColor(mContext.resources.getColor(R.color.black))

//            holder.textViewTo.setTextColor(mContext.getResources().getColor(R.color.split_bg));
        } else if (mParentAssociationEventsModelArrayList[position].status
                .equals("1")
        ) {
            holder.timeFrom.setTextColor(mContext.resources.getColor(R.color.white))
            holder.timeTo.setTextColor(mContext.resources.getColor(R.color.white))
            holder.textViewTo.setTextColor(mContext.resources.getColor(R.color.white))
            holder.userName.setTextColor(mContext.resources.getColor(R.color.white))
            holder.gridClickRelative.setBackgroundResource(R.drawable.parent_slot_new)
            //            holder.textViewTo.setTextColor(mContext.getResources().getColor(R.color.black));
        } else if (mParentAssociationEventsModelArrayList[position].status
                .equals("2")
        ) {
            holder.gridClickRelative.setBackgroundResource(R.drawable.slotbooked_curved_rel_layout)
            holder.textViewTo.setTextColor(mContext.resources.getColor(R.color.black))

//            holder.textViewTo.setTextColor(mContext.getResources().getColor(R.color.split_bg));
        }



    }

    override fun getItemCount(): Int {
        return mParentAssociationEventsModelArrayList.size
    }
}
