package org.library.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.text.TextUtils;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.qmuiteam.qmui.util.QMUIDisplayHelper;

import org.library.ContextProvider;
import org.library.R;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Collection;

import retrofit2.HttpException;

/**
 * 常用工具类汇集
 */
public final class Func {
    private Func() {
    }

    public static int dp2px(int dp) {
        return QMUIDisplayHelper.dp2px(ContextProvider.get(), dp);
    }

    public static int px2dp(int px) {
        return QMUIDisplayHelper.px2dp(ContextProvider.get(), px);
    }

    public static boolean isEmpty(@Nullable CharSequence str) {
        return TextUtils.isEmpty(str);
    }

    public static boolean isNotEmpty(@Nullable CharSequence str) {
        return !TextUtils.isEmpty(str);
    }

    public static boolean isEmpty(@Nullable Collection collection) {
        return null == collection || collection.isEmpty();
    }

    public static boolean isNotEmpty(@Nullable Collection collection) {
        return collection != null && collection.size() > 0;
    }

    @ColorInt
    public static int getColor(@ColorRes int resId) {
        return ContextCompat.getColor(ContextProvider.get(), resId);
    }

    @Nullable
    public static ColorStateList getColorStateList(@ColorRes int resId) {
        return ContextCompat.getColorStateList(ContextProvider.get(), resId);
    }

    public static void setRefresh(SwipeRefreshLayout refreshLayout, boolean refreshing) {
        refreshLayout.postDelayed(() -> refreshLayout.setRefreshing(refreshing), 100);
    }

    /**
     * 字符串转整数
     */
    public static int toInt(String str, int defValue) {
        if (TextUtils.isEmpty(str))
            return defValue;
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return defValue;
    }

    public static long toLong(String str, long defValue) {
        if (TextUtils.isEmpty(str))
            return defValue;
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return defValue;
    }

    public static float toFloat(String str) {
        if (isEmpty(str))
            return 0f;
        try {
            return Float.valueOf(str);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return 0f;
    }

    public static double toDouble(String str) {
        if (TextUtils.isEmpty(str))
            return 0;
        return Double.valueOf(str);
    }

    public static boolean toBoolean(String str) {
        if (TextUtils.isEmpty(str))
            return false;
        return Boolean.valueOf(str);
    }

    /**
     * 去掉价格多余的.与0
     */
    public static String formatPrice(float price) {
        String s = String.valueOf(price);
        if (s.indexOf(".") > 0) {
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return s;
    }

    /**
     * 去掉价格多余的.与0
     */
    public static String formatPrice(String price) {
        if (price.indexOf(".") > 0) {
            price = price.replaceAll("0+?$", "");//去掉多余的0
            price = price.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return price;
    }

    /**
     * 去掉价格多余的.与0
     */
    public static String formatPrice￥(float price) {
        String s = String.valueOf(price);
        if (s.indexOf(".") > 0) {
            s = s.replaceAll("0+?$", ""); //去掉多余的0
            s = s.replaceAll("[.]$", ""); //如最后一位是.则去掉
        }
        return "￥" + s;
    }

    /**
     * 去掉价格多余的.与0
     */
    public static String formatPrice￥(String price) {
        if (price.indexOf(".") > 0) {
            price = price.replaceAll("0+?$", "");//去掉多余的0
            price = price.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return "￥" + price;
    }

    /**
     * <p>Overlays part of a String with another String.</p>
     * <p/>
     * <p>A {@code null} string input returns {@code null}.
     * A negative index is treated as zero.
     * An index greater than the string length is treated as the string length.
     * The start index is always the smaller of the two indices.</p>
     * <p/>
     * <pre>
     * StringUtils.overlay(null, *, *, *)            = null
     * StringUtils.overlay("", "abc", 0, 0)          = "abc"
     * StringUtils.overlay("abcdef", null, 2, 4)     = "abef"
     * StringUtils.overlay("abcdef", "", 2, 4)       = "abef"
     * StringUtils.overlay("abcdef", "", 4, 2)       = "abef"
     * StringUtils.overlay("abcdef", "zzzz", 2, 4)   = "abzzzzef"
     * StringUtils.overlay("abcdef", "zzzz", 4, 2)   = "abzzzzef"
     * StringUtils.overlay("abcdef", "zzzz", -1, 4)  = "zzzzef"
     * StringUtils.overlay("abcdef", "zzzz", 2, 8)   = "abzzzz"
     * StringUtils.overlay("abcdef", "zzzz", -2, -3) = "zzzzabcdef"
     * StringUtils.overlay("abcdef", "zzzz", 8, 10)  = "abcdefzzzz"
     * </pre>
     *
     * @param str     the String to do overlaying in, may be null
     * @param overlay the String to overlay, may be null
     * @param start   the position to start overlaying at
     * @param end     the position to stop overlaying before
     * @return overlay String, {@code null} if null String input
     */
    public static String overlay(final String str, String overlay, int start, int end) {
        if (str == null) {
            return null;
        }
        if (overlay == null) {
            overlay = "";
        }
        final int len = str.length();
        if (start < 0) {
            start = 0;
        }
        if (start > len) {
            start = len;
        }
        if (end < 0) {
            end = 0;
        }
        if (end > len) {
            end = len;
        }
        if (start > end) {
            final int temp = start;
            start = end;
            end = temp;
        }
        return str.substring(0, start) + overlay + str.substring(end);
    }

    public static void goAppInfo(Context context) {
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        String pkg = "com.android.settings";
        String cls = "com.android.settings.applications.InstalledAppDetails";
        intent.setComponent(new ComponentName(pkg, cls));
        intent.setData(Uri.parse("package:" + context.getPackageName()));
        context.startActivity(intent);
    }

    public static String parseErrorMessage(Throwable throwable) {
        if (throwable instanceof HttpException || throwable instanceof UnknownHostException || throwable instanceof ConnectException) {
            return ContextProvider.get().getString(R.string.server_error);
        } else if (throwable instanceof SocketTimeoutException) {
            return ContextProvider.get().getString(R.string.timeout_error);
        } else {
            return throwable.getMessage();
        }
    }
}