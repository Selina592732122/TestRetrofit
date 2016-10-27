package test.com.testretrofit.retrofit;

import android.os.Build;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

public class HttpHandler {
	/** 测试接口地址 */
	public static final String URL = "http://www.kuaidi100.com/";

	private static Retrofit instance;

	public static Retrofit create() {
		if (instance != null) {
			return instance;
		}

		//X509TrustManager xtm = new X509TrustManager() {
		//	@Override
		//	public void checkClientTrusted(X509Certificate[] chain, String authType) {
		//	}
		//
		//	@Override
		//	public void checkServerTrusted(X509Certificate[] chain, String authType) {
		//	}
		//
		//	@Override
		//	public X509Certificate[] getAcceptedIssuers() {
		//		X509Certificate[] x509Certificates = new X509Certificate[0];
		//		return x509Certificates;
		//	}
		//};
		//
		//SSLContext sslContext = null;
		//try {
		//	sslContext = SSLContext.getInstance("SSL");
		//	sslContext.init(null, new TrustManager[]{xtm}, new SecureRandom());
		//} catch (Exception ignored) {
		//}
		//HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
		//	@Override
		//	public boolean verify(String hostname, SSLSession session) {
		//		return true;
		//	}
		//};

		OkHttpClient okHttpClient = new OkHttpClient.Builder()
				//.sslSocketFactory(sslContext.getSocketFactory(),xtm)
				//.hostnameVerifier(DO_NOT_VERIFY)
				.writeTimeout(30, TimeUnit.SECONDS)
				.readTimeout(30, TimeUnit.SECONDS)
				.addInterceptor(new Interceptor() {
					@Override
					public Response intercept(Chain chain) throws IOException {
						Request request = chain.request();

						String token = "";
						//						LoginBean loginBean = LoginBean.read();
						//						if (loginBean != null) {
						//							token = loginBean.getToken();
						//						}

						StringBuilder builder = new StringBuilder(request.url().toString());
						builder.append("?");
						RequestBody body = request.body();

						if (body instanceof FormBody) {
							FormBody.Builder formBodyBuilder = new FormBody.Builder();
							FormBody formBody = (FormBody) body;
							int size = formBody.size();
							for (int index = 0; index < size; index++) {
								String name = formBody.name(index);
								String value = formBody.value(index);
								formBodyBuilder.add(name, value);
								builder.append(name).append("=").append(value).append("&");
							}
							formBodyBuilder.add("token", token);
							builder.append("token").append("=").append(token);

							request = request.newBuilder()
									.method(request.method(), formBodyBuilder.build())
									.addHeader("APP_TYPE", "Android")
									.addHeader("APP_DEVICE_MODEL", Build.DEVICE)
									.addHeader("APP_VERSION", "APP_VERSION")
									.addHeader("APP_BUILD_VERSION", "APP_BUILD_VERSION")
									.addHeader("APP_OS", String.valueOf(Build.VERSION.SDK_INT))
									.build();
						} else {
							request = request.newBuilder().url(
									request.url().newBuilder()
											.addQueryParameter("token", token)
											.build()
							)
									.addHeader("APP_TYPE", "Android")
									.addHeader("APP_DEVICE_MODEL", Build.DEVICE)
									.addHeader("APP_VERSION", "APP_VERSION")
									.addHeader("APP_BUILD_VERSION", "APP_BUILD_VERSION")
									.addHeader("APP_OS", String.valueOf(Build.VERSION.SDK_INT))
									.build();
							builder = new StringBuilder(request.url().toString());
						}

						System.out.println(builder.toString());

						return chain.proceed(request);
					}
				})
				.build();

		return instance = new Retrofit.Builder()
				.baseUrl(URL)
				.client(okHttpClient)
				.addConverterFactory(GsonConverterFactory.create())
				.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
				.build();
	}


}
