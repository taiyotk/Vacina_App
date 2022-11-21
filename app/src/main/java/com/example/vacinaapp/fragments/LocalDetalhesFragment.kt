package com.example.vacinaapp.fragments

import android.content.SharedPreferences
import android.database.Cursor
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vacinaapp.DataHelper
import com.example.vacinaapp.R
import com.example.vacinaapp.dataClass.CampanhasDataClass
import com.example.vacinaapp.recyclerViewAdapters.VacinasAdapter
import com.example.vacinaapp.dataClass.VacinasDataClass
import com.example.vacinaapp.recyclerViewAdapters.CampanhasAdapter



class LocalDetalhesFragment : Fragment() {

    private var db: DataHelper? = null
    private lateinit var recyclerViewVacina: RecyclerView
    private lateinit var vacinasArraylist: ArrayList<VacinasDataClass>
    private lateinit var recyclerViewCampanha: RecyclerView
    private lateinit var campanhasArraylist: ArrayList<CampanhasDataClass>
    private lateinit var cabecEditVacina: TextView
    private var loginKey = "com.example.vacinaapp.loginState"
    private var loginState = 0

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
        val view = inflater.inflate(R.layout.fragment_local_detalhes, container, false)
        cabecEditVacina = view.findViewById(R.id.editar)

        loginState = readSharedPref()
        changeVisibilityVac(loginState)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
        dataInitializeVacinas()
        dataInitializeCampanhas()

        val vacinaslayoutManager = LinearLayoutManager(context)
        recyclerViewVacina = view.findViewById(R.id.vacinas_recyclerview)
        recyclerViewVacina.layoutManager = vacinaslayoutManager
        recyclerViewVacina.setHasFixedSize(true)
        recyclerViewVacina.isNestedScrollingEnabled = false

        recyclerViewVacina.adapter = VacinasAdapter(vacinasArraylist) {
            vacinasArraylist[it]
        }

