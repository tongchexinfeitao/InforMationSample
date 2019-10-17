package com.ali.informationsample;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ali.informationsample.base.BaseFragment;
import com.google.gson.Gson;
import com.qy.xlistview.XListView;

import java.util.ArrayList;
import java.util.List;

public class LawyerFragment extends BaseFragment {

    private XListView xListView;

    @Override
    protected void initView(View inflate) {
        xListView = inflate.findViewById(R.id.xlv);
        //让xListview支持下拉刷新
        xListView.setPullRefreshEnable(true);
        //让xListview支持上拉加载更多
        xListView.setPullLoadEnable(true);

        //如何监听用户的手势，监听用户下拉和上拉
        xListView.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                //下拉刷新
                Log.e("TAG", "用户下拉");
                //重新联网请求，联网请求成功之后,隐藏掉进度条
                xListView.stopRefresh();
            }

            @Override
            public void onLoadMore() {
                //上拉加载更多
                //下拉刷新
                Log.e("TAG", "用户上拉加载更多");
                //隐藏掉进度条 隐藏掉进度条
                xListView.stopLoadMore();
            }
        });
    }

    @Override
    protected int provideLayoutId() {
        return R.layout.fragment_lawyer_layout;
    }

    @Override
    protected void initData() {
        NetUtil.getInstance().doGet("http://blog.zhaoliang5156.cn/api/news/lawyer.json", new NetUtil.MyCallBack() {
            @Override
            public void onDoGetSuccess(String json) {
                //主线程 、解析，要设置适配器
                LawyerBean lawyerBean = new Gson().fromJson(json, LawyerBean.class);
                List<LawyerBean.ListdataBean> data = lawyerBean.getListdata();
                MyAdapter myAdapter = new MyAdapter(data);
                xListView.setAdapter(myAdapter);
            }

            @Override
            public void onDoGetPhotoSuccess(Bitmap bitmap) {

            }
        });
    }

}
