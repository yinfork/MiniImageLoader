package com.inkenka.miniimageloader.demo.MultiItemList.view;

import com.inkenka.miniimageloader.R;
import com.inkenka.miniimageloader.demo.MultiItemList.DataBinder;
import com.inkenka.miniimageloader.demo.MultiItemList.ListItemData;
import com.inkenka.miniimageloader.library.MiniImageLoader;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ThreePicItemView extends LinearLayout implements DataBinder {

    private TextView mTextView;
    private ImageView mPic1View;
    private ImageView mPic2View;
    private ImageView mPic3View;


    public ThreePicItemView(Context context) {
        this(context, null);
    }

    public ThreePicItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ThreePicItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setOrientation(VERTICAL);
        int paddingLeftRight = context.getResources()
            .getDimensionPixelOffset(R.dimen.item_padding_left_right);
        int paddingTopBottom = context.getResources()
            .getDimensionPixelOffset(R.dimen.item_padding_top_bottom);
        setPadding(paddingLeftRight, paddingTopBottom, paddingLeftRight, paddingTopBottom);

        LayoutInflater.from(context).inflate(R.layout.item_three_pic, this);
        mTextView = (TextView) findViewById(R.id.title);
        mPic1View = (ImageView) findViewById(R.id.pic1);
        mPic2View = (ImageView) findViewById(R.id.pic2);
        mPic3View = (ImageView) findViewById(R.id.pic3);
    }

    @Override
    public void bindData(Object object) {
        if (object instanceof ListItemData) {
            ListItemData data = (ListItemData) object;
            String pic1Url = null, pic2Url = null, pic3Url = null;
            String title;
            if (null != data.getImages() && data.getImages().length >= 3) {
                pic1Url = data.getImages()[0];
                pic2Url = data.getImages()[1];
                pic3Url = data.getImages()[2];
            }
            title = data.getName();

            if (!TextUtils.isEmpty(pic1Url) && !TextUtils.isEmpty(pic2Url) && !TextUtils
                .isEmpty(pic3Url) && !TextUtils.isEmpty(title)) {
                mTextView.setText(title);
                MiniImageLoader.get().loadImage(pic1Url, mPic1View);
                MiniImageLoader.get().loadImage(pic1Url, mPic2View);
                MiniImageLoader.get().loadImage(pic1Url, mPic3View);
            } else {
                mTextView.setText("");
                mPic1View.setImageBitmap(null);
                mPic2View.setImageBitmap(null);
                mPic3View.setImageBitmap(null);
            }
        }
    }
}
