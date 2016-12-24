package com.zwy.utils;

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

/**
 * Created by 张维亚(zhangwy) on 2016/12/24 下午2:29.
 * Updated by zhangwy on 2016/12/24 下午2:29.
 * Description 创建各种类型的dialog
 */
public class WindowUtils {

    public static Dialog createAlertDialog(Context ctx, int titleId, String[] array, int index,
                                           OnClickListener onSelect,
                                           OnClickListener onCancelClick) {
        return createAlertDialog(ctx, ctx.getString(titleId), array, index, onSelect, onCancelClick);
    }

    public static Dialog createAlertDialog(Context ctx, String title, String[] array, int index,
                                           OnClickListener onSelect,
                                           OnClickListener onCancelClick) {
        return new AlertDialog.Builder(ctx)
                .setTitle(title)
                .setSingleChoiceItems(array, index, onSelect)
                .setNegativeButton(R.string.cancel, onCancelClick).create();
    }

    public static Dialog createAlertDialog(Context ctx, int titleId, View view, String[] array, boolean[] flags,
                                           OnMultiChoiceClickListener onSelect,
                                           OnClickListener onOKClick,
                                           OnClickListener onCancelClick) {
        return new AlertDialog.Builder(ctx)
                .setView(view)
                .setTitle(ctx.getString(titleId))
                .setMultiChoiceItems(array, flags, onSelect)
                .setPositiveButton(R.string.ok, onOKClick)
                .setNegativeButton(R.string.cancel, onCancelClick).create();
    }

    public static Dialog createAlertDialog(Context ctx, int titleId, String message,
                                           OnClickListener onOkClick, int okId,
                                           OnClickListener onCancelClick, int cancelId) {
        return createAlertDialog(ctx, titleId, message, onOkClick, ctx.getString(okId), onCancelClick, ctx.getString(cancelId));
    }

    public static Dialog createAlertDialog(Context ctx, int titleId, String message,
                                           OnClickListener onOkClick, String okText,
                                           OnClickListener onCancelClick, String cancelText) {
        return new AlertDialog.Builder(ctx)
                .setIcon(R.drawable.alert_dialog_icon)
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
                .setIcon(R.drawable.alert_dialog_icon)
                .setTitle(ctx.getString(titleId))
                .setMessage(message)
                .setPositiveButton(isOk ? R.string.ok : R.string.close, onOKClick)
                .create();
    }

    public static Dialog createAlertDialog(Context ctx, int titleId, String message, boolean showIcon, OnClickListener onCloseClick) {
        AlertDialog dialog = new AlertDialog.Builder(ctx)
                .setTitle(titleId)
                .setMessage(message)
                .setPositiveButton(R.string.close, onCloseClick)
                .create();
        if (showIcon) {
            dialog.setIcon(R.drawable.alert_dialog_icon);
        }
        return dialog;
    }

    public static Dialog createAlertDialog(Context ctx, int titleId, View view, OnClickListener onOKClick, OnClickListener onCancelClick) {
        return createAlertDialog(ctx, titleId, view, onOKClick, R.string.ok, onCancelClick, R.string.cancel);
    }

    public static Dialog createAlertDialog(Context ctx, int titleId, View view,
                                           OnClickListener onOKClick, int okId,
                                           OnClickListener onCancelClick, int cancelId) {
        return new AlertDialog.Builder(ctx)
                .setView(view)
                .setIcon(R.drawable.alert_dialog_icon)
                .setTitle(ctx.getString(titleId))
                .setPositiveButton(okId == 0 ? R.string.ok : okId, onOKClick)
                .setNegativeButton(cancelId == 0 ? R.string.cancel : cancelId, onCancelClick)
                .create();
    }

    public static Dialog createListDialog(Activity ctx, int dialogId, String title, String[] list, OnClickListener onListClick,
                                          OnClickListener onCancelClick, String cancelText) {
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

}
