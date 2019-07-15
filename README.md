#简洁的项目框架
<br><br>
项目基于mvp进行设计，封装一些基础功能，对项目配置进行了处理，方便打包调试以及jenkis持续集成<br>
网络请求采用retrofit进行二次封装，添加自定义拦截器，统一处理返回数据，文件下载进度回调等<br>
图片处理采用glide进行二次封装，面向接口编程，方便后期框架的替换，并实现了图片下载进度的回调<br>
对view、model、presenter和Activity、Fragment都抽离了一个基类，做一些公共功能的处理<br>
加载弹窗、页面加载状态页面、标题栏（里面实现了状态栏处理)、圆角图片等控件进行了自定义处理<br>
一些通用的工具类，如JSON处理、SP缓存处理、安卓FileProvider处理、富文本处理等<br><br>
项目通过多res资源目录拆分和代码按模块进行整理达到一定的模块化效果
![](https://github.com/jiulong160/ProjectFramework/blob/master/img/1563154378(1).jpg?raw=true)<br><br>
项目通过对signingConfigs、buildTypes进行配置，可Build Variants中选择运行的模式，在Terminal中输入assembleRelease、assembleDebug执行打不同的apk文件
![](https://github.com/jiulong160/ProjectFramework/blob/master/img/1563154421(1).jpg?raw=true)
![](https://github.com/jiulong160/ProjectFramework/blob/master/img/1563154446(1).jpg?raw=true)
![](https://github.com/jiulong160/ProjectFramework/blob/master/img/1563155487(1).jpg?raw=true)<br><br>
项目中将一些需要修改的属性放置到了配置文件中，如版本号、联调生产域名，如果还有需要配置的可添加
![](https://github.com/jiulong160/ProjectFramework/blob/master/img/1563154511(1).jpg?raw=true)

