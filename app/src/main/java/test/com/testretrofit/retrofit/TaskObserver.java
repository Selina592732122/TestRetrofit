package test.com.testretrofit.retrofit;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import java.lang.ref.SoftReference;

import rx.Subscriber;
import test.com.testretrofit.BasicActivity;
import test.com.testretrofit.MainActivity;
import test.com.testretrofit.retrofit.bean.BasicBean;

public abstract class TaskObserver<T extends BasicBean> extends Subscriber<T> implements TaskListener<T> {
	private SoftReference<Context> mContextReference;

	public TaskObserver(Context context) {
		mContextReference = new SoftReference<>(context);
	}

	@Override
	public void onStart() {
		taskStart();
	}

	@Override
	public final void onNext(T basicBean) {
		taskStop();

		if (basicBean.getResultCode() == 200) {
			taskSuccess(basicBean);
		} else if (basicBean.getResultCode() == 1001 || basicBean.getResultCode() == 1000) {
			taskOther(basicBean);
		} else {
			taskFailure(basicBean);
		}
	}

	@Override
	public final void onCompleted() {
	}

	@Override
	public final void onError(Throwable throwable) {
		taskStop();
		taskError(throwable);
	}

	@Override
	public void taskStart() {
		Context context = mContextReference.get();
		if (context != null && context instanceof BasicActivity) {
			((BasicActivity) context).showLoading();
		}
	}

	@Override
	public void taskStop() {
		Context context = mContextReference.get();
		if (context != null && context instanceof BasicActivity) {
			((BasicActivity) context).hideLoading();
		}
	}

	@Override
	public void taskOther(T basicBean) {
//		Context context = mContextReference.get();
//		if (context == null || !(context instanceof BasicActivity)) {
//			return;
//		}
//
//		BasicActivity activity = (BasicActivity) context;
//		LoginBean.remove();
//		Toast.makeText(BasicApp.INSTANCE, basicBean.getResultMessage(), Toast.LENGTH_SHORT).show();
//		Intent intent = new Intent(activity, LoginActivity.class);
//		activity.startActivityForResult(intent, BasicActivity.LOGOUT);
	}

	@Override
	public void taskFailure(T basicBean) {
		Toast toast = Toast.makeText(MainActivity.INSTANCE, basicBean.getResultMessage(), Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	@Override
	public void taskError(Throwable throwable) {
		taskStop();
		throwable.printStackTrace();

		Toast toast = Toast.makeText(MainActivity.INSTANCE, "网络请求错误,请重试", Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	//	public void taskError(LoadingView loadingView) {
	//		loadingView.refreshFailure("网络请求错误,请重试");
	//	}
}
