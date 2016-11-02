package no.joharei.flixr.photosets;


import android.content.Intent;
import android.os.Bundle;
import android.support.v17.leanback.app.VerticalGridFragment;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.VerticalGridPresenter;

import java.util.List;

import no.joharei.flixr.CardPresenter;
import no.joharei.flixr.api.models.Photoset;
import no.joharei.flixr.photos.PhotosActivity;
import no.joharei.flixr.photos.PhotosFragment;

public class PhotosetsFragment extends VerticalGridFragment implements PhotosetsView {

    public static final String USER_ID = "userId";
    private static final int NUM_COLUMNS = 4;
    private ArrayObjectAdapter mAdapter;
    private String userId;
    private PhotosetsPresenter photosetsPresenter;

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

        photosetsPresenter = new PhotosetsPresenter();
        photosetsPresenter.attachView(this);

        VerticalGridPresenter gridPresenter = new VerticalGridPresenter();
        gridPresenter.setNumberOfColumns(NUM_COLUMNS);
        setGridPresenter(gridPresenter);

        mAdapter = new ArrayObjectAdapter(new CardPresenter());
        setAdapter(mAdapter);
        setOnItemViewClickedListener((itemViewHolder, item, rowViewHolder, row) -> {
            if (item instanceof Photoset) {
                Photoset photoset = (Photoset) item;
                Intent intent = new Intent(getActivity(), PhotosActivity.class);
                intent.putExtra(PhotosFragment.PHOTOSET_ID, photoset.getId());
                intent.putExtra(PhotosFragment.USER_ID, userId);
                getActivity().startActivity(intent);
            }
        });

        userId = getArguments().getString(USER_ID);
        loadPhotosets();
    }

    private void loadPhotosets() {
        photosetsPresenter.fetchPhotosets(userId);
    }

    @Override
    public void showPhotosets(List<Photoset> photosets) {
        mAdapter.addAll(0, photosets);
    }
}
