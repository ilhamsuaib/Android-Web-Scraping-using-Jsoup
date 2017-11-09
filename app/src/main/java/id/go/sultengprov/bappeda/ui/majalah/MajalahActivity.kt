package id.go.sultengprov.bappeda.ui.majalah

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import id.go.sultengprov.bappeda.R
import id.go.sultengprov.bappeda.common.ViewPagerAdapter
import id.go.sultengprov.bappeda.model.Link
import kotlinx.android.synthetic.main.activity_majalah.*
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.collections.forEachReversedWithIndex

class MajalahActivity : AppCompatActivity(), MajalahView {

    private lateinit var presenter: MajalahPresenter
    private lateinit var pagerAdapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_majalah)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = "Majalah Pembangunan"

        presenter = MajalahPresenter(this)
        pagerAdapter = ViewPagerAdapter(supportFragmentManager)
        presenter.loadMajalahByYear()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showProgress() {
        runOnUiThread { progress.visibility = View.VISIBLE }
    }

    override fun hideProgress() {
        runOnUiThread { progress.visibility = View.GONE }
    }

    override fun displayMajalahByYear(linkList: ArrayList<Link>) {
        tabLayout.visibility = View.VISIBLE
        linkList.forEachReversedWithIndex { i, link ->
            val fragment = MajalahFragment.newInstance(i, link)
            pagerAdapter.addFragment(fragment, link.text!!)
        }
        viewPager.adapter = pagerAdapter
        tabLayout.setupWithViewPager(viewPager)
    }
}
