package no.joharei.flixr.photo

import android.app.Activity
import android.support.v4.view.PagerAdapter
import android.support.v4.widget.TextViewCompat
import android.view.Gravity
import android.view.View
import android.view.View.generateViewId
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import no.joharei.flixr.R
import no.joharei.flixr.api.models.Photo
import no.joharei.flixr.utils.Utils
import org.jetbrains.anko.*
import java.util.*


internal class PhotoViewAdapter(activity: Activity, private val photos: ArrayList<Photo>, private val positionInAlbum: Int) : PagerAdapter() {
    val displaySize = Utils.getDisplaySize(activity)
    var currentItem: View? = null
    private val imageCallback = object : Callback {
        override fun onSuccess() {
            activity.startPostponedEnterTransition()
        }

        override fun onError() {
            activity.startPostponedEnterTransition()
        }
    }

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
        val imageView = itemLayout.findViewById(PhotoItemUI.IMAGE_ID) as ImageView
        imageView.transitionName = photo.id.toString()
        val textView = itemLayout.findViewById(PhotoItemUI.TEXT_ID) as TextView
        textView.tag = "imageTitle" + position
        textView.text = photo.title
        val imageRequest = Picasso.with(context)
                .load(photo.fullscreenImageUrl(displaySize))
        if (position == positionInAlbum) {
            imageRequest.noFade()
        }
        imageRequest.into(imageView, imageCallback)

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

    internal class PhotoItemUI : AnkoComponent<ViewGroup> {
        companion object {
            val IMAGE_ID = generateViewId()
            val TEXT_ID = generateViewId()
        }

        override fun createView(ui: AnkoContext<ViewGroup>): View {
            return with(ui) {
                frameLayout {
                    imageView {
                        id = IMAGE_ID
                        lparams(width = matchParent, height = matchParent)
                        scaleType = ImageView.ScaleType.FIT_CENTER
                    }
                    textView {
                        id = TEXT_ID
                        lparams(width = wrapContent, height = wrapContent) {
                            horizontalMargin = dimen(R.dimen.activity_horizontal_margin)
                            bottomMargin = dimen(R.dimen.activity_vertical_margin)
                            gravity = Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM
                        }
                        setShadowLayer(1.6f, 1.5f, 1.3f, 0x000)
                        TextViewCompat.setTextAppearance(this, R.style.TextStyleHeader)
                        alpha = 0f
                    }
                }
            }
        }
    }
}