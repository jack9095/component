package com.kuanquan.universalcomponents.rx;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.kuanquan.universalcomponents.javaTest.GenericParadigmparent;

public class BaseRxActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GenericParadigmparent bean = new GenericParadigmparent();
    }
}
