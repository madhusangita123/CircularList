package learning.kotlin.com.circularlist

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import learning.kotlin.com.circularlist.adapter.CustomAdapter
import learning.kotlin.com.circularlist.customUI.CustomLayoutManager


class MainActivity : AppCompatActivity() {

    private val DATASET_COUNT : Int = 60;
    lateinit var recyclerView: RecyclerView;
    lateinit var mDataset: Array<String>;
    lateinit var layoutManager: CustomLayoutManager;
    lateinit var mAdapter : CustomAdapter;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initDataset();
        recyclerView = findViewById<RecyclerView>(R.id.recyclerView);
        val customScrollingLayoutCallback = CustomScrollingLayoutCallback()
        layoutManager = CustomLayoutManager(this, customScrollingLayoutCallback)
        recyclerView.setLayoutManager(layoutManager)
        mAdapter = CustomAdapter(mDataset);
        // Set CustomAdapter as the adapter for RecyclerView.
        recyclerView.setAdapter(mAdapter);
    }

    /**
     * Generates Strings for RecyclerView's adapter. This data would usually come
     * from a local content provider or remote server.
     */
    private fun initDataset() {
        mDataset = Array<String>(DATASET_COUNT,{i->
            "This is element #$i" ;
        });
    }

    inner class CustomScrollingLayoutCallback : CustomLayoutManager.LayoutCallback() {

        private var mProgressToCenter: Float = 0.toFloat()
        private val MAX_ICON_PROGRESS = 0.65f

        override fun onLayoutFinished(child: View, parent: RecyclerView) {

            // Figure out % progress from top to bottom
            val centerOffset = child.height.toFloat() / 2.0f / parent.height.toFloat()
            val yRelativeToCenterOffset = child.y / parent.height + centerOffset

            // Normalize for center
            mProgressToCenter = Math.abs(0.5f - yRelativeToCenterOffset)
            // Adjust to the maximum scale
            mProgressToCenter = Math.min(mProgressToCenter, MAX_ICON_PROGRESS)

            child.scaleX = 1 - mProgressToCenter
            child.scaleY = 1 - mProgressToCenter
        }
    }
}
