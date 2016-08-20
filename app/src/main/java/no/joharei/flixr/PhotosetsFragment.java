package no.joharei.flixr;


import android.content.Intent;
import android.os.Bundle;
import android.support.v17.leanback.app.VerticalGridFragment;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.support.v17.leanback.widget.VerticalGridPresenter;
import android.util.Log;

import java.util.ArrayList;

import no.joharei.flixr.network.LocalCredentialStore;
import no.joharei.flixr.network.ServiceGenerator;
import no.joharei.flixr.network.models.PhotosPhotosetContainer;
import no.joharei.flixr.network.models.Photoset;
import no.joharei.flixr.network.models.PhotosetsContainer;
import no.joharei.flixr.network.services.FlickrService;
import no.joharei.flixr.preferences.CommonPreferences;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhotosetsFragment extends VerticalGridFragment {

    public static final String USER_ID = "userId";
    private static final String TAG = PhotosetsFragment.class.getSimpleName();
    private static final int NUM_COLUMNS = 4;
    private ArrayObjectAdapter mAdapter;
    private String userId;
    private ArrayList<Photoset> photosets;

    public static PhotosetsFragment newInstance(String userId) {
        PhotosetsFragment fragment = new PhotosetsFragment();

        Bundle args = new Bundle();
        args.putString(USER_ID, userId);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        VerticalGridPresenter gridPresenter = new VerticalGridPresenter();
        gridPresenter.setNumberOfColumns(NUM_COLUMNS);
        setGridPresenter(gridPresenter);

        mAdapter = new ArrayObjectAdapter(new CardPresenter());
        setAdapter(mAdapter);
        setOnItemViewClickedListener(new OnItemViewClickedListener() {
            @Override
            public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Row row) {
                if (item instanceof Photoset && photosets != null) {
                    Photoset photoset = (Photoset) item;
                    Intent intent = new Intent(getActivity(), PhotosetActivity.class);
                    intent.putExtra(PhotosetFragment.PHOTOSET_ID, photoset.getId());
                    intent.putExtra(PhotosetFragment.USER_ID, userId);
                    getActivity().startActivity(intent);
                }
            }
        });

        userId = getArguments().getString(USER_ID);
        loadPhotosets();
    }

    private void loadPhotosets() {
        LocalCredentialStore localCredentialStore = new LocalCredentialStore(getActivity());
        FlickrService flickrService = ServiceGenerator.createService(FlickrService.class, localCredentialStore);
        Call<PhotosetsContainer> photosetsCall = flickrService.getPhotosets(userId);
        photosetsCall.enqueue(new Callback<PhotosetsContainer>() {
            @Override
            public void onResponse(Call<PhotosetsContainer> call, Response<PhotosetsContainer> response) {
                if (response.isSuccessful()) {
                    photosets = new ArrayList<>();
                    photosets.addAll(response.body().getPhotosets().getPhotoset());
                    mAdapter.addAll(0, photosets);
                }
            }

            @Override
            public void onFailure(Call<PhotosetsContainer> call, Throwable t) {
                Log.e(TAG, "Failure getting photosets", t);
            }
        });
    }
}
