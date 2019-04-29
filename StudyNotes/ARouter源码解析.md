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
    routes.put("yourservicegroupname", ARouter$$Group$$yourservicegroupname.class);
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

#### 2.1 ARouter.init() 初始化

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

IRouteRoot类、拦截器组IInterceptorGroup、服务组IProviderGroup。到这里初始化已经完成了。

在ARouter.init()方法中初始化完成后，接着调用:afterInit()方法

```java
if (hasInit) {
 _ARouter.afterInit();
 }
```

```java
static void afterInit() {
        // 跳转到/arouter/service/interceptor 组件中

        interceptorService = (InterceptorService) ARouter.getInstance().build("/arouter/service/interceptor").navigation();
    }
```

该方法初始化interceptorService的实现类InterceptorServiceImpl并调用init()方法

```java
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
