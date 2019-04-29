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
