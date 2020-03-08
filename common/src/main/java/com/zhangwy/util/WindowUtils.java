package com.zhangwy.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.DialogInterface.OnClickListener;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;

import com.zhangwy.R;

/**
 * Created by 张维亚(zhangwy) on 2016/12/24 下午2:29.
 * Updated by zhangwy on 2016/12/24 下午2:29.
 * Description 创建各种类型的dialog
 */
@SuppressWarnings("unused")
public class WindowUtils {

    public static Dialog createAlertDialog(Context ctx, int titleId, String[] array, int index, OnClickListener onSelect, OnClickListener onCancelClick) {
        return createAlertDialog(ctx, ctx.getString(titleId), array, index, onSelect, onCancelClick);
    }

    public static Dialog createAlertDialog(Context ctx, String title, String[] array, int index, OnClickListener onSelect, OnClickListener onCancelClick) {
        AlertDialog.Builder builder = createAlertDialogBuilder(ctx, title);
        if (builder == null) {
            return null;
        }
        return builder.setSingleChoiceItems(array, index, onSelect)
                .setNegativeButton(R.string.common_cancel, onCancelClick).create();
    }

    public static Dialog createAlertDialog(Context ctx, int titleId, View view, String[] array, boolean[] flags, OnMultiChoiceClickListener onSelect, OnClickListener onOKClick, OnClickListener onCancelClick) {
        return new AlertDialog.Builder(ctx)
                .setView(view)
                .setTitle(ctx.getString(titleId))
                .setMultiChoiceItems(array, flags, onSelect)
                .setPositiveButton(R.string.common_ok, onOKClick)
                .setNegativeButton(R.string.common_cancel, onCancelClick).create();
    }

    public static Dialog createAlertDialog(Context ctx, int titleId, String message, OnClickListener onOkClick, int okId, OnClickListener onCancelClick, int cancelId) {
        return createAlertDialog(ctx, titleId, message, onOkClick, ctx.getString(okId), onCancelClick, ctx.getString(cancelId));
    }

    public static Dialog createAlertDialog(Context ctx, int titleId, String message, OnClickListener onOkClick, String okText, OnClickListener onCancelClick, String cancelText) {
        return new AlertDialog.Builder(ctx)
                .setIcon(R.drawable.common_alert_dialog_icon)
                .setTitle(titleId)
                .setMessage(message)
                .setPositiveButton(okText, onOkClick)
                .setNegativeButton(cancelText, onCancelClick).create();
    }

    public static Dialog createAlertDialog(Context ctx, int titleId, String message, OnClickListener onOKClick) {
        return createAlertDialog(ctx, titleId, message, onOKClick, true);
    }

    public static Dialog createAlertDialog(Context ctx, int titleId, String message, OnClickListener onOKClick, boolean isOk) {
        return new AlertDialog.Builder(ctx)
                .setIcon(R.drawable.common_alert_dialog_icon)
                .setTitle(ctx.getString(titleId))
                .setMessage(message)
                .setPositiveButton(isOk ? R.string.common_ok : R.string.common_close, onOKClick)
                .create();
    }

    public static Dialog createAlertDialog(Context ctx, int titleId, String message, boolean showIcon, OnClickListener onCloseClick) {
        AlertDialog dialog = new AlertDialog.Builder(ctx)
                .setTitle(titleId)
                .setMessage(message)
                .setPositiveButton(R.string.common_close, onCloseClick)
                .create();
        if (showIcon) {
            dialog.setIcon(R.drawable.common_alert_dialog_icon);
        }
        return dialog;
    }

    public static Dialog createAlertDialog(Context ctx, int titleId, View view, OnClickListener onOKClick, OnClickListener onCancelClick) {
        return createAlertDialog(ctx, titleId, view, onOKClick, R.string.common_ok, onCancelClick, R.string.common_cancel);
    }

    public static Dialog createAlertDialog(Context ctx, String title, String message, String onOKText, OnClickListener onOKClick,
                                           String onCancelText, OnClickListener onCancelClick) {
        try {
            AlertDialog.Builder builder = createAlertDialogBuilder(ctx, title);
            if (builder == null) {
                return null;
            }
            return builder.setMessage(message)
                    .setPositiveButton(onOKText, onOKClick)
                    .setNegativeButton(onCancelText, onCancelClick)
                    .create();
        } catch (Exception e) {
            return null;
        }
    }

