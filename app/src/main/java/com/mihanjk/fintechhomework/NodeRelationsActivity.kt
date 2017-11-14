package com.mihanjk.fintechhomework

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_node_relations.*

class NodeRelationsActivity : NodeRelationFragment.OnListFragmentInteractionListener,
        AppCompatActivity() {

    override fun getParents() =
            Injection.provideUserDataSource(this).getParentsNodes(mNodeKey)

    override fun getChildren() =
            Injection.provideUserDataSource(this).getChildrenNodes(mNodeKey)

    companion object {
        const val PARENT_FRAGMENT = "Parent"
        const val CHILD_FRAGMENT = "Child"
        const val NODE_KEY = "Node"
    }

    private var mNodeKey = 0L

    override fun onListFragmentInteraction(item: Node) {
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

        mNodeKey = intent.extras.getLong(NODE_KEY)

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
                    0 -> NodeRelationFragment.newInstance(PARENT_FRAGMENT)
                    1 -> NodeRelationFragment.newInstance(CHILD_FRAGMENT)
                    else -> throw Exception("Unknown position")
                }

        override fun getCount(): Int {
            return 2
        }
    }
}
