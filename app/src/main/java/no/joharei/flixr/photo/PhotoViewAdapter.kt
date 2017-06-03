package no.joharei.flixr.photo

import android.app.Activity
import android.graphics.Color
import android.support.v4.view.PagerAdapter
import android.support.v4.widget.TextViewCompat
import android.view.Gravity
import android.view.View
import android.view.View.generateViewId
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Callback
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import no.joharei.flixr.R
import no.joharei.flixr.api.models.Photo
import no.joharei.flixr.utils.Utils
import org.jetbrains.anko.*
import java.util.*


internal class PhotoViewAdapter(private val activity: Activity, private val photos: ArrayList<Photo>, private val positionInAlbum: Int) : PagerAdapter() {
    val displaySize = Utils.getDisplaySize(activity)
    var currentItem: View? = null

    override fun getCount(): Int {
        return photos.size
    }

    override fun isViewFromObject(view: View?, obj: Any?): Boolean {
        return view === obj
    }

    override fun instantiateItem(container: ViewGroup?, position: Int): Any? {
        if (container == null) {
            return null
        }
        val context = container.context
        val photoItemUI = PhotoItemUI()
        val itemLayout = photoItemUI.createView(AnkoContext.create(context, container))

        val photo = photos[position]
        val imageView = itemLayout.find<ImageView>(PhotoItemUI.IMAGE_ID)
        imageView.transitionName = photo.id.toString()
        val textView = itemLayout.find<TextView>(PhotoItemUI.TEXT_ID)
        textView.tag = "imageTitle" + position
        textView.text = photo.title

        val fullscreenImageUrl = photo.fullscreenImageUrl(displaySize)
        // Try to load fullscreen image from cache
        Picasso.with(context)
                .load(fullscreenImageUrl)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(imageView, object : Callback {
                    override fun onSuccess() {
                        activity.startPostponedEnterTransition()
                    }

                    override fun onError() {
                        // If there was no cache, load the thumbnail first...
                        val imageRequest = Picasso.with(context)
                                .load(photo.thumbnailUrl)
                        if (position == positionInAlbum) {
                            imageRequest.noFade()
                        }
                        val fullScreenRequest = Picasso.with(context)
                                .load(fullscreenImageUrl)
                                .noPlaceholder()
                        imageRequest.into(imageView, object : Callback {
                            override fun onSuccess() {
                                activity.startPostponedEnterTransition()
                                // ... and whether loading the thumbnail was successful or not,
                                // load the full screen image
                                fullScreenRequest.into(imageView)
                            }

                            override fun onError() {
                                activity.startPostponedEnterTransition()
                                fullScreenRequest.into(imageView)
                            }
                        })
                    }
                })

        container.addView(itemLayout)
        return itemLayout
    }

    override fun destroyItem(container: ViewGroup?, position: Int, obj: Any?) {
        container?.removeView(obj as View)
    }

    override fun setPrimaryItem(container: ViewGroup?, position: Int, `object`: Any?) {
        super.setPrimaryItem(container, position, `object`)
        currentItem = `object` as View?
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
