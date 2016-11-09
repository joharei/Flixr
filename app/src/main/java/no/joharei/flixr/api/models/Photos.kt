package no.joharei.flixr.api.models

import com.google.gson.annotations.SerializedName
import java.util.*

data class Photos(
        val id: String? = null,
        val primary: String? = null,
        val owner: String? = null,
        val ownername: String? = null,
        @SerializedName("photo")
        val photos: List<Photo> = ArrayList(),
        val page: Int = 0,
        val perPage: Int = 0,
        val perpage: Int = 0,
        val pages: Int = 0,
        val total: String? = null,
        val title: String? = null
)