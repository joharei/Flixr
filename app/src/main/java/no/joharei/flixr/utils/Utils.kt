package no.joharei.flixr.utils

import android.content.Context
import android.graphics.Point
import android.view.WindowManager

fun getDisplaySize(context: Context): Point {
    val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val display = wm.defaultDisplay
    val size = Point()
    display.getSize(size)
    return size
}

fun getUrlOfSmallestPhotoToFillSize(fillWidth: Int, fillHeight: Int, photoWidths: List<Int>, photoHeights: List<Int>, photoUrls: List<String>): String {
    val sizes = photoWidths zip photoHeights
    val index = sizes.indexOfFirst { (it.first >= fillWidth || it.second >= fillHeight) }
    if (index >= 0) {
        return photoUrls[index]
    } else {
        return photoUrls.last()
    }
}