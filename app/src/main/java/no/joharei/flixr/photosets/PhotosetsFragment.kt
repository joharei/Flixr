package no.joharei.flixr.photosets


import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v17.leanback.app.VerticalGridFragment
import android.support.v17.leanback.widget.ArrayObjectAdapter
import android.support.v17.leanback.widget.VerticalGridPresenter
import com.f2prateek.dart.Dart
import com.f2prateek.dart.InjectExtra
import no.joharei.flixr.CardPresenter
import no.joharei.flixr.Henson
import no.joharei.flixr.api.models.Photoset

class PhotosetsFragment : VerticalGridFragment(), PhotosetsView {
    @InjectExtra
    @Nullable
    @JvmField
    internal var userId: String? = null
    private val photosetsAdapter = ArrayObjectAdapter(CardPresenter())
    private val photosetsPresenter = PhotosetsPresenter()
    private val progressDialog by lazy {
        ProgressDialog(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        photosetsPresenter.attachView(this)

        val gridPresenter = VerticalGridPresenter()
        gridPresenter.numberOfColumns = 4
        setGridPresenter(gridPresenter)

        adapter = photosetsAdapter
        setOnItemViewClickedListener { itemViewHolder, item, rowViewHolder, row ->
            if (item is Photoset) {
                val (id) = item
                val intent = Henson.with(activity)
                        .gotoPhotosActivity()
                        .photosetId(id)
                        .userId(userId)
                        .build()
                activity.startActivity(intent)
            }
        }

        loadPhotosets()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Dart.inject(this, activity)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        photosetsPresenter.stop()
    }

    private fun loadPhotosets() {
        photosetsPresenter.fetchPhotosets(userId)
    }

    override fun showPhotosets(photosets: List<Photoset>) {
        photosetsAdapter.addAll(0, photosets)
    }

    override fun showProgress() {
        progressDialog.setTitle("Please wait")
        progressDialog.setMessage("Loading photosets...")
        progressDialog.show()
    }

    override fun hideProgress() {
        progressDialog.dismiss()
    }
}
