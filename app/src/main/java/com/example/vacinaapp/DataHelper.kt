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
        private const val ID = "id_local"
        private const val POSTO = "posto_saude"
        private const val DISTRITO = "distrito"
        private const val ENDERECO = "endereco"
        private const val TELEFONE = "telefone"
        private const val SEGUNDA = "segunda"
        private const val TERCA = "terca"
        private const val QUARTA = "quarta"
        private const val QUINTA = "quinta"
        private const val SEXTA = "sexta"
        private const val SABADO = "sabado"
        private const val DOMINGO = "domingo"

    }



    override fun onCreate(db: SQLiteDatabase?) {
        val query = ("CREATE TABLE " + TABELANOME + " ("
                + ID + " INTEGER PRIMARY KEY, " +
                POSTO + " TEXT, " +
                DISTRITO + " TEXT, " +
                ENDERECO + " TEXT, " +
                TELEFONE + " TEXT, " +
                SEGUNDA + " TEXT, " +
                TERCA + " TEXT, " +
                QUARTA + " TEXT, " +
                QUINTA + " TEXT, " +
                SEXTA + " TEXT, " +
                SABADO + " TEXT, " +
                DOMINGO + " TEXT" +
                ")")

        db?.execSQL(query)

        val q2 =(
            "INSERT INTO tabela_postos(id_local, posto_saude, distrito, endereco, telefone, segunda, terca, quarta, quinta, sexta, sabado, domingo) "+
                    "VALUES " +
                    "(1, 'POSTO DE SAÚDE DE GALENA', 'Galena', 'Zona rural - Galena, Pres. Olegário - MG, 38750-000', "+
                    "'Não informado', '07:00 às 17:00', '07:00 às 17:00', '07:00 às 17:00', '07:00 às 17:00', '07:00 às 17:00'," +
                    "'Fechado', 'Fechado'), " +


                    "(2, 'POSTO DE SAÚDE DE PONTE FIRME', 'Ponte Firme', 'R. Princesa Isabel - Pte. Firme, Pres. Olegário - MG, 38750-000', "+
                    "'Não informado', '07:00 às 17:00', '07:00 às 17:00', '07:00 às 17:00', '07:00 às 17:00', '07:00 às 17:00'," +
                    "'Fechado', 'Fechado'), " +

                    "(3, 'PSF AEROPORTO', 'Presidente Olegário', 'R. Pres. J K, 690 - Pres. Olegário, MG, 38750-000', "+
                    "'(34) 38112885', '07:00 às 17:00', '07:00 às 17:00', '07:00 às 17:00', '07:00 às 17:00', '07:00 às 17:00'," +
                    "'Fechado', 'Fechado'), " +

                    "(4, 'UBS ANDORINHAS', 'Presidente Olegário', 'R. Saturnino Xavier Rosa, 661 - Pres. Olegário, MG, 38750-000', "+
                    "'(38) 38112136', '07:00 às 17:00', '07:00 às 17:00', '07:00 às 17:00', '07:00 às 17:00', '07:00 às 17:00'," +
                    "'Fechado', 'Fechado'), " +

                    "(5, 'UBS BILÉ GODINHO', 'Presidente Olegário', 'R. Ilídio Araújo, 314 - Pres. Olegário, MG, 38750-000', "+
                    "'(34) 38112136', '07:00 às 19:00', '07:00 às 19:00', '07:00 às 19:00', '07:00 às 19:00', '07:00 às 19:00'," +
                    "'Fechado', 'Fechado'), " +

                    "(6, 'UBS DERCINA MARIA ANDRÉ', 'Santiago de Minas', 'Estrada Pres. Olegário  - Zona Rural - Presidente Olegário, MG - CEP: 38750000', "+
                    "'Não informado', '07:00 às 16:00', '07:00 às 16:00', '07:00 às 16:00', '07:00 às 16:00', '07:00 às 16:00'," +
                    "'Fechado', 'Fechado'), " +

                    "(7, 'UBS MATEUS CAIXETA', 'Presidente Olegário', 'R. Isabel De Souza Vasconcelos, bairro Mateus caixeta, 115 - Pres. Olegário - MG, 38750-000', "+
                    "'Não informado', '07:00 às 17:00', '07:00 às 17:00', '07:00 às 17:00', '07:00 às 17:00', '07:00 às 17:00'," +
                    "'Fechado', 'Fechado'), " +

                    "(8, 'UBS PLANALTO', 'Presidente Olegário', 'R. Pimpim Moreira, bairro Planalto, 915 - Pres. Olegário, MG, 38750-000', "+
                    "'(34) 38112205', '07:00 às 17:00', '07:00 às 17:00', '07:00 às 17:00', '07:00 às 17:00', '07:00 às 17:00'," +
                    "'Fechado', 'Fechado'), " +

                    "(9, 'UBS ZONA RURAL', 'Presidente Olegário', 'Rua Brejo Alegre, Planalto - Pres. Olegário - MG, 38750-000', "+
                    "'(34) 38112973', '07:00 às 19:00', '07:00 às 19:00', '07:00 às 19:00', '07:00 às 19:00', '07:00 às 19:00'," +
                    "'Fechado', 'Fechado')"
            )
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