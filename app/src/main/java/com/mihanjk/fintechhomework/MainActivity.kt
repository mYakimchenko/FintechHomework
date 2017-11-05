package com.mihanjk.fintechhomework

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.gson.*
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val gson = GsonBuilder()
                .registerTypeAdapter(DateExample::class.java, DateSerializer())
                .create()
        val third = gson.toJson(DateExample(Calendar.getInstance().time))
        textView.text = third.toString()
        Log.d(javaClass.name, gson.toJson(third))
    }
}

class DateSerializer(val dateFormat: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US))
    : JsonSerializer<DateExample> {
    override fun serialize(src: DateExample?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement =
            JsonObject().apply {
                add("date", JsonPrimitive(
                        dateFormat.format(src?.date)))
            }
}
