package id.go.sultengprov.bappeda.common

/**
 * Created by ilham on 11/7/17.
 */
interface OnItemClickListener {
    fun onItemClick(any: Any?, position: Int?) {}
    fun onItemLongClick(any: Any?, position: Int?) = true
}