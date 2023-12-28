package com.nas.alreem.activity.home

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.nas.alreem.R
import com.nas.alreem.activity.home.adapter.HomeListAdapter
import com.nas.alreem.constants.ConstantFunctions
import com.nas.alreem.constants.DialogFunctions
import com.nas.alreem.constants.MyDragShadowBuilder
import com.nas.alreem.constants.PreferenceManager
import com.nas.alreem.constants.WebLinkActivity
import com.nas.alreem.fragment.about_us.AboutUsFragment
import com.nas.alreem.fragment.absence.AbsenceFragment
import com.nas.alreem.fragment.calendar.CalendarFragment
import com.nas.alreem.fragment.canteen.CanteenFragment
import com.nas.alreem.fragment.cca.CCAFragment
import com.nas.alreem.fragment.communication.CommunicationFragment
import com.nas.alreem.fragment.contact_us.ContactUsFragment
import com.nas.alreem.fragment.early_years.EarlyYearsFragment
import com.nas.alreem.fragment.gallery.GalleryFragment
import com.nas.alreem.fragment.home.HomeFragment

import com.nas.alreem.fragment.home.mContext
import com.nas.alreem.fragment.home.re_enrollment.EnrollmentFormResponseModel
import com.nas.alreem.fragment.home.re_enrollment.EnrollmentHelpResponseModel
import com.nas.alreem.fragment.home.re_enrollment.EnrollmentSaveResponseModel
import com.nas.alreem.fragment.home.re_enrollment.EnrollmentStatusResponseModel
import com.nas.alreem.fragment.home.re_enrollment.ReEnrollmentFormStudentModel
import com.nas.alreem.fragment.home.re_enrollment.StudentEnrollList
import com.nas.alreem.fragment.home.studentReEnrollList

import com.nas.alreem.fragment.notifications.NotificationFragment
import com.nas.alreem.fragment.parent_meetings.ParentMeetingsFragment
import com.nas.alreem.fragment.parents_essentials.ParentsEssentialFragment
import com.nas.alreem.fragment.payments.PaymentFragment
import com.nas.alreem.fragment.performing_arts.PerformingArtsFragment
import com.nas.alreem.fragment.permission_slip.PermissionSlipFragment
import com.nas.alreem.fragment.primary.PrimaryFragment
import com.nas.alreem.fragment.re_entrollment.ReEnrollAdapter
import com.nas.alreem.fragment.re_entrollment.model.ReEnrollSubmitModel
import com.nas.alreem.fragment.secondary.SecondaryFragment
import com.nas.alreem.fragment.settings.SettingsFragment
import com.nas.alreem.recyclermanager.RecyclerItemListener
import com.nas.alreem.rest.ApiClient
import com.nas.alreem.rest.ApiInterface
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.util.Calendar

class HomeActivity : AppCompatActivity(), AdapterView.OnItemLongClickListener {

    val manager = supportFragmentManager
    lateinit var navigation_menu: ImageView
    lateinit var settings_icon: ImageView
    lateinit var shadowBuilder: MyDragShadowBuilder
    lateinit var context: Context
    lateinit var mActivity: Activity
    var selectedItem: String? = null

