package com.example.vacinaapp.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.vacinaapp.R
import java.util.regex.Pattern

class CadastroFragment : Fragment() {

    private lateinit var editTextNome: EditText
    private lateinit var editTextCpf: EditText
    private lateinit var editTextTel: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var textViewAlert: TextView
    private lateinit var proximoButton: Button
    private lateinit var emailNotValidated: String




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_cadastro, container, false)
        editTextNome = view.findViewById(R.id.nome_edittext)
        editTextCpf = view.findViewById(R.id.cpf_edittext)
        editTextTel = view.findViewById(R.id.tel_edittext)
        editTextEmail = view.findViewById(R.id.email_edittext)
        proximoButton = view.findViewById(R.id.proximo_button)
        textViewAlert = view.findViewById(R.id.textview_aviso_email)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextCpf.addTextChangedListener(object: TextWatcher{
            var first = 0
            var second = 0

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(editable: Editable?) {
                val lenghtEditText = editTextCpf.text.length
                second = first
                first = lenghtEditText

                if(lenghtEditText == 3 && first > second){
                    editTextCpf.text.append(".")
                }
                if(lenghtEditText == 7 && first > second){
                    editTextCpf.text.append(".")
                }
                if(lenghtEditText == 11 && first > second){
                    editTextCpf.text.append("-")
                }
            }


        })

        editTextTel.addTextChangedListener(object: TextWatcher{
            var first = 0
            var second = 0

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(editable: Editable?) {
                val lenghtEditText = editTextTel.text.length
                second = first
                first = lenghtEditText

                if(lenghtEditText == 2 && first > second){
                    editTextTel.text.insert(0,"(")

                } else if(lenghtEditText == 3 && first > second){
                    editTextTel.text.append(")")

                } else if(lenghtEditText == 9 && first > second){
                    editTextTel.text.append("-")
                }
            }
        })

        editTextEmail.setOnFocusChangeListener { _, hasFocus ->
            if(hasFocus){
                if(textViewAlert.visibility == View.VISIBLE){
                    textViewAlert.visibility = View.INVISIBLE
                    editTextEmail.setBackgroundResource(R.drawable.layout_borda_edittext)
                }
            } else {
                emailNotValidated = editTextEmail.text.toString()
                if(emailNotValidated == ""){
                    textViewAlert.text = getString(R.string.aviso_obrigatorio)
                    textViewAlert.visibility = View.VISIBLE
                    editTextEmail.setBackgroundResource(R.drawable.background_error_edittext)

                } else {
                    textViewAlert.text = getString(R.string.e_mail_invalido)
                    editTextEmail.setBackgroundResource(R.drawable.layout_borda_edittext)

                }

                val validation = isEmailValid(emailNotValidated)
                if (!validation){
                    textViewAlert.visibility = View.VISIBLE
                    editTextEmail.setBackgroundResource(R.drawable.background_error_edittext)
                } else {
                    textViewAlert.visibility = View.INVISIBLE
                    editTextEmail.setBackgroundResource(R.drawable.layout_borda_edittext)
                }
            }

        }


        proximoButton.setOnClickListener{
            if(editTextNome.text.isEmpty() or editTextCpf.text.isEmpty() or editTextTel.text.isEmpty() or editTextEmail.text.isEmpty()){
                Toast.makeText(requireContext(), "Favor preencher todos os dados!", Toast.LENGTH_SHORT).show()

            } else {
                val nome_salvar = editTextNome.text.toString()
                val cpf_salvar = editTextCpf.text.toString()
                val telefone_salvar = editTextTel.text.toString()
                val email_salvar = editTextEmail.text.toString()
                changeFragment(nome_salvar, cpf_salvar, telefone_salvar, email_salvar)

            }

        }


    }

    private fun  changeFragment(nome: String, cpf: String, telefone: String, email: String){
        val fragmentCadastro2 = Cadastro2Fragment()
        val bundle = Bundle()
        bundle.putString("key_nome", nome)
        bundle.putString("key_cpf", cpf)
        bundle.putString("key_telefone", telefone)
        bundle.putString("key_email", email)
        fragmentCadastro2.arguments = bundle
        parentFragmentManager.beginTransaction().replace(R.id.fragment_container, fragmentCadastro2)
            .addToBackStack(null)
            .commit()
    }

    private fun isEmailValid(email: String): Boolean{
        val pattern: Pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }
    companion object {

    }
}