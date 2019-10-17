package com.ali.informationsample.duotiaomu;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ali.informationsample.R;

import java.util.List;

public class MyShopAdapter extends BaseAdapter {
    private List<ShopBean.DataBean> data;
    //1、声明条目类型
    private int xiangshang = 0;     //图片在上面    1
    private int kaozuo = 1;      // 图片在左侧      2

    public MyShopAdapter(List<ShopBean.DataBean> data) {

        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }



    //2、设置条目的总数量
    @Override
    public int getViewTypeCount() {
        return 2;
    }

    //3、根据 position 得到 数据类型， 根据数据类型 返回 条目类型
    @Override
    public int getItemViewType(int position) {

        //首先根据 position 拿到当前的数据
        ShopBean.DataBean dataBean = data.get(position);
        //拿到数据类型
        int dataType = dataBean.getType();   // 可能是1  可能是2

        //当数据中 type是1 ，图片在左侧 ，然后type0
        //当数据类型是1 ，返回条目类型 xiangshang
        //当数据类型是2 ，返回条目类型  kaozuo
        if (dataType == 1) {
            return xiangshang;
        } else if (dataType == 2) {
            return kaozuo;
        }
        return xiangshang;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //4、拿到   条目类型  通过 getItemViewType（）
        int itemViewType = getItemViewType(position);  //有可能是type0，有可能是type1

        ViewHolder viewHolder = null;

        //复用holder
        if (convertView == null) {

            //5、根据条目类型，加载不同的布局
            if(itemViewType == xiangshang){
                convertView = View.inflate(parent.getContext(), R.layout.item_shop_layout_xiangshang, null);
                viewHolder = new ViewHolder();
                viewHolder.imageView = convertView.findViewById(R.id.iv);
                viewHolder.name = convertView.findViewById(R.id.tv_name);
                viewHolder.price = convertView.findViewById(R.id.tv_price);
                convertView.setTag(viewHolder);
            }else if(itemViewType == kaozuo) {
                convertView = View.inflate(parent.getContext(), R.layout.item_shop_layout_kaozuo, null);
                viewHolder = new ViewHolder();
                viewHolder.imageView = convertView.findViewById(R.id.iv);
                viewHolder.name = convertView.findViewById(R.id.tv_name);
                viewHolder.price = convertView.findViewById(R.id.tv_price);
                convertView.setTag(viewHolder);
            }

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //拿到当前条目的数据
        ShopBean.DataBean dataBean = data.get(position);

        //6、绑定数据 根据条目类型，绑定对应数据
        if(itemViewType == xiangshang){
            viewHolder.imageView.setImageResource(R.mipmap.ic_launcher_round); ;
            viewHolder.name.setText(dataBean.getGoods_name());
            viewHolder.price.setText(dataBean.getCurrency_price());
        }else if(itemViewType == kaozuo) {
           viewHolder.imageView.setImageResource(R.mipmap.ic_launcher);
           viewHolder.name.setText(dataBean.getGoods_name());
           viewHolder.price.setText(dataBean.getCurrency_price());
        }
        return convertView;
    }

    class ViewHolder {
        ImageView imageView;
        TextView price;
        TextView name;
    }
}
