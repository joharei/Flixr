package no.joharei.flixr.photos;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import no.joharei.flixr.R;
import no.joharei.flixr.api.models.Photo;
import no.joharei.flixr.decorations.SpacesItemDecoration;
import no.joharei.flixr.preferences.CommonPreferences;
import no.joharei.flixr.utils.Utils;

public class PhotosFragment extends Fragment implements PhotosView {

    public static final String PHOTOSET_ID = "photosetId";
    public static final String USER_ID = "userId";
    private static final int NUM_COLUMNS = 6;
    private PhotoAdapter mAdapter;
    private long photosetId;
    private String userId;
    private PhotosPresenter photosPresenter;

    public static PhotosFragment newInstance(long photosetId, String userId) {
        PhotosFragment fragment = new PhotosFragment();

        Bundle args = new Bundle();
        args.putLong(PHOTOSET_ID, photosetId);
        args.putString(USER_ID, userId);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        photosPresenter = new PhotosPresenter();
        photosPresenter.attachView(this);

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
        String user = userId != null ? userId : CommonPreferences.getUserNsid(getActivity());
        photosPresenter.fetchPhotos(photosetId, user);
    }

    @Override
    public void showPhotos(List<Photo> photos) {
        mAdapter.swap(photos);
    }
}
