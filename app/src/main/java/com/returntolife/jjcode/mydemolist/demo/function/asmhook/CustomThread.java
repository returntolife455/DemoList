package com.returntolife.jjcode.mydemolist.demo.function.asmhook;



public class CustomThread extends Thread {
    private static final String TAG = "CustomThread";
    public CustomThread() {
        super();
    }


    public CustomThread(Runnable target) {
        super(target);
    }


    public CustomThread(ThreadGroup group, Runnable target) {
        super(group, target);
    }


    public CustomThread(String name) {
        super(name);
    }

    public CustomThread(ThreadGroup group, String name) {
        super(group, name);
    }

    public CustomThread(final Runnable runnable, final String name) {
        super(runnable, name);
    }

    @Override
    public void run() {
        throw new IllegalArgumentException("不可以自行new 线程");
    }
}
