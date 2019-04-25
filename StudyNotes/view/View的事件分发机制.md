# 事件分发

## 一、几个重要的方法

```java
public boolean dispatchTouchEvent(MotionEvent event)
```

官方文档解释

Pass the touch screen motion event down to the target view, or this  view if it is the target. 

 @param event The motion event to be dispatched. 分发的动作事件

 @return True if the event was handled by the view, false otherwise.  是否是当前View处理这个事件



通过触摸屏幕产生的动作事件下发到目标View，当前View也可以是目标View。

当事件传到该View时会调用此方法，返回的布尔值受该View的OnTouchEvent方法和子View的dispatchTouchEvent方法影响。

```java
public boolean onInterceptTouchEvent(MotionEvent event)
```

Implement this method to intercept all touch screen motion events.  This  

allows you to watch events as they are dispatched to your children, and  

take ownership of the current gesture at any point.

该方法可以拦截所有屏幕上的动作事件，并允许你去监听和决定是否把事件分发到子View中。

返回值表示 是否拦截当前事件

如果返回true 表示拦截该事件(同一个事件序列中此方法不会被调用)，并调用该View的onTouchEvent()方法

```java
public boolean onTouchEvent(MotionEvent event)
```

this method to handle touch screen motion events.

该方法用于处理动作事件。

返回值表示 是否消费当前事件(不消费则同一事件系列中当前View无法再接受事件)



上述三个方法可以用一段伪代码来表示它们的关系

```java
public boolean dispatchTouchEvent(MotionEvent event){
    boolean result = false;
    if(onInterceptTouchEvent(event)){
        result = onTouchEvent(event);
    }else{
        result = child.dispatchTouchEvent(event);
    }
    return result;
}
```

当事件传递到当前ViewGroup时，执行dispatchTouchEvent方法，同时调用onInterceptTouchEvent方法，如果该方法返回true表示拦截该事件，则调用onTouchEvent方法；如果该方法返回fase表示不拦截该事件，那么分发到子View中并调用子View的dispatchTouchEvent方法，如此递归下去直到最深层的子View。
