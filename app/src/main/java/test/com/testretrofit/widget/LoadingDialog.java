package test.com.testretrofit.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import test.com.testretrofit.R;
import test.com.testretrofit.databinding.DialogLoadingBinding;


public class LoadingDialog extends Dialog {
	private MaterialProgressDrawable mProgressDrawable;

	public LoadingDialog(Context context) {
		super(context, R.style.DialogTheme);
		setCanceledOnTouchOutside(false);
		setCancelable(true);
		setOnCancelListener(new OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				dialog.dismiss();
			}
		});
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		DialogLoadingBinding dataBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.dialog_loading, null, false);
		setContentView(dataBinding.getRoot());

		mProgressDrawable = new MaterialProgressDrawable(getContext(), dataBinding.ivLoading);
		mProgressDrawable.updateSizes(MaterialProgressDrawable.LARGE);
		mProgressDrawable.setBackgroundColor(0xFFFAFAFA);
		mProgressDrawable.setColorSchemeColors(getContext().getResources().getColor(R.color.colorPrimary));

		dataBinding.ivLoading.setImageDrawable(mProgressDrawable);
	}

	@Override
	public void show() {
		super.show();

		mProgressDrawable.setProgressRotation(0.8f);
		mProgressDrawable.setStartEndTrim(0f, 0.5f);
		mProgressDrawable.setAlpha(255);
		mProgressDrawable.start();
	}

	@Override
	public void dismiss() {
		super.dismiss();
		mProgressDrawable.stop();
	}
}
