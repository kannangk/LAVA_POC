package io.aequalis.lava.ui

import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import io.aequalis.lava.R
import io.aequalis.lava.api.SearchApiClient
import io.aequalis.lava.api.TenantApiClient
import io.aequalis.lava.utils.Agents
import io.aequalis.lava.utils.Constants
import io.aequalis.lava.utils.LavaApplication
import io.aequalis.lava.utils.UserInterface
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterFragment : Fragment() {
    private var searchTV: LinearLayout? = null
    private var title_id: EditText? = null
    private var parcel_id: EditText? = null
    private var plan_id: EditText? = null
    private var owner: EditText? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.register, container, false)
        searchTV = view.findViewById(R.id.iv_search)
        title_id = view.findViewById(R.id.title_id)
        parcel_id = view.findViewById(R.id.parcel_id)
        plan_id = view.findViewById(R.id.plan_id)
        owner = view.findViewById(R.id.owner)
        searchTV!!.setOnClickListener {
            try {
                GetSearchDetails(1)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        title_id?.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                val params = JSONObject()
                try {
                    if (!title_id!!.text.isNullOrEmpty()) {
                        params.put("titleId", getObject(title_id!!.text.toString()))
                    }
                    if (!owner!!.text.isNullOrEmpty()) {
                        params.put("owner", getObject(owner!!.text.toString()))
                    }
                    if (!parcel_id!!.text.isNullOrEmpty()) {
                        params.put("parcelId", getObject(parcel_id!!.text.toString()))
                    }
                    if (!plan_id!!.text.isNullOrEmpty()) {
                        params.put("planId", getObject(plan_id!!.text.toString()))
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
                if(Constants.getAPIKeyParamUpdateListener()!=null)
                    Constants.getAPIKeyParamUpdateListener()!!.onChanged(params.toString()!!)
            }
        })
        parcel_id?.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                val params = JSONObject()
                try {
                    if (!title_id!!.text.isNullOrEmpty()) {
                        params.put("titleId", getObject(title_id!!.text.toString()))
                    }
                    if (!owner!!.text.isNullOrEmpty()) {
                        params.put("owner", getObject(owner!!.text.toString()))
                    }
                    if (!parcel_id!!.text.isNullOrEmpty()) {
                        params.put("parcelId", getObject(parcel_id!!.text.toString()))
                    }
                    if (!plan_id!!.text.isNullOrEmpty()) {
                        params.put("planId", getObject(plan_id!!.text.toString()))
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
                if(Constants.getAPIKeyParamUpdateListener()!=null)
                    Constants.getAPIKeyParamUpdateListener()!!.onChanged(params.toString()!!)
            }
        })
        plan_id?.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                val params = JSONObject()
                try {
                    if (!title_id!!.text.isNullOrEmpty()) {
                        params.put("titleId", getObject(title_id!!.text.toString()))
                    }
                    if (!owner!!.text.isNullOrEmpty()) {
                        params.put("owner", getObject(owner!!.text.toString()))
                    }
                    if (!parcel_id!!.text.isNullOrEmpty()) {
                        params.put("parcelId", getObject(parcel_id!!.text.toString()))
                    }
                    if (!plan_id!!.text.isNullOrEmpty()) {
                        params.put("planId", getObject(plan_id!!.text.toString()))
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
                if(Constants.getAPIKeyParamUpdateListener()!=null)
                    Constants.getAPIKeyParamUpdateListener()!!.onChanged(params.toString()!!)
            }
        })
        owner?.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                val params = JSONObject()
                try {
                    if (!title_id!!.text.isNullOrEmpty()) {
                        params.put("titleId", getObject(title_id!!.text.toString()))
                    }
                    if (!owner!!.text.isNullOrEmpty()) {
                        params.put("owner", getObject(owner!!.text.toString()))
                    }
                    if (!parcel_id!!.text.isNullOrEmpty()) {
                        params.put("parcelId", getObject(parcel_id!!.text.toString()))
                    }
                    if (!plan_id!!.text.isNullOrEmpty()) {
                        params.put("planId", getObject(plan_id!!.text.toString()))
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
                if(Constants.getAPIKeyParamUpdateListener()!=null)
                    Constants.getAPIKeyParamUpdateListener()!!.onChanged(params.toString()!!)
            }
        })
        return view
    }

    private fun getObject(value: String): JSONObject {
        var obj: JSONObject? = JSONObject()
        try {
            obj!!.put("contains", value)
            obj!!.put("specified", true)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return obj!!
    }

    private fun GetSearchDetails(retryCount: Int) {
        if (activity == null)
            return
        UserInterface.showProgress("Loading...", context)
        val params = JSONObject()
        try {
            if (!title_id!!.text.isNullOrEmpty()) {
                params.put("titleId", getObject(title_id!!.text.toString()))
            }
            if (!owner!!.text.isNullOrEmpty()) {
                params.put("owner", getObject(owner!!.text.toString()))
            }
            if (!parcel_id!!.text.isNullOrEmpty()) {
                params.put("parcelId", getObject(parcel_id!!.text.toString()))
            }
            if (!plan_id!!.text.isNullOrEmpty()) {
                params.put("planId", getObject(plan_id!!.text.toString()))
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        val JSON = "application/json; charset=utf-8".toMediaTypeOrNull()
        var body = params.toString().toRequestBody(JSON)
        var call: Call<ResponseBody>? =
            SearchApiClient.build()?.getSearchData(body, Constants.getHeader(context!!)!!)
        call?.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                if (context == null)
                    return
                try {
                    if (retryCount <= 2) {
                        val newRetry = retryCount + 1
                        GetSearchDetails(newRetry)
                    } else {
                        UserInterface.hideProgress(context!!)
                        UserInterface.showToast(context, t.message!!)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }

            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                response?.body()?.let {
                    if (response.isSuccessful) {
                        UserInterface.hideProgress(context!!)
                        try {
                            if (context == null)
                                return
                            val resObj: JSONObject = JSONObject(it.string())
                            if (resObj.getBoolean("status")) {
                                if(resObj.getJSONObject("data").getJSONArray("content").length()>0) {
                                    LavaApplication.getChildFragmentManager()!!.beginTransaction()
                                        .add(
                                            R.id.lava_main_container,
                                            ResultsTabFragment.newInstance(resObj)
                                        ).addToBackStack("Search").commitAllowingStateLoss()
                                }else{
                                    UserInterface.showOKAlert(
                                        activity,
                                        resources.getString(R.string.no_result),
                                        false,
                                        DialogInterface.OnClickListener { dialog, which ->

                                        }
                                    )
                                }
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                } ?: run {
                    if (response.body() == null) {
                        if (retryCount <= 2) {
                            val newRetry = retryCount + 1
                            GetSearchDetails(newRetry)
                        } else {
                            UserInterface.hideProgress(context!!)
                            UserInterface.showToast(context, response.message())
                        }

                    }
                }
            }

        })

    }
}