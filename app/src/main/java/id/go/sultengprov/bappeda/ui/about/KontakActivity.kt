package id.go.sultengprov.bappeda.ui.about

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import id.go.sultengprov.bappeda.R
import id.go.sultengprov.bappeda.common.openWebsite
import id.go.sultengprov.bappeda.ui.about.adapter.Kontak
import id.go.sultengprov.bappeda.ui.about.adapter.KontakAdapter
import kotlinx.android.synthetic.main.activity_kontak.*
import kotlinx.android.synthetic.main.toolbar.*

class KontakActivity : AppCompatActivity() {

    private lateinit var adapter: KontakAdapter
    private val kotakList = ArrayList<Kontak>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kontak)
        setSupportActionBar(toolbar)
        title = "Kontak Bappeda"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        recKontak.layoutManager = LinearLayoutManager(this)
        adapter = KontakAdapter(kotakList)
        recKontak.adapter = adapter

        setupKontak()
        btnFacebook.setOnClickListener {
            openWebsite(this, "https://www.facebook.com/bappeda.sultengprov/")
        }
    }

    private fun setupKontak() {
        kotakList.add(Kontak("Sekretaris", "Drs. Muh. Ramlan Yunus", R.drawable.ic_user))
        kotakList.add(Kontak("Call", "+620451421844", R.drawable.ic_phone))
        kotakList.add(Kontak("Fax", "+620451421845", R.drawable.ic_fax))
        kotakList.add(Kontak("Email", "bappeda@sulteng.go.id", R.drawable.ic_email))
        kotakList.add(Kontak("Address", "Jl. Prof. Dr. Moh. Yamin, SH No. 7 Palu - Sulawesi Tengah 94112", R.drawable.ic_map_marker))
        kotakList.add(Kontak("Website", "www.bappeda.sultengprov.go.id", R.drawable.ic_web))
        adapter.notifyDataSetChanged()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}
