package no.joharei.flixr.photos

import no.joharei.flixr.MainApplication
import no.joharei.flixr.photos.models.Sizes
import no.joharei.flixr.preferences.CommonPreferences
import no.joharei.flixr.tools.applyDefaultSchedulers
import no.joharei.flixr.tools.applyIOSchedulers
import no.joharei.flixr.utils.Utils
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

class PhotosPresenter : AnkoLogger {
    @Inject
    internal lateinit var photosApi: PhotosApi
    private lateinit var view: PhotosView
    private val compositeSubscription = CompositeSubscription()

    internal fun attachView(view: PhotosView) {
        this.view = view
        MainApplication.component.inject(this)
    }

    internal fun fetchPhotos(photosetId: Long, userId: String?) {
        val user: String? = userId ?: CommonPreferences.getUserNsid(view.getContext())
        val photosSub = photosApi.getPhotos(photosetId, user)
                .applyDefaultSchedulers()
                .flatMapIterable { it.photos }
                .flatMap(
                        { photosApi.getSizes(it.id).applyIOSchedulers() },
                        { photo, sizes ->
                            photo.fullscreenImageUrl = getBestResolution(sizes)
                            photo
                        })
                .toList()
                .applyDefaultSchedulers()
                .subscribe(
                        { photos ->
                            view.showPhotos(photos)
                        },
                        { throwable -> error("Failed fetching photos", throwable) })
        compositeSubscription.add(photosSub)
    }

    private fun getBestResolution(sizes: Sizes): String {
        val displaySize = Utils.getDisplaySize(view.getContext())
        var size = sizes.sizes.find { it.width > displaySize.x || it.height > displaySize.y }
        if (size == null) {
            size = sizes.sizes.last()
        }
        return size.source
    }

    internal fun stop() {
        compositeSubscription.unsubscribe()
    }
}
