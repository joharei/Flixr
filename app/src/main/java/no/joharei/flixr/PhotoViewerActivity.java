package no.joharei.flixr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;

import no.joharei.flixr.adapters.PhotoViewAdapter;
import no.joharei.flixr.network.models.Photo;

public class PhotoViewerActivity extends Activity {

    public static final String PHOTOS_NAME = "photos";
    public static final String PHOTO_POSITION = "position";
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_viewer);

        Intent intent = getIntent();
        ArrayList<Photo> photos = intent.getParcelableArrayListExtra(PHOTOS_NAME);
        final int position = intent.getIntExtra(PHOTO_POSITION, -1);
        viewPager = (ViewPager) findViewById(R.id.pager);
        PhotoViewAdapter adapter = new PhotoViewAdapter(photos);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                fadeOutTitle(position);
            }
        });
        if (position >= 0) {
            viewPager.setCurrentItem(position);
            viewPager.post(new Runnable() {
                @Override
                public void run() {
                    fadeOutTitle(position);
                }
            });
        }
    }

    private void fadeOutTitle(int position) {
        View imageTitle = viewPager.findViewWithTag("imageTitle" + position);
        imageTitle.setAlpha(1);
        imageTitle.animate().setStartDelay(2000).alpha(0).start();
    }
}
