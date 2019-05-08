## ARouter源码解析

1、ARouter的用法

2、ARouter的工作原理

### 1、ARouter的用法

ARouter的项目地址:*https://github.com/alibaba/ARouter*

这里只列举组件件跳转的简单例子:

```java
ARouter.getInstance()
       .build("/test/activity1")
       .withString("name", "老王")
       .withInt("age", 18)
       .withBoolean("boy", true)
       .navigation();
```

上述代码表示 带着4个参数跳转到地址为"/test/activity1"的Activity

```java
@Route(path = "/test/activity1")

public class Test1Activity extends AppCompatActivity {

    @Autowired

    String name = "jack";

    @Autowired
    int age = 10;

    @Autowired(name = "boy", required = true)
    boolean girl;

......
```

更加详细的用法可以参考上面的项目地址

### 2、ARouter的工作原理

该框架核心技术是使用APT(编译时注解)+javapoet自动生成代码

在Activity中用注解@Route(path = "...") 标注后，在编译过程会产生相应的文件

以上面普通组件间跳转为例，生产的代码:

```java
public class ARouter$$Root$$app implements IRouteRoot {
  @Override
  public void loadInto(Map<String, Class<? extends IRouteGroup>> routes) {
    routes.put("test", ARouter$$Group$$test.class);

  }
}
```

```java
public class ARouter$$Group$$test implements IRouteGroup {
  @Override
  public void loadInto(Map<String, RouteMeta> atlas) {
    atlas.put("/test/activity1", RouteMeta.build(RouteType.ACTIVITY, Test1Activity.class, "/test/activity1", "test", new java.util.HashMap<String, Integer>(){{ put("boy", 0);put("age", 3);put("name", 8);}}, -1, -2147483648));

  }
}
```

第一个类是将路由组件进行分组分类，上面例子中@Route(path = "/test/activity1") 就是将activity1 分到test组中，最后把不同的组存放到 Map<String, Class<? extends IRouteGroup>> routes 集合中。

第二个类是将同一组中不同的路由组件存放到Map<String, RouteMeta> atlas 集合中

两个Map 集合都是存放在Warehouse 中同一管理

```java
class Warehouse {
    // Cache route and metas
    static Map<String, Class<? extends IRouteGroup>> groupsIndex = new HashMap<>();
    static Map<String, RouteMeta> routes = new HashMap<>()

......
}
```

到这里我们先总结一下过程:

1、在需要跳转的Activity中标记 @Route注解

2、当程序编译时使用APT技术生产 IRouteRoot 和IRouteGroup两个类

3、框架初始化时，读取生产的类并将其存放到Warehouse 仓库中

4、当调用navigation()方法时在Warehouse仓库查找对应的路由组件

5、使用原生的方法实现跳转

#### 2.1 ARouter.init() 初始化

```java
// ARouter.class 
public static void init(Application application) {
        if (!hasInit) { //如果没初始化则进行初始化

            ......
            hasInit = _ARouter.init(application); //剑桥模式，正在初始化是在_ARouter类中

            if (hasInit) {
                _ARouter.afterInit();
            }

            ......

        }
    }
```

```java
// _ARouter.class
protected static synchronized boolean init(Application application) {
        mContext = application;
        LogisticsCenter.init(mContext, executor); 

        logger.info(Consts.TAG, "ARouter init success!");
        hasInit = true;
        mHandler = new Handler(Looper.getMainLooper());

        return true;
    }
```

调用了LogisticsCenter.init()方法。两个参数:

第一个是上下文 

第二个是一个线程池 executor 是静态成员变量

```java
private volatile static ThreadPoolExecutor executor = DefaultPoolExecutor.getInstance();
...... 
//线程池的配置
instance = new DefaultPoolExecutor(
                            INIT_THREAD_COUNT,//CPU数量+1

                            MAX_THREAD_COUNT,//CPU数量+1

                            SURPLUS_THREAD_LIFE,//保存时间30秒

                            TimeUnit.SECONDS,
                            new ArrayBlockingQueue<Runnable>(64),
                            new DefaultThreadFactory());
```

我们再进去LogisticsCenter.init() 看看

