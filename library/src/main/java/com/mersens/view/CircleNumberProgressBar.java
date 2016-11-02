package com.mersens.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ProgressBar;

/**
 * Created by Mersens on 2016/11/2.
 */

public class CircleNumberProgressBar extends ProgressBar {
    private static final int DEFAULT_TEXT_COLOR = 0XFFFF4081;
    private static final int DEFAULT_TEXT_SIZE = 14;
    private static final int DEFAULT_UNREACH_COLOR = 0XFF3F51B5;
    private static final int DEFAULT_UNREACH_HEIGHT = 2;
    private static final int DEFAULT_REACH_COLOR = DEFAULT_TEXT_COLOR;
    private static final int DEFAULT_REACH_HEIGHT = 3;
    private static final int DEFAULT_RADIUS = 32;


    protected int mTextColor = DEFAULT_TEXT_COLOR;
    protected int mTextSize = sp2px(DEFAULT_TEXT_SIZE);
    protected int mUnreachColor = DEFAULT_UNREACH_COLOR;
    protected int mUnreachHeight = dp2px(DEFAULT_UNREACH_HEIGHT);
    protected int mReachColor = DEFAULT_REACH_COLOR;
    protected int mReachHeight = dp2px(DEFAULT_REACH_HEIGHT);
    protected int mRadius = dp2px(DEFAULT_RADIUS);

    protected Paint mPaint;
    protected  int mMaxPaintWidth;

    public CircleNumberProgressBar(Context context) {
        this(context,null);
    }

    public CircleNumberProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CircleNumberProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public void init(AttributeSet attrs){
        final TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.CircleNumberProgressBar);
        mTextColor = ta.getColor(R.styleable.CircleNumberProgressBar_circleprogress_text_color, mTextColor);
        mTextSize = (int) ta.getDimension(R.styleable.CircleNumberProgressBar_circleprogress_text_size, mTextSize);
        mReachColor = ta.getColor(R.styleable.CircleNumberProgressBar_circleprogress_reache_color, mReachColor);
        mReachHeight = (int) ta.getDimension(R.styleable.CircleNumberProgressBar_circleprogress_reache_height, mReachHeight);
        mUnreachColor = ta.getColor(R.styleable.CircleNumberProgressBar_circleprogress_unreache_color, mUnreachColor);
        mUnreachHeight = (int) ta.getDimension(R.styleable.CircleNumberProgressBar_circleprogress_unreache__height, mUnreachHeight);
        mRadius = (int) ta.getDimension(R.styleable.CircleNumberProgressBar_circleprogress_radius, mRadius);
        ta.recycle();
        mPaint = new Paint();
        mPaint.setTextSize(mTextSize);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
    }


    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mMaxPaintWidth=Math.max(mReachHeight,mUnreachHeight);
        int expect=getPaddingRight() + getPaddingLeft() + mRadius * 2 + mMaxPaintWidth;
        int weight = resolveSize(expect,widthMeasureSpec);
        int height = resolveSize(expect,heightMeasureSpec);
        int readWidth=Math.min(weight,height);
        mRadius=(readWidth-getPaddingLeft()-getPaddingRight()-mMaxPaintWidth)/2;
        setMeasuredDimension(readWidth, readWidth);
    }



    @Override
    protected synchronized void onDraw(Canvas canvas) {
        String text=getProgress()+"%";
        float textWeight=mPaint.measureText(text);
        float textHeight=(mPaint.descent()+mPaint.ascent())/2;

        canvas.save();
        canvas.translate(getPaddingLeft()+mMaxPaintWidth/2,getPaddingRight()+mMaxPaintWidth/2);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(mUnreachColor);
        mPaint.setStrokeWidth(mUnreachHeight);
        canvas.drawCircle(mRadius,mRadius,mRadius,mPaint);

        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mTextColor);
        canvas.drawText(text,mRadius-textWeight/2,mRadius-textHeight,mPaint);


        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(mReachColor);
        mPaint.setStrokeWidth(mReachHeight);
        float sweepAngle=getProgress()*1.0f/getMax()*360;
        canvas.drawArc(new RectF(0,0,mRadius*2,mRadius*2),-90,sweepAngle,false,mPaint);
        canvas.restore();
    }



    public int sp2px(int spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal, getResources().getDisplayMetrics());
    }

    public int dp2px(int dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, getResources().getDisplayMetrics());

    }
}
