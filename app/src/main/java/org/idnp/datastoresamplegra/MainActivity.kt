package org.idnp.datastoresamplegra

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var textInput: AppCompatEditText
    lateinit var increaseBtn: Button
    lateinit var decreaseBtn: Button
    lateinit var setValueBtn: Button
    lateinit var counterManager: CounterDataStoreManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textInput = findViewById(R.id.textInput)
        increaseBtn = findViewById(R.id.increaseBtn)
        decreaseBtn = findViewById(R.id.decreaseBtn)
        setValueBtn = findViewById(R.id.setBtn)

        counterManager = CounterDataStoreManager(this)

        // Collect the counter value and set the text everytime the value changes
        lifecycleScope.launch {
            counterManager.counter.collect { counter ->
                textInput.setText(counter.toString())
            }
        }


        // Increment the counter
        increaseBtn.setOnClickListener {
            lifecycleScope.launch {
                counterManager.incrementCounter()
            }
        }

        // Decrement the counter
        decreaseBtn.setOnClickListener {
            lifecycleScope.launch {
                counterManager.decrementCounter()
            }
        }

        // Set the current value of the counter
        setValueBtn.setOnClickListener {
            lifecycleScope.launch {
                counterManager.setCounter(textInput.text.toString().toInt())
            }
        }


        val btnSecondActivity = findViewById(R.id.btnSecondActivity) as Button

        btnSecondActivity.setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)

        }


    }


}