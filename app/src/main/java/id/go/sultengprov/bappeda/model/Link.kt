package id.go.sultengprov.bappeda.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by ilham on 11/8/17.
 */
@Parcelize
data class Link(
        var text: String? = "",
        var url: String? = "",
        val majalah: ArrayList<Majalah>? = ArrayList()
) : Parcelable