package seve.alo.apps.sqllite


import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.*

class MainActivity(var adapter: NotasAdapter? = null) : AppCompatActivity() {

    var listaDeNotas = ArrayList<Notas>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listView = findViewById<ListView>(R.id.listView)

        listaDeNotas.add(Notas(1, "Título", "Descripción"))
        listaDeNotas.add(Notas(2, "Título", "Descripción"))
        listaDeNotas.add(Notas(3, "Título", "Descripción"))

        adapter = NotasAdapter(this, listaDeNotas)
        listView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

        val buscar = menu!!.findItem(R.id.app_bar_search).actionView as SearchView
        val manejador = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        buscar.setSearchableInfo(manejador.getSearchableInfo(componentName))
        buscar.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                Toast.makeText(applicationContext, p0, Toast.LENGTH_SHORT).show()
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

}