package com.kuanquan.universalcomponents.slide;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.base.library.utils.slideswaphelper.PlusItemSlideCallback;
import com.base.library.utils.slideswaphelper.WItemTouchHelperPlus;
import com.kuanquan.universalcomponents.R;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 *
 */
public class StartFActivity extends AppCompatActivity implements RecAdapter.DeletedItemListener{
    RecyclerView recyclerView;
    private RecAdapter recAdapter;

    public static void start(Context context){
        Intent intent = new Intent(context,StartFActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_f);
        initView();
        initData();
    }

    private void initData() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            list.add("Item  " +i);
        }
        recAdapter.setList(list);
    }

    private void initView() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recAdapter = new RecAdapter(this);
        recAdapter.setDelectedItemListener(this);
        recyclerView.setAdapter(recAdapter);
        /*ItemTouchHelperCallback touchHelperCallback = new ItemTouchHelperCallback();
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(touchHelperCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);*/

        //作为一个ItemDecoration 写入的
        PlusItemSlideCallback callback = new PlusItemSlideCallback();
        callback.setType(WItemTouchHelperPlus.SLIDE_ITEM_TYPE_ITEMVIEW);
        WItemTouchHelperPlus extension = new WItemTouchHelperPlus(callback);
        extension.attachToRecyclerView(recyclerView);
    }

    @Override
    public void deleted(int position) {
        recAdapter.removeDataByPosition(position);
    }
}
