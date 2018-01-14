package no.joharei.flixr.photosets

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.support.v4.widget.TextViewCompat
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import com.f2prateek.dart.Dart
import com.f2prateek.dart.InjectExtra
import no.joharei.flixr.R
import no.joharei.flixr.api.models.Photoset
import no.joharei.flixr.common.adapters.PhotoAdapter
import org.jetbrains.anko.*

class PhotosetsActivity : Activity(), PhotosetsView {

    @InjectExtra
    internal lateinit var userId: String
    @InjectExtra
    internal lateinit var userName: String
    private val photosetsAdapter by lazy { PhotoAdapter(this) }
    private val photosetsPresenter = PhotosetsPresenter()
    private val progressDialog by lazy { ProgressDialog(ctx) }
    private lateinit var titleText: TextView
    private lateinit var recyclerView: RecyclerView

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Dart.inject(this)

        photosetsAdapter.userId = userId

        verticalLayout {
            horizontalPadding = dimen(R.dimen.activity_horizontal_margin)
            titleText = textView {
                TextViewCompat.setTextAppearance(this, R.style.TextStyleHeadline)
                verticalPadding = dimen(R.dimen.activity_vertical_margin)
                text = userName
            }
            recyclerView = include<RecyclerView>(R.layout.vertical_scrollbar_recyclerview) {
                adapter = photosetsAdapter
            }.lparams(matchParent, matchParent)
        }

        photosetsPresenter.attachView(this)
        photosetsPresenter.fetchPhotosets(userId)
    }

    override fun onDestroy() {
        super.onDestroy()
        photosetsPresenter.stop()
    }

    override fun showPhotosets(photosets: List<Photoset>) = photosetsAdapter.swap(photosets)

    override fun showProgress() {
        progressDialog.setTitle("Please wait")
        progressDialog.setMessage("Loading photosets...")
        progressDialog.show()
    }

    override fun hideProgress() = progressDialog.dismiss()

    override fun getContext(): Context = ctx
}
