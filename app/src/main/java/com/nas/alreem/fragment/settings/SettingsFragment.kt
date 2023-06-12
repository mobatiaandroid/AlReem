package com.nas.alreem.fragment.settings

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nas.alreem.BuildConfig
import com.nas.alreem.R
import com.nas.alreem.activity.primary.PrimaryComingUpActivity
import com.nas.alreem.activity.settings.TermsOfServiceActivity
import com.nas.alreem.activity.settings.TutorialActivity
import com.nas.alreem.activity.survey.SurveyListActivity
import com.nas.alreem.constants.*
import com.nas.alreem.fragment.settings.adapter.SettingsAdapter

class SettingsFragment : Fragment() {

    lateinit var mContext: Context
    lateinit var settingsRecycler: RecyclerView
    lateinit var progressDialogAdd: ProgressBar
    lateinit var titleTextView: TextView
    lateinit var versionText: TextView
    lateinit var settingsArrayList:ArrayList<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mContext=requireContext()
        initializeUI()
    }
    private fun initializeUI()
    {
        settingsArrayList= ArrayList()
        if (!PreferenceManager.getaccesstoken(mContext).equals(""))
        {
            settingsArrayList.add(mContext.resources.getString(R.string.change_app_settings))
            settingsArrayList.add(mContext.resources.getString(R.string.terms_of_service))
            settingsArrayList.add(mContext.resources.getString(R.string.email_us))
            settingsArrayList.add(mContext.resources.getString(R.string.tutorial))
            settingsArrayList.add(mContext.resources.getString(R.string.survey))
            settingsArrayList.add(mContext.resources.getString(R.string.change_password))
            settingsArrayList.add(mContext.resources.getString(R.string.delete_my_account))
            settingsArrayList.add(mContext.resources.getString(R.string.logout))
        }
        else
        {
            settingsArrayList.add(mContext.resources.getString(R.string.change_app_settings))
            settingsArrayList.add(mContext.resources.getString(R.string.terms_of_service))
            settingsArrayList.add(mContext.resources.getString(R.string.email_us))
            settingsArrayList.add(mContext.resources.getString(R.string.tutorial))
            settingsArrayList.add(mContext.resources.getString(R.string.logout))
        }
        settingsRecycler=requireView().findViewById(R.id.settingsRecycler)
        progressDialogAdd=requireView().findViewById(R.id.progressDialogAdd)
        titleTextView=requireView().findViewById(R.id.titleTextView)
        versionText=requireView().findViewById(R.id.versionText)
        versionText.text=BuildConfig.VERSION_NAME.toString()
        titleTextView.text=ConstantWords.settings
        var linearLayoutManager = LinearLayoutManager(mContext)
        settingsRecycler.layoutManager = linearLayoutManager
        settingsRecycler.itemAnimator = DefaultItemAnimator()
        var settingsAdapter= SettingsAdapter(settingsArrayList,mContext)
        settingsRecycler.adapter=settingsAdapter
        settingsRecycler.addOnItemClickListener(object : OnItemClickListener {
            @SuppressLint("SimpleDateFormat", "SetTextI18n")
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onItemClicked(position: Int, view: View)
            {

                if (PreferenceManager.getaccesstoken(mContext).equals(""))
                {
                    if (position==0)
                    {
                        val intent = Intent()
                        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                        val uri = Uri.fromParts("package", activity!!.packageName, null)
                        intent.data = uri
                        mContext.startActivity(intent)
                    }
                    else if (position==1)
                    {
                        val intent = Intent(mContext, TermsOfServiceActivity::class.java)
                        startActivity(intent)
                    }
                    else if (position==2)
                    {
                        val intent = Intent(Intent.ACTION_SEND)
                        val recipients = arrayOf("It.help@nasabudhabi.ae")
                        intent.putExtra(Intent.EXTRA_EMAIL, recipients)
                        intent.type = "text/html"
                        intent.setPackage("com.google.android.gm")
                        startActivity(Intent.createChooser(intent, "Send mail"))
                    }
                    else if (position==3)
                    {
                        val intent = Intent(mContext, TutorialActivity::class.java)
                        startActivity(intent)
//                        DialogFunctions.commonErrorAlertDialog("Coming Soon!","This Feature will be available shortly",
//                           mContext)
                    }
                    else if (position==4)
                    {
                        DialogFunctions.logoutDialog(mContext)
                    }
                }
                else
                {
                    if (position==0)
                    {
                        val intent = Intent()
                        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                        val uri = Uri.fromParts("package", activity!!.packageName, null)
                        intent.data = uri
                        mContext.startActivity(intent)
                    }
                    else if (position==1)
                    {
                        val intent = Intent(mContext, TermsOfServiceActivity::class.java)
                        startActivity(intent)
                    }
                    else if (position==2)
                    {
                        val intent = Intent(Intent.ACTION_SEND)
                        val recipients = arrayOf("It.help@nasabudhabi.ae")
                        intent.putExtra(Intent.EXTRA_EMAIL, recipients)
                        intent.type = "text/html"
                        intent.setPackage("com.google.android.gm")
                        startActivity(Intent.createChooser(intent, "Send mail"))
                    }
                    else if (position==3)
                    {
                        val intent = Intent(mContext, TutorialActivity::class.java)
                        startActivity(intent)
//                        DialogFunctions.commonErrorAlertDialog("Coming Soon!","This Feature will be available shortly",
//                            mContext)
                    }
                    else if(position==4)
                    {
                        //survey
                        val intent = Intent(mContext, SurveyListActivity::class.java)
                        startActivity(intent)
                    }
                    else if (position==5)
                    {
                        DialogFunctions.changePasswordDialog(mContext)
                    }
                    else if (position==6)
                    {
                        DialogFunctions.deleteAccountDialog(mContext)
                    }
                    else if (position==7)
                    {
                        DialogFunctions.logoutDialog(mContext)
                    }
                }

            }


        })

    }

}