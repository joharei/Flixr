package no.joharei.flixr.photos

import android.app.Activity
import android.content.Context
import android.os.Bundle
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

const val NUM_COLUMNS = 6

class PhotosActivity : Activity(), PhotosView {
    @JvmField
    @InjectExtra
    var photosetId: Long = 0
    @JvmField
    @InjectExtra
    var userId: String? = null
    lateinit private var photoAdapter: PhotoAdapter
    private val photosPresenter: PhotosPresenter = PhotosPresenter()

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Dart.inject(this)

        photoAdapter = PhotoAdapter(this)
        frameLayout {
            recyclerView {
                layoutManager = GridLayoutManager(this@PhotosActivity, NUM_COLUMNS)
                addItemDecoration(SpacesItemDecoration(Utils.convertDpToPixel(this@PhotosActivity, 4)))
                adapter = photoAdapter
            }.lparams(wrapContent, matchParent) {
                gravity = Gravity.CENTER_HORIZONTAL
            }
        }

        photosPresenter.attachView(this)


        loadPhotos()
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
