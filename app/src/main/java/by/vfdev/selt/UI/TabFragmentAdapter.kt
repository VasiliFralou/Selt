package by.vfdev.selt.UI

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter


class TabFragmentAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> BoardFragment()
            else -> MapFragment()
        }
    }
}