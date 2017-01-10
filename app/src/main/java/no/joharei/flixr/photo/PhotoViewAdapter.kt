package no.joharei.flixr.photo

import android.support.v4.view.PagerAdapter
import android.support.v4.widget.TextViewCompat
import android.view.Gravity
import android.view.View
import android.view.View.generateViewId
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import no.joharei.flixr.R
import no.joharei.flixr.api.models.Photo
import org.jetbrains.anko.*
import java.util.*


internal class PhotoViewAdapter(private val photos: ArrayList<Photo>) : PagerAdapter() {

    override fun getCount(): Int {
        return photos.size
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view === obj
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val context = container.context
        val photoItemUI = PhotoItemUI()
        val itemLayout = photoItemUI.createView(AnkoContext.create(context, container))

        val photo = photos[position]
        val imageView = itemLayout.findViewById(photoItemUI.IMAGE_ID) as ImageView
        val textView = itemLayout.findViewById(photoItemUI.TEXT_ID) as TextView
        textView.tag = "imageTitle" + position
        textView.text = photo.title
        Picasso.with(context)
                .load(photo.fullscreenImageUrl)
                .into(imageView)

        container.addView(itemLayout)
        return itemLayout
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(obj as View)
    }

    internal class PhotoItemUI : AnkoComponent<ViewGroup> {
        val IMAGE_ID = generateViewId()
        val TEXT_ID = generateViewId()
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
