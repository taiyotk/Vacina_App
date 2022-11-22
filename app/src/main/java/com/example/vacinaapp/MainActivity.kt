package com.example.vacinaapp

import android.app.AlertDialog
import android.content.SharedPreferences
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.example.vacinaapp.fragments.*
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity: AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    var registerState: Int = 0 //0 para não logado e 1 para logado
    private lateinit var drawer: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle

    private val inicioFragment = InicioFragment()
    private val locaisFragment = LocaisFragment()
    private val configFragment = ConfigFragment()
    private val pesquisarFragment = PesquisarFragment()
    private val usuarioFragment = UsuarioFragment()
    private val modificarCampanhaFragment = ModificarCampanhaFragment()
    private val adicionarVacinaFragment = AdicionarVacinaFragment()
    private val loginFragment = LoginFragment()
    private lateinit var navigationView: NavigationView
    private lateinit var navMenu: Menu
    private lateinit var menuItemEntrar: MenuItem
    private lateinit var menuItemSair: MenuItem
    private lateinit var menuItemMeusDados: MenuItem
    private lateinit var menuAreaVacinas: MenuItem
    private var loginKey = "com.example.vacinaapp.loginState"

    private lateinit var databaseHelper:  DataHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragment(inicioFragment)

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar_main)

        drawer = findViewById(R.id.drawer_layout)

        toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open,
            R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        navigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        navMenu = navigationView.menu
        menuItemEntrar = navMenu.findItem(R.id.entrar)
        menuItemSair = navMenu.findItem(R.id.sair)
        menuItemMeusDados = navMenu.findItem(R.id.meusdados)
        menuAreaVacinas = navMenu.findItem(R.id.vacinacao_area)

        //inicializacao do databasehelper
        databaseHelper = DataHelper(this)

        registerState = readSharedPrefLogin()
        changeVisibility(registerState)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.ic_home -> replaceFragment(inicioFragment)
            R.id.ic_local -> replaceFragment(locaisFragment)
            R.id.pesquisar -> replaceFragment(pesquisarFragment)
            R.id.ic_config -> replaceFragment(configFragment)
            R.id.meusdados -> replaceFragment(usuarioFragment)
            R.id.modificar_campanha -> replaceFragment(modificarCampanhaFragment)
            R.id.adicionar_vacina -> replaceFragment(adicionarVacinaFragment)
            R.id.entrar -> replaceFragment(loginFragment)
            R.id.sair -> sair()

        }
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    private fun replaceFragment(fragment: Fragment) {
        if(fragment != null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.commit()
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        toggle.syncState()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        exit()

    }

    private fun exit(){
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)

        builder.setTitle("Sair")
        builder.setMessage("Deseja realmente sair do app?")
        builder.setPositiveButton("Sim") { dialogInterface, i ->
            finish()
        }
        builder.setNegativeButton("Cancelar") { dialogInterface, i ->
            dialogInterface.cancel()
        }
        builder.show()
    }
    /*fun showAlertDialog(view: View) {
        MaterialAlertDialogBuilder(this)
            .setTitle("Restaurar Configurações")
            .setMessage("Deseja realmente restaurar as configurações para o original?")
            .setPositiveButton("Sim"
            ) {
                    dialog, _ -> showSnackbar("Sim foi Marcado")
            }
            .setNegativeButton("Cancelar"
            ) {
                    dialog, _ -> dialog.dismiss()
            }
            .show()
    }*/

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (ev?.action == MotionEvent.ACTION_UP){
            val v: View? = currentFocus
            if(v is EditText){
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if(!outRect.contains(ev.rawX.toInt(), ev.rawY.toInt())){
                    v.clearFocus()
                    val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    fun changeVisibility(state: Int) {

        if(state == 1){
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

    private fun readSharedPrefLogin(): Int{

        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val value = prefs.getInt(loginKey, 0)
        Log.d("sharedPrefLogin", "valor${prefs.getInt(loginKey, 0)}")

        return value

    }

    fun sair(){

        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Encerrar sessão")
        builder.setMessage("Deseja realmente encerrar a sessão?")
        builder.setPositiveButton("Sim") { dialogInterface, i ->
            val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
            val editor = prefs.edit()
            editor.putInt(loginKey, 0)
            editor.apply()
            val intent = intent
            finish()
            startActivity(intent)
        }
        builder.setNegativeButton("Cancelar") { dialogInterface, i ->
            dialogInterface.cancel()
        }
        builder.show()

    }

}


