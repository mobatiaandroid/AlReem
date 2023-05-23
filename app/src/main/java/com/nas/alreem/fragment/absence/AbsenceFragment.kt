package com.nas.alreem.fragment.absence

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nas.alreem.R
import com.nas.alreem.constants.*


class AbsenceFragment  : Fragment() {

    lateinit var mContext: Context
    lateinit var mEarlyYearsRecycler: RecyclerView
    lateinit var titleTextView: TextView
    lateinit var progressDialogAdd: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.frament_early_years, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mContext=requireContext()
        initializeUI()
    }
    private fun initializeUI()
    {
        mEarlyYearsRecycler=requireView().findViewById(R.id.mEarlyYearsRecycler)
        progressDialogAdd=requireView().findViewById(R.id.progressDialogAdd)
        titleTextView=requireView().findViewById(R.id.titleTextView)
        titleTextView.text= ConstantWords.early_years
        var linearLayoutManager = LinearLayoutManager(mContext)
        mEarlyYearsRecycler.layoutManager = linearLayoutManager
        mEarlyYearsRecycler.itemAnimator = DefaultItemAnimator()



    }

}