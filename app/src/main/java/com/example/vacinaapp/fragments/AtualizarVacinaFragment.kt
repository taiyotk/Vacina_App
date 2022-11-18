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


class AtualizarVacinaFragment : Fragment() {

    private var db: DataHelper? = null
    private var posto_id_vacina_salvar: Int = -1
    private lateinit var posto_nome_vacina_salvar: String
    private lateinit var distrito_vacina_salvar: String
    private lateinit var doenca_vac_salvar: String
    private lateinit var disp_salvar: String
    private lateinit var publico_salvar: String


    // radiogroups
    private lateinit var rgDistritoCamp: RadioGroup
    private lateinit var rgPostosCamp: RadioGroup
    private lateinit var rgDisponiblidade: RadioGroup
    private lateinit var buttonSalvar: Button

    //do banco de dados
    private var idVacina: Int = -1
    private var idPostoVacina: Int = -1
    private lateinit var postoNomeVacina: String
    private lateinit var doencaVacina: String
    private lateinit var disponibilidade: String
    private lateinit var publicoVacina: String

    //tabela_vacina(id_vacina, id_posto, posto_nome, doenca, disponibilidade, publico)

    //edittexts
    private lateinit var edit_doenca: EditText
    private lateinit var edit_detalhes: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_atualizar_vacina, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id_key: Int = getKey() //id da vacina

        edit_doenca = view.findViewById(R.id.label_doenca)
        edit_detalhes = view.findViewById(R.id.edittext_publico)

        rgDistritoCamp = view.findViewById(R.id.radioGroup_distrito_vac_mod)
        rgPostosCamp = view.findViewById(R.id.radioGroup_postos_vac_mod)
        rgDisponiblidade = view.findViewById(R.id.radioGroup_disponibilidade_mod)

        buttonSalvar = view.findViewById(R.id.btn_modif_vacina)

        //distritos
        val galenaDistrito = view.findViewById<RadioButton>(R.id.galena_radioButton_vac_mod)
        val ponteFirmeDistrito =
            view.findViewById<RadioButton>(R.id.ponte_firme_radioButton_vac_mod)
        val presidenteDistrito =
            view.findViewById<RadioButton>(R.id.presidente_olegario_radioButton_vac_mod)
        val santiagoDistrito =
            view.findViewById<RadioButton>(R.id.santiago_de_minas_radioButton_vac_mod)

        //postos
        val postoGalena = view.findViewById<RadioButton>(R.id.rb_posto_saude_galena_vac_mod)
        val postoPonteFirme =
            view.findViewById<RadioButton>(R.id.rb_posto_saude_ponte_firme_vac_mod)
        val postoAeroporto = view.findViewById<RadioButton>(R.id.rb_posto_saude_aeroporto_vac_mod)
        val postoAndorinhas = view.findViewById<RadioButton>(R.id.rb_posto_saude_andorinhas_vac_mod)
        val postoBileGodinho = view.findViewById<RadioButton>(R.id.rb_posto_saude_godinho_vac_mod)
        val postoDercinaMariaAndre =
            view.findViewById<RadioButton>(R.id.rb_posto_saude_dercina_maria_vac_mod)
        val postoMateusCaixeta =
            view.findViewById<RadioButton>(R.id.rb_posto_saude_mateus_caixeta_vac_mod)
        val postoPlanalto = view.findViewById<RadioButton>(R.id.rb_posto_saude_planalto_vac_mod)
        val postoZonaRural = view.findViewById<RadioButton>(R.id.rb_posto_saude_zona_rural_vac_mod)

