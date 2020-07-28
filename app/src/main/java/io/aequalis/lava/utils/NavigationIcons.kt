package io.aequalis.lava.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import io.aequalis.lava.R

class NavigationIcons : LinearLayout {
    private var mTextView: TextView? = null
    private var main_lay: LinearLayout? = null
    private var iv_selector: View? = null
    private var active: Boolean = false

    constructor(context: Context, title: String,isActive: Boolean) : super(
        context
    ) {
        init(context, title, isActive)
    }


    private fun init(
        context: Context,
        title: String,
        isActive: Boolean
    ) {
        this.active = isActive
        LayoutInflater.from(context).inflate(R.layout.layout_bottom_icon, this)

        mTextView = findViewById(R.id.textNavigation)
        main_lay = findViewById(R.id.main_lay)
        iv_selector = findViewById(R.id.iv_selector)
        mTextView!!.setText(title)

        setState(active)
        main_lay!!.setBackgroundColor(resources.getColor(R.color.white))
        val layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
        setLayoutParams(layoutParams)

    }


    fun setState(state: Boolean) {
        if (state) {
            mTextView!!.setTextColor(context.resources.getColor(R.color.blue_txt))
            iv_selector!!.visibility = View.VISIBLE
        } else {
            mTextView!!.setTextColor(context.resources.getColor(R.color.light_grey))
            iv_selector!!.visibility = View.GONE
        }
    }

}
