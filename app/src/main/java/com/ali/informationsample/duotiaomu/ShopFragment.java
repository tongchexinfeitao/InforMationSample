package com.ali.informationsample.duotiaomu;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ali.informationsample.NetUtil;
import com.ali.informationsample.R;
import com.ali.informationsample.base.BaseFragment;
import com.google.gson.Gson;
import com.qy.xlistview.XListView;

import java.util.ArrayList;
import java.util.List;

public class ShopFragment extends BaseFragment {

    private XListView xListView;

    //当前要展示的页面
    private int page = 1;
    //是否是加载更多 ，默认不是
    private boolean isLoadMore = false;

    //旧数据
    List<ShopBean.DataBean> list = new ArrayList<>();

    @Override
    protected void initView(View inflate) {
        xListView = inflate.findViewById(R.id.xlv);
        //支持刷新
        xListView.setPullRefreshEnable(true);
        //支持加载更多
        xListView.setPullLoadEnable(true);
        //监听用户下拉上拉
        xListView.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(getActivity(), "刷新", Toast.LENGTH_SHORT).show();
                //去刷新  ，   请求第一页接口、删除旧数据、展示新数据
                isLoadMore = false;
                page = 1;
                //联网
                getData();
            }

            @Override
            public void onLoadMore() {
                Toast.makeText(getActivity(), "加载更多", Toast.LENGTH_SHORT).show();

                //去加载  ，  请求下一页接口、保留旧数据、拼接新数据
                isLoadMore = true;
                page++;
                //联网
                getData();
            }
        });
    }

    @Override
    protected int provideLayoutId() {
        return R.layout.fragment_shop_layout;
    }

    @Override
    protected void initData() {
        //1、联网请求
        getData();
    }


    //刷新 和 加载更多，都调用这个方法
    @SuppressLint("StaticFieldLeak")
    public void getData() {
        NetUtil.getInstance().doGet("http://blog.zhaoliang5156.cn/api/shop/fulishe" + page + ".json", new NetUtil.MyCallBack() {
            @Override
            public void onDoGetSuccess(String json) {
                ShopBean shopBean = new Gson().fromJson(json, ShopBean.class);
                //新数据
                List<ShopBean.DataBean> newList = shopBean.getData();

                if (isLoadMore) {
                    //隐藏进度条
                    xListView.stopLoadMore();
                    //是加载更多、所以要保留旧数据、拼接新数据
                    list.addAll(newList);
                } else {
                    //隐藏进度条
                    xListView.stopRefresh();
                    //是刷新、 清除旧数据，使用新数据
                    list.clear();
                    list.addAll(newList);
                }
                MyShopAdapter myShopAdapter = new MyShopAdapter(list);
                xListView.setAdapter(myShopAdapter);
            }

            @Override
            public void onDoGetPhotoSuccess(Bitmap bitmap) {

            }
        });
    }

}


