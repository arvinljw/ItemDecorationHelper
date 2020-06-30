package net.arvin.itemdecorationhelper.sample;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by arvinljw on 2018/7/25 14:03
 * Function：
 * Desc：
 */
public class GridActivity extends LinearActivity {

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new GridLayoutManager(this, 3,
                isVertical ? GridLayoutManager.VERTICAL : GridLayoutManager.HORIZONTAL, false);
    }

}
