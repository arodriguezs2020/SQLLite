package seve.alo.apps.sqllite

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import android.view.View
import java.lang.Exception

class AddActivity : AppCompatActivity() {

    // Creamos una variable llamada id que la instanciamos a 0, con esta podremos saber si se esta editando o añadiendo un valor
    var id = 0

    // Creamos dos variables para nuestros dos EditText
    var editTextTitulo: EditText? = null
    var editTextDescripcion: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        // Rellenamos las dos variables con los dos elementos llemados con la función findViewById
        editTextTitulo = findViewById<EditText>(R.id.editTextTitulo)
        editTextDescripcion = findViewById<EditText>(R.id.editTextDescripcion)

        // Con esta sentencia cogemos los datos que nos llegan del intent, en caso de que sea actualizar y pintamos los valores en sus campos
        try {
            val bundle : Bundle = intent.extras!!
            id = bundle.getInt("ID", 0)
            if (id != 0) {
                editTextTitulo!!.setText(bundle.getString("titulo"))
                editTextDescripcion!!.setText(bundle.getString("descrip"))
            }
        } catch (e: Exception) {
            print(e.printStackTrace())
        }
    }

    // --- Creamos una funcion para nuestro boton de agregar, si es añadir utilizará la función insert y sino la de actualizar --- //
    fun btnAdd(view: View) {

        val baseDatos = DBManager(this)
        val values = ContentValues()
        values.put("Titulo", editTextTitulo!!.text.toString())
        values.put("Descripcion", editTextDescripcion!!.text.toString())

        if (id == 0) {
            val ID = baseDatos.insert(values)
            if (ID > 0) {
                Toast.makeText(this, "Nota agregada correctamente", Toast.LENGTH_LONG).show()
                finish()
            } else{
                Toast.makeText(this, "Lo siento la nota no se agregó correctamente, por favor intentelo de nuevo", Toast.LENGTH_LONG).show()
            }
        } else{
            val selectionArgs = arrayOf(id.toString())

            val ID = baseDatos.actualizar(values, "ID=?", selectionArgs)
            if (ID > 0) {
                Toast.makeText(this, "Nota actualizada correctamente", Toast.LENGTH_LONG).show()
                finish()
            } else{
                Toast.makeText(this, "Lo siento la nota no se actualizó correctamente, por favor intentelo de nuevo", Toast.LENGTH_LONG).show()
            }
        }
    }
}