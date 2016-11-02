package no.joharei.flixr.photosets;

import android.app.Activity;
import android.os.Bundle;

import com.f2prateek.dart.HensonNavigable;

import no.joharei.flixr.R;

@HensonNavigable
public class PhotosetsActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photosets);
    }
}
