package net.arvin.itemdecorationhelper;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;

/**
 * Created by arvinljw on 2018/7/24 14:48
 * Function：
 * Desc：
 */
public class ItemDecorationHelper {

    //--LinearItemDecoration -- start
    public static void getLinearItemOffset(Rect outRect, RecyclerView parent, IDivider dividerHelper) {
        if (!(parent.getLayoutManager() instanceof LinearLayoutManager)) {
            return;
        }
        LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();

        if (layoutManager.getOrientation() == LinearLayoutManager.VERTICAL) {
            outRect.set(0, 0, 0, dividerHelper.getDividerHeight());
        } else {
            //noinspection SuspiciousNameCombination
            outRect.set(0, 0, dividerHelper.getDividerHeight(), 0);
        }
    }

    public static void onLinearDraw(Canvas c, RecyclerView parent, IDivider itemDecoration) {
        if (!(parent.getLayoutManager() instanceof LinearLayoutManager)) {
            return;
        }
        LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
        if (layoutManager.getOrientation() == LinearLayoutManager.VERTICAL) {
            drawLinearVertical(c, parent, itemDecoration);
        } else {
            drawLinearHorizontal(c, parent, itemDecoration);
        }
    }

    private static void drawLinearVertical(Canvas c, RecyclerView parent, IDivider dividerHelper) {
        int left, top, right, bottom;
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            left = parent.getLeft();
            top = child.getBottom() + layoutParams.bottomMargin;
            right = parent.getRight();
            bottom = top + dividerHelper.getDividerHeight();
            c.drawRect(left, top, right, bottom, dividerHelper.getDividerPaint());
        }
    }

    private static void drawLinearHorizontal(Canvas c, RecyclerView parent, IDivider dividerHelper) {
        int left, top, right, bottom;
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            left = child.getRight() + layoutParams.rightMargin;
            top = child.getTop();
            right = left + dividerHelper.getDividerHeight();
            bottom = child.getBottom();
            c.drawRect(left, top, right, bottom, dividerHelper.getDividerPaint());
        }
    }
    //--LinearDividerItemDecoration -- end

    //--GridDividerItemDecoration -- start
    public static void getGridItemOffset(Rect outRect, RecyclerView parent, View view, IDivider dividerHelper) {
        if (!(parent.getLayoutManager() instanceof GridLayoutManager)) {
            return;
        }

        GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();
        int position = parent.getChildAdapterPosition(view);
        int spanCount = layoutManager.getSpanCount();
        int column = position % spanCount + 1;//第几列
        int totalCount = parent.getAdapter().getItemCount();

        if (layoutManager.getOrientation() == GridLayoutManager.VERTICAL) {
            outRect.top = 0;
            outRect.bottom = dividerHelper.getDividerHeight();
            outRect.left = (column - 1) * dividerHelper.getDividerHeight() / spanCount; //左侧为(当前条目数-1)/总条目数*divider宽度
            outRect.right = (spanCount - column) * dividerHelper.getDividerHeight() / spanCount;//右侧为(总条目数-当前条目数)/总条目数*divider宽度
        } else {
            column = position / spanCount + 1;//第几列
            int totalColumn = totalCount / spanCount + (totalCount % spanCount == 0 ? 0 : 1);//总列数
            outRect.top = 0;
            outRect.bottom = dividerHelper.getDividerHeight();
            outRect.left = (column - 1) * dividerHelper.getDividerHeight() / totalColumn; //左侧为(当前条目数-1)/总条目数*divider宽度
            outRect.right = (totalColumn - column) * dividerHelper.getDividerHeight() / totalColumn;//右侧为(总条目数-当前条目数)/总条目数*divider宽度
        }
    }

    public static void onGridDraw(Canvas c, RecyclerView parent, IDivider dividerHelper) {
        if (!(parent.getLayoutManager() instanceof GridLayoutManager)) {
            return;
        }

        GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();
        if (layoutManager.getOrientation() == GridLayoutManager.VERTICAL) {
            drawGridVertical(c, parent, dividerHelper);
        } else {
            drawGridHorizontal(c, parent, dividerHelper);
        }
    }

    private static void drawGridVertical(Canvas c, RecyclerView parent, IDivider dividerHelper) {
        GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();
        float left, top, right, bottom;
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            int spanCount = layoutManager.getSpanCount();
            int position = parent.getChildAdapterPosition(child);
            int column = position % spanCount + 1;//第几列

            //绘制下边
            left = child.getLeft() - layoutParams.leftMargin;
            right = child.getRight() + layoutParams.rightMargin + dividerHelper.getDividerHeight();
            top = child.getBottom() + layoutParams.bottomMargin;
            bottom = top + dividerHelper.getDividerHeight();
            c.drawRect(left, top, right, bottom, dividerHelper.getDividerPaint());

            //绘制左边，第一列的都不绘制左边
            int dividerLeft = (column - 1) * dividerHelper.getDividerHeight() / spanCount;
            right = child.getLeft() - layoutParams.leftMargin;
            left = right - dividerLeft;
            top = child.getTop() - layoutParams.topMargin;
            bottom = child.getBottom() + layoutParams.bottomMargin;
            c.drawRect(left, top, right, bottom, dividerHelper.getDividerPaint());

            //绘制右边，最后一列都不绘制右边
            left = child.getRight() + layoutParams.rightMargin;
            right = left + (spanCount - column) * dividerHelper.getDividerHeight() / spanCount;
            top = child.getTop() - layoutParams.topMargin;
            bottom = child.getBottom() + layoutParams.bottomMargin;
            if (position == parent.getAdapter().getItemCount() - 1 && spanCount != column) {
                right = left + dividerHelper.getDividerHeight();
            }
            c.drawRect(left, top, right, bottom, dividerHelper.getDividerPaint());
        }
    }

    private static void drawGridHorizontal(Canvas c, RecyclerView parent, IDivider dividerHelper) {
        GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();
        float left, top, right, bottom;
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            int spanCount = layoutManager.getSpanCount();
            int position = parent.getChildAdapterPosition(child);
            int column = position / spanCount + 1;//第几列
            int totalCount = parent.getAdapter().getItemCount();
            int totalColumn = totalCount / spanCount + (totalCount % spanCount == 0 ? 0 : 1);//总列数

            //绘制下边
            left = child.getLeft() - layoutParams.leftMargin;
            right = child.getRight() + layoutParams.rightMargin + dividerHelper.getDividerHeight();
            top = child.getBottom() + layoutParams.bottomMargin;
            bottom = top + dividerHelper.getDividerHeight();
            if (column != 1) {
                left = left - (column - 1) * dividerHelper.getDividerHeight() / totalColumn;//避免view被重用是，左边被回收
            }
            c.drawRect(left, top, right, bottom, dividerHelper.getDividerPaint());

            //绘制左边，第一列不画
            right = child.getLeft() - layoutParams.leftMargin;
            left = right - (column - 1) * dividerHelper.getDividerHeight() / totalColumn;
            top = child.getTop() - layoutParams.topMargin;
            bottom = child.getBottom() + layoutParams.bottomMargin;
            c.drawRect(left, top, right, bottom, dividerHelper.getDividerPaint());

            //绘制右边，最后一列不画
            left = child.getRight() + layoutParams.rightMargin;
            right = left + (totalColumn - column) * dividerHelper.getDividerHeight() / totalColumn;
            top = child.getTop() - layoutParams.topMargin;
            bottom = child.getBottom() + layoutParams.bottomMargin;
            if (column == totalColumn - 1 && position + spanCount > totalCount - 1) {
                right = left + dividerHelper.getDividerHeight();
            }
            c.drawRect(left, top, right, bottom, dividerHelper.getDividerPaint());
        }
    }
    //--GridDividerItemDecoration -- end

    //--StaggeredDividerItemDecoration -- start
    public static void getStaggeredItemOffset(Rect outRect, RecyclerView parent, View view, IDivider dividerHelper) {
        if (!(parent.getLayoutManager() instanceof StaggeredGridLayoutManager)) {
            return;
        }

        StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) parent.getLayoutManager();
        int spanCount = layoutManager.getSpanCount();

        if (layoutManager.getOrientation() == GridLayoutManager.VERTICAL) {
            StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
            outRect.top = 0;
            outRect.bottom = dividerHelper.getDividerHeight();
            outRect.left = 0;
            outRect.right = (layoutParams.getSpanIndex() + 1) % spanCount == 0 ? 0 : dividerHelper.getDividerHeight();
        } else {
            int position = parent.getChildAdapterPosition(view);
            int totalCount = parent.getAdapter().getItemCount();
            int column = position / spanCount + 1;
            int totalColumn = totalCount / spanCount + (totalCount % spanCount == 0 ? 0 : 1);
            outRect.top = 0;
            outRect.bottom = dividerHelper.getDividerHeight();
            outRect.left = 0;
            outRect.right = column == totalColumn ? 0 : dividerHelper.getDividerHeight();
        }
    }

    public static void onStaggeredDraw(RecyclerView parent, IDivider dividerHelper) {
        if (!(parent.getLayoutManager() instanceof StaggeredGridLayoutManager)) {
            return;
        }
        parent.setBackgroundColor(dividerHelper.getDividerColor());
    }

    //--StaggeredDividerItemDecoration -- end

    //--StickyLinearDividerItemDecoration -- start
    @SuppressWarnings("SuspiciousNameCombination")
    public static void getStickyLinearItemOffset(Rect outRect, RecyclerView parent, View view, IStickyHeader stickyDividerHelper) {
        if (!(parent.getLayoutManager() instanceof LinearLayoutManager)) {
            return;
        }

        getLinearItemOffset(outRect, parent, stickyDividerHelper);

        StickyDividerCallback callback = stickyDividerHelper.getCallback();
        if (callback == null) {
            return;
        }
        int position = parent.getChildAdapterPosition(view);
        GroupData groupInfo = callback.getGroupData(position);
        if (groupInfo == null) {
            return;
        }

        LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
        boolean isVertical = layoutManager.getOrientation() == LinearLayoutManager.VERTICAL;

        if (groupInfo.isFirstViewInGroup()) {//如果是分组的第一个，那么外边框的上边界要往上移
            View sectionView = callback.getStickyHeaderView(position);
            sectionView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

            if (isVertical) {
                stickyDividerHelper.setHeaderHeight(sectionView.getMeasuredHeight());
                outRect.top = stickyDividerHelper.getHeaderHeight();
            } else {
                stickyDividerHelper.setHeaderHeight(sectionView.getMeasuredWidth());
                outRect.left = stickyDividerHelper.getHeaderHeight();
            }

            if (groupInfo.isLastViewInGroup()) {//如果同时还是最后一个，把外边框的下边界设为0，免得有divider
                if (isVertical) {
                    outRect.bottom = 0;
                } else {
                    outRect.right = 0;
                }
            }
        } else if (groupInfo.isLastViewInGroup()) {//如果只是分组最后一个，那么上下边界都设为0，即无header也无divider
            if (isVertical) {
                outRect.top = 0;
                outRect.bottom = 0;
            } else {
                outRect.left = 0;
                outRect.right = 0;
            }
        } else {//既不是分组的第一个也不是最后一个，那么就只有divider
            if (isVertical) {
                outRect.top = 0;
            } else {
                outRect.left = 0;
            }
        }
    }

    public static void onStickyLinearDrawOver(Canvas c, RecyclerView parent, IStickyHeader stickyDividerHelper, SparseIntArray headerTop) {
        if (!(parent.getLayoutManager() instanceof LinearLayoutManager)) {
            return;
        }
        StickyDividerCallback callback = stickyDividerHelper.getCallback();
        if (callback == null) {
            return;
        }
        //因为RecyclerView是复用item的，所以这个数量就是屏幕内能显示出来的item的数量
        int childCount = parent.getChildCount();
        LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
        boolean isVertical = layoutManager.getOrientation() == LinearLayoutManager.VERTICAL;
        for (int i = 0; i < childCount; i++) {
            if (isVertical) {
                drawStickyLinearHeaderVertical(c, parent, i, stickyDividerHelper, headerTop);
            } else {
                drawStickyLinearHeaderHorizontal(c, parent, i, stickyDividerHelper, headerTop);
            }
        }
    }

    /**
     * @param index 在屏幕中的位置
     */
    private static void drawStickyLinearHeaderVertical(Canvas c, RecyclerView parent, int index, IStickyHeader stickyDividerHelper, SparseIntArray headerTop) {
        StickyDividerCallback callback = stickyDividerHelper.getCallback();
        if (callback == null) {
            return;
        }
        View view = parent.getChildAt(index);
        int position = parent.getChildAdapterPosition(view);
        GroupData groupData = callback.getGroupData(position);

        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        int top;
        int bottom;

        if (index != 0) {
            if (groupData.isFirstViewInGroup()) {//如果是分组的第一个
                top = view.getTop() - stickyDividerHelper.getHeaderHeight();
                bottom = view.getTop();
            } else {
                //不是屏幕的第一个并且不是分组的第一个就不需要绘制header
                return;
            }
        } else {
            top = parent.getPaddingTop();//如果是屏幕中的第一个，就应该在父容器的顶部
            if (groupData.isLastViewInGroup()) {//如果这时候它又是这个分组的最后一个，他就会被下一个分组顶上去
                int realTop = view.getBottom() - stickyDividerHelper.getHeaderHeight();
                if (realTop <= top) {
                    top = realTop;
                }
            }
            bottom = top + stickyDividerHelper.getHeaderHeight();
        }

        View headerView = callback.getStickyHeaderView(position);
        headerView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        headerView.setDrawingCacheEnabled(true);
        headerView.layout(left, top, right, bottom);
        c.drawBitmap(headerView.getDrawingCache(), left, top, null);
        headerTop.put(position, top);
    }

    /**
     * @param index 在屏幕中的位置
     */
    private static void drawStickyLinearHeaderHorizontal(Canvas c, RecyclerView parent, int index, IStickyHeader stickyDividerHelper, SparseIntArray headerTop) {
        StickyDividerCallback callback = stickyDividerHelper.getCallback();
        if (callback == null) {
            return;
        }
        View view = parent.getChildAt(index);
        int position = parent.getChildAdapterPosition(view);
        GroupData groupData = callback.getGroupData(position);

        int top = parent.getPaddingTop();
        int bottom = parent.getHeight() - parent.getPaddingBottom();
        int left;
        int right;

        if (index != 0) {
            if (groupData.isFirstViewInGroup()) {//如果是分组的第一个
                left = view.getLeft() - stickyDividerHelper.getHeaderHeight();
                right = view.getLeft();
            } else {
                //不是屏幕的第一个并且不是分组的第一个就不需要绘制header
                return;
            }
        } else {
            left = parent.getPaddingLeft();//如果是屏幕中的第一个，就应该在父容器的顶部
            if (groupData.isLastViewInGroup()) {//如果这时候它又是这个分组的最后一个，他就会被下一个分组顶上去
                int realLeft = view.getRight() - stickyDividerHelper.getHeaderHeight();
                if (realLeft <= left) {
                    left = realLeft;
                }
            }
            right = left + stickyDividerHelper.getHeaderHeight();
        }

        View headerView = callback.getStickyHeaderView(position);
        headerView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        headerView.setDrawingCacheEnabled(true);
        headerView.layout(left, top, right, bottom);
        c.drawBitmap(headerView.getDrawingCache(), left, top, null);
        headerTop.put(position, top);
    }
    //--StickyLinearDividerItemDecoration -- end

    //--StickyGridDividerItemDecoration -- start
    @SuppressWarnings("SuspiciousNameCombination")
    public static boolean getStickyGridItemOffset(Rect outRect, RecyclerView parent, View view, IStickyHeader stickyDividerHelper) {
        if (checkStickyHeader(parent, stickyDividerHelper)) {
            return false;
        }
        GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();
        final int spanCount = layoutManager.getSpanCount();
        int position = parent.getChildAdapterPosition(view);
        GroupData data = stickyDividerHelper.getCallback().getGroupData(position);
        if (data == null) {
            return false;
        }
        //设置分割线
        int column = data.getPosition() % spanCount + 1;//第几列
        outRect.top = 0;
        outRect.bottom = stickyDividerHelper.getDividerHeight();
        outRect.left = (column - 1) * stickyDividerHelper.getDividerHeight() / spanCount; //左侧为(当前条目数-1)/总条目数*divider宽度
        outRect.right = (spanCount - column) * stickyDividerHelper.getDividerHeight() / spanCount;//右侧为(总条目数-当前条目数)/总条目数*divider宽度
        if (data.getPosition() == data.getGroupLength() - 1) {
            outRect.right = 0;
        }

        //添加header的偏移，并去掉某些不需要的分割线
        if (data.isFirstLineInGroup(spanCount)) {//如果是分组的第一个，那么外边框的上边界要往上移
            View sectionView = stickyDividerHelper.getCallback().getStickyHeaderView(position);
            sectionView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            stickyDividerHelper.setHeaderHeight(sectionView.getMeasuredHeight());
            outRect.top = stickyDividerHelper.getHeaderHeight();

            if (data.isLastLineInGroup(spanCount)) {//如果同时还是最后一个，把外边框的下边界设为0，免得有divider
                outRect.bottom = 0;
            }
        } else if (data.isLastLineInGroup(spanCount)) {//如果只是分组最后一个，那么上下边界都设为0，即无header也无divider
            outRect.top = 0;
            outRect.bottom = 0;
        } else {//既不是分组的第一个也不是最后一个，那么就只有divider
            outRect.top = 0;
        }
        return true;
    }

    public static void onStickyGridDraw(Canvas c, RecyclerView parent, IStickyHeader stickyDividerHelper) {
        if (checkStickyHeader(parent, stickyDividerHelper)) {
            return;
        }
        GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();
        StickyDividerCallback callback = stickyDividerHelper.getCallback();

        float left, top, right, bottom;
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            int spanCount = layoutManager.getSpanCount();
            int position = parent.getChildAdapterPosition(child);
            GroupData data = callback.getGroupData(position);
            int column = data.getPosition() % spanCount + 1;//第几列

            //绘制下边
            left = child.getLeft() - layoutParams.leftMargin;
            right = child.getRight() + layoutParams.rightMargin + stickyDividerHelper.getDividerHeight();
            top = child.getBottom() + layoutParams.bottomMargin;
            bottom = top + stickyDividerHelper.getDividerHeight();
            c.drawRect(left, top, right, bottom, stickyDividerHelper.getDividerPaint());

            //绘制左边，第一列的都不绘制左边
            int dividerLeft = (column - 1) * stickyDividerHelper.getDividerHeight() / spanCount;
            right = child.getLeft() - layoutParams.leftMargin;
            left = right - dividerLeft;
            top = child.getTop() - layoutParams.topMargin;
            bottom = child.getBottom() + layoutParams.bottomMargin;
            c.drawRect(left, top, right, bottom, stickyDividerHelper.getDividerPaint());

            //绘制右边，最后一列都不绘制右边
            left = child.getRight() + layoutParams.rightMargin;
            right = left + (spanCount - column) * stickyDividerHelper.getDividerHeight() / spanCount;
            top = child.getTop() - layoutParams.topMargin;
            bottom = child.getBottom() + layoutParams.bottomMargin;
            if (position == parent.getAdapter().getItemCount() - 1 && spanCount != column) {
                right = left + stickyDividerHelper.getDividerHeight();
            }
            c.drawRect(left, top, right, bottom, stickyDividerHelper.getDividerPaint());
        }
    }

    public static void onStickyGridDrawOver(Canvas c, RecyclerView parent, IStickyHeader stickyDividerHelper, SparseIntArray headersTop) {
        if (checkStickyHeader(parent, stickyDividerHelper)) {
            return;
        }

        int childCount = parent.getChildCount();
        for (int index = 0; index < childCount; index++) {
            GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();
            StickyDividerCallback callback = stickyDividerHelper.getCallback();
            View view = parent.getChildAt(index);
            int realPos = parent.getChildAdapterPosition(view);
            GroupData data = callback.getGroupData(realPos);

            final int spanCount = layoutManager.getSpanCount();

            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();
            int top;
            int bottom;

            if (index != 0) {
                if (data.isFirstLineInGroup(spanCount) && index >= spanCount) {//如果是分组的第一个
                    top = view.getTop() - stickyDividerHelper.getHeaderHeight();
                    bottom = view.getTop();
                } else {
                    //不是屏幕的第一个并且不是分组的第一个就不需要绘制header
                    continue;
                }
            } else {
                top = parent.getPaddingTop();//如果是屏幕中的第一个，就应该在父容器的顶部
                if (data.isLastLineInGroup(spanCount)) {//如果这时候它又是这个分组的最后一个，他就会被下一个分组顶上去
                    int realTop = view.getBottom() - stickyDividerHelper.getHeaderHeight();
                    if (realTop <= top) {
                        top = realTop;
                    }
                }
                bottom = top + stickyDividerHelper.getHeaderHeight();
            }

            View sectionView = callback.getStickyHeaderView(realPos);
            sectionView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

            sectionView.setDrawingCacheEnabled(true);
            sectionView.layout(left, top, right, bottom);
            c.drawBitmap(sectionView.getDrawingCache(), left, top, null);
            headersTop.put(realPos, top);
        }
    }

    /**
     * 不符合数据要求则返回true，就不处理，反之亦然
     */
    private static boolean checkStickyHeader(RecyclerView parent, IStickyHeader stickyDividerHelper) {
        if (!(parent.getLayoutManager() instanceof GridLayoutManager)) {
            return true;
        }
        GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();
        if (layoutManager.getOrientation() == GridLayoutManager.HORIZONTAL) {
            return true;
        }
        StickyDividerCallback callback = stickyDividerHelper.getCallback();
        return callback == null;
    }
    //--StickyGridDividerItemDecoration -- end

    public static class DividerHelper implements IDivider {
        private Paint dividerPaint;
        private int dividerHeight;
        private int dividerColor;

        DividerHelper(ItemDecorationFactory.DividerBuilder builder) {
            this.dividerHeight = builder.getDividerHeight();
            this.dividerColor = builder.getDividerColor();

            initPaint();
        }

        private void initPaint() {
            dividerPaint = new Paint();
            dividerPaint.setAntiAlias(true);
            dividerPaint.setColor(dividerColor);
        }

        @Override
        public Paint getDividerPaint() {
            return dividerPaint;
        }

        @Override
        public int getDividerHeight() {
            return dividerHeight;
        }

        @Override
        public int getDividerColor() {
            return dividerColor;
        }
    }

    public static class StickyDividerHelper implements IStickyHeader {
        private Paint dividerPaint;
        private int dividerHeight;
        private int dividerColor;

        private StickyDividerCallback callback;
        private int headerHeight;

        StickyDividerHelper(ItemDecorationFactory.StickyDividerBuilder builder) {
            this.callback = builder.getCallback();
            this.dividerHeight = builder.getDividerHeight();
            this.dividerColor = builder.getDividerColor();

            initPaint();
        }

        private void initPaint() {
            dividerPaint = new Paint();
            dividerPaint.setAntiAlias(true);
            dividerPaint.setColor(dividerColor);
        }

        @Override
        public Paint getDividerPaint() {
            return dividerPaint;
        }

        @Override
        public int getDividerHeight() {
            return dividerHeight;
        }

        @Override
        public void setHeaderHeight(int headerHeight) {
            this.headerHeight = headerHeight;
        }

        @Override
        public int getDividerColor() {
            return dividerColor;
        }

        public int getHeaderHeight() {
            return headerHeight;
        }

        @Override
        public StickyDividerCallback getCallback() {
            return callback;
        }
    }

    public interface IDivider {
        Paint getDividerPaint();

        int getDividerHeight();

        int getDividerColor();
    }

    public interface IStickyHeader extends IDivider {
        StickyDividerCallback getCallback();

        int getHeaderHeight();

        void setHeaderHeight(int headerHeight);
    }
}
