package id.go.sultengprov.bappeda.ui.majalah.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import id.go.sultengprov.bappeda.R
import id.go.sultengprov.bappeda.common.openWebsite
import id.go.sultengprov.bappeda.model.Majalah
import kotlinx.android.synthetic.main.adapter_majalah.view.*

/**
 * Created by ilham on 11/8/17.
 */
class MajalahAdapter(val majalahList: ArrayList<Majalah>) : RecyclerView.Adapter<MajalahAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.adapter_majalah, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bind(position)
    }

    override fun getItemCount(): Int = majalahList.size

    inner class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        fun bind(position: Int) {
            val majalah = majalahList[position]
            itemView?.tvTitle?.text = majalah.edisi
            Glide.with(itemView?.context)
                    .load(majalah.imageUrl)
                    .into(itemView?.imgMajalah)
            itemView?.setOnClickListener {
                openWebsite(itemView.context, majalah.majalahUrl)
            }
        }
    }
}