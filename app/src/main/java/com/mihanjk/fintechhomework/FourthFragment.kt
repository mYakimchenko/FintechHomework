package com.mihanjk.fintechhomework

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_fourth.*

class FourthFragment : Fragment() {

    private var mFirstNumber: Double? = null
    private var mSecondNumber: Double? = null
    private var mResult: Double? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        if (arguments != null) {
            mFirstNumber = arguments.getDouble(FIRST)
            mSecondNumber = arguments.getDouble(SECOND)
            mResult = arguments.getDouble(RESULT)
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        menu?.findItem(R.id.fourthFragment)?.isVisible = false
        super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_fourth, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        firstNumberTextView.text = getString(R.string.first_number, mFirstNumber)
        secondNumberTextView.text = getString(R.string.second_number, mSecondNumber)
        resultTextView.text = getString(R.string.result, mResult)

        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        private val FIRST = "first"
        private val SECOND = "second"
        private val RESULT = "result"

        fun newInstance(first: Double = 0.0, second: Double = 0.0, result: Double = 0.0): FourthFragment {
            val fragment = FourthFragment()
            val args = Bundle()
            args.putDouble(FIRST, first)
            args.putDouble(SECOND, second)
            args.putDouble(RESULT, result)
            fragment.arguments = args
            return fragment
        }
    }
}
