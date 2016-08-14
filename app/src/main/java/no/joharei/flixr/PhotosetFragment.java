package no.joharei.flixr;


import android.os.Bundle;
import android.support.v17.leanback.app.VerticalGridFragment;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.VerticalGridPresenter;
import android.util.Log;

import no.joharei.flixr.network.LocalCredentialStore;
import no.joharei.flixr.network.ServiceGenerator;
import no.joharei.flixr.network.models.PhotosPhotosetContainer;
import no.joharei.flixr.network.services.FlickrService;
import no.joharei.flixr.preferences.CommonPreferences;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhotosetFragment extends VerticalGridFragment {

    public static final String PHOTOSET_ID = "photosetId";
    private static final String TAG = VerticalGridFragment.class.getSimpleName();
    private static final int NUM_COLUMNS = 4;
    private ArrayObjectAdapter mAdapter;
    private long photosetId;

    public static PhotosetFragment newInstance(long photosetId) {
        PhotosetFragment fragment = new PhotosetFragment();

        Bundle args = new Bundle();
        args.putLong(PHOTOSET_ID, photosetId);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");

        super.onCreate(savedInstanceState);

        VerticalGridPresenter gridPresenter = new VerticalGridPresenter();
        gridPresenter.setNumberOfColumns(NUM_COLUMNS);
        setGridPresenter(gridPresenter);

        mAdapter = new ArrayObjectAdapter(new CardPresenter());
        setAdapter(mAdapter);

        photosetId = getArguments().getLong(PHOTOSET_ID);
        loadPhotos();
    }

    private void loadPhotos() {
        LocalCredentialStore localCredentialStore = new LocalCredentialStore(getActivity());
        FlickrService flickrService = ServiceGenerator.createService(FlickrService.class, localCredentialStore);
        Call<PhotosPhotosetContainer> photosetCall = flickrService.getPhotos(photosetId, CommonPreferences.getUserNsid(getActivity()));
        photosetCall.enqueue(new Callback<PhotosPhotosetContainer>() {
            @Override
            public void onResponse(Call<PhotosPhotosetContainer> call, Response<PhotosPhotosetContainer> response) {
                if (response.isSuccessful()) {
                    mAdapter.addAll(0, response.body().getPhotoset().getPhoto());
                }
            }

            @Override
            public void onFailure(Call<PhotosPhotosetContainer> call, Throwable t) {

            }
        });
    }
}
