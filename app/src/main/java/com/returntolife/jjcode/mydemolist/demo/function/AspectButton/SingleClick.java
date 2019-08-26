package com.returntolife.jjcode.mydemolist.demo.function.AspectButton;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by HeJiaJun on 2019/6/25.
 * Email:455hejiajun@gmail
 * des:
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SingleClick {
    long value();
}
