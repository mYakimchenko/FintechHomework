package com.mihanjk.fintechhomework

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.mihanjk.fintechhomework.Operation.*


class MainActivity : FirstFragment.OnFragmentInteractionListener,
        SecondFragment.OnFragmentInteractionListener,
        ThirdFragment.OnFragmentInteractionListener,
        AppCompatActivity() {

    companion object {
        const val FIRST_FRAGMENT = "first"
        const val SECOND_FRAGMENT = "second"
        const val THIRD_FRAGMENT = "third"
        const val FOURTH_FRAGMENT = "fourth"
    }

    private val mFragmentManager = supportFragmentManager
    private var mFirstNumber = 0.0
    private var mSecondNumber = 0.0
    private var mResult = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            mFragmentManager.beginTransaction()
                    .add(R.id.fragmentContainer, FirstFragment.newInstance(), FIRST_FRAGMENT)
                    .commit()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.firstFragment -> {
                replaceFragment(FIRST_FRAGMENT)
                true
            }
            R.id.secondFragment -> {
                replaceFragment(SECOND_FRAGMENT)
                true
            }
            R.id.thirdFragment -> {
                replaceFragment(THIRD_FRAGMENT)
                true
            }
            R.id.fourthFragment -> {
                replaceFragment(FOURTH_FRAGMENT)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onFirstFragmentConfirmButtonClicked(value: Double) {
        mFirstNumber = value
        replaceFragment(SECOND_FRAGMENT)
    }

    override fun onSecondFragmentConfirmButtonClicked(value: Double) {
        mSecondNumber = value
        replaceFragment(THIRD_FRAGMENT)
    }

    override fun onThirdFragmentButtonClicked(operation: Operation) {
        mResult = when (operation) {
            PLUS -> mFirstNumber + mSecondNumber
            MINUS -> mFirstNumber - mSecondNumber
            MULTIPLICATION -> mFirstNumber * mSecondNumber
            DIVISION -> mFirstNumber / mSecondNumber
        }
        replaceFragment(FOURTH_FRAGMENT)
    }


    private fun replaceFragment(tag: String) {
        val fragment = if (tag == FOURTH_FRAGMENT) {
            FourthFragment.newInstance(mFirstNumber, mSecondNumber, mResult)
        } else mFragmentManager.findFragmentByTag(tag) ?:
                when (tag) {
                    FIRST_FRAGMENT -> FirstFragment.newInstance()
                    SECOND_FRAGMENT -> SecondFragment.newInstance()
                    THIRD_FRAGMENT -> ThirdFragment.newInstance()
                    else -> throw IllegalArgumentException("Unknown tag detected")
                }

        mFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, fragment, tag)
                .addToBackStack(null)
                .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
}