        fun loadData(idKey: Int) {
            db = DataHelper(requireContext())
            val query: Cursor =
                db!!.rawQuery("SELECT * FROM tabela_vacina WHERE id_vacina = $idKey")

            //para checar se o cursor está lendo todas as colunas
            //val count: Int = query.count
            //val countColumns = query.columnCount
            //Log.d("qt de linhas e colunas", "Linhas=$count, colunas=$countColumns")

            if (query.moveToNext()) {

                idVacina = query.getInt(0)
                idPostoVacina = query.getInt(1)
                postoNomeVacina = query.getString(2)
                doencaVacina = query.getString(3)
                disponibilidade = query.getString(4)
                publicoVacina = query.getString(5)

                Log.d(
                    "dadosVacina",
                    "id_vacina=$idVacina, id_posto=$idPostoVacina, posto_nome=$postoNomeVacina, doenca=$doencaVacina, disponibilidade=$disponibilidade, publico=$publicoVacina"
                )

                //tabela_vacina(id_vacina, id_posto, posto_nome, doenca, disponibilidade, publico)
            }

            rgDistritoCamp.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { _, _ ->
                if (galenaDistrito.isChecked) {
                    postoGalena.visibility = View.VISIBLE
                    postoPonteFirme.visibility = View.GONE
                    postoAeroporto.visibility = View.GONE
                    postoAndorinhas.visibility = View.GONE
                    postoBileGodinho.visibility = View.GONE
                    postoDercinaMariaAndre.visibility = View.GONE
                    postoMateusCaixeta.visibility = View.GONE
                    postoPlanalto.visibility = View.GONE
                    postoZonaRural.visibility = View.GONE
                } else if (ponteFirmeDistrito.isChecked) {
                    postoGalena.visibility = View.GONE
                    postoPonteFirme.visibility = View.VISIBLE
                    postoAeroporto.visibility = View.GONE
                    postoAndorinhas.visibility = View.GONE
                    postoBileGodinho.visibility = View.GONE
                    postoDercinaMariaAndre.visibility = View.GONE
                    postoMateusCaixeta.visibility = View.GONE
                    postoPlanalto.visibility = View.GONE
                    postoZonaRural.visibility = View.GONE
                } else if (presidenteDistrito.isChecked) {
                    postoGalena.visibility = View.GONE
                    postoPonteFirme.visibility = View.GONE
                    postoAeroporto.visibility = View.VISIBLE
                    postoAndorinhas.visibility = View.VISIBLE
                    postoBileGodinho.visibility = View.VISIBLE
                    postoDercinaMariaAndre.visibility = View.GONE
                    postoMateusCaixeta.visibility = View.VISIBLE
                    postoPlanalto.visibility = View.VISIBLE
                    postoZonaRural.visibility = View.VISIBLE
                } else if (santiagoDistrito.isChecked) {
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

            when (idPostoVacina) {
                1 -> {
                    rgDistritoCamp.check(R.id.galena_radioButton_vac_mod)
                    rgPostosCamp.check(R.id.rb_posto_saude_galena_vac_mod)
                }
                2 -> {
                    rgDistritoCamp.check(R.id.ponte_firme_radioButton_vac_mod)
                    rgPostosCamp.check(R.id.rb_posto_saude_ponte_firme_vac_mod)
                }
                3 -> {
                    rgDistritoCamp.check(R.id.presidente_olegario_radioButton_vac_mod)
                    rgPostosCamp.check(R.id.rb_posto_saude_aeroporto_vac_mod)
                }
                4 -> {
                    rgDistritoCamp.check(R.id.presidente_olegario_radioButton_vac_mod)
                    rgPostosCamp.check(R.id.rb_posto_saude_andorinhas_vac_mod)
                }
                5 -> {
                    rgDistritoCamp.check(R.id.presidente_olegario_radioButton_vac_mod)
                    rgPostosCamp.check(R.id.rb_posto_saude_godinho_vac_mod)
                }
                6 -> {
                    rgDistritoCamp.check(R.id.santiago_de_minas_radioButton_vac_mod)
                    rgPostosCamp.check(R.id.rb_posto_saude_dercina_maria_vac_mod)
                }
                7 -> {
                    rgDistritoCamp.check(R.id.presidente_olegario_radioButton_vac_mod)
                    rgPostosCamp.check(R.id.rb_posto_saude_mateus_caixeta_vac_mod)
                }
                8 -> {
                    rgDistritoCamp.check(R.id.presidente_olegario_radioButton_vac_mod)
                    rgPostosCamp.check(R.id.rb_posto_saude_planalto_vac_mod)
                }
                9 -> {
                    rgDistritoCamp.check(R.id.presidente_olegario_radioButton_vac_mod)
                    rgPostosCamp.check(R.id.rb_posto_saude_zona_rural_vac_mod)
                }

            }

            when (disponibilidade) {
                "Sim" -> {
                    rgDisponiblidade.check(R.id.disp_sim_mod)

                }
                "Não" -> {
                    rgDisponiblidade.check(R.id.disp_nao_mod)
                }
            }

            //adiciona os dados do banco nos editTexts
            edit_doenca.setText(doencaVacina)
            edit_detalhes.setText(publicoVacina)

        }
        loadData(id_key)

        buttonSalvar.setOnClickListener {
            salvardados()
        }


    }

    fun salvardados() {
        //edittexts para alteração
        val id_vacina = getKey()

        db = DataHelper(requireContext())

        val postoEscolhido = rgPostosCamp.checkedRadioButtonId
        val disponibilidadeEscolhida = rgDisponiblidade.checkedRadioButtonId
        doenca_vac_salvar = edit_doenca.text.toString()
        publico_salvar = edit_detalhes.text.toString()

        when (postoEscolhido) {
            R.id.rb_posto_saude_galena_vac_mod -> {
                posto_id_vacina_salvar = 1
                posto_nome_vacina_salvar = "POSTO DE SAÚDE DE GALENA"
                distrito_vacina_salvar = "Galena"
                //Toast.makeText(requireContext(), "Posto Galena", Toast.LENGTH_SHORT).show()
            }
            R.id.rb_posto_saude_ponte_firme_vac_mod -> {
                posto_id_vacina_salvar = 2
                posto_nome_vacina_salvar = "POSTO DE SAÚDE DE PONTE FIRME"
                distrito_vacina_salvar = "Ponte Firme"
                //Toast.makeText(requireContext(), "ponte firme", Toast.LENGTH_SHORT).show()
            }
            R.id.rb_posto_saude_aeroporto_vac_mod -> {
                posto_id_vacina_salvar = 3
                posto_nome_vacina_salvar = "PSF AEROPORTO"
                distrito_vacina_salvar = "Presidente Olegário"
                //Toast.makeText(requireContext(), "Posto Aeroporto", Toast.LENGTH_SHORT).show()
            }
            R.id.rb_posto_saude_andorinhas_vac_mod -> {
                posto_id_vacina_salvar = 4
                posto_nome_vacina_salvar = "UBS ANDORINHAS"
                distrito_vacina_salvar = "Presidente Olegário"
                //Toast.makeText(requireContext(), "Posto Andorinhas", Toast.LENGTH_SHORT).show()
            }
            R.id.rb_posto_saude_godinho_vac_mod -> {
                posto_id_vacina_salvar = 5
                posto_nome_vacina_salvar = "UBS BILÉ GODINHO"
                distrito_vacina_salvar = "Presidente Olegário"
                //Toast.makeText(requireContext(), "Posto Godinho", Toast.LENGTH_SHORT).show()
            }
            R.id.rb_posto_saude_dercina_maria_vac_mod -> {
                posto_id_vacina_salvar = 6
                posto_nome_vacina_salvar = "UBS DERCINA MARIA ANDRÉ"
                distrito_vacina_salvar = "Santiago de Minas"
                //Toast.makeText(requireContext(), "Posto Dercina Maria", Toast.LENGTH_SHORT).show()
            }
            R.id.rb_posto_saude_mateus_caixeta_vac_mod -> {
                posto_id_vacina_salvar = 7
                posto_nome_vacina_salvar = "UBS MATEUS CAIXETA"
                distrito_vacina_salvar = "Presidente Olegário"
                //Toast.makeText(requireContext(), "Mateus Caixeta", Toast.LENGTH_SHORT).show()
            }
            R.id.rb_posto_saude_planalto_vac_mod -> {
                posto_id_vacina_salvar = 8
                posto_nome_vacina_salvar = "UBS PLANALTO"
                distrito_vacina_salvar = "Presidente Olegário"
                //Toast.makeText(requireContext(), "Posto Planalto", Toast.LENGTH_SHORT).show()
            }
            R.id.rb_posto_saude_zona_rural_vac_mod -> {
                posto_id_vacina_salvar = 9
                posto_nome_vacina_salvar = "UBS ZONA RURAL"
                distrito_vacina_salvar = "Presidente Olegário"
                //Toast.makeText(requireContext(), "Posto Zona Rural", Toast.LENGTH_SHORT).show()
            }
        }

        when (disponibilidadeEscolhida) {
            R.id.disp_sim_mod -> {

                disp_salvar = "Sim"

            }
            R.id.disp_nao_mod -> {

                disp_salvar = "Não"

            }
        }


        if (!edit_detalhes.text.isEmpty() && !edit_doenca.text.isEmpty() && posto_id_vacina_salvar != -1
        ) {

            if (posto_id_vacina_salvar == idPostoVacina && doenca_vac_salvar == doencaVacina && publico_salvar == publicoVacina && disp_salvar == disponibilidade) {
                Toast.makeText(context, "Nenhum dado foi alterado", Toast.LENGTH_SHORT).show()

            } else {

                //tabela_vacina(id_vacina, id_posto, posto_nome, doenca, disponibilidade, publico)
                val result = db?.updateVacina(

                    id_vacina,
                    posto_id_vacina_salvar,
                    posto_nome_vacina_salvar,
                    doenca_vac_salvar,
                    disp_salvar,
                    publico_salvar,

                    )

                if (result!!.equals(-1)) {
                    Toast.makeText(context, "vacina não atualizada.", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "vacina atualizada com sucesso!", Toast.LENGTH_SHORT)
                        .show()
                }
            }

        } else {
            Toast.makeText(context, "Por favor preencha todos os dados.", Toast.LENGTH_SHORT).show()
        }

    }

    fun getKey(): Int { //pega o id do local do bundle
        val args = this.arguments
        val inputData = args?.get("Id_vacina")
        return inputData.toString().toInt()
    }


}