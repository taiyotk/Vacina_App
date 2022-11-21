package com.example.vacinaapp.fragments

import android.database.Cursor
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.vacinaapp.DataHelper
import com.example.vacinaapp.R
import java.util.regex.Matcher
import java.util.regex.Pattern

class ModificarSenhaFragment : Fragment() {

    private lateinit var botaoConfirmar: Button

    //editTexts
    private lateinit var editTextSenhaConfirm: EditText
    private lateinit var editTextSenhaNova: EditText
    private lateinit var editTextSenhaNovaConfirm: EditText

    //Guarda os valores escritos
    private lateinit var senhaDigit: String  //verificar se a senha do banco é a mesma
    private lateinit var senhaNovaDigit: String
    private lateinit var senhaNovaDigitConfirm: String
    private lateinit var senhaDatabase: String

    //Padrão da senha
    private lateinit var textViewQtCaract: TextView
    private lateinit var textViewNumero: TextView
    private lateinit var textViewLetra: TextView
    private lateinit var textViewEspCaract: TextView
    private lateinit var avisoSenhaErrada: TextView
    private var length: Int = 0
    private lateinit var string: String
    private var stateCaractSp= 0
    private var stateNum = 0
    private var stateLetra = 0
    private var stateLength = 0

    // Banco de dados
    private var usuarioId: Int = 1   // chave que deve ser tirada do bundle
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
        val view = inflater.inflate(R.layout.fragment_modificar_senha, container, false)

        //EditTexts
        editTextSenhaConfirm = view.findViewById(R.id.senha_atual)
        editTextSenhaNova = view.findViewById(R.id.nova_senha)
        editTextSenhaNovaConfirm = view.findViewById(R.id.confirmar_nova_senha)

        //TextViews do padrão da senha
        textViewQtCaract = view.findViewById(R.id.aviso_senha_qt_caract)
        textViewLetra = view.findViewById(R.id.letras_aviso)
        textViewNumero = view.findViewById(R.id.aviso_senha_numero)
        textViewEspCaract = view.findViewById(R.id.caract_especial_aviso)
        avisoSenhaErrada = view.findViewById(R.id.aviso_erro_senha)

        botaoConfirmar = view.findViewById(R.id.confirmar_button)

        loadData(usuarioId)

        return view
    }

    private fun loadData(id_usuario: Int){
        db = DataHelper(requireContext())

        val query: Cursor =
            db.rawQuery("SELECT * FROM tab_usuario WHERE id_usuario = $id_usuario")

        if(query.moveToNext()){
            senhaDatabase = query.getString(6)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextSenhaNova.addTextChangedListener (object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                length = editTextSenhaNova.text.length
                string = editTextSenhaNova.text.toString()

                stateLength = if(length >= 8){
                    textViewQtCaract.setTextColor(Color.parseColor("#1553F1"))
                    1

                } else {
                    textViewQtCaract.setTextColor(Color.parseColor("#000000"))
                    0
                }

                stateNum = if(string.contains("[0-9]".toRegex())){
                    textViewNumero.setTextColor(Color.parseColor("#1553F1"))
                    1

                } else {
                    textViewNumero.setTextColor(Color.parseColor("#000000"))
                    0

                }

                stateLetra = if(string.contains("[A-Z]".toRegex()) && string.contains("[a-z]".toRegex())){
                    textViewLetra.setTextColor(Color.parseColor("#1553F1"))
                    1

                } else{
                    textViewLetra.setTextColor(Color.parseColor("#000000"))
                    0
                }

                val p: Pattern = Pattern.compile("[^A-Za-z0-9]")
                val m: Matcher = p.matcher(string)
                val check: Boolean = m.find()
                stateCaractSp = if(check){
                    textViewEspCaract.setTextColor(Color.parseColor("#1553F1"))
                    1
                } else {
                    textViewEspCaract.setTextColor(Color.parseColor("#000000"))
                    0
                }

            }

            override fun afterTextChanged(editable: Editable?) {
            }

        })

        editTextSenhaNovaConfirm.addTextChangedListener (object: TextWatcher{

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(editable: Editable?) {
                val senha1 = editTextSenhaNova.text.toString()
                val senha2 = editTextSenhaNovaConfirm.text.toString()

                if(senha1 == senha2 || senha2.isEmpty()){
                    avisoSenhaErrada.visibility = View.INVISIBLE
                } else {
                    avisoSenhaErrada.visibility = View.VISIBLE
                }

            }

        })

        botaoConfirmar.setOnClickListener {

            senhaDigit = editTextSenhaConfirm.text.toString() //checa na database se a senha escrita é a mesma do banco
            senhaNovaDigit = editTextSenhaNova.text.toString() //checa na database se a senha escrita não é a mesma do banco
            senhaNovaDigitConfirm = editTextSenhaNovaConfirm.text.toString() //checa na database se a senha escrita não é a mesma do banco

            if (editTextSenhaConfirm.text.isEmpty() or editTextSenhaNova.text.isEmpty() or editTextSenhaNovaConfirm.text.isEmpty()) {

                Toast.makeText(requireContext(),"Preencha todos os dados!",Toast.LENGTH_SHORT).show()

            } else if (senhaNovaDigit == senhaDatabase && senhaDigit == senhaNovaDigit) {

                Toast.makeText(requireContext(), "A senha atual e a nova são iguais.", Toast.LENGTH_SHORT).show()

            } else if (senhaDigit == senhaDatabase &&  senhaNovaDigit == senhaNovaDigitConfirm && senhaNovaDigit != senhaDatabase && senhaNovaDigitConfirm != senhaDatabase && stateLetra == 1 && stateLength == 1 && stateCaractSp == 1 && stateNum == 1) {

                val res = db.updateUsuarioSenha(usuarioId, senhaNovaDigit)

                if (res == -1) {
                    Toast.makeText(requireContext(), "Erro ao alterar senha.", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Senha alterada com sucesso!", Toast.LENGTH_SHORT).show()

                    val usuarioFragment = UsuarioFragment()
                    parentFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                    parentFragmentManager.beginTransaction().replace(R.id.fragment_container, usuarioFragment).commit()
                }
            } else if (senhaDigit != senhaDatabase) {

                Toast.makeText(requireContext(), "Senha atual incorreta!", Toast.LENGTH_SHORT).show()

            }  else if (stateLetra == 0 && stateLength == 0 && stateCaractSp == 0 && stateNum == 0){

                Toast.makeText(requireContext(), "A senha não segue os padrões especificados!", Toast.LENGTH_SHORT).show()

            }

        }
    }
}