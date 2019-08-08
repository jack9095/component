package com.example.mycustomkeyboard;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class Main2Activity extends AppCompatActivity implements View.OnTouchListener {

    private EditText et;
//    private EditText et1;
//    private EditText et2;
//    private EditText et3;
//    private EditText et4;
    private MyKeyBoardView keyBoardView;
    private LinearLayout root;
    private RelativeLayout keyboardRoot;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        et = (EditText) findViewById(R.id.et);
//        et1 = (EditText) findViewById(R.id.et1);
//        et2 = (EditText) findViewById(R.id.et2);
//        et3 = (EditText) findViewById(R.id.et3);
//        et4 = (EditText) findViewById(R.id.et4);
        root = (LinearLayout) findViewById(R.id.root);

        keyBoardView = (MyKeyBoardView) findViewById(R.id.mykeyboard);
        keyboardRoot = (RelativeLayout) findViewById(R.id.mykeyboard_root);
        et.setOnTouchListener(new View.OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    keyBoardView.setAttachToEditText((EditText) v, root, keyboardRoot);
                }
                return true;
            }
        });
//        et1.setOnTouchListener(this);
//        et2.setOnTouchListener(this);
//        et3.setOnTouchListener(this);
//        et4.setOnTouchListener(this);

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            keyBoardView.setAttachToEditText((EditText) v, root, keyboardRoot);
        }
        return true;
    }
}
