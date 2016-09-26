package no.joharei.flixr;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.f2prateek.dart.Dart;
import com.f2prateek.dart.InjectExtra;

public class PhotosetActivity extends Activity {

    @Nullable
    @InjectExtra
    String userId;
    @Nullable
    @InjectExtra
    long photosetId = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Dart.inject(this);

        if (savedInstanceState == null) {
            Fragment fragment;
            if (photosetId >= 0) {
                fragment = PhotosetFragment.newInstance(photosetId, userId);
            } else {
                fragment = PhotosetsFragment.newInstance(userId);
            }
            if (fragment != null) {
                getFragmentManager().beginTransaction().add(android.R.id.content, fragment).commit();
            }
        }
    }

}
