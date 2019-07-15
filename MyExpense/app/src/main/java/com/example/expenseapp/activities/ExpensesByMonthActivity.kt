package com.example.expenseapp.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.TextView
import com.example.expenseapp.DatabaseHandler
import com.example.expenseapp.adapter.CustomAdapterByMonth
import com.example.expenseapp.DataClassByMonth
import com.example.expenseapp.R

class ExpensesByMonthActivity : AppCompatActivity() {

    var databaseHandler: DatabaseHandler? = null
    lateinit var myListview: ListView
    lateinit var myAdapter: CustomAdapterByMonth
    var monthArrList = arrayListOf<DataClassByMonth>()
    var totalYear: Int = 0
    lateinit var totalYearTextView: TextView

    var  totalJan: Int = 0
    var  totalFeb: Int = 0
    var  totalMar: Int = 0
    var  totalApr: Int = 0
    var  totalMay: Int = 0
    var  totalJun: Int = 0
    var  totalJul: Int = 0
    var  totalAug: Int = 0
    var  totalSep: Int = 0
    var  totalOct: Int = 0
    var  totalNov: Int = 0
    var  totalDec: Int = 0

    var obj1: DataClassByMonth =
        DataClassByMonth()
    var obj2: DataClassByMonth =
        DataClassByMonth()
    var obj3: DataClassByMonth =
        DataClassByMonth()
    var obj4: DataClassByMonth =
        DataClassByMonth()
    var obj5: DataClassByMonth =
        DataClassByMonth()
    var obj6: DataClassByMonth =
        DataClassByMonth()
    var obj7: DataClassByMonth =
        DataClassByMonth()
    var obj8: DataClassByMonth =
        DataClassByMonth()
    var obj9: DataClassByMonth =
        DataClassByMonth()
    var obj10: DataClassByMonth =
        DataClassByMonth()
    var obj11: DataClassByMonth =
        DataClassByMonth()
    var obj12: DataClassByMonth =
        DataClassByMonth()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expenses_by_month)

        databaseHandler = DatabaseHandler(this)
        myListview = findViewById(R.id.expensesByMonthLsv)
        totalYearTextView = findViewById(R.id.totalYearExpenses)

        var arrayList = databaseHandler!!.getAllUsers()
        for (item in arrayList)
        {
            if (item.expenseDate.contains("Jan")) {
                totalJan = totalJan + item.expenseAmount.toInt()
            } else if (item.expenseDate.contains("Feb")) {
                totalFeb = totalFeb + item.expenseAmount.toInt()
            }
            if (item.expenseDate.contains("Mar")) {
                totalMar = totalMar + item.expenseAmount.toInt()
            } else if (item.expenseDate.contains("Apr")) {
                totalApr = totalApr + item.expenseAmount.toInt()
            }
            if (item.expenseDate.contains("May")) {
                totalMay = totalMay + item.expenseAmount.toInt()
            } else if (item.expenseDate.contains("Jun")) {
                totalJun = totalJun + item.expenseAmount.toInt()
            }
            if (item.expenseDate.contains("Jul")) {
                totalJul = totalJul + item.expenseAmount.toInt()
            } else if (item.expenseDate.contains("Aug")) {
                totalAug = totalAug + item.expenseAmount.toInt()
            }
            if (item.expenseDate.contains("Sep")) {
                totalSep = totalSep + item.expenseAmount.toInt()
            } else if (item.expenseDate.contains("Oct")) {
                totalOct = totalOct + item.expenseAmount.toInt()
            }
            if (item.expenseDate.contains("Nov")) {
                totalNov = totalNov + item.expenseAmount.toInt()
            } else if (item.expenseDate.contains("Dec")) {
                totalDec = totalDec + item.expenseAmount.toInt()
            }

            totalYear = totalYear + item.expenseAmount.toInt()
        }
        totalYearTextView.text = totalYear.toString()

        obj1.monthAmount = totalJan.toString()
        obj1.monthName = "January"
        monthArrList.add(obj1)
        obj2.monthAmount = totalFeb.toString()
        obj2.monthName = "February"
        monthArrList.add(obj2)
        obj3.monthAmount = totalMar.toString()
        obj3.monthName = "March"
        monthArrList.add(obj3)
        obj4.monthAmount = totalApr.toString()
        obj4.monthName = "April"
        monthArrList.add(obj4)
        obj5.monthAmount = totalMay.toString()
        obj5.monthName = "May"
        monthArrList.add(obj5)
        obj6.monthAmount = totalJun.toString()
        obj6.monthName = "June"
        monthArrList.add(obj6)
        obj7.monthAmount = totalJul.toString()
        obj7.monthName = "July"
        monthArrList.add(obj7)
        obj8.monthAmount = totalAug.toString()
        obj8.monthName = "August"
        monthArrList.add(obj8)
        obj9.monthAmount = totalSep.toString()
        obj9.monthName = "September"
        monthArrList.add(obj9)
        obj10.monthAmount = totalOct.toString()
        obj10.monthName = "October"
        monthArrList.add(obj10)
        obj11.monthAmount = totalNov.toString()
        obj11.monthName = "November"
        monthArrList.add(obj11)
        obj12.monthAmount = totalDec.toString()
        obj12.monthName = "December"
        monthArrList.add(obj12)



        myAdapter =
            CustomAdapterByMonth(this, R.layout.row_expenses, monthArrList)
        myListview.adapter = myAdapter

    }
}
