package net.arvin.itemdecorationhelper;

import android.support.v7.widget.RecyclerView;

/**
 * Created by arvinljw on 2018/7/24 15:17
 * Function：
 * Desc：
 */
public class BaseStickyDividerItemDecoration extends RecyclerView.ItemDecoration {
    ItemDecorationHelper.StickyDividerHelper stickyDividerHelper;

    BaseStickyDividerItemDecoration(ItemDecorationFactory.StickyDividerBuilder builder) {
        stickyDividerHelper = new ItemDecorationHelper.StickyDividerHelper(builder);
    }
}
