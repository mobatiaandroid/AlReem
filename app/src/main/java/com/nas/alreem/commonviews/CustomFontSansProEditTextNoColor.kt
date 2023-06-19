package com.nas.alreem.commonviews

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.widget.EditText

class CustomFontSansProEditTextNoColor : EditText {
    constructor(context: Context) : super(context) {
        val type = Typeface.createFromAsset(context.assets, "fonts/SourceSansPro-Regular.otf")
        this.typeface = type
        //        this.setTextColor(context.getResources().getColor(R.color.white));
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        val type = Typeface.createFromAsset(context.assets, "fonts/SourceSansPro-Regular.otf")
        this.typeface = type
        //       this.setTextColor(context.getResources().getColor(R.color.white));
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        val type = Typeface.createFromAsset(context.assets, "fonts/SourceSansPro-Regular.otf")
        this.typeface = type
        //        this.setTextColor(context.getResources().getColor(R.color.white));
    }
}