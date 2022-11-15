package com.example.vacinaapp.fragments

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.example.vacinaapp.DataHelper
import com.example.vacinaapp.R
import java.util.regex.Matcher
import java.util.regex.Pattern

class Cadastro2Fragment : Fragment() {
    private lateinit var nomeSalvar: String
    private lateinit var cpfSalvar: String
    private lateinit var telSalvar: String
    private lateinit var emailSalvar: String
    private var keyNome = "key_nome"
    private var keyCpf = "key_cpf"
    private var keyTelefone = "key_telefone"
    private var keyEmail = "key_email"
    private lateinit var usuarioEditText: EditText
    private lateinit var senhaEditText: EditText
    private lateinit var confirmSenhaEditText: EditText
    private lateinit var buttonFinalizar: Button
    private lateinit var textViewQtCaract: TextView
    private lateinit var textViewNumero: TextView
    private lateinit var textViewLetra: TextView
    private lateinit var textViewEspCaract: TextView
    private lateinit var avisoSenhaErrada: TextView
    private lateinit var db: DataHelper
    private var length: Int = 0
    private lateinit var string: String
    private var stateCaractSp= 0
    private var stateNum = 0
    private var stateLetra = 0
    private var stateLength = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        nomeSalvar = getKey(keyNome)
        cpfSalvar = getKey(keyCpf)
        telSalvar = getKey(keyTelefone)
        emailSalvar = getKey(keyEmail)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cadastro2, container, false)
        usuarioEditText = view.findViewById(R.id.usuario_edittext)
        senhaEditText = view.findViewById(R.id.senha_edittext)
        confirmSenhaEditText = view.findViewById(R.id.confirm_senha_edittext)
        buttonFinalizar = view.findViewById(R.id.finalizar_button)
        textViewQtCaract = view.findViewById(R.id.aviso_senha_qt_caract)
        textViewLetra = view.findViewById(R.id.letras_aviso)
        textViewNumero = view.findViewById(R.id.aviso_senha_numero)
        textViewEspCaract = view.findViewById(R.id.caract_especial_aviso)
        avisoSenhaErrada = view.findViewById(R.id.aviso_erro_senha)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        senhaEditText.addTextChangedListener (object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                length = senhaEditText.text.length
                string = senhaEditText.text.toString()

                if(length >= 8){
                    textViewQtCaract.setTextColor(Color.parseColor("#1553F1"))
                    stateLength = 1

                } else {
                    textViewQtCaract.setTextColor(Color.parseColor("#000000"))
                    stateLength = 0
                }

                if(string.contains("[0-9]".toRegex())){
                    textViewNumero.setTextColor(Color.parseColor("#1553F1"))
                    stateNum = 1

                } else {
                    textViewNumero.setTextColor(Color.parseColor("#000000"))
                    stateNum = 0

                }

                if(string.contains("[A-Z]".toRegex()) && string.contains("[a-z]".toRegex())){
                    textViewLetra.setTextColor(Color.parseColor("#1553F1"))
                    stateLetra = 1

                } else{
                    textViewLetra.setTextColor(Color.parseColor("#000000"))
                    stateLetra = 0
                }

                val p: Pattern = Pattern.compile("[^A-Za-z0-9]")
                val m: Matcher = p.matcher(string)
                val check: Boolean = m.find()
                if(check){
                    textViewEspCaract.setTextColor(Color.parseColor("#1553F1"))
                    stateCaractSp = 1
                } else {
                    textViewEspCaract.setTextColor(Color.parseColor("#000000"))
                    stateCaractSp = 0
                }

            }

            override fun afterTextChanged(editable: Editable?) {
            }

        })

        confirmSenhaEditText.addTextChangedListener (object: TextWatcher{

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(editable: Editable?) {
                val senha1 = senhaEditText.text.toString()
                val senha2 = confirmSenhaEditText.text.toString()

                if(senha1 == senha2 || senha2.isEmpty()){
                    avisoSenhaErrada.visibility = View.INVISIBLE
                } else {
                    avisoSenhaErrada.visibility = View.VISIBLE
                }

            }

        })

        buttonFinalizar.setOnClickListener{
            db = DataHelper(requireContext())

            if(usuarioEditText.text.isNotEmpty() && senhaEditText.text.isNotEmpty() && confirmSenhaEditText.text.isNotEmpty() && senhaEditText.text.toString() == confirmSenhaEditText.text.toString()){

                if(stateLetra == 1 && stateLength == 1 && stateCaractSp == 1 && stateNum == 1){

                    val nomeUsuario = usuarioEditText.text.toString()
                    val senhaSalvar = senhaEditText.text.toString()

                    val res = db.insertUsuario(nomeSalvar, cpfSalvar, telSalvar, emailSalvar, nomeUsuario, senhaSalvar).toInt()
                    if (res == -1){
                        Toast.makeText(requireContext(), "Erro ao efetuar cadastro.", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(requireContext(), "Cadastro efetuado com sucesso!", Toast.LENGTH_SHORT).show()
                        val fragmentLogin = LoginFragment()
                        parentFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                        parentFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, fragmentLogin)
                            .commit()
                    }

                } else {
                    Toast.makeText(requireContext(), "Erro\nVerificar se os campos estão preenchidos corretamente.", Toast.LENGTH_SHORT).show()
                }

            } else {
                Toast.makeText(requireContext(), "Favor preencher todos os campos", Toast.LENGTH_SHORT).show()
            }
        }


    }

    private fun getKey(key: String): String {
        val args = this.arguments
        val info = args?.get(key)
        return info.toString()
    }

}