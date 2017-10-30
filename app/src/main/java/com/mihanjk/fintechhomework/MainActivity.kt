package com.mihanjk.fintechhomework

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        graph.mValues = generateSequence(Pair(0f, 0f),
                { pair -> Pair(pair.first * 2 + 5, pair.second + 10) })
                .take(10).toList()
//        graph.mValues = listOf(Pair(20f, 50f), Pair(100f, 500f))
    }
}
