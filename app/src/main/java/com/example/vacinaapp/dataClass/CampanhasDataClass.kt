package com.example.vacinaapp.dataClass

data class CampanhasDataClass(val id_campanha: Int, val distrito_campanha: String, val id_posto_campanha: Int, val nome_posto: String,
                              val nome_campanha: String, val doenca_campanha: String, val data: String,
                              val horario: String, val publico_campanha: String, val detalhes: String){

    fun getId(): Int{
        return id_campanha
    }
}
