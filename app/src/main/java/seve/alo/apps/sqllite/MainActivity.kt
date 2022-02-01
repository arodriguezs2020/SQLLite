package seve.alo.apps.sqllite


import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.*
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity(var adapter: NotasAdapter? = null) : AppCompatActivity() {

    // Instanciamos nuestra lista de Notas
    val listaDeNotas = ArrayList<Notas>()

    // Creamos dos variables para nuestro listView y nuestro boton flotante
    var listView: ListView? = null
    var fab : FloatingActionButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

        // Los instanciamos en el onCreate asignandoles a cada uno su elemento
        listView = findViewById<ListView>(R.id.listView)
        fab = findViewById<FloatingActionButton>(R.id.fab)

        // Cargamos la lista de notas que tengamos en nuestra base de datos de sqlLite
        cargarQuery("%")

        // Llamamos al metodo setOnClickListener de nuestro boton flotante
        fab!!.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }
    }

    // --- Cargamos la lista de notas tambien en el onResume --- //
    override fun onResume() {
        super.onResume()
        cargarQuery("%")
    }

    // --- Creamos nuestra funcion cargarQuery que nos cargara las notas que tengamos en nuestra base de datos --- //

    // SELECT ID, Titulo, Descripcion FROM Notas WHERE (Titulo like ?) ORDER BY Titulo
    @SuppressLint("Range")
    fun cargarQuery(titulo: String) {
        // Instanciamos la clase DBManager
        val baseDatos = DBManager(this)
        val columnas = arrayOf("ID", "Titulo", "Descripcion")
        val selectionArgs = arrayOf(titulo)
        val cursor = baseDatos.query(columnas, "Titulo like ?", selectionArgs, "Titulo")

        listaDeNotas.clear()

        // Hacemos un bucle para que vaya recorriendo cada uno de los elementos de nuestra base de datos y la vaya añadiendo a la vista
        if (cursor.moveToFirst()) {
            do {
                val ID = cursor.getInt(cursor.getColumnIndex("ID"))
                val titulo = cursor.getString(cursor.getColumnIndex("Titulo"))
                val descripcion = cursor.getString(cursor.getColumnIndex("Descripcion"))

                listaDeNotas.add(Notas(ID, titulo, descripcion))
            }while (cursor.moveToNext())
        }

        // Por ultimo cargamos nuestro adapter y se lo asignamos a nuestro listView
        adapter = NotasAdapter(this, listaDeNotas)
        listView!!.adapter = adapter
    }

    // --- Sobreescribimos la funcion onCreateOptionsMenu y onOptionItemSelected para la parte del menu --- //
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

        val buscar = menu!!.findItem(R.id.app_bar_search).actionView as SearchView
        val manejador = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        buscar.setSearchableInfo(manejador.getSearchableInfo(componentName))
        buscar.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                Toast.makeText(applicationContext, p0, Toast.LENGTH_SHORT).show()
                cargarQuery("%" + p0  + "%")
                return false
            }
            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menuAgregar -> {
                val intent = Intent(this, AddActivity::class.java)
                startActivity(intent)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    // --- Creamos la clase NotasAdapter y es muy importante poner la palabra reservada inner --- //
    inner class NotasAdapter(contexto : Context, var listaDeNotas : ArrayList<Notas>) : BaseAdapter() {

        var contexto : Context? = contexto

        override fun getView(p0: Int, view: View?, p2: ViewGroup?): View {
            var convertView : View ? = view
            if (convertView == null) {
                convertView = View.inflate(contexto, R.layout.molde_notas, null)
            }
            val nota = listaDeNotas[p0]

            // val inflater = contexto!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val miVista = convertView!!
            miVista.findViewById<TextView>(R.id.textViewTitulo).text = nota.titulo
            miVista.findViewById<TextView>(R.id.textViewContenido).text = nota.descripcion

            miVista.findViewById<ImageView>(R.id.imageViewBorrar).setOnClickListener {
                val dbManager = DBManager(this.contexto!!)
                val selectionArgs = arrayOf(nota.notaID.toString())

                dbManager.borrar("ID=?", selectionArgs)
                cargarQuery("%")
            }

            miVista.findViewById<ImageView>(R.id.imageViewEdit).setOnClickListener {
                val intent = Intent(this@MainActivity, AddActivity::class.java)
                intent.putExtra("ID", nota.notaID)
                intent.putExtra("titulo", nota.titulo)
                intent.putExtra("descrip", nota.descripcion)
                startActivity(intent)
            }

            return miVista
        }

        override fun getItem(p0: Int): Any {
            return listaDeNotas[p0]
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }

        override fun getCount(): Int {
            return  listaDeNotas.size
        }
    }

}

