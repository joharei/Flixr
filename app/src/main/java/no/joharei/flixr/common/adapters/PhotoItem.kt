package no.joharei.flixr.common.adapters

import com.github.awanishraj.aspectratiorecycler.DimInterface

abstract class PhotoItem : DimInterface {
    abstract val thumbnailWidth: Int
    abstract val thumbnailHeight: Int

    var uIWidth: Int = 0
        get() = if (field == 0) thumbnailWidth else field
    var uIHeight: Int = 0
        get() = if (field == 0) thumbnailHeight else field

    abstract fun photoUrl(width: Int, height: Int): String

    override fun getWidth() = uIWidth
    override fun setWidth(width: Int) {
        uIWidth = width
    }

    override fun getHeight() = uIHeight
    override fun setHeight(height: Int) {
        uIHeight = height
    }
}