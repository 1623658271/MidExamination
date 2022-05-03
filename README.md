# 中期考核  
## 星球App介绍  
注：削减了部分需求，加上了部分主观臆造的功能  
### 页面  
由四个Activity和五个Fragment组成  
第一个Activity："计时器"页面，"universe"页面，"我的"页面（五个fragment）  
第二个Activity："创建星球"界面  
第三个Activity："时间历程"界面  
第四个Activity："成就"界面  
### 功能及玩法  
**计时器记录历程**  
初始时分配三个星球，每个星球具有一个独立的计时器，当你停留在这个界面时  
，计时器会计时，并实时保存该星球的专注值，专注值可以达成成就并同其他属性  
展示在时间历程界面，（退出时不会计时）  
**点亮星球**  
当星球的点亮日期为当天时，即可点亮星球，并同其他属性展示在时间历程及成就  
界面，创建星球时由自己指定日期  
提供前台通知服务，每隔一段时间会查看是否有星球可点亮，并发送通知  
### bug介绍  
计时器界面的bottomSheetDialog来回滑动点击时，星球名称有时不会变蓝  
创建星球界面的星球外观来回滑动点击时，星球名称有时不会变蓝
### 缺点  
ui设计，没有实现环形滑动，更改需求中的功能  
做得比较匆忙
