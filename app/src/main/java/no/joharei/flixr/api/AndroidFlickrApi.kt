package no.joharei.flixr.api

import com.squareup.moshi.Moshi
import io.reactivex.Flowable
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
    ): FlickrApiDefinition = Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(okHttpClient)
        .build()
        .create(FlickrApiDefinition::class.java)

    override fun getUserDetails(): Observable<User> = api.getUserDetails().map { it.user }

    override fun getPhotosets(userId: String?): Flowable<Photosets> =
        api.getPhotosets(userId).map { it.photosets }

    override fun getPhotos(photosetId: Long, userId: String?): Observable<Photos> =
        api.getPhotos(photosetId, userId).map { it.photoset }

    override fun getContacts(): Observable<Contacts> = api.getContacts().map { it.contacts }
}
