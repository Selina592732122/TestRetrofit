package test.com.testretrofit.retrofit.bean;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ExpressMessageListBean extends BasicBean {
	@SerializedName("list")
	private List<ExpressMessageItemBean> list;

	public List<ExpressMessageItemBean> getList() {
		return list;
	}

	public void setList(List<ExpressMessageItemBean> list) {
		this.list = list;
	}

	@Override
	public void handleField() {
		Log.d("handleField", "list="+list);
	}
}
