package no.joharei.flixr.photosets

import io.reactivex.disposables.CompositeDisposable
import no.joharei.flixr.MainApplication
import no.joharei.flixr.tools.applyDefaultSchedulers
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import javax.inject.Inject

class PhotosetsPresenter : AnkoLogger {
    @Inject
    internal lateinit var mainApi: PhotosetsApi
    private lateinit var view: PhotosetsView
    private val compositeSubscription = CompositeDisposable()

    internal fun attachView(view: PhotosetsView) {
        this.view = view
        MainApplication.component.inject(this)
    }

    internal fun fetchPhotosets(userId: String?) {
        view.showProgress()
        compositeSubscription.add(mainApi.getPhotosets(userId)
                .applyDefaultSchedulers()
                .subscribe(
                        { photosets ->
                            view.hideProgress()
                            view.showPhotosets(photosets.photosets)
                        },
                        { throwable ->
                            error("Failed fetching photosets", throwable)
                            // TODO
                        }))
    }

    internal fun stop() {
        compositeSubscription.clear()
    }
}
