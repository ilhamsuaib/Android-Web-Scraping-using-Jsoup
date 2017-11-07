package id.go.sultengprov.bappeda.ui.article

import id.go.sultengprov.bappeda.model.Article

/**
 * Created by ilham on 9/1/17.
 */
interface ArticleView {

    fun showMessage(message: String)

    fun showProgress()

    fun hideProgress()

    fun onArticleLoaded(articleList: MutableList<Article>, clear: Boolean){}
}