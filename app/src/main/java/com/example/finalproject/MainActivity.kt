package com.example.finalproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.content.Intent as Intent1

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val newAcountButton: Button? = findViewById(R.id.createAcountButton)         //Asignamos boton de crear cuenta
        newAcountButton!!.setOnClickListener () {
            val sendToNewAccount = Intent1(this, newAccountActivity::class.java)    //Envio hacia la pantalla de crear cuenta
            startActivity(sendToNewAccount)
        }

        val logInButton: Button? = findViewById(R.id.LogInButton)                    //Asignamos boton de iniciar sesion
        logInButton!!.setOnClickListener() {
            val sendToLogIn = Intent1(this, logInActivity::class.java)           //Envio hacia la pantalla de log in
            startActivity(sendToLogIn)
        }
    }
}