package no.joharei.flixr.photo

import android.app.Activity
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Priority
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.photo_fullscreen_item.view.*
import no.joharei.flixr.R
import no.joharei.flixr.common.getDisplaySize
import no.joharei.flixr.glide.GlideApp
import no.joharei.flixr.network.models.Photo
import java.util.*


internal class PhotoViewAdapter(
        private val activity: Activity,
        private val photos: ArrayList<Photo>,
        private val positionInAlbum: Int
) : PagerAdapter() {

    private val displaySize = getDisplaySize(activity)
    var currentItem: View? = null

    override fun getCount(): Int {
        return photos.size
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view === obj
    }

    private val requestListener = object : RequestListener<Drawable> {
        override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
            activity.startPostponedEnterTransition()
            return false
        }

        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
            activity.startPostponedEnterTransition()
            return false
        }

    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val context = container.context

        return LayoutInflater.from(context).inflate(R.layout.photo_fullscreen_item, container, false).apply {
            val photo = photos[position]
            image.transitionName = photo.id.toString()
            text.tag = "imageTitle$position"
            text.text = photo.title

            val fullscreenImageUrl = photo.photoUrl(displaySize.x, displaySize.y)

            val thumbnailRequest = GlideApp.with(context)
                    // TODO: this should be the same url as the photo grid thumbnail
                    .load(photo.photoUrl(320, 320))
                    .listener(requestListener)
            if (position == positionInAlbum) {
                thumbnailRequest.dontAnimate()
            }
            GlideApp.with(context)
                    .load(fullscreenImageUrl)
                    .thumbnail(thumbnailRequest)
                    .listener(requestListener)
                    .priority(Priority.HIGH)
                    .into(image)

            container.addView(this)
            return this
        }
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        GlideApp.with(container).clear(container.image)
        container.removeView(obj as View)
    }

    override fun setPrimaryItem(container: ViewGroup, position: Int, obj: Any) {
        super.setPrimaryItem(container, position, obj)
        currentItem = obj as View
    }

}
