package com.mihanjk.fintechhomework

import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.node_list_item.view.*

class MainActivity : NodeRecyclerViewAdapter.OnRecyclerViewAction, AppCompatActivity() {
    lateinit var mAdapter: NodeRecyclerViewAdapter

    override fun onItemClicked(node: Node) {
        startActivity(Intent(this, NodeRelationsActivity::class.java))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        NodeRecyclerViewAdapter(ArrayList(), this).let {
            mAdapter = it
            recyclerView.adapter = it
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0 && floatingActionButton.visibility == View.VISIBLE) {
                    floatingActionButton.hide()
                } else if (dy < 0 && floatingActionButton.visibility != View.VISIBLE) {
                    floatingActionButton.show()
                }
            }
        })

        floatingActionButton.setOnClickListener {
            val editText = EditText(this@MainActivity).apply {
                inputType = InputType.TYPE_CLASS_NUMBER
                setRawInputType(Configuration.KEYBOARD_12KEY)
            }
            AlertDialog.Builder(this).apply {
                setTitle("Add node item")
                setMessage("Input value:")
                setView(editText)
                setPositiveButton("Ok") { _, _ ->
                    addNewNode(
                            if (editText.text.toString().isNotBlank())
                                Integer.parseInt(editText.text.toString())
                            else 0
                    )
                }
                setNegativeButton("Cancel") { _, _ -> }
            }.create().show()
            editText.requestFocus()
        }
    }

    private fun addNewNode(value: Int) {
        mAdapter.apply {
            val element = Node(value, emptyList())
            mValues.add(element)
            notifyItemInserted(mValues.indexOf(element))
            notifyDataSetChanged()
        }
    }
}

class NodeRecyclerViewAdapter(val mValues: MutableList<Node>,
                              val mListener: OnRecyclerViewAction) : RecyclerView.Adapter<NodeRecyclerViewAdapter.ViewHolder>() {

    interface OnRecyclerViewAction {
        fun onItemClicked(node: Node)
    }

    override fun getItemCount(): Int = mValues.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NodeRecyclerViewAdapter.ViewHolder =
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.node_list_item, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mItem = mValues[position]
        val item = holder.mItem
        holder.mNodeValue.text = item.value.toString()

        val isParent = mValues.any { it.children.contains(item) }
        holder.mView.setBackgroundColor(when (item.children.isNotEmpty()) {
            true -> if (isParent) R.color.red else R.color.yellow
            false -> if (isParent) R.color.blue else Color.TRANSPARENT
        })
        holder.mView.setOnClickListener { mListener.onItemClicked(item) }
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        lateinit var mItem: Node
        val mNodeValue: TextView = mView.nodeValue
    }
}
