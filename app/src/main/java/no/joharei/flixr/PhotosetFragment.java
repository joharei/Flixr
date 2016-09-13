package no.joharei.flixr;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import no.joharei.flixr.adapters.PhotoAdapter;
import no.joharei.flixr.decorations.SpacesItemDecoration;
import no.joharei.flixr.network.LocalCredentialStore;
import no.joharei.flixr.network.ServiceGenerator;
import no.joharei.flixr.network.models.PhotosPhotosetContainer;
import no.joharei.flixr.network.services.FlickrService;
import no.joharei.flixr.preferences.CommonPreferences;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhotosetFragment extends Fragment {

    public static final String PHOTOSET_ID = "photosetId";
    public static final String USER_ID = "userId";
    private static final String TAG = PhotosetFragment.class.getSimpleName();
    private static final int NUM_COLUMNS = 6;
    private PhotoAdapter mAdapter;
    private long photosetId;
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
        super.onCreate(savedInstanceState);

        mAdapter = new PhotoAdapter(getActivity());

        Bundle arguments = getArguments();
        photosetId = arguments.getLong(PHOTOSET_ID);
        userId = arguments.getString(USER_ID);
        loadPhotos();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_photoset, container, false);

        RecyclerView imageGrid = (RecyclerView) rootView.findViewById(R.id.image_grid);
        imageGrid.setAdapter(mAdapter);
        imageGrid.setLayoutManager(new GridLayoutManager(getActivity(), NUM_COLUMNS));
        imageGrid.addItemDecoration(new SpacesItemDecoration(Utils.convertDpToPixel(getActivity(), 4)));
        return rootView;
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
                    mAdapter.swap(response.body().getPhotoset().getPhoto());
                }
            }

            @Override
            public void onFailure(Call<PhotosPhotosetContainer> call, Throwable t) {

            }
        });
    }
}
