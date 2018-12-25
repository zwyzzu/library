package com.zhangwy.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.ColorInt;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.widget.Checkable;

/**
 * Created by zhangwy on 2018/10/24 下午9:23.
 * Updated by zhangwy on 2018/10/24 下午9:23.
 * Description
 */
@SuppressWarnings("unused")
public class WImageView extends AppCompatImageView implements Checkable {

    private boolean checked = false;
    private boolean circle = false;
    private @ColorInt int borderColor = Color.TRANSPARENT;
    private float borderWidth = 0f;

    public WImageView(Context context) {
        super(context);
    }

    public WImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init(attrs);
    }

    public WImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray array = this.getContext().obtainStyledAttributes(attrs, R.styleable.WImageView);
        this.circle = array.getBoolean(R.styleable.WImageView_roundAsCircle, false);
        this.borderColor = array.getColor(R.styleable.WImageView_borderColor, Color.TRANSPARENT);
        this.borderWidth = array.getDimension(R.styleable.WImageView_borderWidth, 0);
        this.checked = array.getBoolean(R.styleable.WImageView_checked, false);
        array.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        if (this.circle) {
            float centerX = getWidth() * 1.0f / 2;
            float centerY = getHeight() * 1.0f / 2;
            @SuppressLint("DrawAllocation")
            Path path = new Path();
            path.addCircle(centerX, centerY, this.getRadius(), Path.Direction.CW);
            canvas.clipPath(path);
        }
        super.onDraw(canvas);
        this.paintBorder(canvas);
        canvas.restore();
    }

    private void paintBorder(Canvas canvas) {
        if (this.borderWidth <= 0) {
            return;
        }
        Paint paint = new Paint();//画笔
        paint.setColor(this.borderColor);//设置颜色
        paint.setAntiAlias(true);//设置抗锯齿
        paint.setStrokeWidth(this.borderWidth);//设置画笔粗细
        paint.setStyle(Paint.Style.STROKE);//设置是否为空心

        float width = this.getWidth();
        float height = this.getHeight();
        float halfWidth = this.borderWidth / 2;
        if (this.circle) {
            float centerX = width / 2;
            float centerY = height / 2;
            float radius = this.getRadius() - halfWidth;
            canvas.drawCircle(centerX, centerY, radius, paint);
        } else {
            float right = width - halfWidth;
            float bottom = halfWidth - halfWidth;
            canvas.drawRect(halfWidth, halfWidth, right, bottom, paint);
        }
    }

    private float getRadius() {
        float width = getWidth();
        float height = getHeight();
        return Math.min(width, height) / 2;
    }

    public void setRoundAsCircle(boolean circle) {
        this.circle = circle;
    }

    public void setBorder(@ColorInt int color, float width) {
        this.borderColor = color;
        this.borderWidth = width;
    }

    @Override
    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @Override
    public boolean isChecked() {
        return false;
    }

    @Override
    public void toggle() {

    }
}
