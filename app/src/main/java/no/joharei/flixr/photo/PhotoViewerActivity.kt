package no.joharei.flixr.photo

import android.app.Activity
import android.os.Bundle
import android.support.v4.view.ViewPager
import com.f2prateek.dart.Dart
import com.f2prateek.dart.InjectExtra
import no.joharei.flixr.api.models.Photo
import org.jetbrains.anko.support.v4.viewPager
import java.util.*

class PhotoViewerActivity : Activity() {

    @InjectExtra
    internal lateinit var photos: ArrayList<Photo>
    @InjectExtra
    @JvmField
    internal var position: Int = -1
    private lateinit var pager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Dart.inject(this)

        pager = viewPager()

        val adapter = PhotoViewAdapter(photos)
        pager.adapter = adapter
        pager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                fadeOutTitle(position)
            }
        })
        if (position >= 0) {
            pager.currentItem = position
            pager.post { fadeOutTitle(position) }
        }
    }

    private fun fadeOutTitle(position: Int) {
        val imageTitle = pager.findViewWithTag("imageTitle" + position)
        if (imageTitle != null) {
            imageTitle.alpha = 1f
            imageTitle.animate().setStartDelay(2000).alpha(0f).start()
        }
    }
}
