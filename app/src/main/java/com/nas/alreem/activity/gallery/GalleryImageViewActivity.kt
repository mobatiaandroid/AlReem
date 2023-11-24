package com.nas.alreem.activity.gallery

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nas.alreem.R
import com.nas.alreem.activity.gallery.adapter.AlbumAdapter
import com.nas.alreem.activity.gallery.adapter.PhotosAdapter
import com.nas.alreem.activity.gallery.model.*
import com.nas.alreem.activity.home.HomeActivity
import com.nas.alreem.constants.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GalleryImageViewActivity : AppCompatActivity(){
    lateinit var mContext: Context
    lateinit var recycler_view_photos: RecyclerView
    lateinit var backRelative: RelativeLayout
    lateinit var heading: TextView
    lateinit var logoClickImgView: ImageView
    lateinit var album_id: String
    lateinit var imageArrayList:ArrayList<PhotosModel>
    lateinit var progress: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery_images)
        mContext=this
        initUI()
        if (ConstantFunctions.internetCheck(mContext))
        {
            callAlbumAPi()
        }
        else
        {
            DialogFunctions.showInternetAlertDialog(mContext)
        }


    }
    fun initUI()
    {

        recycler_view_photos=findViewById(R.id.recycler_view_photos)
        heading=findViewById(R.id.heading)
        backRelative=findViewById(R.id.backRelative)
        logoClickImgView=findViewById(R.id.logoClickImgView)
        progress = findViewById(R.id.progress)
        backRelative.setOnClickListener(View.OnClickListener {
            finish()
        })
        heading.text="Images"
        album_id=intent.getStringExtra("album_id").toString()
        logoClickImgView.setOnClickListener(View.OnClickListener {
            val intent = Intent(mContext, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        })
        var linearLayoutManager = GridLayoutManager(mContext,3)
        recycler_view_photos.layoutManager = linearLayoutManager
        recycler_view_photos.itemAnimator = DefaultItemAnimator()
        recycler_view_photos.addOnItemClickListener(object : OnItemClickListener {
            @SuppressLint("SimpleDateFormat", "SetTextI18n")
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onItemClicked(position: Int, view: View)
            {

                val intent = Intent(mContext, PhotosViewPagerActivity::class.java)
                intent.putExtra("photo_array",imageArrayList)
                intent.putExtra("pos",position)
                startActivity(intent)

            }


        })
    }
    fun callAlbumAPi()
    {
        progress.visibility = View.VISIBLE
        imageArrayList= ArrayList()
        var model=PhotosApiModel("1",album_id,"new")
        val call: Call<PhotosResponseModel> = ApiClient.getClient.photos(model,"Bearer "+PreferenceManager.getaccesstoken(mContext))
        call.enqueue(object : Callback<PhotosResponseModel> {
            override fun onFailure(call: Call<PhotosResponseModel>, t: Throwable) {
                progress.visibility = View.GONE


            }
            override fun onResponse(call: Call<PhotosResponseModel>, response: Response<PhotosResponseModel>) {
                progress.visibility = View.GONE
                val responsedata = response.body()

                if (responsedata != null) {
                    try {

                        if (response.body()!!.status==100)
                        {
                            imageArrayList.addAll(response.body()!!.responseArray!!.images)
                            if (imageArrayList.size>0)
                            {
                                var adapterImg= PhotosAdapter(imageArrayList,mContext)
                                recycler_view_photos.adapter=adapterImg

                            }
                            else{
                                DialogFunctions.commonErrorAlertDialog(mContext.resources.getString(R.string.alert), "No Data Found", mContext)

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