```java
public synchronized static void init(Context context, ThreadPoolExecutor tpe) throws HandlerException {

    ......  
    Set<String> routerMap;

    // It will rebuild router map every times when debuggable.
                if (ARouter.debuggable() || PackageUtils.isNewVersion(context)) {

    // 1、在生产的代码的包下读取apt生产的类文件
    //    过指定包名，扫描包下面包含的所有的ClassName

                    routerMap = ClassUtils.getFileNameByPackageName(mContext, ROUTE_ROOT_PAKCAGE);
                    if (!routerMap.isEmpty()) {
                        context.getSharedPreferences(AROUTER_SP_CACHE_KEY, Context.MODE_PRIVATE).edit().putStringSet(AROUTER_SP_KEY_MAP, routerMap).apply();
                    }

                    PackageUtils.updateVersion(context);    // Save new version name when router map update finishes.
                } else {

                    routerMap = new HashSet<>(context.getSharedPreferences(AROUTER_SP_CACHE_KEY, Context.MODE_PRIVATE).getStringSet(AROUTER_SP_KEY_MAP, new HashSet<String>()));
                }
        ......
        //2、遍历获取的className集合
        for (String className : routerMap) {


                    if (className.startsWith(ROUTE_ROOT_PAKCAGE + DOT + SDK_NAME + SEPARATOR + SUFFIX_ROOT)) {
                       // 如果是IRouteRoot类，通过反射创建对象，并调用loadInto()方法将IRouteRoot对象存放到Warehouse.groupsIndex集合中

                        ((IRouteRoot) (Class.forName(className).getConstructor().newInstance())).loadInto(Warehouse.groupsIndex);
                    } else if (className.startsWith(ROUTE_ROOT_PAKCAGE + DOT + SDK_NAME + SEPARATOR + SUFFIX_INTERCEPTORS)) {
                        // 拦截器也是如此类推

                        ((IInterceptorGroup) (Class.forName(className).getConstructor().newInstance())).loadInto(Warehouse.interceptorsIndex);
                    } else if (className.startsWith(ROUTE_ROOT_PAKCAGE + DOT + SDK_NAME + SEPARATOR + SUFFIX_PROVIDERS)) {
                        // Load providerIndex
                        ((IProviderGroup) (Class.forName(className).getConstructor().newInstance())).loadInto(Warehouse.providersIndex);
                    }
                }
}
```

LogisticsCenter.init()方法也是比较清晰的，在固定包名路径下获取通过编译时生成的

IRouteRoot类、拦截器组IInterceptorGroup、服务组IProviderGroup并将它们放到WareHouse仓库中。到这里初始化已经完成了，但是Acitivity相关的路由还没初始化。

在ARouter.init()方法中初始化完成后，接着调用afterInit()方法

```java
// ARouter.class
if (hasInit) {
 _ARouter.afterInit();
 }
```

```java
// _ARouter.class
static void afterInit() {
        // 跳转到/arouter/service/interceptor 组件中

        interceptorService = (InterceptorService) ARouter.getInstance().build("/arouter/service/interceptor").navigation();
    }
```

该方法初始化interceptorService的实现类InterceptorServiceImpl并调用init()方法

```java
// InterceptorServiceImpl.class
public void init(final Context context) {
        //1、该线程就是上面所提到的线程池,执行任务
        LogisticsCenter.executor.execute(new Runnable() {
            @Override
            public void run() {
            //2、判断Warehouse仓库中是否有拦截器

                if (MapUtils.isNotEmpty(Warehouse.interceptorsIndex)) {
                    for (Map.Entry<Integer, Class<? extends IInterceptor>> entry : Warehouse.interceptorsIndex.entrySet()) {
                    //3、遍历拦截器，通过反射创建拦截器对象

                        Class<? extends IInterceptor> interceptorClass = entry.getValue();
                        try {
                            IInterceptor iInterceptor = interceptorClass.getConstructor().newInstance();
                            //4、执行初始化方法

                            iInterceptor.init(context);
                            Warehouse.interceptors.add(iInterceptor);
                        } catch (Exception ex) {
                            throw new HandlerException(TAG + "ARouter init interceptor error! name = [" + interceptorClass.getName() + "], reason = [" + ex.getMessage() + "]");
                        }
                    }

                    interceptorHasInit = true;
                    synchronized (interceptorInitLock) {
                        interceptorInitLock.notifyAll();
                    }
                }
            }
        });
    }
```

到此ARouter的初始化全部完成，这里总结一下流程:

1、扫描编译时生产的类，包括IRouteRoot类、拦截器组IInterceptorGroup、服务组IProviderGroup

2、通过反射创建对象并将其存放到WareHouse仓库中

