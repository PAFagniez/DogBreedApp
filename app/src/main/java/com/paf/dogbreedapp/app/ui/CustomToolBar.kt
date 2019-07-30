package com.paf.dogbreedapp.app.ui

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.paf.dogbreedapp.R
import com.paf.dogbreedapp.app.utils.WeakReference
import kotlinx.android.synthetic.main.toolbar_view.view.*

interface OnClickBackToolbarListener {
    fun onClickBackMenuListener()
}

class CustomToolBar(context: Context, attrs: AttributeSet?): Toolbar(context, attrs) {

    private var backListener: OnClickBackToolbarListener? by WeakReference()

    init {
        View.inflate(context, R.layout.toolbar_view, this)
        iconBack.setOnClickListener { backListener?.onClickBackMenuListener() }
    }

    fun setBackListener(listener: OnClickBackToolbarListener): CustomToolBar{
        this.backListener = listener
        return this
    }

    fun setToolbarTitle(title: String): CustomToolBar{
        titleToolBar.text = title
        return this
    }

    fun hideBackArrow(): CustomToolBar{
        iconBack.visibility = View.GONE
        return this
    }

}