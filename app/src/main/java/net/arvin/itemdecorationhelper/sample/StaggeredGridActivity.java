package net.arvin.itemdecorationhelper.sample;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by arvinljw on 2018/7/25 14:03
 * Function：
 * Desc：
 */
public class StaggeredGridActivity extends LinearActivity {
    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new StaggeredGridLayoutManager(4, isVertical ? LinearLayoutManager.VERTICAL : LinearLayoutManager.HORIZONTAL);
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        return new BaseQuickAdapter<ContactEntity, BaseViewHolder>(R.layout.item_text, items) {
            @Override
            protected void convert(BaseViewHolder helper, ContactEntity item) {
                ViewGroup.LayoutParams layoutParams = helper.itemView.getLayoutParams();
                if (!isVertical) {
                    layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                }
                layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                helper.itemView.setLayoutParams(layoutParams);
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("height", v.getHeight() + "");
                    }
                });
                helper.setText(R.id.tv_text, item.getName());
            }
        };
    }

    @Override
    protected List<ContactEntity> getData() {
        List<ContactEntity> data = new ArrayList<>();
        Random random = new Random(47);
        for (int i = 0; i < 123; i++) {
            int column = i % 4 + 1;
            if (column == 1) {
                data.add(new ContactEntity("item->" + (i + 1) + "--float--" + random.nextFloat()));
            } else if (column == 3) {
                data.add(new ContactEntity("item->" + (i + 1) + "--int--" + random.nextInt()));
            } else {
                data.add(new ContactEntity("item->" + (i + 1)));
            }
        }
        return data;
    }
}
