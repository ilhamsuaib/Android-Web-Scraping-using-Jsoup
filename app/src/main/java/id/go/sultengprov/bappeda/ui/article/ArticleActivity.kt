package id.go.sultengprov.bappeda.ui.article

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log.d
import android.view.View
import id.go.sultengprov.bappeda.R
import id.go.sultengprov.bappeda.common.EndlessRecyclerViewScrollListener
import id.go.sultengprov.bappeda.common.OnItemClickListener
import id.go.sultengprov.bappeda.ui.article.adapter.ArticleAdapter
import id.go.sultengprov.bappeda.model.Article
import kotlinx.android.synthetic.main.activity_article.*
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.toast

class ArticleActivity : AppCompatActivity(), ArticleView {

    private val TAG = ArticleActivity::class.java.simpleName

    private lateinit var presenter : ArticlePresenter
    private lateinit var articleAdapter : ArticleAdapter
    private val articles = ArrayList<Article>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)
        presenter = ArticlePresenter(this)

        setSupportActionBar(toolbar)
        title = "Bappeda Sulteng"

        val layoutManager = LinearLayoutManager(this)
        articleAdapter = ArticleAdapter(this, articles, object : OnItemClickListener{
            override fun onItemClick(any: Any?, position: Int?) {
                val article = any as Article?
                startActivity(Intent(this@ArticleActivity, DetailActivity::class.java).apply {
                    putExtra("article", article)
                })
            }
        })
        recArticle.layoutManager = layoutManager
        recArticle.adapter = articleAdapter

        val scrollListener = object: EndlessRecyclerViewScrollListener(layoutManager){
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                d(TAG, "item count : $totalItemsCount")
                presenter.loadArticle(totalItemsCount, false)
            }
        }
        recArticle.addOnScrollListener(scrollListener)

        presenter.loadArticle(0, true)
    }

    override fun showMessage(message: String) {
        runOnUiThread { toast(message) }
    }

    override fun showProgress() {
        runOnUiThread { progressBar.visibility = View.VISIBLE }
    }

    override fun hideProgress() {
        runOnUiThread { progressBar.visibility = View.GONE }
    }

    override fun onArticleLoaded(articleList: MutableList<Article>, clear: Boolean) {
        if (clear) articles.clear()
        articles.addAll(articleList)
        runOnUiThread{
            articleAdapter.notifyItemRangeInserted(articleAdapter.itemCount, articles.size)
        }
    }
}
