package com.example.gallery.modes

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class baobei(
    val totalHits: Int = 0,
    val hits: Array<PhotoItem>,
    val total: Int,

    ) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as baobei

        if (totalHits != other.totalHits) return false
        if (!hits.contentEquals(other.hits)) return false
        if (total != other.total) return false

        return true
    }

    override fun hashCode(): Int {
        var result = totalHits
        result = 31 * result + hits.contentHashCode()
        result = 31 * result + total
        return result
    }
}

@Parcelize data class PhotoItem(
    @SerializedName("id")
    val id: Int ,
    @SerializedName("largeImageURL")
    val fullUrl: String,
    @SerializedName("webformatURL")
    val preview: String

):  Parcelable

