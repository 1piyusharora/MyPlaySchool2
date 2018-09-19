package com.auribisesmyplayschool.myplayschool.classes;

import android.content.Context;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.TextView;

import com.auribisesmyplayschool.myplayschool.R;

public class textViewGradient extends android.support.v7.widget.AppCompatTextView {
    public textViewGradient(Context context) {
        super(context);
    }

    public textViewGradient(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public textViewGradient(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        //Setting the gradient if layout is changed
        if (changed) {
            getPaint().setShader(new LinearGradient(0, 0, getWidth(), getHeight(),
                    ContextCompat.getColor(getContext(),R.color.tv_start_color),
                    ContextCompat.getColor(getContext(), R.color.tv_end_color),
                    Shader.TileMode.CLAMP));
        }
    }
}
