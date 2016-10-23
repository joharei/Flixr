package no.joharei.flixr.photos;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

public class PhotosActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            Intent intent = getIntent();
            long photosetId = intent.getLongExtra(PhotosFragment.PHOTOSET_ID, -1);
            String userId = intent.getStringExtra(PhotosFragment.USER_ID);
            Fragment fragment = PhotosFragment.newInstance(photosetId, userId);
            getFragmentManager().beginTransaction().add(android.R.id.content, fragment).commit();
        }
    }
}
