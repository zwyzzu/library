package com.zhangwy.alertWindow;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.zhangwy.R;
import com.zhangwy.util.Logger;
import com.zhangwy.util.Screen;

@SuppressWarnings("unused")
public class AlertWindow implements View.OnTouchListener {

    private WindowManager mManager;//实例化的WindowManager.
    private WindowManager.LayoutParams mParams;//布局参数.
    private FrameLayout mTouchLayout;

    private OnStateListener mStateListener;

    private boolean needClick = false;
    private View.OnClickListener mClickListener;

    private int offset;
    private float startX = 0;
    private float startY = 0;
    private float lastX = 0;
    private float lastY = 0;

    public AlertWindow(Context context, View floatingView) {
        this.show(context, floatingView);
    }

    public AlertWindow(Context context, View floatingView, OnStateListener listener) {
        this.setOnStateListener(listener);
        this.show(context, floatingView);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void show(final Context context, View floatingView) {
        try {
            offset = Screen.dip2px(context, 2);//移动偏移量
            this.mManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            this.initParams(context);
            //获取浮动窗口视图所在布局.
            this.mTouchLayout = new FrameLayout(context);
            this.addFloatingView(floatingView);
            this.addClose(context);
            //添加touchLayout
            this.mManager.addView(this.mTouchLayout, this.mParams);
            //主动计算出当前View的宽高信息.
            this.mTouchLayout.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            //处理touch
            this.mTouchLayout.setOnTouchListener(this);
            if (this.mStateListener != null) {
                this.mStateListener.onState(WindowState.show);
            }
        } catch (Exception e) {
            Logger.d("FloatingWindow.init", e);
        }
    }

    @SuppressLint("RtlHardcoded")
    private void initParams(Context context) {
        //赋值WindowManager&LayoutParam.
        this.mParams = new WindowManager.LayoutParams();
        //设置type.系统提示型窗口，一般都在应用程序窗口之上.
        if (Build.VERSION.SDK_INT >= 26) {//8.0新特性
            this.mParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            this.mParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        }
        //设置效果为背景透明.
        this.mParams.format = PixelFormat.RGBA_8888;
        //设置flags.不可聚焦及不可使用按钮对悬浮窗进行操控.
        this.mParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        //设置窗口坐标参考系
        this.mParams.gravity = Gravity.LEFT | Gravity.TOP;
        final int width = Screen.dip2px(context, 170);
        final int height = Screen.dip2px(context, 100);
        //设置原点
        this.mParams.x = Screen.getScreenWidth(context) - width;
        this.mParams.y = (Screen.getScreenHeight(context) - height) / 2;
        //设置悬浮窗口长宽数据.
        this.mParams.width = width;
        this.mParams.height = height;
    }

    private void addFloatingView(View floatingView) {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        this.mTouchLayout.addView(floatingView, params);
    }

    @SuppressLint("RtlHardcoded")
    private void addClose(Context context) {
        ImageView close = new ImageView(context);
        close.setImageResource(R.drawable.icon_close);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(Screen.dip2px(context, 16), Screen.dip2px(context, 16));
        layoutParams.gravity = Gravity.TOP | Gravity.RIGHT;
        layoutParams.rightMargin = Screen.dip2px(context, 3);
        layoutParams.topMargin = Screen.dip2px(context, 3);
        close.setLayoutParams(layoutParams);
        this.mTouchLayout.addView(close, layoutParams);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close();
            }
        });
    }

    private int getStatusBarHeight(Context context) {
        final String name = "status_bar_height", defType = "dimen", defPackage = "android";
        //用于检测状态栏高度.
        int resourceId = context.getResources().getIdentifier(name, defType, defPackage);
        if (resourceId > 0) {
            return context.getResources().getDimensionPixelSize(resourceId);
        }
        return 70;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        boolean isMoved = false;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isMoved = false;
                // 记录按下位置
                lastX = event.getRawX();
                lastY = event.getRawY();

                startX = event.getRawX();
                startY = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                isMoved = true;
                // 记录移动后的位置
                float moveX = event.getRawX();
                float moveY = event.getRawY();
                // 获取当前窗口的布局属性, 添加偏移量, 并更新界面, 实现移动
                this.mParams.x += (int) (moveX - lastX);
                this.mParams.y += (int) (moveY - lastY);
                this.mManager.updateViewLayout(this.mTouchLayout, this.mParams);
                lastX = moveX;
                lastY = moveY;
                break;
            case MotionEvent.ACTION_UP:
                boolean needClick = this.needClick || this.mClickListener != null;
                float fMoveX = event.getRawX();
                float fMoveY = event.getRawY();
                if (needClick && Math.abs(fMoveX - startX) < offset && Math.abs(fMoveY - startY) < offset) {
                    isMoved = false;
                    close();
                    if (this.mClickListener != null) {
                        this.mClickListener.onClick(v);
                    }
                } else {
                    isMoved = true;
                }
                break;
        }
        return isMoved;
    }

    public void close() {
        if (this.mStateListener != null) {
            this.mStateListener.onState(WindowState.close);
        }

        if (this.mManager != null && this.mTouchLayout != null) {
            this.mManager.removeView(this.mTouchLayout);
            this.mManager = null;
            this.mTouchLayout = null;
        }
    }

    public void setNeedClick(boolean needClick) {
        this.needClick = needClick;
    }

    public void setOnClick(View.OnClickListener listener) {
        this.mClickListener = listener;
        this.needClick |= this.mClickListener != null;
    }

    private void setOnStateListener(OnStateListener listener) {
        this.mStateListener = listener;
    }

    public static interface OnStateListener {
        void onState(WindowState state);
    }

    public static enum WindowState {
        show, close;
    }
}
