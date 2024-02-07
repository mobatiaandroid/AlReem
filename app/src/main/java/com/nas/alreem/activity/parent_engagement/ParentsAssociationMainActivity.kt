package com.nas.alreem.activity.parent_engagement

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nas.alreem.R
import com.nas.alreem.activity.home.HomeActivity
import com.nas.alreem.constants.HeaderManager
import com.nas.alreem.recyclermanager.DividerItemDecoration
import com.nas.alreem.recyclermanager.ItemOffsetDecoration
import com.nas.alreem.recyclermanager.RecyclerItemListener


class ParentsAssociationMainActivity : AppCompatActivity() {
    var dataArrayStrings: ArrayList<String> = object : ArrayList<String>() {
        init {
            add("Parents' Association")
            add("Chatter Box Café")
            add("Class Representative")
            //add("Chatter Box Café");
        }
    }
    lateinit var mContext: Context
    lateinit var relativeHeader: RelativeLayout
    lateinit var headermanager: HeaderManager
    lateinit var back: ImageView
    lateinit var home: ImageView
    lateinit var imageViewSlotInfo: ImageView
    lateinit var mRecyclerView: RecyclerView
    var extras: Bundle? = null
    var tab_type: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        setContentView(R.layout.parentsactivityrecyclerview)
        initUI()
    }

    private fun initUI() {
        extras = intent.extras
        if (extras != null) {
            tab_type = extras!!.getString("tab_type")
        }
        relativeHeader = findViewById<View>(R.id.relativeHeader) as RelativeLayout
        mRecyclerView = findViewById<View>(R.id.mRecyclerView) as RecyclerView
        imageViewSlotInfo = findViewById<View>(R.id.imageViewSlotInfo) as ImageView
        imageViewSlotInfo!!.visibility = View.GONE
        val marginLayoutParams = mRecyclerView!!.layoutParams as ViewGroup.MarginLayoutParams
        marginLayoutParams.setMargins(10, 0, 10, 0)
        mRecyclerView!!.layoutParams = marginLayoutParams
        mRecyclerView!!.setHasFixedSize(true)
        headermanager = HeaderManager(this@ParentsAssociationMainActivity, tab_type)
        headermanager.getHeader(relativeHeader, 0)
        back = headermanager.leftButton!!
        headermanager.setButtonLeftSelector(
            R.drawable.back,
            R.drawable.back
        )
        back!!.setOnClickListener {

            finish()
        }
        home = headermanager.logoButton!!
        home!!.setOnClickListener {
            val `in` = Intent(
                mContext,
                HomeActivity::class.java
            )
            `in`.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(`in`)
        }
        val llm = LinearLayoutManager(this)
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
                object : RecyclerItemListener.RecyclerTouchListener {
                    override fun onClickItem(v: View?, position: Int) {
                        if (dataArrayStrings[position] == "Parents' Association") {
                            val intent = Intent(
                                mContext,
                                ParentsAssociationSignUpActivity::class.java
                            )
                            intent.putExtra("tab_type", dataArrayStrings[position])
                            mContext!!.startActivity(intent)
                        } else if (dataArrayStrings[position] == "Chatter Box Café") {
                            val intent = Intent(
                                mContext,
                                ChatterBoxActivityNew::class.java
                            ) //ChatterBoxActivity
                            intent.putExtra("tab_type", dataArrayStrings[position])
                            mContext!!.startActivity(intent)
                        } else if (dataArrayStrings[position] == "Class Representative") {
                            val intent = Intent(
                                mContext,
                                ClassRepresentativeActivity::class.java
                            ) //ClassRepresentativeActivity
                            intent.putExtra("tab_type", dataArrayStrings[position])
                            mContext!!.startActivity(intent)
                        }
                    }

                    override fun onLongClickItem(v: View?, position: Int) {}
                })
        )
    }
}


internal class ParentsAssociationMainAdapter(
    private val mContext: Context?,
    private val mStaffList: ArrayList<String>
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



