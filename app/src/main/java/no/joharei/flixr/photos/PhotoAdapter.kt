package no.joharei.flixr.photos


import android.content.Context
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

internal class PhotoAdapter(private val context: Context) : RecyclerView.Adapter<PhotoAdapter.ViewHolder>() {
    private val mDefaultCardImage = ContextCompat.getDrawable(context, R.drawable.movie)
    private val photos = ArrayList<Photo>()

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
        val photo = photos[position]
        Picasso.with(context)
                .load(photo.thumbnailUrl)
                .placeholder(R.color.black_opaque)
                .error(mDefaultCardImage)
                .fit()
                .centerCrop()
                .into(holder.imageView)
        holder.itemView.setOnClickListener { v ->
            val intent = Henson.with(context)
                    .gotoPhotoViewerActivity()
                    .photos(photos)
                    .position(position)
                    .build()
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return photos.size
    }

    internal inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView = itemView.findViewById(R.id.image) as ImageView
    }
}
