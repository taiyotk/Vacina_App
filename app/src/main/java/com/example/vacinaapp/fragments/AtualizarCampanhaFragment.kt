package com.example.vacinaapp.fragments

import android.database.Cursor
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.vacinaapp.DataHelper
import com.example.vacinaapp.R
import com.example.vacinaapp.dataClass.CampanhasDataClass
class AtualizarCampanhaFragment : Fragment() {

    companion object {

    }

    private var db: DataHelper? = null
    private var posto_id_camp_salvar: Int = -1
    private lateinit var distrito_camp_salvar: String
    private lateinit var posto_nome_camp_salvar: String
    private lateinit var titulo_camp_salvar: String
    private lateinit var doenca_camp_salvar: String
    private lateinit var data_inicio_salvar: String
    private lateinit var data_final_salvar: String
    private lateinit var data_camp_salvar: String
    private lateinit var hor_inicio_salvar: String
    private lateinit var hor_final_salvar: String
    private lateinit var hor_camp_salvar: String
    private lateinit var publico_camp_salvar: String
    private lateinit var detalhes_camp_salvar: String

    private lateinit var rgDistritoCamp: RadioGroup
    private lateinit var rgPostosCamp: RadioGroup
    private lateinit var buttonSalvar: Button
    private lateinit var listaCampanhas: ArrayList<CampanhasDataClass>

    //do banco de dados
    private var idCampanha: Int = -1
    private lateinit var nomeCampanha: String
    private lateinit var distritoCampanha: String
    private var idPostoCampanha: Int = -1
    private lateinit var postoNomeCampanha: String
    private lateinit var doencaCampanha: String
    private lateinit var dataCampanha: String
    private lateinit var horarioCampanha: String
    private lateinit var publicoCampanha: String
    private lateinit var detalhesCampanha: String

    //edittexts
    private lateinit var edit_titulo: EditText
    private lateinit var edit_doenca: EditText
    private lateinit var edit_data_inicio: EditText
    private lateinit var edit_data_final: EditText
    private lateinit var edit_hor_inicio: EditText
    private lateinit var edit_hor_final: EditText
    private lateinit var edit_publico_alvo: EditText
    private lateinit var edit_detalhes: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_atualizar_campanha, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id_key: Int = getKey() //id da campanha
        Log.d("id_key_teste", "idKey é $id_key")

        edit_titulo = view.findViewById(R.id.label_campanha_mod)
        edit_doenca = view.findViewById(R.id.label_doenca_camp_mod)
        edit_data_inicio = view.findViewById(R.id.label_data_inicio_camp_mod)
        edit_data_final = view.findViewById(R.id.label_data_fim_camp_mod)
        edit_hor_inicio = view.findViewById(R.id.label_hor_inicio_camp_mod)
        edit_hor_final = view.findViewById(R.id.label_hor_final_camp_mod)
        edit_publico_alvo = view.findViewById(R.id.label_publico_alvo_camp_mod)
        edit_detalhes = view.findViewById(R.id.label_detalhes_camp_mod)

        rgDistritoCamp = view.findViewById(R.id.radioGroup_distrito_camp_mod)
        rgPostosCamp = view.findViewById(R.id.radioGroup_postos_camp_mod)
        buttonSalvar = view.findViewById(R.id.btn_modif_campanha)

        //distritos
        val galenaDistrito = view.findViewById<RadioButton>(R.id.galena_rb_camp_mod)
        val ponteFirmeDistrito = view.findViewById<RadioButton>(R.id.ponte_firme_rb_camp_mod)
        val presidenteDistrito = view.findViewById<RadioButton>(R.id.presidente_olegario_rb_camp_mod)
        val santiagoDistrito = view.findViewById<RadioButton>(R.id.santiago_de_minas_rb_camp_mod)

        //postos
        val postoGalena = view.findViewById<RadioButton>(R.id.rb_posto_galena_camp_mod)
        val postoPonteFirme = view.findViewById<RadioButton>(R.id.rb_posto_ponte_firme_camp_mod)
        val postoAeroporto = view.findViewById<RadioButton>(R.id.rb_posto_aeroporto_camp_mod)
        val postoAndorinhas = view.findViewById<RadioButton>(R.id.rb_posto_andorinhas_camp_mod)
        val postoBileGodinho = view.findViewById<RadioButton>(R.id.rb_posto_godinho_camp_mod)
        val postoDercinaMariaAndre = view.findViewById<RadioButton>(R.id.rb_posto_dercina_maria_camp_mod)
        val postoMateusCaixeta = view.findViewById<RadioButton>(R.id.rb_posto_mateus_caixeta_camp_mod)
        val postoPlanalto = view.findViewById<RadioButton>(R.id.rb_posto_planalto_camp_mod)
        val postoZonaRural = view.findViewById<RadioButton>(R.id.rb_posto_zona_rural_camp_mod)