3、创建线程池

4、初始化所有拦截器

#### 2.2ARouter Navigation()跳转

```java
ARouter.getInstance()
 .build("/test/activity1")
 .withString("name", "老王")
 .withInt("age", 18)
 .withBoolean("boy", true)
 .navigation();
```

ARouter.getInstance()获取ARouter单例

调用build方法传入要跳转的url地址

```java
// ARouter.class
public Postcard build(String path) {
        return _ARouter.getInstance().build(path);
    }
```

获取_ARouter单例，调用_ARouter的build()方法

```java
//_ARouter.class
protected Postcard build(String path) {
        if (TextUtils.isEmpty(path)) {
            throw new HandlerException(Consts.TAG + "Parameter is invalid!");
        } else {
            PathReplaceService pService = ARouter.getInstance().navigation(PathReplaceService.class);
            if (null != pService) {
                path = pService.forString(path);
            }
            return build(path, extractGroup(path));
        }
    }
```

先判断地址path是否为空

再判断是否有替换地址的服务

extractGroup(path) 方法将path字符串中第1,2个 "/" 之间的字符串作为group 组名 

创建Postcard对象

```java
//Postcard.class
public final class Postcard extends RouteMeta {
   

    private Uri uri;
    private Object tag;             

    private Bundle mBundle;         // 与Intent putExtra传入Bundle对应

    private int flags = -1;         // 与Intent addFlags对应

    private int timeout = 300;      // 跳转超时时间
    

    private IProvider provider;    
    

    private boolean greenChannel;    //是否是绿色通道，是则可以跳过拦截器

    private SerializationService serializationService;


    // Animation
    private Bundle optionsCompat;    // The transition animation of activity
    private int enterAnim = -1;    //跳转动画

    private int exitAnim = -1;
    ......
}
```

Postcard 是继承RouteMeta的,RouteMeta 包含了最基本的路由配置信息

path地址，group分组，type分类，priority优先权等都在RouteMeta属性中

```java
.with...(key,value); 
```

调用上述Postcard中的mBundle对象的put...("key",value)方法将需要传递的传递保存到Bundle中

```java
navigation();
```

最后调用_ARouter中navigation方法

```java
// _ARouter.class
protected Object navigation(final Context context, final Postcard postcard, final int requestCode, final NavigationCallback callback) {
        try {
            LogisticsCenter.completion(postcard);// 1、解析postcard

        } catch (NoRouteFoundException ex) {// 2、报异常时执行相关回调方法

            ......
            if (null != callback) {        

                callback.onLost(postcard);

            } else {    // No callback for this invoke, then we use the global degrade service.
                DegradeService degradeService = ARouter.getInstance().navigation(DegradeService.class);
                if (null != degradeService) {
                    degradeService.onLost(context, postcard);
                }
            }

            return null;
        }

        if (null != callback) {        //如果找到相关路由，则执行onFound()

            callback.onFound(postcard);
        }
        //3、如果不是绿色通道则执行拦截器链中的拦截功能

        if (!postcard.isGreenChannel()) {   // It must be run in async thread, maybe interceptor cost too mush time made ANR.
            interceptorService.doInterceptions(postcard, new InterceptorCallback() {
                @Override
                public void onContinue(Postcard postcard) {
                //4、真正的跳转方法

                    _navigation(context, postcard, requestCode, callback);
                }

                @Override
                public void onInterrupt(Throwable exception) {
                    if (null != callback) {
                        callback.onInterrupt(postcard);
                    } 
                }
            });
        } else {
            return _navigation(context, postcard, requestCode, callback);
        }

        return null;
    }
```

1、解析postcard

```java
public synchronized static void completion(Postcard postcard) {

        RouteMeta routeMeta = Warehouse.routes.get(postcard.getPath());//1、根据路径在Warehouse仓库中查询对应的路由配置

        if (null == routeMeta) { //2、如果找不到则查询是否有对应分组
            Class<? extends IRouteGroup> groupMeta = Warehouse.groupsIndex.get(postcard.getGroup());  // Load route meta.
            if (null == groupMeta) {//如果找不到对应分组则抛出异常

                throw new NoRouteFoundException(TAG + "There is no route match the path [" + postcard.getPath() + "], in group [" + postcard.getGroup() + "]");
            } else {  //3、反射创建对应分组的对象并调用loadInto()方法将分组下对应的路由存放到Warehouse.routes中

                ...
                    IRouteGroup iGroupInstance = groupMeta.getConstructor().newInstance();
                    iGroupInstance.loadInto(Warehouse.routes);
                    Warehouse.groupsIndex.remove(postcard.getGroup());
                ...
                 completion(postcard); // 重新调用   
            }
        }else {//4、如果找到对应的Meta路由则 将Meta的属性配置给postcard
            ...

        }  

}
```

