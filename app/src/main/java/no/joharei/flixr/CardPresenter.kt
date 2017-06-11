package no.joharei.flixr

import android.content.Context
import android.support.v17.leanback.widget.ImageCardView
import android.support.v17.leanback.widget.Presenter
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import android.view.ContextThemeWrapper
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.TextView
import com.squareup.picasso.Picasso
import no.joharei.flixr.api.models.Photoset
import no.joharei.flixr.mainpage.models.Contact
import org.jetbrains.anko.dimen
import org.jetbrains.anko.find

class CardPresenter(val context: Context) : Presenter() {
    private var sSelectedBackgroundColor: Int = 0
    private var sDefaultBackgroundColor: Int = 0
    private lateinit var cardView: ImageCardView
    private val imageHeight: Int by lazy { context.dimen(R.dimen.photo_card_height) }

    override fun onCreateViewHolder(parent: ViewGroup): Presenter.ViewHolder {
        val context = parent.context
        sDefaultBackgroundColor = ContextCompat.getColor(context, R.color.default_background)
        sSelectedBackgroundColor = ContextCompat.getColor(context, R.color.selected_background)

        val cardView = object : ImageCardView(ContextThemeWrapper(context, R.style.CustomImageCardTheme)) {
            val textView = find<TextView>(R.id.title_text)

            init {
                isFocusable = true
                isFocusableInTouchMode = true
                setMainImageDimensions(WRAP_CONTENT, imageHeight)
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
            is Contact -> item.displayName
            else -> null
        }
        when (item) {
            is Photoset -> scaleWidth(item.thumbnailHeight, item.thumbnailWidth, imageHeight)
            is Contact -> scaleWidth(item.thumbnailDim, item.thumbnailDim, imageHeight)
            else -> null
        }?.let {
            cardView.setMainImageDimensions(it, imageHeight)
        }
        Picasso.with(context)
                .load(when (item) {
                    is Photoset -> item.thumbnailUrl(Int.MAX_VALUE, context.dimen(R.dimen.photo_card_height))
                    is Contact -> item.cardImageUrl
                    else -> null
                })
                .placeholder(R.color.black_opaque)
                .error(R.drawable.ic_error)
                .into(cardView.mainImageView)
    }

    private fun scaleWidth(originalHeight: Int, originalWidth: Int, newHeight: Int) =
            newHeight * originalWidth / originalHeight

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
