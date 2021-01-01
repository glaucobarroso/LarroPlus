package com.larro.plus.dialogs

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import com.google.android.material.textview.MaterialTextView
import com.larro.plus.R
import com.larro.plus.qrcode.QrCode

class QrCodeDialog {

    fun show(context : Context, qrCodeContent : String, description : String) {
        val factory = LayoutInflater.from(context)
        val dialogView: View = factory.inflate(R.layout.qr_code_layout, null)
        val dialog = AlertDialog.Builder(context).create()
        val qrCode = QrCode()
        val bitmap = qrCode.createQrCode(qrCodeContent)
        val imageView = dialogView.findViewById<ImageView>(R.id.qr_code)
        val textView = dialogView.findViewById<MaterialTextView>(R.id.qr_code_description)
        textView.text = description
        imageView?.setImageBitmap(bitmap)
        dialog.setView(dialogView)
        dialog.show()
    }
}