package id.go.sultengprov.bappeda.ui.majalah

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import id.go.sultengprov.bappeda.R
import id.go.sultengprov.bappeda.model.Link
import id.go.sultengprov.bappeda.ui.majalah.adapter.MajalahAdapter

/**
 * Created by ilham on 11/8/17.
 */
class MajalahFragment : Fragment() {

    private val TAG = MajalahFragment::class.java.simpleName
    private lateinit var adapter: MajalahAdapter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View? = inflater?.inflate(R.layout.fragment_majalah, container, false)

        val link: Link = arguments.getParcelable(MAJALAH)
        d(TAG, "link : ${Gson().toJsonTree(link)}")

        val recMajalah = view?.findViewById<RecyclerView>(R.id.recMajalah)
        val layoutManager = GridLayoutManager(context, 2)
        recMajalah?.layoutManager = layoutManager
        adapter = MajalahAdapter(link.majalah!!)
        recMajalah?.adapter = adapter
        return view
    }

    companion object {
        val POSITION = "position"
        val MAJALAH = "majalah"
        fun newInstance(position: Int, majalah: Link) : MajalahFragment {
            val args = Bundle()
            args.putInt(POSITION, position)
            args.putParcelable(MAJALAH, majalah)
            val majalahFragment = MajalahFragment()
            majalahFragment.arguments = args
            return majalahFragment
        }
    }
}