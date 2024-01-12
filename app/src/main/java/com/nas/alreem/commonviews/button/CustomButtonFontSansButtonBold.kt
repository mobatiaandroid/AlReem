package com.nas.alreem.commonviews.button

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.widget.Button


class CustomButtonFontSansButtonBold(context: Context, attrs: AttributeSet?) :
    androidx.appcompat.widget.AppCompatButton(context, attrs) {
    private var FONT_NAME: Typeface? = null

    init {
        // TODO Auto-generated constructor stub
        if (FONT_NAME == null) FONT_NAME =
            Typeface.createFromAsset(context.assets, "fonts/SourceSansPro-Bold.otf")
        this.setTypeface(Typeface.DEFAULT_BOLD, Typeface.BOLD)
        this.typeface = FONT_NAME
    }
}
