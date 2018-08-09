package net.arvin.itemdecorationhelper;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by arvinljw on 2018/7/23 09:39
 * Function：
 * Desc：LinearLayoutManager对应的分割线
 */
public class LinearDividerItemDecoration extends BaseDividerItemDecoration {

    LinearDividerItemDecoration(ItemDecorationFactory.DividerBuilder builder) {
        super(builder);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        ItemDecorationHelper.getLinearItemOffset(outRect, view, parent, dividerHelper);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        ItemDecorationHelper.onLinearDraw(c, parent, dividerHelper);
    }
}
