package net.arvin.itemdecorationhelper;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by arvinljw on 2018/7/24 16:35
 * Function：
 * Desc：
 */
public class StickyGridDividerItemDecoration extends BaseStickyDividerItemDecoration {
    private GridLayoutManager.SpanSizeLookup lookup;

    StickyGridDividerItemDecoration(ItemDecorationFactory.StickyDividerBuilder builder) {
        super(builder);
    }

    @SuppressWarnings("SuspiciousNameCombination")
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        if (ItemDecorationHelper.getStickyGridItemOffset(outRect, parent, view, stickyDividerHelper)) {
            setSpanSizeLookup(parent);
        }
    }

    private void setSpanSizeLookup(RecyclerView parent) {
        GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();
        final int spanCount = layoutManager.getSpanCount();
        if (lookup == null) {
            lookup = new GridLayoutManager.SpanSizeLookup() {//相当于weight
                @Override
                public int getSpanSize(int position) {
                    GroupData groupData = stickyDividerHelper.getCallback().getGroupData(position);
                    int returnSpan = 1;
                    if (groupData.isLastViewInGroup()) {
                        returnSpan = spanCount - groupData.getPosition() % spanCount;
                    }
                    return returnSpan;
                }
            };
        }
        layoutManager.setSpanSizeLookup(lookup);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        ItemDecorationHelper.onStickyGridDraw(c, parent, stickyDividerHelper);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        ItemDecorationHelper.onStickyGridDrawOver(c, parent, stickyDividerHelper);
    }

}
