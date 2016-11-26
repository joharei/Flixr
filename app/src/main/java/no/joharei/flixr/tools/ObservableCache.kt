package no.joharei.flixr.tools

import android.util.LruCache
import rx.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ObservableCache
@Inject
internal constructor() {
    private val apiObservables = LruCache<String, Observable<*>>(10)

    fun getCachedObservable(unPreparedObservable: Observable<*>, clazz: Class<*>, cacheObservable: Boolean, useCache: Boolean): Observable<*> {
        return getCachedObservable(unPreparedObservable, clazz, "", cacheObservable, useCache)
    }

    fun getCachedObservable(unPreparedObservable: Observable<*>, clazz: Class<*>, key: String, cacheObservable: Boolean, useCache: Boolean): Observable<*> {
        if (useCache) {
            return apiObservables.get(clazz.toString() + key)
        }

        var preparedObservable = unPreparedObservable

        if (cacheObservable) {
            preparedObservable = preparedObservable.cache()
            apiObservables.put(clazz.toString() + key, preparedObservable)
        }

        return preparedObservable
    }

    fun clearCache() {
        apiObservables.evictAll()
    }
}
