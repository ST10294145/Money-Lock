package com.fake.money_lock

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.fake.money_lock.data.Expense
import com.fake.money_lock.data.UserDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class AddExpenseActivity : AppCompatActivity() {

    private lateinit var etAmount: EditText
    private lateinit var etDescription: EditText
    private lateinit var spinnerCategory: Spinner
    private lateinit var btnPickDate: Button
    private lateinit var btnSaveExpense: Button

    private var selectedDate: Long = System.currentTimeMillis() // Default to current date

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_expense)

        etAmount = findViewById(R.id.etAmount)
        etDescription = findViewById(R.id.etDescription)
        spinnerCategory = findViewById(R.id.spinnerCategory)
        btnPickDate = findViewById(R.id.btnPickDate)
        btnSaveExpense = findViewById(R.id.btnSaveExpense)

        setupCategorySpinner()
        setupDatePicker()

        btnSaveExpense.setOnClickListener {
            saveExpense()
        }
    }

    private fun setupCategorySpinner() {
        val categories = arrayOf("Food", "Transport", "Entertainment", "Utilities", "Other")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCategory.adapter = adapter
    }

    private fun setupDatePicker() {
        btnPickDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val datePicker = DatePickerDialog(
                this,
                { _, year, month, day ->
                    val cal = Calendar.getInstance()
                    cal.set(year, month, day)
                    selectedDate = cal.timeInMillis
                    val formatted = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(cal.time)
                    btnPickDate.text = formatted
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePicker.show()
        }
    }

    private fun saveExpense() {
        val amountText = etAmount.text.toString()
        val note = etDescription.text.toString()
        val category = spinnerCategory.selectedItem.toString()
        val userId = getLoggedInUserId() // Replace this with real logic from logged-in session

        if (amountText.isBlank()) {
            Toast.makeText(this, "Amount is required.", Toast.LENGTH_SHORT).show()
            return
        }

        val amount = amountText.toDoubleOrNull()
        if (amount == null || amount <= 0) {
            Toast.makeText(this, "Enter a valid amount.", Toast.LENGTH_SHORT).show()
            return
        }

        val formattedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date(selectedDate))

        val expense = Expense(
            userId = userId,
            category = category,
            amount = amount,
            date = formattedDate,
            note = if (note.isBlank()) null else note
        )

        lifecycleScope.launch(Dispatchers.IO) {
            val db = UserDatabase.getDatabase(applicationContext)
            db.expenseDao().addExpense(expense)
            runOnUiThread {
                Toast.makeText(this@AddExpenseActivity, "Expense saved.", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    // Temporary mock function; replace with actual session management
    private fun getLoggedInUserId(): Int {
        return 1 // Replace with actual logic to get current user's ID
    }
}
