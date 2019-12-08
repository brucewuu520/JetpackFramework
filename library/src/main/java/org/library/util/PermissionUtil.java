package org.library.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.qmuiteam.qmui.widget.dialog.QMUIDialog;

import java.util.LinkedList;
import java.util.List;

/**
 * 运行时权限工具类
 */
public final class PermissionUtil {

    public interface Callback {
        void success();

        void fail();
    }

    public static final int REQUEST_CODE_PERMISSION = 10011;

    /**
     * Check if the calling context has a set of permissions.
     *
     * @param context     the calling context.
     * @param permissions one ore more permissions, such as {@link Manifest.permission#CAMERA}.
     * @return true if all permissions are already granted, false if at least one permission is not
     * yet granted.
     * @see Manifest.permission
     */
    public static boolean hasPermissions(Context context, @NonNull String... permissions) {
        // Always return true for SDK < M, let the system deal with the permissions
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            //Log.w(TAG, "hasPermissions: API version < M, returning true by default");

            // DANGER ZONE!!! Changing this will break the library.
            return true;
        }

        // Null context may be passed if we have detected Low API (less than M) so getting
        // to this point with a null context should not be possible.
        if (context == null) {
            throw new IllegalArgumentException("Can't check permissions for null context");
        }

        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }

        return true;
    }

    /**
     * Check that all given permissions have been granted by verifying that each entry in the
     * given array is of the value {@link PackageManager#PERMISSION_GRANTED}.
     *
     * @see AppCompatActivity#onRequestPermissionsResult(int, String[], int[])
     */
    public static boolean verifyPermissions(int[] grantResults) {
        // At least one result must be checked.
        if (grantResults.length < 1) {
            return false;
        }

        // Verify that each required permission has been granted, otherwise return false.
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public static void showPermissionDenied(@NonNull final Activity activity, String message) {
        new QMUIDialog.MessageDialogBuilder(activity)
                .setMessage(message)
                .addAction("暂不", ((dialog, index) -> dialog.dismiss()))
                .addAction("去设置", ((dialog, index) -> {
                    Func.goAppInfo(activity);
                    dialog.dismiss();
                }))
                .show();
    }

    public static void checkPermission(final Activity activity, @NonNull final Callback callback, @NonNull final String... permissions) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            //Log.w(TAG, "hasPermissions: API version < M, returning true by default");
            // DANGER ZONE!!! Changing this will break the library.
            callback.success();
            return;
        }
        final List<String> deniedPermissions = new LinkedList<>();
        try {
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(activity, permission)
                        != PackageManager.PERMISSION_GRANTED) {
                    deniedPermissions.add(permission);
                }
            }
            if (deniedPermissions.isEmpty()) {
                callback.success();
            } else {
                new QMUIDialog.MessageDialogBuilder(activity)
                        .setMessage("为了给您提供更好的服务,请允许以下权限。")
                        .setCancelable(false)
                        .setCanceledOnTouchOutside(false)
                        .addAction("残忍拒绝", ((dialog, index) -> {
                            dialog.dismiss();
                            callback.fail();
                        }))
                        .addAction("好的", ((dialog, index) -> {
                            dialog.dismiss();
                            String[] p = deniedPermissions.toArray(new String[0]);
                            ActivityCompat.requestPermissions(activity, p, REQUEST_CODE_PERMISSION);
                        }))
                        .show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            callback.success();
        }
    }

}
