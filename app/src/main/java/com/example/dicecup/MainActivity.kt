package com.example.dicecup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    val mGenerator = Random()

    val TAG = "MainActivity"
    val diceIds = arrayOf(0, R.drawable.dice_1, R.drawable.dice_2, R.drawable.dice_3, R.drawable.dice_4, R.drawable.dice_5, R.drawable.dice_6)
   val mHistory = mutableListOf<Pair<Int, Int>>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ArrayAdapter.createFromResource(
                this,
                R.array.DiceNum_array,
                android.R.layout.simple_spinner_dropdown_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spNumberOfDice.adapter = adapter
        }

        spNumberOfDice?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.d(TAG, "Nothing has been selected")
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val item = parent?.getItemAtPosition(position)
                Log.d(TAG, "Spinner selection made $item")
            }

        }
        updateDiceImage(Pair(6, 6))
        Log.d(TAG, "OnCreate")

        if (savedInstanceState != null)
        {
            Log.d(TAG, "saved state NOT null")
            val history = savedInstanceState.getSerializable("history") as Array<Pair<Int,Int>>
            history.forEach { p -> mHistory.add(p) }
            updateHistory()
        }
    }

    fun onClickRoll(view: View) {
        val d1 = mGenerator.nextInt(6) + 1
        val d2 = mGenerator.nextInt(6) + 1
        imgDice1.setImageResource(diceIds[d1])
        imgDice2.setImageResource(diceIds[d2])
        mHistory.add(Pair(d1, d2))
        updateHistory()
    }

    private fun updateHistory() {
        var s = ""
        mHistory.forEach{p -> val e1 = p.first; val e2 = p.second
        s += "$e1 - $e2\n"}
        tvHistory.text = s
    }

    private fun updateDiceImage(p: Pair<Int,Int>)
    {
        imgDice1.setImageResource(diceIds[p.first])
        imgDice2.setImageResource(diceIds[p.second])
    }

    fun onCLickClear(view: View) {
        mHistory.clear()
        updateHistory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(TAG, "history is saved")
        outState.putSerializable("history", mHistory.toTypedArray())
    }

}