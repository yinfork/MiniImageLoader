package com.inkenka.miniimageloader.demo.MultiItemList;

import com.inkenka.miniimageloader.demo.MultiItemList.view.OnePicItemView;
import com.inkenka.miniimageloader.demo.MultiItemList.view.TextItemView;
import com.inkenka.miniimageloader.demo.MultiItemList.view.ThreePicItemView;

import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class MultitypeAdapterImpl extends MultitypeAdapter<ListItemData> {

    public MultitypeAdapterImpl(List<ListItemData> list) {
        super(list);
    }

    @Override
    public int getItemViewType(int position) {
        if (mList != null && position < mList.size()) {
            return mList.get(position).getType();
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getViewTypeCount() {
        return ListItemData.TYPE_COUNT;
    }

    @Override
    protected View newView(int itemPos, ViewGroup parent){
        switch (getItemViewType(itemPos)) {
            case ListItemData.TYPE_TEXT:
                return new TextItemView(parent.getContext());
            case ListItemData.TYPE_ONE_PIC:
                return new OnePicItemView(parent.getContext());
            case ListItemData.TYPE_THREE_PIC:
                return new ThreePicItemView(parent.getContext());
            default:
                return null;
        }
    }
}
