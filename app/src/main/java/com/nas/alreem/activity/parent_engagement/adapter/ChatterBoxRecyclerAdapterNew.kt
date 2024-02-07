package com.nas.alreem.activity.parent_engagement.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nas.alreem.R
import com.nas.alreem.activity.parent_engagement.model.TermsCalendarModel


class ChatterBoxRecyclerAdapterNew(
    private val mContext: Context,
    mnNewsLetterModelArrayList: ArrayList<TermsCalendarModel>
) :
    RecyclerView.Adapter<ChatterBoxRecyclerAdapterNew.MyViewHolder>() {
    private val mnNewsLetterModelArrayList: ArrayList<TermsCalendarModel>
    var dept: String? = null

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var imageIcon: ImageView
        var pdfTitle: TextView

        init {
            imageIcon = view.findViewById<View>(R.id.imageIcon) as ImageView
            pdfTitle = view.findViewById<View>(R.id.pdfTitle) as TextView
        }
    }

    init {
        this.mnNewsLetterModelArrayList = mnNewsLetterModelArrayList
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ChatterBoxRecyclerAdapterNew.MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_pdf_adapter_row, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: ChatterBoxRecyclerAdapterNew.MyViewHolder,
        position: Int
    ) {
        holder.pdfTitle.setText(mnNewsLetterModelArrayList[position].getmTitle())
        holder.imageIcon.setVisibility(View.GONE)
    }

    override fun getItemCount(): Int {
        return mnNewsLetterModelArrayList.size
    }
}
