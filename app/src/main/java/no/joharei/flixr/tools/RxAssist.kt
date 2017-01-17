package no.joharei.flixr.tools

import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

fun <T> Observable<T>.applyDefaultSchedulers(): Observable<T> =
        subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

fun <T> Observable<T>.applyIOSchedulers(): Observable<T> =
        subscribeOn(Schedulers.io()).observeOn(Schedulers.io())
