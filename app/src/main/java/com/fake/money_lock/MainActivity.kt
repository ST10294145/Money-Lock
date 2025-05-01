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
        setContentView(R.layout.register_page) // Set the UI layout for the registration screen

        // Get references to UI elements
        val nameInput = findViewById<EditText>(R.id.etName)
        val emailInput = findViewById<EditText>(R.id.etEmail)
        val passwordInput = findViewById<EditText>(R.id.etPassword)
        val confirmPasswordInput = findViewById<EditText>(R.id.etConfirmPassword)
        val registerButton = findViewById<Button>(R.id.btnRegister)
        val loginButton = findViewById<Button>(R.id.btnLogin) // Login button

        // Get access to the DAO (Data Access Object) from the Room database
        val userDao = UserDatabase.getDatabase(applicationContext).userDao()

        // Set a click listener on the Register button
        registerButton.setOnClickListener {
            val name = nameInput.text.toString().trim()
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString()
            val confirmPassword = confirmPasswordInput.text.toString()

            // Validate input fields
            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            } else {
                lifecycleScope.launch(Dispatchers.IO) {
                    try {
                        val existingUser = userDao.getUserByEmail(email)
                        withContext(Dispatchers.Main) {
                            if (existingUser != null) {
                                Toast.makeText(this@MainActivity, "Email already registered", Toast.LENGTH_SHORT).show()
                            } else {
                                val newUser = User(
                                    id = 0,
                                    name = name,
                                    email = email,
                                    password = password
                                )
                                lifecycleScope.launch(Dispatchers.IO) {
                                    userDao.addUser(newUser)
                                    withContext(Dispatchers.Main) {
                                        Toast.makeText(this@MainActivity, "Registration successful!", Toast.LENGTH_SHORT).show()
                                        nameInput.text.clear()
                                        emailInput.text.clear()
                                        passwordInput.text.clear()
                                        confirmPasswordInput.text.clear()
                                    }
                                }
                            }
                        }
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@MainActivity, "An error occurred: ${e.message}", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }

        // Set a click listener on the Login button
        loginButton.setOnClickListener {
            Toast.makeText(this, "Navigate to Login Page", Toast.LENGTH_SHORT).show()
            // You can replace the above line with navigation like below:
            // val intent = Intent(this, LoginActivity::class.java)
            // startActivity(intent)
            // finish()
        }
    }
}
