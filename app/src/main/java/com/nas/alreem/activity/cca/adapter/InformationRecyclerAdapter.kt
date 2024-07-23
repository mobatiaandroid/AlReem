package com.nas.alreem.activity.cca.adapter

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
import com.nas.alreem.activity.cca.model.CCaInformationList
import com.nas.alreem.activity.payment_history.PaymentWalletHistoryModel
import com.nas.alreem.constants.ConstantFunctions


class InformationRecyclerAdapter(
    mContext: Context, mListViewArray: ArrayList<CCaInformationList>
) :
    RecyclerView.Adapter<InformationRecyclerAdapter.MyViewHolder>() {
    private val mContext: Context? = mContext
    private val mnNewsLetterModelArrayList:ArrayList<CCaInformationList> = mListViewArray
    var dept: String? = null
    private val statusLayout: RelativeLayout? = null
    private val status: TextView? = null

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        //  ImageView imageIcon;
        var imageIcon: ImageView
        var pdfTitle: TextView
//        private val statusLayout: RelativeLayout
//        private val status: TextView

        init {

            imageIcon = view.findViewById<View>(R.id.imageIcon) as ImageView
            pdfTitle = view.findViewById<View>(R.id.pdfTitle) as TextView

        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_pdf_adapter_row_new, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.pdfTitle.text = mnNewsLetterModelArrayList[position].title
        holder.imageIcon.visibility = View.GONE
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount(): Int {
        Log.e("lostcardsize", mnNewsLetterModelArrayList.size.toString())
        return mnNewsLetterModelArrayList.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}



