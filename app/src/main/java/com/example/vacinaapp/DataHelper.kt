package com.example.vacinaapp

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DataHelper(context: Context): SQLiteOpenHelper(context, dbName, null, dbVersion) {

    companion object {
        val dbVersion = 1
        private const val dbName = "BancoDados.db"
        private const val TABELANOME = "tabela_postos"
        private const val ID = "id"
        private const val POSTO = "posto_saude"
        private const val ENDERECO = "endereco"
        private const val DISTRITO = "distrito"
    }



    override fun onCreate(db: SQLiteDatabase?) {
        val query = ("CREATE TABLE " + TABELANOME + " ("
                + ID + " INTEGER PRIMARY KEY, " +
                POSTO + " TEXT," +
                ENDERECO + " TEXT" + ")")

        db?.execSQL(query)

        val q2 =
            "INSERT INTO tabela_postos(id, posto_saude, endereco) "+
                    "VALUES " +
                    "(1, 'POSTO DE SAÚDE DE PONTE FIRME', " +
                    "'R. Princesa Isabel - Pte. Firme, Pres. Olegário - MG, 38750-000'), "+
                    "(2, 'POSTO DE SAÚDE DE GALENA', " +
                    "'Zona rural - Galena, Pres. Olegário - MG, 38750-000'), "+
                    "(3, 'POSTO DE UBS MATEUS CAIXETA', " +
                    "'R. Isabel De Souza Vasconcelos, bairro Mateus caixeta, 115 - Pres. Olegário - MG, 38750-000')"



        db?.execSQL(q2)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //Usar o código abaixo para reiniciar a tabela
        //db!!.execSQL("DROP TABLE IF EXISTS tabela_postos")
    }

    fun rawQuery(query: String?): Cursor {
        val db = this.writableDatabase
        val mCursor: Cursor = db.rawQuery(query, null)
        return mCursor
    }

}