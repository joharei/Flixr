package no.joharei.flixr.tools

import android.util.LruCache
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ObservableCache
@Inject
internal constructor() {
    private val apiObservables = LruCache<String, Observable<*>>(10)

    fun <T> getCachedObservable(unPreparedObservable: Observable<T>, clazz: Class<T>, cacheObservable: Boolean, useCache: Boolean): Observable<T> {
        return getCachedObservable(unPreparedObservable, clazz, "", cacheObservable, useCache)
    }

    fun <T> getCachedObservable(unPreparedObservable: Observable<T>, clazz: Class<T>, key: String, cacheObservable: Boolean, useCache: Boolean): Observable<T> {
        var preparedObservable: Observable<T>? = null

        if (useCache) {
            preparedObservable = apiObservables.get(clazz.toString() + key) as? Observable<T>
        }

        if (preparedObservable != null) {
            return preparedObservable
        }

        preparedObservable = unPreparedObservable

        if (cacheObservable) {
            preparedObservable = preparedObservable.cache()
            apiObservables.put(clazz.toString() + key, preparedObservable)
        }

        return preparedObservable!!
    }

    fun clearCache() {
        apiObservables.evictAll()
    }
}
