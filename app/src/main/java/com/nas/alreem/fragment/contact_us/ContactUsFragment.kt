package com.nas.alreem.fragment.contact_us

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.nas.alreem.R
import com.nas.alreem.activity.primary.model.ComingUpResponseModel
import com.nas.alreem.constants.ConstantFunctions
import com.nas.alreem.constants.ConstantWords
import com.nas.alreem.constants.DialogFunctions
import com.nas.alreem.constants.PreferenceManager
import com.nas.alreem.fragment.contact_us.adapter.ContactusAdapter
import com.nas.alreem.fragment.contact_us.model.ContactUsModel
import com.nas.alreem.fragment.contact_us.model.ContactUsResponseModel
import com.nas.alreem.fragment.notifications.adapter.NotificationListAdapter
import com.nas.alreem.fragment.notifications.model.NotificationApiModel
import com.nas.alreem.fragment.notifications.model.NotificationResponseModel
import com.nas.alreem.rest.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ContactUsFragment  : Fragment(), LocationListener,
    GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener,
    GoogleMap.OnMarkerDragListener, GoogleMap.OnMapLongClickListener {
    lateinit var mContext: Context
    lateinit var c_latitude: String
    lateinit var c_longitude: String
    lateinit var locationManager: LocationManager
    var isGPSEnabled: Boolean? = null
    var isNetworkEnabled: Boolean? = null
    var lat: Double = 0.0
    var long: Double = 0.0
    lateinit var progressDialogAdd:ProgressBar
    lateinit var contact_usrecyclerview:RecyclerView
    lateinit var descriptiontext:TextView
    lateinit var titleTextView:TextView
    lateinit var map: GoogleMap
    lateinit var mapFragment: SupportMapFragment
    lateinit var contactUsArrayList: ArrayList<ContactUsModel>
     var latitude: String=""
     var longitude: String=""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_contact_us, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mContext = requireContext()
        if (Build.VERSION.SDK_INT >= 23 &&
            ContextCompat.checkSelfPermission(
                mContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(
                mContext,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
        }

        initializeUI()
        fetchlatitudelongitude()
        if (ConstantFunctions.internetCheck(mContext))
        {
            callContactUsApi()
        }
        else
        {
            DialogFunctions.showInternetAlertDialog(mContext)
        }

        // permissioncheck()


    }
    private fun fetchlatitudelongitude() {
        var location: Location
        locationManager = mContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        if (!isGPSEnabled!! && !isNetworkEnabled!!) {

        } else {
            if (isNetworkEnabled as Boolean) {
                if (ActivityCompat.checkSelfPermission(
                        mContext,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        mContext,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {

                } else {
                    locationManager.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        0L,
                        0.0F,
                        this
                    )

                    location =
                        locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)!!
                    Log.e("loc", location.toString())
                    if (location != null) {
                        lat = location.latitude
                        long = location.longitude
                    }
                }
            }
            if (isGPSEnabled as Boolean) {
                locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    0L,
                    0.0F,
                    this
                )
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)!!
                if (location != null) {
                    lat = location.latitude
                    long = location.longitude
                    println("lat---$lat")
                    println("lat---$long")
                    Log.e("CONTACTLATITUDE:", (lat + long).toString())
                }
            }
        }
        c_latitude = lat.toString()
        c_longitude = long.toString()

    }


    private fun initializeUI() {

        progressDialogAdd=requireView().findViewById(R.id.progressDialogAdd)
        contact_usrecyclerview=requireView().findViewById(R.id.contact_usrecyclerview)
        descriptiontext=requireView().findViewById(R.id.descriptiontext)
        titleTextView=requireView().findViewById(R.id.titleTextView)
        var linearLayoutManager = LinearLayoutManager(mContext)
        contact_usrecyclerview.layoutManager = linearLayoutManager
        contact_usrecyclerview.itemAnimator = DefaultItemAnimator()
        titleTextView.text=ConstantWords.contact_us
        mapFragment = childFragmentManager.findFragmentById(R.id.googleMap) as SupportMapFragment
    }



    override fun onLocationChanged(p0: Location) {
        // TODO("Not yet implemented")
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
    }



    override fun onConnected(p0: Bundle?) {
    }

    override fun onConnectionSuspended(p0: Int) {
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
    }

    override fun onMarkerDrag(p0: Marker) {
        TODO("Not yet implemented")
    }


    override fun onMarkerDragEnd(p0: Marker) {
        TODO("Not yet implemented")
    }

    override fun onMarkerDragStart(p0: Marker) {
        TODO("Not yet implemented")
    }

    override fun onMapLongClick(p0: LatLng) {
        TODO("Not yet implemented")
    }


   private fun callContactUsApi()
    {
        progressDialogAdd.visibility=View.VISIBLE
        contactUsArrayList= ArrayList()
        val call: Call<ContactUsResponseModel> = ApiClient.getClient.contactUs()
        call.enqueue(object : Callback<ContactUsResponseModel> {
            override fun onFailure(call: Call<ContactUsResponseModel>, t: Throwable) {
                Log.e("Failed", t.localizedMessage)
                progressDialogAdd.visibility=View.GONE
            }
            override fun onResponse(call: Call<ContactUsResponseModel>, response: Response<ContactUsResponseModel>) {
                val responsedata = response.body()
                progressDialogAdd.visibility=View.GONE
                if (responsedata != null) {
                    try {

                        if (response.body()!!.status==100)
                        {

                            contactUsArrayList=response.body()!!.responseArray!!.contacts!!
                            descriptiontext.text=response.body()!!.responseArray!!.description
                            latitude=response.body()!!.responseArray!!.latitude
                            longitude=response.body()!!.responseArray!!.longitude
                            var adapterContact=ContactusAdapter(contactUsArrayList,mContext)
                            contact_usrecyclerview.adapter=adapterContact
                            mapFragment.getMapAsync { googleMap ->
                                Log.d("Map Working", "good")
                                map = googleMap
                                map.uiSettings.isMapToolbarEnabled = false
                                map.uiSettings.isZoomControlsEnabled = false
                                val latLng = LatLng(latitude.toDouble(), longitude.toDouble())
                                map.addMarker(
                                    MarkerOptions()
                                        .position(latLng)
                                        .draggable(true)
                                        .title("NAS Abu Dhabi")
                                )


                                map.moveCamera(CameraUpdateFactory.newLatLng(latLng))

                                map.animateCamera(CameraUpdateFactory.zoomTo(13f))
                                map.setOnInfoWindowClickListener {

                                    if (!isGPSEnabled!!) {
                                        val callGPSSettingIntent = Intent(
                                            Settings.ACTION_LOCATION_SOURCE_SETTINGS
                                        )
                                        startActivity(callGPSSettingIntent)
                                    } else {
                                        //val url = "http://maps.google.com/maps?saddr=$c_latitude,$c_longitude&daddr=The British International School,Abudhabi"
                                        val url = "http://maps.google.com/maps?saddr=Your Location&daddr=The Nord Anglia International School,Abudhabi"

                                        val i = Intent(Intent.ACTION_VIEW)
                                        i.data = Uri.parse(url)
                                        startActivity(i)
                                    }


                                }
                            }
                        }
                        else
                        {

                            DialogFunctions.commonErrorAlertDialog(mContext.resources.getString(R.string.alert), ConstantFunctions.commonErrorString(response.body()!!.status), mContext)
                        }


                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

        })
    }
}