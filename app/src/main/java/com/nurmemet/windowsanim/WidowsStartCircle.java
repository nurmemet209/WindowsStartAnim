package com.nurmemet.windowsanim;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

/**
 * Created by nurmemet on 9/29/2016.
 */

public class WidowsStartCircle extends Drawable {

    private final static int DURATION=3000;
    private static final int width = 300;
    private static final int height = 300;
    private static final int strokeWidth = 15;
    private static final float delta = 0.05F;
    private Paint mPaint;
    private float t;
    private RectF mRect;
    private Path mPath;
    private PathMeasure mPathMeasure;
    private Paint mBackgroundPaint;
    private Path mDesPath;


    public WidowsStartCircle() {
        mDesPath = new Path();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(strokeWidth);
        mPath = new Path();
        mRect = new RectF();
        mPath.addArc(new RectF(-width / 2, -height / 2, width / 2, height / 2), -90F, 359.9F);
        mPathMeasure = new PathMeasure(mPath, false);
        mBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBackgroundPaint.setColor(Color.GREEN);

    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect(0, 0, mRect.right, mRect.bottom, mBackgroundPaint);
        canvas.save();
        canvas.translate(mRect.right / 2, mRect.bottom / 2);
        resetDesPath(3);
        canvas.drawPath(mDesPath, mPaint);
        canvas.restore();


    }

    private void resetDesPath(int num) {
        mDesPath.reset();
        float x = 0;
        float s = 0;
        float y = 0;
        s = mPathMeasure.getLength();
        switch (num) {
            default:
            case 3:
                x = t - 3 * delta;
                if (x < 0) {
                    x = 1 + x;
                }
                y = -x * x + 2 * x;
                mPathMeasure.getSegment(s * y, s * y + 1, mDesPath, true);
            case 2:
                x = t - 2 * delta;
                if (x < 0) {
                    x = 1 + x;
                }
                y = -x * x + 2 * x;
                mPathMeasure.getSegment(s * y, s * y + 1, mDesPath, true);
            case 1:
                x = t - delta;
                if (x < 0) {
                    x = 1 + x;
                }
                y = -x * x + 2 * x;
                mPathMeasure.getSegment(s * y, s * y + 1, mDesPath, true);
            case 0:
                x = t;
                y = -x * x + 2 * x;
                mPathMeasure.getSegment(s * y, s * y + 1, mDesPath, true);
                break;
        }

        return;
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);

    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }


    public void startAnim() {
        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
        animator.setDuration(DURATION);
        animator.setRepeatCount(-1);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                t = (float) animation.getAnimatedValue();
                invalidateSelf();
            }
        });
        animator.start();

    }


    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        super.setBounds(left, top, right, bottom);
        mRect.set(left, top, right, bottom);
    }


    @Override
    public int getIntrinsicHeight() {
        return height + strokeWidth;
    }

    @Override
    public int getIntrinsicWidth() {
        return width + strokeWidth;
    }
}
