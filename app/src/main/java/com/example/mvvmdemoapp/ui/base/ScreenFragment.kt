package com.example.mvvmdemoapp.ui.base

import android.content.Context
import android.view.MotionEvent
import com.example.common.engine.ui.base.BaseFragment

abstract class ScreenFragment : BaseFragment() {
    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    open fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        return false
    }

    open fun onBackPressed() {
    }
}