package com.bignerdranch.travelcommunity.widget;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;


public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public SpaceItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        if(parent.getChildAdapterPosition(view) != 0)
            outRect.top = space;
            outRect.right = space;
            outRect.left = space;
            outRect.bottom = space;
    }
}
