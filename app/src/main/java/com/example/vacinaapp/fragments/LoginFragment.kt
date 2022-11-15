package com.example.vacinaapp.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button

import android.widget.Toast
import androidx.fragment.app.Fragment
import android.widget.TextView
import androidx.fragment.app.commit
import androidx.preference.PreferenceManager
import com.example.vacinaapp.DataHelper
import com.example.vacinaapp.MainActivity
import com.example.vacinaapp.R
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {

    private lateinit var mainActivity: MainActivity
    private lateinit var botaoentrar: Button
    private var loginKey = "com.example.vacinaapp.loginState"
    private var loginState: Int = 0
    private lateinit var navigationView: NavigationView
    private lateinit var navMenu: Menu
    private lateinit var menuItemEntrar: MenuItem
    private lateinit var menuItemSair: MenuItem
    private lateinit var menuItemMeusDados: MenuItem
    private lateinit var menuAreaVacinas: MenuItem
    private lateinit var textviewCadastro: TextView





    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflar = inflater.inflate(R.layout.fragment_login, container, false)

        textviewCadastro = inflar.findViewById(R.id.textViewLogin)
        mainActivity = MainActivity()

        return inflar
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigationView = activity?.findViewById(R.id.nav_view)!!
        navMenu = navigationView.menu
        menuItemEntrar = navMenu.findItem(R.id.entrar)
        menuItemSair = navMenu.findItem(R.id.sair)
        menuItemMeusDados = navMenu.findItem(R.id.meusdados)
        menuAreaVacinas = navMenu.findItem(R.id.vacinacao_area)
        botaoentrar = view.findViewById(R.id.botao_entrar)

        val helper = DataHelper(requireContext())
        val db = helper.readableDatabase


        botaoentrar.setOnClickListener {
            val inicioFragment = InicioFragment()

            val args = listOf(usuario.text.toString(), senha.text.toString()).toTypedArray()
            val rs = db.rawQuery("SELECT * FROM tab_usuario WHERE  nome_usuario = ? AND senha = ?", args)
            if (rs.moveToNext()){
                parentFragmentManager.commit {
                    setCustomAnimations(
                        R.anim.slide_in,
                        R.anim.fade_out
                    )
                    replace(R.id.fragment_container, inicioFragment)
                }

                editLoginKey() //funcao que coloca o estado de login(1 para logado e 0 para não logado)
                loginState = readSharedPref() //le e armazena o valor do login na variavel
                login(loginState) //funcao que muda a visibilidade do menu
            Toast.makeText(context, "Login feito com sucesso", Toast.LENGTH_SHORT).show()
            rs.close()
        }
            else
                Toast.makeText(context, "Login falhou", Toast.LENGTH_SHORT).show()

            parentFragmentManager.commit {
                setCustomAnimations(
                    R.anim.slide_in,
                    R.anim.fade_out
                )
                replace(R.id.fragment_container, inicioFragment)
            }

            editLoginKey() //funcao que coloca o estado de login(1 para logado e 0 para não logado)
            loginState = readSharedPref() //le e armazena o valor do login na variavel
            login(loginState) //funcao que muda a visibilidade do menu
        }

        textviewCadastro.setOnClickListener{
            val cadastroFragment = CadastroFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, cadastroFragment)
                .addToBackStack(null)
                .commit()
        }

    }

    private fun editLoginKey(){
        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val editor = prefs.edit()
        editor.putInt(loginKey, 1)
        editor.apply()
    }

    fun readSharedPref(): Int{
        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val value = prefs.getInt(loginKey, 0)
        Log.d("sharedPrefLogin", "valor${prefs.getInt(loginKey, 0)}")

        return value
    }

    fun login(loginState: Int){
        if(loginState == 1){
            menuItemEntrar.isVisible = false
            menuItemSair.isVisible = true
            menuItemMeusDados.isVisible = true
            menuAreaVacinas.isVisible = true

        } else{
            menuItemEntrar.isVisible = true
            menuItemMeusDados.isVisible = false
            menuAreaVacinas.isVisible = false
            menuItemSair.isVisible = false

        }
    }

    companion object {

    }
}