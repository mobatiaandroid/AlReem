package com.nas.alreem.fragment.parent_engagement

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nas.alreem.R
import com.nas.alreem.recyclermanager.DividerItemDecoration
import com.nas.alreem.recyclermanager.ItemOffsetDecoration
import com.nas.alreem.recyclermanager.RecyclerItemListener

class ParentAssociationsFragment :  Fragment() {
    private var mRootView: View? = null
    private var mTitle: String? = null
    private var mTabId: String? = null
    private var relMain: RelativeLayout? = null
    lateinit var mTitleTextView: TextView
    var dataArrayStrings: ArrayList<String?> = object : ArrayList<String?>() {
        init {
            add("Parents' Association")
            add("Chatter Box Café")
            add("Class Representatives")
            //add("Chatter Box Café");
        }
    }
    lateinit var mContext: Context
    var imageViewSlotInfo: ImageView? = null
    lateinit var mRecyclerView: RecyclerView

    fun ParentAssociationsFragment() {}

    fun ParentAssociationsFragment(title: String?, tabId: String?) {
        mTitle = title
        mTabId = tabId
    }

    /*
	 * (non-Javadoc)
	 *
	 * @see
	 * android.support.v4.app.Fragment#onCreateOptionsMenu(android.view.Menu,
	 * android.view.MenuInflater)
	 */


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mRootView = inflater.inflate(
            R.layout.fragmentparentsactivityrecyclerview, container,
            false
        )
        //		setHasOptionsMenu(true);
        mContext = requireActivity()
        //		Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(mContext));
        initialiseUI()
        return mRootView
    }

    /*******************************************************
     * Method name : initialiseUI Description : initialise UI elements
     * Parameters : nil Return type : void Date : Jan 12, 2015 Author : Vandana
     * Surendranath
     */
    private fun initialiseUI() {
        mRecyclerView = mRootView!!.findViewById<View>(R.id.mRecyclerView) as RecyclerView
        imageViewSlotInfo = mRootView!!.findViewById<View>(R.id.imageViewSlotInfo) as ImageView
        imageViewSlotInfo!!.visibility = View.GONE
        val marginLayoutParams = mRecyclerView!!.layoutParams as ViewGroup.MarginLayoutParams
        marginLayoutParams.setMargins(10, 0, 10, 0)
        mRecyclerView!!.layoutParams = marginLayoutParams
        mRecyclerView!!.setHasFixedSize(true)
        mTitleTextView = mRootView!!.findViewById<View>(R.id.titleTextView) as TextView
        mTitleTextView.setText("Parents' Association")
        relMain = mRootView!!.findViewById<View>(R.id.relMain) as RelativeLayout
        relMain!!.setOnClickListener { }
        val llm = LinearLayoutManager(mContext)
        llm.orientation = LinearLayoutManager.VERTICAL
        mRecyclerView!!.layoutManager = llm
        val itemDecoration = ItemOffsetDecoration(mContext, 5)
        mRecyclerView!!.addItemDecoration(itemDecoration)
        //mAboutUsList.setLayoutManager(recyclerViewLayoutManager);
        mRecyclerView!!.addItemDecoration(
            DividerItemDecoration(mContext!!.resources.getDrawable(R.drawable.list_divider))
        )
        val adapter = ParentsAssociationMainAdapter(mContext, dataArrayStrings)
        mRecyclerView!!.adapter = adapter
        mRecyclerView!!.addOnItemTouchListener(
            RecyclerItemListener(mContext, mRecyclerView,
                object : RecyclerItemListener.RecyclerTouchListener{
                    override fun onClickItem(v: View?, position: Int) {
                        if (dataArrayStrings[position] == "Parents' Association") {
                           /* val intent = Intent(
                                mContext,
                                ParentsAssociationSignUpActivity::class.java
                            )
                            intent.putExtra("tab_type", dataArrayStrings[position])
                            mContext!!.startActivity(intent)*/
                        } else if (dataArrayStrings[position] == "Chatter Box Café") {
                           /* val intent = Intent(
                                mContext,
                                ChatterBoxActivityNew::class.java
                            ) //ChatterBoxActivity
                            intent.putExtra("tab_type", dataArrayStrings[position])
                            mContext!!.startActivity(intent)*/
                        } else if (dataArrayStrings[position] == "Class Representatives") {
                           /* val intent = Intent(
                                mContext,
                                ClassRepresentativeActivity::class.java
                            ) //ClassRepresentativeActivity
                            intent.putExtra("tab_type", dataArrayStrings[position])
                            mContext!!.startActivity(intent)*/
                        }
                    }

                    override fun onLongClickItem(v: View?, position: Int) {}
                })
        )
    }


    internal class ParentsAssociationMainAdapter(
        private val mContext: Context?,
        private val mStaffList: ArrayList<String?>
    ) :
        RecyclerView.Adapter<ParentsAssociationMainAdapter.MyViewHolder>() {
        inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            var mTitleTxt: TextView

            init {
                mTitleTxt = view.findViewById<View>(R.id.listTxtTitle) as TextView
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val itemView: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.custom_aboutus_list_adapter, parent, false)
            return MyViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.mTitleTxt.text = mStaffList[position]
        }

        override fun getItemCount(): Int {
            return mStaffList.size
        }
    }

}