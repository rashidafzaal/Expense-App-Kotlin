package com.example.expenseapp.adapter

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.expenseapp.DataClassByMonth
import com.example.expenseapp.R

class CustomAdapterByMonth : ArrayAdapter<DataClassByMonth> {

    var layout: Int
    var c: Context?
    var arrList: ArrayList<DataClassByMonth>

    constructor(context: Context?, resource: Int, objects: ArrayList<DataClassByMonth>) : super(context, resource, objects) {
        this.c = context
        this.layout = resource
        this.arrList = objects
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        var row: View;
        val vh: ViewHolder
        if (convertView == null) {
            val inflater = (c as Activity).layoutInflater
            row = inflater.inflate(R.layout.row_by_month, parent, false)
            vh = ViewHolder(row)
            row.tag = vh
        } else {
            row = convertView
            vh = row.tag as ViewHolder
        }

        vh.monthName!!.text = arrList!!.get(position).monthName
        vh.monthAmount!!.text = " = "+arrList!!.get(position).monthAmount

        return row!!
    }


    private class ViewHolder(view: View?) {
        val monthName: TextView?
        val monthAmount: TextView?

        init {
            this.monthName = view?.findViewById(R.id.row_monthName)
            this.monthAmount = view?.findViewById(R.id.row_monthAmount)

        }
    }

}