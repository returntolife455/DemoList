// IPerson.aidl
package com.returntolife.jjcode.mydemolist;

// Declare any non-default types here with import statements
import com.returntolife.jjcode.mydemolist.bean.AIDLBook;

interface IPerson {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void  setName(String s);
    String  getName();

    void setBook(in AIDLBook book);
    AIDLBook getBook();
}
