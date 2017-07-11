package com.qianying.bbdc.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.qianying.bbdc.R;
import com.qianying.bbdc.base.EntityListAdapter;
import com.qianying.bbdc.model.XYInfo;
import com.qianying.bbdc.util.ViewHolder;

import org.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * Created by Aaron on 2017/7/8.
 * 信用列表
 */

public class XYAdapter extends EntityListAdapter<XYInfo,XYHolder>{
    public XYAdapter(Context context) {
        super(context);
    }

    public XYAdapter(Context context, List<XYInfo> list) {
        super(context, list);
    }

    @Override
    protected int getAdapterRes() {
        return R.layout.adapter_xy;
    }

    @Override
    protected XYHolder getViewHolder(View root) {
        return new XYHolder(root);
    }

    @Override
    protected void initViewHolder(XYHolder xyHolder, int position) {
        xyHolder.init(getItem(position));
    }
}
class XYHolder extends ViewHolder{
    @ViewInject(R.id.xy_content)
    private TextView xy_content;
    @ViewInject(R.id.xy_date)
    private TextView xy_date;
    @ViewInject(R.id.xy_score)
    private TextView xy_score;


    public XYHolder(View root) {
        super(root);
    }
    public void init(XYInfo info){
        xy_content.setText(info.getDirection());
        xy_date.setText(info.getCurrent());
        xy_score.setText(info.getNote());
    }
}