package no.joharei.flixr.login

import no.joharei.flixr.api.FlickrApiContainer
import no.joharei.flixr.login.models.User
import no.joharei.flixr.tools.ObservableCache
import rx.Observable
import javax.inject.Inject

class LoginApi
@Inject
constructor(private val flickrApiContainer: FlickrApiContainer, private val observableCache: ObservableCache) {

    internal val userDetails: Observable<User>
        get() = flickrApiContainer.getUserDetails()

    fun clearCache() {
        observableCache.clearCache()
    }
}
