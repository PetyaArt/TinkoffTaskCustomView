package com.example.petya.tinkofftaskcustomview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class CustomViewGroup extends ViewGroup {

    public CustomViewGroup(Context context) {
        this(context, null, 0);
    }

    public CustomViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int childCount = getChildCount();

        int parentWidth = MeasureSpec.getSize(widthMeasureSpec);

        int curWidth;
        int curHeight;

        int maxWidth = 0;
        int minHeight = 0;
        int childState = 0;

        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);

            if (child.getVisibility() != GONE) {

                measureChild(child, widthMeasureSpec, heightMeasureSpec);
                curWidth = child.getMeasuredWidth();
                curHeight = child.getMeasuredHeight();

                if (maxWidth + curWidth > parentWidth) {
                    maxWidth = 0;
                    minHeight += curHeight;
                } else {
                    maxWidth += curWidth + getPaddingRight();
                }
                childState = combineMeasuredStates(childState, child.getMeasuredState());
            }
        }

        setMeasuredDimension(resolveSizeAndState(parentWidth, widthMeasureSpec, childState),
                resolveSizeAndState(minHeight, heightMeasureSpec,
                        childState << MEASURED_HEIGHT_STATE_SHIFT));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int childCount = getChildCount();

        int curTop = getPaddingTop();
        int curWidth;
        int curHeight;

        int minWidth = getPaddingLeft();
        int maxWidth = getMeasuredWidth() - getPaddingRight();

        int mainCounter = childCount;
        int mainCounterTwo = 0;
        int mainCounterThree = 0;
        int topHeight = 0;
        int childNow = 0;


        while (mainCounter > 0) {

            if (getChildAt(mainCounter - 1).getVisibility() == GONE) {
                mainCounter--;
            }

            for (int j = 0; j < mainCounter; j++) {
                View child = getChildAt(mainCounterThree);
                MarginLayoutParams layoutParams = (MarginLayoutParams) child.getLayoutParams();
                curWidth = child.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;

                if (maxWidth - curWidth <= minWidth) {
                    j = mainCounter;
                } else {
                    maxWidth -= curWidth;
                    childNow++;
                    mainCounterThree++;
                }
            }

            for (int j = 0; j < childNow; j++) {
                View child = getChildAt(mainCounterTwo);

                MarginLayoutParams layoutParams = (MarginLayoutParams) child.getLayoutParams();

                curWidth = child.getMeasuredWidth();
                curHeight = child.getMeasuredHeight();

                child.layout(maxWidth, curTop, maxWidth + curWidth, curTop + curHeight);
                topHeight = curHeight + layoutParams.topMargin + layoutParams.bottomMargin;
                maxWidth += curWidth  + layoutParams.leftMargin + layoutParams.rightMargin;
                mainCounter--;
                mainCounterTwo++;
            }

            childNow = 0;
            curTop += topHeight;
            maxWidth = getMeasuredWidth() - getPaddingRight();
        }
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof MarginLayoutParams;
    }




}
