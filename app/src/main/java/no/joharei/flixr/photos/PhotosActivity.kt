package no.joharei.flixr.photos

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v4.widget.TextViewCompat
import android.support.v7.widget.GridLayoutManager
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import com.f2prateek.dart.Dart
import com.f2prateek.dart.InjectExtra
import no.joharei.flixr.R
import no.joharei.flixr.api.models.Photo
import no.joharei.flixr.decorations.SpacesItemDecoration
import no.joharei.flixr.utils.Utils
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView


class PhotosActivity : Activity(), PhotosView {
    @InjectExtra
    @JvmField
    internal var photosetId: Long = 0
    @InjectExtra
    internal lateinit var photosetTitle: String
    @Nullable
    @InjectExtra
    @JvmField
    internal var userId: String? = null
    private val photoAdapter by lazy {
        PhotoAdapter(this)
    }
    private val photosPresenter: PhotosPresenter = PhotosPresenter()
    private lateinit var titleText: TextView

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Dart.inject(this)

        linearLayout {
            orientation = LinearLayout.VERTICAL
            titleText = textView {
                TextViewCompat.setTextAppearance(this, R.style.TextStyleHeadline)
                horizontalPadding = dimen(R.dimen.activity_horizontal_margin)
                verticalPadding = dimen(R.dimen.activity_vertical_margin)
                text = photosetTitle
            }
            recyclerView {
                layoutManager = GridLayoutManager(ctx, 6)
                addItemDecoration(SpacesItemDecoration(Utils.convertDpToPixel(ctx, 4)))
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
        return ctx
    }

    override fun showPhotos(photos: List<Photo>) {
        photoAdapter.swap(photos)
        titleText.requestFocus()
    }
}
