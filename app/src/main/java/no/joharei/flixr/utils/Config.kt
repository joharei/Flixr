package no.joharei.flixr.utils

import android.content.Context
import android.os.StatFs
import com.squareup.picasso.LruCache
import com.squareup.picasso.Picasso
import java.io.File

fun setUpPicasso(context: Context) {
    val PICASSO_CACHE = "picasso-cache"
    val MIN_DISK_CACHE_SIZE: Long = 5 * 1024 * 1024 // 5MB
    val MAX_DISK_CACHE_SIZE: Long = 200 * 1024 * 1024 // 200MB

    val cache = File(context.cacheDir, PICASSO_CACHE)
    if (!cache.exists()) {

        cache.mkdirs()
    }

    var size = MIN_DISK_CACHE_SIZE

    try {
        val statFs = StatFs(cache.absolutePath)
        val available = statFs.blockCountLong * statFs.blockSizeLong
        // Target 10% of the total space.
        size = available / 10
    } catch (ignored: IllegalArgumentException) {
    }

    // Bound inside min/max size for disk cache.
    val finalSize = Math.max(Math.min(size, MAX_DISK_CACHE_SIZE), MIN_DISK_CACHE_SIZE)
    Picasso.setSingletonInstance(Picasso.Builder(context).memoryCache(LruCache(finalSize.toInt())).build())
}