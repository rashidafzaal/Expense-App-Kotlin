package com.example.expenseapp

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class CustomAdapter : ArrayAdapter<DataClass> {

    var layout: Int
    var c: Context?
    var arrList: ArrayList<DataClass>

    constructor(context: Context?, resource: Int, objects: ArrayList<DataClass>) : super(context, resource, objects) {
        this.c = context
        this.layout = resource
        this.arrList = objects
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        var row: View;
        val vh: ViewHolder
        if (convertView == null) {
            val inflater = (c as Activity).layoutInflater
            row = inflater.inflate(R.layout.row_expenses, parent, false)
            vh = ViewHolder(row)
            row.tag = vh
        } else {
            row = convertView
            vh = row.tag as ViewHolder
        }

        vh.id!!.text = arrList!!.get(position).id
        vh.expenseName!!.text = arrList!!.get(position).expenseName
        vh.expenseAmount!!.text = " = "+arrList!!.get(position).expenseAmount
        vh.expenseDate!!.text = arrList!!.get(position).expenseDate


        return row!!
    }


    private class ViewHolder(view: View?) {
        val id: TextView?
        val expenseName: TextView?
        val expenseAmount: TextView?
        val expenseDate: TextView?

        init {
            this.id = view?.findViewById(R.id.row_id)
            this.expenseName = view?.findViewById(R.id.row_expenseName)
            this.expenseAmount = view?.findViewById(R.id.row_expenseAmount)
            this.expenseDate = view?.findViewById(R.id.row_expenseDate)

        }
    }

}