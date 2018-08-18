package no.joharei.flixr.photosets

import io.reactivex.disposables.CompositeDisposable
import no.joharei.flixr.MainApplication
import no.joharei.flixr.tools.applyDefaultSchedulers
import timber.log.Timber
import javax.inject.Inject

class PhotosetsPresenter {
    @Inject
    internal lateinit var photosetsApi: PhotosetsApi
    private lateinit var view: PhotosetsView
    private val compositeSubscription = CompositeDisposable()

    internal fun attachView(view: PhotosetsView) {
        this.view = view
        MainApplication.component.inject(this)
    }

    internal fun fetchPhotosets(userId: String?) {
        view.showProgress()
        compositeSubscription.add(photosetsApi.getPhotosets(userId)
                .applyDefaultSchedulers()
                .subscribe(
                        { photosets ->
                            view.hideProgress()
                            view.showPhotosets(photosets.photosets)
                        },
                        { throwable ->
                            Timber.e(throwable, "Failed fetching photosets")
                            photosetsApi.clearCache()
                            // TODO
                        }))
    }

    internal fun stop() {
        compositeSubscription.clear()
    }
}
