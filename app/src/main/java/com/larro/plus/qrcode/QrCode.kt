package com.larro.plus.qrcode

import android.graphics.Bitmap
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter

class QrCode {

    private val WHITE = -0x1 //0xFFFFFFFF
    private val BLACK = -0x1000000 //0xFF000000
    private val QR_CODE_WIDTH = 600
    private val QR_CODE_HEIGHT = 600

    fun createQrCode(content: String) : Bitmap {
        var sample = MultiFormatWriter()
        val matrix = sample.encode(content, BarcodeFormat.QR_CODE, QR_CODE_WIDTH, QR_CODE_HEIGHT)
        val width: Int = matrix.getWidth()
        val height: Int = matrix.getHeight()
        val pixels = IntArray(width * height)
        for (y in 0 until height) {
            val offset = y * width
            for (x in 0 until width) {
                pixels[offset + x] = if (matrix.get(x,y)) BLACK else WHITE
            }
        }
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height)
        return bitmap

    }

}