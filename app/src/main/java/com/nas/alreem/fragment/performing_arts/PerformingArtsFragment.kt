package com.nas.alreem.fragment.performing_arts

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nas.alreem.R
import com.nas.alreem.fragment.primary.model.PrimaryDataModel
import com.nas.alreem.recyclermanager.DividerItemDecoration
import com.nas.alreem.recyclermanager.ItemOffsetDecoration
import com.nas.alreem.recyclermanager.RecyclerItemListener

class PerformingArtsFragment : Fragment(){
    lateinit var mTitleTextView: TextView
    lateinit var descriptionTV: TextView
   lateinit var descriptionTitle: TextView
    lateinit var text_content: TextView
   lateinit var text_dialog: TextView
    private val mRootView: View? = null
    lateinit var mContext: Context

    //	private ListView mListView;
    lateinit var mListView: RecyclerView
    private val mTitle: String? = null
    private val mTabId: String? = null
    lateinit var  relMain: RelativeLayout
    lateinit var mtitleRel:RelativeLayout
    private val mListViewArray: ArrayList<PrimaryDataModel>? = null

    //	ViewPager bannerImagePager;
    var bannerImagePager: ImageView? = null
    var bannerUrlImageArray: ArrayList<String>? = null
    var mailImageView: ImageView? = null
    var description = ""
    var contactEmail = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_performingart_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mContext = requireContext()

        //		Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(mContext));
        initialiseUI()


    }
    private fun initialiseUI() {
        mTitleTextView = mRootView!!.findViewById<View>(R.id.titleTextView) as TextView
        mTitleTextView.setText("PERFORMING_ARTS")
        //		mListView = (ListView) mRootView.findViewById(R.id.mPerformingArtListView);
        mListView = mRootView.findViewById<View>(R.id.mPerformingArtListView) as RecyclerView
        bannerImagePager = mRootView.findViewById<View>(R.id.bannerImagePager) as ImageView
        //		bannerImagePager= (ViewPager) mRootView.findViewById(R.id.bannerImagePager);
        descriptionTV = mRootView.findViewById<View>(R.id.descriptionTV) as TextView
        descriptionTitle = mRootView.findViewById<View>(R.id.descriptionTitle) as TextView
        mailImageView = mRootView.findViewById<View>(R.id.mailImageView) as ImageView
        mtitleRel = mRootView.findViewById<View>(R.id.title) as RelativeLayout
        relMain = mRootView.findViewById<View>(R.id.relMain) as RelativeLayout
        relMain.setOnClickListener { }
        //		mListView.setOnItemClickListener(this);
        mailImageView!!.setOnClickListener {
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
               // sendEmailToStaff(URL_SEND_EMAIL_TO_STAFF)
            }
            dialog.show()
        }

//        mNewsLetterListView.addItemDecoration(new DividerItemDecoration(getResources().getDrawable(R.drawable.list_divider)));
        /*GridLayoutManager recyclerViewLayout= new GridLayoutManager(mContext, 4);
		int spacing = 5; // 50px
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(mContext,spacing);
		mListView.addItemDecoration(itemDecoration);
		mListView.setLayoutManager(recyclerViewLayout);*/mListView.setHasFixedSize(true)
        val llm = LinearLayoutManager(mContext)
        llm.orientation = LinearLayoutManager.VERTICAL
        val spacing = 5 // 50px
        val itemDecoration = ItemOffsetDecoration(mContext, spacing)
        mListView.addItemDecoration(itemDecoration)
        mListView.setLayoutManager(llm)
        mListView.addItemDecoration(DividerItemDecoration(resources.getDrawable(R.drawable.list_divider)))
        mListView.addOnItemTouchListener(
            RecyclerItemListener(
                activity, mListView,
                object : RecyclerItemListener.RecyclerTouchListener {
                    override fun onClickItem(v: View?, position: Int) {
                        if (mListViewArray!![position].getmFile().endsWith(".pdf")) {
                            val intent = Intent(
                                mContext,
                                PdfReaderActivity::class.java
                            )
                            intent.putExtra("pdf_url", mListViewArray[position].getmFile())
                            startActivity(intent)
                        } else {
                            val intent = Intent(
                                mContext,
                                LoadUrlWebViewActivity::class.java
                            )
                            intent.putExtra("url", mListViewArray[position].getmFile())
                            intent.putExtra("tab_type", mListViewArray[position].getmName())
                            mContext.startActivity(intent)
                        }
                    }

                    override fun onLongClickItem(v: View?, position: Int) {
                        println("On Long Click Item interface")
                    }
                })
        )
        if (AppUtils.checkInternet(mContext)) {
            getList()
        } else {
            AppUtils.showDialogAlertDismiss(
                mContext as Activity,
                "Network Error",
                getString(R.string.no_internet),
                R.drawable.nonetworkicon,
                R.drawable.roundred
            )
        }
        if (!description.equals("", ignoreCase = true)) {
            descriptionTV!!.visibility = View.VISIBLE
            descriptionTV!!.text = description
            descriptionTitle!!.visibility = View.GONE
            //			descriptionTitle.setVisibility(View.VISIBLE);
        } else {
            descriptionTV!!.visibility = View.GONE
            descriptionTitle!!.visibility = View.GONE
        }
        if (!contactEmail.equals("", ignoreCase = true) && !PreferenceManager.getUserId(mContext)
                .equals("")
        ) {
            mailImageView!!.visibility = View.VISIBLE
        } else {
            mailImageView!!.visibility = View.INVISIBLE
        }
    }

    fun onItemClick(
        parent: AdapterView<*>?, view: View?, position: Int,
        id: Long
    ) {
        if (mListViewArray!![position].getmFile().endsWith(".pdf")) {
            val intent = Intent(mContext, PdfReaderActivity::class.java)
            intent.putExtra("pdf_url", mListViewArray[position].getmFile())
            startActivity(intent)
        } else {
            val intent = Intent(
                mContext,
                LoadUrlWebViewActivity::class.java
            )
            intent.putExtra("url", mListViewArray[position].getmFile())
            intent.putExtra("tab_type", mListViewArray[position].getmName())
            mContext.startActivity(intent)
        }
    }
}