package com.nas.alreem.activity.home.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.res.TypedArray
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.nas.alreem.R

class HomeListAdapter(
    private val context: Activity,
    private val title: Array<String>,
    private val imgid: TypedArray
) : ArrayAdapter<String>(context, R.layout.adapter_home, title) {

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("ViewHolder", "ResourceAsColor")
    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.adapter_home, null, true)
        val titleText = rowView.findViewById(R.id.title) as TextView
        val imageView = rowView.findViewById(R.id.icon) as ImageView
        titleText.text = title[position]
        imageView.setImageResource(imgid.getResourceId(position, 0))
        return rowView
    }
}