        val campanhaslayoutManager = LinearLayoutManager(context)
        recyclerViewCampanha = view.findViewById(R.id.campanhas_recyclerview)
        recyclerViewCampanha.layoutManager = campanhaslayoutManager
        recyclerViewCampanha.setHasFixedSize(true)
        recyclerViewCampanha.isNestedScrollingEnabled = false
        recyclerViewCampanha.adapter = CampanhasAdapter(campanhasArraylist) {
            campanhasArraylist[it]
        }
        recyclerViewCampanha.adapter = CampanhasAdapter(campanhasArraylist){
            listOnClick(it)
        }

    }

    private fun getKey(): Int { //pega o id do local do bundle
        val args = this.arguments
        val inputData = args?.get("key") // variavel do id_local
        return inputData.toString().toInt()
    }

    private fun dataInitializeVacinas() {
        val idposto = getKey()

        db = DataHelper(requireContext())

        //checa se o fetch deu certo
        val vacinasCursor: Cursor = db!!.rawQuery(
            "SELECT id_vacina, id_posto, posto_nome, doenca, disponibilidade, publico" +
                    " FROM tabela_vacina WHERE id_posto = " + idposto + " ORDER BY doenca ASC"
        )

        val vacinasSize: Int = vacinasCursor.count
        Log.d("listVacinas()", "vacinasSize=" + vacinasSize)

        // Add a list of vacinas
        vacinasArraylist = ArrayList<VacinasDataClass>()
        while (vacinasCursor.moveToNext()) {
            val vacinaId = vacinasCursor.getInt(0)
            val postoId = vacinasCursor.getInt(1)
            val postoNome = vacinasCursor.getString(2)
            val doenca = vacinasCursor.getString(3)
            val disponibilidade = vacinasCursor.getString(4)
            val publico = vacinasCursor.getString(5)


            Log.d(
                "listCategories()",
                "id_local=" + postoId + " doenca=" + doenca + " disponibilidade=" +
                        disponibilidade + "publico=" + publico
            )

            vacinasArraylist.add(
                VacinasDataClass(vacinaId, postoId, postoNome, doenca, disponibilidade, publico)
            )

        }

    }

    private fun dataInitializeCampanhas() {
        val idposto = getKey()

        db = DataHelper(requireContext())

        //checa se o fetch deu certo
        val campanhasCursor: Cursor = db!!.rawQuery(
            "SELECT id_campanha, distrito_fk, distrito_campanha, id_posto_campanha, posto_nome_campanha, nome_campanha, doenca_campanha, data, horario, publico_campanha, detalhes" +
                    " FROM tab_campanha WHERE id_posto_campanha = " + idposto
        )

        val vacinasSize: Int = campanhasCursor.count
        Log.d("listVacinas()", "vacinasSize=" + vacinasSize)

        // Add a list of vacinas
        campanhasArraylist = ArrayList()
        while (campanhasCursor.moveToNext()) {
            val idCampanha = campanhasCursor.getInt(0)
            val distritoId = campanhasCursor.getInt(1)
            val distritoCampanha = campanhasCursor.getString(2)
            val idPostoCampanha = campanhasCursor.getInt(3)
            val postoNomeCampanha = campanhasCursor.getString(4)
            val nomeCampanha = campanhasCursor.getString(5)
            val doencaCampanha = campanhasCursor.getString(6)
            val data = campanhasCursor.getString(7)
            val horario = campanhasCursor.getString(8)
            val publicoCampanha = campanhasCursor.getString(9)
            val detalhes = campanhasCursor.getString(10)

            Log.d("teste", "fk_id=$distritoId")
            campanhasArraylist.add(
                CampanhasDataClass(idCampanha,distritoId, distritoCampanha,idPostoCampanha,postoNomeCampanha,nomeCampanha,doencaCampanha,data,horario,publicoCampanha,detalhes)
            )

        }

    }

    private fun listOnClick(itemID: Int){
        val fragmentoDetalhesCampanha = CampanhaDetalhesFragment()
        val bundle = Bundle()
        bundle.putInt("key", itemID)
        fragmentoDetalhesCampanha.arguments = bundle
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragmentoDetalhesCampanha)
            .addToBackStack(null)
            .commit()

    }

    private fun getData() {
        val keyLocal = getKey()

        val db = DataHelper(requireContext())
        val cursorDados: Cursor = db.rawQuery(
            "SELECT * FROM tabela_postos WHERE id_local = " + keyLocal
        )


        if (cursorDados.moveToNext()) {
            val localId = cursorDados.getInt(0)
            val localPosto = cursorDados.getString(1)
            val distritoId = cursorDados.getInt(2)
            val localDistrito = cursorDados.getString(3)
            val localEndereco = cursorDados.getString(4)
            val telefone = cursorDados.getString(5)
            val horSegunda = cursorDados.getString(6)
            val horTerca = cursorDados.getString(7)
            val horQuarta = cursorDados.getString(8)
            val horQuinta = cursorDados.getString(9)
            val horSexta = cursorDados.getString(10)
            val horSabado = cursorDados.getString(11)
            val horDomingo = cursorDados.getString(12)

            Log.d(
                "Dados_posto: ",
                "id_local=" + localId + " posto_saude=" + localPosto + " distrito= " + localDistrito
                        + " endereco=" + localEndereco + " telefone=" + telefone + " segunda=" + horSegunda
            )

            val textviewPosto: TextView = requireView().findViewById(R.id.nome_posto_titulo)
            val textviewEndereco: TextView = requireView().findViewById(R.id.textview_endereco)
            val textviewTelefone: TextView = requireView().findViewById(R.id.textview_telefone)
            val textviewSegunda: TextView = requireView().findViewById(R.id.segunda_label)
            val textviewTerca: TextView = requireView().findViewById(R.id.terca_label)
            val textviewQuarta: TextView = requireView().findViewById(R.id.quarta_label)
            val textviewQuinta: TextView = requireView().findViewById(R.id.quinta_label)
            val textviewSexta: TextView = requireView().findViewById(R.id.sexta_label)
            val textviewSabado: TextView = requireView().findViewById(R.id.sabado_label)
            val textviewDomingo: TextView = requireView().findViewById(R.id.domingo_label)

            textviewPosto.text = localPosto
            textviewEndereco.text = localEndereco
            textviewTelefone.text = telefone
            textviewSegunda.text = horSegunda
            textviewTerca.text = horTerca
            textviewQuarta.text = horQuarta
            textviewQuinta.text = horQuinta
            textviewSexta.text = horSexta
            textviewSabado.text = horSabado
            textviewDomingo.text = horDomingo

        }


    }

    fun readSharedPref(): Int{
        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val value = prefs.getInt(loginKey, 0)
        Log.d("sharedPrefLogin", "valor${prefs.getInt(loginKey, 0)}")

        return value
    }

    fun changeVisibilityVac(state: Int){
        if(state == 1){
            cabecEditVacina.visibility = View.VISIBLE
        } else {
            cabecEditVacina.visibility = View.GONE
        }

    }

}