        fun loadData(idKey: Int){
            db = DataHelper(requireContext())
            val query: Cursor = db!!.rawQuery("SELECT * FROM tab_campanha WHERE id_campanha = $idKey")

            //para checar se o cursor está lendo todas as colunas
            //val count: Int = query.count
            //val countColumns = query.columnCount
            //Log.d("qt de linhas e colunas", "Linhas=$count, colunas=$countColumns")

            if(query.moveToNext()){
                idCampanha = query.getInt(0)
                distritoCampanha = query.getString(1)
                idPostoCampanha = query.getInt(2)
                postoNomeCampanha = query.getString(3)
                nomeCampanha = query.getString(4)
                doencaCampanha = query.getString(5)
                dataCampanha = query.getString(6)
                horarioCampanha = query.getString(7)
                publicoCampanha = query.getString(8)
                detalhesCampanha = query.getString(9)

                Log.d("dadosPosto", "id_campanha=$idCampanha, distrito_campanha=$distritoCampanha, id_posto_campanha=$idPostoCampanha, posto_nome_campanha=$postoNomeCampanha, nome_campanha=$nomeCampanha, doenca_campanha=$doencaCampanha, data=$dataCampanha, horario=$horarioCampanha, publico_campanha=$publicoCampanha, detalhes=$detalhesCampanha")
            }

            rgDistritoCamp.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { _, _ ->
                if (galenaDistrito.isChecked){
                    postoGalena.visibility = View.VISIBLE
                    postoPonteFirme.visibility = View.GONE
                    postoAeroporto.visibility = View.GONE
                    postoAndorinhas.visibility = View.GONE
                    postoBileGodinho.visibility = View.GONE
                    postoDercinaMariaAndre.visibility = View.GONE
                    postoMateusCaixeta.visibility = View.GONE
                    postoPlanalto.visibility = View.GONE
                    postoZonaRural.visibility = View.GONE
                }
                else if(ponteFirmeDistrito.isChecked){
                    postoGalena.visibility = View.GONE
                    postoPonteFirme.visibility = View.VISIBLE
                    postoAeroporto.visibility = View.GONE
                    postoAndorinhas.visibility = View.GONE
                    postoBileGodinho.visibility = View.GONE
                    postoDercinaMariaAndre.visibility = View.GONE
                    postoMateusCaixeta.visibility = View.GONE
                    postoPlanalto.visibility = View.GONE
                    postoZonaRural.visibility = View.GONE
                }
                else if(presidenteDistrito.isChecked){
                    postoGalena.visibility = View.GONE
                    postoPonteFirme.visibility = View.GONE
                    postoAeroporto.visibility = View.VISIBLE
                    postoAndorinhas.visibility = View.VISIBLE
                    postoBileGodinho.visibility = View.VISIBLE
                    postoDercinaMariaAndre.visibility = View.GONE
                    postoMateusCaixeta.visibility = View.VISIBLE
                    postoPlanalto.visibility = View.VISIBLE
                    postoZonaRural.visibility = View.VISIBLE
                }
                else if(santiagoDistrito.isChecked){
                    postoDercinaMariaAndre.visibility = View.VISIBLE
                    postoGalena.visibility = View.GONE
                    postoPonteFirme.visibility = View.GONE
                    postoAeroporto.visibility = View.GONE
                    postoAndorinhas.visibility = View.GONE
                    postoBileGodinho.visibility = View.GONE
                    postoMateusCaixeta.visibility = View.GONE
                    postoPlanalto.visibility = View.GONE
                    postoZonaRural.visibility = View.GONE
                }
            })

