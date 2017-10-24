package com.mihanjk.fintechhomework

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import java.lang.ref.WeakReference

class FirstTaskFragment : Fragment() {
    private var mListener: TaskCallback? = null
    private val mTask = Task(this)

    fun getTaskStatus(): AsyncTask.Status = mTask.status

    fun cancelTask() = mTask.cancel(true)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true

        mTask.execute()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is TaskCallback) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement TaskCallback")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    interface TaskCallback {
        fun onPostExecute(result: ArrayList<Int>)
        fun onPreExecute()
    }

    fun onPreExecute() {
        mListener?.onPreExecute()
    }

    fun onPostExecute(result: ArrayList<Int>) {
        mListener?.onPostExecute(result)
    }

}

class Task(fragment: FirstTaskFragment) : AsyncTask<Void, Void, ArrayList<Int>>() {
    private val mFragment = WeakReference(fragment)

    override fun onPreExecute() {
        mFragment.get()?.onPreExecute()
    }

    override fun doInBackground(vararg p0: Void?): ArrayList<Int> {
        val array = ArrayList<Int>()
        for (i in 1..20) {
            if (isCancelled) break
            array.add(i)
        }
        return array
    }

    override fun onPostExecute(result: ArrayList<Int>) {
        mFragment.get()?.onPostExecute(result)
    }
}
