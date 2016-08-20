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
import no.joharei.flixr.network.models.Photo;
import no.joharei.flixr.network.models.PhotosPhotosetContainer;
import no.joharei.flixr.network.services.FlickrService;
import no.joharei.flixr.preferences.CommonPreferences;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhotosetFragment extends VerticalGridFragment {

    public static final String PHOTOSET_ID = "photosetId";
    public static final String USER_ID = "userId";
    private static final String TAG = PhotosetFragment.class.getSimpleName();
    private static final int NUM_COLUMNS = 4;
    private ArrayObjectAdapter mAdapter;
    private long photosetId;
    private ArrayList<Photo> photos;
    private String userId;

    public static PhotosetFragment newInstance(long photosetId, String userId) {
        PhotosetFragment fragment = new PhotosetFragment();

        Bundle args = new Bundle();
        args.putLong(PHOTOSET_ID, photosetId);
        args.putString(USER_ID, userId);
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
        setOnItemViewClickedListener(new OnItemViewClickedListener() {
            @Override
            public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Row row) {
                if (item instanceof Photo && photos != null) {
                    Intent intent = new Intent(getActivity(), PhotoViewerActivity.class);
                    intent.putParcelableArrayListExtra(PhotoViewerActivity.PHOTOS_NAME, photos);
                    intent.putExtra(PhotoViewerActivity.PHOTO_POSITION, photos.indexOf(item));
                    startActivity(intent);
                }
            }
        });

        Bundle arguments = getArguments();
        photosetId = arguments.getLong(PHOTOSET_ID);
        userId = arguments.getString(USER_ID);
        loadPhotos();
    }

    private void loadPhotos() {
        LocalCredentialStore localCredentialStore = new LocalCredentialStore(getActivity());
        FlickrService flickrService = ServiceGenerator.createService(FlickrService.class, localCredentialStore);
        String user = userId != null ? userId : CommonPreferences.getUserNsid(getActivity());
        Call<PhotosPhotosetContainer> photosetCall = flickrService.getPhotos(photosetId, user);
        photosetCall.enqueue(new Callback<PhotosPhotosetContainer>() {
            @Override
            public void onResponse(Call<PhotosPhotosetContainer> call, Response<PhotosPhotosetContainer> response) {
                if (response.isSuccessful()) {
                    photos = new ArrayList<>();
                    photos.addAll(response.body().getPhotoset().getPhoto());
                    mAdapter.addAll(0, photos);
                }
            }

            @Override
            public void onFailure(Call<PhotosPhotosetContainer> call, Throwable t) {

            }
        });
    }
}
