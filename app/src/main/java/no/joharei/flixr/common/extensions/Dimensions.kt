@file:Suppress("unused", "NOTHING_TO_INLINE")

package no.joharei.flixr.common.extensions

import android.content.Context
import android.view.View
import androidx.annotation.DimenRes
import androidx.fragment.app.Fragment

const val LDPI: Int = android.util.DisplayMetrics.DENSITY_LOW
const val MDPI: Int = android.util.DisplayMetrics.DENSITY_MEDIUM
const val HDPI: Int = android.util.DisplayMetrics.DENSITY_HIGH

//May not be available on older Android versions
const val TVDPI: Int = 213
const val XHDPI: Int = 320
const val XXHDPI: Int = 480
const val XXXHDPI: Int = 640

const val MAXDPI: Int = 0xfffe

//returns dip(dp) dimension value in pixels
fun Context.dip(value: Int): Int = (value * resources.displayMetrics.density).toInt()

fun Context.dip(value: Float): Int = (value * resources.displayMetrics.density).toInt()

//return sp dimension value in pixels
fun Context.sp(value: Int): Int = (value * resources.displayMetrics.scaledDensity).toInt()

fun Context.sp(value: Float): Int = (value * resources.displayMetrics.scaledDensity).toInt()

//converts px value into dip or sp
fun Context.px2dip(px: Int): Float = px.toFloat() / resources.displayMetrics.density

fun Context.px2sp(px: Int): Float = px.toFloat() / resources.displayMetrics.scaledDensity

fun Context.dimen(@DimenRes resource: Int): Int = resources.getDimensionPixelSize(resource)

//the same for the views
inline fun View.dip(value: Int): Int = context.dip(value)

inline fun View.dip(value: Float): Int = context.dip(value)
inline fun View.sp(value: Int): Int = context.sp(value)
inline fun View.sp(value: Float): Int = context.sp(value)
inline fun View.px2dip(px: Int): Float = context.px2dip(px)
inline fun View.px2sp(px: Int): Float = context.px2sp(px)
inline fun View.dimen(@DimenRes resource: Int): Int = context.dimen(resource)

//the same for Fragments
inline fun Fragment.dip(value: Int): Int = requireActivity().dip(value)

inline fun Fragment.dip(value: Float): Int = requireActivity().dip(value)
inline fun Fragment.sp(value: Int): Int = requireActivity().sp(value)
inline fun Fragment.sp(value: Float): Int = requireActivity().sp(value)
inline fun Fragment.px2dip(px: Int): Float = requireActivity().px2dip(px)
inline fun Fragment.px2sp(px: Int): Float = requireActivity().px2sp(px)
inline fun Fragment.dimen(@DimenRes resource: Int): Int = requireActivity().dimen(resource)
