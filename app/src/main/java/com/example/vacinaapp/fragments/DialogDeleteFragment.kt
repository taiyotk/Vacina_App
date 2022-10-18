package com.example.vacinaapp.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.vacinaapp.DataHelper
import com.example.vacinaapp.R

class DialogDeleteFragment : DialogFragment() {

    private lateinit var item: String
    private var  db: SQLiteDatabase? = null
    private var _id: Int? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        item = arguments?.getString(KEY) ?: throw IllegalStateException("No args provided")
        item = arguments?.getString(KEY).toString()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dialog_delete, container, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _id = arguments?.getInt("id")


        var dataHelper = DataHelper(requireContext())
        var db = dataHelper.writableDatabase
        /*
        val b = AlertDialog.Builder(context)
            .setTitle("Dialog Title")
            .setMessage("Deseja Realmente apagar a campanha?")
            .setPositiveButton("Sim") { dialog, _ -> DialogInterface.OnClickListener(){

            } }*/


        return super.onCreateDialog(savedInstanceState)
    }

    companion object {
        private const val KEY = "id_campanha"

        fun newInstance(item: String): DialogDeleteFragment =
            DialogDeleteFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY, item)
                }
            }
    }
}