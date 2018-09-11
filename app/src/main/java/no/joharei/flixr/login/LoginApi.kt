package no.joharei.flixr.login

import io.reactivex.Observable
import no.joharei.flixr.login.models.User
import no.joharei.flixr.network.FlickrApiContainer
import no.joharei.flixr.tools.ObservableCache
import javax.inject.Inject

class LoginApi
@Inject
constructor(private val flickrApiContainer: FlickrApiContainer, private val observableCache: ObservableCache) {

    fun fetchUserDetails(): Observable<User> {
        return flickrApiContainer.getUserDetails()
    }

    fun clearCache() {
        observableCache.clearCache()
    }
}
