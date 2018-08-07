package net.arvin.itemdecorationhelper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.WeakHashMap;

/**
 * Created by arvinljw on 2018/7/27 14:13
 * Function：
 * Desc：
 */
public abstract class DefaultHeaderCallBack implements StickyDividerCallback {
    private Context context;
    private WeakHashMap<Integer, View> headerMap;

    public DefaultHeaderCallBack(Context context) {
        this.context = context;
        headerMap = new WeakHashMap<>();
    }

    @Override
    public View getStickyHeaderView(int position) {
        View headerView = headerMap.get(position);
        if (headerView == null) {
            headerView = LayoutInflater.from(context).inflate(R.layout.item_decoration_default_header, null);
            TextView tvHeader = (TextView) headerView.findViewById(R.id.tv_header);
            GroupData groupData = getGroupData(position);
            if (groupData != null) {
                tvHeader.setText(groupData.getTitle());
            }
            headerMap.put(position, headerView);
        }
        return headerView;
    }

    public void onDestroy() {
        context = null;
        headerMap.clear();
    }
}
