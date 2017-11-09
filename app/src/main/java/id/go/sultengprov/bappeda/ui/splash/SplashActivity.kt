package id.go.sultengprov.bappeda.ui.splash

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import id.go.sultengprov.bappeda.R
import id.go.sultengprov.bappeda.ui.artikel.ArticleActivity
import org.jetbrains.anko.startActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        startActivity<ArticleActivity>()
        finish()
    }
}
