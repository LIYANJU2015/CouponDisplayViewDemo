package com.example.coupondisplayviewlibrary;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by liyanju on 16/7/26.
 */
public class CouponDisplayView extends LinearLayout {

    private int mSectorNum;

    private int mSectorWidth = 30;

    private float mGap = 8;//间距

    private float mRemain;

    private RectF mRectTop = new RectF();

    private RectF mBottomTop = new RectF();

    private Paint mPaint;

    private int mWidth;

    private int mHeight;

    private PorterDuffXfermode mPorterDuffXfermode;

    public CouponDisplayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.WHITE);

        mPorterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);

        setWillNotDraw(false);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mSectorNum = (int) ((w - mGap) / (mSectorWidth + mGap));
        mRemain = (int) ((w - mGap) % (mSectorWidth + mGap));

        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float left = mGap;

        int sc = canvas.saveLayer(0, 0, getWidth(), getWidth(), null, Canvas.MATRIX_SAVE_FLAG |
                Canvas.CLIP_SAVE_FLAG |
                Canvas.HAS_ALPHA_LAYER_SAVE_FLAG |
                Canvas.FULL_COLOR_LAYER_SAVE_FLAG |
                Canvas.CLIP_TO_LAYER_SAVE_FLAG);

        mPaint.setColor(0xFF66AAFF);
        canvas.drawRect(0f, 0f, (float) getWidth(), (float) getHeight(), mPaint);

        mPaint.setXfermode(mPorterDuffXfermode);

        for (int i = 0; i < mSectorNum; i++) {
            if (i == 0 && mRemain != 0) {
                left = mGap + mRemain / 2;
                mRectTop.set(left, -(mSectorWidth / 2), left + mSectorWidth, mSectorWidth / 2);
            } else {
                mRectTop.left = left + (mSectorWidth + mGap) * i;
                mRectTop.right = mRectTop.left + mSectorWidth;
                mRectTop.bottom = mSectorWidth / 2;
                mRectTop.top = -(mSectorWidth / 2);
            }

            canvas.drawArc(mRectTop, 0, 180, true, mPaint);

            mBottomTop.set(mRectTop.left, mHeight - mSectorWidth / 2, mRectTop.right, mHeight + mSectorWidth / 2);

            canvas.drawArc(mBottomTop, 0, -180, true, mPaint);
        }

        mPaint.setXfermode(null);

        canvas.restoreToCount(sc);
    }
}
