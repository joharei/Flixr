package no.joharei.flixr.common.adapters

import android.app.Activity
import android.app.ActivityOptions
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.photo_list_item.*
import kotlinx.android.synthetic.main.thumbnail_text.*
import no.joharei.flixr.Henson
import no.joharei.flixr.R
import no.joharei.flixr.api.models.Photo
import no.joharei.flixr.api.models.PhotoItem
import no.joharei.flixr.api.models.Photoset
import no.joharei.flixr.glide.GlideApp

class PhotoViewHolder(
    override val containerView: View,
    onClickListener: (PhotoItem) -> Unit
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    private var photoItem: PhotoItem? = null

    init {
        with(selection_view) {
            setOnFocusChangeListener { _, hasFocus -> itemView.isSelected = hasFocus }
            setOnClickListener { _ -> photoItem?.let { onClickListener(it) } }
        }
    }

    fun bind(photo: PhotoItem) {
        when (photo) {
            is Photoset -> {
                thumbnail_text.text = photo.title
            }
            is Photo -> {
                image.transitionName = photo.id.toString()
            }
        }
//            val size = sizeCalculator.sizeForChildAtPosition(position)
//            val size = Size(itemView.dip(150), itemView.dip(100))
        for (view in arrayOf(image, selection_view)) {
            view.layoutParams.apply {
                width = photo.width
                height = photo.height
            }
        }
//            thumbnail_text.layoutParams.width = size.width
        GlideApp.with(itemView.context)
            .load(photo)
            .placeholder(R.color.black_opaque)
            .error(R.drawable.ic_error)
            .override(photo.width, photo.height)
            .fitCenter()
            .into(image)
        photoItem = photo
    }

    companion object {

        var isPhotoActivityStarted = false
        var userId: String? = null

        fun Activity.onPhotoClicked(photoItem: PhotoItem) {
            if (photoItem is Photo) {
                @Suppress("UNCHECKED_CAST")
                // TODO: implement cached photos
//                val intent = Henson.with(this)
//                    .gotoPhotoViewerActivity()
//                    .photos(photos as ArrayList<Photo>)
//                    .startingPosition(pos)
//                    .build()
                if (!isPhotoActivityStarted) {
                    isPhotoActivityStarted = true
                    startActivity(
                        intent, ActivityOptions.makeSceneTransitionAnimation(
                            this,
                            image,
                            image.transitionName
                        ).toBundle()
                    )
                }
            } else if (photoItem is Photoset) {
                val intent = Henson.with(this)
                    .gotoPhotosActivity()
                    .photosetId(photoItem.id)
                    .photosetTitle(photoItem.title)
                    .userId(userId)
                    .build()
                startActivity(intent)
            }
        }

    }

}