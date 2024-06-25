package com.nas.alreem.activity.cca.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nas.alreem.R
import com.nas.alreem.activity.cca.model.ExternalProvidersResponseModel
import com.nas.alreem.activity.shop_new.adapter.BasketItemsAdapter_new


class ExternalProviderRecyclerAdapter(mContext: Context
                                      , mListViewArray: ArrayList<ExternalProvidersResponseModel.Data.Lists>)
    : RecyclerView.Adapter<ExternalProviderRecyclerAdapter.MyViewHolder>() {
    private val mContext: Context? = mContext
    private val mnNewsLetterModelArrayList: ArrayList<ExternalProvidersResponseModel.Data.Lists> = mListViewArray
    var dept: String? = null
    private val statusLayout: RelativeLayout? = null
    private val status: TextView? = null

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var imageIcon: ImageView
        var pdfTitle: TextView
//        private val statusLayout: RelativeLayout
//        private val status: TextView

        init {
            imageIcon = view.findViewById<View>(R.id.imageIcon) as ImageView
           // pdfTitle = view.findViewById<View>(R.id.pdfTitle) as TextView
            pdfTitle = view.findViewById<TextView>(R.id.pdfTitle)

//            status = view.findViewById<View>(R.id.status) as TextView
//            statusLayout = view.findViewById<View>(R.id.statusLayout) as RelativeLayout
        }
    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.custom_pdf_adapter_row_new, viewGroup, false)
        return MyViewHolder(view)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

//        holder.submenu.setText(mnNewsLetterModelArrayList.get(position).getSubmenu());
        holder.pdfTitle.text = mnNewsLetterModelArrayList[position].title
        holder.imageIcon.visibility = View.GONE
/*
        if (mnNewsLetterModelArrayList.get(position).getmFile().endsWith(".pdf")) {
            holder.imageIcon.setBackgroundResource(R.drawable.pdfdownloadbutton);
        }
        else
        {
            holder.imageIcon.setBackgroundResource(R.drawable.webcontentviewbutton);

        }*/
        /*
        if (mnNewsLetterModelArrayList.get(position).getmFile().endsWith(".pdf")) {
            holder.imageIcon.setBackgroundResource(R.drawable.pdfdownloadbutton);
        }
        else
        {
            holder.imageIcon.setBackgroundResource(R.drawable.webcontentviewbutton);

        }*/
//        if (mnNewsLetterModelArrayList[position].getStatus().equalsIgnoreCase("0")) {
//            holder.itemView.statusLayout.setVisibility(View.VISIBLE)
//            holder.itemView.status.setBackgroundResource(R.drawable.rectangle_red)
//            holder.itemView.status.setText("New")
//        } else if (mnNewsLetterModelArrayList[position].getStatus()
//                .equalsIgnoreCase("1") || mnNewsLetterModelArrayList[position].getStatus()
//                .equalsIgnoreCase("")
//        ) {
//            holder.itemView.status.setVisibility(View.INVISIBLE)
//        } else if (mnNewsLetterModelArrayList[position].getStatus().equalsIgnoreCase("2")) {
//            holder.itemView.statusLayout.setVisibility(View.VISIBLE)
//            holder.itemView.status.setBackgroundResource(R.drawable.rectangle_orange)
//            holder.itemView.status.setText("Updated")
//        } else {
//            holder.itemView.status.setVisibility(View.GONE)
//        }
    }

    override fun getItemCount(): Int {
        return mnNewsLetterModelArrayList.size
    }

}
