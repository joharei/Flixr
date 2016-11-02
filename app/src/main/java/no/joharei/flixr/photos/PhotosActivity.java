package no.joharei.flixr.photos;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.f2prateek.dart.Dart;
import com.f2prateek.dart.InjectExtra;

import java.util.List;

import no.joharei.flixr.R;
import no.joharei.flixr.api.models.Photo;
import no.joharei.flixr.decorations.SpacesItemDecoration;
import no.joharei.flixr.utils.Utils;

public class PhotosActivity extends Activity implements PhotosView {

    private static final int NUM_COLUMNS = 6;
    @InjectExtra
    long photosetId;
    @Nullable
    @InjectExtra
    String userId;
    private PhotoAdapter mAdapter;
    private PhotosPresenter photosPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_photoset);
        Dart.inject(this);

        photosPresenter = new PhotosPresenter();
        photosPresenter.attachView(this);

        mAdapter = new PhotoAdapter(this);

        RecyclerView imageGrid = (RecyclerView) findViewById(R.id.image_grid);
        imageGrid.setAdapter(mAdapter);
        imageGrid.setLayoutManager(new GridLayoutManager(this, NUM_COLUMNS));
        imageGrid.addItemDecoration(new SpacesItemDecoration(Utils.convertDpToPixel(this, 4)));

        loadPhotos();
    }

    private void loadPhotos() {
        photosPresenter.fetchPhotos(photosetId, userId);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showPhotos(List<Photo> photos) {
        mAdapter.swap(photos);
    }
}
