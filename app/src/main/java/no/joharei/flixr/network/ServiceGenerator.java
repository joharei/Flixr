package no.joharei.flixr.network;


import no.joharei.flixr.utils.ApiKeys;
import no.joharei.flixr.utils.Constants;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer;
import se.akerfeldt.okhttp.signpost.SigningInterceptor;

public class ServiceGenerator {

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass, LocalCredentialStore localCredentialStore) {
        httpClient.addInterceptor(new FlickrInterceptor());
        if (localCredentialStore != null) {
            OkHttpOAuthConsumer consumer = new OkHttpOAuthConsumer(ApiKeys.CONSUMER_KEY, ApiKeys.CONSUMER_SECRET);
            AuthToken authToken = localCredentialStore.getToken();
            consumer.setTokenWithSecret(authToken.getAuthToken(), authToken.getAuthTokenSecret());
            httpClient.addInterceptor(new SigningInterceptor(consumer));
        }

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(logging);

        OkHttpClient client = httpClient.build();
        Retrofit retrofit = builder.client(client).build();
        return retrofit.create(serviceClass);
    }
}
