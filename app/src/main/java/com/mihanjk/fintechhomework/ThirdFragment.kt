package com.mihanjk.fintechhomework

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_third.*

class ThirdFragment : Fragment() {

    private var mListener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        menu?.findItem(R.id.thirdFragment)?.isVisible = false
        super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_third, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        plusButton.setOnClickListener { mListener?.onThirdFragmentButtonClicked(Operation.PLUS) }
        minusButton.setOnClickListener { mListener?.onThirdFragmentButtonClicked(Operation.MINUS) }
        divisionButton.setOnClickListener { mListener?.onThirdFragmentButtonClicked(Operation.DIVISION) }
        multiplicationButton.setOnClickListener { mListener?.onThirdFragmentButtonClicked(Operation.MULTIPLICATION) }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    interface OnFragmentInteractionListener {
        fun onThirdFragmentButtonClicked(operation: Operation)
    }

    companion object {
        fun newInstance(): ThirdFragment {
            return ThirdFragment()
        }
    }
}
