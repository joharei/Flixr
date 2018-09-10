package no.joharei.flixr.common.extensions

import androidx.lifecycle.LiveData
import androidx.lifecycle.toLiveData
import io.reactivex.Flowable
import org.reactivestreams.Publisher

fun <T> Publisher<T>.toLiveData(onError: (Throwable) -> T): LiveData<T> =
    ((this as? Flowable)?.onErrorReturn { onError(it) } ?: this)
        .toLiveData()