    public static Dialog createAlertDialog(Context ctx, int titleId, View view, OnClickListener onOKClick, int okId, OnClickListener onCancelClick, int cancelId) {
        return new AlertDialog.Builder(ctx)
                .setView(view)
                .setIcon(R.drawable.common_alert_dialog_icon)
                .setTitle(ctx.getString(titleId))
                .setPositiveButton(okId == 0 ? R.string.common_ok : okId, onOKClick)
                .setNegativeButton(cancelId == 0 ? R.string.common_cancel : cancelId, onCancelClick)
                .create();
    }

    private static AlertDialog.Builder createAlertDialogBuilder(Context context, String title) {
        try {
            if (TextUtils.isEmpty(title)) {
                return new AlertDialog.Builder(context);
            } else {
                return new AlertDialog.Builder(context).setTitle(title);
            }
        } catch (Exception e) {
            return null;
        }
    }

    public static Dialog createListDialog(Activity ctx, int dialogId, String title, String[] list, OnClickListener onListClick, OnClickListener onCancelClick, String cancelText) {
        return new AlertDialog.Builder(ctx)
                .setTitle(title)
                .setItems(list, onListClick)
                .setNegativeButton(cancelText, onCancelClick)
                .setOnKeyListener(new BackOnKeyListener(ctx, dialogId))
                .create();
    }

    public static Dialog createListDialog(Context ctx, String title, String[] list, OnClickListener onListClick) {
        return new AlertDialog.Builder(ctx)
                .setTitle(title)
                .setItems(list, onListClick)
                .create();
    }

    public static ProgressDialog createProgressDialog(Context ctx, int titleId, int messageId, boolean indeterminate, boolean cancelable) {
        return createProgressDialog(ctx, titleId, ctx.getString(messageId), indeterminate, cancelable);
    }

    public static ProgressDialog createProgressDialog(Context ctx, int titleId, String message, boolean indeterminate, boolean cancelable) {
        String title = titleId == 0 ? "" : ctx.getString(titleId);
        ProgressDialog dialog = new ProgressDialog(ctx);
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setIndeterminate(indeterminate);
        dialog.setCancelable(cancelable);
        return dialog;
    }

    public static ProgressDialog createProgressDialog(Context ctx, int titleId, String message, int buttonText, OnClickListener onCancelClick, boolean indeterminate) {
        if (TextUtils.isEmpty(message)) {
            message = "...";
        }
        return createProgressDialog(ctx, titleId, message, onCancelClick, ctx.getString(buttonText), indeterminate);
    }

    public static ProgressDialog createProgressDialog(Context ctx, int titleId, String message,
                                                      OnClickListener onCancelClick, String cancelText,
                                                      boolean indeterminate) {
        ProgressDialog dialog = new ProgressDialog(ctx);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setMessage(message);
        dialog.setTitle(ctx.getString(titleId));
        dialog.setMessage(message);
        dialog.setIndeterminate(indeterminate);
        dialog.setButton(ProgressDialog.BUTTON_POSITIVE, cancelText, onCancelClick);
        return dialog;
    }

    public static class BackOnKeyListener implements DialogInterface.OnKeyListener {

        private Activity mContext;
        private int mDialogId;

        public BackOnKeyListener(Activity ctx, int dialogId) {
            mContext = ctx;
            mDialogId = dialogId;
        }

        @Override
        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK)
                mContext.removeDialog(mDialogId);

            return false;
        }

    }


    public static Dialog createAlertDialog(Context ctx, int titleId, String message, int onOkId, OnClickListener onOKClick, int onCancelId, OnClickListener onCancelClick) {
        return createAlertDialog(ctx, titleId, message, ctx.getString(onOkId), onOKClick, ctx.getString(onCancelId), onCancelClick);
    }

    public static Dialog createAlertDialog(Context ctx, int titleId, String message, String onOKText, OnClickListener onOKClick, String onCancelText, OnClickListener onCancelClick) {
        return new AlertDialog.Builder(ctx)
                .setIcon(R.drawable.common_alert_dialog_icon)
                .setTitle(ctx.getString(titleId))
                .setMessage(message)
                .setPositiveButton(onOKText, onOKClick)
                .setNegativeButton(onCancelText, onCancelClick)
                .create();
    }

    public static Dialog createSimpleTipAlertDialog(Context ctx, int titleId, String message) {
        return createAlertDialog(ctx, titleId, message, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
    }

}
