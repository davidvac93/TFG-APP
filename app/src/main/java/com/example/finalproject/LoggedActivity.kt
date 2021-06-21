package com.example.finalproject

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class LoggedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.logged_activity)

        val welcome: TextView? = findViewById(R.id.textUserWelcome)
        val logOff: TextView = findViewById(R.id.logOFF)
        val newApoint: Button = findViewById(R.id.newAppointment)
        val cancelApoint: Button = findViewById(R.id.cancelAppointment)
        val myApoints: Button = findViewById(R.id.viewAppointment)
        var nombre: String = ""

        if (intent.hasExtra("nombre")) {
            welcome?.text = "Bienvenido " + intent.getStringExtra("nombre")
            nombre = intent.getStringExtra("nombre")!!
        } else {
            welcome?.text = "Bienvenido de nuevo"
        }


        val dbHelper = HelperDB(this)


        newApoint.setOnClickListener {
            val sendToCreateAppointment = Intent (this, CreateAppointment::class.java)
            sendToCreateAppointment.putExtra("nombre", nombre)
            startActivity(sendToCreateAppointment)
            //this.finish()
        }

        cancelApoint.setOnClickListener {
            val sendToCancelAppointment = Intent (this, CancelAppointment::class.java)
            sendToCancelAppointment.putExtra("nombre", nombre)
            startActivity(sendToCancelAppointment)
            //this.finish()
        }


        myApoints.setOnClickListener {
            val sendToViewAppointment = Intent (this, ViewAppointment::class.java)
            sendToViewAppointment.putExtra("nombre", nombre)
            startActivity(sendToViewAppointment)
            //this.finish()
        }


        logOff.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("¡ATENCION!")
                    .setIcon(getResources().getDrawable(R.drawable.ic_baseline_back_hand_24))
                    .setMessage("¿Realmente desea salir de la aplicación?")
                    .setPositiveButton("Aceptar", DialogInterface.OnClickListener { dialog, which -> finishAffinity() })
                    .setNegativeButton("Cancelar", DialogInterface.OnClickListener { dialog, which ->  })
            val dialog = builder.create()
            dialog.show()
            }
        }
    }