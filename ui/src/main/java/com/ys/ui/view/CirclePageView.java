package com.ys.ui.view;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ys.ui.R;
import com.ys.ui.base.App;
import com.ys.ui.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 圆点分页
 * Created by river on 2016/5/22.
 */
public class CirclePageView extends LinearLayout {
    List<ImageView> imageViewList = new ArrayList<>();
    Context mContext;
    private int mLastSelectedIndex;

    public CirclePageView(Context context) {
        super(context);
        mContext = context;
    }

    public void init(int totalPageCount) {
        if (totalPageCount == 0) return;
        //根据总数初始化圆点
        for (int i = 0; i < totalPageCount; i++) {
            ImageView circleImage = new ImageView(mContext);

            circleImage.setBackgroundResource(R.drawable.circle_page);

            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            p.setMargins(0, 0, ScreenUtils.dpToPxInt(App.getContext(), 5), 0);
            p.height = ScreenUtils.dpToPxInt(App.getContext(), 10);
            p.width = p.height;

            imageViewList.add(circleImage);
            this.addView(circleImage, p);
        }
        setSelectedPage(0);
    }

    /**
     * 设置选中
     *
     * @param pageIndex
     */
    public void setSelectedPage(int pageIndex) {
        if (imageViewList.isEmpty() || pageIndex >= imageViewList.size()) return;
        imageViewList.get(mLastSelectedIndex).setBackgroundResource(R.drawable.circle_page);
        mLastSelectedIndex = pageIndex;
        imageViewList.get(pageIndex).setBackgroundResource(R.drawable.circle_page_selected);

    }
}
