package com.example.vacinaapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.vacinaapp.DataHelper
import com.example.vacinaapp.R

class AdicionarCampanhaFragment : Fragment() {

    private lateinit var distrito_camp: String
    private var id_posto_camp: Int = -1
    private lateinit var posto_nome_camp: String
    private lateinit var titulo_campanha: String
    private lateinit var doenca_camp: String
    private lateinit var data_inicio_camp: String
    private lateinit var data_final_camp: String
    private lateinit var data_completa_camp: String
    private lateinit var horario_inicio_camp: String
    private lateinit var horario_final_camp: String
    private lateinit var horario_completo_camp: String
    private lateinit var publico_alvo_camp: String
    private lateinit var detalhes_camp: String

    private lateinit var db: DataHelper

    companion object {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_adicionar_campanha, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rgDistrito = view.findViewById<RadioGroup>(R.id.radioGroup_distrito_camp)

        //distritos
        val galenaDistrito = view.findViewById<RadioButton>(R.id.galena_radioButton_camp)
        val ponteFirmeDistrito = view.findViewById<RadioButton>(R.id.ponte_firme_radioButton_camp)
        val presidenteDistrito = view.findViewById<RadioButton>(R.id.presidente_olegario_radioButton_camp)
        val santiagoDistrito = view.findViewById<RadioButton>(R.id.santiago_de_minas_radioButton_camp)

        //postos
        val postoGalena = view.findViewById<RadioButton>(R.id.rb_posto_saude_galena_camp)
        val postoPonteFirme = view.findViewById<RadioButton>(R.id.rb_posto_saude_ponte_firme_camp)
        val postoAeroporto = view.findViewById<RadioButton>(R.id.rb_posto_saude_aeroporto_camp)
        val postoAndorinhas = view.findViewById<RadioButton>(R.id.rb_posto_saude_andorinhas_camp)
        val postoBileGodinho = view.findViewById<RadioButton>(R.id.rb_posto_saude_godinho_camp)
        val postoDercinaMariaAndre = view.findViewById<RadioButton>(R.id.rb_posto_saude_dercina_maria_camp)
        val postoMateusCaixeta = view.findViewById<RadioButton>(R.id.rb_posto_saude_mateus_caixeta_camp)
        val postoPlanalto = view.findViewById<RadioButton>(R.id.rb_posto_saude_planalto_camp)
        val postoZonaRural = view.findViewById<RadioButton>(R.id.rb_posto_saude_zona_rural_camp)



        rgDistrito.setOnCheckedChangeListener{ _, _ ->
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
        }

        //funcao para salvar dados
        salvarCampanha()

    }

