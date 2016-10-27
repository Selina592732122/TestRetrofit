package test.com.testretrofit.retrofit;


import test.com.testretrofit.retrofit.bean.BasicBean;

public interface TaskListener<T extends BasicBean> {
	void taskStart();

	void taskStop();

	void taskSuccess(T basicBean);

	void taskFailure(T basicBean);

	void taskOther(T basicBean);

	void taskError(Throwable throwable);
}
