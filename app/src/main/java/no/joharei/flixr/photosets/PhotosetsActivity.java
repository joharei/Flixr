package no.joharei.flixr.photosets;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

public class PhotosetsActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            Intent intent = getIntent();
            String userId = intent.getStringExtra(PhotosetsFragment.USER_ID);
            Fragment fragment = PhotosetsFragment.newInstance(userId);
            getFragmentManager().beginTransaction().add(android.R.id.content, fragment).commit();
        }
    }
}
