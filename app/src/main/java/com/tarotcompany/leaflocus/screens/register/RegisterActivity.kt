package com.tarotcompany.leaflocus.screens.register

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.tarotcompany.leaflocus.R
import com.tarotcompany.leaflocus.data.AppDatabase
import com.tarotcompany.leaflocus.screens.login.LoginActivity
import com.tarotcompany.leaflocus.screens.login.LoginPresenter

class RegisterActivity : AppCompatActivity(), RegisterContract.View {

    private lateinit var presenter: RegisterPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val database = AppDatabase.getDatabase(applicationContext)
        presenter = RegisterPresenter(
            this,
            database.userDao(),
            database.achievementDao() // Add this here
        )
        val userDao = database.userDao()

        val edittextUsername = findViewById<EditText>(R.id.edittextUsername)
        val edittextEmail = findViewById<EditText>(R.id.edittextEmail)
        val edittextPassword = findViewById<EditText>(R.id.edittextPassword)
        val edittextConfirmPassword = findViewById<EditText>(R.id.edittextConfirmPassword)
        val buttonSubmit = findViewById<Button>(R.id.buttonSubmit)
        val textviewBackToLogin = findViewById<TextView>(R.id.textviewBackToLogin)

        buttonSubmit.setOnClickListener {
            val username = edittextUsername.text.toString()
            val email = edittextEmail.text.toString()
            val password = edittextPassword.text.toString()
            val confirmPassword = edittextConfirmPassword.text.toString()

            if (password != confirmPassword) {
                showRegisterError("Passwords do not match")
                return@setOnClickListener
            }

            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                showRegisterError("Please fill all fields")
                return@setOnClickListener
            }

            presenter.register(username, email, password)
        }

        textviewBackToLogin.setOnClickListener {
            finish()
        }
    }

    override fun showLoading() {
        // Show progress bar
    }

    override fun hideLoading() {
        // Hide progress bar
    }

    override fun showRegisterSuccess() {
        Toast.makeText(this, "Registration Successful!", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun showRegisterError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }
}