package no.joharei.flixr.api.models

import com.google.gson.annotations.SerializedName
import java.util.*

data class Photosets(
        @SerializedName("cancreate")
        val canCreate: Int = 0,
        val page: Int = 0,
        val pages: Int = 0,
        @SerializedName("perpage")
        val perPage: Int = 0,
        val total: Int = 0,
        @SerializedName("photoset")
        val photosets: List<Photoset> = ArrayList()
)