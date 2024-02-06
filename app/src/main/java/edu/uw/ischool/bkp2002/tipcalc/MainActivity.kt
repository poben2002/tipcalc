package edu.uw.ischool.bkp2002.tipcalc

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private val TAG: String = "MainActivity"
    private lateinit var tipButton : Button
    private lateinit var billText : EditText
    private lateinit var tipSpinner: Spinner
    private lateinit var tipTextView : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tipButton = findViewById(R.id.tipButton)
        tipButton.isEnabled = false
        billText = findViewById(R.id.editTextNumber)
        tipSpinner = findViewById(R.id.tipPercentageSpinner)
        tipSpinner.visibility = View.GONE
        tipTextView = findViewById(R.id.tipPercentageLabel)
        tipTextView.visibility = View.GONE

        billText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val input = s.toString().replace("$", "").toDoubleOrNull() ?: 0.0
                tipButton.isEnabled = input > 0.0
            }

            override fun afterTextChanged(s: Editable?) {
                val input = s.toString()
                if (input.isNotEmpty()) {
                    // Show the spinner when the user enters text.
                    tipSpinner.visibility = View.VISIBLE
                    tipTextView.visibility = View.VISIBLE
                    tipButton.isEnabled = true
                } else {
                    // Hide the spinner when the input is empty.
                    tipSpinner.visibility = View.GONE
                    tipTextView.visibility = View.GONE
                    tipButton.isEnabled = false
                }

                // Add "$" prefix to the input if it's not already there.
                if (!input.startsWith("$")) {
                    billText.setText("$" + input)
                    billText.setSelection(billText.text.length)
                }
            }
        })


        tipButton.setOnClickListener {
            val input = billText.text.toString().replace("$", "").trim()
            val tipPercentage = tipSpinner.selectedItem.toString().trim('%').toDoubleOrNull() ?: 0.0
            if (input.isNotBlank()) {
                val serviceCharge = input.toDouble()
                val tipAmount = (serviceCharge * tipPercentage) / 100
                val formattedTip = String.format("$%.2f", tipAmount)
                showTipToast(formattedTip)
            }
        }
    }
    private fun showTipToast(formattedTip: String) {
        Toast.makeText(this, "Tip: $formattedTip", Toast.LENGTH_SHORT).show()
    }
}