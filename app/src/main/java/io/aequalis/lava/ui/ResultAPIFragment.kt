package io.aequalis.lava.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import io.aequalis.lava.R
import org.json.JSONObject


class ResultAPIFragment : Fragment() {
    private var api_keys: TextView? = null
    var resObj: JSONObject? = null

    companion object {
        fun newInstance(resObj: JSONObject): ResultAPIFragment {
            val fragment = ResultAPIFragment()
            fragment.resObj = resObj
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.result_api, container, false)
        api_keys = view.findViewById(R.id.api_keys)
        try {
            if (resObj!!.getBoolean("status")) {
                api_keys?.text = formatString(resObj!!.getJSONObject("data").getJSONArray("content").toString())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return view
    }

    fun formatString(text: String): String {

        val json = StringBuilder()
        var indentString = ""

        for (i in 0 until text.length) {
            val letter = text[i]
            when (letter) {
                '{', '[' -> {
                    json.append("\n" + indentString + letter + "\n")
                    indentString = indentString + "\t"
                    json.append(indentString)
                }
                '}', ']' -> {
                    indentString = indentString.replaceFirst("\t".toRegex(), "")
                    json.append("\n" + indentString + letter)
                }
                ',' -> json.append(letter + "\n" + indentString)

                else -> json.append(letter)
            }
        }

        return json.toString()
    }

}