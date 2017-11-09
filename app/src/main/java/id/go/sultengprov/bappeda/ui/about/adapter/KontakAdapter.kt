package id.go.sultengprov.bappeda.ui.about.adapter

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.go.sultengprov.bappeda.R
import kotlinx.android.synthetic.main.adapter_kontak.view.*

/**
 * Created by ilham on 11/9/17.
 */
class KontakAdapter(val kontakList: ArrayList<Kontak>) : RecyclerView.Adapter<KontakAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.adapter_kontak, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bind(position)
    }

    override fun getItemCount(): Int = kontakList.size

    inner class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        fun bind(position: Int) {
            val kontak = kontakList[position]
            kontak.icon?.let {
                itemView?.icon?.setImageResource(it)
            }
            itemView?.tvJudul?.text = kontak.judul
            itemView?.tvDeskripsi?.text = kontak.deskripsi
        }
    }
}

data class Kontak(
        var judul: String? = "",
        var deskripsi: String? = "",
        var icon: Int? = 0
)