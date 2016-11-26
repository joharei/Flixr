package no.joharei.flixr.error

import android.app.Activity
import android.os.Bundle

class BrowseErrorActivity : Activity() {
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentManager.beginTransaction().add(android.R.id.content, ErrorFragment()).commit()
    }
}
