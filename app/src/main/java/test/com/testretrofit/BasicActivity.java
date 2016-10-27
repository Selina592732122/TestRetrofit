package test.com.testretrofit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.Toast;

import test.com.testretrofit.widget.LoadingDialog;

public abstract class BasicActivity extends AppCompatActivity {
	/** 注销登录 */
	public static final int LOGOUT = 1000;
	/** 应用实例 */
	public static BasicActivity INSTANCE;

	private LoadingDialog mLoadingDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		INSTANCE = this;
		mLoadingDialog = new LoadingDialog(this);
	}

	/**
	 * 设置标题
	 *
	 * @param dataBinding 数据绑定
	 * @param titleId     标题资源ID
	 */
//	public final void setTitle(ViewDataBinding dataBinding, int titleId) {
//		setTitle(dataBinding, getString(titleId));
//	}

	/**
	 * 设置标题
	 *
	 * @param dataBinding 数据绑定
	 * @param title       标题
	 */
//	public final void setTitle(ViewDataBinding dataBinding, String title) {
//		dataBinding.setVariable(BR.title, title);
//	}

	/**
	 * 设置返回按钮
	 *
	 * @param dataBinding 数据绑定
	 */
//	public final void showLeftBack(ViewDataBinding dataBinding) {
//		dataBinding.setVariable(BR.leftAction,
//				new ToolbarAction()
//						.setDrawable(getResources().getDrawable(R.mipmap.ic_arrow_back))
//						.setListener(new View.OnClickListener() {
//							@Override
//							public void onClick(View v) {
//								finish();
//							}
//						}));
//	}

	/**
	 * 设置左按钮事件
	 *
	 * @param dataBinding   数据绑定
	 * @param toolbarAction 左按钮操作事件
	 */
//	public final void showLeftAction(ViewDataBinding dataBinding, ToolbarAction toolbarAction) {
//		dataBinding.setVariable(BR.leftAction, toolbarAction);
//	}

	/**
	 * 设置右按钮事件
	 *
	 * @param dataBinding   数据绑定
	 * @param toolbarAction 右按钮操作事件
	 */
//	public final void showRightAction(ViewDataBinding dataBinding, ToolbarAction toolbarAction) {
//		dataBinding.setVariable(BR.rightAction, toolbarAction);
//	}

	/**
	 * 显示加载窗口
	 */
	public final void showLoading() {
		if (!mLoadingDialog.isShowing()) {
			mLoadingDialog.show();
		}
	}

	/**
	 * 隐藏加载窗口
	 */
	public final void hideLoading() {
		if (mLoadingDialog.isShowing()) {
			mLoadingDialog.dismiss();
		}
	}

	/**
	 * toast提示
	 *
	 * @param text 提示内容
	 */
	protected final void toast(String text) {
		Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	/**
	 * toast提示
	 *
	 * @param resId 提示内容资源ID
	 */
	protected final void toast(int resId) {
		Toast toast = Toast.makeText(this, resId, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}
}
