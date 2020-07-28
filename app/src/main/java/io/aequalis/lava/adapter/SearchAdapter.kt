package io.aequalis.lava.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.aequalis.lava.R
import io.aequalis.lava.utils.SearchDatas
import io.aequalis.lava.utils.UserInterface

class SearchAdapter(
    private val context: Context,
    private var searchDataVal: ArrayList<SearchDatas>
) :
    RecyclerView.Adapter<SearchAdapter.MViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.search_row, parent, false)

        return MViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: MViewHolder, position: Int) {
        try {
            if (searchDataVal[position].titleId.isNullOrEmpty())
                holder.title_id.text = ""
            else
                holder.title_id.text = searchDataVal[position].titleId

            if (searchDataVal[position].parcelId.isNullOrEmpty())
                holder.parcel_id.text = ""
            else
                holder.parcel_id.text = searchDataVal[position].parcelId

            if (searchDataVal[position].planId.isNullOrEmpty())
                holder.plan_id.text = ""
            else
                holder.plan_id.text = searchDataVal[position].planId

            if (searchDataVal[position].addressText.isNullOrEmpty())
                holder.address.text = ""
            else
                holder.address.text = searchDataVal[position].addressText

            if (searchDataVal[position].owner.isNullOrEmpty())
                holder.owner.text = ""
            else
                holder.owner.text = searchDataVal[position].owner

            if (searchDataVal[position].rightholder.isNullOrEmpty())
                holder.right_holder.text = ""
            else
                holder.right_holder.text = searchDataVal[position].rightholder

            if (searchDataVal[position].lastModifiedDateFrom.isNullOrEmpty())
                holder.date.text = ""
            else
                holder.date.text =
                    "Last Modified:   " + UserInterface.parseDateWithTimeYear(searchDataVal[position].lastModifiedDateFrom)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun addAllDatas(searchDatas: ArrayList<SearchDatas>) {
        this.searchDataVal = searchDatas
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int {
        return searchDataVal!!.size
    }


    class MViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val date: TextView = view.findViewById(R.id.date)
        val title_id: TextView = view.findViewById(R.id.title_id)
        val parcel_id: TextView = view.findViewById(R.id.parcel_id)
        val plan_id: TextView = view.findViewById(R.id.plan_id)
        val address: TextView = view.findViewById(R.id.address)
        val owner: TextView = view.findViewById(R.id.owner)
        val right_holder: TextView = view.findViewById(R.id.right_holder)
    }
}