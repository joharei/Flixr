package no.joharei.flixr.photos

import android.app.Activity
import android.app.SharedElementCallback
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.annotation.Nullable
import com.f2prateek.dart.Dart
import com.f2prateek.dart.InjectExtra
import com.github.awanishraj.aspectratiorecycler.ARAdapterWrapper
import com.github.awanishraj.aspectratiorecycler.ARLayoutManager
import kotlinx.android.synthetic.main.activity_photos.*
import no.joharei.flixr.R
import no.joharei.flixr.api.models.Photo
import no.joharei.flixr.common.adapters.PhotoAdapter
import no.joharei.flixr.common.adapters.PhotoViewHolder
import no.joharei.flixr.common.adapters.PhotoViewHolder.Companion.onPhotoClicked


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
        PhotoAdapter { onPhotoClicked(it) }
    }
    private val photosPresenter: PhotosPresenter = PhotosPresenter()
    private var tmpReenterState: Bundle? = null

    private val callback = object : SharedElementCallback() {
        override fun onMapSharedElements(
            names: MutableList<String>,
            sharedElements: MutableMap<String, View>
        ) {
            if (tmpReenterState != null) {
                val startingPosition = tmpReenterState!!.getInt(EXTRA_STARTING_ALBUM_POSITION)
                val currentPosition = tmpReenterState!!.getInt(EXTRA_CURRENT_ALBUM_POSITION)
                if (startingPosition != currentPosition) {
                    // If startingPosition != currentPosition the user must have swiped to a
                    // different page in the DetailsActivity. We must update the shared element
                    // so that the correct one falls into place.
                    val newTransitionName = photosPresenter.photos[currentPosition].id.toString()
                    val newSharedElement: View? = photos_list.findViewWithTag(newTransitionName)
                    newSharedElement?.let {
                        names.clear()
                        names.add(newTransitionName)
                        sharedElements.clear()
                        sharedElements.put(newTransitionName, it)
                    }
                }

                tmpReenterState = null
            } else {
                // If tmpReenterState is null, then the activity is exiting.
                val navigationBar: View? = findViewById(android.R.id.navigationBarBackground)
                val statusBar: View? = findViewById(android.R.id.statusBarBackground)
                navigationBar?.let {
                    names.add(it.transitionName)
                    sharedElements.put(it.transitionName, it)
                }
                statusBar?.let {
                    names.add(it.transitionName)
                    sharedElements.put(it.transitionName, it)
                }
            }
        }
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Dart.inject(this)

        setContentView(R.layout.activity_photos)

        title_text.text = photosetTitle
        photos_list.adapter = ARAdapterWrapper(photoAdapter)

        setExitSharedElementCallback(callback)

        photosPresenter.attachView(this)

        loadPhotos()
    }

    override fun onResume() {
        super.onResume()
        PhotoViewHolder.isPhotoActivityStarted = false
    }

    override fun onActivityReenter(requestCode: Int, data: Intent?) {
        super.onActivityReenter(requestCode, data)
        tmpReenterState = Bundle(data?.extras)
        val startingPosition = tmpReenterState?.getInt(EXTRA_STARTING_ALBUM_POSITION)
        val currentPosition = tmpReenterState?.getInt(EXTRA_CURRENT_ALBUM_POSITION)
        if (currentPosition != null && startingPosition != currentPosition) {
            photos_list.scrollToPosition(currentPosition)
            photos_list.getChildAt(currentPosition)?.requestFocus()
        }
        postponeEnterTransition()
        photos_list.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                photos_list.viewTreeObserver.removeOnPreDrawListener(this)
                // TODO: figure out why it is necessary to request layout here in order to get a smooth transition.
                photos_list.requestLayout()
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

    override fun getContext(): Context = this

    override fun showPhotos(photos: List<Photo>) {
        photoAdapter.submitList(photos)
        photos_list.layoutManager = ARLayoutManager(this, photoAdapter).apply {
            setThresholds(6.0f, 8.0f)
        }
        title_text.requestFocus()
    }

    companion object {
        const val EXTRA_STARTING_ALBUM_POSITION = "extra_starting_item_position"
        const val EXTRA_CURRENT_ALBUM_POSITION = "extra_current_item_position"
    }
}
