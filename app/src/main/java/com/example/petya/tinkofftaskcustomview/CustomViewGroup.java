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


    public boolean isContains(View view) {
        for (int i = 0; i < getChildCount(); i++) {
            if (getChildAt(i).equals(view))
                return true;
        }
        return false;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int childCount = getChildCount();

        int parentWidth = MeasureSpec.getSize(widthMeasureSpec);

        int curWidth = 0;
        int curHeight = 0;

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
                curWidth = child.getMeasuredWidth();
                if (maxWidth - curWidth <= minWidth) {
                    j = mainCounter;
                } else {
                    maxWidth -= curWidth + 20;
                    childNow++;
                    mainCounterThree++;
                }
            }

            for (int j = 0; j < childNow; j++) {
                View child = getChildAt(mainCounterTwo);
                curWidth = child.getMeasuredWidth();
                curHeight = child.getMeasuredHeight();
                child.layout(maxWidth, curTop, maxWidth + curWidth, curTop + curHeight);
                topHeight = curHeight;
                maxWidth += curWidth + 20;
                mainCounter--;
                mainCounterTwo++;
            }

            childNow = 0;
            curTop += topHeight;
            maxWidth = getMeasuredWidth() - getPaddingRight();
        }
    }
}
