package no.joharei.flixr.mainpage

import android.app.Activity
import android.os.Bundle

class MainActivity : Activity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fragmentManager.beginTransaction().add(android.R.id.content, MainFragment()).commit()
    }
}
