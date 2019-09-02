package com.returntolife.jjcode.mydemolist.demo.function.test;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import io.reactivex.Observable;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by HeJiaJun on 2019/8/26.
 * Email:hejj@mama.cn
 * des:
 */
public class UserPresenterImplTest {

    UserPresenter userPresenter;
    UserView      userView;
    UserService   userService;

    @Before
    public void setUp() throws Exception {
        RxUnitTestTools.asyncToSync();

        // 生成mock对象
        userView = mock(UserView.class);
        userService = mock(UserService.class);

        userPresenter = new UserPresenterImpl(userService, userView);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void loadUser() {
        User user = new User();
        user.uid = 1;
        user.name = "kkmike999";

        when(userService.loadUser(anyInt())).thenReturn(Observable.just(user));

        userPresenter.loadUser(1);

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);

        verify(userService).loadUser(1);
        verify(userView).onUserLoaded(captor.capture());

        User result = captor.getValue(); // 捕获的User

        Assert.assertEquals(result.uid, 2);
        Assert.assertEquals(result.name, "kkmike999");
    }
}