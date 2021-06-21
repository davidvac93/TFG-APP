package com.example.finalproject

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.util.*


class CancelAppointment : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cancel_appointment)


        val hello: TextView = findViewById(R.id.textViewUserCancel)
        val lofOff: TextView =findViewById(R.id.textViewLLogOffCancel)
        val back: Button = findViewById(R.id.buttonBackCancel)
        val delete: Button = findViewById(R.id.buttonDelete)
        val dynamic: ListView = findViewById(R.id.dinamic)
        var nombre: String = ""
        val editDelete: EditText = findViewById(R.id.editTextIdDelete)

        val dbHelper = HelperDB(this)

        if (intent.hasExtra("nombre")) {
            hello?.text = "Bienvenido " + intent.getStringExtra("nombre")
            nombre = intent.getStringExtra("nombre")!!
        } else {
            hello?.text = "Bienvenido"
        }

        val db = dbHelper.readableDatabase
        val projection = arrayOf(HelperDB.FeedEntry.COLUMN_SERVICES, HelperDB.FeedEntry.COLUMN_HOUR, HelperDB.FeedEntry.COLUMN_DATE, HelperDB.FeedEntry.COLUMN_USER_SERVICE, HelperDB.FeedEntry.COLUMN_AUTO)
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
        var hourFound = ""
        var autoFound = ""

        var allData =  ArrayList <String> ()

        while (cursor.moveToNext()) {
            foundService = cursor.getString(cursor.getColumnIndexOrThrow(HelperDB.FeedEntry.COLUMN_SERVICES))
            dateFound = cursor.getString(cursor.getColumnIndexOrThrow(HelperDB.FeedEntry.COLUMN_DATE))
            hourFound = cursor.getString(cursor.getColumnIndexOrThrow(HelperDB.FeedEntry.COLUMN_HOUR))
            autoFound = cursor.getString(cursor.getColumnIndexOrThrow(HelperDB.FeedEntry.COLUMN_AUTO))

            allData.add(" Reserva "+ foundService +" el "+ dateFound + " a las "+ hourFound + " ID " + autoFound + "\n\n")

            Log.i("PRUEBACURSOR", "Reserva" + foundService + dateFound + hourFound);
        }
        val arrayAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                allData)
        dynamic.adapter = arrayAdapter




        lofOff.setOnClickListener {
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


        delete.setOnClickListener {
            val db = dbHelper.writableDatabase
            // Define 'where' part of query.
            val selection = "${HelperDB.FeedEntry.COLUMN_AUTO} LIKE ?"
            // Specify arguments in placeholder order.
            val selectionArgs = arrayOf(editDelete.text.toString())
            // Issue SQL statement.
            val deletedRows = db.delete(HelperDB.FeedEntry.TABLE_NAME_SERVICE, selection, selectionArgs)
            Toast.makeText(this,"Se han eliminado " + deletedRows + " la reserva con ID: " + autoFound,
                    Toast.LENGTH_SHORT).show()

            if (deletedRows != 0) {
                val sendToActivity = Intent(this, LoggedActivity::class.java)
                sendToActivity.putExtra("nombre", nombre)
                startActivity(sendToActivity)
                finish()
            } else {
                Toast.makeText(this,"Hubo un error ",
                        Toast.LENGTH_SHORT).show()
            }
        }

        back.setOnClickListener {
            val sendToMainActivity = Intent(this, LoggedActivity::class.java)
            sendToMainActivity.putExtra("nombre", nombre)
            startActivity(sendToMainActivity)
            finish()
        }

    }
}

private fun ListView.toString(allData: String) {

}
