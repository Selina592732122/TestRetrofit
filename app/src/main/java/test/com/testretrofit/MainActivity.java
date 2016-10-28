package test.com.testretrofit;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import test.com.testretrofit.databinding.ActivityMainBinding;
import test.com.testretrofit.retrofit.HttpHandler;
import test.com.testretrofit.retrofit.TaskObserver;
import test.com.testretrofit.retrofit.bean.ExpressMessageItemBean;
import test.com.testretrofit.retrofit.bean.ExpressMessageListBean;
import test.com.testretrofit.retrofit.service.MineService;
import test.com.testretrofit.widget.RefreshRecyclerView;

public class MainActivity extends BasicActivity {

	/** 应用实例 */
	public static MainActivity INSTANCE;
	private ActivityMainBinding mDataBinding;
	private ExpressMessageListAdapter mAdapter;

	private List<ExpressMessageItemBean> mList = new ArrayList<>();

	private boolean isLoadingMore = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		INSTANCE = this;
		mDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

		mDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));
		//						mDataBinding.recyclerView.addItemDecoration(new SpaceDividerDecoration(MainActivity.this).addTopDecoration(10));
		mAdapter = new ExpressMessageListAdapter(MainActivity.this);
		mDataBinding.recyclerView.setAdapter(mAdapter);

		observeRefresh();
		observeLoadMore();
	}

	private void observeLoadMore() {
		mDataBinding.recyclerView.setOnLoadMoreListener(new RefreshRecyclerView.OnLoadMoreListener() {
			@Override
			public void onLoad() {
				getMoreData();
			}
		});
	}

	private void observeRefresh() {
		mDataBinding.recyclerView.setOnRefreshListener(new RefreshRecyclerView.OnRefreshListener() {
			@Override
			public void onRefresh() {
				getData();
			}
		});
		mDataBinding.recyclerView.onRefresh();
	}

	private void getData() {
		HttpHandler.create()
				.create(MineService.class)
				.getExpressMessage("shentong", "3316456179946")
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new TaskObserver<ExpressMessageListBean>(this) {
					@Override
					public void taskSuccess(ExpressMessageListBean basicBean) {
						mList.clear();
						for (ExpressMessageItemBean item : basicBean.getList()) {
							mList.add(item);
						}
						mDataBinding.recyclerView.taskSuccess(basicBean.getList());
					}
				});
	}

	private void getMoreData() {
		HttpHandler.create()
				.create(MineService.class)
				.getExpressMessage("shentong", "3316456179946")
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new TaskObserver<ExpressMessageListBean>(this) {
					@Override
					public void taskSuccess(ExpressMessageListBean basicBean) {
						for (ExpressMessageItemBean item : basicBean.getList()) {
							mList.add(item);
						}
						mDataBinding.recyclerView.taskSuccess(mList);
					}
				});
	}
}
