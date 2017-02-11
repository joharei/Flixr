package no.joharei.flixr.common.adapters


import android.app.Activity
import android.app.ActivityOptions
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.squareup.picasso.Picasso
import no.joharei.flixr.Henson
import no.joharei.flixr.R
import no.joharei.flixr.api.models.Photo
import no.joharei.flixr.api.models.Photoset
import java.util.*

internal class PhotoAdapter(private val activity: Activity) : RecyclerView.Adapter<PhotoAdapter.ViewHolder>() {
    private val PHOTO_TYPE = 0
    private val PHOTOSET_TYPE = 1
    private val mDefaultCardImage = ContextCompat.getDrawable(activity, R.drawable.movie)
    private val photos = ArrayList<PhotoItem>()
    internal var isPhotoActivityStarted = false
    internal var userId: String? = null

    fun swap(photos: List<PhotoItem>) {
        this.photos.clear()
        this.photos.addAll(photos)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if (photos[position] is Photoset) PHOTOSET_TYPE else PHOTO_TYPE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.image_item, parent, false)
        itemView.isFocusable = true
        itemView.isFocusableInTouchMode = true
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return photos.size
    }

    internal inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private var thumbnailUrl: String? = null
        private var pos = 0
        val imageView = itemView.findViewById(R.id.image) as ImageView

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(position: Int) {
            val photo = photos[position]
            when (photo) {
                is Photoset -> thumbnailUrl = photo.thumbnailUrl
                is Photo -> {
                    thumbnailUrl = photo.thumbnailUrl
                    imageView.transitionName = photo.id.toString()
                    imageView.tag = photo.id.toString()
                }
                else -> thumbnailUrl = null
            }
            Picasso.with(activity)
                    .load(thumbnailUrl)
                    .placeholder(R.color.black_opaque)
                    .error(mDefaultCardImage)
                    .fit()
                    .centerCrop()
                    .into(imageView)
            pos = position
        }

        override fun onClick(v: View?) {
            if (photos[pos] is Photo) {
                @Suppress("UNCHECKED_CAST")
                val intent = Henson.with(activity)
                        .gotoPhotoViewerActivity()
                        .photos(photos as ArrayList<Photo>)
                        .startingPosition(pos)
                        .build()
                if (!isPhotoActivityStarted) {
                    isPhotoActivityStarted = true
                    activity.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(activity,
                            imageView, imageView.transitionName).toBundle())
                }
            } else if (photos[pos] is Photoset) {
                val item = photos[pos] as Photoset
                val intent = Henson.with(activity)
                        .gotoPhotosActivity()
                        .photosetId(item.id)
                        .photosetTitle(item.title)
                        .userId(userId)
                        .build()
                activity.startActivity(intent)
            }
        }
    }
}
