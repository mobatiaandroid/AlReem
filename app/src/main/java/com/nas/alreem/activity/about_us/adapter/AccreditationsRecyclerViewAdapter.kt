package com.nas.alreem.activity.about_us.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.nas.alreem.R
import com.nas.alreem.fragment.about_us.model.AboutUsDataModel
import com.nas.alreem.fragment.about_us.model.AboutusList

class AccreditationsRecyclerViewAdapter(
    private val mContext: Context,
    aboutusModelArrayList: ArrayList<AboutusList>
) : BaseAdapter(){
    private val mAboutusLists: ArrayList<String>? = null
    private val aboutusModelArrayList: ArrayList<AboutusList>
    private var view: View? = null
    lateinit var mTitleTxt: TextView
    private val mImageView: ImageView? = null
    private val mTitle: String? = null
    private val mTabId: String? = null

    //	public CustomAboutUsAdapter(Context context,
    //								ArrayList<AboutUsModel> arrList, String title, String tabId) {
    //		this.mContext = context;
    //		this.mAboutusList = arrList;
    //		this.mTitle = title;
    //		this.mTabId = tabId;
    //	}
    init {
        this.aboutusModelArrayList = aboutusModelArrayList
    }

    //	public CustomAboutUsAdapter(Context context,
    //								ArrayList<String> arrList) {
    //		this.mContext = context;
    //		this.mAboutusList = arrList;
    //
    //	}
    /*
     * (non-Javadoc)
     *
     * @see android.widget.Adapter#getCount()
     */
    override fun getCount(): Int {
        // TODO Auto-generated method stub
        return aboutusModelArrayList.size
    }

    /*
     * (non-Javadoc)
     *
     * @see android.widget.Adapter#getItem(int)
     */
    override fun getItem(position: Int): Any {
        // TODO Auto-generated method stub
        return aboutusModelArrayList[position]
    }

    /*
     * (non-Javadoc)
     *
     * @see android.widget.Adapter#getItemId(int)
     */
    override fun getItemId(position: Int): Long {
        // TODO Auto-generated method stub
        return position.toLong()
    }

    /*
     * (non-Javadoc)
     *
     * @see android.widget.Adapter#getView(int, android.view.View,
     * android.view.ViewGroup)
     */
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        if (convertView == null) {
            val inflate = LayoutInflater.from(mContext)
            view = inflate.inflate(R.layout.adapter_primary_list, null)
        } else {
            view = convertView
        }
        try {
            mTitleTxt = view!!.findViewById<View>(R.id.listTxtTitle) as TextView
            mTitleTxt.setText(aboutusModelArrayList[position].title)
        } catch (e: IndexOutOfBoundsException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return view!!
    }
}