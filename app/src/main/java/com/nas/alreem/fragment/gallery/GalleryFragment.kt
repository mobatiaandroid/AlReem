package com.nas.alreem.fragment.gallery

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nas.alreem.R
import com.nas.alreem.activity.gallery.GalleryImageListActivity
import com.nas.alreem.activity.gallery.GalleryVideoList
import com.nas.alreem.activity.payments.model.payment_submit.PaymentSubmitModel
import com.nas.alreem.constants.*
import com.nas.alreem.fragment.about_us.adapter.AboutUsAdapter
import com.nas.alreem.fragment.about_us.model.AboutUsDataModel
import com.nas.alreem.fragment.about_us.model.AboutUsResponseModel
import com.nas.alreem.fragment.gallery.adapter.ImageThumnailAdapter
import com.nas.alreem.fragment.gallery.model.ThumnailImageModel
import com.nas.alreem.fragment.gallery.model.ThumnailResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GalleryFragment : Fragment() {

    lateinit var mContext: Context
    lateinit var titleTextView: TextView
    lateinit var moreImage: TextView
    lateinit var photoImageIcon: ImageView
    lateinit var videoImageIcon: ImageView
    lateinit var moreVideo: TextView
    lateinit var viewGridPhotos: RecyclerView
    lateinit var viewGridVideo: RecyclerView
    lateinit var progressDialogAdd: ProgressBar
    lateinit var thumnailImageArray:ArrayList<ThumnailImageModel>
    lateinit var videoImageArray:ArrayList<ThumnailImageModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_gallery, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mContext=requireContext()
        initializeUI()
        progressDialogAdd.visibility=View.VISIBLE
        if (ConstantFunctions.internetCheck(mContext))
        {
            callGalleryThumnail()
        }
        else
        {
            DialogFunctions.showInternetAlertDialog(mContext)
        }

    }
    private fun initializeUI()
    {
        titleTextView=requireView().findViewById(R.id.titleTextView)
        viewGridPhotos=requireView().findViewById(R.id.viewGridPhotos)
        viewGridVideo=requireView().findViewById(R.id.viewGridVideo)
        moreImage=requireView().findViewById(R.id.moreImage)
        photoImageIcon=requireView().findViewById(R.id.photoImageIcon)
        progressDialogAdd=requireView().findViewById(R.id.progressDialogAdd)
        moreVideo=requireView().findViewById(R.id.moreVideo)
        videoImageIcon=requireView().findViewById(R.id.videoImageIcon)
        titleTextView.text=ConstantWords.gallery
        var linearLayoutManager = GridLayoutManager(mContext,3)
        var linearLayoutManagerV = GridLayoutManager(mContext,3)
        viewGridPhotos.layoutManager = linearLayoutManager
        viewGridPhotos.itemAnimator = DefaultItemAnimator()
        viewGridVideo.layoutManager = linearLayoutManagerV
        viewGridVideo.itemAnimator = DefaultItemAnimator()

        moreImage.setOnClickListener(View.OnClickListener {
            val intent = Intent(mContext, GalleryImageListActivity::class.java)
            startActivity(intent)
        })
        moreVideo.setOnClickListener(View.OnClickListener {
            val intent = Intent(mContext, GalleryVideoList::class.java)
            startActivity(intent)
        })
        photoImageIcon.setOnClickListener(View.OnClickListener {
            if (thumnailImageArray.size>0)
            {
                val intent = Intent(mContext, GalleryImageListActivity::class.java)
                startActivity(intent)
            }
            else{
                DialogFunctions.commonErrorAlertDialog(mContext.resources.getString(R.string.alert), "No Images found in your gallery", mContext)

            }

        })
        videoImageIcon.setOnClickListener(View.OnClickListener {
            if (videoImageArray.size>0)
            {
                val intent = Intent(mContext, GalleryVideoList::class.java)
                startActivity(intent)
            }
            else{
                DialogFunctions.commonErrorAlertDialog(mContext.resources.getString(R.string.alert), "No Videos found in your gallery", mContext)

            }

        })


    }
    private fun callGalleryThumnail()
    {

        val call: Call<ThumnailResponseModel> = ApiClient(mContext).getClient.galleryThumbNail("Bearer "+PreferenceManager.getaccesstoken(mContext))
        call.enqueue(object : Callback<ThumnailResponseModel> {
            override fun onFailure(call: Call<ThumnailResponseModel>, t: Throwable) {
                progressDialogAdd.visibility=View.GONE
            }
            override fun onResponse(call: Call<ThumnailResponseModel>, response: Response<ThumnailResponseModel>) {
                progressDialogAdd.visibility=View.GONE
                val responsedata = response.body()
                if (responsedata != null) {
                    try {
                        thumnailImageArray= ArrayList()
                        videoImageArray= ArrayList()
                        if (response.body()!!.status==100)
                        {

                            thumnailImageArray.addAll(response.body()!!.responseArray!!.image)
                            videoImageArray.addAll(response.body()!!.responseArray!!.video)
                            if (thumnailImageArray.size>0)
                            {
                                moreImage.visibility=View.VISIBLE
                                var adapterPic=ImageThumnailAdapter(thumnailImageArray,mContext)
                                viewGridPhotos.adapter=adapterPic
                            }
                            else{
                                moreImage.visibility=View.GONE
                            }
                            if (videoImageArray.size>0)
                            {
                                moreVideo.visibility=View.VISIBLE
                                var adapterPic=ImageThumnailAdapter(thumnailImageArray,mContext)
                                viewGridVideo.adapter=adapterPic
                            }
                            else{
                                moreVideo.visibility=View.GONE
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