package io.aequalis.lava.utils

import java.io.Serializable

data class TenantData(val status:Boolean,val msg:String, val data:ArrayList<String>):Serializable
data class Agents(val status:Boolean,val msg:String, val data:ArrayList<AgentData>):Serializable
data class AgentData(val id:String,val access:String,val status: Boolean,val name:String,val order:Int,val header:String)
data class SearchVal(val status:Boolean,val msg:String, val data:SearchData):Serializable
data class SearchData(val content:ArrayList<SearchDatas>)
data class SearchDatas(val id:String,val titleId:String,val planId:String,val owner:String,val parcelId:String,val customMetadata:String,val addressText:String,val rightholder:String,val lastModifiedDateFrom:String)