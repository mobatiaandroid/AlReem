package com.nas.alreem.constants

import android.app.Activity
import android.graphics.drawable.Drawable
import android.graphics.drawable.StateListDrawable
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.nas.alreem.R
import java.io.Serializable

// TODO: Auto-generated Javadoc
class HeaderManager : Serializable {
    /**
     * Gets the context.
     *
     * @return the context
     */
    /**
     * Sets the context.
     *
     * @param context
     * the new context
     */
    /** The context.  */
    var context: Activity? = null

    /** The inflator.  */
    private var inflator: LayoutInflater

    /** The header view.  */
    lateinit var headerView: View

    /** The m heading1.  */
    private var mHeading1: TextView? = null

    /** The relative params.  */
    private var relativeParams: RelativeLayout.LayoutParams? = null

    /** The heading1.  */
    private var heading1: String? = null

    /** The is cancel.  */
    private val isCancel = false
    private var edtText: EditText? = null
    /* FOR HOME SCREEN */
    /** The is home.  */
    private var isHome = false
    /**
     * Gets the left text.
     *
     * @return the left text
     */
    /**
     * Sets the left text.
     *
     * @param mLeftText
     * the new left text
     */
    /** The m right text.  */
    var leftText: TextView? = null
    /**
     * Gets the right text.
     *
     * @return the right text
     */
    /**
     * Sets the right text.
     *
     * @param mLeftText
     * the new right text
     */
    var rightText: TextView? = null
    /**
     * Gets the left image.
     *
     * @return the left image
     */
    /**
     * Sets the left image.
     *
     * @param mLeftImage
     * the new left image
     */
    /**
     * Gets the right image.
     *
     * @return the right image
     */
    /** The m left.  */
    var rightImage: ImageView? = null
        /**
         * Sets the right image.
         *
         * @param mLeftImage
         * the new right image
         */
        set(mLeftImage) {
            mRightImage = mLeftImage
        }
    private var mRightImage: ImageView? = null
    /**
     * Gets the right button.
     *
     * @return the right button
     */
    /**
     * Sets the right button.
     *
     * @param right
     * the new right button
     */
    var rightButton: ImageView? = null
    /**
     * Gets the left button.
     *
     * @return the left button
     */
    /**
     * Sets the left button.
     *
     * @param right
     * the new left button
     */
    var leftButton: ImageView? = null
    var logoButton: ImageView? = null
        private set
    var infoButton: ImageView? = null
    var historyButton: ImageView? = null
    lateinit var linearRight: LinearLayout

    /**
     * Instantiates a new headermanager.
     *
     * @param context
     * the context
     * @param heading1
     * the heading1
     */
    constructor(context: Activity?, heading1: String?) {
        this.context = context
        inflator = LayoutInflater.from(context)
        this.heading1 = heading1
    }
    /*
	 * public Headermanager(Activity context,String heading1) {
	 * this.setContext(context); inflator = LayoutInflater.from(context);
	 * this.heading1=heading1; this.isCancel=isCancel; }
	 */
    /**
     * Instantiates a new headermanager.
     *
     * @param home
     * the home
     * @param context
     * the context
     */
    constructor(home: Boolean, context: Activity?) {
        this.context = context
        inflator = LayoutInflater.from(context)
        isHome = home
    }
    // image view
    /**
     * Sets the visible.
     *
     * @param v
     * the new visible
     */
    fun setVisible(v: View?) {
        v!!.visibility = View.VISIBLE
    }

    /**
     * Sets the invisible.
     */
    fun setInvisible() {
        headerView!!.visibility = View.INVISIBLE
    }

    /**
     * Sets the invisible.
     *
     * @param v
     * the new invisible
     */
    fun setInvisible(v: View) {
        v.visibility = View.INVISIBLE
    }

    /**
     * Gets the header.
     *
     * @param headerHolder
     * the header holder
     * @return the header
     */
    fun getHeader(headerHolder: RelativeLayout, type: Int): Int {
        initializeUI(type)
        relativeParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        relativeParams!!.addRule(RelativeLayout.ALIGN_PARENT_TOP)
        headerHolder.addView(headerView, relativeParams)
        return headerView!!.id
    }

    /**
     * Gets the header.
     *
     * @param headerHolder
     * the header holder
     * @return the header
     */
    fun getHeader(
        headerHolder: RelativeLayout, getHeading: Boolean,
        type: Int
    ): Int {
        initializeUI(getHeading, type)
        relativeParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        relativeParams!!.addRule(RelativeLayout.ALIGN_PARENT_TOP)
        headerHolder.addView(headerView, relativeParams)
        return headerView!!.id
    }

