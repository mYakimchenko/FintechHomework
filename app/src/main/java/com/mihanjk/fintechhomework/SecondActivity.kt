package com.mihanjk.fintechhomework

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : SecondTaskFragment.HandlerCallback, AppCompatActivity() {
    private var mFragment: SecondTaskFragment? = null
    private var mFirstNumber: String = "First"
    private var mSecondNumber: String = "Second"

    companion object {
        const val TASK_FRAGMENT = "Second task"
        const val FIRST = "First"
        const val SECOND = "Second"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        if (savedInstanceState != null) {
            mFirstNumber = savedInstanceState.getString(FIRST)
            mSecondNumber = savedInstanceState.getString(SECOND)
            firstNumber.text = mFirstNumber
            secondNumber.text = mSecondNumber
        }

        mFragment = supportFragmentManager.findFragmentByTag(TASK_FRAGMENT) as SecondTaskFragment?
        if (mFragment == null) {
            mFragment = SecondTaskFragment()
            supportFragmentManager.beginTransaction()
                    .add(mFragment, TASK_FRAGMENT)
                    .commit()
        }
    }

    override fun updateFirstNumber(number: Int) {
        mFirstNumber = number.toString()
        firstNumber.text = mFirstNumber
    }

    override fun updateSecondNumber(number: Int) {
        mSecondNumber = number.toString()
        secondNumber.text = mSecondNumber
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putString(FIRST, mFirstNumber)
        outState?.putString(SECOND, mSecondNumber)
        super.onSaveInstanceState(outState)
    }

    override fun onUserLeaveHint() {
        mFragment?.cancelTask()
        super.onUserLeaveHint()
    }
}
