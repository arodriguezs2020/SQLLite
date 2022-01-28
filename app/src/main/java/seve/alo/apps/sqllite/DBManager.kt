package seve.alo.apps.sqllite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class DBManager {

    // Atributos de nuestra Base de Datos

    val dbNombre = "MisNotas"
    val dbTabla = "Notas"
    val columnaID = "ID"
    val columnaTitulo = "Titulo"
    val columnaDescripcion = "Descripcion"
    val dbVersion = 1

    // CREATE TABLE IF NOT EXISTS + Notas + (ID INTEGER PRIMARY KEY AUTOINCREMENT, Titulo TEXT NOT NULL, Descipcion TEXT NOT NULL)
    val sqlCrearTabla = "CREATE TABLE IF NOT EXISTS " + dbTabla + " (" + columnaID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
            columnaTitulo + " TEXT NOT NULL," + columnaDescripcion + " TEXT NOT NULL)"
    var sqlDB:SQLiteDatabase?=null

    constructor(contexto: Context) {
        val db = DBHelperNotas(contexto)
        sqlDB = db.writableDatabase

    }

    inner class DBHelperNotas(contexto: Context) :SQLiteOpenHelper(contexto, dbNombre, null, dbVersion){

        var contexto:Context?=contexto

        override fun onCreate(p0: SQLiteDatabase?) {
            p0!!.execSQL(sqlCrearTabla)
            Toast.makeText(this.contexto, "Base datos creada", Toast.LENGTH_SHORT).show()
        }

        override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
            p0!!.execSQL("Drop table IF EXISTS" + dbTabla)
        }
    }

    fun insert(values: ContentValues) : Long{
        val ID = sqlDB!!.insert(dbTabla, "", values)
        return ID
    }

}