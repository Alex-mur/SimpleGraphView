package com.example.simplegraphview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val graphView: SimpleGraphView = findViewById(R.id.my_graph)

        val data = mutableMapOf<Float, Float>()
        data.put(1f, 35f)
        data.put(7f, 67f)
        data.put(10f, 40f)
        data.put(11f, 96f)
        data.put(15f, 152f)
        data.put(16f, 143f)
        data.put(19f, 185f)
        data.put(23f, 237f)
        data.put(28f, 194f)

        graphView.showGraph(
            data,
            "Day of month",
            "Cost"
        )
    }
}
