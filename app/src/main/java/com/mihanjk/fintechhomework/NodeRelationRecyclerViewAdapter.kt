package com.mihanjk.fintechhomework

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.mihanjk.fintechhomework.NodeRelationFragment.OnListFragmentInteractionListener
import com.mihanjk.fintechhomework.dummy.DummyContent.DummyItem
import kotlinx.android.synthetic.main.fragment_item.view.*

class NodeRelationRecyclerViewAdapter(private val mValues: List<DummyItem>,
                                      private val mListener: OnListFragmentInteractionListener?) :
        RecyclerView.Adapter<NodeRelationRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mItem = mValues[position]
        holder.mNodeId.text = mValues[position].id
        holder.mNodeValue.text = mValues[position].content

        holder.mView.setOnClickListener {
            mListener?.onListFragmentInteraction(holder.mItem)
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        lateinit var mItem: DummyItem
        val mNodeId: TextView = mView.nodeId
        val mNodeValue: TextView = mView.nodeValue
    }
}
