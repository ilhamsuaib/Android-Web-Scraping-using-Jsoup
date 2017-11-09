package id.go.sultengprov.bappeda.ui.artikel

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log.d
import android.view.MenuItem
import android.view.View
import id.go.sultengprov.bappeda.R
import id.go.sultengprov.bappeda.common.EndlessRecyclerViewScrollListener
import id.go.sultengprov.bappeda.common.OnItemClickListener
import id.go.sultengprov.bappeda.ui.artikel.adapter.ArticleAdapter
import id.go.sultengprov.bappeda.model.Article
import id.go.sultengprov.bappeda.ui.about.AboutActivity
import id.go.sultengprov.bappeda.ui.about.KontakActivity
import id.go.sultengprov.bappeda.ui.majalah.MajalahActivity
import kotlinx.android.synthetic.main.activity_article.*
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class ArticleActivity : AppCompatActivity(), ArticleView {

    private val TAG = ArticleActivity::class.java.simpleName

    private lateinit var presenter : ArticlePresenter
    private lateinit var articleAdapter : ArticleAdapter
    private val articles = ArrayList<Article>()
    private lateinit var drawerToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)
        presenter = ArticlePresenter(this)

        setSupportActionBar(toolbar)
        title = "Bappeda Sulteng".toUpperCase()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

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

        drawerToggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name)
        drawerLayout.addDrawerListener(drawerToggle)
        navView.setNavigationItemSelectedListener (object : NavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.menu_majalah -> {
                        startActivity<MajalahActivity>()
                    }
                    R.id.menu_kontak -> {
                        startActivity<KontakActivity>()
                    }
                    R.id.menu_tentang -> {
                        startActivity<AboutActivity>()
                    }
                }
                drawerLayout.closeDrawers()
                return false
            }
        })

        toolbar.setOnClickListener {
            recArticle.smoothScrollToPosition(0)
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        drawerToggle.syncState()
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
