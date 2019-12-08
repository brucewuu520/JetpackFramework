package org.library;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import com.qmuiteam.qmui.arch.QMUISwipeBackActivityManager;

@RestrictTo(RestrictTo.Scope.LIBRARY)
public class ContextProvider extends ContentProvider {

    @SuppressLint("StaticFieldLeak")
    static Context context;

    @Override
    public boolean onCreate() {
        context = getContext();
        Log.e("ContextProvider", "---onCreate--");
        QMUISwipeBackActivityManager.init((Application) context);
        return true;
    }

    public static Context get() {
        return ContextProvider.context;
    }

    public static Application getApplication() {
        return (Application) ContextProvider.context;
    }

    @Deprecated
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        return null;
    }

    @Deprecated
    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Deprecated
    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Deprecated
    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
    @Deprecated
    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}