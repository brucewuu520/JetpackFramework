package org.ui.widget;

import android.content.Context;

import androidx.appcompat.widget.AppCompatTextView;

import com.qmuiteam.qmui.widget.dialog.QMUIDialog;

import org.ui.R;

public class LoadingDialog {

    private QMUIDialog loadingDialog;
    private AppCompatTextView tvMessage;

    public LoadingDialog(Context context) {
        loadingDialog = new QMUIDialog.CustomDialogBuilder(context)
                .setLayout(R.layout.loading_dialog)
                .setCanceledOnTouchOutside(false)
                .create();
        tvMessage = loadingDialog.findViewById(R.id.loading_msg);
    }

    public void show() {
        loadingDialog.show();
    }

    public void show(String msg) {
        tvMessage.setText(msg);
        loadingDialog.show();
    }

    public void dismiss() {
        if (loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }
}
