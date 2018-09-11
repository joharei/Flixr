package no.joharei.flixr.network

import androidx.lifecycle.LiveData
import com.squareup.moshi.Moshi
import io.reactivex.Observable
import no.joharei.flixr.login.models.User
import no.joharei.flixr.mainpage.models.Contacts
import no.joharei.flixr.network.framework.ApiResponse
import no.joharei.flixr.network.framework.LiveDataCallAdapterFactory
import no.joharei.flixr.network.models.Photos
import no.joharei.flixr.network.models.PhotosetsResponse
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
        .addCallAdapterFactory(LiveDataCallAdapterFactory())
        .client(okHttpClient)
        .build()
        .create(FlickrApiDefinition::class.java)

    override fun getUserDetails(): Observable<User> = api.getUserDetails().map { it.user }

    override fun getPhotosets(userId: String?): LiveData<ApiResponse<PhotosetsResponse>> =
        api.getPhotosets(userId)

    override fun getPhotos(photosetId: Long, userId: String?): Observable<Photos> =
        api.getPhotos(photosetId, userId).map { it.photoset }

    override fun getContacts(): Observable<Contacts> = api.getContacts().map { it.contacts }
}
