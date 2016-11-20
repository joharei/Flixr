package no.joharei.flixr.api

import no.joharei.flixr.api.models.Photos
import no.joharei.flixr.api.models.Photosets
import no.joharei.flixr.login.models.User
import no.joharei.flixr.mainpage.models.Contacts
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.Observable

class AndroidFlickrApi(okHttpClient: OkHttpClient, url: String) : FlickrApiContainer {

    private val api: FlickrApiDefinition = createApiClient(okHttpClient, url)

    private fun createApiClient(okHttpClient: OkHttpClient, url: String): FlickrApiDefinition {
        val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(ResponseEnvelopeConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build()
        return retrofit.create(FlickrApiDefinition::class.java)
    }

    override fun getUserDetails(): Observable<User> {
        return api.getUserDetails()
    }

    override fun getPhotosets(userId: String?): Observable<Photosets> {
        return api.getPhotosets(userId)
    }

    override fun getPhotos(photosetId: Long, userId: String?): Observable<Photos> {
        return api.getPhotos(photosetId, userId)
    }

    override fun getContacts(): Observable<Contacts> {
        return api.getContacts()
    }
}
