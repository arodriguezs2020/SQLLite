package seve.alo.apps.sqllite

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast

class AddActivity : AppCompatActivity() {

    val editTextTitulo = findViewById<EditText>(R.id.editTextTitulo)
    val editTextDescripcion = findViewById<EditText>(R.id.editTextDescripcion)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
    }

    fun btnAdd(view: android.view.View) {

        val baseDatos = DBManager(this)
        val values = ContentValues()
        values.put("Titulo", editTextTitulo.text.toString())
        values.put("Descripcion", editTextDescripcion.text.toString())

        val ID = baseDatos.insert(values)
        if (ID > 0) {
            Toast.makeText(this, "Nota agregada correctamente", Toast.LENGTH_LONG).show()
            finish()
        } else{
            Toast.makeText(this, "Nota no agregada", Toast.LENGTH_LONG).show()
        }

    }
}