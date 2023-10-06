package com.nas.alreem.commonviews

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.widget.TextView
import com.nas.alreem.R

class CustomFontSansProTextBlack : TextView {
    constructor(context: Context) : super(context) {
        val type = Typeface.createFromAsset(context.assets, "fonts/SourceSansPro-Regular.otf")
        this.setTypeface(type)
        this.setTextColor(context.resources.getColor(R.color.black))
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        val type = Typeface.createFromAsset(context.assets, "fonts/SourceSansPro-Regular.otf")
        this.setTypeface(type)
        this.setTextColor(context.resources.getColor(R.color.black))
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        val type = Typeface.createFromAsset(context.assets, "fonts/SourceSansPro-Regular.otf")
        this.setTypeface(type)
        this.setTextColor(context.resources.getColor(R.color.black))
    }
}
