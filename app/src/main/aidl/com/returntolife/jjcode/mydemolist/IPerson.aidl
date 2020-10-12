// IPerson.aidl
package com.returntolife.jjcode.mydemolist;

// Declare any non-default types here with import statements
import com.returntolife.jjcode.mydemolist.bean.AIDLBook;
import com.returntolife.jjcode.mydemolist.IOnNewBookArrivedListener;

interface IPerson {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void  setName(String s);
    String  getName();

    void setBook(in AIDLBook book);
    AIDLBook getBook();

    void addBookWithIn(in AIDLBook book);
    void addBookWithOut(out AIDLBook book);
    void addBookWithInOut(inout AIDLBook book);

    void registerListener(IOnNewBookArrivedListener listener);
    void unregisterListener(IOnNewBookArrivedListener listener);
}
