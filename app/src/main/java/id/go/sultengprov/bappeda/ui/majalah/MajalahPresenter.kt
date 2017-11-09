package id.go.sultengprov.bappeda.ui.majalah

import android.util.Log.d
import com.google.gson.Gson
import id.go.sultengprov.bappeda.common.BASE_URL
import id.go.sultengprov.bappeda.model.Link
import id.go.sultengprov.bappeda.model.Majalah
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

/**
 * Created by ilham on 11/8/17.
 */

class MajalahPresenter(val view: MajalahView) {

    private val TAG = MajalahPresenter::class.java.simpleName

    fun loadMajalahByYear() {
        view.showProgress()
        val linkList = ArrayList<Link>()
        Flowable.fromCallable {
            val document: Document = Jsoup.connect(BASE_URL).get()
            val elements: Elements = document.select("ul.menu > li.item-128 ul.sub-menu > li")
            for (el in elements) {
                val link = Link()
                link.text = el.select("a").text()
                link.url = el.select("a").attr("href")
                linkList.add(link)
            }
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            loadMajalah(linkList)
                        }, { it.printStackTrace() }
                )
    }

    fun loadMajalah(linkList: ArrayList<Link>) {
        Flowable.fromCallable {
            linkList.forEach {
                val majalahList: MutableList<Majalah> = ArrayList()
                val document: Document = Jsoup.connect("$BASE_URL${it.url}").get()
                val elementList: Elements = document.select("div.item-page p a")
                val elements: List<Element> = elementList.filter { it.toString().contains("img") }
                elements.forEach {
                    val majalah = Majalah()
                    val title = it.attr("title")
                    val majalahUrl = it.attr("href")
                    val imgUrl = it.select("img").attr("src")
                    if ((title.isNotEmpty() && imgUrl.isNotEmpty() && majalahUrl.isNotEmpty())) {
                        majalah.edisi = title
                        majalah.imageUrl = imgUrl
                        majalah.majalahUrl = majalahUrl
                        if (!majalah.imageUrl?.contains("http")!!) { //nambahin base url jika url image gak ada base urlnya
                            majalah.imageUrl = "$BASE_URL${majalah.imageUrl}"
                        }
                        d(TAG, "majalah : ${Gson().toJsonTree(majalah)}")
                        majalahList.add(majalah)
                    }
                }
                it.majalah?.addAll(majalahList)
                d(TAG, "new link : ${Gson().toJsonTree(it)}")
            }
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnEach { view.hideProgress() }
                .subscribe(
                        {
                            view.displayMajalahByYear(linkList)
                        }, { it.printStackTrace() }
                )
    }
}