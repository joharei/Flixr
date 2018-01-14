package no.joharei.flixr.glide

import com.bumptech.glide.load.Options
import com.bumptech.glide.load.model.*
import com.bumptech.glide.load.model.stream.BaseGlideUrlLoader
import no.joharei.flixr.api.models.Photo
import java.io.InputStream


/**
 * An implementation of ModelStreamLoader that leverages the StreamOpener class and the
 * ExecutorService backing the Engine to download the image and resize it in memory before saving
 * the resized version directly to the disk cache.
 */
class FlickrModelLoader(
        urlLoader: ModelLoader<GlideUrl, InputStream>,
        modelCache: ModelCache<Photo, GlideUrl>
) : BaseGlideUrlLoader<Photo>(urlLoader, modelCache) {

    override fun handles(model: Photo) = true

    override fun getUrl(model: Photo, width: Int, height: Int, options: Options?): String {
        return model.photoUrl(width, height)
    }

    override fun getAlternateUrls(model: Photo, width: Int, height: Int, options: Options?): List<String> {
        return model.alternativeUrls(width, height)
    }

    /**
     * The default factory for [FlickrModelLoader]s.
     */
    class Factory : ModelLoaderFactory<Photo, InputStream> {

        private val modelCache = ModelCache<Photo, GlideUrl>(500)

        override fun build(multiFactory: MultiModelLoaderFactory) =
                FlickrModelLoader(
                        multiFactory.build(GlideUrl::class.java, InputStream::class.java),
                        modelCache
                )

        override fun teardown() {}

    }

}