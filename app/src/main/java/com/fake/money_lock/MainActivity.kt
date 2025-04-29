package com.fake.money_lock

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.fake.money_lock.data.User
import com.fake.money_lock.data.UserDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_page)

        val nameInput = findViewById<EditText>(R.id.etName)
        val emailInput = findViewById<EditText>(R.id.etEmail)
        val passwordInput = findViewById<EditText>(R.id.etPassword)
        val confirmPasswordInput = findViewById<EditText>(R.id.etConfirmPassword)
        val registerButton = findViewById<Button>(R.id.btnRegister)

        val userDao = UserDatabase.getDatabase(applicationContext).userDao()

        registerButton.setOnClickListener {
            val name = nameInput.text.toString().trim()
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString()
            val confirmPassword = confirmPasswordInput.text.toString()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            } else {
                lifecycleScope.launch(Dispatchers.IO) {
                    val existingUser = userDao.getUserByEmail(email)
                    if (existingUser != null) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(applicationContext, "Email already registered", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        val newUser = User(
                            id = 0,
                            name = name,
                            email = email,
                            password = password
                        )
                        userDao.addUser(newUser)
                        withContext(Dispatchers.Main) {
                            Toast.makeText(applicationContext, "Registration successful!", Toast.LENGTH_SHORT).show()
                            // Optionally, clear the form fields
                            nameInput.text.clear()
                            emailInput.text.clear()
                            passwordInput.text.clear()
                            confirmPasswordInput.text.clear()
                        }
                    }
                }
            }
        }
    }
}
