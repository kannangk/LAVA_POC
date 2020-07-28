package io.aequalis.lava.ui

import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
import androidx.fragment.app.Fragment
import fr.ganfra.materialspinner.MaterialSpinner
import io.aequalis.lava.R
import io.aequalis.lava.api.ApiClient
import io.aequalis.lava.api.TenantApiClient
import io.aequalis.lava.utils.Agents
import io.aequalis.lava.utils.Constants
import io.aequalis.lava.utils.TenantData
import io.aequalis.lava.utils.UserInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class TenantChooseFragment : Fragment() {
    private var adapter: ArrayAdapter<String>? = null
    var tenantSpinner: MaterialSpinner? = null
    var tenant_data: LinearLayout? = null
    private var process: TextView? = null
    private var pb: ProgressBar? = null
    private var tenantList: ArrayList<String>? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.tenant_choose, container, false)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = activity!!.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resources.getColor(R.color.app_blue, null)
        }
        process = view.findViewById(R.id.process)
        tenant_data = view.findViewById(R.id.tenant_data)
        pb = view.findViewById(R.id.pb)
        tenantSpinner = view.findViewById(R.id.spinner1)
        tenant_data!!.visibility = View.INVISIBLE
        process!!.setOnClickListener {
            try {
                if (tenantSpinner?.selectedItemPosition!! > 0) {
                    GetAgentDetails(1)
                }else{
                    tenantSpinner?.error = "Please select Tenant"
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        tenantSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position >= 0) {
                    tenantSpinner?.error = null
                    Constants.setTenantName(tenantList!![position])
                }
            }

        }
        GetTenantList(1)
        return view
    }

    private fun GetTenantList(retryCount: Int) {
        if (activity == null)
            return
        var call: Call<TenantData>? =
            ApiClient.build()?.getTenantDatas()
        call?.enqueue(object : Callback<TenantData> {
            override fun onFailure(call: Call<TenantData>, t: Throwable) {
                if (context == null)
                    return
                try {
                    if (retryCount <= 2) {
                        val newRetry = retryCount + 1
                        GetTenantList(newRetry)
                    } else {
                        UserInterface.showToast(context, t.message!!)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }

            override fun onResponse(
                call: Call<TenantData>,
                response: Response<TenantData>
            ) {
                response?.body()?.let {
                    if (response.isSuccessful) {
                        try {
                            if (context == null)
                                return
                            if (it.status) {
                                if (it.data.isNotEmpty()) {
                                    adapter = ArrayAdapter(
                                        context!!,
                                        android.R.layout.simple_spinner_item,
                                        it.data
                                    )
                                    tenantList = it.data
                                    adapter!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                                    tenantSpinner!!.adapter = adapter
                                    tenantSpinner!!.setPaddingSafe(0, 0, 0, 0)
                                    tenant_data!!.visibility = View.VISIBLE
                                    pb!!.visibility = View.GONE
                                } else {
                                    UserInterface.showOKAlert(
                                        activity,
                                        resources.getString(R.string.no_tenant),
                                        false,
                                        DialogInterface.OnClickListener { dialog, which ->

                                        }
                                    )
                                }
                            } else {
                                UserInterface.showOKAlert(
                                    activity,
                                    resources.getString(R.string.no_tenant),
                                    false,
                                    DialogInterface.OnClickListener { dialog, which ->

                                    }
                                )
                            }

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                } ?: run {
                    if (response.body() == null) {
                        if (retryCount <= 2) {
                            val newRetry = retryCount + 1
                            GetTenantList(newRetry)
                        } else {
                            UserInterface.showToast(context, response.message())
                        }

                    }
                }
            }

        })

    }


    private fun GetAgentDetails(retryCount: Int) {
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
                        GetAgentDetails(newRetry)
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
                            GetAgentDetails(newRetry)
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