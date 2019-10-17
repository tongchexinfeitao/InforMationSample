package com.ali.informationsample.tab.banner;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.ali.informationsample.NetUtil;
import com.ali.informationsample.R;
import com.ali.informationsample.base.BaseFragment;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.stx.xhb.xbanner.XBanner;
import com.stx.xhb.xbanner.entity.SimpleBannerInfo;

import java.util.ArrayList;
import java.util.List;

public class XBannerFragment extends BaseFragment {

    private XBanner xBanner;

    @Override
    protected void initView(View inflate) {
        xBanner = inflate.findViewById(R.id.xbanner);
    }


    @Override
    protected int provideLayoutId() {
        return R.layout.fragment_xbanner_layout;
    }

    @Override
    protected void initData() {
        getData();


    }

    //请求网络
    private void getData() {
        NetUtil.getInstance().doGet("http://blog.zhaoliang5156.cn/api/shop/fulishe1.json", new NetUtil.MyCallBack() {
            @Override
            public void onDoGetSuccess(String json) {
                XBannerBean xBannerBean = new Gson().fromJson(json, XBannerBean.class);
                final List<XBannerBean.BannerBean> bannerBeans = xBannerBean.getBanner();
                //绑定数据给xbanner
                //绑定数据
                xBanner.setBannerData(bannerBeans);
                //去加载图片
                xBanner.loadImage(new XBanner.XBannerAdapter() {
                    @Override
                    public void loadBanner(XBanner banner, Object model, View view, int position) {
                        //加载图片
                        Glide.with(XBannerFragment.this).load(bannerBeans.get(position).getImageUrl()).into((ImageView) view);
                    }
                });
            }

            @Override
            public void onDoGetPhotoSuccess(Bitmap bitmap) {

            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        xBanner.stopAutoPlay();
    }

    @Override
    public void onResume() {
        super.onResume();
        xBanner.startAutoPlay();
    }
}
