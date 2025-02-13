package com.zybooks.pizzaparty

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.ceil

//const val SLICES_PER_PIZZA = 8

class MainActivity : AppCompatActivity() {

    private lateinit var numAttendEditText: EditText
    private lateinit var numPizzasTextView: TextView
    private lateinit var howHungrySpinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        numAttendEditText = findViewById(R.id.num_attend_edit_text)
        numPizzasTextView = findViewById(R.id.num_pizzas_text_view)
        howHungrySpinner = findViewById(R.id.hungry_spinner)

        val hungerLevels = arrayOf("Craving","Famished","Ravenous")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, hungerLevels )
        howHungrySpinner.adapter = adapter

        howHungrySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                numPizzasTextView.text = ""
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

    }

    fun calculateClick(view: View) {

        // Get the text that was typed into the EditText
        val numAttendStr = numAttendEditText.text.toString()

        // Convert the text into an integer
        val numAttend = numAttendStr.toIntOrNull() ?: 0

        // Get hunger level selection
        val hungerLevel = when (howHungrySpinner.selectedItemPosition) {
            0 -> PizzaCalculator.HungerLevel.LIGHT
            1 -> PizzaCalculator.HungerLevel.MEDIUM
            else -> PizzaCalculator.HungerLevel.RAVENOUS
        }

        // Get the number of pizzas needed
        val calc = PizzaCalculator(numAttend, hungerLevel)
        val totalPizzas = calc.totalPizzas

        // Place totalPizzas into the string resource and display
        val totalText = getString(R.string.total_pizzas_num, totalPizzas)
        numPizzasTextView.text = totalText
    }
    fun showHelpMessage(view: android.view.View) {
        Toast.makeText(this, "Hank needs youuuu to calculate how many pizzas he needs for his pizza party",Toast.LENGTH_LONG).show()
    }
}