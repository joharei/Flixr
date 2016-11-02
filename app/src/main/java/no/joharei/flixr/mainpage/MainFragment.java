package no.joharei.flixr.mainpage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v17.leanback.app.BackgroundManager;
import android.support.v17.leanback.app.BrowseFragment;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.OnItemViewSelectedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import no.joharei.flixr.CardPresenter;
import no.joharei.flixr.R;
import no.joharei.flixr.api.LocalCredentialStore;
import no.joharei.flixr.api.models.Photoset;
import no.joharei.flixr.error.BrowseErrorActivity;
import no.joharei.flixr.login.LoginActivity;
import no.joharei.flixr.mainpage.models.Contact;
import no.joharei.flixr.photos.PhotosActivity;
import no.joharei.flixr.photos.PhotosFragment;
import no.joharei.flixr.photosets.PhotosetsActivity;
import no.joharei.flixr.photosets.PhotosetsFragment;
import no.joharei.flixr.preferences.CommonPreferences;
import no.joharei.flixr.utils.Constants;

public class MainFragment extends BrowseFragment implements MainView {
    private static final String TAG = "MainFragment";

    private final Handler mHandler = new Handler();
    private ArrayObjectAdapter mRowsAdapter;
    private Drawable mDefaultBackground;
    private DisplayMetrics mMetrics;
    private Timer mBackgroundTimer;
    private String mBackgroundURL;
    private BackgroundManager mBackgroundManager;
    private Target mBackgroundTarget;
    private MainPresenter mainPresenter;
    private ArrayObjectAdapter photosetAdapter;
    private ArrayObjectAdapter contactsAdapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mainPresenter = new MainPresenter();
        mainPresenter.attachView(this);

        prepareBackgroundManager();

        setupUIElements();

        mRowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());
        setAdapter(mRowsAdapter);

        LocalCredentialStore credentialStore = new LocalCredentialStore(getActivity());
        if (credentialStore.noToken()) {
            startActivity(Henson.with(getActivity()).gotoLoginActivity().build());
        } else {
            loadPhotosets();
            loadContacts();
        }

        setupEventListeners();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != mBackgroundTimer) {
            Log.d(TAG, "onDestroy: " + mBackgroundTimer.toString());
            mBackgroundTimer.cancel();
        }
    }

    private void loadPhotosets() {
        CardPresenter cardPresenter = new CardPresenter();

        HeaderItem photosetHeader = new HeaderItem("Photosets");
        photosetAdapter = new ArrayObjectAdapter(cardPresenter);
        mRowsAdapter.add(new ListRow(photosetHeader, photosetAdapter));
        mainPresenter.fetchMyPhotosets();
    }

    @Override
    public void showMyPhotosets(List<Photoset> photosets) {
        photosetAdapter.addAll(0, photosets);
    }

    private void loadContacts() {
        CardPresenter cardPresenter = new CardPresenter();

        HeaderItem followingHeader = new HeaderItem("Following");
        contactsAdapter = new ArrayObjectAdapter(cardPresenter);
        mRowsAdapter.add(new ListRow(followingHeader, contactsAdapter));
        mainPresenter.fetchMyContacts();
    }

    @Override
    public void showMyContacts(List<Contact> contacts) {
        contactsAdapter.addAll(0, contacts);
    }

    private void prepareBackgroundManager() {

        mBackgroundManager = BackgroundManager.getInstance(getActivity());
        mBackgroundManager.attach(getActivity().getWindow());
        mDefaultBackground = ContextCompat.getDrawable(getActivity(), R.drawable.default_background);
        mBackgroundTarget = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                mBackgroundManager.setBitmap(bitmap);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
            }
        };
        mMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(mMetrics);
    }

    private void setupUIElements() {
        setTitle(getString(R.string.hi_title, CommonPreferences.getUsername(getActivity())));
        setHeadersState(HEADERS_ENABLED);
        setHeadersTransitionOnBackEnabled(true);

        // set fastLane (or headers) background color
        setBrandColor(ContextCompat.getColor(getActivity(), R.color.fastlane_background));
        // set search icon color
        setSearchAffordanceColor(ContextCompat.getColor(getActivity(), R.color.search_opaque));
    }

    private void setupEventListeners() {
        setOnSearchClickedListener(view ->
                Toast.makeText(getActivity(), "Implement your own in-app search", Toast.LENGTH_LONG)
                        .show());

        setOnItemViewClickedListener(new ItemViewClickedListener());
        setOnItemViewSelectedListener(new ItemViewSelectedListener());
    }

    protected void updateBackground(String uri) {
        int width = mMetrics.widthPixels;
        int height = mMetrics.heightPixels;
        Picasso.with(getActivity())
                .load(uri)
                .centerCrop()
                .resize(width, height)
                .error(mDefaultBackground)
                .into(mBackgroundTarget);
        mBackgroundTimer.cancel();
    }

    private void startBackgroundTimer() {
        if (null != mBackgroundTimer) {
            mBackgroundTimer.cancel();
        }
        mBackgroundTimer = new Timer();
        mBackgroundTimer.schedule(new UpdateBackgroundTask(), Constants.BACKGROUND_UPDATE_DELAY);
    }

    private final class ItemViewClickedListener implements OnItemViewClickedListener {
        @Override
        public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item,
                                  RowPresenter.ViewHolder rowViewHolder, Row row) {

            if (item instanceof Photoset) {
                Photoset photoset = (Photoset) item;
                Log.d(TAG, "Item: " + item.toString());
                Intent intent = Henson.with(getActivity())
                        .gotoPhotosetActivity()
                        .photosetId(photoset.getId())
                        .build();
                startActivity(intent);
            } else if (item instanceof Contact) {
                Contact contact = (Contact) item;
                Intent intent = Henson.with(getActivity())
                        .gotoPhotosetActivity()
                        .userId(contact.getNsid())
                        .build();
                startActivity(intent);
            } else if (item instanceof String) {
                if (((String) item).contains(getString(R.string.error_fragment))) {
                    Intent intent = new Intent(getActivity(), BrowseErrorActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), ((String) item), Toast.LENGTH_SHORT)
                            .show();
                }
            }
        }
    }

    private final class ItemViewSelectedListener implements OnItemViewSelectedListener {
        @Override
        public void onItemSelected(Presenter.ViewHolder itemViewHolder, Object item,
                                   RowPresenter.ViewHolder rowViewHolder, Row row) {
            if (item instanceof Photoset) {
                mBackgroundURL = ((Photoset) item).getBackgroundImageUrl();
                startBackgroundTimer();
            }

        }
    }

    private class UpdateBackgroundTask extends TimerTask {

        @Override
        public void run() {
            mHandler.post(() -> {
                if (mBackgroundURL != null) {
                    Log.d(TAG, mBackgroundURL);
                    updateBackground(mBackgroundURL);
                }
            });

        }
    }
}
