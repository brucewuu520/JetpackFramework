package org.library.util;

import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

import org.library.ContextProvider;

public final class FastBlur {

    public static Bitmap blur(Bitmap bitmap, int radius) {
        //创建一个空bitmap，其大小与我们想要模糊的bitmap大小相同
        Bitmap outBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        //实例化一个新的Renderscript
        RenderScript rs = RenderScript.create(ContextProvider.get());
        //创建Allocation对象
        Allocation allIn = Allocation.createFromBitmap(rs, bitmap);
        Allocation allOut = Allocation.createFromBitmap(rs, outBitmap);
        //创建ScriptIntrinsicBlur对象，该对象实现了高斯模糊算法
        ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));

        //设置模糊半径，0 <radius <= 25
        blurScript.setRadius(radius);

        //执行Renderscript
        blurScript.setInput(allIn);
        blurScript.forEach(allOut);
        //将allOut创建的Bitmap复制到outBitmap
        allOut.copyTo(outBitmap);
        //释放内存占用
        bitmap.recycle();

        //销毁Renderscript。
        rs.destroy();
        return outBitmap;
    }
}
