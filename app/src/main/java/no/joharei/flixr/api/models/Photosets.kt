package no.joharei.flixr.api.models

import com.google.gson.annotations.SerializedName
import java.util.*

data class Photosets(
        @SerializedName("cancreate")
        private val canCreate: Int = 0,
        private val page: Int = 0,
        private val pages: Int = 0,
        @SerializedName("perpage")
        private val perPage: Int = 0,
        private val total: Int = 0,
        @SerializedName("photoset")
        val photosets: List<Photoset> = ArrayList()
)