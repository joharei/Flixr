package no.joharei.flixr.photosets

import android.app.Activity
import android.os.Bundle
import com.f2prateek.dart.HensonNavigable

@HensonNavigable
class PhotosetsActivity : Activity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentManager.beginTransaction().add(android.R.id.content, PhotosetsFragment()).commit()
    }
}
