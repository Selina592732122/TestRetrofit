package test.com.testretrofit;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;

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
				.getExpressMessage("shentong","3316456179946")
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new TaskObserver<ExpressMessageListBean>(this) {
					@Override
					public void taskSuccess(ExpressMessageListBean basicBean) {
						Log.d("taskSuccess",""+basicBean+","+basicBean.getList().get(0).getContext());
						StringBuffer sb = new StringBuffer();
						for (int i = 0; i < basicBean.getList().size(); i++) {
							sb.append(basicBean.getList().get(i).getContext());
							sb.append("\n");
						}
						mDataBinding.setResult(sb.toString());
					}
				});
	}
}
