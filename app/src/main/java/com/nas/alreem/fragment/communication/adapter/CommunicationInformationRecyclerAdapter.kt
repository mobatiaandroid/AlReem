package com.nas.alreem.fragment.communication.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nas.alreem.R
import com.nas.alreem.fragment.communication.model.CommunicationDataModel


class CommunicationInformationRecyclerAdapter(
    private val mContext: Context,
    mnNewsLetterModelArrayList: ArrayList<CommunicationDataModel>
) :
    RecyclerView.Adapter<CommunicationInformationRecyclerAdapter.MyViewHolder>() {
    private val mnNewsLetterModelArrayList: ArrayList<CommunicationDataModel>
    var dept: String? = null
    private val statusLayout: RelativeLayout? = null
    private val status: TextView? = null

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var imageIcon: ImageView
        var pdfTitle: TextView

        init {
            imageIcon = view.findViewById<View>(R.id.arrowImg) as ImageView
            pdfTitle = view.findViewById<View>(R.id.listTxtTitle) as TextView
        }
    }

    init {
        this.mnNewsLetterModelArrayList = mnNewsLetterModelArrayList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_primary_list, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        holder.submenu.setText(mnNewsLetterModelArrayList.get(position).getSubmenu());
        holder.pdfTitle.setText(mnNewsLetterModelArrayList[position].submenu)
        // holder.imageIcon.visibility = View.GONE
        /*
        if (mnNewsLetterModelArrayList.get(position).getmFile().endsWith(".pdf")) {
            holder.imageIcon.setBackgroundResource(R.drawable.pdfdownloadbutton);
        }
        else
        {
            holder.imageIcon.setBackgroundResource(R.drawable.webcontentviewbutton);

        }*/
    }

    override fun getItemCount(): Int {

        return mnNewsLetterModelArrayList.size
    }
}
