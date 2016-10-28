package test.com.testretrofit.widget;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

public abstract class RefreshAdapter<AdapterInfo, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter {
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		return null;
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		onBindItemHolder((VH)holder,position);
	}

	@Override
	public int getItemCount() {
		return 0;
	}


	public void taskSuccess(AdapterInfo bean) {
		updateAdapterInfo(bean);
	}

	/**
	 * 更新数据信息
	 *
	 * @param adapterInfo 数据信息
	 */
	public abstract void updateAdapterInfo(AdapterInfo adapterInfo);

	public abstract void onBindItemHolder(VH holder,int pos);
}
