package com.example.expenseapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHandler(context: Context) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSIOM) {

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE = "CREATE TABLE $TABLE_NAME " +
                "($ID Integer PRIMARY KEY, " +
                "$EXPENSE_NAME TEXT, " +
                "$EXPENSE_AMOUNT TEXT, " +
                "$EXPENSE_DATE TEXT)"
        db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Called when the database needs to be upgraded
    }

    //Insert
    fun addExpense(user: DataClass): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(EXPENSE_NAME, user.expenseName)
        values.put(EXPENSE_AMOUNT, user.expenseAmount)
        values.put(EXPENSE_DATE, user.expenseDate)
        val _success = db.insert(TABLE_NAME, null, values)
        db.close()
        return (Integer.parseInt("$_success") != -1)
    }

    //get all users
    fun getAllUsers(): ArrayList<DataClass> {
        var arrayList: ArrayList<DataClass> = ArrayList()
        var allUser: String = "";
        val db = readableDatabase
        val selectALLQuery = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(selectALLQuery, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    var id = cursor.getString(cursor.getColumnIndex(ID))
                    var expenseName = cursor.getString(cursor.getColumnIndex(EXPENSE_NAME))
                    var expenseAmount = cursor.getString(cursor.getColumnIndex(EXPENSE_AMOUNT))
                    var expenseDate = cursor.getString(cursor.getColumnIndex(EXPENSE_DATE))

                    var obj: DataClass = DataClass()
                    obj.id = id
                    obj.expenseName = expenseName
                    obj.expenseAmount = expenseAmount
                    obj.expenseDate = expenseDate

                    arrayList.add(obj)


                    allUser = "$allUser\n$id = $expenseName $expenseAmount $expenseDate"
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        db.close()
        return arrayList
    }
    fun deleteEntry(_id: String): Boolean {
        val db = this.writableDatabase
        val _success = db.delete(TABLE_NAME, ID + "=?", arrayOf(_id)).toLong()
        db.close()
        return Integer.parseInt("$_success") != -1
    }


    fun deleteAllEntries(){
        val db = this.writableDatabase
        db.execSQL("delete from "+ TABLE_NAME);
        db.close()
    }
    companion object {
        private val DB_NAME = "expensesDB"
        private val DB_VERSIOM = 1;
        private val TABLE_NAME = "expenses"
        private val ID = "id"
        private val EXPENSE_NAME = "ExpenseName"
        private val EXPENSE_AMOUNT = "ExpenseAmount"
        private val EXPENSE_DATE = "ExpenseDate"
    }


}