package test.com.testretrofit.widget;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class RefreshRecyclerView extends SwipeRefreshLayout {
	private RecyclerView mRecyclerView;
	/** 正在刷新 */
	private boolean isRefreshing;
	/** 正在加载 */
	private boolean isLoadingMore = true;
	private OnRefreshListener mOnRefreshListener;

	public RefreshRecyclerView(Context context) {
		super(context);
	}

	public RefreshRecyclerView(Context context, AttributeSet attrs) {
		super(context, attrs);

		mRecyclerView = new RecyclerView(context);
		addView(mRecyclerView, new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

		setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				if (isRefreshing) {
					return;
				}
				if (mOnRefreshListener != null) {
					isRefreshing = true;
					mOnRefreshListener.onRefresh();
				}
			}
		});
	}

	public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
		this.mOnRefreshListener = onRefreshListener;
	}

	public void taskSuccess() {
		isRefreshing = false;
		setRefreshing(false);
	}

	public void onRefresh() {
		if (!isRefreshing && mOnRefreshListener != null) {
			isRefreshing = true;
			mOnRefreshListener.onRefresh();
		}
	}

	public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
		mRecyclerView.setLayoutManager(layoutManager);
	}

	public void setAdapter(RecyclerView.Adapter adapter) {
		mRecyclerView.setAdapter(adapter);
	}

	public interface OnRefreshListener {
		void onRefresh();
	}
}
