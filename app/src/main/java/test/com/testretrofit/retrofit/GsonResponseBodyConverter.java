package test.com.testretrofit.retrofit;

import android.util.Log;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import test.com.testretrofit.retrofit.bean.BasicBean;

final class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
	private final TypeAdapter<T> mBasicAdapter;
	private final boolean mIsNesting;
	private final boolean mIsBasic;

	GsonResponseBodyConverter(TypeAdapter<T> basicAdapter, boolean isBasic, boolean isNesting) {
		mBasicAdapter = basicAdapter;
		mIsBasic = isBasic;
		mIsNesting = isNesting;
	}

	@Override
	public T convert(ResponseBody value) throws IOException {
		try {
			if (mIsNesting || mIsBasic) {
				JsonParser parser = new JsonParser();
				JsonObject jsonObject = parser.parse(value.charStream()).getAsJsonObject();

				System.out.println(jsonObject.toString());

				BaseBean baseBean = new BaseBean();
				baseBean.setResultCode(jsonObject.get("status").getAsInt());
				JsonElement messageElement = jsonObject.get("message");
				if (messageElement != null) {
					baseBean.setResultMessage(messageElement.getAsString());
				}
				JsonElement resultElement = jsonObject.get("data");
				Log.d("resultElement", "" + resultElement.toString());
				if (resultElement != null) {
					baseBean.setResultData(resultElement.toString());
				}

				T resultBean;
				if (mIsBasic || baseBean.getResultData() == null || baseBean.getResultData().equalsIgnoreCase("null") || baseBean.getResultData().length() == 0) {
					//解析resultData无嵌套的情况
					resultBean = mBasicAdapter.fromJson("{}");
					Log.d("Converter1", "" + mBasicAdapter.fromJson("{}"));
				} else {
					//解析resultData嵌套的情况，baseBean.getResultData()需为{}，不能为数组[]
					//如：
					/*{
						"code": 200,
							"msg": "",
							"result": {
						"list": [
						{
							"addrId": 42,
								"rname": "shenling",
								"mobile": "18259566425"
						},
						{
							"addrId": 347,
								"rname": "sl",
								"mobile": "18259566952"
						}
						]
					}
					}*/
					if (baseBean.getResultData().startsWith("{"))
						resultBean = mBasicAdapter.fromJson(baseBean.getResultData());
					else {
						//baseBean.getResultData()不为{}，本地转化成 {list：[]}
						baseBean.setResultData("{\"list\":" + baseBean.getResultData() + "}");
						resultBean = mBasicAdapter.fromJson(baseBean.getResultData());
					}

					//					Log.d("Converter2",""+mBasicAdapter.fromJson(baseBean.getResultData()));
				}
				BasicBean basicBean = (BasicBean) resultBean;
				basicBean.setResultCode(baseBean.getResultCode());
				basicBean.setResultMessage(baseBean.getResultMessage());
				basicBean.handleField();
				Log.d("Converter3", "" + baseBean);
				return resultBean;
			}
			return mBasicAdapter.fromJson(value.charStream());
		} finally {
			value.close();
		}
	}

	public static class BaseBean {
		@SerializedName("status")
		public int resultCode;
		@SerializedName("message")
		public String resultMessage;
		@SerializedName("data")
		public String resultData;

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

		public String getResultData() {
			return resultData;
		}

		public void setResultData(String resultData) {
			this.resultData = resultData;
		}
	}
}