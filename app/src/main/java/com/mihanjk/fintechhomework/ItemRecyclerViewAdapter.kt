package com.mihanjk.fintechhomework

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.list_item.view.*

class ItemRecyclerViewAdapter(private var mValues: List<Int>) :
        RecyclerView.Adapter<ItemRecyclerViewAdapter.ViewHolder>() {
    override fun getItemCount(): Int = mValues.size

    fun updateData(value: List<Int>) {
        mValues = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mValueItem.text = mValues[position].toString()
    }

    inner class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        val mValueItem: TextView = mView.value
    }
}
