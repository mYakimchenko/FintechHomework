package com.mihanjk.fintechhomework

import android.content.Context
import android.os.*
import android.support.v4.app.Fragment
import android.util.Log


class SecondTaskFragment : Fragment() {
    private var mListener: HandlerCallback? = null
    private lateinit var mHtHandler: Handler
    private lateinit var mUiHandler: Handler
    private lateinit var mHandlerThread: HandlerThread

    companion object {
        const val TAG = "TEST"
    }

    fun cancelTask() {
        mHtHandler.removeCallbacksAndMessages(null)
        mUiHandler.removeCallbacksAndMessages(null)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true

        mHandlerThread = HandlerThread("Ht")
        mHandlerThread.start()

        mHtHandler = Handler(mHandlerThread.looper, {
            Thread.sleep(2000)
            Log.d(TAG, "got a message in " + (Looper.myLooper() == Looper.getMainLooper()).toString() + ", now sleeping... ${it.arg1} ")
            when (it.what) {
                1 -> {
                    val firstMessage = Message.obtain()
                    firstMessage.what = it.what
                    firstMessage.arg1 = it.arg1
                    mUiHandler.sendMessage(firstMessage)
                }
                2 -> {
                    val secondMessage = Message.obtain()
                    secondMessage.what = it.what
                    secondMessage.arg1 = it.arg1
                    mUiHandler.sendMessageDelayed(secondMessage, 5000)
                }
            }
            false
        })

        mUiHandler = Handler({
            Log.d(TAG, "got a message in " + (Looper.myLooper() == Looper.getMainLooper()).toString() + ", now listener... ${it.arg1} ")
            when (it.what) {
                1 -> mListener?.updateFirstNumber(it.arg1)
                2 -> mListener?.updateSecondNumber(it.arg1)
            }
            false
        })

        val firstMessage = Message.obtain()
        firstMessage.what = 1
        firstMessage.arg1 = 1
        mHtHandler.sendMessage(firstMessage)

        val second = Message.obtain()
        second.what = 2
        second.arg1 = 2
        mHtHandler.sendMessage(second)
    }

    interface HandlerCallback {
        fun updateFirstNumber(number: Int)
        fun updateSecondNumber(number: Int)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is HandlerCallback) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement Handler")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }
}

