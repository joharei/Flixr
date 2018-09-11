package no.joharei.flixr.photosets

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import com.f2prateek.dart.Dart
import com.f2prateek.dart.InjectExtra
import kotlinx.android.synthetic.main.activity_photosets.*
import no.joharei.flixr.R
import no.joharei.flixr.common.adapters.PhotoAdapter
import no.joharei.flixr.common.adapters.PhotoViewHolder
import no.joharei.flixr.common.adapters.PhotoViewHolder.Companion.onPhotoClicked
import no.joharei.flixr.network.models.Photoset

class PhotosetsActivity : Activity(), PhotosetsView {

    @InjectExtra
    internal lateinit var userId: String
    @InjectExtra
    internal lateinit var userName: String
    private val photosetsAdapter by lazy { PhotoAdapter { onPhotoClicked(it) } }
    private val photosetsPresenter = PhotosetsPresenter()
    private val progressDialog by lazy { ProgressDialog(this) }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Dart.inject(this)

        setContentView(R.layout.activity_photosets)

        PhotoViewHolder.userId = userId

        user_name.text = userName
        photo_sets.adapter = photosetsAdapter

        photosetsPresenter.attachView(this)
        photosetsPresenter.fetchPhotosets(userId)
    }

    override fun onDestroy() {
        super.onDestroy()
        photosetsPresenter.stop()
    }

    override fun showPhotosets(photosets: List<Photoset>) = photosetsAdapter.submitList(photosets)

    override fun showProgress() {
        progressDialog.setTitle("Please wait")
        progressDialog.setMessage("Loading photosets...")
        progressDialog.show()
    }

    override fun hideProgress() = progressDialog.dismiss()

    override fun getContext(): Context = this
}
