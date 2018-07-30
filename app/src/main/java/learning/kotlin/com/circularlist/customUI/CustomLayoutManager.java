package learning.kotlin.com.circularlist.customUI;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by behurm on 3/19/18.
 */

public class CustomLayoutManager extends LinearLayoutManager {

    @Nullable
    private LayoutCallback mLayoutCallback;

    /**
     * Callback for interacting with layout passes.
     */
    public abstract static class LayoutCallback {
        /**
         * Override this method to implement custom child layout behavior on scroll. It is called
         * at the end of each layout pass of the view (including scrolling) and enables you to
         * modify any property of the child view. Examples include scaling the children based on
         * their distance from the center of the parent, or changing the translation of the children
         * to create an illusion of the path they are moving along.
         *
         * @param child  the current child to be affected.
         * @param parent the {@link RecyclerView} parent that this class is attached to.
         */
        public abstract void onLayoutFinished(View child, RecyclerView parent);
    }

    public CustomLayoutManager(Context context){
        super(context);
    }

    public CustomLayoutManager(Context context, LayoutCallback layoutCallback){
        super(context, VERTICAL, false);
        mLayoutCallback = layoutCallback;
    }
    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
        if (getChildCount() == 0) {
            return;
        }

        updateLayout();
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int scrolled = super.scrollVerticallyBy(dy, recycler, state);

        updateLayout();
        return scrolled;
    }

    private  void updateLayout(){
        if (mLayoutCallback == null) {
            return;
        }
        final int childCount = getChildCount();
        for (int count = 0; count < childCount; count++) {
            View child = getChildAt(count);
            mLayoutCallback.onLayoutFinished(child, (RecyclerView) child.getParent());
        }
    }



}