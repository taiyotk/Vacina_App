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
import androidx.fragment.app.FragmentManager
import com.example.vacinaapp.DataHelper
import com.example.vacinaapp.R
import java.util.regex.Pattern

class ModificarUsuarioFragment : Fragment() {

    private lateinit var editTextNomeComp: EditText
    private lateinit var editTextCpf: EditText
    private lateinit var editTextTel: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var editTextSenhaConfirm: EditText
    private lateinit var textViewAviso: TextView
    private lateinit var botaoConfirmar: Button
    private lateinit var emailNotValidated: String
    private lateinit var nomeFinal: String
    private lateinit var cpfFinal: String
    private lateinit var telFinal: String
    private lateinit var emailFinal: String
    private lateinit var senhaDigit: String  //var que guarda a senha digitada// tem que fazer uma verificacao no banco para ver se a senha no banco é a mesma

    private var usuarioId: Int = 0
    private lateinit var nomeCompDatabase: String
    private lateinit var cpfDatabase: String
    private lateinit var telefoneDatabase: String
    private lateinit var emailDatabase: String
    private lateinit var senhaDatabase: String //var que guarda a senha obtida pelo banco


    private lateinit var db: DataHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //pegar os dados do bundle aqui

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_modificar_usuario, container, false)
        editTextCpf = view.findViewById(R.id.cpf_edittext_editar)
        editTextTel = view.findViewById(R.id.tel_edittext_editar)
        editTextEmail = view.findViewById(R.id.email_edittext_editar)
        editTextNomeComp = view.findViewById(R.id.nome_edittext_editar)
        editTextSenhaConfirm = view.findViewById(R.id.senha_edittext_editar)
        botaoConfirmar = view.findViewById(R.id.confirmar_button)
        textViewAviso = view.findViewById(R.id.textview_aviso_email)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextCpf.addTextChangedListener(object: TextWatcher {
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
                if(textViewAviso.visibility == View.VISIBLE){
                    textViewAviso.visibility = View.INVISIBLE
                    editTextEmail.setBackgroundResource(R.drawable.layout_borda_edittext)
                }
            } else {
                emailNotValidated = editTextEmail.text.toString()
                if(emailNotValidated == ""){
                    textViewAviso.text = getString(R.string.aviso_obrigatorio)
                    textViewAviso.visibility = View.VISIBLE
                    editTextEmail.setBackgroundResource(R.drawable.background_error_edittext)

                } else {
                    textViewAviso.text = getString(R.string.e_mail_invalido)
                    editTextEmail.setBackgroundResource(R.drawable.layout_borda_edittext)

                }

                val validation = isEmailValid(emailNotValidated)
                if (!validation){
                    textViewAviso.visibility = View.VISIBLE
                    editTextEmail.setBackgroundResource(R.drawable.background_error_edittext)
                } else {
                    textViewAviso.visibility = View.INVISIBLE
                    editTextEmail.setBackgroundResource(R.drawable.layout_borda_edittext)
                }
            }

        }

        botaoConfirmar.setOnClickListener{
            senhaDigit = editTextSenhaConfirm.text.toString()//checar na database se a senha escrita é a mesma do banco
            nomeFinal = editTextNomeComp.text.toString() //nome para salvar
            cpfFinal = editTextCpf.text.toString()  //cpf para salvar
            telFinal = editTextTel.text.toString()   //telefone para salvar
            emailFinal = editTextEmail.text.toString()  //emial para salvar

            if(editTextNomeComp.text.isEmpty() or editTextCpf.text.isEmpty() or editTextTel.text.isEmpty() or editTextEmail.text.isEmpty() or editTextSenhaConfirm.text.isEmpty()){
                Toast.makeText(requireContext(), "Favor preencher todos os dados!", Toast.LENGTH_SHORT).show()

            } else if(nomeFinal == nomeCompDatabase && cpfFinal == cpfDatabase && telFinal == telefoneDatabase && emailFinal == emailDatabase){
                Toast.makeText(requireContext(), "Nenhum dado foi alterado.", Toast.LENGTH_SHORT).show()

            } else if (senhaDigit == senhaDatabase){
                val res = db.updateUsuarioDados(usuarioId, nomeFinal, cpfFinal, telFinal, emailFinal)
                if (res == -1){
                    Toast.makeText(requireContext(), "Erro ao atualizar usuário.", Toast.LENGTH_SHORT).show()

                } else {
                    Toast.makeText(requireContext(), "Usuário atualizado com sucesso!.", Toast.LENGTH_SHORT).show()
                    val usuarioFragment = UsuarioFragment()
                    parentFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, usuarioFragment)
                        .commit()

                }


            }

        }

    }

    private fun isEmailValid(email: String): Boolean{
        val pattern: Pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }

    companion object {

    }
}