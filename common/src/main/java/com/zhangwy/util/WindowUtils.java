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
 * CreateTime 2016/12/24 14:29.
 * UpdateTime 2021/4/30 15:56.
 * Author zhangwy
 * desc:
 * 创建各种类型的dialog
 * -------------------------------------------------------------------------------------------------
 * use:
 * TODO
 */
@SuppressWarnings({"unused", "WeakerAccess", "deprecation"})
public class WindowUtils {

    public static Dialog createAlertDialog(Context ctx, int titleId, String[] array, int index, OnClickListener onSelect, OnClickListener cancelClick) {
        return createAlertDialog(ctx, ctx.getString(titleId), array, index, onSelect, cancelClick);
    }

    public static Dialog createAlertDialog(Context ctx, String title, String[] array, int index, OnClickListener onSelect, OnClickListener cancel) {
        AlertDialog.Builder builder = createAlertBuilder(ctx, title, 0, null, R.string.common_cancel, cancel);
        if (!Util.isEmpty(array) && onSelect != null) {
            builder.setSingleChoiceItems(array, index, onSelect);
        }
        return builder.create();
    }

    public static Dialog createAlertDialog(Context ctx, int titleId, View view, String[] array, boolean[] flags, OnMultiChoiceClickListener select, OnClickListener oKClick, OnClickListener cancelClick) {
        AlertDialog.Builder builder = createAlertBuilder(ctx, titleId, R.string.common_ok, oKClick, R.string.common_cancel, cancelClick);
        if (view != null) {
            builder.setView(view);
        }
        if (!Util.isEmpty(array) && select != null) {
            builder.setMultiChoiceItems(array, flags, select).create();
        }
        return builder.create();
    }

    public static Dialog createAlertDialog(Context context, View view) {
        return createAlertDialog(context, 0, view);
    }

    public static Dialog createAlertDialog(Context context, int titleId, View view) {
        return createAlertDialog(context, titleId, view, null, null);
    }

    public static Dialog createAlertDialog(Context ctx, View view, OnClickListener onOKClick, OnClickListener onCancelClick) {
        return createAlertDialog(ctx, 0, view, R.string.common_ok, onOKClick, R.string.common_cancel, onCancelClick);
    }

    public static Dialog createAlertDialog(Context ctx, int titleId, View view, OnClickListener onOKClick, OnClickListener onCancelClick) {
        return createAlertDialog(ctx, titleId, view, R.string.common_ok, onOKClick, R.string.common_cancel, onCancelClick);
    }

    public static Dialog createAlertDialog(Context ctx, int titleId, View view, int ok, OnClickListener oKClick, int cancel, OnClickListener cancelClick) {
        AlertDialog.Builder builder = createAlertBuilder(ctx, titleId, ok, oKClick, cancel, cancelClick);
        if (view != null) {
            builder.setView(view);
        }
        return builder.create();
    }

    public static Dialog createAlertDialog(Context context, int titleId, View view, String ok, OnClickListener oKClick, String cancel, OnClickListener cancelClick) {
        AlertDialog.Builder builder = createAlertBuilder(context, titleId, ok, oKClick, cancel, cancelClick);
        if (view != null) {
            builder.setView(view);
        }
        return builder.create();
    }

    public static Dialog createAlertDialog(Context ctx, int titleId, String message, boolean showIcon, OnClickListener close) {
        AlertDialog.Builder builder = createAlertBuilder(ctx, titleId, R.string.common_close, close, 0, null);
        builder.setMessage(message);
        if (showIcon) {
            builder.setIcon(R.drawable.common_alert_dialog_icon);
        }
        return builder.create();
    }

    public static Dialog createAlertDialog(Context ctx, String title, String message, String ok, OnClickListener oKClick, String cancel, OnClickListener cancelClick) {
        AlertDialog.Builder builder = createAlertBuilder(ctx, title, ok, oKClick, cancel, cancelClick);
        return builder.setMessage(message).create();
    }

    public static Dialog createAlertDialog(Context ctx, int titleId, String message, OnClickListener oKClick) {
        return createAlertDialog(ctx, titleId, message, oKClick, true);
    }

    public static Dialog createAlertDialog(Context ctx, int titleId, String message, OnClickListener onClick, boolean isOk) {
        return createAlertDialog(ctx, titleId, message, isOk ? R.string.common_ok : R.string.common_close, onClick, 0, null);
    }

    public static Dialog createAlertDialog(Context ctx, int titleId, String message, int ok, OnClickListener okClick, int cancel, OnClickListener cancelClick) {
        AlertDialog.Builder builder = createAlertBuilder(ctx, titleId, ok, okClick, cancel, cancelClick);
        return builder.setMessage(message).create();
    }

    public static Dialog createAlertDialog(Context ctx, int titleId, String message, String ok, OnClickListener okClick, String cancel, OnClickListener cancelClick) {
        AlertDialog.Builder builder = createAlertBuilder(ctx, titleId, ok, okClick, cancel, cancelClick);
        return builder.setMessage(message).create();
    }

    public static AlertDialog.Builder createAlertBuilder(Context context, int titleId, int ok, final OnClickListener okClick, int cancel, OnClickListener cancelClick) {
        return createAlertBuilder(context, titleId, StringUtil.getString(context, ok), okClick, StringUtil.getString(context, cancel), cancelClick);
    }

    public static AlertDialog.Builder createAlertBuilder(Context context, int titleId, String ok, final OnClickListener okClick, String cancel, OnClickListener cancelClick) {
        return createAlertBuilder(context, StringUtil.getString(context, titleId), ok, okClick, cancel, cancelClick);
    }

    public static AlertDialog.Builder createAlertBuilder(Context context, String title, int ok, final OnClickListener okClick, int cancel, OnClickListener cancelClick) {
        return createAlertBuilder(context, title, StringUtil.getString(context, ok), okClick, StringUtil.getString(context, cancel), cancelClick);
    }

    public static AlertDialog.Builder createAlertBuilder(Context context, String title, String ok, final OnClickListener okClick, String cancel, OnClickListener cancelClick) {
        AlertDialog.Builder builder = createAlertBuilder(context, title);
        if (okClick != null) {
            builder.setPositiveButton((StringUtil.isEmpty(ok) ? StringUtil.getString(context, R.string.common_ok) : ok), okClick);
        }
        if (cancelClick != null) {
            builder.setNegativeButton((StringUtil.isEmpty(cancel) ? StringUtil.getString(context, R.string.common_cancel) : cancel), cancelClick);
        }
        return builder;
    }

    public static AlertDialog.Builder createAlertBuilder(Context context, String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (!StringUtil.isEmpty(title)) {
            builder.setTitle(title);
        }
        return builder;
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

    public static ProgressDialog createProgressDialog(Context ctx, int titleId, String message, OnClickListener onCancelClick, String cancelText, boolean indeterminate) {
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

    public static Dialog createSimpleTipAlertDialog(Context ctx, int titleId, String message) {
        return createAlertDialog(ctx, titleId, message, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
    }

}
