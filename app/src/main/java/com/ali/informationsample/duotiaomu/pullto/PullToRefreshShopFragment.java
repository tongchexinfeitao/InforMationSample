package com.ali.informationsample.duotiaomu.pullto;

import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ali.informationsample.NetUtil;
import com.ali.informationsample.R;
import com.ali.informationsample.base.BaseFragment;
import com.ali.informationsample.duotiaomu.MyShopAdapter;
import com.ali.informationsample.duotiaomu.ShopBean;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.stx.xhb.xbanner.XBanner;

import java.util.ArrayList;
import java.util.List;

public class PullToRefreshShopFragment extends BaseFragment {

    private int page = 1;
    private boolean isLoadmore;
    //旧数据
    List<ShopBean.DataBean> list = new ArrayList<>();
    private PullToRefreshListView pullToRefreshListView;

    XBanner xBanner = null;

    @Override
    protected void initView(View inflate) {
        pullToRefreshListView = inflate.findViewById(R.id.pull);
        //设置支持下拉刷新和上拉加载更多
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        //设置上下拉监听
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //刷新
                page = 1;
                isLoadmore = false;
                getData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                //加载更多
                page++;
                isLoadmore = true;
                getData();
            }
        });
    }

    private void getData() {
        NetUtil.getInstance().doGet("http://blog.zhaoliang5156.cn/api/shop/fulishe" + page + ".json", new NetUtil.MyCallBack() {
            @Override
            public void onDoGetSuccess(String json) {
                //解析
                final ShopBean shopBean = new Gson().fromJson(json, ShopBean.class);
                List<ShopBean.DataBean> newList = shopBean.getData();
                if (isLoadmore) {
                    //隐身
                    pullToRefreshListView.onRefreshComplete();

                    list.addAll(newList);
                    MyShopAdapter myShopAdapter = new MyShopAdapter(list);
                    pullToRefreshListView.setAdapter(myShopAdapter);
                } else {
                    //隐身
                    pullToRefreshListView.onRefreshComplete();

                    list.clear();
                    list.addAll(newList);
                    MyShopAdapter myShopAdapter = new MyShopAdapter(list);
                    pullToRefreshListView.setAdapter(myShopAdapter);
                }


                if (xBanner == null) {
                    if (shopBean.getBanner() != null) {
                        RelativeLayout relativeLayout = (RelativeLayout) View.inflate(getActivity(),R.layout.header_pulltorefresh_fragment,null);
                        xBanner = relativeLayout.findViewById(R.id.xbanner);
                        xBanner.setBannerData(shopBean.getBanner());
                        xBanner.loadImage(new XBanner.XBannerAdapter() {
                            @Override
                            public void loadBanner(XBanner banner, Object model, View view, int position) {
                                Glide.with(PullToRefreshShopFragment.this).load(shopBean.getBanner().get(position).getImageUrl()).error(R.mipmap.ic_launcher).placeholder(R.mipmap.ic_launcher).into((ImageView) view);
                            }
                        });
                        ListView refreshableView = pullToRefreshListView.getRefreshableView();
                        refreshableView.addHeaderView(xBanner);
                    }
                }

            }

            @Override
            public void onDoGetPhotoSuccess(Bitmap bitmap) {

            }
        });
    }

    @Override
    protected int provideLayoutId() {
        return R.layout.fragment_pull_to_layout;
    }

    @Override
    protected void initData() {
        //联网请求操作放到initData();
        getData();
    }
}
