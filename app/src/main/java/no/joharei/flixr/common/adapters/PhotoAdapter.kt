package no.joharei.flixr.common.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.github.awanishraj.aspectratiorecycler.ARAdapterInterface
import no.joharei.flixr.R
import no.joharei.flixr.api.models.Photo
import no.joharei.flixr.api.models.PhotoItem
import no.joharei.flixr.api.models.Photoset

class PhotoAdapter(
    private val onClickListener: (PhotoItem) -> Unit
) : RecyclerView.Adapter<PhotoViewHolder>(), ARAdapterInterface {

    private val differ = AsyncListDiffer<PhotoItem>(this, DiffCallback)

    override fun getItemViewType(position: Int) =
        if (differ.currentList[position] is Photoset) PHOTOSET_TYPE else PHOTO_TYPE

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder =
        PhotoViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.photo_list_item, parent, false),
            onClickListener
        )

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount() = differ.currentList.size

//    override fun getAdapter() = this

    override fun getAdapter(): RecyclerView.Adapter<*> {
        return this
    }

    override fun getDataset(): List<PhotoItem> = differ.currentList

    fun submitList(newList: List<PhotoItem>) {
        differ.submitList(newList)
    }

    companion object {

        private const val PHOTO_TYPE = 0
        private const val PHOTOSET_TYPE = 1

    }

    private object DiffCallback : DiffUtil.ItemCallback<PhotoItem>() {
        override fun areItemsTheSame(oldItem: PhotoItem, newItem: PhotoItem) =
            when (oldItem) {
                is Photo -> oldItem.id == (newItem as? Photo)?.id
                is Photoset -> oldItem.id == (newItem as? Photoset)?.id
            }

        override fun areContentsTheSame(oldItem: PhotoItem, newItem: PhotoItem) =
            oldItem == newItem
    }

}
