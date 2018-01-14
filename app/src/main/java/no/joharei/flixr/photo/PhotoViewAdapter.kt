package no.joharei.flixr.photo

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.support.v4.view.PagerAdapter
import android.support.v4.widget.TextViewCompat
import android.view.Gravity
import android.view.View
import android.view.View.generateViewId
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Priority
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import no.joharei.flixr.R
import no.joharei.flixr.api.models.Photo
import no.joharei.flixr.glide.GlideApp
import no.joharei.flixr.utils.getDisplaySize
import org.jetbrains.anko.*
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
        val photoItemUI = PhotoItemUI()
        val itemLayout = photoItemUI.createView(AnkoContext.create(context, container))

        val photo = photos[position]
        val imageView = itemLayout.findViewById<ImageView>(PhotoItemUI.IMAGE_ID)
        imageView.transitionName = photo.id.toString()
        val textView = itemLayout.findViewById<TextView>(PhotoItemUI.TEXT_ID)
        textView.tag = "imageTitle" + position
        textView.text = photo.title

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
                .into(imageView)

        container.addView(itemLayout)
        return itemLayout
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.findViewById<ImageView>(PhotoItemUI.IMAGE_ID)?.let {
            GlideApp.with(container).clear(it)
        }
        container.removeView(obj as View)
    }

    override fun setPrimaryItem(container: ViewGroup, position: Int, obj: Any) {
        super.setPrimaryItem(container, position, obj)
        currentItem = obj as View
    }

    class PhotoItemUI : AnkoComponent<ViewGroup> {
        companion object {
            val IMAGE_ID = generateViewId()
            val TEXT_ID = generateViewId()
        }

        override fun createView(ui: AnkoContext<ViewGroup>): View {
            return with(ui) {
                frameLayout {
                    imageView {
                        id = IMAGE_ID
                        scaleType = ImageView.ScaleType.FIT_CENTER
                    }.lparams(width = matchParent, height = matchParent)
                    textView {
                        id = TEXT_ID
                        horizontalPadding = dimen(R.dimen.activity_horizontal_margin)
                        setShadowLayer(1.6f, 1.5f, 1.3f, Color.BLACK)
                        TextViewCompat.setTextAppearance(this, R.style.TextStyleHeader)
                        alpha = 0f
                    }.lparams(width = wrapContent, height = wrapContent) {
                        bottomMargin = dimen(R.dimen.activity_vertical_margin)
                        gravity = Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM
                    }
                }
            }
        }
    }
}
