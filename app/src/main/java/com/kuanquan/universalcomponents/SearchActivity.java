package com.kuanquan.universalcomponents;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;
import com.base.library.widget.ClearEditText;
import com.base.library.widget.SearchEditText;

/**
 * 搜索
 */
public class SearchActivity extends AppCompatActivity implements SearchEditText.OnSearchClickListener{

    private SearchEditText searchEditText;
    private ClearEditText mClearEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchEditText = (SearchEditText) findViewById(R.id.searchEditText);

        //搜索事件
        searchEditText.setOnSearchClickListener(this);

        mClearEditText = findViewById(R.id.et_earch);
    }

    /**
     * 搜索事件
     */
    @Override
    public void onSearchClick(View view, String keyword) {
        if (!TextUtils.isEmpty(keyword)) {
            //在这里处理逻辑
            Toast.makeText(this, keyword, Toast.LENGTH_SHORT).show();
        }
    }
}