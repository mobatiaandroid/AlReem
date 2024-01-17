package com.nas.alreem.activity.shop.Adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.nas.alreem.R
import com.nas.alreem.activity.shop.model.Instrument


class InstrumentListAdapter(activity: Activity, arrayList: ArrayList<Instrument>) :
    RecyclerView.Adapter<InstrumentListAdapter.MyViewHolder>() {
    private val context: Context
    private val instrumentsList: ArrayList<Instrument>

    init {
        instrumentsList = arrayList
        context = activity
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_instrument, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.instrumentNameTextView.setText(instrumentsList[position].getInstrumentName())
        if (instrumentsList[position].getInstrumentSelected() === 1) {
            holder.selectedImage.setImageResource(R.drawable.approve_new)
        } else {
            holder.selectedImage.setImageResource(R.drawable.arrowright)
        }
    }

    override fun getItemCount(): Int {
        return instrumentsList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var instrumentNameTextView: TextView
        var instrumentView: ConstraintLayout
        var selectedImage: ImageView

        init {
            instrumentNameTextView = itemView.findViewById<TextView>(R.id.instrumentNameTextView)
            instrumentView = itemView.findViewById<ConstraintLayout>(R.id.instrumentButton)
            selectedImage = itemView.findViewById<ImageView>(R.id.selectedImage)
        }
    }
}
