package com.mihanjk.fintechhomework

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val SHOW_MESSAGE_REQUEST = 1
        const val MESSAGE_KEY = "Message"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        implicitIntentButton.setOnClickListener {
            sendImplicitIntentWithText(editText.text.toString())
        }
        explicitIntentButton.setOnClickListener {
            sendExplicitIntentForResultWithText(editText.text.toString())
        }
    }

    private fun sendExplicitIntentForResultWithText(message: String) {
        val intent = Intent(this, SecondActivity::class.java)
        intent.putExtra(MESSAGE_KEY, message)
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        startActivityForResult(intent, SHOW_MESSAGE_REQUEST)
    }

    private fun sendImplicitIntentWithText(message: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.putExtra(Intent.EXTRA_TEXT, message)
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            showToast(R.string.no_text_application_install)
        }
    }

    private fun showToast(messageResourceId: Int) {
        Toast.makeText(this, getString(messageResourceId), Toast.LENGTH_LONG).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            SHOW_MESSAGE_REQUEST -> when (resultCode) {
                Activity.RESULT_OK -> showToast(R.string.activity_result_ok)
                Activity.RESULT_CANCELED -> showToast(R.string.activity_result_cancel)
            }
        }
    }
}
