package no.joharei.flixr.photosets;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v17.leanback.app.VerticalGridFragment;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.VerticalGridPresenter;

import com.f2prateek.dart.Dart;
import com.f2prateek.dart.InjectExtra;

import java.util.List;

import no.joharei.flixr.CardPresenter;
import no.joharei.flixr.Henson;
import no.joharei.flixr.api.models.Photoset;

public class PhotosetsFragment extends VerticalGridFragment implements PhotosetsView {

    private static final int NUM_COLUMNS = 4;
    @InjectExtra
    String userId;
    private ArrayObjectAdapter mAdapter;
    private PhotosetsPresenter photosetsPresenter;

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
                Intent intent = Henson.with(getActivity())
                        .gotoPhotosActivity()
                        .photosetId(photoset.getId())
                        .userId(userId)
                        .build();
                getActivity().startActivity(intent);
            }
        });

        loadPhotosets();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Dart.inject(this, getActivity());
    }

    private void loadPhotosets() {
        photosetsPresenter.fetchPhotosets(userId);
    }

    @Override
    public void showPhotosets(List<Photoset> photosets) {
        mAdapter.addAll(0, photosets);
    }
}
