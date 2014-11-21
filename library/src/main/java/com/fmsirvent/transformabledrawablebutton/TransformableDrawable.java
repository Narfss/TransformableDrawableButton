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
 * Created by Narfss on 21/Nov/2024
 *
 * Original by thibaultguegan on 05/09/2014.
 */
public class TransformableDrawable extends Drawable {

    private static final long ANIMATION_DURATION = 150;
    public static final int DEF_LINES_COLOR = Color.DKGRAY;
    public static final int DEF_BACKGROUND_COLOR = Color.WHITE;
    public static final int DEF_LINE_WIDTH = 4;

    private static final int PLUS_POINTS = 0;
    private static final int MINUS_POINTS = 1;
    private static final int X_POINTS = 2;
    private static final int ANGLE_DOWN_POINTS = 3;
    private static final int ANGLE_LEFT_POINTS = 4;
    private static final int ANGLE_UP_POINTS = 5;
    private static final int ANGLE_RIGHT_POINTS = 6;
    private static final int CHECK_POINTS = 7;
    private static final int ARROW_DOWN_POINTS = 8;
    private static final int ARROW_LEFT_POINTS = 9;
    private static final int ARROW_UP_POINTS = 10;
    private static final int ARROW_RIGHT_POINTS = 11;
    private static final int MENU_POINTS = 12;

    private Paint mLinePaint;
    private Paint mBackgroundPaint;

    private float[] mPoints = new float[12];
    private float[][] mPointsDraw = new float[13][12];
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

