package no.joharei.flixr.error

import android.os.Bundle
import androidx.fragment.app.FragmentActivity

class BrowseErrorActivity : FragmentActivity() {
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.beginTransaction().replace(android.R.id.content, ErrorFragment()).commit()
    }
}
