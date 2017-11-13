package com.mihanjk.fintechhomework

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.mihanjk.fintechhomework.dummy.DummyContent
import kotlinx.android.synthetic.main.activity_node_relations.*

class NodeRelationsActivity : NodeRelationFragment.OnListFragmentInteractionListener,
        AppCompatActivity() {
    companion object {
        const val PARENT_FRAGMENT = "Parent"
        const val CHILD_FRAGMENT = "Child"
    }

    override fun onListFragmentInteraction(item: DummyContent.DummyItem) {
        Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show()
    }

    /**
     * The [android.support.v4.view.PagerAdapter] that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * [android.support.v4.app.FragmentStatePagerAdapter].
     */
    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_node_relations)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        // Set up the ViewPager with the sections adapter.
        container.adapter = mSectionsPagerAdapter

        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))
    }


    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment =
                when (position) {
                    0 -> supportFragmentManager.findFragmentByTag(PARENT_FRAGMENT) ?:
                            NodeRelationFragment.newInstance(1)
                    1 -> supportFragmentManager.findFragmentByTag(CHILD_FRAGMENT) ?:
                            NodeRelationFragment.newInstance(1)
                    else -> throw Exception("Unknown position")
                }

        override fun getCount(): Int {
            return 2
        }
    }
}
