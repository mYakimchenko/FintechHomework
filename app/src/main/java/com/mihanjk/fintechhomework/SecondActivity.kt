package com.mihanjk.fintechhomework

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val message = intent.extras.getString(MainActivity.MESSAGE_KEY)
        messageTextView.text = if (message.isEmpty()) getString(R.string.default_message) else message

        okButton.setOnClickListener {
            setResult(Activity.RESULT_OK)
            finish()
        }
        cancelButton.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }
}