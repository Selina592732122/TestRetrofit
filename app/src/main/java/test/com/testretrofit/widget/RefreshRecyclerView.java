package test.com.testretrofit.widget;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;

import static android.content.ContentValues.TAG;

public class RefreshRecyclerView extends SwipeRefreshLayout {
	private RecyclerView mRecyclerView;
	private RefreshAdapter mAdapter;
	/** 正在刷新 */
	private boolean isRefreshing;
	/** 正在加载 */
	private boolean isLoadingMore = true;
	private OnRefreshListener mOnRefreshListener;
	private OnLoadMoreListener mOnLoadMoreListener;

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

		/**如果想用GridView，可以试试这个，注意例子里的span_count =2

		@Override
		public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
			super.onScrolled(recyclerView, dx, dy);
			int[] visibleItems = mLayoutManager.findLastVisibleItemPositions(null);
			int lastitem = Math.max(visibleItems[0],visibleItems[1]);
			Log.d(TAG,"visibleItems =" + visibleItems);
			Log.d(TAG,"lastitem =" + lastitem);
			Log.d(TAG,"adapter.getItemCount() =" + adapter.getItemCount());
			if (dy > 0 && lastitem > adapter.getItemCount() - 5 && !isLoadingMore) {
				Log.d(TAG,"will loadNewFeeds");
			}
		}

		文／BlackSwift（简书作者）
		原文链接：http://www.jianshu.com/p/4feb0c16d1b5
		著作权归作者所有，转载请联系作者获得授权，并标注“简书作者”。

		**/
		mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
				super.onScrolled(recyclerView, dx, dy);
				int lastVisibleItem = ((LinearLayoutManager) mRecyclerView.getLayoutManager()).findLastVisibleItemPosition();
				int totalItemCount = mRecyclerView.getLayoutManager().getItemCount();
				//lastVisibleItem >= totalItemCount - 4 表示剩下4个item自动加载，各位自由选择
				// dy>0 表示向下滑动
				if (lastVisibleItem >= totalItemCount - 1 && dy > 0) {
					if (isLoadingMore) {
						Log.d(TAG, "ignore manually update!");
						return;
					}

					if (mOnLoadMoreListener != null) {
						mOnLoadMoreListener.onLoad();
						isLoadingMore = false;
					}
				}
			}
		});
	}

	public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
		this.mOnRefreshListener = onRefreshListener;
	}

	public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
		this.mOnLoadMoreListener = onLoadMoreListener;
	}

	public void taskSuccess(Object bean) {
		if (isRefreshing)
			isRefreshing = false;
		if (isLoadingMore)
			isLoadingMore = false;
		setRefreshing(false);
		mAdapter.taskSuccess(bean);
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

	public void setAdapter(RefreshAdapter adapter) {
		mRecyclerView.setAdapter(adapter);
		mAdapter = adapter;
	}

	public interface OnRefreshListener {
		void onRefresh();
	}

	public interface OnLoadMoreListener {
		void onLoad();
	}
}
