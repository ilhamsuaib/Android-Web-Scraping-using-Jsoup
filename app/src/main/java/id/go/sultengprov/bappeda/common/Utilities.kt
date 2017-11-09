package id.go.sultengprov.bappeda.common

import android.content.Context
import android.content.Intent
import android.net.Uri

/**
 * Created by ilham on 9/1/17.
 */

val BASE_URL = "http://bappeda.sultengprov.go.id"
val ARTICLE_URL = "http://bappeda.sultengprov.go.id/?start="

fun openWebsite(context: Context, url: String?) {
    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
}