package io.aequalis.lava.utils

import android.content.Context

object Constants {

    const val BASE_URL: String = "https://master.lava.landadmin.com"
    const val TENANT_BASE_URL: String = "https://configuration.lava.landadmin.com"
    const val SEARCH_BASE_URL: String = "https://search.lava.landadmin.com"


    private var tenantName: String = ""
    private var apiKeyParamUpdateListener: APIKeyParamUpdateListener? = null


    fun setTenantName(tenantName: String) {
        this.tenantName = tenantName
    }

    fun getTenantName(): String? {
        return tenantName
    }

    fun setAPIKeyParamUpdateListener(listener: APIKeyParamUpdateListener) {
        this.apiKeyParamUpdateListener = listener
    }

    fun getAPIKeyParamUpdateListener(): APIKeyParamUpdateListener? {
        return apiKeyParamUpdateListener!!
    }


    fun getHeader(context: Context): HashMap<String, String>? {
        var headersMap: HashMap<String, String>? = HashMap()
        try {
            headersMap?.put("Accept", "*/*")
            headersMap?.put("lava-tenant", getTenantName()!!)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return headersMap!!
    }


}