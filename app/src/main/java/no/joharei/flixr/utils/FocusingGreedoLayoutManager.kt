package no.joharei.flixr.utils

import android.support.v7.widget.RecyclerView
import android.view.View
import com.fivehundredpx.greedolayout.GreedoLayoutManager
import com.fivehundredpx.greedolayout.GreedoLayoutSizeCalculator


class FocusingGreedoLayoutManager(
        sizeCalculatorDelegate: GreedoLayoutSizeCalculator.SizeCalculatorDelegate
) : GreedoLayoutManager(sizeCalculatorDelegate) {

    override fun onFocusSearchFailed(focused: View, focusDirection: Int,
                                     recycler: RecyclerView.Recycler, state: RecyclerView.State): View? {
        // Need to be called in order to layout new row/column
        scrollVerticallyBy(when (focusDirection) {
            View.FOCUS_DOWN -> 10
            View.FOCUS_UP -> -10
            else -> 0
        }, recycler, state)

        return super.onFocusSearchFailed(focused, focusDirection, recycler, state)
    }
}