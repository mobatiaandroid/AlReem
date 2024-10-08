package com.nas.alreem.fragment.performing_arts.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nas.alreem.R
import com.nas.alreem.fragment.performing_arts.model.SecondaryModel
import com.nas.alreem.fragment.primary.model.PrimaryDataModel

class PerformingArtsListAdapter(
    private val mContext: Context,
    mnNewsLetterModelArrayList: ArrayList<SecondaryModel>
) :
    RecyclerView.Adapter<PerformingArtsListAdapter.MyViewHolder>() {
    private val mnNewsLetterModelArrayList: ArrayList<SecondaryModel>
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_pdf_adapter_row, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        holder.submenu.setText(mnNewsLetterModelArrayList.get(position).getSubmenu());
        holder.pdfTitle.setText(mnNewsLetterModelArrayList[position].getmName())
        holder.imageIcon.visibility = View.GONE
         /* if (mnNewsLetterModelArrayList.get(position).getmFile().contains(".pdf")) {
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