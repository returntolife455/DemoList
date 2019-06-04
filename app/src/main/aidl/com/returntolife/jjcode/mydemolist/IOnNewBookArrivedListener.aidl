// IOnNewBookArrivedListener.aidl
package com.returntolife.jjcode.mydemolist;

// Declare any non-default types here with import statements
import com.returntolife.jjcode.mydemolist.bean.AIDLBook;

interface IOnNewBookArrivedListener {

    void onNewsBookArrived(in AIDLBook book);
}
