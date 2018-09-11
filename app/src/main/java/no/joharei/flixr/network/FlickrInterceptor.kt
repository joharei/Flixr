package no.joharei.flixr.network

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException


class FlickrInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalHttpUrl = original.url()

        val url = originalHttpUrl.newBuilder()
                .addQueryParameter("nojsoncallback", "1")
                .addQueryParameter("format", "json")
                .build()

        val requestBuilder = original.newBuilder()
                .url(url)

        return chain.proceed(requestBuilder.build())
    }
}