            when(idPostoCampanha){
                1 -> {
                    rgDistritoCamp.check(R.id.galena_rb_camp_mod)
                    rgPostosCamp.check(R.id.rb_posto_galena_camp_mod)
                }
                2 -> {
                    rgDistritoCamp.check(R.id.ponte_firme_rb_camp_mod)
                    rgPostosCamp.check(R.id.rb_posto_ponte_firme_camp_mod)
                }
                3 -> {
                    rgDistritoCamp.check(R.id.presidente_olegario_rb_camp_mod)
                    rgPostosCamp.check(R.id.rb_posto_aeroporto_camp_mod)
                }
                4 -> {
                    rgDistritoCamp.check(R.id.presidente_olegario_rb_camp_mod)
                    rgPostosCamp.check(R.id.rb_posto_andorinhas_camp_mod)
                }
                5 -> {
                    rgDistritoCamp.check(R.id.presidente_olegario_rb_camp_mod)
                    rgPostosCamp.check(R.id.rb_posto_godinho_camp_mod)
                }
                6 -> {
                    rgDistritoCamp.check(R.id.santiago_de_minas_rb_camp_mod)
                    rgPostosCamp.check(R.id.rb_posto_dercina_maria_camp_mod)
                }
                7 -> {
                    rgDistritoCamp.check(R.id.presidente_olegario_rb_camp_mod)
                    rgPostosCamp.check(R.id.rb_posto_mateus_caixeta_camp_mod)
                }
                8 -> {
                    rgDistritoCamp.check(R.id.presidente_olegario_rb_camp_mod)
                    rgPostosCamp.check(R.id.rb_posto_planalto_camp_mod)
                }
                9 -> {
                    rgDistritoCamp.check(R.id.presidente_olegario_rb_camp_mod)
                    rgPostosCamp.check(R.id.rb_posto_zona_rural_camp_mod)
                }

            }

            dividirData(dataCampanha)
            dividirHorario(horarioCampanha)

