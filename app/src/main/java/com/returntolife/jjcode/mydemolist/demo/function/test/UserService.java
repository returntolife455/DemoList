package com.returntolife.jjcode.mydemolist.demo.function.test;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by HeJiaJun on 2019/8/26.
 * Email:hejj@mama.cn
 * des:
 */
public interface UserService {

    @GET("user/{uid}.json")
    Observable<User> loadUser(@Path("uid") int uid);
}
