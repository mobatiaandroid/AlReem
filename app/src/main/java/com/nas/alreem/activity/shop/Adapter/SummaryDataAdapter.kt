package com.nas.alreem.activity.shop.Adapter

import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nas.alreem.R
import com.nas.alreem.activity.shop.model.InstrumentDataModel


class SummaryDataAdapter(instrumentDataList: ArrayList<InstrumentDataModel>) :
    RecyclerView.Adapter<SummaryDataAdapter.SummaryViewHolder>() {
    private val instrumentDataList: ArrayList<InstrumentDataModel>
    private val selectedItems: SparseBooleanArray

    init {
        this.instrumentDataList = instrumentDataList
        selectedItems = SparseBooleanArray()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SummaryViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_instrument_cart, parent, false)
        return SummaryViewHolder(view)
    }

    override fun onBindViewHolder(holder: SummaryViewHolder, position: Int) {
        val instrumentData: InstrumentDataModel = instrumentDataList[position]
        holder.bind(instrumentData, position)
    }

    override fun getItemCount(): Int {
        return instrumentDataList.size
    }

    inner class SummaryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val instrumentNameTextView: TextView
        private val cartItemContainer: LinearLayout

        init {
            instrumentNameTextView = itemView.findViewById<TextView>(R.id.instrumentNameTextView)
            cartItemContainer = itemView.findViewById<LinearLayout>(R.id.cartItemContainer)
        }

        fun bind(instrumentData: InstrumentDataModel, position: Int) {
            instrumentNameTextView.setText(instrumentData.getInstrumentName())
            cartItemContainer.removeAllViews()
            for (cartData in instrumentData.getOrderData()) {
                val cartItemView: View = LayoutInflater.from(itemView.context)
                    .inflate(R.layout.item_cart_data, cartItemContainer, false)
                val lessonDetailsTextView =
                    cartItemView.findViewById<TextView>(R.id.lessonDetailsTextView)
                val amountTextView = cartItemView.findViewById<TextView>(R.id.amountTextView)
                val termTextView = cartItemView.findViewById<TextView>(R.id.termTextView)
                termTextView.setText(cartData.getTermName())
                lessonDetailsTextView.setText(cartData.getLessonName())
                amountTextView.setText(cartData.getTotalAmount() + " AED")
                cartItemContainer.addView(cartItemView)
            }
        }
    }
}
