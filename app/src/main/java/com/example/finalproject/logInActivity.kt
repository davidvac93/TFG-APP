package com.example.finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast


class logInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        val dbHelper = HelperDB(this)

        val buttonLog: Button? = findViewById(R.id.buttonLogIn)         //Boton inicio para pasar a la pantalla de los fragments
        buttonLog?.setOnClickListener() {

            val id: EditText? = findViewById(R.id.editTextNumberDNI)
            val user: EditText? = findViewById(R.id.editTextUserName)
            val password: EditText? = findViewById(R.id.editTextNumberPassword2)
            val db = dbHelper.readableDatabase

            val projection = arrayOf(HelperDB.FeedEntry.COLUMN_NAME, HelperDB.FeedEntry.COLUMN_LASTNAME,
                    HelperDB.FeedEntry.COLUMN_DNI, HelperDB.FeedEntry.COLUMN_FLAT, HelperDB.FeedEntry.COLUMN_USER, HelperDB.FeedEntry.COLUMN_PASSWORD)
            val selection = "${HelperDB.FeedEntry.COLUMN_USER} = ?"
            val selectionArgs = arrayOf(user?.text.toString())

            val cursor = db.query(
                    HelperDB.FeedEntry.TABLE_NAME,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null
            )

            var foundUser = ""
            var foundPassword = ""

            while (cursor.moveToNext()) {
                //foundUser = cursor.getString(cursor.getColumnIndexOrThrow(HelperDB.FeedEntry.COLUMN_USER)).toString()
                foundUser = cursor.getString(cursor.getColumnIndex("user"))
                foundPassword = cursor.getString(cursor.getColumnIndex("password"))
                //foundPassword = cursor.getString(cursor.getColumnIndexOrThrow(HelperDB.FeedEntry.COLUMN_PASSWORD)).toString()

                //user!!.setText(foundUser)
                //password!!.setText(foundPassword)
            }

            if (foundUser.equals(user?.text.toString() )&& foundPassword.equals(password?.text.toString())) {
                val sendToMainActivity = Intent(this, LoggedActivity::class.java)
                sendToMainActivity.putExtra("nombre", foundUser)
                startActivity(sendToMainActivity)
                finish()
            } else {
                Toast.makeText(this, "Error al introducir los datos de usuario/contraseña", Toast.LENGTH_SHORT).show()
            }
        }

        val buttonBack: Button? = findViewById(R.id.buttonBack)         //Boton atras para volver al main
        buttonBack!!.setOnClickListener() {
            val sendToMainActivity = Intent(this, MainActivity::class.java)
            startActivity(sendToMainActivity)
            finish()
        }
    }
}