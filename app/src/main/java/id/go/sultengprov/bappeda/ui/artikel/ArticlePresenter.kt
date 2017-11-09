package id.go.sultengprov.bappeda.ui.artikel

import android.util.Log.d
import id.go.sultengprov.bappeda.common.ARTICLE_URL
import id.go.sultengprov.bappeda.model.Article
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

/**
 * Created by ilham on 9/1/17.
 */

class ArticlePresenter(val view: ArticleView) {

    private val TAG = ArticlePresenter::class.java.simpleName

    fun loadArticle(page: Int, clear: Boolean) {
        if (page == 0) view.showProgress()
        val articleList = ArrayList<Article>()

        Flowable.fromCallable {
            val document: Document = Jsoup.connect(ARTICLE_URL+page).get()
            val elements: Elements = document.getElementsByClass("item-page-title")
            val createdBys: Elements = document.select("span.createdby")
            for (i in elements.indices) {
                val article = Article()
                article.url = elements[i].select("h2.item-page-title a").first().absUrl("href")
                article.title = elements[i].text()
                article.createdBy = createdBys[i].text()
                articleList.add(article)
            }
            view.onArticleLoaded(articleList, clear)
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnEach { view.hideProgress() }
                .subscribe(
                        { d(TAG, "load article success") },
                        { d(TAG, "error : ${it.localizedMessage}") }
                )
    }
}