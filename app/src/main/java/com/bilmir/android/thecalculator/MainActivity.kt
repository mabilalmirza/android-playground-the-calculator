package com.bilmir.android.thecalculator

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.math.BigDecimal

class MainActivity : AppCompatActivity() {

    lateinit var tvScreenCurrent: TextView
    lateinit var tvScreenPreviuos: TextView
    lateinit var tvScreenOp: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvScreenCurrent = findViewById(R.id.tvScreenCurrent)
        tvScreenPreviuos = findViewById(R.id.tvScreenPrevious)
        tvScreenOp = findViewById(R.id.tvScreenOp)
    }

    fun onDigit(view: View) {
        clearError()
        val digit = (view as Button).text
        if (tvScreenCurrent.text.toString() == "0") {
            tvScreenCurrent.text = digit
        } else if (tvScreenCurrent.text.toString() == "-0") {
            tvScreenCurrent.text = ("-$digit")
        } else {
            tvScreenCurrent.append(digit)
        }
    }

    fun onPositiveNegative(view: View) {
        clearError()
        if (tvScreenCurrent.text.startsWith("-")) {
            tvScreenCurrent.text = tvScreenCurrent.text.substring(1)
        } else {
            tvScreenCurrent.text = "-${tvScreenCurrent.text}"
        }
    }

    fun onDecimal(view: View) {
        clearError()
        if (!tvScreenCurrent.text.contains(".")) {
            tvScreenCurrent.append(".")
        }
    }

    fun onOperation(view: View) {
        clearError()
        val op = (view as Button).text
        if (tvScreenPreviuos.text.isEmpty()) {
            tvScreenPreviuos.text = tvScreenCurrent.text
            tvScreenCurrent.text = "0"
        } else if (tvScreenCurrent.text.toString() != "0") {
            calculate()
            tvScreenCurrent.text = "0"
        }
        tvScreenOp.text = op
    }

    fun onEqual(view: View) {
        clearError()
        if (tvScreenPreviuos.text.isEmpty()) {
            tvScreenPreviuos.text = tvScreenCurrent.text
        } else {
            calculate()
        }
        tvScreenOp.text = ""
        tvScreenCurrent.text = "0"
    }


    fun onClearEntry(view: View) {
        tvScreenCurrent.text = "0"
    }

    fun onClearAll(view: View) {
        tvScreenCurrent.text = "0"
        tvScreenPreviuos.text = ""
        tvScreenOp.text = ""
    }

    private fun clearError() {
        if (tvScreenPreviuos.text == "ERROR") {
            tvScreenPreviuos.text = ""
        }
    }

    private fun calculate() {
        try {
            val current = tvScreenCurrent.text.toString().toBigDecimal()
            val previous = tvScreenPreviuos.text.toString().toBigDecimal()
            val result = when (tvScreenOp.text) {
                "/" -> previous / current
                "*" -> previous * current
                "-" -> previous - current
                "+" -> previous + current
                else -> current
            }

            if (result.rem(BigDecimal.ONE) == BigDecimal.ZERO) {
                tvScreenPreviuos.text = result.toLong().toString()
            } else {
                tvScreenPreviuos.text = result.toString()
            }
        } catch (e: Exception) {
            Log.i("Calculator","Error : ${tvScreenCurrent.text} ${tvScreenOp.text} ${tvScreenCurrent.text}", e)
            tvScreenPreviuos.text = "ERROR"
        }

    }
}