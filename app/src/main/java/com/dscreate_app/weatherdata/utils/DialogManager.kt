package com.dscreate_app.weatherdata.utils

import android.app.AlertDialog
import android.content.Context
import android.widget.EditText
import com.dscreate_app.weatherdata.R

object DialogManager {

    fun locationSettingDialog(context: Context, listener: Listener) {
        val builder = AlertDialog.Builder(context)
        val dialog = builder.create()
        dialog.setTitle(context.getString(R.string.dialog_title))
        dialog.setMessage(context.getString(R.string.dialog_message))
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, context.getString(R.string.ok)) { _,_ ->
            listener.onClick(null)
            dialog.dismiss()
        }
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, context.getString(R.string.cancel)) { _,_ ->
            dialog.dismiss()
        }
        dialog.show()
    }

    fun searchByNameDialog(context: Context, listener: Listener) {
        val builder = AlertDialog.Builder(context)
        val edName = EditText(context)
        builder.setView(edName)
        val dialog = builder.create()
        dialog.setTitle(context.getString(R.string.city_name))
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, context.getString(R.string.ok)) { _,_ ->
            listener.onClick(edName.text.toString())
            dialog.dismiss()
        }
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, context.getString(R.string.cancel)) { _,_ ->
            dialog.dismiss()
        }
        dialog.show()
    }

    interface Listener {
        fun onClick(name: String?)
    }
}