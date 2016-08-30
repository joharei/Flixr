/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package no.joharei.flixr;

import android.content.Intent;
import android.graphics.Color;
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
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.Timer;
import java.util.TimerTask;

import no.joharei.flixr.network.LocalCredentialStore;
import no.joharei.flixr.network.ServiceGenerator;
import no.joharei.flixr.network.models.Contact;
import no.joharei.flixr.network.models.ContactsContainer;
import no.joharei.flixr.network.models.Photoset;
import no.joharei.flixr.network.models.PhotosetsContainer;
import no.joharei.flixr.network.services.FlickrService;
import no.joharei.flixr.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainFragment extends BrowseFragment {
    private static final String TAG = "MainFragment";

    private static final int GRID_ITEM_WIDTH = 200;
    private static final int GRID_ITEM_HEIGHT = 200;

    private final Handler mHandler = new Handler();
    private ArrayObjectAdapter mRowsAdapter;
    private Drawable mDefaultBackground;
    private DisplayMetrics mMetrics;
    private Timer mBackgroundTimer;
    private String mBackgroundURL;
    private BackgroundManager mBackgroundManager;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        prepareBackgroundManager();

        setupUIElements();

        mRowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());
        setAdapter(mRowsAdapter);

        LocalCredentialStore credentialStore = new LocalCredentialStore(getActivity());
        if (credentialStore.noToken()) {
            startActivity(new Intent(getActivity(), LoginActivity.class));
        } else {
            final FlickrService flickrService = ServiceGenerator.createService(FlickrService.class, new LocalCredentialStore(getActivity()));

            loadPhotosets(flickrService);

            loadContacts(flickrService);
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

    private void loadPhotosets(FlickrService flickrService) {
        CardPresenter cardPresenter = new CardPresenter();

        HeaderItem photosetHeader = new HeaderItem("Photosets");
        final ArrayObjectAdapter photosetAdapter = new ArrayObjectAdapter(cardPresenter);
        mRowsAdapter.add(new ListRow(photosetHeader, photosetAdapter));
        Call<PhotosetsContainer> photosetsCall = flickrService.getPhotosets(null);
        photosetsCall.enqueue(new Callback<PhotosetsContainer>() {
            @Override
            public void onResponse(Call<PhotosetsContainer> call, Response<PhotosetsContainer> response) {
                if (response.isSuccessful()) {
                    photosetAdapter.addAll(0, response.body().getPhotosets().getPhotoset());
                } else {
                    Log.d(TAG, "Response was unsuccessful: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<PhotosetsContainer> call, Throwable t) {
                Log.e(TAG, "Failure getting photosets", t);
            }
        });
    }

    private void loadContacts(FlickrService flickrService) {
        CardPresenter cardPresenter = new CardPresenter();

        HeaderItem followingHeader = new HeaderItem("Following");
        final ArrayObjectAdapter followingAdapter = new ArrayObjectAdapter(cardPresenter);
        mRowsAdapter.add(new ListRow(followingHeader, followingAdapter));
        Call<ContactsContainer> contactsCall = flickrService.getContacts();
        contactsCall.enqueue(new Callback<ContactsContainer>() {
            @Override
            public void onResponse(Call<ContactsContainer> call, Response<ContactsContainer> response) {
                if (response.isSuccessful()) {
                    followingAdapter.addAll(0, response.body().getContacts().getContact());
                }
            }

            @Override
            public void onFailure(Call<ContactsContainer> call, Throwable t) {

            }
        });
    }

    private void prepareBackgroundManager() {

        mBackgroundManager = BackgroundManager.getInstance(getActivity());
        mBackgroundManager.attach(getActivity().getWindow());
        mDefaultBackground = getResources().getDrawable(R.drawable.default_background);
        mMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(mMetrics);
    }

    private void setupUIElements() {
        setTitle(getString(R.string.browse_title));
        setHeadersState(HEADERS_ENABLED);
        setHeadersTransitionOnBackEnabled(true);

        // set fastLane (or headers) background color
        setBrandColor(getResources().getColor(R.color.fastlane_background));
        // set search icon color
        setSearchAffordanceColor(getResources().getColor(R.color.search_opaque));
    }

    private void setupEventListeners() {
        setOnSearchClickedListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Implement your own in-app search", Toast.LENGTH_LONG)
                        .show();
            }
        });

        setOnItemViewClickedListener(new ItemViewClickedListener());
        setOnItemViewSelectedListener(new ItemViewSelectedListener());
    }

    protected void updateBackground(String uri) {
        int width = mMetrics.widthPixels;
        int height = mMetrics.heightPixels;
        Glide.with(getActivity())
                .load(uri)
                .centerCrop()
                .error(mDefaultBackground)
                .into(new SimpleTarget<GlideDrawable>(width, height) {
                    @Override
                    public void onResourceReady(GlideDrawable resource,
                                                GlideAnimation<? super GlideDrawable>
                                                        glideAnimation) {
                        mBackgroundManager.setDrawable(resource);
                    }
                });
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
                Intent intent = new Intent(getActivity(), PhotosetActivity.class);
                intent.putExtra(PhotosetFragment.PHOTOSET_ID, photoset.getId());

//                Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
//                        getActivity(),
//                        ((ImageCardView) itemViewHolder.view).getMainImageView(),
//                        PhotosetActivity.SHARED_ELEMENT_NAME).toBundle();
//                getActivity().startActivity(intent, bundle);
                getActivity().startActivity(intent);
            } else if (item instanceof Contact) {
                Contact contact = (Contact) item;
                Intent intent = new Intent(getActivity(), PhotosetActivity.class);
                intent.putExtra(PhotosetsFragment.USER_ID, contact.getNsid());
                getActivity().startActivity(intent);
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
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (mBackgroundURL != null) {
                        Log.d(TAG, mBackgroundURL);
                        updateBackground(mBackgroundURL);
                    }
                }
            });

        }
    }

    private class GridItemPresenter extends Presenter {
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent) {
            TextView view = new TextView(parent.getContext());
            view.setLayoutParams(new ViewGroup.LayoutParams(GRID_ITEM_WIDTH, GRID_ITEM_HEIGHT));
            view.setFocusable(true);
            view.setFocusableInTouchMode(true);
            view.setBackgroundColor(getResources().getColor(R.color.default_background));
            view.setTextColor(Color.WHITE);
            view.setGravity(Gravity.CENTER);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, Object item) {
            ((TextView) viewHolder.view).setText((String) item);
        }

        @Override
        public void onUnbindViewHolder(ViewHolder viewHolder) {
        }
    }

}
