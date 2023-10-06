package com.nas.alreem.commonviews.button

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import com.nas.alreem.R


class CustomFontButtonBoldBlack :androidx.appcompat.widget.AppCompatButton{
    @SuppressLint("ResourceAsColor")
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        // ...
        val type= Typeface.createFromAsset(context.assets,"fonts/SourceSansPro-Bold.otf")
        this.typeface = type
        this.setTextColor(R.color.black)
    }

    @SuppressLint("ResourceAsColor")
    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0) {
        val type =
            Typeface.createFromAsset(context.assets, "fonts/SourceSansPro-Bold.otf")
        this.typeface = type
        this.setTextColor(R.color.black)
    }
}