package com.larro.plus.activities

import android.graphics.Bitmap
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.larro.plus.R
import com.larro.plus.qrcode.QrCode

class QrCodeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.qr_code_layout)
        val qrCode = QrCode()
        val bitmap = qrCode.createQrCode("just a sample")
        val imageView = findViewById<ImageView>(R.id.qr_code)
        imageView?.setImageBitmap(bitmap)
    }
}