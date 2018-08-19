package no.joharei.flixr.mainpage

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.leanback.app.BackgroundManager
import androidx.leanback.app.BrowseSupportFragment
import androidx.leanback.widget.*
import kotlinx.coroutines.experimental.launch
import no.joharei.flixr.CardPresenter
import no.joharei.flixr.Henson
import no.joharei.flixr.R
import no.joharei.flixr.api.LocalCredentialStore
import no.joharei.flixr.api.models.Photoset
import no.joharei.flixr.common.Constants
import no.joharei.flixr.common.getDisplaySize
import no.joharei.flixr.error.BrowseErrorActivity
import no.joharei.flixr.glide.GlideApp
import no.joharei.flixr.mainpage.models.Contact
import no.joharei.flixr.preferences.CommonPreferences
import timber.log.Timber
import java.util.*

class MainFragment : BrowseSupportFragment(), MainView {

    private val mHandler = Handler()
    private val mRowsAdapter = ArrayObjectAdapter(ListRowPresenter())
    private val mDefaultBackground: Drawable by lazy {
        ContextCompat.getDrawable(requireContext(), R.drawable.default_background)!!
    }
    private val mMetrics: DisplayMetrics by lazy {
        DisplayMetrics()
    }
    private var mBackgroundTimer: Timer? = null
    private var mBackgroundURL: String? = null
    private val mBackgroundManager: BackgroundManager by lazy {
        BackgroundManager.getInstance(activity)
    }
    private val mainPresenter = MainPresenter()
    private val photosetAdapter by lazy { ArrayObjectAdapter(CardPresenter(requireContext())) }
    private val contactsAdapter by lazy { ArrayObjectAdapter(CardPresenter(requireContext())) }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mainPresenter.attachView(this)

        prepareBackgroundManager()

        setupUIElements()

        adapter = mRowsAdapter

        val credentialStore = LocalCredentialStore(requireContext())
        if (credentialStore.noToken()) {
            startActivity(Henson.with(activity).gotoLoginActivity().build())
        } else {
            loadPhotosets()
            loadContacts()
        }

        setupEventListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mainPresenter.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("onDestroy: $mBackgroundTimer")
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

    override fun getViewContext(): Context = super.requireContext()

    private fun prepareBackgroundManager() {
        activity?.let {
            if (!mBackgroundManager.isAttached) {
                mBackgroundManager.attach(it.window)
                it.windowManager.defaultDisplay.getMetrics(mMetrics)
            }
        }
    }

    private fun setupUIElements() {
        title = getString(R.string.hi_title, CommonPreferences.getUsername(requireContext()))
        val titleText: TextView = titleView.findViewById(R.id.title_text)
        titleText.setShadowLayer(5f, 1.5f, 1.3f, Color.BLACK)
        headersState = BrowseSupportFragment.HEADERS_ENABLED
        isHeadersTransitionOnBackEnabled = true

        // set fastLane (or headers) background color
        brandColor = ContextCompat.getColor(requireContext(), R.color.fastlane_background)
        // set search icon color
        searchAffordanceColor = ContextCompat.getColor(requireContext(), R.color.search_opaque)
    }

    private fun setupEventListeners() {
        setOnSearchClickedListener { _ ->
            Toast.makeText(activity, "Implement your own in-app search", Toast.LENGTH_LONG)
                    .show()
        }

        onItemViewClickedListener = ItemViewClickedListener()
        onItemViewSelectedListener = ItemViewSelectedListener()
    }

    private fun updateBackground(uri: String) {
        launch {
            val width = mMetrics.widthPixels
            val height = mMetrics.heightPixels
            val futureDrawable = GlideApp.with(requireContext())
                    .asDrawable()
                    .load(uri)
                    .centerCrop()
                    .override(width, height)
                    .error(mDefaultBackground)
                    .submit()
            try {
                mBackgroundManager.drawable = futureDrawable.get()
            } catch (e: Exception) {
                // ignore
            }
            GlideApp.with(requireContext()).clear(futureDrawable)
            mBackgroundTimer?.cancel()
        }
    }

    private fun startBackgroundTimer() {
        mBackgroundTimer?.cancel()
        mBackgroundTimer = Timer()
        mBackgroundTimer?.schedule(UpdateBackgroundTask(), Constants.BACKGROUND_UPDATE_DELAY.toLong())
    }

    private inner class ItemViewClickedListener : OnItemViewClickedListener {
        override fun onItemClicked(itemViewHolder: Presenter.ViewHolder, item: Any,
                                   rowViewHolder: RowPresenter.ViewHolder, row: Row) {
            when (item) {
                is Photoset -> {
                    startActivity(Henson.with(activity)
                            .gotoPhotosActivity()
                            .photosetId(item.id)
                            .photosetTitle(item.title)
                            .build())
                }
                is Contact -> {
                    startActivity(Henson.with(activity)
                            .gotoPhotosetsActivity()
                            .userId(item.nsid)
                            .userName(item.displayName)
                            .build())
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
                mBackgroundURL = item.backgroundImageUrl(getDisplaySize(requireContext()))
                startBackgroundTimer()
            }

        }
    }

    private inner class UpdateBackgroundTask : TimerTask() {

        override fun run() {
            mHandler.post {
                mBackgroundURL?.let {
                    Timber.d(mBackgroundURL)
                    updateBackground(mBackgroundURL!!)
                }
            }

        }
    }
}
