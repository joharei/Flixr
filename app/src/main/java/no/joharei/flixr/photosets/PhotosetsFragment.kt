package no.joharei.flixr.photosets


import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import androidx.annotation.Nullable
import androidx.leanback.app.VerticalGridSupportFragment
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.VerticalGridPresenter
import com.f2prateek.dart.Dart
import com.f2prateek.dart.InjectExtra
import no.joharei.flixr.CardPresenter
import no.joharei.flixr.Henson
import no.joharei.flixr.network.models.Photoset

class PhotosetsFragment : VerticalGridSupportFragment(), PhotosetsView {
    @InjectExtra
    @Nullable
    @JvmField
    internal var userId: String? = null
    private val photosetsAdapter by lazy { ArrayObjectAdapter(CardPresenter(context)) }
    private val photosetsPresenter = PhotosetsPresenter()
    private val progressDialog by lazy { ProgressDialog(context) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        photosetsPresenter.attachView(this)

        val gridPresenter = VerticalGridPresenter()
        gridPresenter.numberOfColumns = 4
        setGridPresenter(gridPresenter)

        adapter = photosetsAdapter
        setOnItemViewClickedListener { _, item, _, _ ->
            if (item is Photoset) {
                val intent = Henson.with(activity)
                        .gotoPhotosActivity()
                        .photosetId(item.id)
                        .photosetTitle(item.title)
                        .userId(userId)
                        .build()
                startActivity(intent)
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

    override fun getContext(): Context = requireContext()

}
