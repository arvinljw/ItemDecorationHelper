package net.arvin.itemdecorationhelper;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by arvinljw on 2018/7/24 09:42
 * Function：
 * Desc：
 */
public class BaseDividerItemDecoration extends RecyclerView.ItemDecoration {
    protected ItemDecorationHelper.DividerHelper dividerHelper;

    BaseDividerItemDecoration(ItemDecorationFactory.DividerBuilder builder) {
        dividerHelper = new ItemDecorationHelper.DividerHelper(builder);
    }

    public ItemDecorationHelper.DividerHelper getDividerHelper() {
        return dividerHelper;
    }
}
