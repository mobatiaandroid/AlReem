package com.nas.alreem.commonviews.button

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet

class CustomFontButtonBoldNoColor :androidx.appcompat.widget.AppCompatButton{
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        // ...
        val type= Typeface.createFromAsset(context.assets,"fonts/SourceSansPro-Bold.otf")
        this.typeface = type
    }

    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0) {
        val type =
            Typeface.createFromAsset(context.assets, "fonts/SourceSansPro-Bold.otf")
        this.typeface = type
    }
}