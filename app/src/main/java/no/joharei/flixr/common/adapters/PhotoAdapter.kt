package no.joharei.flixr.common.adapters


import android.app.Activity
import android.app.ActivityOptions
import android.support.v7.widget.RecyclerView
import android.view.Gravity.BOTTOM
import android.view.View
import android.view.View.generateViewId
import android.view.ViewGroup
import android.view.ViewStub
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import no.joharei.flixr.Henson
import no.joharei.flixr.R
import no.joharei.flixr.api.models.Photo
import no.joharei.flixr.api.models.Photoset
import no.joharei.flixr.utils.selectableItemBackground
import org.jetbrains.anko.*
import java.util.*

internal class PhotoAdapter(private val activity: Activity) : RecyclerView.Adapter<PhotoAdapter.ViewHolder>(), AnkoLogger {
    private val PHOTO_TYPE = 0
    private val PHOTOSET_TYPE = 1
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

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val itemView = PhotoItemUI().createView(AnkoContext.create(parent!!.context, parent))
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
        val imageView = itemView.find<ImageView>(PhotoItemUI.IMAGE_ID)
        val selectionView = itemView.find<View>(PhotoItemUI.SELECTION_VIEW_ID).apply {
            isFocusable = true
            isFocusableInTouchMode = true
            setOnFocusChangeListener { _, hasFocus -> itemView.isSelected = hasFocus }
        }
        val textView by lazy {
            (itemView.find<ViewStub>(PhotoItemUI.TEXT_STUB_ID).inflate() as TextView).apply {
                setHorizontallyScrolling(true)
            }
        }

        init {
            selectionView.setOnClickListener(this)
        }

        fun bind(position: Int) {
            val photo = photos[position]
            when (photo) {
                is Photoset -> {
                    thumbnailUrl = photo.thumbnailUrl
                    textView.text = photo.title
                }
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
                    .error(R.drawable.ic_error)
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

    internal class PhotoItemUI : AnkoComponent<ViewGroup> {
        companion object {
            val IMAGE_ID = generateViewId()
            val SELECTION_VIEW_ID = generateViewId()
            val TEXT_STUB_ID = generateViewId()
        }

        override fun createView(ui: AnkoContext<ViewGroup>): View {
            return with(ui) {
                frameLayout {
                    lparams(width = wrapContent, height = wrapContent)
                    imageView {
                        id = IMAGE_ID
                    }.lparams(width = dip(135), height = dip(100))
                    view {
                        id = SELECTION_VIEW_ID
                        background = selectableItemBackground
                    }.lparams(width = dip(135), height = dip(100))
                    viewStub {
                        id = TEXT_STUB_ID
                        layoutResource = R.layout.thumbnail_text
                    }.lparams(width = dip(135), height = wrapContent, gravity = BOTTOM)
                }
            }
        }
    }
}
