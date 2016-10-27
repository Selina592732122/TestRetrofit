package test.com.testretrofit.retrofit;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import test.com.testretrofit.retrofit.bean.BasicBean;

public final class GsonConverterFactory extends Converter.Factory {

	private static GsonConverterFactory mFactory;

	public static GsonConverterFactory create() {
		if (mFactory != null) {
			return mFactory;
		}
		return create(new Gson());
	}

	public static GsonConverterFactory create(Gson gson) {
		if (mFactory != null) {
			return mFactory;
		}
		mFactory = new GsonConverterFactory(gson);
		return mFactory;
	}

	private final Gson gson;

	private GsonConverterFactory(Gson gson) {
		if (gson == null) {
			throw new NullPointerException("gson == null");
		}
		this.gson = gson;
	}

	public Gson getGson() {
		return gson;
	}

	@Override
	public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
		TypeAdapter<?> basicAdapter = gson.getAdapter(TypeToken.get(type));
		Log.d("GsonConverterFactory",(type instanceof Class)+","+(type == BasicBean.class)+","+(BasicBean.class.isAssignableFrom((Class<?>) type)));
		if (type instanceof Class) {
			if (type == BasicBean.class) {
				return new GsonResponseBodyConverter<>(basicAdapter, true, false);
			}
			if (BasicBean.class.isAssignableFrom((Class<?>) type)) {
				return new GsonResponseBodyConverter<>(basicAdapter, false, true);
			}
		}
		return new GsonResponseBodyConverter<>(basicAdapter, false, false);
	}

	@Override
	public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
		return new GsonRequestBodyConverter<>();
	}
}
