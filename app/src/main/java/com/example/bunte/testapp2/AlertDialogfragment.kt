package com.example.bunte.testapp2

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.os.Bundle

/**
 * Created by bunte on 7/17/2017.
 */
class AlertDialogfragment() : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val context = activity
        val builder = AlertDialog.Builder(context)
        builder.setTitle(getString(R.string.Error_Title)).setMessage(getString(R.string.Error_Msg))
                .setPositiveButton(getString(R.string.Error_Pos_Button),null)
        val dialog = builder.create()
        return dialog
    }

}