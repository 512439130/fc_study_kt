package com.fc.study.util

import android.graphics.Bitmap
import android.graphics.Matrix
import androidx.annotation.ColorInt
import com.fc.study.util.config.Encoding
import com.google.zxing.BarcodeFormat
import com.google.zxing.BinaryBitmap
import com.google.zxing.DecodeHintType
import com.google.zxing.EncodeHintType
import com.google.zxing.LuminanceSource
import com.google.zxing.MultiFormatReader
import com.google.zxing.MultiFormatWriter
import com.google.zxing.RGBLuminanceSource
import com.google.zxing.Reader
import com.google.zxing.common.HybridBinarizer
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import java.util.EnumMap
import java.util.EnumSet

fun createQRCode(
    content: String,
    width: Int,
    height: Int,
    charset: String = Encoding.UTF_8,
    errorCorrectionLevel: ErrorCorrectionLevel = ErrorCorrectionLevel.L,
    margin: Int = 4,
    @ColorInt colorBlack: Int,
    @ColorInt colorWhite: Int,
): Bitmap {
    val hints = mapOf(
        Pair(EncodeHintType.CHARACTER_SET, charset),
        Pair(EncodeHintType.ERROR_CORRECTION, errorCorrectionLevel),
        Pair(EncodeHintType.MARGIN, margin)
    )
    val matrix = MultiFormatWriter()
        .encode(
            content,
            BarcodeFormat.QR_CODE,
            width,
            height,
            hints
        )
    val pixels = IntArray(width * height)
    for (y in 0 until height) {
        for (x in 0 until width) {
            if (matrix.get(x, y)) {
                pixels[y * width + x] = colorBlack
            } else {
                pixels[y * width + x] = colorWhite
            }
        }
    }

    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
    bitmap.setPixels(pixels, 0, width, 0, 0, width, height)
    return bitmap
}

fun Bitmap.readQRCode(): String? {
    var contents: String? = null
    val pixels = IntArray(width * height)
    getPixels(pixels, 0, width, 0, 0, width, height)
    val source: LuminanceSource = RGBLuminanceSource(width, height, pixels)
    val binBitmap = BinaryBitmap(HybridBinarizer(source))
    val reader: Reader = MultiFormatReader()
    try {
        val tmpHintsMap = EnumMap<DecodeHintType, Any>(DecodeHintType::class.java)
        tmpHintsMap[DecodeHintType.TRY_HARDER] = true
        tmpHintsMap[DecodeHintType.POSSIBLE_FORMATS] = EnumSet.allOf(BarcodeFormat::class.java)
        tmpHintsMap[DecodeHintType.PURE_BARCODE] = false
        val result = reader.decode(binBitmap, tmpHintsMap)
        contents = result.text
    } catch (e: Exception) {
        //  ignored
    }
    return contents
}

fun createQRCodeWithIcon(
    content: String,
    width: Int,
    height: Int,
    iconBitmap: Bitmap,
    charset: String = Encoding.UTF_8,
    errorCorrectionLevel: ErrorCorrectionLevel = ErrorCorrectionLevel.H,
    margin: Int = 4,
    @ColorInt colorBlack: Int,
    @ColorInt colorWhite: Int,
): Bitmap {
    val hints = mapOf(
        Pair(EncodeHintType.CHARACTER_SET, charset),
        Pair(EncodeHintType.ERROR_CORRECTION, errorCorrectionLevel),
        Pair(EncodeHintType.MARGIN, margin)
    )
    val matrix = MultiFormatWriter()
        .encode(
            content,
            BarcodeFormat.QR_CODE,
            width,
            height,
            hints
        )

    //  logo 配置
    val halfW = matrix.width / 2
    val halfH = matrix.height / 2
    val imgHalfWidth = width / 10
    val m = Matrix()
    val sx = 2 * imgHalfWidth.toFloat() / iconBitmap.width
    val sy = 2 * imgHalfWidth.toFloat() / iconBitmap.height
    m.setScale(sx, sy)
    //  设置缩放信息
    //  将logo图片按 matrix 设置的信息缩放
    val mBitmap = Bitmap.createBitmap(
        iconBitmap, 0, 0,
        iconBitmap.width, iconBitmap.height, m, false
    )

    val pixels = IntArray(width * height)
    for (y in 0 until height) {
        for (x in 0 until width) {
            if (x > halfW - imgHalfWidth && x < halfW + imgHalfWidth && y > halfH - imgHalfWidth && y < halfH + imgHalfWidth
            ) {
                //  该位置用于存放图片信息
                //  记录图片每个像素信息
                pixels[y * width + x] = mBitmap.getPixel(
                    x - halfW + imgHalfWidth, y - halfH + imgHalfWidth
                )
            } else {
                if (matrix.get(x, y)) {
                    pixels[y * width + x] = colorBlack
                } else {
                    pixels[y * width + x] = colorWhite
                }
            }
        }
    }

    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
    bitmap.setPixels(pixels, 0, width, 0, 0, width, height)
    return bitmap
}
