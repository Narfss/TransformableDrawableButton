package com.fmsirvent.transformabledrawablebutton;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;

import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.util.Property;

import java.util.Arrays;
import java.util.List;

/**
 * Created by thibaultguegan on 05/09/2014.
 */
public class TransformableDrawable extends Drawable {
    private static final long ANIMATION_DURATION = 150;
    public static final int DEF_LINES_COLOR = Color.DKGRAY;
    public static final int DEF_BACKGROUND_COLOR = Color.WHITE;
    public static final int DEF_LINE_WIDTH = 4;
    private static final int PLUS_POINTS = 0;
    private static final int MINUS_POINTS = 1;
    private static final int X_POINTS = 2;
    private static final int ARROW_DOWN_POINTS = 3;
    private static final int ARROW_LEFT_POINTS = 4;
    private static final int ARROW_UP_POINTS = 5;
    private static final int ARROW_RIGHT_POINTS = 6;
    private static final int CHECK_POINTS = 7;

    private Paint mLinePaint;
    private Paint mBackgroundPaint;

    private float[] mPoints = new float[8];
    private float[][] mPointsDraw = new float[8][8];
    private final RectF mBounds = new RectF();

    private int mStrokeWidth = DEF_LINE_WIDTH;
    private int mLineColor = DEF_LINES_COLOR;
    private int mBackgroundColor = DEF_BACKGROUND_COLOR;

    private boolean mChecked = false;
    private Context context;
    private final int unCheckDraw;
    private final int checkDraw;
    private BackgroundPosition positionDrawable = BackgroundPosition.BACKGROUND;

    public TransformableDrawable(Context context){
        this(context, PLUS_POINTS, MINUS_POINTS, DEF_LINE_WIDTH, DEF_LINES_COLOR, DEF_BACKGROUND_COLOR);
        this.context = context;
    }

