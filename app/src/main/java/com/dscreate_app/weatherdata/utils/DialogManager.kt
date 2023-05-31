package com.dscreate_app.weatherdata.utils

import android.app.AlertDialog
import android.content.Context
import android.widget.EditText

object DialogManager {

    fun locSettingsDialog(context: Context, listener: Listener) {
        val builder = AlertDialog.Builder(context)
        val dialog = builder.create()
        dialog.setTitle("Включить GPS?")
        dialog.setMessage("GPS выкл. Вы хотите вкл отслеживание местоположения?")
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK") {
            _,_ ->
            listener.onClick(null)
            dialog.dismiss()
        }
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Отмена") {
            _,_ ->
            dialog.dismiss()
        }
        dialog.show()
    }

    fun searchByNameDialog(context: Context, listener: Listener) {
        val builder = AlertDialog.Builder(context)
        val edName = EditText(context)
        builder.setView(edName)
        val dialog = builder.create()
        dialog.setTitle("Название города:")
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK") {
                _,_ ->
            listener.onClick(edName.text.toString())
            dialog.dismiss()
        }
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Отмена") {
                _,_ ->
            dialog.dismiss()
        }
        dialog.show()
    }

    interface Listener {
        fun onClick(name: String?)
    }
}