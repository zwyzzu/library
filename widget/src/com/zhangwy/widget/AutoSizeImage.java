package com.zhangwy.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Pair;
import android.widget.ImageView;

/**
 * Author: 张维亚
 * 创建时间：2014年12月19日 上午10:51:56
 * 修改时间：2014年12月19日 上午10:51:56
 * Description: 
 **/
public class AutoSizeImage extends ImageView {

	public AutoSizeImage(Context context) {
		super(context);
	}

	public AutoSizeImage(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public AutoSizeImage(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		Pair<Integer, Integer> spec = measureWidthAndHeight(widthMeasureSpec, heightMeasureSpec);
		super.onMeasure(spec.first, spec.second);
	}

	/********************************************************************************************
	 * UNSPECIFIED 这说明parent没有对child强加任何限制，child可以是它想要的任何尺寸。						*
	 * EXACTLY  Parent为child决定了一个绝对尺寸，child将会被赋予这些边界限制，不管child自己想要多大。			*
	 * AT_MOST Child可以是自己任意的大小，但是有个绝对尺寸的上限。											*
	 ********************************************************************************************
	 * 覆写onMeasure方法的时候，子类有责任确保measured height and width至少为这个View的最小height和width。	*
	 * (getSuggestedMinimumHeight() and getSuggestedMinimumWidth())。							*
	 ********************************************************************************************/
    private Pair<Integer, Integer> measureWidthAndHeight(int widthMeasureSpec, int heightMeasureSpec) {
    	if (getDrawable() == null)
    		return Pair.create(widthMeasureSpec, heightMeasureSpec);

    	int drawableWidth = getDrawable().getMinimumWidth();
    	int drawableHeight = getDrawable().getMinimumHeight();

    	if (drawableWidth <= 0 || drawableHeight <= 0)
    		return Pair.create(widthMeasureSpec, heightMeasureSpec);

        int specSizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int specSizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int specModeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int specModeHeight = MeasureSpec.getMode(heightMeasureSpec);

        if (specSizeHeight <= 0 && specSizeWidth <= 0)
        	return Pair.create(widthMeasureSpec, heightMeasureSpec);

        float scalingWidth = specSizeWidth / drawableWidth;
        float scalingHeight = specSizeHeight / drawableHeight;

		int viewWidth = specSizeWidth;
		int viewHeight = specSizeHeight;

		if (scalingWidth <= 0) {
			viewHeight = specSizeHeight;
			viewWidth = drawableWidth * specSizeHeight / drawableHeight;
		} else if (scalingHeight <= 0) {
			viewWidth = specSizeWidth;
			viewHeight = drawableHeight * specSizeWidth / drawableWidth;
		} else if (scalingWidth > scalingHeight && scalingHeight > 0) {
			viewHeight = specSizeHeight;
			switch (specModeWidth) {
			case MeasureSpec.UNSPECIFIED:
				viewWidth = drawableWidth * specSizeHeight / drawableHeight;
				break;
			case MeasureSpec.EXACTLY:
				viewWidth = specSizeWidth;
				if (specModeHeight == MeasureSpec.UNSPECIFIED) {
					viewHeight = drawableHeight * specSizeWidth / drawableWidth;
				}
				break;
			case MeasureSpec.AT_MOST:
				viewWidth = drawableWidth * specSizeHeight / drawableHeight;
				break;
			}
		} else if (scalingHeight > scalingWidth && scalingWidth > 0) {
			viewWidth = specSizeWidth;
			switch (specModeHeight) {
			case MeasureSpec.UNSPECIFIED:
				viewHeight = drawableHeight * specSizeWidth / drawableWidth;
				break;
			case MeasureSpec.EXACTLY:
				viewHeight = specSizeHeight;
				if (specModeWidth == MeasureSpec.UNSPECIFIED) {
					viewWidth = drawableWidth * specSizeHeight / drawableHeight;
				}
				break;
			case MeasureSpec.AT_MOST:
				viewHeight = drawableHeight * specSizeWidth / drawableWidth;
				break;
			}
		}

		widthMeasureSpec = MeasureSpec.makeMeasureSpec(viewWidth, MeasureSpec.EXACTLY);
		heightMeasureSpec = MeasureSpec.makeMeasureSpec(viewHeight, MeasureSpec.EXACTLY);
        return Pair.create(widthMeasureSpec, heightMeasureSpec);
    }

}