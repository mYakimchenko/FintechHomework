package com.mihanjk.fintechhomework

import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View.GONE
import android.view.View.VISIBLE
import kotlinx.android.synthetic.main.activity_first.*

class FirstActivity : FirstTaskFragment.TaskCallback, AppCompatActivity() {
    private lateinit var mAdapter: ItemRecyclerViewAdapter
    private lateinit var mAdapterData: ArrayList<Int>

    private var mFragment: FirstTaskFragment? = null

    companion object {
        const val TASK_FRAGMENT = "Task"
        const val ADAPTER_DATA = "Adapter data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)

        mFragment = supportFragmentManager.findFragmentByTag(TASK_FRAGMENT) as FirstTaskFragment?
        if (mFragment == null) {
            mFragment = FirstTaskFragment()
            supportFragmentManager.beginTransaction()
                    .add(mFragment, TASK_FRAGMENT)
                    .commit()
        }

        mAdapterData = savedInstanceState?.getIntegerArrayList(ADAPTER_DATA) ?: ArrayList()
        mAdapter = ItemRecyclerViewAdapter(mAdapterData)
        recyclerView.adapter = mAdapter
    }

    private fun showProgress() {
        progressBar.visibility = VISIBLE
    }

    private fun hideProgress() {
        progressBar.visibility = GONE
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putIntegerArrayList(ADAPTER_DATA, mAdapterData)
        super.onSaveInstanceState(outState)
    }

    override fun onUserLeaveHint() {
        mFragment?.cancelTask()
        super.onUserLeaveHint()
    }

    override fun onResume() {
        super.onResume()
        when (mFragment?.getTaskStatus()) {
            AsyncTask.Status.FINISHED -> hideProgress()
            AsyncTask.Status.RUNNING -> showProgress()
            AsyncTask.Status.PENDING -> hideProgress()
        }
    }

    override fun onPreExecute() = showProgress()

    override fun onPostExecute(result: ArrayList<Int>) {
        hideProgress()
        mAdapter.updateData(result)
        mAdapterData = result
    }
}
