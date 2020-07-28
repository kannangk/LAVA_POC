package io.aequalis.lava.utils

import android.app.Application
import android.content.Context
import androidx.fragment.app.FragmentManager

class LavaApplication :Application(){
    companion object {
        private var childFragmentManager: FragmentManager? = null
        private var context: Context? = null


        fun getChildFragmentManager(): FragmentManager {
            return childFragmentManager!!
        }

        fun setChildFragmentManager(childFragmentManager: FragmentManager) {
            this.childFragmentManager = childFragmentManager
        }

    }
    override fun onCreate() {
        super.onCreate()
        context = this
    }
}