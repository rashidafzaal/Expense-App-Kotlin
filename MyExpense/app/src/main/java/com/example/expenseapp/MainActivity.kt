package com.example.expenseapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.widget.EditText
import java.text.SimpleDateFormat
import java.util.*
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import com.example.expenseapp.ExpensesByMonth.ExpensesByMonthActivity
import java.io.Serializable


class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var addBtn: Button
    lateinit var viewBtn: Button
    lateinit var chooseDateBtn: Button
    lateinit var selectedDateTextView: TextView
    lateinit var viewExpensesByMonth: Button

    lateinit var mAlertDialog: AlertDialog
    lateinit var cal: Calendar

    var databaseHandler: DatabaseHandler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        databaseHandler = DatabaseHandler(this)
        initUi()
        initListeners()
        initTodayDate()

    }

    private fun initUi() {
        addBtn = findViewById(R.id.addNewExpense)
        viewBtn = findViewById(R.id.viewExpenses)
        chooseDateBtn = findViewById(R.id.chooseDateBtn)
        selectedDateTextView = findViewById(R.id.selectedDateTextView)
        viewExpensesByMonth = findViewById(R.id.viewExpensesByMonth)

    }

    private fun initListeners() {
        addBtn.setOnClickListener(this)
        viewBtn.setOnClickListener(this)
        chooseDateBtn.setOnClickListener(this)
        viewExpensesByMonth.setOnClickListener(this)
    }

    private fun initTodayDate() {
        cal = Calendar.getInstance()
        val mYear = cal.get(Calendar.YEAR)
        val mMonth = cal.getDisplayName(Calendar.MONTH,Calendar.SHORT,Locale.US)
        val mDay = cal.get(Calendar.DAY_OF_MONTH)

        var strDate: String = mDay.toString() + "-" + mMonth.toString() + "-" + mYear.toString()
        selectedDateTextView.setText(strDate)

    }

    //Listener
    val mDateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
        cal.set(Calendar.YEAR, year)
        cal.set(Calendar.MONTH, monthOfYear)
        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        val myFormat = "d-MMM-yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        selectedDateTextView.text = sdf.format(cal.time)

    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.addNewExpense -> {
                showMyDialog();
            }
            R.id.viewExpenses -> {
                //get all data
                var entryArrayList = databaseHandler!!.getAllUsers()
                //filter according to current month
                cal = Calendar.getInstance()
                val mMonth = cal.getDisplayName(Calendar.MONTH,Calendar.SHORT,Locale.US)
                var filteredArrList = arrayListOf<DataClass>()
                for (item in entryArrayList) {
                    if (item.expenseDate.contains(mMonth)) {
                        filteredArrList.add(item)
                    }
                }
                val intent = Intent(this@MainActivity, ViewExpensesActivity::class.java)
                val args = Bundle()
                args.putSerializable("ARRAYLIST", filteredArrList as Serializable)
                intent.putExtra("BUNDLE", args)
                startActivity(intent)
            }
            R.id.chooseDateBtn -> {
                val dialog = DatePickerDialog(
                    this,
                    mDateSetListener,
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
                )
                dialog.show()
            }
            R.id.viewExpensesByMonth -> {

                val intent = Intent(this@MainActivity, ExpensesByMonthActivity::class.java)
                startActivity(intent)

            }
        }
    }

    private fun showMyDialog() {

        //inflate
        val view = layoutInflater.inflate(R.layout.add_dialog, null)
        //build
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Add New Expense")
        builder.setView(view)
        val expenseNameTxtView = view.findViewById(R.id.expenseName) as EditText
        val expenseAmountTxtView = view.findViewById(R.id.expenseAmount) as EditText
        val btnAddEntry = view.findViewById(R.id.addEntryBtn) as Button

        //buttons click
        btnAddEntry.setOnClickListener {
            addNewEntry(
                expenseNameTxtView.text.toString(),
                expenseAmountTxtView.text.toString(),
                selectedDateTextView.text.toString()
            )
        }
        builder.setNegativeButton(android.R.string.cancel)
        { dialog, p1 ->
            dialog.cancel()
        }
        mAlertDialog = builder.show()
//        expenseNameTxtView.setOnFocusChangeListener(View.OnFocusChangeListener { v, hasFocus ->
//            if (hasFocus) {
//                mAlertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
//            }
//        })
    }



    private fun addNewEntry(expenseName: String, expenseAmount: String, expenseDate: String) {

        if (expenseName.equals("") || expenseAmount.equals("") || expenseDate.equals("")) {
            Toast.makeText(this, "Fill all Fields", Toast.LENGTH_SHORT).show();
        } else {
            //create and fill Object
            val entry: DataClass = DataClass()
            var success: Boolean = false

            entry.expenseName = expenseName
            entry.expenseAmount = expenseAmount
            entry.expenseDate = expenseDate

            success = databaseHandler!!.addExpense(entry)
            if (success) {
                Toast.makeText(this, "Added Successfully", Toast.LENGTH_SHORT).show();
                mAlertDialog.dismiss()
            } else {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                mAlertDialog.dismiss()
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.myMenu -> {
                showConfirmDialog()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showConfirmDialog() {
        lateinit var dialog: AlertDialog
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Clear Database")
        builder.setMessage("Do you Want to Clear All Database?")
        val dialogClickListener = DialogInterface.OnClickListener{ _, which ->
            when(which){
                DialogInterface.BUTTON_POSITIVE -> {
                    databaseHandler!!.deleteAllEntries()
                    Toast.makeText(this@MainActivity, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                }
                DialogInterface.BUTTON_NEGATIVE -> {}
            }
        }
        builder.setPositiveButton("YES",dialogClickListener)
        builder.setNegativeButton("NO",dialogClickListener)
        dialog = builder.create()
        dialog.show()
    }
}
