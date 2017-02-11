package no.joharei.flixr

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v17.leanback.widget.ImageCardView
import android.support.v17.leanback.widget.Presenter
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import android.view.ContextThemeWrapper
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.TextView
import com.squareup.picasso.Picasso
import no.joharei.flixr.api.models.Photo
import no.joharei.flixr.api.models.Photoset
import no.joharei.flixr.mainpage.models.Contact
import org.jetbrains.anko.dip
import org.jetbrains.anko.find

class CardPresenter(val context: Context) : Presenter() {
    private var sSelectedBackgroundColor: Int = 0
    private var sDefaultBackgroundColor: Int = 0
    private lateinit var mDefaultCardImage: Drawable
    private lateinit var cardView: ImageCardView

    override fun onCreateViewHolder(parent: ViewGroup): Presenter.ViewHolder {
        val context = parent.context
        sDefaultBackgroundColor = ContextCompat.getColor(context, R.color.default_background)
        sSelectedBackgroundColor = ContextCompat.getColor(context, R.color.selected_background)
        mDefaultCardImage = ContextCompat.getDrawable(context, R.drawable.movie)

        val cardView = object : ImageCardView(ContextThemeWrapper(context, R.style.CustomImageCardTheme)) {
            val textView = find<TextView>(R.id.title_text)

            init {
                isFocusable = true
                isFocusableInTouchMode = true
                setMainImageDimensions(WRAP_CONTENT, dip(100))
            }

            override fun setSelected(selected: Boolean) {
                updateCardBackgroundColor(this, selected)
                textView.ellipsize = if (selected) TextUtils.TruncateAt.MARQUEE else TextUtils.TruncateAt.END
                textView.setHorizontallyScrolling(selected)
                super.setSelected(selected)
            }
        }

        updateCardBackgroundColor(cardView, false)
        return Presenter.ViewHolder(cardView)
    }

    override fun onBindViewHolder(viewHolder: Presenter.ViewHolder, item: Any) {
        cardView = viewHolder.view as ImageCardView
        cardView.titleText = when (item) {
            is Photoset -> item.title
            is Photo -> item.title
            is Contact -> item.displayName
            else -> null
        }
        Picasso.with(context)
                .load(when (item) {
                    is Photoset -> item.thumbnailUrl
                    is Photo -> item.thumbnailUrl
                    is Contact -> item.cardImageUrl
                    else -> null
                })
                .error(mDefaultCardImage)
                .into(cardView.mainImageView)
    }

    override fun onUnbindViewHolder(viewHolder: Presenter.ViewHolder) {
        val cardView = viewHolder.view as ImageCardView
        // Remove references to images so that the garbage collector can free up memory
        cardView.badgeImage = null
        cardView.mainImage = null
    }

    private fun updateCardBackgroundColor(view: ImageCardView, selected: Boolean) {
        val color = if (selected) sSelectedBackgroundColor else sDefaultBackgroundColor
        // Both background colors should be set because the view's background is temporarily visible
        // during animations.
        view.setBackgroundColor(color)
        view.findViewById(R.id.info_field).setBackgroundColor(color)
    }
}
