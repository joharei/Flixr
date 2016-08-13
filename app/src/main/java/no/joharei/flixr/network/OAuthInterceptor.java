package no.joharei.flixr.network;

import com.google.api.client.auth.oauth.OAuthParameters;
import com.google.api.client.http.GenericUrl;

import java.io.IOException;
import java.security.GeneralSecurityException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


public class OAuthInterceptor implements Interceptor {

    private final OAuthParameters oAuthParams;

    public OAuthInterceptor(OAuthParameters oAuthParams) {
        this.oAuthParams = oAuthParams;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        GenericUrl requestUrl = new GenericUrl(originalRequest.url().toString());
        oAuthParams.computeNonce();
        oAuthParams.computeTimestamp();
        try {
            oAuthParams.computeSignature("GET", requestUrl);
            Request compressedRequest = originalRequest.newBuilder()
                    .header("Authorization", oAuthParams.getAuthorizationHeader())
                    .header("Accept", "application/json")
                    .method(originalRequest.method(), originalRequest.body())
                    .build();
            return chain.proceed(compressedRequest);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }


        return chain.proceed(originalRequest);
    }
}
