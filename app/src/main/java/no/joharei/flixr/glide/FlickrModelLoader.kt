package no.joharei.flixr.glide

import com.bumptech.glide.load.Options
import com.bumptech.glide.load.model.*
import com.bumptech.glide.load.model.stream.BaseGlideUrlLoader
import no.joharei.flixr.api.models.Photo
import no.joharei.flixr.common.adapters.PhotoItem
import java.io.InputStream


/**
 * An implementation of ModelStreamLoader that leverages the StreamOpener class and the
 * ExecutorService backing the Engine to download the image and resize it in memory before saving
 * the resized version directly to the disk cache.
 */
class FlickrModelLoader(
        urlLoader: ModelLoader<GlideUrl, InputStream>,
        modelCache: ModelCache<PhotoItem, GlideUrl>
) : BaseGlideUrlLoader<PhotoItem>(urlLoader, modelCache) {

    override fun handles(model: PhotoItem) = true

    override fun getUrl(model: PhotoItem, width: Int, height: Int, options: Options?) =
            model.photoUrl(width, height)

    override fun getAlternateUrls(model: PhotoItem, width: Int, height: Int, options: Options?) =
            when (model) {
                is Photo -> model.alternativeUrls(width, height)
                else -> emptyList()
            }

    /**
     * The default factory for [FlickrModelLoader]s.
     */
    class Factory : ModelLoaderFactory<PhotoItem, InputStream> {

        private val modelCache = ModelCache<PhotoItem, GlideUrl>(500)

        override fun build(multiFactory: MultiModelLoaderFactory) =
                FlickrModelLoader(
                        multiFactory.build(GlideUrl::class.java, InputStream::class.java),
                        modelCache
                )

        override fun teardown() {}

    }

}