completion() 是按需加载，首次使用时才开始加载路由信息，方法主要步骤如下:

1. 根据postcard 中地址在Warehouse仓库查询，如果存在则将Meta的属性配置给postcard。

2. 如果Warehouse找不到对应的Meta路由信息，则从Warehouse中找postcard对应的分组，如果存在则在分组里找对应的路由信息

3. 如果还找不到路由信息，如果编译时生成的class分组还没初始化。通过反射创建对应分组的对象并调用loadInto()方法将分组下对应的路由存放到Warehouse.routes中。

4. 初始化完成后再次执行步骤 1



回到_ARouter 中的navigation()方法第二步

2、 如果我们调用Postcard的navigation方法时，传入了NavCallback参数则会调用相关的回调方法。如果找不到配置信息则调用onLost()方法，找到则调用onFound()方法。



3、如果postcard没有绿色通道，则调用拦截器链中的拦截器方法。

interceptorService.doInterceptions()

interceptorService 在调用ARouter.init()方法的时候初始化。它的实现类是InterceptorServiceImpl.class

```java
// InterceptorServiceImpl.class 
public void doInterceptions(final Postcard postcard, final InterceptorCallback callback) {
        if (null != Warehouse.interceptors && Warehouse.interceptors.size() > 0) {

            checkInterceptorsInitStatus();//检查拦截器链初始化状态

            ...
            // 线程池中执行拦截任务

            LogisticsCenter.executor.execute(new Runnable() {
                @Override
                public void run() {
                // 倒数锁 参数为需要等待的子线程数

                    CancelableCountDownLatch interceptorCounter = new CancelableCountDownLatch(Warehouse.interceptors.size());
                    try {
                    //1.主线程等待,子线程执行拦截方法
                    // _excute()方法递归执行每个拦截器的方法,执行完成后释放倒数锁
                        _excute(0, interceptorCounter, postcard);                        
                    //主线程等待所有子线程完成后再往下执行
                    interceptorCounter.await(postcard.getTimeout(), TimeUnit.SECONDS);
                        if (interceptorCounter.getCount() > 0) {    

                            callback.onInterrupt(new HandlerException("The interceptor processing timed out."));
                        } else if (null != postcard.getTag()) {    

                            callback.onInterrupt(new HandlerException(postcard.getTag().toString()));
                        } else {
                        //2. 执行完所以拦截方法后调用回调方法

                            callback.onContinue(postcard);
                        }
                    } catch (Exception e) {
                        callback.onInterrupt(e);
                    }
                }
            });
        } else {
            callback.onContinue(postcard);
        }
    }
```

doInterceptions()方法主要步骤

1. 线程池开启线程执行每个拦截器的拦截功能，主线程等待所有子线程执行完成后再往下执行。

2. 当所有拦截器执行完成后，调用onContinue()方法

onContinue()方法中执行了_Arouter的_navigation()方法，实现真正的跳转功能



4、_Arouter的_navigation()

```java
// _Arouter.class
private Object _navigation(final Context context, final Postcard postcard, final int requestCode, final NavigationCallback callback) {
        final Context currentContext = null == context ? mContext : context;

        switch (postcard.getType()) {
            case ACTIVITY:
                // Build intent
                final Intent intent = new Intent(currentContext, postcard.getDestination());
                intent.putExtras(postcard.getExtras());

                // Set flags.
                int flags = postcard.getFlags();
                if (-1 != flags) {
                    intent.setFlags(flags);
                } else if (!(currentContext instanceof Activity)) {    // Non activity, need less one flag.
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                }

                // Set Actions
                String action = postcard.getAction();
                if (!TextUtils.isEmpty(action)) {
                    intent.setAction(action);
                }

                // Navigation in main looper.
                runInMainThread(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(requestCode, currentContext, intent, postcard, callback);
                    }
                });

                break;
            ...
                return null;
        }

        return null;
    }
```

到这里，就是我们熟悉的Activity跳转的原生方法了，整个路由的跳转也分析完了。
