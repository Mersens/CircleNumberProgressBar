package com.mersens.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ProgressBar;

/**
 * Created by Mersens on 2016/11/2.
 */

public class NumberProgressBar extends ProgressBar {
    private static final int DEFAULT_TEXT_COLOR = 0XFFFF4081;
    private static final int DEFAULT_TEXT_SIZE = 12;
    private static final int DEFAULT_UNREACH_COLOR = 0XFF3F51B5;
    private static final int DEFAULT_UNREACH_HEIGHT = 2;
    private static final int DEFAULT_REACH_COLOR = DEFAULT_TEXT_COLOR;
    private static final int DEFAULT_REACH_HEIGHT = 2;
    private static final int DEFAULT_OFFSET = 10;


    protected int mTextColor = DEFAULT_TEXT_COLOR;
    protected int mTextSize = sp2px(DEFAULT_TEXT_SIZE);
    protected int mUnreachColor = DEFAULT_UNREACH_COLOR;
    protected int mUnreachHeight = dp2px(DEFAULT_UNREACH_HEIGHT);
    protected int mReachColor = DEFAULT_REACH_COLOR;
    protected int mReachHeight = dp2px(DEFAULT_REACH_HEIGHT);
    protected int mOffset = dp2px(DEFAULT_OFFSET);

    protected Paint mPaint ;
    protected  Paint mTextPaint;
    protected int mRealWidth;

    public NumberProgressBar(Context context) {
        this(context,null);
    }

    public NumberProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public NumberProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }


    public void init(AttributeSet attrs){
        final TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.NumberProgressBar);
        mTextColor = ta.getColor(R.styleable.NumberProgressBar_numprogress_text_color, mTextColor);
        mTextSize = (int) ta.getDimension(R.styleable.NumberProgressBar_numprogress_text_size, mTextSize);
        mReachColor = ta.getColor(R.styleable.NumberProgressBar_numprogress_reache_color, mReachColor);
        mReachHeight = (int) ta.getDimension(R.styleable.NumberProgressBar_numprogress_reache_height, mReachHeight);
        mUnreachColor = ta.getColor(R.styleable.NumberProgressBar_numprogress_unreache_color, mUnreachColor);
        mUnreachHeight = (int) ta.getDimension(R.styleable.NumberProgressBar_numprogress_unreache__height, mUnreachHeight);
        mOffset = (int) ta.getDimension(R.styleable.NumberProgressBar_numprogress_offset, mOffset);
        ta.recycle();
        mPaint = new Paint();
        mTextPaint=new Paint();
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setAntiAlias(true);

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
    }


    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int textHeight = (int) (mTextPaint.descent() - mTextPaint.ascent());
        int maxHeight=Math.max(textHeight,Math.max(mReachHeight,mUnreachHeight));
        int expectHeight=getPaddingTop()+getPaddingBottom()+maxHeight;
        int heightVal=resolveSize(expectHeight,heightMeasureSpec);
        int widthVal = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(widthVal,heightVal);
        mRealWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        canvas.save();
        canvas.translate(getPaddingLeft(), getHeight() / 2);
        boolean noNeedUnreach = false;
        float radio = getProgress() * 1.0f / getMax();

        String text = getProgress() + "%";

        int textWidth = (int) mTextPaint.measureText(text);

        float progressX = radio * mRealWidth;
        if (progressX + textWidth > mRealWidth) {
            progressX = mRealWidth - textWidth;
            noNeedUnreach = true;
        }
        float endx = progressX - mOffset / 2;
        if (endx > 0) {
            mPaint.setColor(mReachColor);
            mPaint.setStrokeWidth(mReachHeight);
            canvas.drawLine(0, 0, endx, 0, mPaint);
        }

        mTextPaint.setColor(mTextColor);
        mTextPaint.setStrokeWidth(mUnreachHeight);
        int y = (int) (-(mTextPaint.descent() + mTextPaint.ascent()) / 2);
        canvas.drawText(text, progressX, y, mTextPaint);
        if (!noNeedUnreach) {
            float startX = progressX + mOffset / 2 + textWidth;
            mPaint.setColor(mUnreachColor);
            mPaint.setStrokeWidth(mUnreachHeight);
            canvas.drawLine(startX, 0, mRealWidth, 0, mPaint);
        }
        canvas.restore();
    }

    public int sp2px(int spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal, getResources().getDisplayMetrics());
    }

    public int dp2px(int dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, getResources().getDisplayMetrics());

    }
}
