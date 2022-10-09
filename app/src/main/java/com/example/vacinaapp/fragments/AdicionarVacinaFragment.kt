package com.example.vacinaapp.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.example.vacinaapp.DataHelper
import com.example.vacinaapp.R


class AdicionarVacinaFragment : Fragment() {

    //ids radiogroups-> radioGroup_distrito, radioGroup_postos, rg_disponibilidade
    //ids dos radiobuttons: galena_radioButton, ponte_firme_radioButton, presidente_olegario_radioButton, santiago_de_minas_radioButton
    //ids radiobutton: rb_posto_saude_galena, rb_posto_ponte_firme etc
    //disp_sim, disp_nao

    private var posto_id_salvar: Int = -1
    private lateinit var posto_nome_salvar: String
    private lateinit var doenca_salvar: String
    private lateinit var disp_salvar: String
    private lateinit var publico_salvar: String
    private lateinit var db: DataHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_adicionar_vacina, container, false)

        return view
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rgDistrito = view.findViewById<RadioGroup>(R.id.radioGroup_distrito)

        //distritos
        val galenaDistrito = view.findViewById<RadioButton>(R.id.galena_radioButton)
        val ponteFirmeDistrito = view.findViewById<RadioButton>(R.id.ponte_firme_radioButton)
        val presidenteDistrito = view.findViewById<RadioButton>(R.id.presidente_olegario_radioButton)
        val santiagoDistrito = view.findViewById<RadioButton>(R.id.santiago_de_minas_radioButton)

        //postos
        val postoGalena = view.findViewById<RadioButton>(R.id.rb_posto_saude_galena)
        val postoPonteFirme = view.findViewById<RadioButton>(R.id.rb_posto_saude_ponte_firme)
        val postoAeroporto = view.findViewById<RadioButton>(R.id.rb_posto_saude_aeroporto)
        val postoAndorinhas = view.findViewById<RadioButton>(R.id.rb_posto_saude_andorinhas)
        val postoBileGodinho = view.findViewById<RadioButton>(R.id.rb_posto_saude_godinho)
        val postoDercinaMariaAndre = view.findViewById<RadioButton>(R.id.rb_posto_saude_dercina_maria)
        val postoMateusCaixeta = view.findViewById<RadioButton>(R.id.rb_posto_saude_mateus_caixeta)
        val postoPlanalto = view.findViewById<RadioButton>(R.id.rb_posto_saude_planalto)
        val postoZonaRural = view.findViewById<RadioButton>(R.id.rb_posto_saude_zona_rural)



        rgDistrito.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { _, _ ->
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

        //funcao para salvar dados
        salvardados()

        }


    fun salvardados() {
        val editTextDoenca = view?.findViewById<EditText>(R.id.label_doenca)
        val editTextPublico = view?.findViewById<EditText>(R.id.edittext_publico)

        val rgDistrito = view?.findViewById<RadioGroup>(R.id.radioGroup_distrito)
        val rgPostos = view?.findViewById<RadioGroup>(R.id.radioGroup_postos)
        val rgDisponibilidade = view?.findViewById<RadioGroup>(R.id.radioGroup_disponibilidade)
        val buttonSubmit = view?.findViewById<Button>(R.id.adicionar_vacina_btn)

        db = DataHelper(requireContext())

        buttonSubmit?.setOnClickListener {

            val postoEscolhido = rgPostos?.checkedRadioButtonId
            val disponibilidadeEscolha = rgDisponibilidade?.checkedRadioButtonId
            doenca_salvar = editTextDoenca!!.text.toString()
            publico_salvar = editTextPublico!!.text.toString()

            when (postoEscolhido) {
                R.id.rb_posto_saude_galena -> {
                    posto_id_salvar = 1
                    posto_nome_salvar = "POSTO DE SAÚDE DE GALENA"
                    //Toast.makeText(requireContext(), "Posto Galena", Toast.LENGTH_SHORT).show()
                }
                R.id.rb_posto_saude_ponte_firme -> {
                    posto_id_salvar = 2
                    posto_nome_salvar = "POSTO DE SAÚDE DE PONTE FIRME"
                    //Toast.makeText(requireContext(), "ponte firme", Toast.LENGTH_SHORT).show()
                }
                R.id.rb_posto_saude_aeroporto -> {
                    posto_id_salvar = 3
                    posto_nome_salvar = "PSF AEROPORTO"
                    //Toast.makeText(requireContext(), "Posto Aeroporto", Toast.LENGTH_SHORT).show()
                }
                R.id.rb_posto_saude_andorinhas -> {
                    posto_id_salvar = 4
                    posto_nome_salvar = "UBS ANDORINHAS"
                    //Toast.makeText(requireContext(), "Posto Andorinhas", Toast.LENGTH_SHORT).show()
                }
                R.id.rb_posto_saude_godinho -> {
                    posto_id_salvar = 5
                    posto_nome_salvar = "UBS BILÉ GODINHO"
                    //Toast.makeText(requireContext(), "Posto Godinho", Toast.LENGTH_SHORT).show()
                }
                R.id.rb_posto_saude_dercina_maria -> {
                    posto_id_salvar = 6
                    posto_nome_salvar = "UBS DERCINA MARIA ANDRÉ"
                    //Toast.makeText(requireContext(), "Posto Dercina Maria", Toast.LENGTH_SHORT).show()
                }
                R.id.rb_posto_saude_mateus_caixeta -> {
                    posto_id_salvar = 7
                    posto_nome_salvar = "UBS MATEUS CAIXETA"
                    //Toast.makeText(requireContext(), "Mateus Caixeta", Toast.LENGTH_SHORT).show()
                }
                R.id.rb_posto_saude_planalto -> {
                    posto_id_salvar = 8
                    posto_nome_salvar = "UBS PLANALTO"
                    //Toast.makeText(requireContext(), "Posto Planalto", Toast.LENGTH_SHORT).show()
                }
                R.id.rb_posto_saude_zona_rural -> {
                    posto_id_salvar = 9
                    posto_nome_salvar = "UBS ZONA RURAL"
                    //Toast.makeText(requireContext(), "Posto Zona Rural", Toast.LENGTH_SHORT).show()
                }
            }

            when (disponibilidadeEscolha) {
                R.id.disp_sim -> {
                    disp_salvar = "Sim"
                    //Toast.makeText(requireContext(), "Sim foi marcado", Toast.LENGTH_SHORT).show()
                }
                R.id.disp_nao -> {
                    disp_salvar = "Não"
                    //Toast.makeText(requireContext(), "Não foi marcado", Toast.LENGTH_SHORT).show()
                }
            }



            if(!editTextDoenca.text.isEmpty() && !editTextPublico.text.isEmpty() &&
                !posto_id_salvar.equals(-1) && !disp_salvar.isEmpty()) {

                val result = db.insert(posto_id_salvar, posto_nome_salvar,  doenca_salvar, disp_salvar, publico_salvar)
                if(result.equals(-1)){
                    Toast.makeText(context, "Vacina não inserida.", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Vacina adicionada com sucesso!", Toast.LENGTH_SHORT).show()
                    rgDistrito!!.clearCheck()
                    rgPostos!!.clearCheck()
                    rgDisponibilidade!!.clearCheck()
                    editTextDoenca.text.clear()
                    editTextPublico.text.clear()
                }
            }else{
                Toast.makeText(context, "Por favor insira preencha todos os dados necessários!", Toast.LENGTH_SHORT).show()
            }


        }

    }

}