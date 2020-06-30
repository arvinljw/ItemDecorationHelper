package net.arvin.itemdecorationhelper;

import android.view.View;

/**
 * Created by arvinljw on 2018/7/24 15:28
 * Function：
 * Desc：
 */
public interface StickyDividerCallback {

    GroupData getGroupData(int position);

    View getStickyHeaderView(int position);
}
