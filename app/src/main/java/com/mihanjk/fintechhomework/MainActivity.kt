package com.mihanjk.fintechhomework

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.reflect.Type
import java.math.BigDecimal

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val gson = GsonBuilder()
                .registerTypeAdapter(Third::class.java, BigDecimalDeserializer())
                .create()
        val third = gson.fromJson("""{"money_amount":"2444.88"}""",
                Third::class.java)
        textView.text = third.toString()
        Log.d(javaClass.name, gson.toJson(third))
    }
}

class BigDecimalDeserializer : JsonDeserializer<Third> {
    override fun deserialize(json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext?): Third =
            Third(BigDecimal(json.asJsonObject.get("money_amount").asString.replace(',', '.')))
}
