package no.joharei.flixr.photos

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v7.widget.GridLayoutManager
import android.view.Gravity
import com.f2prateek.dart.Dart
import com.f2prateek.dart.InjectExtra
import no.joharei.flixr.api.models.Photo
import no.joharei.flixr.decorations.SpacesItemDecoration
import no.joharei.flixr.utils.Utils
import org.jetbrains.anko.frameLayout
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.wrapContent


class PhotosActivity : Activity(), PhotosView {
    @InjectExtra
    @JvmField
    internal var photosetId: Long = 0
    @Nullable
    @InjectExtra
    @JvmField
    internal var userId: String? = null
    private val photoAdapter by lazy {
        PhotoAdapter(this)
    }
    private val photosPresenter: PhotosPresenter = PhotosPresenter()

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Dart.inject(this)

        frameLayout {
            recyclerView {
                layoutManager = GridLayoutManager(this@PhotosActivity, 6)
                addItemDecoration(SpacesItemDecoration(Utils.convertDpToPixel(this@PhotosActivity, 4)))
                adapter = photoAdapter
            }.lparams(wrapContent, matchParent) {
                gravity = Gravity.CENTER_HORIZONTAL
            }
        }

        photosPresenter.attachView(this)

        loadPhotos()
    }

    override fun onDestroy() {
        super.onDestroy()
        photosPresenter.stop()
    }

    private fun loadPhotos() {
        photosPresenter.fetchPhotos(photosetId, userId)
    }

    override fun getContext(): Context {
        return this
    }

    override fun showPhotos(photos: List<Photo>) {
        photoAdapter.swap(photos)
    }
}