    /**
     * Initialize ui.
     */
    private fun initializeUI(type: Int) {
        inflator = LayoutInflater.from(context)
        println("htype$type")
        headerView = inflator.inflate(R.layout.common_header_single, null)
        val logoHeader = headerView
            .findViewById<View>(R.id.relative_logo_header) as RelativeLayout
        infoButton = headerView
            .findViewById<View>(R.id.btn_info) as ImageView
        historyButton = headerView
            .findViewById<View>(R.id.btn_history) as ImageView
        mHeading1 = headerView!!.findViewById<View>(R.id.heading) as TextView
        rightButton = headerView!!.findViewById<View>(R.id.btn_right) as ImageView
        leftButton = headerView!!.findViewById<View>(R.id.btn_left) as ImageView
        linearRight = headerView!!.findViewById<LinearLayout>(R.id.linearRight)
        logoButton = headerView!!.findViewById<View>(R.id.logoClickImgView) as ImageView
        if (type == 0) {
            logoHeader.setBackgroundResource(R.drawable.titlebar) // two
            infoButton!!.visibility = View.GONE
            historyButton!!.visibility = View.GONE
            // buttons
        } else if (type == 1) {
            logoHeader.setBackgroundResource(R.drawable.titlebar) // left
            infoButton!!.visibility = View.GONE
            historyButton!!.visibility = View.GONE
            // button
        } else if (type == 3) {
            logoHeader.setBackgroundResource(R.drawable.titlebar) // left
            infoButton!!.visibility = View.VISIBLE
            historyButton!!.visibility = View.GONE
            // button
        } else if (type == 4) {
            logoHeader.setBackgroundResource(R.drawable.titlebar) // left
            infoButton!!.visibility = View.GONE
            historyButton!!.visibility = View.GONE
            linearRight.setVisibility(View.VISIBLE)
            // button
        } else if (type == 5) {
            logoHeader.setBackgroundResource(R.drawable.titlebar) // left
            infoButton!!.visibility = View.VISIBLE
            historyButton!!.visibility = View.GONE
            linearRight.setVisibility(View.GONE)
            // button
        } else if (type == 6) {
            logoHeader.setBackgroundResource(R.drawable.titlebar) // left
            infoButton!!.visibility = View.GONE
            historyButton!!.visibility = View.VISIBLE
            linearRight.setVisibility(View.GONE)
            historyButton!!.setImageResource(R.drawable.payment_history)
            // button
        } else if (type == 7) {
            logoHeader.setBackgroundResource(R.drawable.titlebar) // left
            infoButton!!.visibility = View.GONE
            historyButton!!.visibility = View.VISIBLE
            linearRight.setVisibility(View.GONE)
            historyButton!!.setImageResource(R.drawable.basket)
            // button
        } else if (type == 8) {
            logoHeader.setBackgroundResource(R.drawable.titlebar) // left
            infoButton!!.visibility = View.GONE
            historyButton!!.visibility = View.VISIBLE
            linearRight.setVisibility(View.GONE)
          //  historyButton!!.setImageResource(R.drawable.invoice)
        }
        mHeading1!!.text = heading1
        mHeading1!!.setBackgroundColor(context!!.resources.getColor(R.color.split_bg))
    }

    /**
     * Initialize ui.
     */
    private fun initializeUI(getHeading: Boolean, type: Int) {
        inflator = LayoutInflater.from(context)
        headerView = inflator.inflate(R.layout.common_header_single, null)
        val logoHeader = headerView
            .findViewById<View>(R.id.relative_logo_header) as RelativeLayout
        infoButton = headerView
            .findViewById<View>(R.id.btn_info) as ImageView
        historyButton = headerView
            .findViewById<View>(R.id.btn_history) as ImageView
        rightButton = headerView!!.findViewById<View>(R.id.btn_right) as ImageView
        linearRight = headerView!!.findViewById<LinearLayout>(R.id.linearRight)
        leftButton = headerView!!.findViewById<View>(R.id.btn_left) as ImageView
        logoButton = headerView!!.findViewById<View>(R.id.logoClickImgView) as ImageView
        mHeading1 = headerView!!.findViewById<View>(R.id.heading) as TextView
        if (type == 0) {
            logoHeader.setBackgroundResource(R.drawable.titlebar)
            infoButton!!.visibility = View.GONE
            historyButton!!.visibility = View.GONE
        } else if (type == 1) {
            logoHeader.setBackgroundResource(R.drawable.titlebar)
            mHeading1!!.visibility = View.GONE
            infoButton!!.visibility = View.GONE
            historyButton!!.visibility = View.GONE
        } else if (type == 3) {
            logoHeader.setBackgroundResource(R.drawable.titlebar) // left
            infoButton!!.visibility = View.VISIBLE
            historyButton!!.visibility = View.GONE
            // button
        } else if (type == 4) {
            logoHeader.setBackgroundResource(R.drawable.titlebar) // left
            infoButton!!.visibility = View.GONE
            historyButton!!.visibility = View.GONE
            linearRight.setVisibility(View.VISIBLE)
            // button
        } else if (type == 5) {
            logoHeader.setBackgroundResource(R.drawable.titlebar) // left
            infoButton!!.visibility = View.VISIBLE
            linearRight.setVisibility(View.GONE)
            historyButton!!.visibility = View.GONE
            // button
        } else if (type == 6) {
            logoHeader.setBackgroundResource(R.drawable.titlebar) // left
            infoButton!!.visibility = View.GONE
            historyButton!!.visibility = View.VISIBLE
            // button
        }
        /*
		 * else if(type==2) {
		 * logoHeader.setBackgroundResource(R.drawable.titlebars); }
		 */


//		edtText = (EditText) headerView.findViewById(R.id.edtTxt_Header);
        mHeading1!!.text = heading1
        mHeading1!!.setBackgroundColor(context!!.resources.getColor(R.color.split_bg))
    }

