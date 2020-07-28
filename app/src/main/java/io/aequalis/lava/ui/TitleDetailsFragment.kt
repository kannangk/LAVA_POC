package io.aequalis.lava.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.aequalis.lava.R
import org.json.JSONObject

class TitleDetailsFragment : Fragment() {
    var resObj: JSONObject? = null

    companion object {
        fun newInstance(resObj: JSONObject): TitleDetailsFragment {
            val fragment = TitleDetailsFragment()
            fragment.resObj = resObj
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.result_view_details, container, false)
        return view
    }

}