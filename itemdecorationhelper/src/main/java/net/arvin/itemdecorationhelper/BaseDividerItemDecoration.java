package net.arvin.itemdecorationhelper;

import android.support.v7.widget.RecyclerView;

/**
 * Created by arvinljw on 2018/7/24 09:42
 * Function：
 * Desc：
 */
public class BaseDividerItemDecoration extends RecyclerView.ItemDecoration {
    ItemDecorationHelper.DividerHelper dividerHelper;

    BaseDividerItemDecoration(ItemDecorationFactory.DividerBuilder builder) {
        dividerHelper = new ItemDecorationHelper.DividerHelper(builder);
    }
}
