package com.ali.informationsample.tab;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import com.ali.informationsample.OtherFragment;
import com.ali.informationsample.R;
import com.ali.informationsample.base.BaseActivity;
import com.ali.informationsample.tab.banner.XBannerFragment;


import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends BaseActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private List<Fragment> fragmentList = new ArrayList<>();

    private List<String> titleList = new ArrayList<>();

    @Override
    protected void initData() {
        titleList.add("首页");
        titleList.add("热点");
        titleList.add("我的");
        titleList.add("xbanner");



        //首页
        OtherFragment fragment = new OtherFragment();
        Bundle bundle = new Bundle();
        bundle.putString("key", titleList.get(0));
        fragment.setArguments(bundle);
        fragmentList.add(fragment);

        //热点
        OtherFragment fragment1 = new OtherFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putString("key", titleList.get(1));
        fragment1.setArguments(bundle1);
        fragmentList.add(fragment1);


        //  我的
        OtherFragment fragment2 = new OtherFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putString("key", titleList.get(2));
        fragment2.setArguments(bundle2);
        fragmentList.add(fragment2);

        //推荐Fragment，（该fragment中又嵌套了一个tablayout+Viewpager）
        XBannerFragment tabFragment = new XBannerFragment();
        fragmentList.add(tabFragment);


        viewPager.setAdapter(new MyTabPagerAdapter(getSupportFragmentManager(), fragmentList, titleList));

        //tablayout关联viewpager
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void initView() {
        tabLayout = findViewById(R.id.tlayout);
        viewPager = findViewById(R.id.vp);
    }

    @Override
    protected int provideLayoutId() {
        return R.layout.activity_second2;
    }
}
