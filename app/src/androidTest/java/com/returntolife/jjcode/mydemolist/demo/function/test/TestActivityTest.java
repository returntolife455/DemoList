package com.returntolife.jjcode.mydemolist.demo.function.test;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.returntolife.jjcode.mydemolist.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

/**
 * Created by HeJiaJun on 2019/8/26.
 * Email:hejj@mama.cn
 * des:
 */
@RunWith(AndroidJUnit4.class)
public class TestActivityTest {

//    @Rule
//    public TestActivityTest<> rule = new TestActivityTest<>(TestActivity.class);

    @Rule
    public ActivityTestRule<TestActivity> rule = new ActivityTestRule<>(TestActivity.class);


    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test() throws Exception{
        //tvContent是否默认不显示
        onView(withId(R.id.tv_content))
            .check(matches(is(isDisplayed())));    //是否不可见

        onView(withId(R.id.tv_content))
            .check(matches(is(isEnabled())));    //是否不可见

        //检查btn01的text，然后执行点击事件
        onView(withId(R.id.btn_clear))
            .perform(click());

        onView(withId(R.id.tv_content))
            .check(matches(withText("")));    //是否不可见

        onView(withId(R.id.btn_test))
            .perform(click());

        onView(withId(R.id.tv_content))
            .check(matches(withText("test")));    //是否不可见
    }
}