    /**
     * Sets the title bar.
     *
     * @param titleBar
     * the new title bar
     */
    fun setTitleBar(titleBar: Int) {
        headerView!!.setBackgroundResource(titleBar)
    }

    fun setTitle(title: String?) {
        mHeading1!!.text = title
    }

    var editText: EditText?
        get() {
            mHeading1!!.visibility = View.GONE
            edtText!!.visibility = View.VISIBLE
            return edtText
        }
        /**
         * Sets the edits the text.
         *
         * @param editText
         * the new edits the text
         */
        set(editText) {
            edtText = editText
            setVisible(edtText)
        }

    /**
     * Sets the button right selector.
     *
     * @param normalStateResID
     * the normal state res id
     * @param pressedStateResID
     * the pressed state res id
     */
    fun setButtonRightSelector(
        normalStateResID: Int,
        pressedStateResID: Int
    ) {
        if (normalStateResID == R.drawable.settings) {
            rightButton!!.setBackgroundResource(R.drawable.settings)
        }
        //		else if (normalStateResID==R.drawable.help)
//		{
//			mRight.setBackgroundResource(R.drawable.help);
//		}
//		else if (normalStateResID==R.drawable.share)
//		{
//			mRight.setBackgroundResource(R.drawable.share);
//		}
//		else
//		{
//			mRight.setBackgroundResource(R.drawable.settings);
//
//		}
        rightButton!!.setImageDrawable(
            getButtonDrawableByScreenCathegory(
                normalStateResID, pressedStateResID
            )
        )
        setVisible(rightButton)
    }

    /**
     * Sets the button left selector.
     *
     * @param normalStateResID
     * the normal state res id
     * @param pressedStateResID
     * the pressed state res id
     */
    fun setButtonLeftSelector(
        normalStateResID: Int,
        pressedStateResID: Int
    ) {
        leftButton!!.setImageDrawable(
            getButtonDrawableByScreenCathegory(
                normalStateResID, pressedStateResID
            )
        )
        setVisible(leftButton)
    }

    fun setButtonInfoSelector(
        normalStateResID: Int,
        pressedStateResID: Int
    ) {
        infoButton!!.setImageDrawable(
            getButtonDrawableByScreenCathegory(
                normalStateResID, pressedStateResID
            )
        )
        setVisible(infoButton)
    }

    fun setButtonHistorySelector(
        normalStateResID: Int,
        pressedStateResID: Int
    ) {
        historyButton!!.setImageDrawable(
            getButtonDrawableByScreenCathegory(
                normalStateResID, pressedStateResID
            )
        )
        setVisible(historyButton)
    }

    /**
     * Gets the button drawable by screen cathegory.
     *
     * @param normalStateResID
     * the normal state res id
     * @param pressedStateResID
     * the pressed state res id
     * @return the button drawable by screen cathegory
     */
    fun getButtonDrawableByScreenCathegory(
        normalStateResID: Int,
        pressedStateResID: Int
    ): Drawable {
        val state_normal = context!!.resources
            .getDrawable(normalStateResID).mutate()
        val state_pressed = context!!.resources
            .getDrawable(pressedStateResID).mutate()
        val drawable = StateListDrawable()
       /* drawable.addState(
            intArrayOf(R.attr.state_pressed),
            state_pressed
        )*/
        /*drawable.addState(
            intArrayOf(R.attr.state_enabled),
            state_normal
        )*/
        return drawable
    }
    // public void setCancelButton()
    // {
    // mRight.setBackgroundResource(R.drawable.close_button_selector);
    // setVisible(mRight);
    // }
}
