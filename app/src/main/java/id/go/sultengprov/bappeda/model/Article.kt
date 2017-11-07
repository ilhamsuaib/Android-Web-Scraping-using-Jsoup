package id.go.sultengprov.bappeda.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by ilham on 9/1/17.
 */

@Parcelize
data class Article(
        var url : String? = "",
        var title : String? = "",
        var createdBy : String? = "",
        var createdDate : String? = "",
        var imgUrl : String? = "",
        var totalView : String? = "",
        var contens: MutableList<String>? = ArrayList<String>()) : Parcelable