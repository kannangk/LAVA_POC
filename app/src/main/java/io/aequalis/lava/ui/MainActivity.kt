package io.aequalis.lava.ui

import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import io.aequalis.lava.R
import io.aequalis.lava.utils.LavaApplication

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialize()
    }

    private fun initialize() {
        try {
            supportFragmentManager.beginTransaction()
                .add(R.id.main_container, Lava()).commitAllowingStateLoss()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onBackPressed() {
        try {
            if (LavaApplication.getChildFragmentManager() != null) {
                val mFragmentManager = LavaApplication.getChildFragmentManager()
                if (mFragmentManager.backStackEntryCount > 0) {
                    mFragmentManager.popBackStackImmediate()
                } else
                    finish()
            } else
                finish()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


}
