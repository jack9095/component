package com.kuanquan.universalcomponents.main;

import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;
import com.base.library.base.BaseViewModelActivity;
import com.base.library.utils.KeyBoard;
import com.base.library.utils.LogUtil;
import com.base.library.widget.ClearEditText;
import com.base.library.widget.SearchEditText;
import com.kuanquan.universalcomponents.R;
import com.kuanquan.universalcomponents.viewmodel.SearchViewModel;

/**
 * 搜索
 */
public class SearchDemoActivity extends BaseViewModelActivity<SearchViewModel> implements SearchEditText.OnSearchClickListener {

    private SearchEditText searchEditText;
    private ClearEditText mClearEditText;

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

    @Override
    protected SearchViewModel createViewModel() {
        return createViewModel(this,SearchViewModel.class);
    }

    @Override
    protected void dataObserver() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public void initView() {
        super.initView();
        searchEditText = findViewById(R.id.searchEditText);

        //搜索事件
        searchEditText.setOnSearchClickListener(this);

        mClearEditText = findViewById(R.id.et_earch);

        // 监听系统软件盘搜索按钮
        mClearEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {//搜索按键action
                    KeyBoard.hintKbTwo(SearchDemoActivity.this, SearchDemoActivity.this.getWindow());
                    String content = mClearEditText.getText().toString();
                    if (TextUtils.isEmpty(content)) {
                        return true;
                    }
                    LogUtil.d("开始搜索");
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    public void onClick(View v) {

    }
}