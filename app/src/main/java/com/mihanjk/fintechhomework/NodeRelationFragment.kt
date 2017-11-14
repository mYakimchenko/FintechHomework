package com.mihanjk.fintechhomework

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.view.*

/**
 * A fragment representing a list of Items.
 *
 *
 * Activities containing this fragment MUST implement the [OnListFragmentInteractionListener]
 * interface.
 */
/**
 * Mandatory empty constructor for the fragment manager to instantiate the
 * fragment (e.g. upon screen orientation changes).
 */
class NodeRelationFragment : Fragment() {
    // TODO: Customize parameters
    lateinit var mFragmentType: String
    private var mListener: OnListFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null) {
            mFragmentType = arguments.getString(TYPE)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_item_list, container, false)
        when (mFragmentType) {
            NodeRelationsActivity.PARENT_FRAGMENT -> mListener?.getParents()
            NodeRelationsActivity.CHILD_FRAGMENT -> mListener?.getChildren()
            else -> throw Exception("Unknown fragment type")
        }?.apply {
            subscribeOn(Schedulers.io())
            observeOn(AndroidSchedulers.mainThread())
            subscribe {
                view.recyclerView.adapter = NodeRelationRecyclerViewAdapter(it, mListener)
            }
        }
        return view
    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException((context!!.toString() + " must implement OnListFragmentInteractionListener"))
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    interface OnListFragmentInteractionListener {
        fun onListFragmentInteraction(item: Node)
        fun getParents(): Flowable<List<Node>>
        fun getChildren(): Flowable<List<Node>>
    }

    companion object {
        private val TYPE = "type"

        fun newInstance(type: String): NodeRelationFragment {
            val fragment = NodeRelationFragment()
            val args = Bundle()
            args.putString(TYPE, type)
            fragment.arguments = args
            return fragment
        }
    }
}
