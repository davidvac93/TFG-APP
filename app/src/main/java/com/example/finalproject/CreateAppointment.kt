package com.example.finalproject

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.example.finalproject.R.id.editPeople
import com.example.finalproject.R.id.editTextNumberDNI
import java.util.*
import kotlin.time.days
import kotlin.time.microseconds
import kotlin.time.milliseconds

class CreateAppointment : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_appointment)

        var mTimePicker: TimePickerDialog
        val c = Calendar.getInstance()
        val cal = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val hourTime = cal.get(Calendar.HOUR_OF_DAY)
        val minuteTime = cal.get(Calendar.MINUTE)
        val spinner = findViewById<Spinner>(R.id.spinner)
        val welcomeUser: TextView = findViewById(R.id.welcomeUser)
        val logOffUser: TextView = findViewById(R.id.logoffUser)
        var dpd: DatePickerDialog     //Para poder meter comprobacion bbdd
        val saveInfo: Button = findViewById(R.id.buttonSave)
        val date: TextView = findViewById(R.id.editTextDate)
        val hour: TextView = findViewById(R.id.editTextTime)
        val people: EditText = findViewById(editPeople)
        var nombre: String = ""


        var services = arrayOf("Gimnasio", "Pádel", "Piscina")


        if (intent.hasExtra("nombre")) {
            welcomeUser?.text = "Bienvenido " + intent.getStringExtra("nombre")
            nombre = intent.getStringExtra("nombre")!!
        } else {
            welcomeUser?.text = "Bienvenido"
        }


        val dbHelper = HelperDB(this)


        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, services)
        spinner.adapter = adapter

        logOffUser.setOnClickListener {
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


        date.setOnClickListener {
            dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                date.text = "$mDay/$mMonth/$mYear" //Probar toString
            }, year, month, day)
            dpd.show()
        }

        hour.setOnClickListener {
            mTimePicker = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                hour.text = String.format("%d : %d", hourOfDay, minute)  //Probar toString
            }, hourTime, minuteTime, true)
            mTimePicker.show()
        }


        saveInfo.setOnClickListener {


            val db = dbHelper.writableDatabase

            if (services.toString() != null && date.text.toString() != null && hour.text.toString() != null && people.text.toString() != null){

                val values = ContentValues().apply {
                    put(HelperDB.FeedEntry.COLUMN_SERVICES, spinner.selectedItem.toString())
                    put(HelperDB.FeedEntry.COLUMN_HOUR, hour.text.toString())
                    put(HelperDB.FeedEntry.COLUMN_DATE, date.text.toString())
                    put(HelperDB.FeedEntry.COLUMN_PEOPLE, people.text.toString())
                    put(HelperDB.FeedEntry.COLUMN_USER_SERVICE, nombre)
                }

                val newRowId = db?.insert(HelperDB.FeedEntry.TABLE_NAME_SERVICE, null, values)
                Toast.makeText(this, "Reservado", Toast.LENGTH_LONG).show()
                if (newRowId == -1.toLong()) {
                    Toast.makeText(this, "Error, consulte con su administrador", Toast.LENGTH_LONG).show()
                }

                val sendToActivityLogged = Intent(this, LoggedActivity::class.java)
                sendToActivityLogged.putExtra("nombre", nombre)
                startActivity(sendToActivityLogged)
                finish()


            }else if (services.toString().isEmpty()) {
                Toast.makeText(this, "Seleccione un servicio", Toast.LENGTH_LONG).show()
            }else if (month != 0) {
                Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
            }else if (hour.text.toString().contentEquals(hour.text.toString())) {
                Toast.makeText(this, "Ya tienes una cita reservada a esta hora", Toast.LENGTH_LONG)
                    .show()
            }else{
                Toast.makeText(this, "Error, consulte con su administrador", Toast.LENGTH_LONG).show()
            }




            fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {

            }
        }
    }
}