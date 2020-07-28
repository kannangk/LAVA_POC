package io.aequalis.lava.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.aequalis.lava.R
import io.aequalis.lava.utils.LavaApplication

class Lava : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         LavaApplication.setChildFragmentManager(
            childFragmentManager
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*childFragmentManager.beginTransaction()
            .add(R.id.shareg_main_container, WelcomeFragment.newInstance(1)).commitAllowingStateLoss() */
        childFragmentManager.beginTransaction()
            .add(R.id.lava_main_container, SplashFragment()).commitAllowingStateLoss()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_lava, container, false)
    }
}