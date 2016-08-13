package no.joharei.flixr.network;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


public class FlickrInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        HttpUrl originalHttpUrl = original.url();

        HttpUrl url = originalHttpUrl.newBuilder()
                .addQueryParameter("nojsoncallback", "1")
                .addQueryParameter("format", "json")
                .build();

        Request.Builder requestBuilder = original.newBuilder()
                .url(url);

        return chain.proceed(requestBuilder.build());
    }
}
