package com.zhangwy.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.StyleRes;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.zhangwy.util.Logger;
import com.zhangwy.util.Screen;

import java.util.Locale;

/**
 * Created by zhangwy on 2018/1/29 上午10:40.
 * Updated by zhangwy on 2018/1/29 上午10:40.
 * Description 现实电量的view
 */
@SuppressWarnings("unused")
public class PowerView extends FrameLayout {

    public int power = 100;
    private int rgb = 22;
    private View column;
    private TextView percent;
    private int maxColumnHeight = this.getMeasuredHeight();

    public PowerView(@NonNull Context context) {
        super(context);
        this.init(null);
    }

    public PowerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.init(attrs);
    }

    public PowerView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init(attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PowerView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.init(attrs);
    }

    private void init(AttributeSet attrs) {
        LayoutInflater.from(this.getContext()).inflate(R.layout.widget_power, this, true);
        this.column = findViewById(R.id.power_column);
        this.percent = (TextView) findViewById(R.id.power_percent);
        if (attrs != null) {
            TypedArray array = this.getContext().obtainStyledAttributes(attrs, R.styleable.PowerView);
            this.setPercentVisibility(array.getBoolean(R.styleable.PowerView_show_percent, true));
            this.setPercentTextSize(array.getDimension(R.styleable.PowerView_percent_textSize, Screen.dip2px(this.getContext(), 10)));
            array.recycle();
        }
        this.refreshPercent(this.power);
    }

    public void setPower(int power) {
        power = power > 100 ? 100 : power;
        this.power = power;
        this.refreshPercent(this.power);
        this.refreshPowerColumn(this.power);
    }

    private void refreshPercent(int power) {
        if (this.percent != null) {
            if (power <= 25) {
                this.percent.setTextColor(Color.RED);
            } else {
                this.percent.setTextColor(Color.WHITE);
            }
            this.percent.setText(String.format(Locale.getDefault(), "%1$d%%", power));
        }
    }

    private void refreshPowerColumn(int power) {
        if (this.column != null) {
            LayoutParams params = (LayoutParams) this.column.getLayoutParams();
            if (params == null) {
                params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            }
            params.height = Math.round(maxColumnHeight * power / 100);
            this.column.setLayoutParams(params);
            this.column.setBackgroundColor(getColor(power));
            Logger.d(String.format(Locale.getDefault(), "power=%1$d;c.height=%2$d", power, params.height));
        }
    }

    public void setPercentTextSize(float size) {
        if (this.percent != null) {
            this.percent.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        }
    }

    public void setPercentVisibility(boolean show) {
        if (this.percent != null) {
            this.percent.setVisibility(show ? VISIBLE : INVISIBLE);
        }
    }

    @ColorInt
    private int getColor(int power) {
        return Color.argb(255, Math.round(55 + (100 - power) * 2), 33, 54);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Logger.d("onLayout");
        if (changed) {
            this.finalMeasure();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Logger.d("onMeasure");
    }

    private void finalMeasure() {
        int width = this.getMeasuredWidth();
        int height = this.getMeasuredHeight();
        this.maxColumnHeight = Math.round(height * 3 / 4);
        if (this.column != null) {
            LayoutParams params = (LayoutParams) this.column.getLayoutParams();
            if (params == null) {
                params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            }
            params.width = Math.round(width * 3 / 8);
            params.height = Math.round(maxColumnHeight * power / 100);
            params.setMargins(params.leftMargin, params.topMargin, params.rightMargin, Math.round(height * 5 / 64));
            this.column.setLayoutParams(params);
            Logger.d(String.format(Locale.getDefault(), "p.width=%1$d;p.height=%2$d;power=%3$d;c.width=%4$d;c.height=%5$d;c.maxHeight=%6$d", width, height, power, params.width, params.height, this.maxColumnHeight));
        }

        if (this.percent != null) {
            LayoutParams params = (LayoutParams) this.percent.getLayoutParams();
            if (params == null) {
                params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            }
            params.setMargins(params.leftMargin, params.topMargin, params.rightMargin, Math.round(height * 3 / 32));
            this.percent.setLayoutParams(params);
        }
        this.setPower(this.power);
    }
}
