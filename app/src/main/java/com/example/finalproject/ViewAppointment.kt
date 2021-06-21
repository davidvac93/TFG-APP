package com.example.finalproject

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.log

class ViewAppointment : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_appointment)

        var nombre: String = ""
        val viewWelcome: TextView = findViewById(R.id.textViewWelcomeViewApoint)
        val logOff: TextView = findViewById(R.id.textViewLogOff)
        val back: Button = findViewById(R.id.buttonBackView)
        val text: TextView = findViewById(R.id.textViewTest)
        var date: String = ""
        var hour: String = ""
        var service: String = ""


        val dbHelper = HelperDB(this)


        if (intent.hasExtra("nombre")) {
            viewWelcome?.text = "Bienvenido " + intent.getStringExtra("nombre")
            nombre = intent.getStringExtra("nombre")!!
        } else {
            viewWelcome?.text = "Bienvenido"
        }

        val db = dbHelper.readableDatabase
        val projection = arrayOf(HelperDB.FeedEntry.COLUMN_SERVICES, HelperDB.FeedEntry.COLUMN_HOUR, HelperDB.FeedEntry.COLUMN_DATE, HelperDB.FeedEntry.COLUMN_USER_SERVICE)
        val selection = "${HelperDB.FeedEntry.COLUMN_USER_SERVICE} = ?"
        val selectionArgs = arrayOf(nombre?.toString())

        val cursor = db.query(
                HelperDB.FeedEntry.TABLE_NAME_SERVICE,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        )

        var foundService = ""
        var dateFound = ""
        var hourFound= ""

        var allData: String = ""

        while (cursor.moveToNext()) {
            foundService = cursor.getString(cursor.getColumnIndexOrThrow(HelperDB.FeedEntry.COLUMN_SERVICES))
            dateFound = cursor.getString(cursor.getColumnIndexOrThrow(HelperDB.FeedEntry.COLUMN_DATE))
            hourFound = cursor.getString(cursor.getColumnIndexOrThrow(HelperDB.FeedEntry.COLUMN_HOUR))

            allData = allData + " Reserva de "+ foundService +" el "+ dateFound + " a las "+ hourFound + "\n\n"

            Log.i("PRUEBACURSOR", "Reserva" + foundService + dateFound + hourFound);
        }

        if (nombre == nombre){
            text.setText(allData)
        }

//----------------------------------------------------------------------------------------------------------------------------------

        logOff.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("¡ATENCION!")
                    .setIcon(resources.getDrawable(R.drawable.ic_baseline_back_hand_24))
                    .setMessage("¿Realmente desea salir de la aplicación?")
                    .setPositiveButton("Aceptar", DialogInterface.OnClickListener { dialog, which -> finishAffinity() })
                    .setNegativeButton("Cancelar", DialogInterface.OnClickListener { dialog, which -> })
            val dialog = builder.create()
            dialog.show()
            finish()
        }

        back.setOnClickListener {
            val sendToMainActivity = Intent(this, LoggedActivity::class.java)
            sendToMainActivity.putExtra("nombre", nombre)
            startActivity(sendToMainActivity)
            finish()
        }
    }
}