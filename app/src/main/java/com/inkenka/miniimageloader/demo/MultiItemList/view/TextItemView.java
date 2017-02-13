package com.inkenka.miniimageloader.demo.MultiItemList.view;

import com.inkenka.miniimageloader.R;
import com.inkenka.miniimageloader.demo.MultiItemList.DataBinder;
import com.inkenka.miniimageloader.demo.MultiItemList.ListItemData;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

public class TextItemView extends FrameLayout implements DataBinder{

    private TextView mTextView;

    public TextItemView(Context context) {
        this(context, null);
    }

    public TextItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        int paddingLeftRight = context.getResources().getDimensionPixelOffset(R.dimen.item_padding_left_right);
        int paddingTopBottom = context.getResources().getDimensionPixelOffset(R.dimen.item_padding_top_bottom);
        setPadding(paddingLeftRight, paddingTopBottom, paddingLeftRight, paddingTopBottom);

        LayoutInflater.from(context).inflate(R.layout.item_text, this);
        mTextView = (TextView) findViewById(R.id.title);
    }

    @Override
    public void bindData(Object object) {
        if(object instanceof ListItemData){
            ListItemData data = (ListItemData) object;
            String title;
            title = data.getName();
            if(!TextUtils.isEmpty(title)){
                mTextView.setText(title);
            } else {
                mTextView.setText("");
            }
        }
    }
}
