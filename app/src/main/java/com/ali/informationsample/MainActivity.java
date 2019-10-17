package com.ali.informationsample;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.widget.RadioGroup;

import com.ali.informationsample.base.BaseActivity;
import com.ali.informationsample.duotiaomu.ShopFragment;
import com.ali.informationsample.duotiaomu.pullto.PullToRefreshShopFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    List<Fragment> fragments = new ArrayList<>();
    private ViewPager viewpager;
    private RadioGroup radioGroup;

    @Override
    protected void initData() {
        LawyerFragment lawyerFragment = new LawyerFragment();
        ShopFragment shopFragment = new ShopFragment();
        PullToRefreshShopFragment pullToRefreshFragment =new PullToRefreshShopFragment();

        //发现
        OtherFragment findFagment = new OtherFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putString("key", "发现");
        findFagment.setArguments(bundle1);

        //我的
        OtherFragment myFagment = new OtherFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putString("key", "我的");
        myFagment.setArguments(bundle2);

        //添加到集合
        fragments.add(lawyerFragment);
//        fragments.add(shopFragment);
        fragments.add(pullToRefreshFragment);
        fragments.add(findFagment);
        fragments.add(myFagment);
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), fragments);
        viewpager.setAdapter(myPagerAdapter);
    }

    @Override
    protected void initView() {
        viewpager = findViewById(R.id.vp);
        radioGroup = findViewById(R.id.rg);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                switch (i) {
                    case 0:
                        radioGroup.check(R.id.rbtn_laywer);
                        break;
                    case 1:
                        radioGroup.check(R.id.rbtn_message);
                        break;
                    case 2:
                        radioGroup.check(R.id.rbtn_find);
                        break;
                    case 3:
                        radioGroup.check(R.id.rbtn_my);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        //radiogroup的选中监听
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbtn_laywer:
                        viewpager.setCurrentItem(0);
                        break;
                    case R.id.rbtn_message:
                        viewpager.setCurrentItem(1);
                        break;
                    case R.id.rbtn_find:
                        viewpager.setCurrentItem(2);
                        break;
                    case R.id.rbtn_my:
                        viewpager.setCurrentItem(3);
                        break;
                }
            }
        });
    }

    @Override
    protected int provideLayoutId() {
        return R.layout.activity_main;
    }
}
