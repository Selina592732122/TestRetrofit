package test.com.testretrofit;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import test.com.testretrofit.databinding.ActivityMainBinding;
import test.com.testretrofit.retrofit.HttpHandler;
import test.com.testretrofit.retrofit.TaskObserver;
import test.com.testretrofit.retrofit.bean.ExpressMessageListBean;
import test.com.testretrofit.retrofit.service.MineService;

public class MainActivity extends BasicActivity {

	/** 应用实例 */
	public static MainActivity INSTANCE;
	private ActivityMainBinding mDataBinding;
	private ExpressMessageListAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		INSTANCE = this;
		mDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

		getData();
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
						mDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));
//						mDataBinding.recyclerView.addItemDecoration(new SpaceDividerDecoration(MainActivity.this).addTopDecoration(10));
						mAdapter = new ExpressMessageListAdapter(MainActivity.this, basicBean.getList());
						mDataBinding.recyclerView.setAdapter(mAdapter);
					}
				});
	}
}