    public TransformableDrawable(Context context, int unCheckDraw, int checkDraw, int strokeWidth, int lineColor, int backgroundColor){
        this.context = context;
        this.unCheckDraw = unCheckDraw;
        this.checkDraw = checkDraw;
        Resources r = context.getResources();
        this.mStrokeWidth =  Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, strokeWidth, r.getDisplayMetrics()));
        this.mLineColor = lineColor;
        this.mBackgroundColor = backgroundColor;

        setUp();
    }

    private void setUp(){
        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setColor(mLineColor);
        mLinePaint.setStrokeWidth(mStrokeWidth);
        mLinePaint.setStrokeCap(Paint.Cap.ROUND);

        mBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBackgroundPaint.setStyle(Paint.Style.FILL);
        mBackgroundPaint.setColor(mBackgroundColor);
    }


    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);

        float padding = bounds.width()/4;

        long deltaX = 0;
        long deltaY = 0;
        switch (positionDrawable) {
            case BACKGROUND:
                break;
            case LEFT:
                deltaX = bounds.height();
                deltaY = bounds.height() / 2;
                break;
            case TOP:
                deltaY = bounds.height();
                deltaX = bounds.height() / 2;
                break;
            case RIGHT:
                deltaY = bounds.height() / 2;
                break;
            case BOTTOM:
                deltaX = bounds.height() / 2;
                break;
        }

        mBounds.left = bounds.left + padding - deltaX;
        mBounds.right = bounds.right - padding - deltaX;
        mBounds.top = bounds.top + padding - deltaY;
        mBounds.bottom = bounds.bottom - padding - deltaY;

        setUpMenuLines();
    }


    private void setUpMenuLines(){

        //ABCDE
        //FGHIJ
        //KLMNO
        //PQRST
        //UVWXY


        //Plus points
        mPointsDraw[PLUS_POINTS][0] = mBounds.centerX();
        mPointsDraw[PLUS_POINTS][1] = mBounds.top;
        mPointsDraw[PLUS_POINTS][2] = mBounds.centerX();
        mPointsDraw[PLUS_POINTS][3] = mBounds.bottom;
        mPointsDraw[PLUS_POINTS][4] = mBounds.left;
        mPointsDraw[PLUS_POINTS][5] = mBounds.centerY();
        mPointsDraw[PLUS_POINTS][6] = mBounds.right;
        mPointsDraw[PLUS_POINTS][7] = mBounds.centerY();

        //Minus points
        mPointsDraw[MINUS_POINTS][0] = mBounds.left;
        mPointsDraw[MINUS_POINTS][1] = mBounds.centerY();
        mPointsDraw[MINUS_POINTS][2] = mBounds.right;
        mPointsDraw[MINUS_POINTS][3] = mBounds.centerY();
        mPointsDraw[MINUS_POINTS][4] = mBounds.left;
        mPointsDraw[MINUS_POINTS][5] = mBounds.centerY();
        mPointsDraw[MINUS_POINTS][6] = mBounds.right;
        mPointsDraw[MINUS_POINTS][7] = mBounds.centerY();

        //X points
        mPointsDraw[X_POINTS][0] = mBounds.left;
        mPointsDraw[X_POINTS][1] = mBounds.top;
        mPointsDraw[X_POINTS][2] = mBounds.right;
        mPointsDraw[X_POINTS][3] = mBounds.bottom;
        mPointsDraw[X_POINTS][4] = mBounds.right;
        mPointsDraw[X_POINTS][5] = mBounds.top;
        mPointsDraw[X_POINTS][6] = mBounds.left;
        mPointsDraw[X_POINTS][7] = mBounds.bottom;

        //Arrow down points
        mPointsDraw[ARROW_DOWN_POINTS][0] = mBounds.left;
        mPointsDraw[ARROW_DOWN_POINTS][1] = mBounds.centerY();
        mPointsDraw[ARROW_DOWN_POINTS][2] = mBounds.centerX();
        mPointsDraw[ARROW_DOWN_POINTS][3] = mBounds.bottom;
        mPointsDraw[ARROW_DOWN_POINTS][4] = mBounds.right;
        mPointsDraw[ARROW_DOWN_POINTS][5] = mBounds.centerY();
        mPointsDraw[ARROW_DOWN_POINTS][6] = mBounds.centerX();
        mPointsDraw[ARROW_DOWN_POINTS][7] = mBounds.bottom;

        //Arrow up points
        mPointsDraw[ARROW_UP_POINTS][0] = mBounds.left;
        mPointsDraw[ARROW_UP_POINTS][1] = mBounds.centerY();
        mPointsDraw[ARROW_UP_POINTS][2] = mBounds.centerX();
        mPointsDraw[ARROW_UP_POINTS][3] = mBounds.top;
        mPointsDraw[ARROW_UP_POINTS][4] = mBounds.right;
        mPointsDraw[ARROW_UP_POINTS][5] = mBounds.centerY();
        mPointsDraw[ARROW_UP_POINTS][6] = mBounds.centerX();
        mPointsDraw[ARROW_UP_POINTS][7] = mBounds.top;

        //Arrow left points
        mPointsDraw[ARROW_LEFT_POINTS][0] = mBounds.centerX();
        mPointsDraw[ARROW_LEFT_POINTS][1] = mBounds.top;
        mPointsDraw[ARROW_LEFT_POINTS][2] = mBounds.left;
        mPointsDraw[ARROW_LEFT_POINTS][3] = mBounds.centerY();
        mPointsDraw[ARROW_LEFT_POINTS][4] = mBounds.centerX();
        mPointsDraw[ARROW_LEFT_POINTS][5] = mBounds.bottom;
        mPointsDraw[ARROW_LEFT_POINTS][6] = mBounds.left;
        mPointsDraw[ARROW_LEFT_POINTS][7] = mBounds.centerY();


        //Arrow right points
        mPointsDraw[ARROW_RIGHT_POINTS][0] = mBounds.centerX();
        mPointsDraw[ARROW_RIGHT_POINTS][1] = mBounds.top;
        mPointsDraw[ARROW_RIGHT_POINTS][2] = mBounds.right;
        mPointsDraw[ARROW_RIGHT_POINTS][3] = mBounds.centerY();
        mPointsDraw[ARROW_RIGHT_POINTS][4] = mBounds.centerX();
        mPointsDraw[ARROW_RIGHT_POINTS][5] = mBounds.bottom;
        mPointsDraw[ARROW_RIGHT_POINTS][6] = mBounds.right;
        mPointsDraw[ARROW_RIGHT_POINTS][7] = mBounds.centerY();


        //Check points
        mPointsDraw[CHECK_POINTS][0] = mBounds.left;
        mPointsDraw[CHECK_POINTS][1] = mBounds.centerY();
        mPointsDraw[CHECK_POINTS][2] = mBounds.centerX();
        mPointsDraw[CHECK_POINTS][3] = mBounds.bottom;
        mPointsDraw[CHECK_POINTS][4] = mBounds.right;
        mPointsDraw[CHECK_POINTS][5] = mBounds.top;
        mPointsDraw[CHECK_POINTS][6] = mBounds.centerX();
        mPointsDraw[CHECK_POINTS][7] = mBounds.bottom;

        //Transitional points
        mPoints[0] = mPointsDraw[unCheckDraw][0];
        mPoints[1] = mPointsDraw[unCheckDraw][1];
        mPoints[2] = mPointsDraw[unCheckDraw][2];
        mPoints[3] = mPointsDraw[unCheckDraw][3];
        mPoints[4] = mPointsDraw[unCheckDraw][4];
        mPoints[5] = mPointsDraw[unCheckDraw][5];
        mPoints[6] = mPointsDraw[unCheckDraw][6];
        mPoints[7] = mPointsDraw[unCheckDraw][7];


    }

    private float getPoint(int i, boolean enable) {
        return mPointsDraw[(enable) ? checkDraw : unCheckDraw][i];
    }

    private float x(int pointIndex) {
        return mPoints[xPosition(pointIndex)];
    }

    private float y(int pointIndex) {
        return mPoints[yPosition(pointIndex)];
    }

    private int xPosition(int pointIndex) {
        return pointIndex*2;
    }

    private int yPosition(int pointIndex) {
        return xPosition(pointIndex) + 1;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawCircle(mBounds.centerX(), mBounds.centerY(), mBounds.width(), mBackgroundPaint);

        canvas.save();
        canvas.drawLine(x(0), y(0), x(1), y(1), mLinePaint);
        canvas.restore();

        canvas.save();
        canvas.drawLine(x(2), y(2), x(3), y(3), mLinePaint);
        canvas.restore();

    }

    public void toggle(){
        if(mChecked){
            animateToDisable();
        } else {
            animateToEnable();
        }
    }

    private void animateToEnable() {
        setChecked(true, true);
    }

    private void animateToDisable() {
        setChecked(false, true);
    }

    @Override
    public void setAlpha(int alpha) {
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
    }

    @Override
    public int getOpacity() {
        return PixelFormat.OPAQUE;
    }

    private PointProperty mPropertyPointAX = new XPointProperty(0);
    private PointProperty mPropertyPointAY = new YPointProperty(0);
    private PointProperty mPropertyPointBX = new XPointProperty(1);
    private PointProperty mPropertyPointBY = new YPointProperty(1);

    private PointProperty mPropertyPointCX = new XPointProperty(2);
    private PointProperty mPropertyPointCY = new YPointProperty(2);
    private PointProperty mPropertyPointDX = new XPointProperty(3);
    private PointProperty mPropertyPointDY = new YPointProperty(3);

    public Boolean isChecked() {
        return mChecked;
    }

    public void setChecked(boolean enable, boolean animated) {
        mChecked = enable;

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(this, mPropertyPointAX, getPoint(0, enable)),
                ObjectAnimator.ofFloat(this, mPropertyPointAY, getPoint(1, enable)),
                ObjectAnimator.ofFloat(this, mPropertyPointBX, getPoint(2, enable)),
                ObjectAnimator.ofFloat(this, mPropertyPointBY, getPoint(3, enable)),

                ObjectAnimator.ofFloat(this, mPropertyPointCX, getPoint(4, enable)),
                ObjectAnimator.ofFloat(this, mPropertyPointCY, getPoint(5, enable)),
                ObjectAnimator.ofFloat(this, mPropertyPointDX, getPoint(6, enable)),
                ObjectAnimator.ofFloat(this, mPropertyPointDY, getPoint(7, enable))
        );
        animatorSet.setDuration(animated ? ANIMATION_DURATION : 0).start();
    }

    public void setPositionDrawable(BackgroundPosition positionDrawable) {
        this.positionDrawable = positionDrawable;
    }

    public BackgroundPosition getPositionDrawable() {
        return positionDrawable;
    }

    public enum BackgroundPosition {
        BACKGROUND, LEFT, TOP, RIGHT, BOTTOM;

        public int getNum() {
            return getBackgroundPositions().indexOf(this);
        }

        private static List<BackgroundPosition> getBackgroundPositions() {
            return Arrays.asList(BACKGROUND, LEFT, TOP, RIGHT, BOTTOM);
        }


        public static BackgroundPosition enumOf(int drawablePosition) {
            return getBackgroundPositions().get(drawablePosition);
        }
    }

    private abstract class PointProperty extends Property<TransformableDrawable, Float> {

        protected int mPointIndex;

        private PointProperty(int pointIndex) {
            super(Float.class, "point_" + pointIndex);
            mPointIndex = pointIndex;
        }
    }

    private class XPointProperty extends PointProperty {

        private XPointProperty(int pointIndex) {
            super(pointIndex);
        }

        @Override
        public Float get(TransformableDrawable object) {
            return object.x(mPointIndex);
        }

        @Override
        public void set(TransformableDrawable object, Float value) {
            object.mPoints[object.xPosition(mPointIndex)] = value;
            invalidateSelf();
        }
    }

    private class YPointProperty extends PointProperty {

        private YPointProperty(int pointIndex) {
            super(pointIndex);
        }

        @Override
        public Float get(TransformableDrawable object) {
            return object.y(mPointIndex);
        }

        @Override
        public void set(TransformableDrawable object, Float value) {
            object.mPoints[object.yPosition(mPointIndex)] = value;
            invalidateSelf();
        }
    }
}
