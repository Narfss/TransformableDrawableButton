package com.fmsirvent.transformabledrawablebutton;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewTreeObserver;
import android.widget.Button;


/**
 * Created by fmsirvent on 13/11/14.
 */
public class TransformableButton extends Button {
    private TransformableDrawable transformableDrawable;
    private TransformableDrawable.BackgroundPosition backgroundPosition;

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

    private void init(final Context context, AttributeSet attrs, int defStyle) {
        TypedArray arr = getContext().obtainStyledAttributes(attrs, R.styleable.TransformableButtonAttrs);
        int unCheckDraw = arr.getInt(R.styleable.TransformableButtonAttrs_unCheckDraw, 0);
        int checkDraw = arr.getInt(R.styleable.TransformableButtonAttrs_checkDraw, 1);
        int strokeColor = arr.getColor(R.styleable.TransformableButtonAttrs_strokeColor, TransformableDrawable.DEF_LINES_COLOR);
        int backgroundColor = arr.getColor(R.styleable.TransformableButtonAttrs_backgroundColor, TransformableDrawable.DEF_BACKGROUND_COLOR);
        int drawablePosition = arr.getInt(R.styleable.TransformableButtonAttrs_drawablePosition, TransformableDrawable.BackgroundPosition.BACKGROUND.getNum());


        transformableDrawable = new TransformableDrawable(context, unCheckDraw, checkDraw, TransformableDrawable.DEF_LINE_WIDTH, strokeColor, backgroundColor);

        backgroundPosition = TransformableDrawable.BackgroundPosition.enumOf(drawablePosition);

        transformableDrawable.setPositionDrawable(backgroundPosition);
        switch (backgroundPosition) {
            case BACKGROUND:
                if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    setBackgroundDrawable(transformableDrawable);
                } else {
                    setBackground(transformableDrawable);
                }
                break;
            case LEFT:
                setCompoundDrawables(transformableDrawable, null, null, null);
                break;
            case TOP:
                setCompoundDrawables(null, transformableDrawable, null, null);
                break;
            case RIGHT:
                setCompoundDrawables(null, null, transformableDrawable, null);
                break;
            case BOTTOM:
                setCompoundDrawables(null, null, null, transformableDrawable);
                break;
        }

        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int min = (backgroundPosition == TransformableDrawable.BackgroundPosition.BACKGROUND) ? Math.min(getHeight(), getWidth()) : Math.min(getHeight(), getWidth())/2;
                transformableDrawable.setBounds(0, 0, min, min);
            }
        });
    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int min = Math.min(widthMeasureSpec, heightMeasureSpec);
        int drawMin = Math.min(getHeight(), getWidth())*2/3;
        switch (backgroundPosition) {
            case BACKGROUND:
                super.onMeasure(min, min);
            case LEFT:
                setPadding(getPaddingLeft() + drawMin/3, getPaddingTop(), getPaddingRight(), getPaddingBottom());
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
                break;
            case TOP:
                setPadding(getPaddingLeft(), getPaddingTop() + drawMin/2, getPaddingRight(), getPaddingBottom());
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
                break;
            case RIGHT:
                setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight() + drawMin/3, getPaddingBottom());
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
                break;
            case BOTTOM:
                setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight(), getPaddingBottom() + drawMin/2);
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
                break;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP)
            transformableDrawable.toggle();
        return super.onTouchEvent(event);
    }
}