            //adiciona os dados do banco nos editTexts
            edit_titulo.setText(nomeCampanha)
            edit_doenca.setText(doencaCampanha)
            edit_data_inicio.setText(data_inicio_salvar)
            edit_data_final.setText(data_final_salvar)
            edit_hor_inicio.setText(hor_inicio_salvar)
            edit_hor_final.setText(hor_final_salvar)
            edit_publico_alvo.setText(publicoCampanha)
            edit_detalhes.setText(detalhesCampanha)

        }
        loadData(id_key)

        buttonSalvar.setOnClickListener {
            salvardados()
        }



    }
    fun salvardados() {
        //edittexts para alteração
        val id_campanha = getKey()

        db = DataHelper(requireContext())

        val postoEscolhido = rgPostosCamp.checkedRadioButtonId
        titulo_camp_salvar = edit_titulo.text.toString()
        doenca_camp_salvar = edit_doenca.text.toString()
        data_inicio_salvar = edit_data_inicio.text.toString()
        data_final_salvar = edit_data_final.text.toString()
        hor_inicio_salvar = edit_hor_inicio.text.toString()
        hor_final_salvar = edit_hor_final.text.toString()
        hor_camp_salvar = "$hor_inicio_salvar às $hor_final_salvar"
        publico_camp_salvar = edit_publico_alvo.text.toString()
        detalhes_camp_salvar = edit_detalhes.text.toString()

        //para concatenar as strings da data da campanha
        if (data_inicio_salvar == data_final_salvar){
            data_camp_salvar = data_inicio_salvar
        } else {
            data_camp_salvar = "$data_inicio_salvar até $data_final_salvar"
        }

        when (postoEscolhido) {
            R.id.rb_posto_galena_camp_mod -> {
                posto_id_camp_salvar = 1
                posto_nome_camp_salvar = "POSTO DE SAÚDE DE GALENA"
                distrito_camp_salvar = "Galena"
                //Toast.makeText(requireContext(), "Posto Galena", Toast.LENGTH_SHORT).show()
            }
            R.id.rb_posto_ponte_firme_camp_mod -> {
                posto_id_camp_salvar = 2
                posto_nome_camp_salvar = "POSTO DE SAÚDE DE PONTE FIRME"
                distrito_camp_salvar = "Ponte Firme"
                //Toast.makeText(requireContext(), "ponte firme", Toast.LENGTH_SHORT).show()
            }
            R.id.rb_posto_aeroporto_camp_mod -> {
                posto_id_camp_salvar = 3
                posto_nome_camp_salvar = "PSF AEROPORTO"
                distrito_camp_salvar = "Presidente Olegário"
                //Toast.makeText(requireContext(), "Posto Aeroporto", Toast.LENGTH_SHORT).show()
            }
            R.id.rb_posto_andorinhas_camp_mod -> {
                posto_id_camp_salvar = 4
                posto_nome_camp_salvar = "UBS ANDORINHAS"
                distrito_camp_salvar = "Presidente Olegário"
                //Toast.makeText(requireContext(), "Posto Andorinhas", Toast.LENGTH_SHORT).show()
            }
            R.id.rb_posto_godinho_camp_mod -> {
                posto_id_camp_salvar = 5
                posto_nome_camp_salvar = "UBS BILÉ GODINHO"
                distrito_camp_salvar = "Presidente Olegário"
                //Toast.makeText(requireContext(), "Posto Godinho", Toast.LENGTH_SHORT).show()
            }
            R.id.rb_posto_dercina_maria_camp_mod -> {
                posto_id_camp_salvar = 6
                posto_nome_camp_salvar = "UBS DERCINA MARIA ANDRÉ"
                distrito_camp_salvar = "Santiago de Minas"
                //Toast.makeText(requireContext(), "Posto Dercina Maria", Toast.LENGTH_SHORT).show()
            }
            R.id.rb_posto_mateus_caixeta_camp_mod -> {
                posto_id_camp_salvar = 7
                posto_nome_camp_salvar = "UBS MATEUS CAIXETA"
                distrito_camp_salvar = "Presidente Olegário"
                //Toast.makeText(requireContext(), "Mateus Caixeta", Toast.LENGTH_SHORT).show()
            }
            R.id.rb_posto_planalto_camp_mod -> {
                posto_id_camp_salvar = 8
                posto_nome_camp_salvar = "UBS PLANALTO"
                distrito_camp_salvar = "Presidente Olegário"
                //Toast.makeText(requireContext(), "Posto Planalto", Toast.LENGTH_SHORT).show()
            }
            R.id.rb_posto_zona_rural_camp_mod -> {
                posto_id_camp_salvar = 9
                posto_nome_camp_salvar = "UBS ZONA RURAL"
                distrito_camp_salvar = "Presidente Olegário"
                //Toast.makeText(requireContext(), "Posto Zona Rural", Toast.LENGTH_SHORT).show()
            }
        }

        if(!edit_titulo.text.isEmpty() && !edit_doenca.text.isEmpty() && !edit_data_inicio.text.isEmpty() &&
            !edit_data_final.text.isEmpty() && !edit_hor_inicio.text.isEmpty() && !edit_hor_final.text.isEmpty() &&
            !edit_publico_alvo.text.isEmpty() && !edit_detalhes.text.isEmpty() &&
            posto_id_camp_salvar != -1){

            if(posto_id_camp_salvar == idPostoCampanha && titulo_camp_salvar == nomeCampanha && doenca_camp_salvar == doencaCampanha && data_camp_salvar == dataCampanha && hor_camp_salvar == horarioCampanha && publico_camp_salvar == publicoCampanha && detalhes_camp_salvar == detalhesCampanha){
                Toast.makeText(context, "Nenhum dado foi alterado", Toast.LENGTH_SHORT).show()

            } else {

                val result = db?.updateCampanha(id_campanha, distrito_camp_salvar, posto_id_camp_salvar, posto_nome_camp_salvar, titulo_camp_salvar, doenca_camp_salvar, data_camp_salvar, horarioCampanha, publico_camp_salvar, detalhes_camp_salvar)

                if(result!!.equals(-1)) {
                    Toast.makeText(context, "Campanha não atualizada.", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Campanha atualizada com sucesso!", Toast.LENGTH_SHORT).show()
                }
            }

        } else {
            Toast.makeText(context, "Por favor preencha todos os dados.", Toast.LENGTH_SHORT).show()
        }

    }

    fun getKey(): Int { //pega o id do local do bundle
        val args = this.arguments
        val inputData = args?.get("Id_campanha")
        return inputData.toString().toInt()
    }

    fun dividirData(data_completa: String){
        val dataArray: Array<String> = data_completa.split("\\s+".toRegex()).toTypedArray()
        if (dataArray.size == 3){
            data_inicio_salvar = dataArray[0]
            data_final_salvar = dataArray[2]
        } else if (dataArray.size < 3){
            data_inicio_salvar = dataArray[0]
            data_final_salvar = dataArray[0]
        }
    }

    fun dividirHorario(hor_completo: String){
        val dataArray: Array<String> = hor_completo.split("\\s+".toRegex()).toTypedArray()
            hor_inicio_salvar = dataArray[0]
            hor_final_salvar = dataArray[2]

    }

}