package id.go.sultengprov.bappeda.ui.artikel

import id.go.sultengprov.bappeda.common.BaseView
import id.go.sultengprov.bappeda.model.Article

/**
 * Created by ilham on 9/1/17.
 */
interface ArticleView : BaseView{

    fun showMessage(message: String)

    fun onArticleLoaded(articleList: MutableList<Article>, clear: Boolean){}
}