package id.go.sultengprov.bappeda.ui.article

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.util.Log.d
import android.util.TypedValue
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import id.go.sultengprov.bappeda.R
import id.go.sultengprov.bappeda.model.Article
import kotlinx.android.synthetic.main.activity_detail.*
import org.jetbrains.anko.toast

class DetailActivity : AppCompatActivity(), ArticleView {

    private val TAG = DetailActivity::class.java.simpleName
    private lateinit var presenter: ArticlePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        presenter = ArticlePresenter(this)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = ""

        val article: Article? = intent.getParcelableExtra("article")
        d(TAG, "article : $article")

        Glide.with(this)
                .load(article?.imgUrl)
                .into(imgHeader)
        collapsingToolbar.title = "Detail Artikel"
        collapsingToolbar.setExpandedTitleColor(ContextCompat.getColor(this, android.R.color.transparent))
        tvTitle.text = article?.title
        tvPublished.text = " ${article?.createdDate}"
        tvHits.text = " ${article?.totalView}"
        tvCreatedBy.text = " ${article?.createdBy}"

        val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        layoutParams.setMargins(0, 0, 0, 20);
        article?.contens?.let {
            for (content in it){
                val textView = TextView(this)
                textView.text = content
                textView.setLineSpacing(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5.0f,  getResources().getDisplayMetrics()), 1.0f)
                textView.layoutParams = layoutParams
                layContent.addView(textView)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showMessage(message: String) {
        toast(message)
    }

    override fun showProgress() {
        progress.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progress.visibility = View.GONE
    }
}
