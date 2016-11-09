package no.joharei.flixr.api

import com.google.gson.reflect.TypeToken
import no.joharei.flixr.api.models.Envelope
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type


internal class ResponseEnvelopeConverterFactory : Converter.Factory() {
    override fun responseBodyConverter(type: Type, annotations: Array<Annotation>, retrofit: Retrofit): Converter<ResponseBody, *>? {
        if (annotations.none { annotation -> annotation is WrappedResponse }) {
            return null
        }
        val envelopeType = TypeToken.getParameterized(Envelope::class.java, type).type
        val delegate = retrofit.nextResponseBodyConverter<Envelope<*>>(this, envelopeType, annotations)
        return Converter<ResponseBody, Any> { body ->
            val envelope = delegate.convert(body)
            if (envelope.stat != "ok") {
                throw Envelope.FailureException(envelope.stat, envelope.code, envelope.message)
            }
            envelope.data
        }
    }

    annotation class WrappedResponse
}
