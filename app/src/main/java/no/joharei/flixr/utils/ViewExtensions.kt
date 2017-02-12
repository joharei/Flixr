package no.joharei.flixr.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.annotation.AttrRes
import android.util.TypedValue
import android.view.View

val View.selectableItemBackground: Drawable get() {
    return context.getDrawable(context.getResourceIdAttribute(android.R.attr.selectableItemBackground))
}

fun Context.getResourceIdAttribute(@AttrRes attribute: Int): Int {
    val typedValue = TypedValue()
    theme.resolveAttribute(attribute, typedValue, true)
    return typedValue.resourceId
}