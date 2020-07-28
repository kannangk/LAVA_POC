package io.aequalis.lava.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import io.aequalis.lava.R
import io.aequalis.lava.utils.APIKeyParamUpdateListener
import io.aequalis.lava.utils.Constants

class APIKeysFragment : Fragment() {
    private var api_keys: TextView? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.api_view, container, false)
        api_keys = view.findViewById(R.id.api_keys)
        api_keys?.text =
            "curl -X POST --header 'Content-Type: application/json' --header 'Accept: application/json' --header 'lava-tenant: "+Constants.getTenantName()+"' -d '{}' '"+Constants.SEARCH_BASE_URL+"/api/v3/title-views/search/0/25/titleId/asc'"
        Constants.setAPIKeyParamUpdateListener(apiKeyParamUpdateListener)
        return view
    }

    private val apiKeyParamUpdateListener: APIKeyParamUpdateListener =
        object : APIKeyParamUpdateListener {
            override fun onChanged(params: String) {
                api_keys?.text =
                    "curl -X POST --header 'Content-Type: application/json' --header 'Accept: application/json' --header 'lava-tenant: "+Constants.getTenantName()+"' -d '" + formatString(params) + "' '"+Constants.SEARCH_BASE_URL+"/api/v3/title-views/search/0/25/titleId/asc'"
            }

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