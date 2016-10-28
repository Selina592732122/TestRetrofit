package test.com.testretrofit;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import test.com.testretrofit.databinding.ItemAddressListBinding;
import test.com.testretrofit.retrofit.bean.ExpressMessageItemBean;
import test.com.testretrofit.widget.RefreshAdapter;

public class ExpressMessageListAdapter extends RefreshAdapter<List<ExpressMessageItemBean>, ExpressMessageListAdapter.ExpressMessageViewHolder> {

	private List<ExpressMessageItemBean> mList;
	private Context context;

	public ExpressMessageListAdapter(Context context) {
		this.context = context;
	}

	@Override
	public ExpressMessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		return new ExpressMessageViewHolder(DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_address_list, parent, false));
	}

	@Override
	public void onBindItemHolder(ExpressMessageViewHolder holder, int position) {
		if (position == 0) {
			holder.mDataBinding.setIsCurrentItem(true);
		} else {
			holder.mDataBinding.setIsCurrentItem(false);
		}
		holder.mDataBinding.setExpressMessageItemBean(mList.get(position));
	}

	@Override
	public int getItemCount() {
		return mList != null ? mList.size() : 0;
	}

	@Override
	public void updateAdapterInfo(List<ExpressMessageItemBean> list) {
		this.mList = list;
		notifyDataSetChanged();
	}

	class ExpressMessageViewHolder extends RecyclerView.ViewHolder {
		private ItemAddressListBinding mDataBinding;

		public ExpressMessageViewHolder(ViewDataBinding dataBinding) {
			super(dataBinding.getRoot());
			this.mDataBinding = (ItemAddressListBinding) dataBinding;
		}
	}
}
