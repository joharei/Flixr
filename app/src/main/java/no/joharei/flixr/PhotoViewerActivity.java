package no.joharei.flixr;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.f2prateek.dart.Dart;
import com.f2prateek.dart.InjectExtra;

import java.util.ArrayList;

import no.joharei.flixr.adapters.PhotoViewAdapter;
import no.joharei.flixr.network.models.Photo;

public class PhotoViewerActivity extends Activity {

    @InjectExtra
    ArrayList<Photo> photos;
    @InjectExtra
    int position;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_viewer);
        Dart.inject(this);

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
            viewPager.post(() -> fadeOutTitle(position));
        }
    }

    private void fadeOutTitle(int position) {
        View imageTitle = viewPager.findViewWithTag("imageTitle" + position);
        if (imageTitle != null) {
            imageTitle.setAlpha(1);
            imageTitle.animate().setStartDelay(2000).alpha(0).start();
        }
    }
}
