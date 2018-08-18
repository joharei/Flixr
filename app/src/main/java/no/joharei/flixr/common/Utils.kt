package no.joharei.flixr.common

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

fun getUrlOfSmallestPhotoToFillSize(fillWidth: Int, fillHeight: Int, photoWidths: List<Int>, photoHeights: List<Int>, photoUrls: List<String>): String =
        (photoWidths zip photoHeights)
                .indexOfFirst { (width, height) -> width >= fillWidth || height >= fillHeight }
                .let { index -> photoUrls.getOrElse(index) { photoUrls.last() } }

fun getUrlsOfLargerPhotos(fillWidth: Int, fillHeight: Int, photoWidths: List<Int>, photoHeights: List<Int>, photoUrls: List<String>): List<String> =
        (photoWidths zip photoHeights)
                .withIndex()
                .filter { (_, pair) -> pair.first >= fillWidth || pair.second >= fillHeight }
                .drop(1)
                .map { (index, _) -> photoUrls.getOrElse(index) { photoUrls.last() } }