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
        if (!isInEditMode()) {
            init(context, null, 0);
        }
    }

    public TransformableButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            init(context, attrs, 0);
        }
    }

    public TransformableButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (!isInEditMode()) {
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
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int height = getHeight();
                int width = getWidth();
                int min = (backgroundPosition == TransformableDrawable.BackgroundPosition.BACKGROUND) ? Math.min(height, width) : Math.min(height, width) / 2;

                switch (backgroundPosition) {
                    case BACKGROUND:
                        transformableDrawable.setBounds(0, 0, min, min);
                        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                            setBackgroundDrawable(transformableDrawable);
                        } else {
                            setBackground(transformableDrawable);
                        }
                        break;
                    case LEFT:
                        transformableDrawable.setBounds(min, min/2, min*2, min*3/2);
                        setCompoundDrawables(transformableDrawable, null, null, null);
                        break;
                    case TOP:
                        transformableDrawable.setBounds((width-min)/2, min, ((width-min)/2) + min,  min*2);
                        setCompoundDrawables(null, transformableDrawable, null, null);
                        break;
                    case RIGHT:
                        transformableDrawable.setBounds(0, min/2, min, min*3/2);
                        setCompoundDrawables(null, null, transformableDrawable, null);
                        break;
                    case BOTTOM:
                        transformableDrawable.setBounds((width-min)/2, (height) / 2 - min, ((width-min)/2) + min,  (height / 2) - min + min);
                        setCompoundDrawables(null, null, null, transformableDrawable);
                        break;
                }
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int min = Math.min(widthMeasureSpec, heightMeasureSpec);
        switch (backgroundPosition) {
            case BACKGROUND:
                super.onMeasure(min, min);
            default:
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
