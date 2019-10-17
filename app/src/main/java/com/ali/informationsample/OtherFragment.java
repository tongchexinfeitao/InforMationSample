package com.ali.informationsample;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ali.informationsample.base.BaseFragment;

public class OtherFragment extends BaseFragment {

    private TextView textView;

    @Override
    protected void initView(View inflate) {
        textView = inflate.findViewById(R.id.tv);
    }

    @Override
    protected int provideLayoutId() {
        return R.layout.fragment_other_layout;
    }

    @Override
    protected void initData() {
        // TODO: 2019/10/15  Bundle只能在initData（) 去取，否则空指针
        //获取activity传递的bundle
        Bundle bundle = getArguments();
        //从bundle中获取数据
        String key = bundle.getString("key");
        textView.setText(key);
    }
}
