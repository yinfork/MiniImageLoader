package com.inkenka.miniimageloader.demo.MultiItemList;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * 支持多类型的ListView Adapter
 */
public abstract class MultitypeAdapter<T> extends BaseAdapter {
    List<T> mList;

    abstract View newView(int itemPos, ViewGroup parent);

    public MultitypeAdapter(List<T> list) {
        mList = list;
    }

    @Override
    public int getCount() {
        return null != mList ? mList.size() : 0;
    }

    @Override
    public Object getItem(int i) {
        return null != mList ? mList.get(i) : null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(null == convertView) {
            convertView = newView(position, parent);
        }
        bindView(position, convertView, parent);
        return convertView;
    }

    private void bindView(int itemPos, View convertView, ViewGroup parent){
        if(convertView instanceof DataBinder){
            ((DataBinder) convertView).bindData(mList.get(itemPos));
        }
    }
}
