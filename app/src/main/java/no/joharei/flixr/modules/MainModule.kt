package no.joharei.flixr.modules

import dagger.Module
import dagger.Provides
import no.joharei.flixr.api.AndroidFlickrApi
import no.joharei.flixr.api.FlickrApiContainer
import no.joharei.flixr.api.FlickrInterceptor
import no.joharei.flixr.api.LocalCredentialStore
import no.joharei.flixr.utils.ApiKeys
import no.joharei.flixr.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer
import se.akerfeldt.okhttp.signpost.SigningInterceptor
import javax.inject.Singleton

@Module
class MainModule {

    @Provides
    fun provideApiContainer(okHttpClient: OkHttpClient): FlickrApiContainer {
        return AndroidFlickrApi(okHttpClient, Constants.BASE_URL)
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
