package com.nas.alreem.activity.parent_engagement.adapter

import android.content.Context
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.nas.alreem.R
import com.nas.alreem.activity.parent_engagement.model.ParentAssociationEventsModel


class ParentsAssociationItemNameRecyclerviewAdapter :
    RecyclerView.Adapter<ParentsAssociationItemNameRecyclerviewAdapter.MyViewHolder> {
    private var mContext: Context
    private var mParentAssociationEventsModelArrayList: ArrayList<ParentAssociationEventsModel>
    var photo_id = "-1"
    var startTime = ""
    var startTimeAm = true
    var endTimeAm = true
    var endTime = ""
    private val selectedItems: SparseBooleanArray? = null
    var pos = 0

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var itemName: TextView
        var eventDate: TextView? = null
        var gridClickRelative: RelativeLayout
        var card_view: CardView

        init {

//            photoImageView = (ImageView) view.findViewById(R.id.imgView);
            itemName = view.findViewById<View>(R.id.itemName) as TextView
            gridClickRelative = view.findViewById<View>(R.id.gridClickRelative) as RelativeLayout
            card_view = view.findViewById<View>(R.id.card_view) as CardView
        }
    }

    constructor(
        mContext: Context,
        mParentAssociationEventsModelArrayList: ArrayList<ParentAssociationEventsModel>
    ) {
        this.mContext = mContext
        this.mParentAssociationEventsModelArrayList = mParentAssociationEventsModelArrayList
        pos = 0
    }

    constructor(
        mContext: Context,
        mParentAssociationEventsModelArrayList: ArrayList<ParentAssociationEventsModel>,
        mPos: Int
    ) {
        this.mContext = mContext
        this.mParentAssociationEventsModelArrayList = mParentAssociationEventsModelArrayList
        pos = mPos
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ParentsAssociationItemNameRecyclerviewAdapter.MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.parents_association_itemname_adapter, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: ParentsAssociationItemNameRecyclerviewAdapter.MyViewHolder,
        position: Int
    ) {
//        holder.phototakenDate.setText(mPhotosModelArrayList.get(position).getMonth() + " " + mPhotosModelArrayList.get(position).getDay() + "," + mPhotosModelArrayList.get(position).getYear());
        holder.itemName.setText(mParentAssociationEventsModelArrayList[position].getItemName())


//        if (position==pos)

//        if (mParentAssociationEventsModelArrayList.get(position).isItemSelected())
//        {
//            holder.gridClickRelative.setBackgroundResource(R.drawable.curve_filled_pta_selected);
//        }
//        else
//        {
//            holder.gridClickRelative.setBackgroundResource(R.drawable.curved_filled_layout_parents_association);
//        }
        if (position == pos) {
            holder.gridClickRelative.setBackgroundResource(R.drawable.curve_filled_pta_selected)
            //            holder.gridClickRelative.getBackground().setAlpha(150);
        } else {
            holder.gridClickRelative.setBackgroundResource(R.drawable.curved_filled_layout_parents_association)
        }
    }

    override fun getItemCount(): Int {
        return mParentAssociationEventsModelArrayList.size
    } //    public void didTapButton(View view) {
    ////        ImageView button = (ImageView)view.findViewById(R.id.imgView);
    ////        final Animation myAnim = AnimationUtils.loadAnimation(mContext, R.anim.bounce);
    ////        button.startAnimation(myAnim);
    //        final Animation myAnim = AnimationUtils.loadAnimation(mContext, R.anim.bounce);
    //
    //        // Use bounce interpolator with amplitude 0.2 and frequency 20
    //        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.6, 20);
    //        myAnim.setInterpolator(interpolator);
    //
    //        view.startAnimation(myAnim);
    //    }
    //    @Override
    //    public void onViewDetachedFromWindow(MyViewHolder holder) {
    //        super.onViewDetachedFromWindow(holder);
    //        holder.photoImageView.clearAnimation();
    //    }
}
