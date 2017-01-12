package no.joharei.flixr

import android.graphics.drawable.Drawable
import android.support.v17.leanback.widget.ImageCardView
import android.support.v17.leanback.widget.Presenter
import android.support.v4.content.ContextCompat
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import no.joharei.flixr.api.models.Photo
import no.joharei.flixr.api.models.Photoset
import no.joharei.flixr.mainpage.models.Contact

class CardPresenter : Presenter() {
    private var sSelectedBackgroundColor: Int = 0
    private var sDefaultBackgroundColor: Int = 0
    private lateinit var mDefaultCardImage: Drawable

    override fun onCreateViewHolder(parent: ViewGroup): Presenter.ViewHolder {
        val context = parent.context
        sDefaultBackgroundColor = ContextCompat.getColor(context, R.color.default_background)
        sSelectedBackgroundColor = ContextCompat.getColor(context, R.color.selected_background)
        mDefaultCardImage = ContextCompat.getDrawable(context, R.drawable.movie)

        val cardView = object : ImageCardView(context) {
            init {
                isFocusable = true
                isFocusableInTouchMode = true
            }

            override fun setSelected(selected: Boolean) {
                updateCardBackgroundColor(this, selected)
                super.setSelected(selected)
            }
        }

        updateCardBackgroundColor(cardView, false)
        return Presenter.ViewHolder(cardView)
    }

    override fun onBindViewHolder(viewHolder: Presenter.ViewHolder, item: Any) {
        val cardView = viewHolder.view as ImageCardView

        cardView.setMainImageDimensions(ViewGroup.LayoutParams.WRAP_CONTENT, 176)
        val context = viewHolder.view.context
        if (item is Photoset) {
            cardView.titleText = item.title
            cardView.contentText = item.description
            Picasso.with(context)
                    .load(item.thumbnailUrl)
                    .error(mDefaultCardImage)
                    .into(cardView.mainImageView)
        } else if (item is Photo) {
            cardView.titleText = item.title
            Picasso.with(context)
                    .load(item.thumbnailUrl)
                    .error(mDefaultCardImage)
                    .into(cardView.mainImageView)
        } else if (item is Contact) {
            cardView.titleText = if (!item.realName.isEmpty())
                item.realName
            else
                item.userName
            Picasso.with(context)
                    .load(item.cardImageUrl)
                    .error(mDefaultCardImage)
                    .into(cardView.mainImageView)
        }
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
