package no.joharei.flixr.di

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import no.joharei.flixr.common.ApiKeys
import no.joharei.flixr.common.Constants
import no.joharei.flixr.network.AndroidFlickrApi
import no.joharei.flixr.network.FlickrApiContainer
import no.joharei.flixr.network.FlickrInterceptor
import no.joharei.flixr.network.LocalCredentialStore
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer
import se.akerfeldt.okhttp.signpost.SigningInterceptor
import javax.inject.Singleton


@Module(includes = [JsonObjectMapperModule::class])
class NetworkModule {

    @Provides
    fun provideApiContainer(okHttpClient: OkHttpClient, moshi: Moshi): FlickrApiContainer {
        return AndroidFlickrApi(okHttpClient, Constants.BASE_URL, moshi)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(consumer: OkHttpOAuthConsumer): OkHttpClient {
        val clientBuilder = OkHttpClient.Builder()
        clientBuilder.addInterceptor(FlickrInterceptor())
        clientBuilder.addInterceptor(SigningInterceptor(consumer))

        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        clientBuilder.addInterceptor(httpLoggingInterceptor)

        return clientBuilder.build()
    }

    @Provides
    @Singleton
    fun provideOkHttpOAuthConsumer(localCredentialStore: LocalCredentialStore): OkHttpOAuthConsumer {
        val consumer = OkHttpOAuthConsumer(ApiKeys.CONSUMER_KEY, ApiKeys.CONSUMER_SECRET)
        val authToken = localCredentialStore.token
        consumer.setTokenWithSecret(authToken.authToken, authToken.authTokenSecret)
        return consumer
    }
}
