package com.returntolife.jjcode.mydemolist.demo.function.test.sqlite;

import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import com.returntolife.jjcode.mydemolist.BuildConfig;
import com.returntolife.jjcode.mydemolist.RoboApp;
import com.returntolife.jjcode.mydemolist.demo.function.test.User;
import com.returntolife.jjcode.mydemolist.demo.function.test.robo.DbHelper;
import com.returntolife.jjcode.mydemolist.demo.function.test.robo.UserDao;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.Random;

/**
 * Created by HeJiaJun on 2019/8/26.
 * Email:hejj@mama.cn
 * des:
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, manifest = Config.NONE, sdk = Build.VERSION_CODES.JELLY_BEAN, application = RoboApp.class)
public class DaoTest {
    UserDao dao;
    @Before
    public void setUp() throws Exception {
        // 用随机数做数据库名称，让每个测试方法，都用不同数据库，保证数据唯一性
        DbHelper dbHelper = new DbHelper(RuntimeEnvironment.application, new Random().nextInt(1000) + ".db");
        SQLiteDatabase db       = dbHelper.getWritableDatabase();

        dao = new UserDao(db);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testInsertAndGet() {
        User bean = new User(1, "键盘男");

        dao.insert(bean);

        User retBean = dao.get(1);

        Assert.assertEquals(retBean.uid, 1);
        Assert.assertEquals(retBean.name, "键盘男");
    }


}