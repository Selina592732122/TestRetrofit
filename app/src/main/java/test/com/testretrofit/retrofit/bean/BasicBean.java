package test.com.testretrofit.retrofit.bean;

import android.databinding.BaseObservable;

public class BasicBean extends BaseObservable {
	/** 请求成功 */
	public static final int RESULT_OK = 200;

	public int resultCode;
	public String resultMessage;

	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

	public boolean isSuccessful() {
		return resultCode == 200;
	}

	public void handleField() {
	}

	@Override
	public String toString() {
		return "BasicBean{" +
				"resultCode=" + resultCode +
				", resultMessage='" + resultMessage + '\'' +
				'}';
	}
}
