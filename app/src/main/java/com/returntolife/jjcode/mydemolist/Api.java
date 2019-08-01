package com.returntolife.jjcode.mydemolist;

import com.returntolife.jjcode.mydemolist.demo.function.baiduai.bean.AnimalBean;
import com.returntolife.jjcode.mydemolist.demo.function.baiduai.bean.TokenResultBean;
import com.tools.jj.tools.http.JsonResult;


import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by HeJiaJun on 2019/8/1.
 * Email:hejj@mama.cn
 * des:
 */
public interface Api {

    @GET("oauth/2.0/token")
    Observable<TokenResultBean> getToken(@Query("grant_type")String type, @Query("client_id")String id , @Query("client_secret")String secret);

    @FormUrlEncoded
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @POST("rest/2.0/image-classify/v1/animal")
    Observable<AnimalBean> searchAnimal(@Query("access_token")String token, @Field("image") String image);
}
