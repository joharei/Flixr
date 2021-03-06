package no.joharei.flixr.api

import com.squareup.moshi.Moshi
import io.reactivex.Observable
import no.joharei.flixr.api.models.Photos
import no.joharei.flixr.api.models.Photosets
import no.joharei.flixr.login.models.User
import no.joharei.flixr.mainpage.models.Contacts
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class AndroidFlickrApi(okHttpClient: OkHttpClient, url: String, moshi: Moshi) : FlickrApiContainer {

    private val api: FlickrApiDefinition = createApiClient(okHttpClient, url, moshi)

    private fun createApiClient(
        okHttpClient: OkHttpClient,
        url: String,
        moshi: Moshi
    ): FlickrApiDefinition {
        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
        return retrofit.create(FlickrApiDefinition::class.java)
    }

    override fun getUserDetails(): Observable<User> {
        return api.getUserDetails().map { it.user }
    }

    override fun getPhotosets(userId: String?): Observable<Photosets> {
        return api.getPhotosets(userId).map { it.photosets }
    }

    override fun getPhotos(photosetId: Long, userId: String?): Observable<Photos> {
        return api.getPhotos(photosetId, userId).map { it.photoset }
    }

    override fun getContacts(): Observable<Contacts> {
        return api.getContacts().map { it.contacts }
    }
}
