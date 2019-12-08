# 基础UI库

基于QMUIAndroid封装的一套UI库，定义统一常用主题沉、浸式状态栏、通用Adapter、通用LoadingDialog等。

* 支持沉浸式状态栏
* 支持Activity手势返回
* 支持像Activity一样启动跳转Fragment，且Fragment支持手势返回；
  Activity需继承BaseFragmentActivity，Fragment需继承SwipeBackFragment
* 如果不需要一个Activity可以跳转多个Fragment，只需继承BaseActivity即可