package com.example.dicecup

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    val mGenerator = Random()
    var numberOfDice = 1
    val TAG = "MainActivity"
    val diceIds = arrayOf(0, R.drawable.dice_1, R.drawable.dice_2, R.drawable.dice_3, R.drawable.dice_4, R.drawable.dice_5, R.drawable.dice_6)
   val mHistory = mutableListOf<Pair<Int, Int>>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var diceArray = arrayOf(6)
        initializeDiceBoard(diceArray)
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
                numberOfDice = position + 1
                var diceArray = Array(numberOfDice, { i -> i * 1 })
                for (i in 0 until numberOfDice)
                {
                    diceArray[i] = 6
                }
                Log.d(TAG, "$numberOfDice")
                initializeDiceBoard(diceArray)
            }

        }
        Log.d(TAG, "OnCreate")

        if (savedInstanceState != null)
        {
            Log.d(TAG, "saved state NOT null")
            val history = savedInstanceState.getSerializable("history") as Array<Pair<Int, Int>>
            history.forEach { p -> mHistory.add(p) }
            updateHistory()
        }
    }

    fun onClickRoll(view: View) {

        /*val d1 = mGenerator.nextInt(6) + 1
        val d2 = mGenerator.nextInt(6) + 1
        //imgDice1.setImageResource(diceIds[d1])
        //imgDice2.setImageResource(diceIds[d2])
        mHistory.add(Pair(d1, d2))
        updateHistory()*/

        var diceArray = Array(numberOfDice, { i -> i * 1 })
        for (i in 0 until numberOfDice)
        {
            diceArray[i] = mGenerator.nextInt(6) + 1
        }
        Log.d(TAG, "$numberOfDice")
        initializeDiceBoard(diceArray)
    }

    private fun updateHistory() {
        var s = ""
        mHistory.forEach{ p -> val e1 = p.first; val e2 = p.second
        s += "$e1 - $e2\n"}
        tvHistory.text = s
    }

    private fun updateDiceImage(p: Pair<Int, Int>)
    {
        //imgDice1.setImageResource(diceIds[p.first])
        //imgDice2.setImageResource(diceIds[p.second])
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

    private fun initializeDiceBoard(arrayOfDice: Array<Int>)
    {
        diceBoard.removeAllViewsInLayout()
        var arraySize = arrayOfDice.size
        for ( i in 1 .. arraySize){
            val imageView = ImageView(this)
            imageView.layoutParams = TableRow.LayoutParams(150, 150)
            imageView.setImageResource(diceIds[arrayOfDice[i - 1]])
            diceBoard.addView(imageView)
            Log.d(TAG, "Create Number of dice: $arraySize, i:  $i")
        }
    }


}