package com.github.library.bubbleview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.github.bubbleview.R;


/**
 * Created by lgp on 2015/3/25.
 */
public class BubbleLinearLayout extends LinearLayout {
    private BubbleDrawable bubbleDrawable;
    private float mArrowWidth;
    private float mAngle;
    private float mArrowHeight;
    private float mArrowPosition;
    private BubbleDrawable.ArrowLocation mArrowLocation;
    private int bubbleColor;
    private boolean mArrowCenter;
    private int mArrowHorizontal; //角标水平方向的位置，0代表左边，1右边
    private int mArrowMargin; //角标水平方向左边或者右边的边距

    public BubbleLinearLayout(Context context) {
        super(context);
        initView(null);
    }

    public BubbleLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(attrs);
    }


    private void initView(AttributeSet attrs){
        if (attrs != null){
            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.BubbleView);
            mArrowWidth = array.getDimension(R.styleable.BubbleView_arrowWidth,
                    BubbleDrawable.Builder.DEFAULT_ARROW_WITH);
            mArrowHeight = array.getDimension(R.styleable.BubbleView_arrowHeight,
                    BubbleDrawable.Builder.DEFAULT_ARROW_HEIGHT);
            mAngle = array.getDimension(R.styleable.BubbleView_angle,
                    BubbleDrawable.Builder.DEFAULT_ANGLE);
            mArrowPosition = array.getDimension(R.styleable.BubbleView_arrowPosition,
                    BubbleDrawable.Builder.DEFAULT_ARROW_POSITION);
            bubbleColor = array.getColor(R.styleable.BubbleView_bubbleColor,
                    BubbleDrawable.Builder.DEFAULT_BUBBLE_COLOR);
            int location = array.getInt(R.styleable.BubbleView_arrowLocation, 0);
            mArrowLocation = BubbleDrawable.ArrowLocation.mapIntToValue(location);
            mArrowCenter = array.getBoolean(R.styleable.BubbleView_arrowCenter, false);
            mArrowHorizontal= (int) array.getInt(R.styleable.BubbleView_arrowHorizontal,1);
            mArrowMargin= (int) array.getDimension(R.styleable.BubbleView_arrowMargin,10);
            array.recycle();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //measure完以后会调用这个方法，此时可以获取view的宽高
        if (w > 0 && h > 0){
            setUp(w, h);
        }
    }

    private void setUp(int left, int right, int top, int bottom){
        if (right < left || bottom < top)
            return;
        RectF rectF = new RectF(left, top, right, bottom);
        bubbleDrawable = new BubbleDrawable.Builder()
                .rect(rectF)
                .arrowLocation(mArrowLocation)
                .bubbleType(BubbleDrawable.BubbleType.COLOR)
                .angle(mAngle)
                .arrowHeight(mArrowHeight)
                .arrowWidth(mArrowWidth)
                .arrowPosition(mArrowPosition)
                .bubbleColor(bubbleColor)
                .arrowCenter(mArrowCenter)
                .arrowHorizaontalPos(mArrowHorizontal)
                .arrowMargin(mArrowMargin)
                .build();
    }

    private void setUp(int width, int height){
        //参数主要用来创建rect对象，getpaddingleft获取到的是这个ViewGroup的左边距， 就是childview的左边
        //距离父view的左边的距离
        setUp(getPaddingLeft(),  width - getPaddingRight(),
                getPaddingTop(), height - getPaddingBottom());
        setBackgroundDrawable(bubbleDrawable);
    }

    public void setUpBubbleDrawable () {
        setBackgroundDrawable(null);
        post(new Runnable() {
            @Override
            public void run() {
                setUp(getWidth(), getHeight());
            }
        });
    }

}
