package org.ui.widget.materialedittext;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;

public class MaterialEditText extends AppCompatEditText {

    private int iconSize;
    private int iconPadding;
    private Bitmap iconLeft, iconRight;

    private Bitmap clearButton;
    private boolean showClearButton;

    public MaterialEditText(Context context) {
        super(context);
        init();
    }

    public MaterialEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MaterialEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setBackgroundDrawable(null);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}