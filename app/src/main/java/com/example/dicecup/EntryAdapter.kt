package com.example.dicecup

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class EntryAdapter(context: Context,
                    private val entries: Array<Entry>
) : ArrayAdapter<Entry>(context, 0, entries)
{



    override fun getView(position: Int, v: View?, parent: ViewGroup): View {
        var v1: View? = v
        if (v1 == null) {
            val li = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)
                    as LayoutInflater
            v1 = li.inflate(R.layout.cell, null)

        }
        val resView: View = v1!!

        val e = entries[position]
        val numbers = resView.findViewById<TextView>(R.id.numbers)
        var s = ""

        s += "${e.time}:  "
        e.ListofNumbers.forEach { n ->
           s += "$n "
        }
        numbers.text = s

        return resView
    }
}