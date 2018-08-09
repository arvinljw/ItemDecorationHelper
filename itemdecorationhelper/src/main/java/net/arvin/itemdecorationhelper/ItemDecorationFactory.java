package net.arvin.itemdecorationhelper;

import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 * Created by arvinljw on 2018/7/24 09:36
 * Function：
 * Desc：
 */
public class ItemDecorationFactory {
    private static final int DEFAULT_DIVIDER_HEIGHT = 2;
    private static final int DEFAULT_DIVIDER_COLOR = Color.parseColor("#D8D8D8");
    private static final boolean DEFAULT_SHOW_LAST_DIVIDER = true;

    public static class DividerBuilder {
        private int dividerHeight;
        private int dividerColor;
        private boolean showLastDivider;

        public DividerBuilder() {
            dividerHeight = DEFAULT_DIVIDER_HEIGHT;
            dividerColor = DEFAULT_DIVIDER_COLOR;
            showLastDivider = DEFAULT_SHOW_LAST_DIVIDER;
        }

        public DividerBuilder dividerHeight(int dividerHeight) {
            this.dividerHeight = dividerHeight;
            return this;
        }

        public DividerBuilder dividerColor(@ColorInt int dividerColor) {
            this.dividerColor = dividerColor;
            return this;
        }

        /**
         * 只用于LinearLayoutManager，处理最后一项是否显示分割线
         */
        public DividerBuilder showLastDivider(boolean showLastDivider) {
            this.showLastDivider = showLastDivider;
            return this;
        }

        public int getDividerHeight() {
            return dividerHeight;
        }

        public int getDividerColor() {
            return dividerColor;
        }

        public boolean isShowLastDivider() {
            return showLastDivider;
        }

        public RecyclerView.ItemDecoration build(RecyclerView recyclerView) {
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager instanceof GridLayoutManager) {
                return new GridDividerItemDecoration(this);
            }
            if (layoutManager instanceof LinearLayoutManager) {
                return new LinearDividerItemDecoration(this);
            }
            if (layoutManager instanceof StaggeredGridLayoutManager) {
                return new StaggeredGridDividerItemDecoration(this);
            }
            return null;
        }
    }

    public static class StickyDividerBuilder {
        private int dividerHeight;
        private int dividerColor;
        private boolean showLastDivider;

        private StickyDividerCallback callback;

        public StickyDividerBuilder() {
            dividerHeight = DEFAULT_DIVIDER_HEIGHT;
            dividerColor = DEFAULT_DIVIDER_COLOR;
            showLastDivider = DEFAULT_SHOW_LAST_DIVIDER;
        }

        public StickyDividerBuilder callback(StickyDividerCallback callback) {
            this.callback = callback;
            return this;
        }

        public StickyDividerBuilder dividerHeight(int dividerHeight) {
            this.dividerHeight = dividerHeight;
            return this;
        }

        public StickyDividerBuilder dividerColor(@ColorInt int dividerColor) {
            this.dividerColor = dividerColor;
            return this;
        }

        /**
         * 只用于LinearLayoutManager，处理最后一项是否显示分割线
         */
        public StickyDividerBuilder showLastDivider(boolean showLastDivider) {
            this.showLastDivider = showLastDivider;
            return this;
        }

        public int getDividerHeight() {
            return dividerHeight;
        }

        public int getDividerColor() {
            return dividerColor;
        }

        public boolean isShowLastDivider() {
            return showLastDivider;
        }

        public StickyDividerCallback getCallback() {
            return callback;
        }

        public BaseStickyDividerItemDecoration build(RecyclerView recyclerView) {
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager instanceof GridLayoutManager) {
                return new StickyGridDividerItemDecoration(this);
            }
            if (layoutManager instanceof LinearLayoutManager) {
                return new StickyLinearDividerItemDecoration(this);
            }
            return null;
        }
    }


}
