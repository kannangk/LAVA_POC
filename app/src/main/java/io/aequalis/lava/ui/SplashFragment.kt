package io.aequalis.lava.ui

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import io.aequalis.lava.R

class SplashFragment :Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.splash, container, false)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = activity!!.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resources.getColor(R.color.colorPrimaryDark, null)
        }
        standLoad()
        return view
    }

    //wait for 1 seconds
    private fun standLoad() {
        try {
            Handler().postDelayed({
                try {
                    fragmentManager!!.beginTransaction()
                        .replace(R.id.lava_main_container, TenantChooseFragment()).commitAllowingStateLoss()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }, 1000)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}