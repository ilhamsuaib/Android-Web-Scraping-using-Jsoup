package id.go.sultengprov.bappeda.ui.majalah

import id.go.sultengprov.bappeda.common.BaseView
import id.go.sultengprov.bappeda.model.Link

/**
 * Created by ilham on 11/8/17.
 */
interface MajalahView : BaseView{
    fun displayMajalahByYear(linkList: ArrayList<Link>)
}