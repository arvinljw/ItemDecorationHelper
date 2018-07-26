# ItemDecorationHelper

RecyclerView提供了良好的设计，将各部分功能的实现都拆解开，方便自定义，虽然加大了使用难度，但是却大大的增加了可扩展性，稍微会使用之后，扩展起来非常的舒心。

**功能**：适用于以下几种布局的分割线和粘性头部

* LinearLayoutManager
* GridLayoutManager *（水平方向粘性头部暂未实现）*
* StaggeredGridLayoutManager *（粘性头部暂未实现）*

**对于分割线，与大多数的实现不同，在GridLayoutManager和StaggeredGridLayoutManager布局时是平分Item的，分割线大的时候就能发现**

先上效果图

![image.png](https://upload-images.jianshu.io/upload_images/3157525-d893956a04d45f2a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

*注意：其中这三种布局的分割线不管是水平还是垂直方向都是支持的，但是带有header的只支持LinearLayoutManager的水平和垂直以及GridLayoutManager垂直方向*

还可以[下载Demo](https://github.com/arvinljw/ItemDecorationHelper/tree/master/app/Demo.apk)在手机上预览一下。

### 使用

**1、在根目录的build.gradle中加入如下配置**

```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

**2、在要用的module中增加如下引用**

```
dependencies {
    ...
    implementation 'com.github.arvinljw:ItemDecorationHelper:v1.0.0'
    implementation 'com.android.support:recyclerview-v7:'.concat(supportVersion)
}
```

*其中supportVersion换成自己app中的版本即可*

**3、为RecyclerView添加ItemDecoration**，通过ItemDecorationFactory生成包括分割线的ItemDecoration和粘性头部的ItemDecoration，后者包含了分割线

* ItemDecorationFactory.DividerBuilder 分割线

```
itemDecoration = new ItemDecorationFactory.DividerBuilder()
        .dividerHeight(2)
        .dividerColor(Color.parseColor("#D8D8D8"))
        .build(recyclerView);
recyclerView.addItemDecoration(itemDecoration);
```

* ItemDecorationFactory.StickyDividerBuilder 粘性头部

```
itemDecoration = new ItemDecorationFactory.StickyDividerBuilder()
        .dividerHeight(2)
        .dividerColor(Color.parseColor("#D8D8D8"))
        .callback(new StickyDividerCallback() {
            @Override
            public GroupData getGroupData(int position) {
                //生成GroupData
                return data;
            }
            @Override
            public View getStickyHeaderView(int position) {
					//...生成headerView
                return headerView;
            }
        }).build(recyclerView);
recyclerView.addItemDecoration(itemDecoration);
```

其中如果是粘性头部，需要自己生成GroupData和自定义View的设置，GroupData需要设置，**分组标题，在分组中的位置以及该分组的长度**；至于如何实现，需要根据数据格式定义不同的逻辑，可以参照[demo中的实现逻辑去获取](https://github.com/arvinljw/ItemDecorationHelper/blob/master/app/src/main/java/net/arvin/itemdecorationhelper/sample/LinearActivity.java)，该类的getItemDecoration方法。

**对于分割线没啥说的，可以设置分割线的颜色和大小。**

其中对于显示

#### TODO

* 为粘性头部增加点击事件
* 增加默认的头部，只含文本
* 增加对头部View的缓存优化
* 优化实现方式

### 原理

继承ItemDecoration，然后重写`getItemOffsets`和`onDraw`方法，实现分割线的绘制，通过重写`onDrawOver`实现粘性头部。

原理很简单，就是首先让itemView在你需要的方向偏移，例如left，top，right，botton；然后再在`onDraw`或者`onDrawOver`方法中获取到当前屏幕的view，并根据他们的位置，按照偏移的逻辑在相应区域绘制即可。借用一张图，感谢[【Android】RecyclerView：打造悬浮效果](https://www.jianshu.com/p/b335b620af39)。

![](https://upload-images.jianshu.io/upload_images/1638147-9e8a8158237c005c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/548)

很直观，可以看到不同内容绘制在不同层次，位置重合的话，上层会覆盖下层的内容。

具体的实现全是根据这个逻辑计算绘制。其中为了保持平分内容，将分割线拆分，如图：感谢[RecyclerView的 GridItemDecoration等分itemView](https://blog.csdn.net/qq_27192795/article/details/80563487)

![](https://upload-images.jianshu.io/upload_images/3157525-0c77a946eb7278c6.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

具体的难点就在这两个位置。

至于粘性头部，核心逻辑就是对数据的分组以及头部的偏移计算和绘制。也是万变不离其中。具体的实现请参考源码，含有一定的注释。


## License

```
Copyright 2018 arvinljw

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.