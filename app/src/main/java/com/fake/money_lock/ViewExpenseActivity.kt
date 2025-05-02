package com.fake.money_lock

import android.os.Bundle
import android.view.View // Add this import
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fake.money_lock.adapters.ExpenseAdapter
import com.fake.money_lock.data.ExpenseViewModel
import com.fake.money_lock.databinding.ActivityViewExpenseBinding

class ViewExpenseActivity : AppCompatActivity() {

    private lateinit var expenseViewModel: ExpenseViewModel
    private lateinit var binding: ActivityViewExpenseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inflate the layout
        binding = ActivityViewExpenseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up RecyclerView
        binding.recyclerViewExpenses.layoutManager = LinearLayoutManager(this)

        // Initialize ViewModel
        expenseViewModel = ViewModelProvider(this).get(ExpenseViewModel::class.java)

        // Observe LiveData to get the list of expenses
        expenseViewModel.expenses.observe(this, Observer { expenses ->
            if (expenses.isEmpty()) {
                // Show "No expenses recorded" if there are no expenses
                binding.tvNoExpenses.visibility = View.VISIBLE
                binding.recyclerViewExpenses.visibility = View.GONE
            } else {
                // Hide "No expenses" text and show the RecyclerView
                binding.tvNoExpenses.visibility = View.GONE
                binding.recyclerViewExpenses.visibility = View.VISIBLE
                // Set up the adapter with the expense list
                binding.recyclerViewExpenses.adapter = ExpenseAdapter(expenses)
            }
        })

        // Handling system bars (notches or status bar adjustments)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
