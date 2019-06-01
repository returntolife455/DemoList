package com.returntolife.jjcode.mydemolist.demo.function.AnnotateMvp;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface CreatePresenter {
    Class<? extends BaseContract.BasePresenter> value();
}
