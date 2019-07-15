package com.example.expenseapp.activities

import android.content.DialogInterface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.Menu
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import com.example.expenseapp.adapter.CustomAdapter
import com.example.expenseapp.DataClass
import com.example.expenseapp.DatabaseHandler
import com.example.expenseapp.R


class ViewExpensesActivity : AppCompatActivity(), AdapterView.OnItemLongClickListener{


    lateinit var myListview: ListView
    lateinit var myAdapter: CustomAdapter
    lateinit var totalExpenses: TextView
    lateinit var entriesArrayList: ArrayList<DataClass>
    
    var databaseHandler: DatabaseHandler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_expenses)

        databaseHandler = DatabaseHandler(this)
        myListview = findViewById(R.id.viewExpensesListView)
        totalExpenses = findViewById(R.id.totalExpenses)

        val intent = intent
        val args = intent.getBundleExtra("BUNDLE")
        entriesArrayList = args.getSerializable("ARRAYLIST") as ArrayList<DataClass>

        setListView(entriesArrayList)
        calculateTotal(entriesArrayList)

    }

    private fun calculateTotal(entriesArrayList: ArrayList<DataClass>) {
        var total:Int = 0
        for (item in entriesArrayList) {
            total = total + item.expenseAmount.toInt()
        }
        totalExpenses.text = "= " +total.toString()
    }

    private fun setListView(entriesArrayList: ArrayList<DataClass>) {
        myAdapter =
            CustomAdapter(this, R.layout.row_expenses, entriesArrayList)
        myListview.adapter = myAdapter
        myListview.setOnItemLongClickListener(this)

    }
    override fun onItemLongClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long): Boolean {
        var txt:TextView = view!!.findViewById(R.id.row_id)
        var id = txt.text
        showMyDialog(id.toString(), position)

        return true;
    }

    private fun showMyDialog(id: String, position: Int) {
        lateinit var dialog: AlertDialog
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Remove Expense")
        builder.setMessage("Do you Want to Delete this Expense?")
        val dialogClickListener = DialogInterface.OnClickListener{_,which ->
            when(which){
                DialogInterface.BUTTON_POSITIVE -> {deleteNow(id, position)}
                DialogInterface.BUTTON_NEGATIVE -> {}
            }
        }
        builder.setPositiveButton("YES",dialogClickListener)
        builder.setNegativeButton("NO",dialogClickListener)
        dialog = builder.create()
        dialog.show()
    }

    private fun deleteNow(id: String, position: Int) {
        var success:Boolean = false
        success = databaseHandler!!.deleteEntry(id)
        if (success) {
            Toast.makeText(this@ViewExpensesActivity, "Deleted Successfully", Toast.LENGTH_SHORT).show();
            myAdapter.remove(myAdapter.getItem(position))
            myAdapter.notifyDataSetChanged()
            calculateAgain()
        } else {
            Toast.makeText(this@ViewExpensesActivity, "Error Occurred", Toast.LENGTH_SHORT).show();
        }

    }

    private fun calculateAgain() {
        var total:Int = 0
        for (item in entriesArrayList) {
            total = total + item.expenseAmount.toInt()
        }
        totalExpenses.text = "= " +total.toString()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.my_second_menu, menu)
        var myMenuItem = menu!!.findItem(R.id.menuTotal)
        myMenuItem.title = entriesArrayList.size.toString()
        return super.onCreateOptionsMenu(menu)
    }





}
