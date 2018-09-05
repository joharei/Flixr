package no.joharei.flixr.mainpage

import android.os.Bundle
import no.joharei.flixr.di.DaggerFragmentActivity

class MainActivity : DaggerFragmentActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.beginTransaction().replace(android.R.id.content, MainFragment())
            .commit()
    }
}
