package com.nurmemet.windowsanim;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

/**
 * Created by nurmemet on 10/1/2016.
 */

public class WindowsStartLine extends Drawable {

    private final static int POINT_NUM = 4;
    private final static float DELTA = 0.05F;
    private final static int DURATION = 2000;
    private final static int width = 1000;
    private final static int height = 600;
    private final static int strokeWidth = 15;
    private Paint mPain;
    private Path mPath;
    private PathMeasure mPathMeasure;
    private float mFrac = 0;
    private Path mDesPath;
    private Paint mBackgroundPaint;
    private RectF mBoundsRect;

    private CustomInterpolator mInterpolater;

    public WindowsStartLine() {
        init();
    }

    private void init() {
        mBoundsRect = new RectF();
        mBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBackgroundPaint.setColor(Color.GREEN);
        mPath = new Path();
        mDesPath = new Path();
        mPain = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPain.setColor(Color.RED);
        mPain.setStrokeCap(Paint.Cap.ROUND);
        mPain.setStyle(Paint.Style.STROKE);
        mPain.setStrokeWidth(strokeWidth);
        mPath.moveTo(0, height / 2);
        mPath.lineTo(width, height / 2);
        mPathMeasure = new PathMeasure(mPath, false);
        mInterpolater = new CustomInterpolator();

    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect(0, 0, mBoundsRect.right, mBoundsRect.bottom, mBackgroundPaint);
        canvas.save();
        canvas.translate((mBoundsRect.width() - strokeWidth - width) / 2, 0);
        resetDesPath(3);
        canvas.drawPath(mDesPath, mPain);
        canvas.restore();


    }

    private void resetDesPath(int num) {
        mDesPath.reset();
        float s = mPathMeasure.getLength(), y = 0, x;

        switch (num) {
            default:
            case 3:
                x = mFrac - 3 * DELTA;
                y = mInterpolater.getInterpolation(x);
                mPathMeasure.getSegment(s * y, s * y + 1, mDesPath, true);
            case 2:
                x = mFrac - 2 * DELTA;
                ;
                y = mInterpolater.getInterpolation(x);
                mPathMeasure.getSegment(s * y, s * y + 1, mDesPath, true);
            case 1:
                x = mFrac - 1 * DELTA;
                y = mInterpolater.getInterpolation(x);
                mPathMeasure.getSegment(s * y, s * y + 1, mDesPath, true);
            case 0:
                x = mFrac;
                y = mInterpolater.getInterpolation(x);
                mPathMeasure.getSegment(s * y, s * y + 1, mDesPath, true);
                break;

        }
        return;
    }

    public void startAnim() {
        ValueAnimator animator = ValueAnimator.ofFloat(0, 1 + POINT_NUM * DELTA);
        animator.setDuration(DURATION);
        animator.setRepeatCount(-1);
        animator.setInterpolator(mInterpolater);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                mFrac = value;
                invalidateSelf();
            }
        });
        animator.start();
    }

    @Override
    public void setAlpha(int alpha) {
        mPain.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        mPain.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSPARENT;
    }

    @Override
    public int getIntrinsicWidth() {
        return width + strokeWidth;
    }

    @Override
    public int getIntrinsicHeight() {
        return height + strokeWidth;
    }


    private class CustomInterpolator implements TimeInterpolator {


        @Override
        public float getInterpolation(float input) {
            double y = 0;
            if (input >= 0 && input < 0.5) {
                y = Math.sin((double) input * Math.PI) * 1 / 2;
            } else {
                y = Math.sin((double) input * Math.PI) * (-1.0F / 2) + 1;
            }
            return (float) y;
        }
    }

    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        super.setBounds(left, top, right, bottom);
        mBoundsRect.set(left, mFrac, right, bottom);
    }
}
