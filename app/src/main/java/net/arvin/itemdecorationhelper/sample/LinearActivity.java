package net.arvin.itemdecorationhelper.sample;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import net.arvin.itemdecorationhelper.BaseStickyDividerItemDecoration;
import net.arvin.itemdecorationhelper.DefaultHeaderCallBack;
import net.arvin.itemdecorationhelper.GroupData;
import net.arvin.itemdecorationhelper.ItemDecorationFactory;
import net.arvin.itemdecorationhelper.OnHeaderClickListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * Created by arvinljw on 2018/7/25 10:27
 * Function：
 * Desc：
 */
public class LinearActivity extends AppCompatActivity {
    protected boolean isVertical;
    protected boolean havingHeader;

    RecyclerView recyclerView;
    protected List<ContactEntity> items = new ArrayList<>();
    private Map<String, Integer> index = new HashMap<>();//字母对应在items中的位置
    private List<String> keys = new ArrayList<>();//字母列表
    private SparseArray<GroupData> dataMap = new SparseArray<>();//每一项对应的分组信息
    private RecyclerView.ItemDecoration itemDecoration;
    private DefaultHeaderCallBack headerCallBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linear);

        isVertical = getIntent().getBooleanExtra(MainActivity.ORIENTATION, false);
        havingHeader = getIntent().getBooleanExtra(MainActivity.HAVING_HEADER, false);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(getLayoutManager());

        items.addAll(getData());
        if (havingHeader) {
            groupInfo();
        }
        final BaseQuickAdapter<ContactEntity, BaseViewHolder> adapter = getAdapter();
        recyclerView.setAdapter(adapter);

        itemDecoration = getItemDecoration();
        recyclerView.addItemDecoration(itemDecoration);

    }

    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(this,
                isVertical ? LinearLayoutManager.VERTICAL : LinearLayoutManager.HORIZONTAL, false);
    }

    private RecyclerView.ItemDecoration getItemDecoration() {
        RecyclerView.ItemDecoration itemDecoration;
        if (havingHeader) {
            headerCallBack = new DefaultHeaderCallBack(this) {
                @Override
                public GroupData getGroupData(int position) {
                    if (items.size() == 0) {
                        return null;
                    }
                    GroupData data = dataMap.get(position);
                    if (data == null) {
                        String letter = items.get(position).getLetter();
                        int i = keys.indexOf(letter);
                        int groupLength;//通过下一个字母的位置减去当前字母的位置，如果是最后一个就使用总共的数量减去当前字母的位置
                        if (i < keys.size() - 1) {
                            String str = keys.get(i + 1);
                            groupLength = index.get(str) - index.get(letter);
                        } else {
                            groupLength = items.size() - index.get(letter);
                        }
                        data = new GroupData(letter)
                                .position(position - index.get(letter))//在分组中的位置，就用实际位置减去当前字母的位置
                                .groupLength(groupLength);
                    }
                    return data;
                }
            };
            itemDecoration = new ItemDecorationFactory.StickyDividerBuilder()
                    .dividerHeight(2)
                    .dividerColor(Color.parseColor("#D8D8D8"))
                    .callback(headerCallBack)
                    .build(recyclerView);
            ((BaseStickyDividerItemDecoration) itemDecoration).setOnHeaderClickListener(new OnHeaderClickListener() {
                @Override
                public void onHeaderClicked(int position) {
                    Log.d("headerClicked", items.get(position).getLetter());
                }
            });
        } else {
            itemDecoration = new ItemDecorationFactory.DividerBuilder()
                    .dividerHeight(2)
                    .dividerColor(Color.parseColor("#D8D8D8"))
                    .showLastDivider(false)
                    .build(recyclerView);
        }
        return itemDecoration;
    }

    protected BaseQuickAdapter<ContactEntity, BaseViewHolder> getAdapter() {
        return new BaseQuickAdapter<ContactEntity, BaseViewHolder>(R.layout.item_text, items) {
            @Override
            protected void convert(BaseViewHolder helper, ContactEntity item) {
                ViewGroup.LayoutParams layoutParams = helper.itemView.getLayoutParams();
                if (!isVertical) {
                    layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                    helper.itemView.setLayoutParams(layoutParams);
                }
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

    protected List<ContactEntity> getData() {
        return TestData.getData();
    }

    private void groupInfo() {
        index = new HashMap<>();
        keys = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            String ch = items.get(i).getLetter();
            if (!index.containsKey(ch)) {
                index.put(ch, i);
                keys.add(ch);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (headerCallBack != null) {
            headerCallBack.onDestroy();
        }
    }
}
