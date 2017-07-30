package no.joharei.flixr.photo

import android.app.Activity
import android.app.SharedElementCallback
import android.content.Intent
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.View
import com.f2prateek.dart.Dart
import com.f2prateek.dart.InjectExtra
import no.joharei.flixr.api.models.Photo
import no.joharei.flixr.photo.PhotoViewAdapter.PhotoItemUI
import no.joharei.flixr.photos.PhotosActivity
import org.jetbrains.anko.support.v4.viewPager
import java.util.*

class PhotoViewerActivity : Activity() {

    @InjectExtra
    internal lateinit var photos: ArrayList<Photo>
    @InjectExtra
    @JvmField
    internal var startingPosition = -1
    private var currentPosition = -1
    private lateinit var pager: ViewPager
    private var isReturning = false

    private val callback = object : SharedElementCallback() {
        override fun onMapSharedElements(names: MutableList<String>, sharedElements: MutableMap<String, View>) {
            if (isReturning) {
                val sharedElement: View? = (pager.adapter as PhotoViewAdapter).currentItem?.findViewById(PhotoItemUI.IMAGE_ID)
                if (sharedElement == null) {
                    // If shared element is null, then it has been scrolled off screen and
                    // no longer visible. In this case we cancel the shared element transition by
                    // removing the shared element from the shared elements map.
                    names.clear()
                    sharedElements.clear()
                } else if (startingPosition != currentPosition) {
                    // If the user has swiped to a different ViewPager page, then we need to
                    // remove the old shared element and replace it with the new shared element
                    // that should be transitioned instead.
                    names.clear()
                    names.add(sharedElement.transitionName)
                    sharedElements.clear()
                    sharedElements.put(sharedElement.transitionName, sharedElement)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Dart.inject(this)

        pager = viewPager()

        postponeEnterTransition()
        setEnterSharedElementCallback(callback)

        if (savedInstanceState == null) {
            currentPosition = startingPosition
        } else {
            currentPosition = savedInstanceState.getInt(STATE_CURRENT_PAGE_POSITION)
        }

        val adapter = PhotoViewAdapter(this, photos, startingPosition)
        pager.adapter = adapter
        pager.offscreenPageLimit = 5
        if (startingPosition >= 0) {
            pager.currentItem = startingPosition
        }
        pager.setOnClickListener { toggleTitle(pager.currentItem) }
        pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageSelected(position: Int) {
                currentPosition = position
            }
        })
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putInt(STATE_CURRENT_PAGE_POSITION, currentPosition)
    }

    override fun finishAfterTransition() {
        isReturning = true
        val data = Intent()
        data.putExtra(PhotosActivity.EXTRA_STARTING_ALBUM_POSITION, startingPosition)
        data.putExtra(PhotosActivity.EXTRA_CURRENT_ALBUM_POSITION, currentPosition)
        setResult(Activity.RESULT_OK, data)
        super.finishAfterTransition()
    }

    private fun toggleTitle(position: Int) {
        val imageTitle: View? = pager.findViewWithTag("imageTitle" + position)
        imageTitle?.let {
            if (it.alpha == 1f) {
                it.animate().alpha(0f).start()
            } else {
                it.animate().alpha(1f).start()
            }
        }
    }

    companion object {
        private const val STATE_CURRENT_PAGE_POSITION = "state_current_page_position"
    }
}