    fun salvarCampanha(){
        val titulo_campanha_label = view?.findViewById<EditText>(R.id.label_campanha)
        val doenca_label = view?.findViewById<EditText>(R.id.label_doenca_camp)
        val data_inicio_label = view?.findViewById<EditText>(R.id.label_data_inicio_campanha)
        val data_fim_label = view?.findViewById<EditText>(R.id.label_data_fim_campanha)
        val hor_inicio_label = view?.findViewById<EditText>(R.id.label_hor_inicio_campanha)
        val hor_fim_label = view?.findViewById<EditText>(R.id.label_hor_final_campanha)
        val publico_label = view?.findViewById<EditText>(R.id.label_publico_alvo_camp)
        val detalhesLabel = view?.findViewById<EditText>(R.id.label_detalhes_camp)

        val rg_postos_camp = view?.findViewById<RadioGroup>(R.id.radioGroup_postos_camp)
        val rg_distrito = view?.findViewById<RadioGroup>(R.id.radioGroup_distrito_camp)
        val btnSalvar = view?.findViewById<Button>(R.id.btn_ad_campanha)

        db = DataHelper(requireContext())

        btnSalvar?.setOnClickListener {
            val postoEscolhido = rg_postos_camp?.checkedRadioButtonId
            titulo_campanha = titulo_campanha_label!!.text.toString()
            doenca_camp = doenca_label!!.text.toString()
            publico_alvo_camp = publico_label!!.text.toString()
            data_inicio_camp = data_inicio_label!!.text.toString()
            data_final_camp = data_fim_label!!.text.toString()
            data_completa_camp = "$data_inicio_camp até $data_final_camp"  //string de data para salvar
            horario_inicio_camp = hor_inicio_label!!.text.toString()
            horario_final_camp = hor_fim_label!!.text.toString()
            horario_completo_camp = "$horario_inicio_camp às $horario_final_camp" //string de horario para salvar
            detalhes_camp = detalhesLabel!!.text.toString()

            when (postoEscolhido) {
                R.id.rb_posto_saude_galena_camp -> {
                    id_posto_camp = 1
                    distrito_camp = "Galena"
                    posto_nome_camp = "POSTO DE SAÚDE DE GALENA"
                    //Toast.makeText(requireContext(), "Posto Galena", Toast.LENGTH_SHORT).show()
                }
                R.id.rb_posto_saude_ponte_firme_camp -> {
                    id_posto_camp = 2
                    distrito_camp = "Ponte Firme"
                    posto_nome_camp = "POSTO DE SAÚDE DE PONTE FIRME"
                    //Toast.makeText(requireContext(), "ponte firme", Toast.LENGTH_SHORT).show()
                }
                R.id.rb_posto_saude_aeroporto_camp -> {
                    id_posto_camp = 3
                    distrito_camp = "Presidente Olegário"
                    posto_nome_camp = "PSF AEROPORTO"
                    //Toast.makeText(requireContext(), "Posto Aeroporto", Toast.LENGTH_SHORT).show()
                }
                R.id.rb_posto_saude_andorinhas_camp -> {
                    id_posto_camp = 4
                    distrito_camp = "Presidente Olegário"
                    posto_nome_camp = "UBS ANDORINHAS"
                    //Toast.makeText(requireContext(), "Posto Andorinhas", Toast.LENGTH_SHORT).show()
                }
                R.id.rb_posto_saude_godinho_camp -> {
                    id_posto_camp = 5
                    distrito_camp = "Presidente Olegário"
                    posto_nome_camp = "UBS BILÉ GODINHO"
                    //Toast.makeText(requireContext(), "Posto Godinho", Toast.LENGTH_SHORT).show()
                }
                R.id.rb_posto_saude_dercina_maria_camp -> {
                    id_posto_camp = 6
                    distrito_camp = "Santiago de Minas"
                    posto_nome_camp = "UBS DERCINA MARIA ANDRÉ"
                    //Toast.makeText(requireContext(), "Posto Dercina Maria", Toast.LENGTH_SHORT).show()
                }
                R.id.rb_posto_saude_mateus_caixeta_camp -> {
                    id_posto_camp = 7
                    distrito_camp = "Presidente Olegário"
                    posto_nome_camp = "UBS MATEUS CAIXETA"
                    //Toast.makeText(requireContext(), "Mateus Caixeta", Toast.LENGTH_SHORT).show()
                }
                R.id.rb_posto_saude_planalto_camp -> {
                    id_posto_camp = 8
                    distrito_camp = "Presidente Olegário"
                    posto_nome_camp = "UBS PLANALTO"
                    //Toast.makeText(requireContext(), "Posto Planalto", Toast.LENGTH_SHORT).show()
                }
                R.id.rb_posto_saude_zona_rural_camp -> {
                    id_posto_camp = 9
                    distrito_camp = "Presidente Olegário"
                    posto_nome_camp = "UBS ZONA RURAL"
                    //Toast.makeText(requireContext(), "Posto Zona Rural", Toast.LENGTH_SHORT).show()
                }
            }

            if(!titulo_campanha_label.text.isEmpty() && !doenca_label.text.isEmpty() && !data_inicio_label.text.isEmpty() &&
                !data_fim_label.text.isEmpty() && !hor_inicio_label.text.isEmpty() && !hor_fim_label.text.isEmpty() &&
                !publico_label.text.isEmpty() && !detalhesLabel.text.isEmpty() &&
                id_posto_camp != -1) {

                val result = db.insertCampanha(distrito_camp, id_posto_camp, posto_nome_camp, titulo_campanha, doenca_camp,
                data_completa_camp, horario_completo_camp, publico_alvo_camp, detalhes_camp)
                if(result.equals(-1)){
                    Toast.makeText(context, "Campanha não inserida.", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Campanha adicionada com sucesso!", Toast.LENGTH_SHORT).show()
                    rg_distrito!!.clearCheck()
                    rg_postos_camp!!.clearCheck()
                    titulo_campanha_label.text.clear()
                    doenca_label.text.clear()
                    data_inicio_label.text.clear()
                    data_fim_label.text.clear()
                    hor_inicio_label.text.clear()
                    hor_fim_label.text.clear()
                    publico_label.text.clear()
                    detalhesLabel.text.clear()
                }
            }else{
                Toast.makeText(context, id_posto_camp.toString(), Toast.LENGTH_LONG).show()

            }

        }


    }

}