package no.joharei.flixr.photos


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
import java.util.*

internal class PhotoAdapter(private val activity: Activity) : RecyclerView.Adapter<PhotoAdapter.ViewHolder>() {
    private val mDefaultCardImage = ContextCompat.getDrawable(activity, R.drawable.movie)
    private val photos = ArrayList<Photo>()
    internal var isPhotoActivityStarted = false

    fun swap(photos: List<Photo>) {
        this.photos.clear()
        this.photos.addAll(photos)
        notifyDataSetChanged()
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

        private var pos = 0
        val imageView = itemView.findViewById(R.id.image) as ImageView

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(position: Int) {
            Picasso.with(activity)
                    .load(photos[position].thumbnailUrl)
                    .placeholder(R.color.black_opaque)
                    .error(mDefaultCardImage)
                    .fit()
                    .centerCrop()
                    .into(imageView)
            pos = position
            imageView.transitionName = photos[position].id.toString()
            imageView.tag = photos[position].id.toString()
        }

        override fun onClick(v: View?) {
            val intent = Henson.with(activity)
                    .gotoPhotoViewerActivity()
                    .photos(photos)
                    .startingPosition(pos)
                    .build()
            if (!isPhotoActivityStarted) {
                isPhotoActivityStarted = true
                activity.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(activity,
                        imageView, imageView.transitionName).toBundle())
            }
        }
    }
}
