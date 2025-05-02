package com.fake.money_lock.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fake.money_lock.R
import com.fake.money_lock.data.Expense

class ExpenseAdapter(private val expenses: List<Expense>) :
    RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_expense, parent, false)
        return ExpenseViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val currentExpense = expenses[position]
        holder.tvCategory.text = currentExpense.category
        holder.tvAmount.text = "R${currentExpense.amount}"
        holder.tvDate.text = currentExpense.date
        holder.tvNote.text = currentExpense.note ?: "No notes"
    }

    override fun getItemCount(): Int {
        return expenses.size
    }

    inner class ExpenseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvCategory: TextView = itemView.findViewById(R.id.tvCategory)
        val tvAmount: TextView = itemView.findViewById(R.id.tvAmount)
        val tvDate: TextView = itemView.findViewById(R.id.tvDate)
        val tvNote: TextView = itemView.findViewById(R.id.tvNote)
    }
}
