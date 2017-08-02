# WheelGame
> 该项目是 一款老虎机游戏， 其中3个滚轮会滚动，成功后3个滚轮中的图形会一致，失败会有一个图形不一样。
>>可控制中奖条目<br>
>>可控制转动圈数<br>
>>可控制转动速度<br>
>>but~!~!~!~! 哥们我最近太懒，没有封装成jar，，，各个参数谁用可以在程序中自行改参数
效果图如下：
<div align="center">
<img src="https://github.com/littlefishwill/WheelGame/blob/master/app/whellview1.jpg"  height="515" width="290">
<img src="https://github.com/littlefishwill/WheelGame/blob/master/app/whellview2.jpg"  height="515" width="290">
<img src="https://github.com/littlefishwill/WheelGame/blob/master/app/whellview3.jpg"  height="515" width="290">

 </div>
<br>
<br/>





## 核心方法讲解

> 该方法是控制滚轮中奖与否的方法.
>>第一个参数，我在demo中传入的是所有奖品图片的资源数组"
new Integer[]         {R.drawable.a,R.drawable.b,R.drawable.c,R.drawable.d,R.drawable.e,R.drawable.f};<br>"<br>
>>第二个参数就是控制传入int[]数组（第一个参数）中的第几个中奖（ps:中奖就是3个转动结束后一样）<br>
>>第三个参数就是控制要转几圈后停止（数字越大，转的时间越长）<br>
>>第四个参数就是游戏是否成功了（ps：成功=三个转完为一样的图标，失败=3个不一样）<br>

```/**
     *  选中 规则 数据初始化
     * @param arrs  要初始化的序列数据（此处为 奖品图片序列）
     * @param choosePos  成功后选中的 条目 在数据序列中的角标
     * @param endScrollPos 要旋转几次（）次数多了，
     * @param isSuccess  （游戏是否成功了）成功 数据序列会使3个都一样，失败序列会有其中1个不一样
     */
    public void preparePlayDateSequence(final Integer[] arrs, final int choosePos, final int endScrollPos, final boolean isSuccess){...```
