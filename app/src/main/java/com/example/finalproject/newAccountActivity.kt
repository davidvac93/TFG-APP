package com.example.finalproject

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class newAccountActivity : AppCompatActivity() {
    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_account)

        val name: EditText = findViewById(R.id.editTextName)
        val lastName: EditText = findViewById(R.id.editTextLastName)
        var dni: EditText = findViewById(R.id.editTextNumberDNI)
        val flat: EditText = findViewById(R.id.editTextNumberFlatNewAcount)
        val user: EditText = findViewById(R.id.editTextTextPersonNameUserNewAcount)
        val password: EditText = findViewById(R.id.editTextNumberPassword)


        val dbHelper = HelperDB(this)

        val saveUser: Button? = findViewById(R.id.buttonSaveNewAcount)         //Asignamos boton de crear cuenta
        saveUser?.setOnClickListener {
            val db = dbHelper.writableDatabase

            if (name.text.toString().length > 0 && lastName.text.toString().length > 0 && dni.text.toString().length > 0        //Comprobamos si introduce algun dato el usuario
                     && flat.text.toString().length > 0 && user.text.toString().length > 0 && password.text.toString().length > 0) {

                val values = ContentValues().apply {
                    put(HelperDB.FeedEntry.COLUMN_NAME, name.text.toString())
                    put(HelperDB.FeedEntry.COLUMN_LASTNAME, lastName.text.toString())
                    put(HelperDB.FeedEntry.COLUMN_DNI, dni.text.toString())
                    put(HelperDB.FeedEntry.COLUMN_FLAT, flat.text.toString())
                    put(HelperDB.FeedEntry.COLUMN_USER, user.text.toString())
                    put(HelperDB.FeedEntry.COLUMN_PASSWORD, password.text.toString())
                }


                val newRowId = db?.insert(HelperDB.FeedEntry.TABLE_NAME, null, values)              //Sitodo sale bien creamos usuario en BBDD
                Toast.makeText(this, "Enhorabuena has creado tu cuenta en NOW", Toast.LENGTH_LONG).show()

                if (newRowId == -1.toLong()) {
                    Toast.makeText(this, "ERROR NO HAS PODIDO CREAR TU CUENTA NOW CONSULTE CON EL ADMINISTRADOR", Toast.LENGTH_LONG).show()   //Error a la hora de crearlo
            }

                val sendMainActivity = Intent(this, MainActivity::class.java)           //Envio hacia la pantalla de log in
                startActivity(sendMainActivity)
                finish()


            } else if (name.text == null || name.text.toString().length >= 20){           //Mensajes de error para cada value
                Toast.makeText(this, "Introduzca un nombre o ha superado el límete de veintidos caracteres", Toast.LENGTH_LONG).show()
            } else if (lastName.text == null || lastName.text.toString().length >=40) {
                Toast.makeText(this, "Introduzca los apellidos o ha superado el límte de cuarenta caracteres", Toast.LENGTH_SHORT).show()
            } else if (dni.text ==  null || dni.text.toString().length >= 9 ) {
                Toast.makeText(this, "Introduzca el DNI o el DNI ha superado el límite de nueve caracteres", Toast.LENGTH_SHORT).show()
            } else if (flat.text ==  null || flat.text.toString().length >= 4) {
                Toast.makeText(this, "Introouzca un número de piso o ha superado el límite de 6 caracteres", Toast.LENGTH_SHORT).show()
            } else if (user.text == null || user.text.toString().length >= 14) {
                Toast.makeText(this, "Introduzca un nommbre de usuario o ha superado el límte de catorce caracteres", Toast.LENGTH_SHORT).show()
            } else if (password.text == null || password.text.toString().length >= 8) {
                Toast.makeText(this, "Introduzca una contraseña o ha superado el límite de 8 caracteres", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Revise los datos introducidos por favor", Toast.LENGTH_SHORT).show()          //Si hay varios errores mostramos el generico
            }


        }
    }
}