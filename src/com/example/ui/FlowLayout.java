package com.example.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class FlowLayout extends ViewGroup {
 
    private int paddingHorizontal;
    private int paddingVertical;
   
    public FlowLayout(Context context) {
      super(context);
      init();
    }
   
    public FlowLayout(Context context, AttributeSet attrs) {
      this(context, attrs, 0);
    }
   
    public FlowLayout(Context context, AttributeSet attrs, int defStyle) {
      super(context, attrs, defStyle);
      init();
    }
   
    private void init() {
      paddingHorizontal = getResources().getDimensionPixelSize(R.dimen.flowlayout_horizontal_padding);
      paddingVertical = getResources().getDimensionPixelSize(R.dimen.flowlayout_vertical_padding);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int layoutWidth     = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft() - getPaddingRight();
        int layoutHeight    = 0;
        
        int cursorX         = 0;
        int cursorY         = 0;
        int lineHeight      = 0;
        boolean breakLine   = false;
        int childCount      = getChildCount();
        
        // Base values
        cursorX         = getPaddingLeft();
        cursorY         = getPaddingTop();
        layoutHeight    = getPaddingTop();
        
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == GONE) {
                continue;
            }

            // Measure children itself
            child.measure(
                getChildMeasureSpec(widthMeasureSpec, 0, child.getLayoutParams().width),
                getChildMeasureSpec(heightMeasureSpec, 0, child.getLayoutParams().height)
            );

            // Get child measurements
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();
            
            // if free width is not enough, we need break line
            if (cursorX + childWidth > layoutWidth) {
                breakLine = true;
            }
            
            if (breakLine) {
                cursorX = getPaddingLeft();
                cursorY += lineHeight;

                layoutHeight += lineHeight;
                lineHeight = 0;

                breakLine = false;
            }

            // In the end, we need to move cursor to left 
            cursorX += childWidth + paddingHorizontal;

            // lineHeight is equal the height of highest child in row
            lineHeight = Math.max(lineHeight, (childHeight + paddingVertical));

        }

        // Increase layout height by the height of last row
        layoutHeight += lineHeight;
        setMeasuredDimension(layoutWidth, layoutHeight);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int layoutWidth = right - left;

        int cursorX = 0;
        int cursorY = 0;
        int lineHeight = 0;
        boolean breakLine = false;
        int childCount = getChildCount();

        // Base values
        cursorX = getPaddingLeft();
        cursorY = getPaddingTop();

        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == GONE) {
                continue;
            }

            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            if (cursorX + childWidth > layoutWidth) {
                breakLine = true;
            }

            if (breakLine) {
                cursorX = getPaddingLeft();
                cursorY += lineHeight;

                lineHeight = 0;
                breakLine = false;
            }

            child.layout(cursorX, cursorY, cursorX + childWidth, cursorY + childHeight);

            // In the end, we need to move cursor to left
            cursorX += childWidth + paddingHorizontal;

            // lineHeight is equal the height of highest child in row
            lineHeight = Math.max(lineHeight, (childHeight + paddingVertical));
        }
    }
}