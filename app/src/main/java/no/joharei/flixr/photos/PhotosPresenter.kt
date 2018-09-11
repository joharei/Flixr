package no.joharei.flixr.photos

import io.reactivex.disposables.CompositeDisposable
import no.joharei.flixr.MainApplication
import no.joharei.flixr.network.models.Photo
import no.joharei.flixr.preferences.CommonPreferences
import no.joharei.flixr.tools.applyDefaultSchedulers
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class PhotosPresenter {
    @Inject
    internal lateinit var photosApi: PhotosApi
    internal val photos = ArrayList<Photo>()
    private lateinit var view: PhotosView
    private val compositeSubscription = CompositeDisposable()

    internal fun attachView(view: PhotosView) {
        this.view = view
        MainApplication.component.inject(this)
    }

    internal fun fetchPhotos(photosetId: Long, userId: String?) {
        val user: String? = userId ?: CommonPreferences.getUserNsid(view.getContext())
        val photosSub = photosApi.getPhotos(photosetId, user)
                .applyDefaultSchedulers()
                .map { it.photos }
                .subscribe(
                        { photos ->
                            this.photos.clear()
                            this.photos.addAll(photos)
                            view.showPhotos(photos)
                        },
                        { throwable ->
                            Timber.e(throwable, "Failed fetching photos")
                            photosApi.clearCache()
                            //TODO
                        })
        compositeSubscription.add(photosSub)
    }

    internal fun stop() {
        compositeSubscription.clear()
    }
}
