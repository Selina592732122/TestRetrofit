package test.com.testretrofit.widget;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import java.util.ArrayList;

public class SpaceDividerDecoration extends RecyclerView.ItemDecoration {
	private DisplayMetrics mDisplayMetrics;

	private ArrayList<ItemDecoration> mItemDecorations;

	public SpaceDividerDecoration(Context context) {
		mDisplayMetrics = context.getResources().getDisplayMetrics();
		mItemDecorations = new ArrayList<>();
	}

	@Deprecated
	public SpaceDividerDecoration(Context context, int size) {
		mDisplayMetrics = context.getResources().getDisplayMetrics();
		mItemDecorations = new ArrayList<>();
		addTopDecoration(size);
	}

	public SpaceDividerDecoration addLeftDecoration(int leftSpaceSize) {
		mItemDecorations.add(new LeftSpaceDividerDecoration(leftSpaceSize));
		return this;
	}

	public SpaceDividerDecoration addTopDecoration(int topSpaceSize) {
		mItemDecorations.add(new TopSpaceDividerDecoration(topSpaceSize, false));
		return this;
	}

	public SpaceDividerDecoration addTopDecoration(int topSpaceSize, boolean isFirst) {
		mItemDecorations.add(new TopSpaceDividerDecoration(topSpaceSize, isFirst));
		return this;
	}

	public SpaceDividerDecoration addRightDecoration(int rightSpaceSize) {
		mItemDecorations.add(new RightSpaceDividerDecoration(rightSpaceSize));
		return this;
	}

	public SpaceDividerDecoration addBottomDecoration(int bottomSpaceSize) {
		mItemDecorations.add(new BottomSpaceDividerDecoration(bottomSpaceSize));
		return this;
	}

	@Override
	public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
		if (view.getVisibility() == View.GONE) {
			return;
		}

		int position = parent.getChildAdapterPosition(view);
		for (ItemDecoration itemDecoration : mItemDecorations) {
			itemDecoration.getItemOffsets(outRect, position);
		}
	}

	private interface ItemDecoration {
		void getItemOffsets(Rect outRect, int position);
	}

	private class LeftSpaceDividerDecoration implements ItemDecoration {
		private int mSpace;

		public LeftSpaceDividerDecoration(int space) {
			mSpace = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, space, mDisplayMetrics);
		}

		@Override
		public void getItemOffsets(Rect outRect, int position) {
			outRect.left = mSpace;
		}
	}

	private class TopSpaceDividerDecoration implements ItemDecoration {
		private int mSpace;
		private boolean isFirst;

		public TopSpaceDividerDecoration(int space, boolean isFirst) {
			mSpace = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, space, mDisplayMetrics);
			this.isFirst = isFirst;
		}

		@Override
		public void getItemOffsets(Rect outRect, int position) {
			if ((isFirst && position == 0) || !isFirst) {
				outRect.top = mSpace;
			}
		}

	}

	private class RightSpaceDividerDecoration implements ItemDecoration {
		private int mSpace;

		public RightSpaceDividerDecoration(int space) {
			mSpace = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, space, mDisplayMetrics);
		}

		@Override
		public void getItemOffsets(Rect outRect, int position) {
			outRect.right = mSpace;
		}

	}

	private class BottomSpaceDividerDecoration implements ItemDecoration {
		private int mSpace;

		public BottomSpaceDividerDecoration(int space) {
			mSpace = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, space, mDisplayMetrics);
		}

		@Override
		public void getItemOffsets(Rect outRect, int position) {
			outRect.bottom = mSpace;
		}

	}
}
