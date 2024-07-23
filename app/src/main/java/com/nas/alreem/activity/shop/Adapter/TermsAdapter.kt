package com.nas.alreem.activity.shop.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nas.alreem.R
import com.nas.alreem.activity.shop.model.Instrument
import com.nas.alreem.activity.shop.model.Term
import java.util.ArrayList


class TermsAdapter(var context: Context, instrument: Instrument) :
    RecyclerView.Adapter<TermsAdapter.MyViewHolder>() {
    var instrument: Instrument

    init {
        this.instrument = instrument
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_term_header, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.noSlotsTextView.visibility = View.GONE
        val term: Term = instrument.getTermData().get(position)
        holder.bind(term)
        if (term.getRemainingSlotCount().equals("0")) {
            holder.itemView.alpha = 0.5f
            holder.itemView.isClickable = false
            holder.itemView.isEnabled = false
        }
        if (term.getRemainingSlotCount().toInt() > 0) {
            holder.noSlotsTextView.visibility = View.GONE
        } else {
            holder.noSlotsTextView.visibility = View.VISIBLE
        }
    }

    override fun getItemCount(): Int {
        return instrument.getTermData().size
    }

    val terms: ArrayList<Term>?
        get() = instrument.getTermData()

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        public val termNameTextView: TextView
        public val noSlotsTextView: TextView
        private val lessonsRecyclerView: RecyclerView

        init {
            termNameTextView = itemView.findViewById<TextView>(R.id.termNameTextView)
            lessonsRecyclerView = itemView.findViewById<RecyclerView>(R.id.lessonsRecyclerView)
            noSlotsTextView = itemView.findViewById<TextView>(R.id.noSlotAvailable)
        }

        fun bind(term: Term) {
            var lessonAvailable = true
            lessonAvailable = if (term.getRemainingSlotCount().toInt() > 0) {
                true
            } else {
                false
            }
            term.getRemainingSlotCount().toInt()
            termNameTextView.setText(term.getTermName())
            lessonsRecyclerView.layoutManager = LinearLayoutManager(context)
            lessonsRecyclerView.adapter =
                LessonAdapter(context, term.getLessonData(), lessonAvailable)
        }
    }
}
