package no.joharei.flixr.network;


import com.google.api.client.auth.oauth.OAuthHmacSigner;
import com.google.api.client.auth.oauth.OAuthParameters;

import no.joharei.flixr.utils.ApiKeys;
import no.joharei.flixr.utils.Constants;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass, LocalCredentialStore localCredentialStore) {
        httpClient.addInterceptor(new FlickrInterceptor());
        if (localCredentialStore != null) {
            OAuthHmacSigner signer = new OAuthHmacSigner();
            signer.clientSharedSecret = ApiKeys.CONSUMER_SECRET;
            signer.tokenSharedSecret = localCredentialStore.getToken().getAuthTokenSecret();
            OAuthParameters authorizer = new OAuthParameters();
            authorizer.consumerKey = ApiKeys.CONSUMER_KEY;
            authorizer.signer = signer;
            authorizer.token = localCredentialStore.getToken().getAuthToken();
            httpClient.addInterceptor(new OAuthInterceptor(authorizer));
        }

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(logging);

        OkHttpClient client = httpClient.build();
        Retrofit retrofit = builder.client(client).build();
        return retrofit.create(serviceClass);
    }
}
