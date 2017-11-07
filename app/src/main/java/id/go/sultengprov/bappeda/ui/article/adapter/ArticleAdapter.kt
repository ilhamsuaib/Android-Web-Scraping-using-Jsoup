package id.go.sultengprov.bappeda.ui.article.adapter

import android.support.v7.widget.RecyclerView
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import id.go.sultengprov.bappeda.R
import id.go.sultengprov.bappeda.common.OnItemClickListener
import id.go.sultengprov.bappeda.model.Article
import id.go.sultengprov.bappeda.ui.article.ArticleActivity
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.adapter_article.view.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.util.concurrent.Callable

/**
 * Created by ilham on 9/1/17.
 */
class ArticleAdapter(val activity: ArticleActivity,
                     val articleList: MutableList<Article>,
                     val onItemClickListener: OnItemClickListener) :
        RecyclerView.Adapter<ArticleAdapter.ViewHolder>(){

    val TAG = ArticleAdapter::class.java.simpleName

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent?.context).inflate(R.layout.adapter_article, parent, false);
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = articleList.size

    /*fun getImage(article: Article): Observable<String>{
        return Observable.defer(object : Callable<ObservableSource<String>>{
            override fun call(): ObservableSource<String> {
                var imgUrl = ""
                val document: Document = Jsoup.connect(article.url).get()
                imgUrl = document.select("meta").get(2).attr("content")
                println("img : "+imgUrl)
                return Observable.just(imgUrl)
            }
        })
    }*/

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(position: Int) {
            val article: Article = articleList[position]
            itemView.tvTitle.text = article.title
            var createBy = article.createdBy?: ""
            if (createBy.contains("Ditulis oleh ")) {
                createBy = createBy.replace("Ditulis oleh ", "")
            }
            article.createdBy = createBy
            itemView.tvCreatedBy.text = " $createBy"
            itemView.progress.visibility = View.VISIBLE
            if (article.imgUrl == ""){
                Flowable.fromCallable {
                    val document: Document = Jsoup.connect(article.url).get()
                    val contens: Elements = document.select("div.item-page p")
                    val imgUrl = document.select("meta").get(2).attr("content")

                    var totalView = document.select("span.hits").text()
                    if (totalView.contains("Dilihat: ")) {
                        totalView = totalView.replace("Dilihat: ", "")
                    }
                    var createDate = document.getElementsByClass("published").text()
                    if (createDate.contains("Ditayangkan: ")) {
                        createDate = createDate.replace("Ditayangkan: ", "")
                    }
                    println(totalView)
                    println(createDate)

                    article.totalView = totalView
                    article.imgUrl = imgUrl
                    article.createdDate = createDate
                    var content = ""
                    for (i in contens.indices){
                        article.contens?.add(contens[i].text())
                        content = content + article.contens?.get(i)
                    }
                    articleList.set(position, article)
                    activity.runOnUiThread {
                        Glide.with(activity)
                                .load(imgUrl)
                                .into(itemView.imgArticle)
                        itemView.progress.visibility = View.GONE
                        itemView.tvContent.text = content
                        itemView.tvTotalView.text = " $totalView"
                        itemView.tvCreatedDate.text = " $createDate"
                    }
                }.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnEach { itemView.progress.visibility = View.GONE }
                        .subscribe(
                                { d(TAG, "load image succes") },
                                { d(TAG, "load image error : ${it.localizedMessage}") }
                        )
            }else{
                Glide.with(activity)
                        .load(article.imgUrl)
                        .into(itemView.imgArticle)
                itemView.progress.visibility = View.GONE
            }

            itemView.setOnClickListener { onItemClickListener.onItemClick(article, position) }
        }
    }
}
