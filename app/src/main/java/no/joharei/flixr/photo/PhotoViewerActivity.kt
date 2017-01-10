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
        if (position >= 0) {
            pager.currentItem = position
        }
        pager.setOnClickListener { toggleTitle(pager.currentItem) }
    }

    private fun toggleTitle(position: Int) {
        val imageTitle = pager.findViewWithTag("imageTitle" + position)
        if (imageTitle != null) {
            if (imageTitle.alpha == 1f) {
                imageTitle.animate().alpha(0f).start()
            } else {
                imageTitle.animate().alpha(1f).start()
            }
        }
    }
}
