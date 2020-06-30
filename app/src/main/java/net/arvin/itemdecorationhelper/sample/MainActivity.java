package net.arvin.itemdecorationhelper.sample;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatRadioButton;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String ORIENTATION = "orientation";
    public static final String HAVING_HEADER = "having_header";

    AppCompatRadioButton rbVertical;
    AppCompatRadioButton rbHeaderHaving;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rbVertical = findViewById(R.id.rb_vertical);
        rbHeaderHaving = findViewById(R.id.rb_having);
        initEvent();
    }

    private void initEvent() {
        findViewById(R.id.btn_linear).setOnClickListener(this);
        findViewById(R.id.btn_grid).setOnClickListener(this);
        findViewById(R.id.btn_staggered).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_linear:
                toLinear();
                break;
            case R.id.btn_grid:
                toGrid();
                break;
            case R.id.btn_staggered:
                toStaggeredGrid();
                break;
        }
    }

    private void toLinear() {
        boolean isVertical = rbVertical.isChecked();
        boolean havingHeader = rbHeaderHaving.isChecked();
        Intent intent = new Intent(this, LinearActivity.class);
        intent.putExtra(ORIENTATION, isVertical);
        intent.putExtra(HAVING_HEADER, havingHeader);
        startActivity(intent);
    }

    private void toGrid() {
        boolean isVertical = rbVertical.isChecked();
        boolean havingHeader = rbHeaderHaving.isChecked();
        if (havingHeader && !isVertical) {
            Toast.makeText(this, "GridLayoutManager暂不支持水平方向带头部的布局", Toast.LENGTH_LONG).show();
            return;
        }
        Intent intent = new Intent(this, GridActivity.class);
        intent.putExtra(ORIENTATION, isVertical);
        intent.putExtra(HAVING_HEADER, havingHeader);
        startActivity(intent);
    }

    private void toStaggeredGrid() {
        boolean isVertical = rbVertical.isChecked();
        boolean havingHeader = rbHeaderHaving.isChecked();
        if (havingHeader) {
            Toast.makeText(this, "StaggeredGridLayoutManager暂不支持带头部的布局", Toast.LENGTH_LONG).show();
            return;
        }
        Intent intent = new Intent(this, StaggeredGridActivity.class);
        intent.putExtra(ORIENTATION, isVertical);
        startActivity(intent);
    }
}
