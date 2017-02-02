package no.joharei.flixr.photos

import io.reactivex.disposables.CompositeDisposable
import no.joharei.flixr.MainApplication
import no.joharei.flixr.api.models.Photo
import no.joharei.flixr.preferences.CommonPreferences
import no.joharei.flixr.tools.applyDefaultSchedulers
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import java.util.*
import javax.inject.Inject

class PhotosPresenter : AnkoLogger {
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
                        { throwable -> error("Failed fetching photos", throwable) })
        compositeSubscription.add(photosSub)
    }

    internal fun stop() {
        compositeSubscription.clear()
    }
}
