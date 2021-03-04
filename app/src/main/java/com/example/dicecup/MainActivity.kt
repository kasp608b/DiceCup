package com.example.dicecup

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TableRow
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*

class MainActivity : AppCompatActivity() {
    val REQUEST_CODE_ANSWER = 10
    val RESULT_OK = 1
    val RESULT_CLEAR = 2
    val mGenerator = Random()
    var numberOfDice = 1
    val TAG = "MainActivity"
    val diceIds = arrayOf(0, R.drawable.dice_1, R.drawable.dice_2, R.drawable.dice_3, R.drawable.dice_4, R.drawable.dice_5, R.drawable.dice_6)
    var mHistory2DList = mutableListOf<Entry>()
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
            val history = savedInstanceState.getSerializable("history") as Array<Entry>
            history.forEach { p -> mHistory2DList.add(p) }

        }


    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onClickRoll(view: View) {

        var diceArray = Array(numberOfDice, { i -> i * 1 })
        for (i in 0 until numberOfDice)
        {
            diceArray[i] = mGenerator.nextInt(6) + 1

        }
        mHistory2DList.add(Entry(diceArray, DateTimeFormatter
                .ofPattern("HH:mm:ss")
                .withZone(ZoneOffset.UTC)
                .format(Instant.now()) ))
        Log.d(TAG, "$numberOfDice")
        initializeDiceBoard(diceArray)

    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(TAG, "history is saved")
        outState.putSerializable("history", mHistory2DList.toTypedArray())
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

    fun onClickHistory(view: View){
        val i = Intent(this, HistoryActivity::class.java)
        i.putExtra("history",mHistory2DList.toTypedArray())
        startActivityForResult(i,REQUEST_CODE_ANSWER)

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_CODE_ANSWER) {
            if (resultCode == RESULT_CLEAR) {
                mHistory2DList.clear()

            } else if(resultCode == RESULT_OK) {

                val list = data?.extras?.get("history") as Array<Entry>

                mHistory2DList.clear()

                list.forEach { p -> mHistory2DList.add(p) }


            }
        }


    }






}