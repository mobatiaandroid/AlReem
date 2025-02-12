package com.nas.alreem.constants

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.ProgressBar
import android.widget.Toast
import com.nas.alreem.appcontroller.AppController


open class MyBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        AppController.reciver="1"
        Toast.makeText(context, "Download completed!", Toast.LENGTH_SHORT).show()
        PDFViewerActivity().receiveroption()

    }
}
