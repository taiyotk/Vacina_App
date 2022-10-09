package com.example.vacinaapp

import android.graphics.Rect
import android.os.Bundle
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
import com.example.vacinaapp.fragments.*
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity: AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawer: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle

    private val inicioFragment = InicioFragment()
    private val locaisFragment = LocaisFragment()
    private val configFragment = ConfigFragment()
    private val pesquisarFragment = PesquisarFragment()
    private val usuarioFragment = UsuarioFragment()
    private val local1Fragment = Local1Fragment()
    private val local2Fragment = Local2Fragment()
    private val modificarCampanhaFragment = ModificarCampanhaFragment()
    private val adicionarVacinaFragment = AdicionarVacinaFragment()
    private val campanhasFinalizadasFragment = CampanhasFinalizadasFragment()
    private val loginFragment = LoginFragment()

    //classe do banco de dados
    private lateinit var databaseHelper:  DataHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragment(inicioFragment)

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar_main)
        supportActionBar?.hide()
        drawer = findViewById(R.id.drawer_layout)

        toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open,
            R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        //inicializacao do databasehelper
        databaseHelper = DataHelper(this)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.ic_home -> replaceFragment(inicioFragment)
            R.id.ic_local -> replaceFragment(locaisFragment)
            R.id.pesquisar -> replaceFragment(pesquisarFragment)
            R.id.ic_config -> replaceFragment(configFragment)
            R.id.meusdados -> replaceFragment(usuarioFragment)
            R.id.local1 -> replaceFragment(local1Fragment)
            R.id.local2 -> replaceFragment(local2Fragment)
            R.id.modificar_campanha -> replaceFragment(modificarCampanhaFragment)
            R.id.adicionar_vacina -> replaceFragment(adicionarVacinaFragment) //id do ic errado modificar_vacin antigo
            R.id.campanhas_finalizadas -> replaceFragment(campanhasFinalizadasFragment)
            R.id.entrar -> replaceFragment(loginFragment)

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

    fun showAlertDialog(view: View) {
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
    }

    private fun showSnackbar(msg: String) {
        Snackbar.make(fragment_container, msg, Snackbar.LENGTH_SHORT).show()
    }

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


}


