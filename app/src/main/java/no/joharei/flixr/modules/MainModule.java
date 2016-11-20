package no.joharei.flixr.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import no.joharei.flixr.api.AndroidFlickrApi;
import no.joharei.flixr.api.FlickrApiContainer;
import no.joharei.flixr.api.FlickrInterceptor;
import no.joharei.flixr.api.LocalCredentialStore;
import no.joharei.flixr.api.models.AuthToken;
import no.joharei.flixr.utils.ApiKeys;
import no.joharei.flixr.utils.Constants;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer;
import se.akerfeldt.okhttp.signpost.SigningInterceptor;

@Module
public class MainModule {

    @Provides
    FlickrApiContainer provideApiContainer(OkHttpClient okHttpClient) {
        return new AndroidFlickrApi(okHttpClient, Constants.BASE_URL);
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(LocalCredentialStore localCredentialStore) {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();

        clientBuilder.addInterceptor(new FlickrInterceptor());

        OkHttpOAuthConsumer consumer = new OkHttpOAuthConsumer(ApiKeys.CONSUMER_KEY, ApiKeys.CONSUMER_SECRET);
        AuthToken authToken = localCredentialStore.getToken();
        // TODO: refresh somehow?
        consumer.setTokenWithSecret(authToken.getAuthToken(), authToken.getAuthTokenSecret());
        clientBuilder.addInterceptor(new SigningInterceptor(consumer));

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        clientBuilder.addInterceptor(httpLoggingInterceptor);

        return clientBuilder.build();
    }
}
