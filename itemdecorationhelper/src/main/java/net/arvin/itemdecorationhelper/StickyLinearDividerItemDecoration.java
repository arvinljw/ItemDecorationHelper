package net.arvin.itemdecorationhelper;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by arvinljw on 2018/7/24 14:45
 * Function：
 * Desc：线性粘性头部，水平垂直都可以
 */
public class StickyLinearDividerItemDecoration extends BaseStickyDividerItemDecoration {

    StickyLinearDividerItemDecoration(ItemDecorationFactory.StickyDividerBuilder builder) {
        super(builder);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        ItemDecorationHelper.getStickyLinearItemOffset(outRect, parent, view, stickyDividerHelper);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        ItemDecorationHelper.onLinearDraw(c, parent, stickyDividerHelper);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        ItemDecorationHelper.onStickyLinearDrawOver(c, parent, stickyDividerHelper);
    }

}
