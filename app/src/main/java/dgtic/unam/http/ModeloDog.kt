package dgtic.unam.http

import com.google.gson.annotations.SerializedName

data class ModeloDog (
    @SerializedName( "message") var images : List<String>  )