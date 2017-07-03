package com.gl.recyclerviewdownup;

import android.content.Context;
import android.widget.TextView;

import java.util.List;

/**
 * @author xiaoyuan
 * 这个是精选跟热门的适配器
 */

public class HotAdapter extends MultiBaseAdapter<String> {
    private Context context;
    public HotAdapter(Context context, List<String> listBean, boolean isOpenLoadMore) {
        super(context, listBean, isOpenLoadMore);
        this.context = context;
        setHaed(false);//false是无Banner true是有Banner
    }

    @Override
    protected void convert(ViewHolder holder, String data, int viewType) {
        if (viewType == 0) {
            //如果返回0需要加载banner的布局
        } else {

            TextView item_tv = holder.getView(R.id.item_tv);
            item_tv.setText(data);
        }
    }


    @Override
    protected int getViewType(int position, String data) {
        if (data == null) {
            return 1;
            //这里是有Banner 如果 setHaed(true) 需要返回0
        } else {
            return 1;
        }

    }


    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.item;
    }



}
