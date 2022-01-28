package seve.alo.apps.sqllite

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class NotasAdapter(contexto : Context, var listaDeNotas : ArrayList<Notas>) : BaseAdapter() {

    var contexto : Context? = contexto

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val nota = listaDeNotas[p0]
        val inflater = contexto!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val miVista = inflater.inflate(R.layout.molde_notas, null)
        miVista.findViewById<TextView>(R.id.textViewTitulo).text = nota.titulo
        miVista.findViewById<TextView>(R.id.textViewContenido).text = nota.descripcion
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
