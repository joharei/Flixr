package no.joharei.flixr.api

import io.reactivex.Observable
import no.joharei.flixr.api.models.Photos
import no.joharei.flixr.api.models.Photosets
import no.joharei.flixr.login.models.User
import no.joharei.flixr.mainpage.models.Contacts
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class AndroidFlickrApi(okHttpClient: OkHttpClient, url: String, gsonConverterFactory: GsonConverterFactory) : FlickrApiContainer {

    private val api: FlickrApiDefinition = createApiClient(okHttpClient, url, gsonConverterFactory)

    private fun createApiClient(okHttpClient: OkHttpClient, url: String, gsonConverterFactory: GsonConverterFactory): FlickrApiDefinition {
        val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(ResponseEnvelopeConverterFactory())
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
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
