package com.nas.alreem.activity.home

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.content.res.TypedArray
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
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
import com.google.android.material.snackbar.Snackbar
import com.nas.alreem.R
import com.nas.alreem.activity.home.adapter.HomeListAdapter
import com.nas.alreem.constants.DialogFunctions
import com.nas.alreem.constants.MyDragShadowBuilder
import com.nas.alreem.constants.PreferenceManager
import com.nas.alreem.fragment.about_us.AboutUsFragment
import com.nas.alreem.fragment.absence.AbsenceFragment
import com.nas.alreem.fragment.bus_service.BusServiceFragment
import com.nas.alreem.fragment.calendar.CalendarFragment
import com.nas.alreem.fragment.canteen.CanteenFragment
import com.nas.alreem.fragment.cca.CCAFragment
import com.nas.alreem.fragment.contact_us.ContactUsFragment
import com.nas.alreem.fragment.early_years.EarlyYearsFragment
import com.nas.alreem.fragment.gallery.GalleryFragment
import com.nas.alreem.fragment.home.HomeFragment
import com.nas.alreem.fragment.home.mContext
import com.nas.alreem.fragment.intention.Intentionfragment
import com.nas.alreem.fragment.notifications.NotificationFragment
import com.nas.alreem.fragment.parent_meetings.ParentMeetingsFragment
import com.nas.alreem.fragment.parents_essentials.ParentsEssentialFragment
import com.nas.alreem.fragment.payments.PaymentFragment
import com.nas.alreem.fragment.permission_slip.PermissionSlipFragment
import com.nas.alreem.fragment.permission_slip.PermissionSlipFragmentNew
import com.nas.alreem.fragment.primary.PrimaryFragment
import com.nas.alreem.fragment.reports.ReportsFragment
import com.nas.alreem.fragment.secondary.SecondaryFragment
import com.nas.alreem.fragment.settings.SettingsFragment
import com.nas.alreem.fragment.student_information.StudentInformationFragment
import com.nas.alreem.fragment.time_table.TimeTableFragment

class HomeActivity : AppCompatActivity(), AdapterView.OnItemLongClickListener {

    val manager = supportFragmentManager
    lateinit var navigation_menu: ImageView
    lateinit var settings_icon: ImageView
    lateinit var shadowBuilder: MyDragShadowBuilder
    lateinit var context: Context
    lateinit var mActivity: Activity

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
                if (result) {
                    // PERMISSION GRANTED
                    // Toast.makeText(mContext, String.valueOf(result), Toast.LENGTH_SHORT).show();
                } else {
                    // PERMISSION NOT GRANTED
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
                        mFragment = HomeFragment()
                        replaceFragmentsSelected(position)
                    }
                    1->
                    {
                        //Notification
                        mFragment =StudentInformationFragment()
                        replaceFragmentsSelected(position)
                    }
                    2->
                    {
                        //Notification
                        mFragment = NotificationFragment()
                        replaceFragmentsSelected(position)
                    }
                    3->
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
                    4->
                    {
                        //About Us
                        mFragment = PaymentFragment()
                        replaceFragmentsSelected(position)


                    }
                    5->
                    {
                        //payment
                        mFragment = CanteenFragment()
                        replaceFragmentsSelected(position)
                    }
                    6->
                    {

                        mFragment = ParentsEssentialFragment()
                        replaceFragmentsSelected(position)
//                        DialogFunctions.commonErrorAlertDialog("Coming Soon!","This Feature will be available shortly",
//                            mContext
//                        )
                    }
                    7->
                    {
                        PreferenceManager.setStudentID(mContext,"")
                        mFragment = AbsenceFragment()
                        replaceFragmentsSelected(position)
//                        DialogFunctions.commonErrorAlertDialog("Coming Soon!","This Feature will be available shortly",
//                            mContext
//                        )
                    }
                    8->
                    {
                        //Early years
                        mFragment = EarlyYearsFragment()
                        replaceFragmentsSelected(position)
                    }
                    9->
                    {
                        //Primary
                        mFragment = BusServiceFragment()
                        replaceFragmentsSelected(position)
                    }
                    10->
                    {
                        //Secondary
                        mFragment = Intentionfragment()
                        replaceFragmentsSelected(position)
                    }
                    11->
                    {
                        //Reports
                        PreferenceManager.setStudentID(context,"")
                        mFragment = ReportsFragment()
                        replaceFragmentsSelected(position)


                    }
                    12->
                    {
                        //Permission_form
                        PreferenceManager.setStudentID(context,"")
                        mFragment = TimeTableFragment()
                        replaceFragmentsSelected(position)

                    }
                    13->
                    {
                        //Permission_form
                        PreferenceManager.setStudentID(context,"")
                        mFragment = PermissionSlipFragmentNew()
                        replaceFragmentsSelected(position)

                    }

                    14->
                    {
                        //CCa
                        mFragment = CCAFragment()
                        replaceFragmentsSelected(position)
                    }
                    15->
                    {
                        //Parents meeting
                        mFragment = ParentMeetingsFragment()
                        replaceFragmentsSelected(position)
                    }
                    16->
                    {
                        //Gallery
                        mFragment = GalleryFragment()
                        replaceFragmentsSelected(position)
                    }
                    17->
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
                    18->
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
    }