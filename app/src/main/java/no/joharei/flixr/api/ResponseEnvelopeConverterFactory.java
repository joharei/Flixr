package no.joharei.flixr.api;

import android.annotation.SuppressLint;

import org.apache.commons.lang3.reflect.TypeUtils;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Type;
import java.util.Arrays;

import no.joharei.flixr.api.models.Envelope;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;


class ResponseEnvelopeConverterFactory extends Converter.Factory {
    @SuppressLint("NewApi")
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        // TODO: this only works on api 24
        if (Arrays.stream(annotations).noneMatch(annotation -> annotation instanceof WrappedResponse)) {
            return null;
        }
        Type envelopeType = TypeUtils.parameterize(Envelope.class, type);
        return new ResponseEnvelopeConverter<>(retrofit.nextResponseBodyConverter(this, envelopeType, annotations));
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface WrappedResponse {
    }

    private static class ResponseEnvelopeConverter<T> implements Converter<ResponseBody, T> {
        private final Converter<ResponseBody, Envelope<T>> delegate;

        ResponseEnvelopeConverter(Converter<ResponseBody, Envelope<T>> delegate) {
            this.delegate = delegate;
        }

        @Override
        public T convert(ResponseBody value) throws IOException {
            Envelope<T> envelope = delegate.convert(value);
            if (!envelope.getStat().equals("ok")) {
                throw new Envelope.FailureException(envelope.getStat(), envelope.getCode(), envelope.getMessage());
            }
            return envelope.getData();
        }
    }
}
