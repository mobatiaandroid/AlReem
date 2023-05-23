package com.nas.alreem.activity.gallery.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.nas.alreem.R
import com.nas.alreem.activity.gallery.model.AlbumImageModel
import com.nas.alreem.activity.gallery.model.PhotosModel

class PhotosAdapter (private var imageVideoArray: ArrayList<PhotosModel>, var context: Context) :
    RecyclerView.Adapter<PhotosAdapter.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var imgView: ImageView = view.findViewById(R.id.imgView)
        var photoTitle: TextView = view.findViewById(R.id.photoTitle)
        var photoDescription: TextView = view.findViewById(R.id.photoDescription)
    }
    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        if(!imageVideoArray.get(position).image.equals(""))
        {
            Glide.with(context) //1
                .load(imageVideoArray.get(position).image)
                .placeholder(R.drawable.student)
                .error(R.drawable.student)
                .skipMemoryCache(true) //2
                .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                .transform(CircleCrop()) //4
                .into(holder.imgView)
        }
        else{
             holder.imgView.setImageResource(R.drawable.student)
        }
//        holder.photoTitle.text=imageVideoArray.get(position).title
//        holder.photoDescription.text=imageVideoArray.get(position).description

    }
    override fun getItemCount(): Int {

        return imageVideoArray.size

    }
}