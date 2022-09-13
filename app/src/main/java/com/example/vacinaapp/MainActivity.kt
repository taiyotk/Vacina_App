package com.example.vacinaapp

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuItem
import android.view.View
import android.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.OnConfigurationChangedProvider
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
    private val modificarVacinaFragment = ModificarVacinaFragment()
    private val campanhasFinalizadasFragment = CampanhasFinalizadasFragment()


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
            R.id.modificar_vacina -> replaceFragment(modificarVacinaFragment)
            R.id.campanhas_finalizadas -> replaceFragment(campanhasFinalizadasFragment)

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
}