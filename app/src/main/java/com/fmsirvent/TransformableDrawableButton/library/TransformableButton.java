package com.fmsirvent.TransformableDrawableButton.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;

import fr.northborders.menucrossdrawable.app.R;

/**
 * Created by fmsirvent on 13/11/14.
 */
public class TransformableButton extends Button {
    private TransformableDrawable transformableDrawable;

    public TransformableButton(Context context) {
        super(context);
        if (!isInEditMode()){
            init(context, null, 0);
        }
    }

    public TransformableButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()){
            init(context, attrs, 0);
        }
    }

    public TransformableButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (!isInEditMode()){
            init(context, attrs, defStyle);
        }
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        TypedArray arr = getContext().obtainStyledAttributes(attrs, R.styleable.TransformableButtonAttrs);
        int unCheckDraw = arr.getInt(R.styleable.TransformableButtonAttrs_unCheckDraw, 0);
        int checkDraw = arr.getInt(R.styleable.TransformableButtonAttrs_checkDraw, 1);
        int strokeColor = arr.getColor(R.styleable.TransformableButtonAttrs_strokeColor, TransformableDrawable.DEF_LINES_COLOR);
        int backgroundColor = arr.getColor(R.styleable.TransformableButtonAttrs_backgroundColor, TransformableDrawable.DEF_BACKGROUND_COLOR);


        transformableDrawable = new TransformableDrawable(context, unCheckDraw, checkDraw, TransformableDrawable.DEF_LINE_WIDTH, strokeColor, backgroundColor);
        if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            setBackgroundDrawable(transformableDrawable);
        } else {
            setBackground(transformableDrawable);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int min = Math.min(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(min, min);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP)
            transformableDrawable.toggle();
        return super.onTouchEvent(event);
    }
}
