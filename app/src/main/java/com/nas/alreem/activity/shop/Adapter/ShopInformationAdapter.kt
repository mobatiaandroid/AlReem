package com.nas.alreem.activity.shop.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nas.alreem.R
import com.nas.alreem.fragment.primary.model.PrimaryDataModel
import com.nas.alreem.fragment.primary.model.PrimaryFileModel


class ShopInformationAdapter(
    private val mContext: Context,
    mnNewsLetterModelArrayList: ArrayList<PrimaryFileModel>
) :
    RecyclerView.Adapter<ShopInformationAdapter.MyViewHolder>() {
    var dept: String? = null
    private val mnNewsLetterModelArrayList: ArrayList<PrimaryFileModel>
    private val statusLayout: RelativeLayout? = null
    private val status: TextView? = null

    init {
        this.mnNewsLetterModelArrayList = mnNewsLetterModelArrayList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_pdf_adapter_row_new, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        holder.submenu.setText(mnNewsLetterModelArrayList.get(position).getSubmenu());
        holder.pdfTitle.setText(mnNewsLetterModelArrayList[position].file)
        holder.imageIcon.visibility = View.GONE
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

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var imageIcon: ImageView
        var pdfTitle: TextView
        private val statusLayout: RelativeLayout
        private val status: TextView

        init {
            imageIcon = view.findViewById<View>(R.id.imageIcon) as ImageView
            pdfTitle = view.findViewById<View>(R.id.pdfTitle) as TextView
            status = view.findViewById<View>(R.id.status) as TextView
            statusLayout = view.findViewById<View>(R.id.statusLayout) as RelativeLayout
        }
    }
}
