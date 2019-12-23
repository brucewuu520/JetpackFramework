package com.jetpack.demo;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.qmuiteam.qmui.arch.QMUILatestVisit;

import org.ui.utils.PermissionUtils;

/**
 * 启动页
 */
public class LauncherActivity extends Activity {

    private static final String[] PERMISSIONS_REQUIRED = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
//        checkPermission();
        readyGo();
    }

    private void checkPermission() {
        PermissionUtils.checkPermission(this, new PermissionUtils.Callback() {
            @Override
            public void success() {
                readyGo();
            }

            @Override
            public void fail() {
                finish();
            }
        }, PERMISSIONS_REQUIRED);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PermissionUtils.REQUEST_CODE_PERMISSION) {
            checkPermission();
        }
    }

    private void readyGo() {
        Intent intent = QMUILatestVisit.intentOfLatestVisit(this);
        if (intent == null) {
            intent = new Intent(this, MainActivity.class);
        }
        startActivity(intent);
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }
}