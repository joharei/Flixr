package no.joharei.flixr.photos

import android.app.Activity
import android.app.SharedElementCallback
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v4.widget.TextViewCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewTreeObserver
import android.widget.LinearLayout
import android.widget.TextView
import com.f2prateek.dart.Dart
import com.f2prateek.dart.InjectExtra
import no.joharei.flixr.R
import no.joharei.flixr.api.models.Photo
import no.joharei.flixr.common.adapters.PhotoAdapter
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
    private lateinit var recyclerView: RecyclerView
    private lateinit var titleText: TextView
    private var tmpReenterState: Bundle? = null

    private val callback = object : SharedElementCallback() {
        override fun onMapSharedElements(names: MutableList<String>, sharedElements: MutableMap<String, View>) {
            if (tmpReenterState != null) {
                val startingPosition = tmpReenterState!!.getInt(EXTRA_STARTING_ALBUM_POSITION)
                val currentPosition = tmpReenterState!!.getInt(EXTRA_CURRENT_ALBUM_POSITION)
                if (startingPosition != currentPosition) {
                    // If startingPosition != currentPosition the user must have swiped to a
                    // different page in the DetailsActivity. We must update the shared element
                    // so that the correct one falls into place.
                    val newTransitionName = photosPresenter.photos[currentPosition].id.toString()
                    val newSharedElement = recyclerView.findViewWithTag(newTransitionName)
                    if (newSharedElement != null) {
                        names.clear()
                        names.add(newTransitionName)
                        sharedElements.clear()
                        sharedElements.put(newTransitionName, newSharedElement)
                    }
                }

                tmpReenterState = null
            } else {
                // If tmpReenterState is null, then the activity is exiting.
                val navigationBar = findViewById(android.R.id.navigationBarBackground)
                val statusBar = findViewById(android.R.id.statusBarBackground)
                if (navigationBar != null) {
                    names.add(navigationBar.transitionName)
                    sharedElements.put(navigationBar.transitionName, navigationBar)
                }
                if (statusBar != null) {
                    names.add(statusBar.transitionName)
                    sharedElements.put(statusBar.transitionName, statusBar)
                }
            }
        }
    }

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
            recyclerView = recyclerView {
                layoutManager = GridLayoutManager(ctx, 6)
                addItemDecoration(SpacesItemDecoration(Utils.convertDpToPixel(ctx, 4)))
                adapter = photoAdapter
            }.lparams(wrapContent, matchParent) {
                gravity = Gravity.CENTER_HORIZONTAL
            }
        }
        setExitSharedElementCallback(callback)

        photosPresenter.attachView(this)

        loadPhotos()
    }

    override fun onResume() {
        super.onResume()
        photoAdapter.isPhotoActivityStarted = false
    }

    override fun onActivityReenter(requestCode: Int, data: Intent?) {
        super.onActivityReenter(requestCode, data)
        tmpReenterState = Bundle(data?.extras)
        val startingPosition = tmpReenterState?.getInt(EXTRA_STARTING_ALBUM_POSITION)
        val currentPosition = tmpReenterState?.getInt(EXTRA_CURRENT_ALBUM_POSITION)
        if (currentPosition != null && startingPosition != currentPosition) {
            recyclerView.scrollToPosition(currentPosition)
            recyclerView.getChildAt(currentPosition).requestFocus()
        }
        postponeEnterTransition()
        recyclerView.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                recyclerView.viewTreeObserver.removeOnPreDrawListener(this)
                // TODO: figure out why it is necessary to request layout here in order to get a smooth transition.
                recyclerView.requestLayout()
                startPostponedEnterTransition()
                return true
            }
        })
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

    companion object {
        const val EXTRA_STARTING_ALBUM_POSITION = "extra_starting_item_position"
        const val EXTRA_CURRENT_ALBUM_POSITION = "extra_current_item_position"
    }
}