        setUpLines();
    }


    private void setUpLines(){

        //ABCDE
        //FGHIJ
        //KLMNO
        //PQRST
        //UVWXY

        float centerX = mBounds.centerX();
        float top = mBounds.top;
        float bottom = mBounds.bottom;
        float left = mBounds.left;
        float centerY = mBounds.centerY();
        float right = mBounds.right;

        //Plus points
        mPointsDraw[PLUS_POINTS][0] = centerX;
        mPointsDraw[PLUS_POINTS][1] = top;
        mPointsDraw[PLUS_POINTS][2] = centerX;
        mPointsDraw[PLUS_POINTS][3] = bottom;
        mPointsDraw[PLUS_POINTS][4] = left;
        mPointsDraw[PLUS_POINTS][5] = centerY;
        mPointsDraw[PLUS_POINTS][6] = right;
        mPointsDraw[PLUS_POINTS][7] = centerY;
        mPointsDraw[PLUS_POINTS][8] = centerX;
        mPointsDraw[PLUS_POINTS][9] = centerY;
        mPointsDraw[PLUS_POINTS][10] = centerX;
        mPointsDraw[PLUS_POINTS][11] = centerY;

        //Minus points
        mPointsDraw[MINUS_POINTS][0] = left;
        mPointsDraw[MINUS_POINTS][1] = centerY;
        mPointsDraw[MINUS_POINTS][2] = right;
        mPointsDraw[MINUS_POINTS][3] = centerY;
        mPointsDraw[MINUS_POINTS][4] = left;
        mPointsDraw[MINUS_POINTS][5] = centerY;
        mPointsDraw[MINUS_POINTS][6] = right;
        mPointsDraw[MINUS_POINTS][7] = centerY;
        mPointsDraw[MINUS_POINTS][8] = centerX;
        mPointsDraw[MINUS_POINTS][9] = centerY;
        mPointsDraw[MINUS_POINTS][10] = centerX;
        mPointsDraw[MINUS_POINTS][11] = centerY;

        //X points
        mPointsDraw[X_POINTS][0] = left;
        mPointsDraw[X_POINTS][1] = top;
        mPointsDraw[X_POINTS][2] = right;
        mPointsDraw[X_POINTS][3] = bottom;
        mPointsDraw[X_POINTS][4] = left;
        mPointsDraw[X_POINTS][5] = bottom;
        mPointsDraw[X_POINTS][6] = right;
        mPointsDraw[X_POINTS][7] = top;
        mPointsDraw[X_POINTS][8] = centerX;
        mPointsDraw[X_POINTS][9] = centerY;
        mPointsDraw[X_POINTS][10] = centerX;
        mPointsDraw[X_POINTS][11] = centerY;

        //Angle down points
        mPointsDraw[ANGLE_DOWN_POINTS][0] = left;
        mPointsDraw[ANGLE_DOWN_POINTS][1] = centerY;
        mPointsDraw[ANGLE_DOWN_POINTS][2] = centerX;
        mPointsDraw[ANGLE_DOWN_POINTS][3] = bottom;
        mPointsDraw[ANGLE_DOWN_POINTS][4] = centerX;
        mPointsDraw[ANGLE_DOWN_POINTS][5] = bottom;
        mPointsDraw[ANGLE_DOWN_POINTS][6] = right;
        mPointsDraw[ANGLE_DOWN_POINTS][7] = centerY;
        mPointsDraw[ANGLE_DOWN_POINTS][8] = centerX;
        mPointsDraw[ANGLE_DOWN_POINTS][9] = bottom;
        mPointsDraw[ANGLE_DOWN_POINTS][10] = centerX;
        mPointsDraw[ANGLE_DOWN_POINTS][11] = bottom;

        //Arrow down points
        mPointsDraw[ARROW_DOWN_POINTS][0] = left;
        mPointsDraw[ARROW_DOWN_POINTS][1] = centerY;
        mPointsDraw[ARROW_DOWN_POINTS][2] = centerX;
        mPointsDraw[ARROW_DOWN_POINTS][3] = bottom;
        mPointsDraw[ARROW_DOWN_POINTS][4] = centerX;
        mPointsDraw[ARROW_DOWN_POINTS][5] = bottom;
        mPointsDraw[ARROW_DOWN_POINTS][6] = right;
        mPointsDraw[ARROW_DOWN_POINTS][7] = centerY;
        mPointsDraw[ARROW_DOWN_POINTS][8] = centerX;
        mPointsDraw[ARROW_DOWN_POINTS][9] = top;
        mPointsDraw[ARROW_DOWN_POINTS][10] = centerX;
        mPointsDraw[ARROW_DOWN_POINTS][11] = bottom;

        //Angle up points
        mPointsDraw[ANGLE_UP_POINTS][0] = centerX;
        mPointsDraw[ANGLE_UP_POINTS][1] = top;
        mPointsDraw[ANGLE_UP_POINTS][2] = right;
        mPointsDraw[ANGLE_UP_POINTS][3] = centerY;
        mPointsDraw[ANGLE_UP_POINTS][4] = left;
        mPointsDraw[ANGLE_UP_POINTS][5] = centerY;
        mPointsDraw[ANGLE_UP_POINTS][6] = centerX;
        mPointsDraw[ANGLE_UP_POINTS][7] = top;
        mPointsDraw[ANGLE_UP_POINTS][8] = centerX;
        mPointsDraw[ANGLE_UP_POINTS][9] = top;
        mPointsDraw[ANGLE_UP_POINTS][10] = centerX;
        mPointsDraw[ANGLE_UP_POINTS][11] = top;

        //Arrow up points
        mPointsDraw[ARROW_UP_POINTS][0] = centerX;
        mPointsDraw[ARROW_UP_POINTS][1] = top;
        mPointsDraw[ARROW_UP_POINTS][2] = right;
        mPointsDraw[ARROW_UP_POINTS][3] = centerY;
        mPointsDraw[ARROW_UP_POINTS][4] = left;
        mPointsDraw[ARROW_UP_POINTS][5] = centerY;
        mPointsDraw[ARROW_UP_POINTS][6] = centerX;
        mPointsDraw[ARROW_UP_POINTS][7] = top;
        mPointsDraw[ARROW_UP_POINTS][8] = centerX;
        mPointsDraw[ARROW_UP_POINTS][9] = top;
        mPointsDraw[ARROW_UP_POINTS][10] = centerX;
        mPointsDraw[ARROW_UP_POINTS][11] = bottom;

        //Angle left points
        mPointsDraw[ANGLE_LEFT_POINTS][0] = left;
        mPointsDraw[ANGLE_LEFT_POINTS][1] = centerY;
        mPointsDraw[ANGLE_LEFT_POINTS][2] = centerX;
        mPointsDraw[ANGLE_LEFT_POINTS][3] = bottom;
        mPointsDraw[ANGLE_LEFT_POINTS][4] = left;
        mPointsDraw[ANGLE_LEFT_POINTS][5] = centerY;
        mPointsDraw[ANGLE_LEFT_POINTS][6] = centerX;
        mPointsDraw[ANGLE_LEFT_POINTS][7] = top;
        mPointsDraw[ANGLE_LEFT_POINTS][8] = left;
        mPointsDraw[ANGLE_LEFT_POINTS][9] = centerY;
        mPointsDraw[ANGLE_LEFT_POINTS][10] = left;
        mPointsDraw[ANGLE_LEFT_POINTS][11] = centerY;


        //Arrow left points
        mPointsDraw[ARROW_LEFT_POINTS][0] = left;
        mPointsDraw[ARROW_LEFT_POINTS][1] = centerY;
        mPointsDraw[ARROW_LEFT_POINTS][2] = centerX;
        mPointsDraw[ARROW_LEFT_POINTS][3] = bottom;
        mPointsDraw[ARROW_LEFT_POINTS][4] = left;
        mPointsDraw[ARROW_LEFT_POINTS][5] = centerY;
        mPointsDraw[ARROW_LEFT_POINTS][6] = centerX;
        mPointsDraw[ARROW_LEFT_POINTS][7] = top;
        mPointsDraw[ARROW_LEFT_POINTS][8] = left;
        mPointsDraw[ARROW_LEFT_POINTS][9] = centerY;
        mPointsDraw[ARROW_LEFT_POINTS][10] = right;
        mPointsDraw[ARROW_LEFT_POINTS][11] = centerY;


        //Angle right points
        mPointsDraw[ANGLE_RIGHT_POINTS][0] = centerX;
        mPointsDraw[ANGLE_RIGHT_POINTS][1] = top;
        mPointsDraw[ANGLE_RIGHT_POINTS][2] = right;
        mPointsDraw[ANGLE_RIGHT_POINTS][3] = centerY;
        mPointsDraw[ANGLE_RIGHT_POINTS][4] = centerX;
        mPointsDraw[ANGLE_RIGHT_POINTS][5] = bottom;
        mPointsDraw[ANGLE_RIGHT_POINTS][6] = right;
        mPointsDraw[ANGLE_RIGHT_POINTS][7] = centerY;
        mPointsDraw[ANGLE_RIGHT_POINTS][8] = right;
        mPointsDraw[ANGLE_RIGHT_POINTS][9] = centerY;
        mPointsDraw[ANGLE_RIGHT_POINTS][10] = right;
        mPointsDraw[ANGLE_RIGHT_POINTS][11] = centerY;

        //Arrow right points
        mPointsDraw[ARROW_RIGHT_POINTS][0] = centerX;
        mPointsDraw[ARROW_RIGHT_POINTS][1] = top;
        mPointsDraw[ARROW_RIGHT_POINTS][2] = right;
        mPointsDraw[ARROW_RIGHT_POINTS][3] = centerY;
        mPointsDraw[ARROW_RIGHT_POINTS][4] = centerX;
        mPointsDraw[ARROW_RIGHT_POINTS][5] = bottom;
        mPointsDraw[ARROW_RIGHT_POINTS][6] = right;
        mPointsDraw[ARROW_RIGHT_POINTS][7] = centerY;
        mPointsDraw[ARROW_RIGHT_POINTS][8] = left;
        mPointsDraw[ARROW_RIGHT_POINTS][9] = centerY;
        mPointsDraw[ARROW_RIGHT_POINTS][10] = right;
        mPointsDraw[ARROW_RIGHT_POINTS][11] = centerY;


        //Check points
        mPointsDraw[CHECK_POINTS][0] = right;
        mPointsDraw[CHECK_POINTS][1] = top;
        mPointsDraw[CHECK_POINTS][2] = centerX;
        mPointsDraw[CHECK_POINTS][3] = bottom;
        mPointsDraw[CHECK_POINTS][4] = left;
        mPointsDraw[CHECK_POINTS][5] = centerY;
        mPointsDraw[CHECK_POINTS][6] = centerX;
        mPointsDraw[CHECK_POINTS][7] = bottom;
        mPointsDraw[CHECK_POINTS][8] = centerX;
        mPointsDraw[CHECK_POINTS][9] = bottom;
        mPointsDraw[CHECK_POINTS][10] = centerX;
        mPointsDraw[CHECK_POINTS][11] = bottom;

        //Check points
        mPointsDraw[MENU_POINTS][0] = left;
        mPointsDraw[MENU_POINTS][1] = top;
        mPointsDraw[MENU_POINTS][2] = right;
        mPointsDraw[MENU_POINTS][3] = top;
        mPointsDraw[MENU_POINTS][4] = left;
        mPointsDraw[MENU_POINTS][5] = centerY;
        mPointsDraw[MENU_POINTS][6] = right;
        mPointsDraw[MENU_POINTS][7] = centerY;
        mPointsDraw[MENU_POINTS][8] = left;
        mPointsDraw[MENU_POINTS][9] = bottom;
        mPointsDraw[MENU_POINTS][10] = right;
        mPointsDraw[MENU_POINTS][11] = bottom;

        //Transitional points
        mPoints[0] = mPointsDraw[unCheckDraw][0];
        mPoints[1] = mPointsDraw[unCheckDraw][1];
        mPoints[2] = mPointsDraw[unCheckDraw][2];
        mPoints[3] = mPointsDraw[unCheckDraw][3];
        mPoints[4] = mPointsDraw[unCheckDraw][4];
        mPoints[5] = mPointsDraw[unCheckDraw][5];
        mPoints[6] = mPointsDraw[unCheckDraw][6];
        mPoints[7] = mPointsDraw[unCheckDraw][7];
        mPoints[8] = mPointsDraw[unCheckDraw][8];
        mPoints[9] = mPointsDraw[unCheckDraw][9];
        mPoints[10] = mPointsDraw[unCheckDraw][10];
        mPoints[11] = mPointsDraw[unCheckDraw][11];


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

        canvas.save();
        canvas.drawLine(x(4), y(4), x(5), y(5), mLinePaint);
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


    private PointProperty mPropertyPointEX = new XPointProperty(4);
    private PointProperty mPropertyPointEY = new YPointProperty(4);
    private PointProperty mPropertyPointFX = new XPointProperty(5);
    private PointProperty mPropertyPointFY = new YPointProperty(5);

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
                ObjectAnimator.ofFloat(this, mPropertyPointDY, getPoint(7, enable)),

                ObjectAnimator.ofFloat(this, mPropertyPointEX, getPoint(8, enable)),
                ObjectAnimator.ofFloat(this, mPropertyPointEY, getPoint(9, enable)),
                ObjectAnimator.ofFloat(this, mPropertyPointFX, getPoint(10, enable)),
                ObjectAnimator.ofFloat(this, mPropertyPointFY, getPoint(11, enable))
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