    lateinit var clipData: ClipData
    lateinit var mListItemArray: Array<String>
    var mListImgArray: TypedArray? = null
    lateinit var linear_layout: LinearLayout
    lateinit var drawer_layout: DrawerLayout
    lateinit var toolbar: Toolbar
    lateinit var logoClickImgView: ImageView
    lateinit var homelist: ListView
    var mFragment: Fragment? = null
    var sPosition: Int = 0
    lateinit var reEnrollRecycler: RecyclerView
    lateinit var text_content: TextView
    lateinit var text_dialog: TextView
    lateinit var studentEnrollList: ArrayList<StudentEnrollList>
    lateinit var reEnrollSaveArray: ArrayList<ReEnrollSubmitModel>
    lateinit var studentList :ArrayList<StudentEnrollList>
    private val PERMISSION_CALLBACK_CONSTANT_CALENDAR = 1
    private val REQUEST_PERMISSION_CALENDAR = 101
    var permissionsRequiredCalendar = arrayOf(
        Manifest.permission.READ_CALENDAR,
        Manifest.permission.WRITE_CALENDAR
    )
    lateinit var calendarPermissionStatus: SharedPreferences
    private var calendarToSettings = false
    lateinit var requestPermissionLauncher: ActivityResultLauncher<String>
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activtiy_home)
        Intent.FLAG_ACTIVITY_CLEAR_TASK
        initializeUI()
        calendarPermissionStatus =
            getSharedPreferences("calendarPermissionStatus", Context.MODE_PRIVATE)

        showfragmenthome()

    }

    fun showfragmenthome() {
        val transaction = manager.beginTransaction()
        val fragment = HomeFragment()
        transaction.replace(R.id.fragment_holder, fragment)
        transaction.commit()
    }

    @SuppressLint("Recycle", "WrongViewCast")
    @RequiresApi(Build.VERSION_CODES.M)
    private fun initializeUI() {
        context = this
        mActivity = this
        homelist = findViewById(R.id.homelistview)
        drawer_layout = findViewById(R.id.drawer_layout)
        linear_layout = findViewById(R.id.linear_layout)
        var downarrow = findViewById<ImageView>(R.id.downarrow)


        mListItemArray =
            applicationContext.resources.getStringArray(R.array.navigation_item_names)
        mListImgArray =
            applicationContext.resources.obtainTypedArray(R.array.navigation_item_icons)
        val width = (resources.displayMetrics.widthPixels / 1.7).toInt()
        val params = linear_layout
            .layoutParams as DrawerLayout.LayoutParams
        params.width = width
        linear_layout.layoutParams = params
        val myListAdapter = HomeListAdapter(this, mListItemArray, mListImgArray!!)
        homelist.adapter = myListAdapter
        homelist.onItemLongClickListener = this
        requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission(),
            ActivityResultCallback<Boolean> { result ->
                Log.e("result", result.toString())
                if (result) {
                    // PERMISSION GRANTED
                    Log.e("result", result.toString())
                    // Toast.makeText(mContext, String.valueOf(result), Toast.LENGTH_SHORT).show();
                } else {
                    // PERMISSION NOT GRANTED
                    Log.e("denied", result.toString())
                    val snackbar = Snackbar.make(
                        drawer_layout,
                        "Notification Permission Denied",
                        Snackbar.LENGTH_LONG
                    )
                        .setAction("Settings") {
                            val intent = Intent()
                            intent.action = "android.settings.APP_NOTIFICATION_SETTINGS"
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            intent.putExtra("app_package", context.packageName)
                            intent.putExtra("app_uid", context.applicationInfo.uid)
                            intent.putExtra(
                                "android.provider.extra.APP_PACKAGE",
                                context.packageName
                            )
                            startActivity(intent)
                        }
                    snackbar.setActionTextColor(Color.RED)

                    val view = snackbar.view
                    val tv = view
                        .findViewById<View>(com.google.android.material.R.id.snackbar_text) as TextView
                    tv.setTextColor(Color.WHITE)
                    snackbar.show()


                    // Toast.makeText(mContext, String.valueOf(result), Toast.LENGTH_SHORT).show();
                }
            }
        )
        askForNotificationPermission()

        homelist.setOnItemClickListener { adapterView, view, position, id ->
            val itemAtPos = adapterView.getItemAtPosition(position)
            val itemIdAtPos = adapterView.getItemIdAtPosition(position)

            if (PreferenceManager.getaccesstoken(context).equals(""))
            {
              when(position)
              {
                  0->
                  {
                      mFragment = HomeFragment()
                      replaceFragmentsSelected(position)
                  }
                  1->
                  {
                      //Notification
                      DialogFunctions.commonErrorAlertDialog(context.resources.getString(R.string.alert),context.resources.getString(R.string.feature_only_for_registered_user),context)
                  }
                  2->
                  {
                      //Calendar
                      DialogFunctions.commonErrorAlertDialog(context.resources.getString(R.string.alert),context.resources.getString(R.string.feature_only_for_registered_user),context)

                  }
                  3->
                  {
                      //payment
                      //absence
                      DialogFunctions.commonErrorAlertDialog(context.resources.getString(R.string.alert),context.resources.getString(R.string.feature_only_for_registered_user),context)

                  }
                  4->
                  {
                      //lunchbox
                      //payment
                      DialogFunctions.commonErrorAlertDialog(context.resources.getString(R.string.alert),context.resources.getString(R.string.feature_only_for_registered_user),context)

                  }
                 5->
                  {
                      //parents essential
                      //lunchbox
                      DialogFunctions.commonErrorAlertDialog(context.resources.getString(R.string.alert),context.resources.getString(R.string.feature_only_for_registered_user),context)

                  }
                  6->
                  {
                      //parents essential
                      DialogFunctions.commonErrorAlertDialog(context.resources.getString(R.string.alert),context.resources.getString(R.string.feature_only_for_registered_user),context)

                  }
                  7->
                  {
                      //Early years
                      mFragment = EarlyYearsFragment()
                      replaceFragmentsSelected(position)
                  }
                  8->
                  {
                      //Primary
                      mFragment = PrimaryFragment()
                      replaceFragmentsSelected(position)
                  }
                  9->
                  {
                      //Secondary
                      mFragment = SecondaryFragment()
                      replaceFragmentsSelected(position)
                  }
                  10->
                  {
                      //Gallery
                      //Enrichment
                      DialogFunctions.commonErrorAlertDialog(context.resources.getString(R.string.alert),context.resources.getString(R.string.feature_only_for_registered_user),context)

                  }
                  11->
                  {
                      //Gallery
                      //parent meetings
                      DialogFunctions.commonErrorAlertDialog(context.resources.getString(R.string.alert),context.resources.getString(R.string.feature_only_for_registered_user),context)

                  }
                  12->
                  {
                      //Gallery
                      //permission forms
                      DialogFunctions.commonErrorAlertDialog(context.resources.getString(R.string.alert),context.resources.getString(R.string.feature_only_for_registered_user),context)

                  }
                  13->
                  {
                      //Gallery
                      DialogFunctions.commonErrorAlertDialog(context.resources.getString(R.string.alert),context.resources.getString(R.string.feature_only_for_registered_user),context)

                  }
                  14->
                  {
                      //About Us



                      if (ActivityCompat.checkSelfPermission(
                              context,
                              Manifest.permission.ACCESS_FINE_LOCATION
                          ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                              context,
                              Manifest.permission.ACCESS_COARSE_LOCATION
                          ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                              context,
                              Manifest.permission.CALL_PHONE
                          ) != PackageManager.PERMISSION_GRANTED
                      ) {
                          checkPermission()


                      }
                      else
                      {
                          mFragment = ContactUsFragment()
                          replaceFragmentsSelected(position)
                      }
                  }
                  15->
                  {
                      // Contact Us
                      mFragment = AboutUsFragment()
                      replaceFragmentsSelected(position)
                  }
              }
            }
            else
            {
                when(position)
                {
                    0->
                    {
                        // about us
                       // reEnroll(mContext)
                      //  replaceFragmentsSelected(position)
                        mFragment = PerformingArtsFragment()
                        replaceFragmentsSelected(position)
                    }
                    1->
                    {
                        //Notification
                       // mFragment = NotificationFragment()
                       // replaceFragmentsSelected(position)
                        mFragment = CommunicationFragment()
                        replaceFragmentsSelected(position)

                    }
                    2->
                    {
                        //Calendar
                        mFragment = CalendarFragment()

                        if (Build.VERSION.SDK_INT < 23) {
                            //Do not need to check the permission
                            replaceFragmentsSelected(position)
                        } else {
                            if (ActivityCompat.checkSelfPermission(
                                    mActivity,
                                    permissionsRequiredCalendar.get(0)
                                ) != PackageManager.PERMISSION_GRANTED
                                || ActivityCompat.checkSelfPermission(
                                    mActivity,
                                    permissionsRequiredCalendar.get(1)
                                ) != PackageManager.PERMISSION_GRANTED
                            ) {
                                if (ActivityCompat.shouldShowRequestPermissionRationale(
                                        mActivity,
                                        permissionsRequiredCalendar.get(0)
                                    )
                                    || ActivityCompat.shouldShowRequestPermissionRationale(
                                        mActivity,
                                        permissionsRequiredCalendar.get(1)
                                    )
                                ) {
                                    //Show information about why you need the permission
                                    val builder = AlertDialog.Builder(mActivity)
                                    builder.setTitle("Need Calendar Permission")
                                    builder.setMessage("This module needs Calendar permissions.")
                                    builder.setCancelable(false)
                                    builder.setPositiveButton(
                                        "Grant"
                                    ) { dialog, which ->
                                        dialog.cancel()
                                        ActivityCompat.requestPermissions(
                                            mActivity,
                                            permissionsRequiredCalendar,
                                            HomeActivity().PERMISSION_CALLBACK_CONSTANT_CALENDAR
                                        )
                                    }
                                    builder.setNegativeButton(
                                        "Cancel"
                                    ) { dialog, which -> dialog.cancel() }
                                    builder.show()
                                } else if (calendarPermissionStatus.getBoolean(
                                        permissionsRequiredCalendar.get(0),
                                        false
                                    )
                                ) {
                                    //Previously Permission Request was cancelled with 'Dont Ask Again',
                                    // Redirect to Settings after showing information about why you need the permission
                                    println("Permission0")
                                    val builder = AlertDialog.Builder(mActivity)
                                    builder.setTitle("Need Calendar Permission")
                                    builder.setMessage("This module needs Calendar permissions.")
                                    builder.setCancelable(false)
                                    builder.setPositiveButton(
                                        "Grant"
                                    ) { dialog, which ->
                                        dialog.cancel()
                                        calendarToSettings = true
                                        val intent =
                                            Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                        val uri = Uri.fromParts(
                                            "package",
                                            mActivity.getPackageName(),
                                            null
                                        )
                                        intent.data = uri
                                        startActivityForResult(
                                            intent,
                                            HomeActivity().REQUEST_PERMISSION_CALENDAR
                                        )
                                        Toast.makeText(
                                            mContext,
                                            "Go to settings and grant access to calendar",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                    builder.setNegativeButton(
                                        "Cancel"
                                    ) { dialog, which ->
                                        dialog.cancel()
                                        calendarToSettings = false
                                    }
                                    builder.show()
                                } else if (calendarPermissionStatus.getBoolean(
                                        permissionsRequiredCalendar.get(1),
                                        false
                                    )
                                ) {
                                    //Previously Permission Request was cancelled with 'Dont Ask Again',
                                    // Redirect to Settings after showing information about why you need the permission
                                    println("Permission1")
                                    val builder = AlertDialog.Builder(mActivity)
                                    builder.setTitle("Need Calendar Permission")
                                    builder.setMessage("This module needs Calendar permissions.")
                                    builder.setCancelable(false)
                                    builder.setPositiveButton(
                                        "Grant"
                                    ) { dialog, which ->
                                        dialog.cancel()
                                        calendarToSettings = true
                                        val intent =
                                            Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                        val uri = Uri.fromParts(
                                            "package",
                                            mActivity.getPackageName(),
                                            null
                                        )
                                        intent.data = uri
                                        startActivityForResult(
                                            intent,
                                            HomeActivity().REQUEST_PERMISSION_CALENDAR
                                        )
                                        Toast.makeText(
                                            mContext,
                                            "Go to settings and grant access to calendar",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                    builder.setNegativeButton(
                                        "Cancel"
                                    ) { dialog, which ->
                                        dialog.cancel()
                                        calendarToSettings = false
                                    }
                                    builder.show()
                                } else {
                                    println("Permission3")

                                    //just request the permission
//                        ActivityCompat.requestPermissions(mActivity, permissionsRequired, PERMISSION_CALLBACK_CONSTANT_CALENDAR);
                                    ActivityCompat.requestPermissions(
                                        mActivity,
                                        permissionsRequiredCalendar,
                                       HomeActivity().PERMISSION_CALLBACK_CONSTANT_CALENDAR
                                    )
                                }
                                val editor: SharedPreferences.Editor =
                                    calendarPermissionStatus.edit()
                                editor.putBoolean(permissionsRequiredCalendar.get(0), true)
                                editor.commit()
                            } else {
                                replaceFragmentsSelected(position)
                            }
                        }
                    }
                    3->
                    {
                        //About Us
                        mFragment = PaymentFragment()
                        replaceFragmentsSelected(position)


                    }
                    4->
                    {
                        //payment
                        mFragment = CanteenFragment()
                        replaceFragmentsSelected(position)
                    }
                    5->
                    {

                        mFragment = ParentsEssentialFragment()
                        replaceFragmentsSelected(position)
//                        DialogFunctions.commonErrorAlertDialog("Coming Soon!","This Feature will be available shortly",
//                            mContext
//                        )
                    }
                    6->
                    {
                        PreferenceManager.setStudentID(mContext,"")
                        mFragment = AbsenceFragment()
                        replaceFragmentsSelected(position)
//                        DialogFunctions.commonErrorAlertDialog("Coming Soon!","This Feature will be available shortly",
//                            mContext
//                        )
                    }
                    7->
                    {
                        //Early years
                        mFragment = EarlyYearsFragment()
                        replaceFragmentsSelected(position)
                    }
                    8->
                    {
                        //Primary
                        mFragment = PrimaryFragment()
                        replaceFragmentsSelected(position)
                    }
                    9->
                    {
                        //Secondary
                        mFragment = SecondaryFragment()
                        replaceFragmentsSelected(position)
                    }
                    10->
                    {
                        //Secondary
                        PreferenceManager.setStudentID(context,"")
                        mFragment = PermissionSlipFragment()
                        replaceFragmentsSelected(position)


                    }
                    11->
                    {
                        //About Us
                        mFragment = CCAFragment()
                        replaceFragmentsSelected(position)

                    }

                    12->
                    {
                        //About Us
                        mFragment = ParentMeetingsFragment()
                        replaceFragmentsSelected(position)
                    }
                    13->
                    {
                        //Gallery
                        mFragment = GalleryFragment()
                        replaceFragmentsSelected(position)
                    }
                    14->
                    {
                        //About Us


                        if (ActivityCompat.checkSelfPermission(
                                context,
                                Manifest.permission.ACCESS_FINE_LOCATION
                            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                context,
                                Manifest.permission.ACCESS_COARSE_LOCATION
                            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                context,
                                Manifest.permission.CALL_PHONE
                            ) != PackageManager.PERMISSION_GRANTED
                        ) {
                            checkPermission()


                        } else {
                            mFragment = ContactUsFragment()
                            replaceFragmentsSelected(position)
                        }
                    }
                    15->
                    {
                        // Contact Us
                        mFragment = AboutUsFragment()
                        replaceFragmentsSelected(position)
                    }




                }
            }



        }

        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setCustomView(R.layout.custom_titlebar)
        supportActionBar!!.elevation = 0F

        var view = supportActionBar!!.customView
        toolbar = view.parent as Toolbar
        toolbar.setBackgroundColor(resources.getColor(R.color.white))
        toolbar.setContentInsetsAbsolute(0, 0)

        navigation_menu = view.findViewById(R.id.action_bar_back)
        settings_icon = view.findViewById(R.id.action_bar_forward)
        logoClickImgView = view.findViewById(R.id.logoClickImgView)
        settings_icon.visibility = View.VISIBLE
        homelist.setBackgroundColor(getColor(R.color.split_bg))
        homelist.setOnScrollListener(object : AbsListView.OnScrollListener {
            override fun onScrollStateChanged(view: AbsListView, scrollState: Int) {}
            override fun onScroll(
                view: AbsListView,
                firstVisibleItem: Int,
                visibleItemCount: Int,
                totalItemCount: Int
            ) {
                if (view.id == homelist.id) {
                    val currentFirstVisibleItem: Int = homelist.lastVisiblePosition

                    if (currentFirstVisibleItem == totalItemCount - 1) {
                        downarrow.visibility = View.INVISIBLE
                    } else {
                        downarrow.visibility = View.VISIBLE
                    }
                }
            }
        })
        mListItemArray = context.resources.getStringArray(R.array.navigation_item_names)
        mListImgArray = context.resources.obtainTypedArray(R.array.navigation_item_icons)
        navigation_menu.setOnClickListener {
            if (drawer_layout.isDrawerOpen(linear_layout)) {
                drawer_layout.closeDrawer(linear_layout)
            } else {
                drawer_layout.openDrawer(linear_layout)
            }
        }

        logoClickImgView.setOnClickListener(View.OnClickListener {
            settings_icon.visibility = View.VISIBLE
            if (drawer_layout.isDrawerOpen(linear_layout)) {
                drawer_layout.closeDrawer(linear_layout)
            }
            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            mFragment = HomeFragment()
            supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        })



        settings_icon.setOnClickListener {
            val fm = supportFragmentManager
            val currentFragment =
                fm.findFragmentById(R.id.fragment_holder)
            if (drawer_layout.isDrawerOpen(linear_layout)) {
                drawer_layout.closeDrawer(linear_layout)
            }
            mFragment = SettingsFragment()
            if (mFragment != null) {
                val fragmentManager =
                    supportFragmentManager
                fragmentManager.beginTransaction()
                    .add(R.id.fragment_holder, mFragment!!, "Settings")
                    .addToBackStack("Settings").commit()

                supportActionBar!!.setTitle(R.string.null_value)
                settings_icon.visibility = View.INVISIBLE

            }
        }

    }


    override fun onItemLongClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long): Boolean
    {

        shadowBuilder = MyDragShadowBuilder(view)
        sPosition = position
        val selecteditem = parent?.getItemIdAtPosition(position)
        view?.setBackgroundColor(Color.parseColor("#47C2D1"))
        val data = ClipData.newPlainText("", "")
        view?.startDrag(data, shadowBuilder, view, 0)
        view!!.visibility = View.VISIBLE
        drawer_layout.closeDrawer(linear_layout)
        return false
    }

    private fun replaceFragmentsSelected(position: Int) {
        settings_icon.visibility = View.VISIBLE
        if (mFragment != null) {
            val fragmentManager = supportFragmentManager
            fragmentManager
                .beginTransaction()
                .replace(
                    R.id.fragment_holder, mFragment!!,
                    mListItemArray[position]
                )
                .addToBackStack(mListItemArray[position]).commitAllowingStateLoss()
            homelist.setItemChecked(position, true)
            homelist.setSelection(position)
            supportActionBar!!.setTitle(R.string.null_value)
            if (drawer_layout.isDrawerOpen(linear_layout)) {
                drawer_layout.closeDrawer(linear_layout)
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (drawer_layout.isDrawerOpen(linear_layout)) {
            drawer_layout.closeDrawer(linear_layout)
        }
        settings_icon.visibility = View.VISIBLE

    }

    fun fragmentIntent(mFragment: Fragment?) {
        if (mFragment != null) {
            val fragmentManager = supportFragmentManager
            fragmentManager.beginTransaction()
                .add(R.id.fragment_holder, mFragment, "")
                .addToBackStack("").commitAllowingStateLoss() //commit
        }
    }



    private fun askForNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                // FCM SDK (and your app) can post notifications.
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                // TODO: display an educational UI explaining to the user the features that will be enabled
                //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
                //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
                //       If the user selects "No thanks," allow the user to continue without notifications.
            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        Intent.FLAG_ACTIVITY_CLEAR_TASK

    }
    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            || ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
            || ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED
//            || ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_NOTIFICATION_POLICY) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.CALL_PHONE
//                    ,
//                    Manifest.permission.ACCESS_NOTIFICATION_POLICY
                ),
                123
            )
        }
    }
    private fun reEnroll(mContext: Context) {
        Log.e("re", "en")
        val page_count = 0
        //int total_count=studDetailList.size-1;
        val check = 0
        val dialog = Dialog(mContext)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialogue_re_enrollment_status)
        var mTitle: String
        var mTabId: String
        var mRootView: View
        val titleTextView: TextView
        var relMain: RelativeLayout
        val closeImage: ImageView
        val emailHelpImg: ImageView
        titleTextView = dialog.findViewById(R.id.titleTextView)
        reEnrollRecycler = dialog.findViewById<RecyclerView>(R.id.enroll_rec)
        closeImage = dialog.findViewById(R.id.close_img)
        emailHelpImg = dialog.findViewById(R.id.emailHelpImg)
        emailHelpImg.setOnClickListener {
            val dialog = Dialog(mContext)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.alert_send_email_dialog)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val dialogCancelButton =
                dialog.findViewById<View>(R.id.cancelButton) as Button
            val submitButton =
                dialog.findViewById<View>(R.id.submitButton) as Button
            text_dialog =
                dialog.findViewById<View>(R.id.text_dialog) as EditText
            text_content =
                dialog.findViewById<View>(R.id.text_content) as EditText
            dialogCancelButton.setOnClickListener { dialog.dismiss() }
            submitButton.setOnClickListener {
               // sendEmailEnroll(URL_SEND_EMAIL_ENROLL)
                dialog.dismiss()
            }
            dialog.show()
        }
        titleTextView.text = "Re-Enrollment"
        studentEnrollList = ArrayList<StudentEnrollList>()
        callSettingsAPI(mContext)
        closeImage.setOnClickListener { dialog.dismiss() }
        reEnrollRecycler.addOnItemTouchListener(
            RecyclerItemListener(mContext, reEnrollRecycler,
                object : RecyclerItemListener.RecyclerTouchListener {
                    override fun onClickItem(v: View?, position: Int) {
                        Log.e("Click","Click")
                        if (studentList.get(position).status.equals("")) {
                            Log.e("Click1","Click1")
                            if (studentList.get(position).enrollment_status.equals("1")
                            ) {
                                Log.e("Click2","Click2")

                                callReEnrollAPI(studentList, position)
                                Log.e("pos1", position.toString())
                            } else {
                               ConstantFunctions. showDialogAlertDismiss(
                                    mActivity, "Alert",
                                    """
                            Re-Enrolment is currently not enabled        
                            
                            Please wait for further update
                            """.trimIndent(), R.drawable.exclamationicon, R.drawable.round
                                )
                            }
                        } else {
                            val dialog = Dialog(mContext)
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                            dialog.setContentView(R.layout.alert_re_enroll)
                            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                            val name = dialog.findViewById<TextView>(R.id.nametxt)
                            val studName = dialog.findViewById<TextView>(R.id.stud_name)
                            val department = dialog.findViewById<TextView>(R.id.mailtxt)
                            val role = dialog.findViewById<TextView>(R.id.statustxt)
                            val imageView = dialog.findViewById<ImageView>(R.id.iconImageView)
                            name.setText(studentList.get(position).parent_name)
                            studName.setText(studentList.get(position).name)
                            department.setText(studentList.get(position).parent_email)
                            role.setText(studentList.get(position).status)
                            // TODO set Staff Image
                            dialog.show()
                        }
                    }

                    override fun onLongClickItem(v: View?, position: Int) {}
                })
        )
        dialog.show()
    }
    private fun callSettingsAPI(mContext: Context) {
        //  progressDialogP.show()
        studentList= ArrayList()
        val call: Call<EnrollmentStatusResponseModel> =
            ApiClient.getClient.getenrollstatus("Bearer " + PreferenceManager.getaccesstoken(mContext))

        call.enqueue(object : Callback<EnrollmentStatusResponseModel?> {
            override fun onResponse(
                call: Call<EnrollmentStatusResponseModel?>,
                response: Response<EnrollmentStatusResponseModel?>
            ) {
                // progressDialogP.hide()
                if (response.body() != null) {
                    val apiResponse: EnrollmentStatusResponseModel? = response.body()
                    val status: String = apiResponse!!.getStatus()
                    if (status == "100") {

                        studentList.addAll(apiResponse.responseArray.students)
                        reEnrollRecycler.layoutManager= LinearLayoutManager(context)
                        var re_enroll_adapter=
                            ReEnrollAdapter(context,studentList)
                        reEnrollRecycler.adapter=re_enroll_adapter

                    } else {
                        /*AppUtils.showDialogAlertDismiss(
                            mContext as Activity,
                            "Alert",
                            "Cannot continue. Please try again later",
                            R.drawable.exclamationicon,
                            R.drawable.round
                        )*/
                    }
                } else {
                    /* AppUtils.showDialogAlertDismiss(
                         mContext as Activity,
                         "Alert",
                         getString(R.string.common_error),
                         R.drawable.exclamationicon,
                         R.drawable.round
                     )*/
                }
            }

            override fun onFailure(call: Call<EnrollmentStatusResponseModel?>, t: Throwable) {
                /* progressDialogP.hide()
                 AppUtils.showDialogAlertDismiss(
                     mContext as Activity,
                     "Alert",
                     getString(R.string.common_error),
                     R.drawable.exclamationicon,
                     R.drawable.round
                 )*/
            }
        })
    }
    private fun callReEnrollAPI(studentEnrollList: java.util.ArrayList<StudentEnrollList>, position: Int) {
        // progressDialogP.show()
        val service: ApiInterface = ApiClient.getClient

        val call: Call<EnrollmentFormResponseModel> =
            service.getenrollform("Bearer " +  PreferenceManager.getaccesstoken(mContext))
        call.enqueue(object : Callback<EnrollmentFormResponseModel?> {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onResponse(
                call: Call<EnrollmentFormResponseModel?>,
                response: Response<EnrollmentFormResponseModel?>
            ) {
                if (response.body() != null) {
                    val apiResponse: EnrollmentFormResponseModel? = response.body()
                    val responseCode: String = apiResponse!!.getResponsecode()
                    if (responseCode == "200") {
                        val statuscode: String = apiResponse!!.getResponse().getStatuscode()
                        val responseData: EnrollmentFormResponseModel.ResponseData = apiResponse!!.getResponse()
                        if (statuscode == "303") {
                            // progressDialogP.hide()
                            val responseArrayObject: EnrollmentFormResponseModel.ResponseData.ResponseArray =
                                responseData.getResponseArray()
                            val settingsObject: EnrollmentFormResponseModel.ResponseData.ResponseArray.Settings =
                                responseArrayObject.getSettings()
                            val headingString: String = settingsObject.getHeading()
                            val question: String = settingsObject.getQuestion()
                            val descriptionString: String = settingsObject.getDescription()
                            val tAndCString: String = settingsObject.getT_and_c()
                            val optionsArray: java.util.ArrayList<String> = settingsObject.getOptions()
                            val userObject: EnrollmentFormResponseModel.ResponseData.ResponseArray.User =
                                responseArrayObject.getUser()
                            val userNameString: String = userObject.getFirstname()
                            val emailString: String = userObject.getEmail()
                            val studentArray: java.util.ArrayList<ReEnrollmentFormStudentModel> =
                                responseArrayObject.getStudents()
                            val studentList: java.util.ArrayList<ReEnrollmentFormStudentModel> =
                                java.util.ArrayList()
                            val temp = ReEnrollmentFormStudentModel()
                            for (i in studentArray.indices) {
                                val item: ReEnrollmentFormStudentModel =
                                    apiResponse.getResponse().getResponseArray().getStudents().get(i)
                                val gson = Gson()
                                val eventJson = gson.toJson(item)
                                try {
                                    val jsonObject = JSONObject(eventJson)
                                    studentList.add(
                                        addStudentReEnrollDetails(
                                            jsonObject
                                        )
                                    )
                                    //  Log.e("array", String.valueOf(mCCAmodelArrayList));
                                } catch (e: JSONException) {
                                    e.printStackTrace()
                                }
                            }
                            showReEnrollPopUp(
                                mContext,
                                headingString,
                                descriptionString,
                                tAndCString,
                                optionsArray,
                                userNameString,
                                emailString,
                                studentList,
                                position,
                                studentEnrollList,
                                question
                            )
                        }
                    } else if (responseCode.equals("500", ignoreCase = true)) {
                        /*AppUtils.showDialogAlertDismiss(
                            mContext as Activity,
                            "Alert",
                            "Cannot continue. Please try again later",
                            R.drawable.exclamationicon,
                            R.drawable.round
                        )*/
                    } else if (responseCode.equals("400", ignoreCase = true)) {

                        //						getStudentsListAPI(URLHEAD);
                    } else if (responseCode.equals("401", ignoreCase = true)) {

                        //						getStudentsListAPI(URLHEAD);
                    } else if (responseCode.equals("402", ignoreCase = true)) {

                        //						getStudentsListAPI(URLHEAD);
                    } else {
                        /* progressDialogP.hide()
                         AppUtils.showDialogAlertDismiss(
                             mContext as Activity,
                             "Alert",
                             "Cannot continue. Please try again later",
                             R.drawable.exclamationicon,
                             R.drawable.round
                         )*/
                    }
                } else {
                    /* AppUtils.showDialogAlertDismiss(
                         mContext as Activity,
                         "Alert",
                         getString(R.string.common_error),
                         R.drawable.exclamationicon,
                         R.drawable.round
                     )*/
                }
            }

            override fun onFailure(call: Call<EnrollmentFormResponseModel?>, t: Throwable) {
                /* progressDialogP.hide()
                 AppUtils.showDialogAlertDismiss(
                     mContext as Activity,
                     "Alert",
                     mContext.getString(R.string.common_error),
                     R.drawable.exclamationicon,
                     R.drawable.round
                 )*/
            }
        })
    }

    private fun saveReEnrollApi(
        reEnrollSaveArray: java.util.ArrayList<com.nas.alreem.fragment.home.re_enrollment.ReEnrollSubmitModel>,
        dialog1: Dialog,
        dialog: Dialog,
        position: Int
    ) {
        // selected item studentlist positio
        // progressDialogP.show()
        val service: ApiInterface = ApiClient.getClient
        val paramObject = JsonObject()
        paramObject.addProperty("status", selectedItem)
        paramObject.addProperty("student_id", studentReEnrollList[position].id)
        val call: Call<EnrollmentSaveResponseModel> = service.getenrollsave(
            "Bearer " + PreferenceManager.getaccesstoken(mContext),
            paramObject
        )
        call.enqueue(object : Callback<EnrollmentSaveResponseModel?> {
            override fun onResponse(
                call: Call<EnrollmentSaveResponseModel?>,
                response: Response<EnrollmentSaveResponseModel?>
            ) {
                // progressDialogP.hide()
                if (response.body() != null) {
                    val apiResponse: EnrollmentSaveResponseModel? = response.body()
                    val status: String = apiResponse!!.getStatus()
                    if (status == "100") {
                        if (selectedItem.equals("Returning", ignoreCase = true)) {
                            showSuccessReEnrollAlert(
                                mContext,
                                " Thank you         \n" +
                                        "\n" +
                                        "Once this form is submitted, please find the invoice under Payments on this App",
                                "Success",
                                dialog1,
                                dialog
                            )
                        } else if (selectedItem.equals("Unsure", ignoreCase = true)) {
                            showSuccessReEnrollAlert(
                                mContext,
                                ("Thank you\n" +
                                        "\n" +
                                        "Once this form is submitted, please find the invoice under Payments on this App (this will be used to hold your child’s place)  "),
                                "Success",
                                dialog1,
                                dialog
                            )
                        } else if (selectedItem.equals("Graduating", ignoreCase = true)) {
                            showSuccessReEnrollAlert(
                                mContext, ("Thank you\n" +
                                        "\n" +
                                        "Congratulations to the graduate!"), "Success", dialog1, dialog
                            )
                        } else if (selectedItem.equals("Not returning", ignoreCase = true)) {
                            showSuccessReEnrollAlert(
                                mContext,
                                ("Thank you\n" +
                                        "\n" +
                                        "We wish you all the best in your new school!"),
                                "Success",
                                dialog1,
                                dialog
                            )
                        } else {
                            showSuccessReEnrollAlert(
                                mContext,
                                "Submit Successful, Thank you.",
                                "Success",
                                dialog1,
                                dialog
                            )
                        }
                        callSettingsAPI(mContext)
                    } else {
                        /* AppUtils.showDialogAlertDismiss(
                             mContext as Activity,
                             "Alert",
                             "Cannot continue. Please try again later",
                             R.drawable.exclamationicon,
                             R.drawable.round
                         )*/
                    }
                } else {
                    /* AppUtils.showDialogAlertDismiss(
                         mContext as Activity,
                         "Alert",
                         getString(R.string.common_error),
                         R.drawable.exclamationicon,
                         R.drawable.round
                     )*/
                }
            }

            override fun onFailure(call: Call<EnrollmentSaveResponseModel?>, t: Throwable) {
                //  progressDialogP.hide()
                /*AppUtils.showDialogAlertDismiss(
                    mContext as Activity,
                    "Alert",
                    getString(R.string.common_error),
                    R.drawable.exclamationicon,
                    R.drawable.round
                )*/
            }
        })
    }
    private fun showSuccessReEnrollAlert(
        mContext: Context,
        successfully_submitted_: String,
        success: String,
        dialog1: Dialog,
        dialog: Dialog
    ) {
        val d = Dialog(mContext)
        d.requestWindowFeature(Window.FEATURE_NO_TITLE)
        d.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        d.setCancelable(false)
        d.setContentView(R.layout.dialog_common_error_alert)
        val iconImageView = d.findViewById<ImageView>(R.id.iconImageView)
        val alertHead = d.findViewById<TextView>(R.id.alertHead)
        val text_dialog = d.findViewById<TextView>(R.id.text_dialog)
        val btn_Ok = d.findViewById<Button>(R.id.btn_Ok)
        text_dialog.text = successfully_submitted_
        alertHead.text = success
        iconImageView.setImageResource(R.drawable.tick)
        btn_Ok.setOnClickListener {
            d.dismiss()
            dialog.dismiss()
            dialog1.dismiss()
        }
        d.show()
    }
    private fun addStudentStatus(dataObject: JSONObject): com.nas.alreem.fragment.home.re_enrollment.StudentEnrollList {
        val studentModel = com.nas.alreem.fragment.home.re_enrollment.StudentEnrollList()
        studentModel.id = dataObject.optString("id")
        studentModel.unique_id = dataObject.optString("unique_id")
        studentModel.name = dataObject.optString("name")
        studentModel.class_name = dataObject.optString("class_name")
        studentModel.section = dataObject.optString("section")
        studentModel.house = dataObject.optString("house")
        studentModel.photo = dataObject.optString("photo")
        studentModel.parent_name = dataObject.optString("parent_name")
        studentModel.parent_email = dataObject.optString("parent_email")
        studentModel.enrollment_status = dataObject.optString("enrollment_status")
        studentModel.status = dataObject.optString("status")
        return studentModel
    }

    private fun addStudentReEnrollDetails(dataObject: JSONObject): ReEnrollmentFormStudentModel {
        val studentModel = ReEnrollmentFormStudentModel()
        studentModel.id = dataObject.optString("id")
        studentModel.unique_id = dataObject.optString("unique_id")
        studentModel.name = dataObject.optString("name")
        studentModel.class_name = dataObject.optString("class_name")
        studentModel.section = dataObject.optString("section")
        studentModel.house = dataObject.optString("house")
        studentModel.photo = dataObject.optString("photo")
        return studentModel
    }
    private fun sendEmailEnroll(URL: String) {
        //  progressDialogP.show()
        val service: ApiInterface = ApiClient.getClient
        val paramObject = JsonObject()
        paramObject.addProperty("title", text_dialog.getText().toString())
        paramObject.addProperty("message", text_content.getText().toString())
        val call: Call<EnrollmentHelpResponseModel> =
            service.getenrollhelp("Bearer " + PreferenceManager.getaccesstoken(mContext), paramObject)
        call.enqueue(object : Callback<EnrollmentHelpResponseModel?> {
            override fun onResponse(
                call: Call<EnrollmentHelpResponseModel?>,
                response: Response<EnrollmentHelpResponseModel?>
            ) {
                // progressDialogP.hide()
                if (response.body() != null) {
                    val apiResponse: EnrollmentHelpResponseModel? = response.body()
                    val response_code: Int = apiResponse!!.getStatus()
                    if (response_code .equals("100")) {
                        val toast = Toast.makeText(
                            mContext,
                            "Successfully sent email to admission team",
                            Toast.LENGTH_SHORT
                        )
                        toast.show()
                    } else {
                        /*CustomDialog dialog = new CustomDialog(mContext, getResources().getString(R.string.common_error)
                                    , getResources().getString(R.string.ok));
                            dialog.show();*/
                        /*AppUtils.showDialogAlertDismiss(
                            mContext as Activity,
                            "Alert",
                            mContext.getString(R.string.common_error),
                            R.drawable.exclamationicon,
                            R.drawable.round
                        )*/
                    }
                } else {
                    /* AppUtils.showDialogAlertDismiss(
                         mContext as Activity,
                         "Alert",
                         getString(R.string.common_error),
                         R.drawable.exclamationicon,
                         R.drawable.round
                     )*/
                }
            }

            override fun onFailure(call: Call<EnrollmentHelpResponseModel?>, t: Throwable) {
                //   progressDialogP.hide()
                /*AppUtils.showDialogAlertDismiss(
                    mContext as Activity,
                    "Alert",
                    getString(R.string.common_error),
                    R.drawable.exclamationicon,
                    R.drawable.round
                )*/
            }
        })
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun showReEnrollPopUp(
        mContext: Context,
        headingString: String,
        descriptionString: String,
        tAndCString: String,
        optionsArray: java.util.ArrayList<String>,
        userNameString: String,
        emailString: String,
        studentList: java.util.ArrayList<ReEnrollmentFormStudentModel>,
        position: Int,
        studentEnrollList: java.util.ArrayList<StudentEnrollList>,
        question: String
    ) {
        val dialog = Dialog(mContext)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialogue_re_enrollment)
        for (i in studentList.indices) {
        }
        for (i in optionsArray.indices) {
        }
        val check = intArrayOf(0)
        val header_txt = dialog.findViewById<TextView>(R.id.header)
        val close_img = dialog.findViewById<ImageView>(R.id.close_img)
        val sub_btn = dialog.findViewById<Button>(R.id.sub_btn)
        val image_view = dialog.findViewById<ImageView>(R.id.image_view)
        val stud_img = dialog.findViewById<ImageView>(R.id.stud_img)
        val stud_name = dialog.findViewById<TextView>(R.id.stud_name)
        val stud_class = dialog.findViewById<TextView>(R.id.stud_class)
        val date_field = dialog.findViewById<EditText>(R.id.textField_date)
        val descrcrptn = dialog.findViewById<TextView>(R.id.descrptn_txt)
        val parent_name = dialog.findViewById<EditText>(R.id.textField_parentName)
        val parent_email = dialog.findViewById<EditText>(R.id.textField_parentEmail)
        val spinnerList = dialog.findViewById<Spinner>(R.id.spinnerlist)
        val option_txt = dialog.findViewById<TextView>(R.id.option_txt)
        val dropdown_btn = dialog.findViewById<ImageView>(R.id.dropdown_btn)
        val radioButton = dialog.findViewById<RadioButton>(R.id.check_btn)
        val scrollView = dialog.findViewById<ScrollView>(R.id.scroll)
        val terms_and_condtns = dialog.findViewById<Button>(R.id.terms_conditions)
        val questionTxt = dialog.findViewById<TextView>(R.id.questionTxt)
        var dropDownList: java.util.ArrayList<String?> = java.util.ArrayList()
        val currentDate = LocalDate.now().toString()
        val currentYear = Calendar.YEAR.toString()
        val currentMonth = Calendar.MONTH.toString()
        val currentDay = Calendar.DAY_OF_MONTH.toString()
        var date = "$currentDay/$currentMonth/$currentYear"
        header_txt.text = headingString
        descrcrptn.text = descriptionString
        parent_email.setText(emailString)
        parent_name.setText(userNameString)
        questionTxt.text = question
        val reEnrollSubmit: java.util.ArrayList<com.nas.alreem.fragment.home.re_enrollment.ReEnrollSubmitModel> =
            java.util.ArrayList<com.nas.alreem.fragment.home.re_enrollment.ReEnrollSubmitModel>()
        for (i in studentList.indices) {
            val temp = com.nas.alreem.fragment.home.re_enrollment.ReEnrollSubmitModel("", "")
            reEnrollSubmit.add(i, temp)
        }
        date = ConstantFunctions.dateConversionddmmyyyy(currentDate)
        date_field.setText(date)
        dropDownList = java.util.ArrayList()
        stud_name.text = studentEnrollList[position].name
        //        stud_name.setText(studentList.get(position).getName());
        stud_class.text = studentEnrollList[position].class_name
        //        stud_class.setText(studentList.get(position).getClass_name());
        // studDetailList= ArrayList()
        val stud_photo = studentEnrollList[position].photo
        //        String stud_photo= studentList.get(position).getPhoto();
        val stud_id = studentEnrollList[position].id
        //        String stud_id=studentList.get(position).getId();
        dropDownList.add(0, "Please Select")
        for (i in 1..optionsArray.size) {
            dropDownList.add(optionsArray[i - 1])
        }
        val sp_adapter: ArrayAdapter<*> =
            ArrayAdapter(mContext, R.layout.spinner_textview, dropDownList)
        spinnerList.adapter = sp_adapter
        spinnerList.setSelection(0)
        val finalDropDownList: java.util.ArrayList<*> = dropDownList
        spinnerList.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                selectedItem = parent.getItemAtPosition(position).toString()
                val optionlistSize = finalDropDownList.size - 1
                for (i in 1 until optionlistSize) {
                    if (selectedItem === finalDropDownList[i].toString()) {
                        reEnrollSubmit[0].setStatus(finalDropDownList[i].toString())
                        reEnrollSubmit[0].setStudent_id(studentEnrollList[position].id)
                        check[0] = 1
                    } else if (selectedItem === finalDropDownList[0]) {
                        reEnrollSubmit[position].setStatus("")
                        check[0] = 0
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        if (stud_photo != "") {
            Glide.with(mContext)
                .load(stud_photo)
                .placeholder(R.drawable.student)
                .error(R.drawable.student)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(stud_img)
        } else {
            stud_img.setImageResource(R.drawable.student)
        }
        if (userNameString.equals("", ignoreCase = true)) {
            parent_name.setText(userNameString)
        }
        if (emailString.equals("", ignoreCase = true)) {
            parent_email.setText(emailString)
        }
        dropdown_btn.setOnClickListener { dropdown_btn.visibility = View.GONE }
        option_txt.setOnClickListener {
            option_txt.visibility = View.GONE
            spinnerList.visibility = View.VISIBLE
        }
        terms_and_condtns.setOnClickListener {
            val intent = Intent(mContext, WebLinkActivity::class.java)
            intent.putExtra("Url", tAndCString)
            mContext.startActivity(intent)
        }
        sub_btn.setOnClickListener {
            if (selectedItem.equals("", ignoreCase = true) || selectedItem.equals(
                    "Please Select",
                    ignoreCase = true
                )
            ) {
                showReEnrollNoData(
                    mContext,
                    "You didn't enter any data of your child. Please Enter data and Submit",
                    "Alert",
                    dialog
                )
            } else {
                showSubmitConfirm(mContext, "Would you like to submit?", "Alert", dialog, position)
            }
        }
        close_img.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }
    private fun showReEnrollNoData(activity: Context, s: String, alert: String, dialog: Dialog) {
        val dialog1 = Dialog(mContext)
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog1.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog1.setCancelable(false)
        dialog1.setContentView(R.layout.dialog_common_error_alert)
        val iconImageView = dialog1.findViewById<ImageView>(R.id.iconImageView)
        iconImageView.setImageResource(R.drawable.exclamationicon)
        val alertHead = dialog1.findViewById<TextView>(R.id.alertHead)
        val text_dialog = dialog1.findViewById<TextView>(R.id.text_dialog)
        val btn_Ok = dialog1.findViewById<Button>(R.id.btn_Ok)
        // var btn_Cancel = dialog.findViewById(R.id.btn_Cancel) as Button
        text_dialog.text = s
        alertHead.text = alert
        btn_Ok.setOnClickListener { dialog1.dismiss() }

        /* btn_Cancel.setOnClickListener {
              dialog.dismiss()
          }*/dialog1.show()
    }
    private fun showSubmitConfirm(
        activity: Context, do_you_want_to_submit: String, alert: String,
        dialog: Dialog, position: Int
    ) {
        val dialog1 = Dialog(mContext)
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog1.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog1.setCancelable(false)
        dialog1.setContentView(R.layout.dialog_ok_cancel)
        val iconImageView = dialog1.findViewById<ImageView>(R.id.iconImageView)
        val alertHead = dialog1.findViewById<TextView>(R.id.alertHead)
        val text_dialog = dialog1.findViewById<TextView>(R.id.text_dialog)
        val btn_Ok = dialog1.findViewById<Button>(R.id.btn_Ok)
        val btn_Cancel = dialog1.findViewById<Button>(R.id.btn_Cancel)
        text_dialog.text = do_you_want_to_submit
        alertHead.text = alert
        btn_Ok.setOnClickListener {
            saveReEnrollApi(
                com.nas.alreem.fragment.home.reEnrollSaveArray,
                dialog1,
                dialog,
                position
            )
            dialog1.dismiss()
        }
        btn_Cancel.setOnClickListener { dialog1.dismiss() }
        dialog1.show()
    }
    }