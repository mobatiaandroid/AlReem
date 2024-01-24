package com.nas.alreem.activity.communication.socialmedia.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nas.alreem.R
import com.nas.alreem.activity.communication.socialmedia.model.SocialMediaModel


class SocialMediaAdapter(var mContext: Context, mSocialMediaModels: ArrayList<SocialMediaModel>) :
    RecyclerView.Adapter<SocialMediaAdapter.MyViewHolder>() {
    var mSocialMediaModels: ArrayList<SocialMediaModel>
    var iconImage: Drawable? = null

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var imgIcon: ImageView
        var listTxtView: TextView

        init {
            imgIcon = view.findViewById<View>(R.id.imagicon) as ImageView
            listTxtView = view.findViewById<View>(R.id.listTxtTitle) as TextView
        }
    }

    init {
        this.mSocialMediaModels = mSocialMediaModels
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_social_media_adapter, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (mSocialMediaModels[position].tab_type.startsWith("Facebook")) {
            holder.imgIcon.setImageResource(R.drawable.facebookiconmedia)
            val sdk = Build.VERSION.SDK_INT
            if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
                holder.imgIcon.setBackgroundDrawable(mContext.resources.getDrawable(R.drawable.roundfb))
            } else {
                holder.imgIcon.background = mContext.resources.getDrawable(R.drawable.roundfb)
            }
            holder.listTxtView.setText(
                mSocialMediaModels[position].tab_type.replace("Facebook:", " ")
            )

            //holder.imgIcon.setBackgroundDrawable(R.drawable.roundfb);
        } else if (mSocialMediaModels[position].tab_type.startsWith("X")) {
            holder.imgIcon.setImageResource(R.drawable.twittericon)
            val sdk = Build.VERSION.SDK_INT
            if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
                holder.imgIcon.setBackgroundDrawable(mContext.resources.getDrawable(R.drawable.roundtw))
            } else {
                holder.imgIcon.background = mContext.resources.getDrawable(R.drawable.roundtw)
            }
            //holder.imgIcon.setBackground(mContext.getDrawable(R.drawable.roundtw));
            holder.listTxtView.setText(
                mSocialMediaModels[position].tab_type.replace("X:", " ")
            )
        } else if (mSocialMediaModels[position].tab_type.startsWith("Instagram")) {
            holder.imgIcon.setImageResource(R.drawable.instagramicon)
            val sdk = Build.VERSION.SDK_INT
            if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
                holder.imgIcon.setBackgroundDrawable(mContext.resources.getDrawable(R.drawable.roundins))
            } else {
                holder.imgIcon.background = mContext.resources.getDrawable(R.drawable.roundins)
            }
            holder.listTxtView.setText(
                mSocialMediaModels[position].tab_type.replace("Instagram:", " ")
            )
        }
    }

    override fun getItemCount(): Int {
        println("Adapter---size" + mSocialMediaModels.size)
        return mSocialMediaModels.size
    }
}
