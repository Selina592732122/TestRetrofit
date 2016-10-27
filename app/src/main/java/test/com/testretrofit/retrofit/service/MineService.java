package test.com.testretrofit.retrofit.service;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;
import test.com.testretrofit.retrofit.bean.BasicBean;
import test.com.testretrofit.retrofit.bean.ExpressMessageListBean;

public interface MineService {
	@FormUrlEncoded
	@POST("app/my/info/updateBaseInfo.app")
	Observable<BasicBean> updateUserInfo(@Field("nickName") String nickName,
	                                     @Field("sex") String gender,
	                                     @Field("birthday") String birthday,
	                                     @Field("qq") String qq,
	                                     @Field("openId") String openId);

	@FormUrlEncoded
	@POST("query")
	Observable<ExpressMessageListBean> getExpressMessage(@Field("type") String type,
	                                                     @Field("postid") String postId);

}
