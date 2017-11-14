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
    lateinit var mDatabase: NodeDao
    lateinit var mHelper: NodeDatabaseHelper

    override fun onItemClicked(node: Node) {
        startActivity(Intent(this, NodeRelationsActivity::class.java).apply {
            putExtra(NodeRelationsActivity.NODE_KEY, node.id)
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mHelper = Injection.provideUserDataSource(this)

//        mDatabase = Injection.provideUserDataSource(this)

//        Completable.fromAction { mDatabase.insertRelations(NodeChildren(1, 2)) }
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe()

//        mDatabase.getNode()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe {
//                    NodeRecyclerViewAdapter(if (it.isEmpty()) ArrayList() else (it as MutableList),
//                            this).let {
//                        mAdapter = it
//                        recyclerView.adapter = it
//                    }
//                }


        recyclerView.layoutManager = LinearLayoutManager(this)
        mAdapter = NodeRecyclerViewAdapter(mHelper.getNode(), this)
        recyclerView.adapter = mAdapter

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
        val nodeId = mHelper.insertNode(value)
        mAdapter.apply {
            mValues.add(mHelper.getNode(nodeId)[0])
            notifyDataSetChanged()
        }
//        mAdapter.apply {
//            val element = Node(value, emptyList())
//            Completable.fromAction { mDatabase.insertNode(element) }
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe {
//                        mValues.add(element)
//                        notifyItemInserted(mValues.indexOf(element))
//                        notifyDataSetChanged()
//                    }
//        }
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

        val isHaveChild = (item.children != null && item.children.isNotEmpty())
        val isHaveParent = mValues.any { if (it.children == null) false else it.children.contains(item) }

        holder.mView.setBackgroundColor(when (isHaveChild) {
            true -> if (isHaveParent) R.color.red else R.color.yellow
            false -> if (isHaveParent) R.color.blue else Color.TRANSPARENT
        })
        holder.mView.setOnClickListener { mListener.onItemClicked(item) }
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        lateinit var mItem: Node
        val mNodeValue: TextView = mView.nodeValue
    }
}
