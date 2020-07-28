package io.aequalis.lava.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import io.aequalis.lava.R
import io.aequalis.lava.adapter.SearchAdapter
import io.aequalis.lava.api.TenantApiClient
import io.aequalis.lava.utils.Agents
import io.aequalis.lava.utils.Constants
import io.aequalis.lava.utils.SearchVal
import io.aequalis.lava.utils.UserInterface
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResultFragment : Fragment() {
    private var recyclerView:RecyclerView?=null

    private var searchAdapter: SearchAdapter?=null
    var resObj: JSONObject? = null

    companion object {
        fun newInstance(resObj: JSONObject): ResultFragment {
            val fragment= ResultFragment()
            fragment.resObj = resObj
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.result, container, false)
        recyclerView=view.findViewById(R.id.recyclerView)
        val gson = Gson()
        val data: SearchVal = gson.fromJson(resObj.toString(), SearchVal::class.java)
        if (data.status) {
            searchAdapter = SearchAdapter(context!!,data.data.content)
            recyclerView!!.layoutManager = LinearLayoutManager(context)
            recyclerView!!.setHasFixedSize(false)
            recyclerView?.adapter=searchAdapter
        }else{
            activity!!.onBackPressed()
        }
        return view
    }

    private fun GetResultDetails(retryCount: Int) {
        if (activity == null)
            return
        UserInterface.showProgress("Loading...",context)
        var call: Call<Agents>? =
            TenantApiClient.build()?.getTenantDetails(Constants.getHeader(context!!)!!)
        call?.enqueue(object : Callback<Agents> {
            override fun onFailure(call: Call<Agents>, t: Throwable) {
                if (context == null)
                    return
                try {
                    if (retryCount <= 2) {
                        val newRetry = retryCount + 1
                        GetResultDetails(newRetry)
                    } else {
                        UserInterface.hideProgress(context!!)
                        UserInterface.showToast(context, t.message!!)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }

            override fun onResponse(
                call: Call<Agents>,
                response: Response<Agents>
            ) {
                response?.body()?.let {
                    if (response.isSuccessful) {
                        UserInterface.hideProgress(context!!)
                        try {
                            if (context == null)
                                return
                            if (it.status) {
                                fragmentManager!!.beginTransaction()
                                    .add(
                                        R.id.lava_main_container,
                                        RegisterTabFragment()
                                    ).addToBackStack("Tenant").commitAllowingStateLoss()
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                } ?: run {
                    if (response.body() == null) {
                        if (retryCount <= 2) {
                            val newRetry = retryCount + 1
                            GetResultDetails(newRetry)
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