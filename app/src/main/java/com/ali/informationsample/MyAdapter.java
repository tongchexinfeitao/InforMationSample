package com.ali.informationsample;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends BaseAdapter {

    //类型1  不带头像
    private int type1 = 0;
    //类型2  带头像
    private int type2 = 1;

    private List<LawyerBean.ListdataBean> dataBeanList;

    public MyAdapter(List<LawyerBean.ListdataBean> dataBeanList) {
        this.dataBeanList = dataBeanList;

    }

    @Override
    public int getCount() {
        return dataBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    //有多少中类型   type1 type2  共 2种
    @Override
    public int getViewTypeCount() {
        return 2;
    }


    //根据position返回当前条目的类型
    @Override
    public int getItemViewType(int position) {
        //这是当前位置的数据
        LawyerBean.ListdataBean dataBean = dataBeanList.get(position);
        int type = dataBean.getType();
        //根据数据中的类型，返回不同的类型
        if (type == 1) {
            return type1;
        } else if (type == 2) {
            return type2;
        }
        return type1;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            if(type ==type1){
                convertView = View.inflate(parent.getContext(), R.layout.item_lawyer1, null);
                viewHolder.textView = convertView.findViewById(R.id.tv);
            }else if(type ==type2) {
                convertView = View.inflate(parent.getContext(), R.layout.item_lawyer2, null);
                viewHolder.textView = convertView.findViewById(R.id.tv);
                viewHolder.imageView =convertView.findViewById(R.id.iv);
            }
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //绑定数据
        LawyerBean.ListdataBean dataBean = dataBeanList.get(position);

        if(type ==type1) {
            viewHolder.textView.setText(dataBean.getName());
        }else if(type ==type2) {
            viewHolder.textView.setText(dataBean.getName());
            viewHolder.imageView.setImageResource(R.mipmap.ic_launcher_round);
        }
        return convertView;
    }

    class ViewHolder {
        TextView textView;
        ImageView imageView;
    }
}
