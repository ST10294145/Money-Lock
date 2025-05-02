package com.fake.money_lock.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ExpenseViewModel(application: Application) : AndroidViewModel(application) {

    private val expenseDao: ExpenseDao = ExpenseDatabase.getDatabase(application).expenseDao()
    val expenses: LiveData<List<Expense>> = expenseDao.getExpensesForUser(1) // Replace 1 with the actual user ID

    fun addExpense(expense: Expense) {
        viewModelScope.launch {
            expenseDao.addExpense(expense)
        }
    }
}
