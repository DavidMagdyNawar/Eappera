package com.android.example.earppa

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cardview_login.setOnClickListener {
        userSignIn()
        }
    }


    private fun userSignIn() {

        val email = input_email.text.toString().trim()
        val password = input_password.text.toString().trim()

        if (email.isEmpty()) {
            input_email.error = "Email is required"
            input_email.requestFocus()
            return
        }
        if (password.isEmpty()) {
            input_password.error = "Password required"
            input_password.requestFocus()
            return
        }

        if(email == "davidmagdynawar@gmail.com" && password == "11223344")
        {
            var intent = Intent(this@MainActivity,LocationActivity::class.java)
            startActivity(intent)
        } else
        {
            Toast.makeText(this,"You entered a wrong email or password",Toast.LENGTH_SHORT).show()
            return
        }

    }

}
