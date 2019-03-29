package com.massimoregoli.mp2019.myviews;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

public class MyEditText extends android.support.v7.widget.AppCompatEditText {
    public MyEditText(Context context) {
        super(context);
    }

    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean isInEditMode() {
        return super.isInEditMode();
    }
}
