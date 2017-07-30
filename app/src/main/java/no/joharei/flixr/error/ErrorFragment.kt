package no.joharei.flixr.error

import android.os.Bundle
import android.support.v4.content.ContextCompat
import no.joharei.flixr.R

class ErrorFragment : android.support.v17.leanback.app.ErrorFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        title = resources.getString(R.string.app_name)

        imageDrawable = ContextCompat.getDrawable(activity, R.drawable.lb_ic_sad_cloud)
        message = resources.getString(R.string.error_fragment_message)
        setDefaultBackground(true)

        buttonText = resources.getString(R.string.dismiss_error)
        setButtonClickListener { activity.finish() }
    }
}
