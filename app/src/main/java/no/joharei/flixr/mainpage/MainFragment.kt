package no.joharei.flixr.mainpage

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.support.v17.leanback.app.BackgroundManager
import android.support.v17.leanback.app.BrowseFragment
import android.support.v17.leanback.widget.*
import android.support.v4.content.ContextCompat
import android.util.DisplayMetrics
import android.widget.Toast
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import no.joharei.flixr.CardPresenter
import no.joharei.flixr.Henson
import no.joharei.flixr.R
import no.joharei.flixr.api.LocalCredentialStore
import no.joharei.flixr.api.models.Photoset
import no.joharei.flixr.error.BrowseErrorActivity
import no.joharei.flixr.mainpage.models.Contact
import no.joharei.flixr.preferences.CommonPreferences
import no.joharei.flixr.utils.Constants
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug
import java.util.*

class MainFragment : BrowseFragment(), MainView, AnkoLogger {

    val mHandler = Handler()
    val mRowsAdapter = ArrayObjectAdapter(ListRowPresenter())
    val mDefaultBackground: Drawable by lazy {
        ContextCompat.getDrawable(activity, R.drawable.default_background)
    }
    val mMetrics: DisplayMetrics by lazy {
        DisplayMetrics()
    }
    var mBackgroundTimer: Timer? = null
    var mBackgroundURL: String? = null
    val mBackgroundManager: BackgroundManager by lazy {
        BackgroundManager.getInstance(activity)
    }
    val mBackgroundTarget = object : Target {
        override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom) {
            mBackgroundManager.setBitmap(bitmap)
        }

        override fun onBitmapFailed(errorDrawable: Drawable) {
        }

        override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
        }
    }
    val mainPresenter = MainPresenter()
    val photosetAdapter by lazy {
        ArrayObjectAdapter(CardPresenter())
    }
    val contactsAdapter by lazy {
        ArrayObjectAdapter(CardPresenter())
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mainPresenter.attachView(this)

        prepareBackgroundManager()

        setupUIElements()

        adapter = mRowsAdapter

        val credentialStore = LocalCredentialStore(activity)
        if (credentialStore.noToken()) {
            startActivity(Henson.with(activity).gotoLoginActivity().build())
        } else {
            loadPhotosets()
            loadContacts()
        }

        setupEventListeners()
    }

    override fun onDestroy() {
        super.onDestroy()
        debug("onDestroy: " + mBackgroundTimer?.toString())
        mBackgroundTimer?.cancel()
    }

    private fun loadPhotosets() {
        val photosetHeader = HeaderItem("Photosets")
        mRowsAdapter.add(ListRow(photosetHeader, photosetAdapter))
        mainPresenter.fetchMyPhotosets()
    }

    override fun showMyPhotosets(photosets: List<Photoset>) {
        photosetAdapter.addAll(0, photosets)
    }

    private fun loadContacts() {
        val followingHeader = HeaderItem("Following")
        mRowsAdapter.add(ListRow(followingHeader, contactsAdapter))
        mainPresenter.fetchMyContacts()
    }

    override fun showMyContacts(contacts: List<Contact>) {
        contactsAdapter.addAll(0, contacts)
    }

    private fun prepareBackgroundManager() {
        mBackgroundManager.attach(activity.window)
        activity.windowManager.defaultDisplay.getMetrics(mMetrics)
    }

    private fun setupUIElements() {
        title = getString(R.string.hi_title, CommonPreferences.getUsername(activity))
        headersState = BrowseFragment.HEADERS_ENABLED
        isHeadersTransitionOnBackEnabled = true

        // set fastLane (or headers) background color
        brandColor = ContextCompat.getColor(activity, R.color.fastlane_background)
        // set search icon color
        searchAffordanceColor = ContextCompat.getColor(activity, R.color.search_opaque)
    }

    private fun setupEventListeners() {
        setOnSearchClickedListener { view ->
            Toast.makeText(activity, "Implement your own in-app search", Toast.LENGTH_LONG)
                    .show()
        }

        onItemViewClickedListener = ItemViewClickedListener()
        onItemViewSelectedListener = ItemViewSelectedListener()
    }

    private fun updateBackground(uri: String) {
        val width = mMetrics.widthPixels
        val height = mMetrics.heightPixels
        Picasso.with(activity)
                .load(uri)
                .centerCrop()
                .resize(width, height)
                .error(mDefaultBackground)
                .into(mBackgroundTarget)
        mBackgroundTimer?.cancel()
    }

    private fun startBackgroundTimer() {
        mBackgroundTimer?.cancel()
        mBackgroundTimer = Timer()
        mBackgroundTimer!!.schedule(UpdateBackgroundTask(), Constants.BACKGROUND_UPDATE_DELAY.toLong())
    }

    private inner class ItemViewClickedListener : OnItemViewClickedListener {
        override fun onItemClicked(itemViewHolder: Presenter.ViewHolder, item: Any,
                                   rowViewHolder: RowPresenter.ViewHolder, row: Row) {
            when (item) {
                is Photoset -> {
                    val intent = Henson.with(activity)
                            .gotoPhotosActivity()
                            .photosetId(item.id)
                            .build()
                    startActivity(intent)
                }
                is Contact -> {
                    val intent = Henson.with(activity)
                            .gotoPhotosetsActivity()
                            .build()
                    val extras = Henson.with(activity)
                            .gotoPhotosetsFragment()
                            .userId(item.nsid)
                            .build()
                    intent.putExtras(extras)
                    startActivity(intent)
                }
                is String -> {
                    if (getString(R.string.error_fragment) in item) {
                        val intent = Intent(activity, BrowseErrorActivity::class.java)
                        startActivity(intent)
                    }
                }
                else -> {
                    Toast.makeText(activity, item.toString(), Toast.LENGTH_SHORT)
                            .show()
                }
            }
        }
    }

    private inner class ItemViewSelectedListener : OnItemViewSelectedListener {
        override fun onItemSelected(itemViewHolder: Presenter.ViewHolder?, item: Any?,
                                    rowViewHolder: RowPresenter.ViewHolder, row: Row) {
            if (item is Photoset) {
                mBackgroundURL = item.backgroundImageUrl
                startBackgroundTimer()
            }

        }
    }

    private inner class UpdateBackgroundTask : TimerTask() {

        override fun run() {
            mHandler.post {
                mBackgroundURL?.let {
                    debug(mBackgroundURL)
                    updateBackground(mBackgroundURL!!)
                }
            }

        }
    }
}