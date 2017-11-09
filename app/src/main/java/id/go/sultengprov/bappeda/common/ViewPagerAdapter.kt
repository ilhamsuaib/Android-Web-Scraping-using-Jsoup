package id.go.sultengprov.bappeda.common

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import id.go.sultengprov.bappeda.model.Link

/**
 * Created by ilham on 11/8/17.
 */
class ViewPagerAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm) {

    companion object {
        val KEY = "position"
    }

    val fragmentList = ArrayList<Fragment>()
    val linkList = ArrayList<String>()

    override fun getItem(position: Int): Fragment {
        val fragment = fragmentList[position]
        var bundle: Bundle? = fragment.arguments
        if (bundle == null) {
           bundle = Bundle()
        }
        if (bundle.getInt(KEY, -1) == -1){
            bundle.putInt(KEY, position)
            fragment.arguments = bundle
        }
        return fragment
    }

    override fun getCount(): Int = fragmentList.size

    override fun getPageTitle(position: Int): CharSequence = linkList[position]

    fun addFragment(fragment: Fragment, link: String) {
        fragmentList.add(fragment)
        linkList.add(link)
    }
}