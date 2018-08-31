package net.arvin.itemdecorationhelper;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.util.SparseIntArray;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by arvinljw on 2018/7/24 15:17
 * Function：
 * Desc：
 */
public class BaseStickyDividerItemDecoration extends RecyclerView.ItemDecoration implements RecyclerView.OnItemTouchListener {
    private ItemDecorationHelper.StickyDividerHelper stickyDividerHelper;
    private SparseIntArray headersTop;
    private OnHeaderClickListener headerClickListener;
    private StickyHeaderClickGestureDetector gestureDetector;

    BaseStickyDividerItemDecoration(ItemDecorationFactory.StickyDividerBuilder builder) {
        stickyDividerHelper = new ItemDecorationHelper.StickyDividerHelper(builder);
        headersTop = new SparseIntArray();
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        headersTop.clear();
        if (gestureDetector == null) {
            parent.addOnItemTouchListener(this);
            gestureDetector = new StickyHeaderClickGestureDetector(parent.getContext(), headersTop,
                    stickyDividerHelper);
        }
        gestureDetector.setOnHeaderClickListener(headerClickListener);
    }

    public void setOnHeaderClickListener(OnHeaderClickListener headerClickListener) {
        this.headerClickListener = headerClickListener;
    }

    public ItemDecorationHelper.StickyDividerHelper getStickyDividerHelper() {
        return stickyDividerHelper;
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        return gestureDetector.onTouchEvent(e);
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    }
}
