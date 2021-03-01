package com.example.dicecup

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.historylayout.*

class HistoryActivity : AppCompatActivity() {
    val REQUEST_CODE_ANSWER = 10
    val RESULT_OK = 1
    val RESULT_CLEAR = 2
    var mHistory2DList = mutableListOf<Entry>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.historylayout)



        val list = intent.extras?.get("history") as Array<Entry>

        mHistory2DList.clear()

        list.forEach { p -> mHistory2DList.add(p) }


        val adapter = EntryAdapter(this, list)
        historyList.adapter = adapter

        backButton.setOnClickListener{v -> onClickBack()}
        clearButton.setOnClickListener{v -> onCLickClear()}

    }
    fun onClickBack(){

        val intent = Intent()
        intent.putExtra("history", mHistory2DList.toTypedArray())
        setResult(RESULT_OK,intent)
        finish()
    }

    fun onCLickClear() {
        mHistory2DList.clear()
        setResult(RESULT_CLEAR,intent)
        finish()

    }

}