package com.fmsirvent.transformabledrawablebutton;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.CompoundButton;

import java.util.Arrays;
import java.util.List;


/**
 * Created by Narfss on 21/Nov/2024
 */
public class TransformableButton extends Button {
    private TransformableDrawable transformableDrawable;
    private TransformableDrawable.BackgroundPosition backgroundPosition;
    private OnCheckedChangeListener onCheckedChangeListener = null;
    private ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener = null;

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

    public TransformableButton(Context context, Draw unCheckDraw, Draw checkDraw, int strokeColor, int backgroundColor, PositionDraw positionDraw) {
        super(context);
        setAttributes(context, unCheckDraw.getNum(), checkDraw.getNum(), strokeColor, backgroundColor, positionDraw.getNum());
    }

    private void init(final Context context, AttributeSet attrs, int defStyle) {
        TypedArray arr = getContext().obtainStyledAttributes(attrs, R.styleable.TransformableButtonAttrs);
        int unCheckDraw = arr.getInt(R.styleable.TransformableButtonAttrs_unCheckDraw, 0);
        int checkDraw = arr.getInt(R.styleable.TransformableButtonAttrs_checkDraw, 1);
        int strokeColor = arr.getColor(R.styleable.TransformableButtonAttrs_strokeColor, TransformableDrawable.DEF_LINES_COLOR);
        int backgroundColor = arr.getColor(R.styleable.TransformableButtonAttrs_backgroundColor, TransformableDrawable.DEF_BACKGROUND_COLOR);
        int drawablePosition = arr.getInt(R.styleable.TransformableButtonAttrs_drawablePosition, TransformableDrawable.BackgroundPosition.BACKGROUND.getNum());

        setAttributes(context, unCheckDraw, checkDraw, strokeColor, backgroundColor, drawablePosition);
    }

    private void setAttributes(Context context, int unCheckDraw, int checkDraw, int strokeColor, int backgroundColor, int drawablePosition) {
        transformableDrawable = new TransformableDrawable(context, unCheckDraw, checkDraw, TransformableDrawable.DEF_LINE_WIDTH, strokeColor, backgroundColor);

        backgroundPosition = TransformableDrawable.BackgroundPosition.enumOf(drawablePosition);

        transformableDrawable.setPositionDrawable(backgroundPosition);

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
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
                        transformableDrawable.setBounds(min, min / 2, min * 2, min * 3 / 2);
                        setCompoundDrawables(transformableDrawable, null, null, null);
                        break;
                    case TOP:
                        transformableDrawable.setBounds((width - min) / 2, min, ((width - min) / 2) + min, min * 2);
                        setCompoundDrawables(null, transformableDrawable, null, null);
                        break;
                    case RIGHT:
                        transformableDrawable.setBounds(0, min / 2, min, min * 3 / 2);
                        setCompoundDrawables(null, null, transformableDrawable, null);
                        break;
                    case BOTTOM:
                        transformableDrawable.setBounds((width - min) / 2, (height) / 2 - min, ((width - min) / 2) + min, (height / 2) - min + min);
                        setCompoundDrawables(null, null, null, transformableDrawable);
                        break;
                }
            }
        };
        getViewTreeObserver().addOnGlobalLayoutListener(onGlobalLayoutListener);
    }

    @Override
    protected void onDetachedFromWindow() {
        ViewTreeObserver viewTreeObserver = getViewTreeObserver();
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            viewTreeObserver.removeOnGlobalLayoutListener(onGlobalLayoutListener);
        } else {
            viewTreeObserver.removeGlobalOnLayoutListener(onGlobalLayoutListener);
        }
        super.onDetachedFromWindow();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (isInEditMode()) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        } else {
            int min = Math.min(widthMeasureSpec, heightMeasureSpec);
            switch (backgroundPosition) {
                case BACKGROUND:
                    super.onMeasure(min, min);
                default:
                    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
                    break;
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP)
            transformableDrawable.toggle();
        if (onCheckedChangeListener != null)
            onCheckedChangeListener.onCheckedChanged(this, isChecked());
        return super.onTouchEvent(event);
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        this.onCheckedChangeListener = onCheckedChangeListener;
    }

    public Boolean isChecked() {
        return transformableDrawable!= null && transformableDrawable.isChecked();
    }

    public void setChecked(boolean checked, boolean animated) {
        transformableDrawable.setChecked(checked, animated);
    }

    public enum Draw {
        PLUS,
        MINUS,
        X,
        ANGLE_DOWN,
        ANGLE_LEFT,
        ANGLE_UP,
        ANGLE_RIGHT,
        CHECK,
        ARROW_DOWN,
        ARROW_LEFT,
        ARROW_UP,
        ARROW_RIGHT,
        MENU;

        public int getNum() {
            return getDraws().indexOf(this);
        }

        private static List<Draw> getDraws() {
            return Arrays.asList(PLUS,
                    MINUS,
                    X,
                    ANGLE_DOWN,
                    ANGLE_LEFT,
                    ANGLE_UP,
                    ANGLE_RIGHT,
                    CHECK,
                    ARROW_DOWN,
                    ARROW_LEFT,
                    ARROW_UP,
                    ARROW_RIGHT,
                    MENU);
        }

        public static Draw enumOf(int drawablePosition) {
            return getDraws().get(drawablePosition);
        }
    }

    public enum PositionDraw {
        BACKGROUND,
        LEFT,
        TOP,
        RIGHT,
        BOTTOM;

        public int getNum() {
            return getDrawPositions().indexOf(this);
        }

        private static List<PositionDraw> getDrawPositions() {
            return Arrays.asList(
                    BACKGROUND,
                    LEFT,
                    TOP,
                    RIGHT,
                    BOTTOM);
        }

        public static PositionDraw enumOf(int drawablePosition) {
            return getDrawPositions().get(drawablePosition);
        }
    }

    public static interface OnCheckedChangeListener {
        void onCheckedChanged(TransformableButton buttonView, boolean isChecked);
    }

}
