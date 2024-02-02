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
import com.nas.alreem.constants.PreferenceManager

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
        val badge = rowView.findViewById<View>(R.id.badge) as TextView
        titleText.text = title[position]
        imageView.setImageResource(imgid.getResourceId(position, 0))

        if (position == 3) {
            if (!PreferenceManager.getCalenderhomeBadge(context)
                    .equals("0") && !PreferenceManager.getCalenderEditedhomeBadge(context)
                    .equals("0")
            ) {
                badge.setVisibility(View.VISIBLE)
                badge.setText(PreferenceManager.getCalenderhomeBadge(context))
               badge.setBackgroundResource(R.drawable.shape_circle_red)
            } else if (PreferenceManager.getCalenderhomeBadge(context)
                    .equals("0") && !PreferenceManager.getCalenderEditedhomeBadge(context)
                    .equals("0")
            ) {
                badge.setVisibility(View.VISIBLE)
                badge.setText(PreferenceManager.getCalenderEditedhomeBadge(context))
                badge.setBackgroundResource(R.drawable.shape_circle_navy)
            } else if (!PreferenceManager.getCalenderhomeBadge(context)
                    .equals("0") && PreferenceManager.getCalenderEditedhomeBadge(context)
                    .equals("0")
            ) {
               badge.setVisibility(View.VISIBLE)
                badge.setText(PreferenceManager.getCalenderhomeBadge(context))
                badge.setBackgroundResource(R.drawable.shape_circle_red)
            } else {
               badge.setVisibility(View.GONE)
            }
        } else if (position == 2) {
            if (!PreferenceManager.getNotificationBadge(context)
                    .equals("0") && !PreferenceManager.getNotificationEditedBadge(context)
                    .equals("0")
            ) {
               badge.setVisibility(View.VISIBLE)
                badge.setText(PreferenceManager.getNotificationBadge(context))
               badge.setBackgroundResource(R.drawable.shape_circle_red)
            } else if (PreferenceManager.getNotificationBadge(context)
                    .equals("0") && !PreferenceManager.getNotificationEditedBadge(context)
                    .equals("0")
            ) {
               badge.setVisibility(View.VISIBLE)
               badge.setText(PreferenceManager.getNotificationEditedBadge(context))
               badge.setBackgroundResource(R.drawable.shape_circle_navy)
            } else if (!PreferenceManager.getNotificationBadge(context)
                    .equals("0") && PreferenceManager.getNotificationEditedBadge(context)
                    .equals("0")
            ) {
               badge.setVisibility(View.VISIBLE)
               badge.setText(PreferenceManager.getNotificationBadge(context))
               badge.setBackgroundResource(R.drawable.shape_circle_red)
            } else {
                badge.setVisibility(View.GONE)
            }
        } else if (position == 4) {
            if (!PreferenceManager.getNoticeBadge(context)
                    .equals("0") && !PreferenceManager.getNoticeEditedBadge(context)
                    .equals("0")
            ) {
                badge.setVisibility(View.VISIBLE)
                badge.setText(PreferenceManager.getNoticeBadge(context))
               badge.setBackgroundResource(R.drawable.shape_circle_red)
            } else if (PreferenceManager.getNoticeBadge(context)
                    .equals("0") && !PreferenceManager.getNoticeEditedBadge(context)
                    .equals("0")
            ) {
               badge.setVisibility(View.VISIBLE)
               badge.setText(PreferenceManager.getNoticeEditedBadge(context))
                badge.setBackgroundResource(R.drawable.shape_circle_navy)
            } else if (!PreferenceManager.getNoticeBadge(context)
                    .equals("0") && PreferenceManager.getNoticeEditedBadge(context)
                    .equals("0")
            ) {
               badge.setVisibility(View.VISIBLE)
                badge.setText(PreferenceManager.getNoticeBadge(context))
                badge.setBackgroundResource(R.drawable.shape_circle_red)
            } else {
               badge.setVisibility(View.GONE)
            }
        }   else if (position == 14) {
            System.out.println(
                "report badge" + PreferenceManager.getReportsBadge(context) + "report edited badge" + PreferenceManager.getReportsEditedBadge(
                    context
                )
            )
            if (!PreferenceManager.getReportsBadge(context)
                    .equals("0") && !PreferenceManager.getReportsEditedBadge(context)
                    .equals("0")
            ) {
              badge.setVisibility(View.VISIBLE)
               badge.setText(PreferenceManager.getReportsBadge(context))
               badge.setBackgroundResource(R.drawable.shape_circle_red)
            } else if (PreferenceManager.getReportsBadge(context)
                    .equals("0") && !PreferenceManager.getReportsEditedBadge(context)
                    .equals("0")
            ) {
               badge.setVisibility(View.VISIBLE)
               badge.setText(PreferenceManager.getReportsEditedBadge(context))
               badge.setBackgroundResource(R.drawable.shape_circle_navy)
            } else if (!PreferenceManager.getReportsBadge(context)
                    .equals("0") && PreferenceManager.getReportsEditedBadge(context)
                    .equals("0")
            ) {
               badge.setVisibility(View.VISIBLE)
               badge.setText(PreferenceManager.getReportsBadge(context))
               badge.setBackgroundResource(R.drawable.shape_circle_red)
            } else if (PreferenceManager.getReportsBadge(context)
                    .equals("0") && PreferenceManager.getReportsEditedBadge(context)
                    .equals("0")
            ) {
               badge.setVisibility(View.GONE)
            } else {
               badge.setVisibility(View.GONE)
            }
        } else if (position == 17) {
            if (!PreferenceManager.getCcaBadge(context)
                    .equals("0") && !PreferenceManager.getCcaEditedBadge(context)
                    .equals("0")
            ) {
              badge.setVisibility(View.VISIBLE)
               badge.setText(PreferenceManager.getCcaBadge(context))
               badge.setBackgroundResource(R.drawable.shape_circle_red)
            } else if (PreferenceManager.getCcaBadge(context)
                    .equals("0") && !PreferenceManager.getCcaEditedBadge(context)
                    .equals("0")
            ) {
                badge.setVisibility(View.VISIBLE)
               badge.setText(PreferenceManager.getCcaEditedBadge(context))
               badge.setBackgroundResource(R.drawable.shape_circle_navy)
            } else if (!PreferenceManager.getCcaBadge(context)
                    .equals("0") && PreferenceManager.getCcaEditedBadge(context)
                    .equals("0")
            ) {
                badge.setVisibility(View.VISIBLE)
                badge.setText(PreferenceManager.getCcaBadge(context))
               badge.setBackgroundResource(R.drawable.shape_circle_red)
            } else {
                badge.setVisibility(View.GONE)
            }
        } else if (position == 5) {
            if (!PreferenceManager.getPaymentitem_badge(context)
                    .equals("0") && !PreferenceManager.getPaymentitem_edit_badge(context)
                    .equals("0")
            ) {
                badge.setVisibility(View.VISIBLE)
                badge.setText(PreferenceManager.getPaymentitem_badge(context))
                badge.setBackgroundResource(R.drawable.shape_circle_red)
            } else if (PreferenceManager.getPaymentitem_badge(context)
                    .equals("0") && !PreferenceManager.getPaymentitem_edit_badge(context)
                    .equals("0")
            ) {
               badge.setVisibility(View.VISIBLE)
                badge.setText(PreferenceManager.getPaymentitem_edit_badge(context))
                badge.setBackgroundResource(R.drawable.shape_circle_navy)
            } else if (!PreferenceManager.getPaymentitem_badge(context)
                    .equals("0") && PreferenceManager.getPaymentitem_edit_badge(context)
                    .equals("0")
            ) {
                badge.setVisibility(View.VISIBLE)
                badge.setText(PreferenceManager.getPaymentitem_badge(context))
               badge.setBackgroundResource(R.drawable.shape_circle_red)
            } else {
               badge.setVisibility(View.GONE)
            }
        } else {
            badge.setVisibility(View.GONE)
        }
        return rowView
    }
}

