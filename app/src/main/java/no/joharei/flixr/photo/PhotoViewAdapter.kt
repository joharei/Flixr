package no.joharei.flixr.photo

import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import no.joharei.flixr.R
import no.joharei.flixr.api.models.Photo
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
        val inflater = LayoutInflater.from(context)
        val itemLayout = inflater.inflate(R.layout.fullscreen_image, container, false)

        val photo = photos[position]
        val imageView = itemLayout.findViewById(R.id.image) as ImageView
        val textView = itemLayout.findViewById(R.id.title) as TextView
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
}
