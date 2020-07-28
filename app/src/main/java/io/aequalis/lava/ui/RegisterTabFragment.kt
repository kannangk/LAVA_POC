package io.aequalis.lava.ui

import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import io.aequalis.lava.R
import io.aequalis.lava.adapter.LavaViewPageAdapter
import io.aequalis.lava.utils.NavigationIcons
import io.aequalis.lava.utils.UserInterface

class RegisterTabFragment : Fragment() {
    var tabView: LinearLayout? = null
    var viewPager: ViewPager? = null
    var apiTab: NavigationIcons? = null
    var searchTab: NavigationIcons? = null
    var adapter: LavaViewPageAdapter? = null
    var back: ImageView? = null
    var appBarLayout: AppBarLayout? = null
    var bgImage: ImageView? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.register_tab, container, false)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = activity!!.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resources.getColor(R.color.app_blue, null)
        }
        tabView = view.findViewById(R.id.tab_layout)
        viewPager = view.findViewById(R.id.viewPager)
        appBarLayout = view.findViewById(R.id.appbar)
        back = view.findViewById(R.id.iv_back)
        bgImage = view.findViewById(R.id.bgImage)
        setTabView()

        viewPager!!.offscreenPageLimit = 2
        adapter = LavaViewPageAdapter(childFragmentManager)
        adapter!!.add(RegisterFragment())
        adapter!!.add(APIKeysFragment())
        viewPager!!.adapter = adapter
        back!!.setOnClickListener{activity!!.onBackPressed()}
        viewPager!!.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> {
                        searchTab!!.performClick()
                    }
                    1 -> {
                        apiTab!!.performClick()
                    }
                }
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })

        apiTab!!.setOnClickListener {
            viewPager!!.currentItem = 1
            apiTab!!.setState(true)
            searchTab!!.setState(false)
            UserInterface.hideKeyboard(context!!,view)
        }

        searchTab!!.setOnClickListener {
            viewPager!!.currentItem = 0
            apiTab!!.setState(false)
            searchTab!!.setState(true)
            UserInterface.hideKeyboard(context!!,view)
        }

        viewPager!!.currentItem = 0

        val collapsingToolbar = view.findViewById<CollapsingToolbarLayout>(R.id.collapsing_toolbar)
        val face = Typeface.createFromAsset(activity!!.assets, "SFUIText_Bold.ttf")
        collapsingToolbar.setCollapsedTitleTypeface(face)
        collapsingToolbar.setExpandedTitleTypeface(face)
        collapsingToolbar.title = "Search Register"

        appBarLayout?.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (Math.abs(verticalOffset) - appBarLayout.totalScrollRange == 0) {
                //  Collapsed
                bgImage?.visibility=View.GONE

            } else {
                //Expanded
                bgImage?.visibility=View.VISIBLE

            }
        })

        return view
    }


    private fun setTabView() {
        searchTab = NavigationIcons(
            activity!!,
            "Title Search",
            true
        )
        apiTab = NavigationIcons(
            activity!!,
            "API",
            false
        )
        tabView!!.addView(searchTab)
        tabView!!.addView(apiTab)
    }

}