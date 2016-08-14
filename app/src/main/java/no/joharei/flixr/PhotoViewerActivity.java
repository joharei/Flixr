package no.joharei.flixr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;

import no.joharei.flixr.adapters.PhotoViewAdapter;
import no.joharei.flixr.network.models.Photo;

public class PhotoViewerActivity extends Activity {

    public static final String PHOTOS_NAME = "photos";
    public static final String PHOTO_POSITION = "position";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_viewer);

        Intent intent = getIntent();
        ArrayList<Photo> photos = intent.getParcelableArrayListExtra(PHOTOS_NAME);
        int position = intent.getIntExtra(PHOTO_POSITION, -1);
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        PhotoViewAdapter adapter = new PhotoViewAdapter(photos);
        viewPager.setAdapter(adapter);
        if (position >= 0) {
            viewPager.setCurrentItem(position);
        }
    }
}
