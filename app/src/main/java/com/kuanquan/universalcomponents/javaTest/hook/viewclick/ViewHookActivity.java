package com.kuanquan.universalcomponents.javaTest.hook.viewclick;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.kuanquan.universalcomponents.R;
import com.kuanquan.universalcomponents.javaTest.hook.openpage.ActivityNavigation;
import com.kuanquan.universalcomponents.javaTest.hook.openpage.INavigation;
import com.kuanquan.universalcomponents.javaTest.hook.openpage.NavigationHandler;
import org.jetbrains.annotations.NotNull;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class ViewHookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_hook);
        Button button = findViewById(R.id.btn);
        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(v -> {
            openPage("OpenPagerActivity",ViewHookActivity.this);
        });

        // 因为代理了 OnClickListener 对象，所以这里每次点击都会触发 myProxyListener 的 onClick().
        hookListener(button);
    }

    private void hookListener(@NotNull View view){
        View.OnClickListener listener = (View.OnClickListener) Proxy.newProxyInstance(view.getClass().getClassLoader(), new Class[]{View.OnClickListener.class},
                new ProxyHandler(new MyProxyListener()));
        view.setOnClickListener(listener);
    }

    private void openPage(String activityName, Context context){
        InvocationHandler handler = new NavigationHandler(new ActivityNavigation());
        ActivityNavigation activityNavigation = new ActivityNavigation();
        Class<? extends ActivityNavigation> aClass = activityNavigation.getClass();
        INavigation iNavigation = (INavigation) Proxy.newProxyInstance(aClass.getClassLoader(), aClass.getInterfaces(), handler);
        iNavigation.openPage(activityName,context);  // 得到接口对象，然后调用相应的方法
    }

}
