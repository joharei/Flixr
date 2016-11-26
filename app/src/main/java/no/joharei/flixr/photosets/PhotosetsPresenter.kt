package no.joharei.flixr.photosets

import no.joharei.flixr.MainApplication
import no.joharei.flixr.tools.applyDefaultSchedulers
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

class PhotosetsPresenter : AnkoLogger {
    @Inject
    internal lateinit var mainApi: PhotosetsApi
    private lateinit var view: PhotosetsView
    private val compositeSubscription = CompositeSubscription()

    internal fun attachView(view: PhotosetsView) {
        this.view = view
        MainApplication.component.inject(this)
    }

    internal fun fetchPhotosets(userId: String?) {
        view.showProgress()
        mainApi.getPhotosets(userId)
                .applyDefaultSchedulers()
                .subscribe(
                        { photosets ->
                            view.hideProgress()
                            view.showPhotosets(photosets.photosets)
                        },
                        { throwable ->
                            error("Failed fetching photosets", throwable)
                            // TODO
                        })
    }

    internal fun stop() {
        compositeSubscription.unsubscribe()
    }
}
