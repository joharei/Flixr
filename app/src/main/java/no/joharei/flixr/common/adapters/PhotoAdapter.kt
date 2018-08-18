package no.joharei.flixr.common.adapters


import android.app.Activity
import android.app.ActivityOptions
import android.graphics.drawable.Drawable
import android.util.Size
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.ListPreloader
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader
import com.bumptech.glide.util.FixedPreloadSizeProvider
import com.fivehundredpx.greedolayout.GreedoLayoutSizeCalculator
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.photo_list_item.*
import kotlinx.android.synthetic.main.photo_list_item.view.*
import kotlinx.android.synthetic.main.thumbnail_text.*
import no.joharei.flixr.Henson
import no.joharei.flixr.R
import no.joharei.flixr.api.models.Photo
import no.joharei.flixr.api.models.Photoset
import no.joharei.flixr.common.extensions.dimen
import no.joharei.flixr.common.extensions.dip
import no.joharei.flixr.glide.GlideApp
import java.util.*


internal class PhotoAdapter(
        private val activity: Activity
) : RecyclerView.Adapter<PhotoAdapter.ViewHolder>(),
        GreedoLayoutSizeCalculator.SizeCalculatorDelegate,
        ListPreloader.PreloadModelProvider<PhotoItem> {

    private val photos = ArrayList<PhotoItem>()
    lateinit var sizeCalculator: GreedoLayoutSizeCalculator
    var isPhotoActivityStarted = false
    var userId: String? = null
    private val thumbnailRequest = GlideApp.with(activity)


    fun swap(photos: List<PhotoItem>) {
        this.photos.clear()
        this.photos.addAll(photos)
        notifyDataSetChanged()
    }

    override fun aspectRatioForIndex(index: Int): Double {
        if (index >= itemCount) {
            return 1.0
        }
        return photos[index].thumbnailWidth.toDouble() / photos[index].thumbnailHeight
    }

    override fun getItemViewType(position: Int): Int {
        return if (photos[position] is Photoset) PHOTOSET_TYPE else PHOTO_TYPE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.photo_list_item, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return photos.size
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        //            recyclerView.layoutManager = FocusingGreedoLayoutManager(this).apply {
//                setMaxRowHeight(recyclerView.dimen(R.dimen.max_thumbnail_height))
//                this@PhotoAdapter.sizeCalculator = sizeCalculator
//            }
//            recyclerView.addItemDecoration(GreedoSpacingItemDecoration(recyclerView.dimen(R.dimen.photos_spacing)))
        recyclerView.layoutManager = GridLayoutManager(recyclerView.context, 4)

        recyclerView.setRecyclerListener { holder -> GlideApp.with(recyclerView).clear((holder as ViewHolder).itemView.image) }
        for (type in intArrayOf(PHOTO_TYPE, PHOTOSET_TYPE)) {
            recyclerView.recycledViewPool.setMaxRecycledViews(type, recyclerView.height / recyclerView.dimen(R.dimen.max_thumbnail_height) * 12)
        }
        recyclerView.addOnScrollListener(
                RecyclerViewPreloader(Glide.with(recyclerView),
                        this,
//                            ListPreloader.PreloadSizeProvider { _: PhotoItem, adapterPosrecyclerViewion, _ ->
//                                sizeCalculator.sizeForChildAtPosrecyclerViewion(adapterPosrecyclerViewion).let { intArrayOf(recyclerView.width, recyclerView.height) }
//                            },
                        FixedPreloadSizeProvider(recyclerView.dip(150), recyclerView.dip(100)),
                        15
                )
        )
    }

    override fun getPreloadItems(position: Int): MutableList<PhotoItem> =
            photos.subList(position, position + 1)

    override fun getPreloadRequestBuilder(item: PhotoItem): RequestBuilder<Drawable> {
        return thumbnailRequest.load(item)
    }

    inner class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), View.OnClickListener, LayoutContainer {

        private var pos = 0

        init {
            with(selection_view) {
                setOnFocusChangeListener { _, hasFocus -> itemView.isSelected = hasFocus }
                setOnClickListener(this@ViewHolder)
            }
        }

        fun bind(position: Int) {
            val photo = photos[position]
            when (photo) {
                is Photoset -> {
                    thumbnail_text.text = photo.title
                }
                is Photo -> {
                    image.transitionName = photo.id.toString()
                }
            }
//            val size = sizeCalculator.sizeForChildAtPosition(position)
            val size = Size(itemView.dip(150), itemView.dip(100))
            for (view in arrayOf(image, selection_view)) {
                view.layoutParams.apply {
                    width = size.width
                    height = size.height
                }
            }
            thumbnail_text.layoutParams.width = size.width
            thumbnailRequest
                    .load(photo)
                    .placeholder(R.color.black_opaque)
                    .error(R.drawable.ic_error)
                    .into(image)
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
                            image, image.transitionName).toBundle())
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

    companion object {

        private const val PHOTO_TYPE = 0
        private const val PHOTOSET_TYPE = 1

    }

}
