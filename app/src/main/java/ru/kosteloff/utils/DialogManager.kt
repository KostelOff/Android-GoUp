package ru.kosteloff.utils

import android.app.AlertDialog
import android.content.Context
import ru.kosteloff.R

object DialogManager {
    fun showDialog(context: Context, messageID: Int, listener: Listener) {
        val builder = AlertDialog.Builder(context)
        var dialog: AlertDialog? = null
        builder.setTitle(R.string.attention)
        builder.setMessage(messageID)

        builder.setPositiveButton(R.string.reset) { _, _ ->
            listener.onclick()
            dialog?.dismiss()
        }

        builder.setNegativeButton(R.string.cancel) { _, _ ->
            dialog?.dismiss()
        }
        dialog = builder.create()
        dialog.show()
    }

    interface Listener {
        fun onclick()
    }
}