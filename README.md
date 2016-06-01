# uexAlertView
alertView弹框插件

[TOC]
#1、简介

alertView弹框插件

#1.1、说明

uexAlertView插件主要通过前端自定义弹框的显示样式。

#1.2、UI展示

![](http://zymobi.appcan.cn/zymobiResource/docResource/406/IMG_0321.jpg)
![](http://zymobi.appcan.cn/zymobiResource/docResource/406/IMG_0323.jpg)

#2、API概览

##2.1、方法

>###open创建alertView对象

`uexAlertview.open()`

**说明:**

创建alertView对象，点击弹框上的按钮的回调方法[onItemClick](#onItemClick 点击弹框上的按钮回调方法 "onItemClick")

**参数:**

| 参数名称 | 参数类型  | 是否必选  |  说明 |
| ----- | ----- | ----- | ----- |
| parma | String | 是 | 该字符串为JSON格式，参见下方parma列表 |


parma 列表
```
var titleParams = {
            head:{ //(可选) 弹框标题
                title:, //(可选)标题文字
                color:, //(可选)标题文字颜色
            },
            message:{ //(可选)具体内容
                title:, //(可选)内容文字
                color:,//(可选)文字颜色
            },
            button:[ //(可选)按钮数组，每个字典是一个按钮
                {
                   title:,//(可选)按钮文字
                   color:,//(可选)按钮文字颜色
                },
                {
                   title:,//(可选)按钮文字
                   color:,//(可选)按钮文字颜
                },
                {
                    title:,//(可选)按钮文字
                   color:,//(可选)按钮文字颜
                }
            ]
        };
```

**平台支持:**

iOS6.0+

**版本支持:**

3.0.0+

**示例:**
```
var titleParams = {
            head:{
                title:"我只想安静的做一个美男子",
                color:"#000000"
            },
            message:{
                title:"string为第一个参数，也就是最右边的已知参也就是最右边的已知参\n[1].也就是最右边的已知参也就是最右边的已知参也就是最右边的已知参.也就是最右边的已知参也就是最右边的已知参也就是最右边的已知参也就是最右边的已知参.空",
                color:"#151314"
            },
            button:[
                {
                   title:"放弃",
                   color:"#000000"
                },
                {
                    title:"确定",
                    color:"#000000"
                },
                {
                    title:"思考",
                    color:"#000000"
                }
            ]
        };
        var data1 = JSON.stringify(titleParams);
        uexAlertView.open(data1);

```
>###close 关闭alertView弹框

`uexAlertView.close();`

**说明:**
关闭弹框

**平台支持:**

iOS6.0+

**版本支持:**

3.0.0+

**示例:**

```
uexAlertView.close();

```

>###openCustomProgress 打开进度条弹框

` uexAlertView.openCustomProgress()`

**说明:**

初始化进度条弹框，初始化成功回调[cbShow](#cbShow 初始化弹框的回调方法 "cbShow")

**参数说明:**

| 参数名称 | 参数类型  | 是否必选  |  说明 |
| ----- | ----- | ----- | ----- |
| parma | String | 是 | 该字符串为JSON格式，参见下方parma列表 |

parma 列表

| 参数名称 | 参数类型  | 是否必选  |  说明 |
| ----- | ----- | ----- | ----- |
|title|String|可选|弹框标题|
|titleColor|String|可选|标题字体颜色|
|progressViewColor|String|可选|进度条颜色|
|progressTitleColor|String|可选|进度条百分比文字颜色|


**平台支持:**

iOS6.0+

**版本支持:**

3.0.0+

**示例:**
```
var titleParams = {
        title:"一个安静的美男子",
        titleColor:"#000000",
        progressViewColor:"#FF0000",
        progressTitleColor:"#DC143C"
};
        var data1 = JSON.stringify(titleParams);
        uexAlertView.openCustomProgress(data1);
```
>### customProgressPresent 进度条百分比

`uexAlertView.customProgressPresent()`

**说明:**

进度条弹框上进度条百分比

**参数说明:**

|参数名称|参数类型|是否必选|说明|
|-----|-----|-----|-----|
|parma|String|必选|该字符串为JSON格式，参见下方parma列表|

parma 列表

|参数名称|参数类型|是否比选|说明|
|------|-----|-----|-----|
|num|Number类型|是|当前进度百分比|

**平台支持:**

iOS6.0+

**版本支持:**

3.0.0+



**示例:**
```
  var parma = {
           num:80
       }
	   uexAlertView.customProgressPresent(JSON.stringify(parma));
				   
```


##2.2、回调方法

>### onItemClick 点击弹框按钮回调

`uexAlertView.onItemClick(data)`

**参数:**

|参数名称|参数类型|是否必选|说明|
|------|-----|-----|------|
|data|String|是|返回点击按钮的下标,按钮文字,该字符串为JSON格式，参见下方data列表|

data 列表

|参数名称|参数类型|说明|
|------|-----|-----|
|index|String|该按钮的下标|
|name|String|该按钮的文字|

**平台支持:**

Android2.2+
iOS6.0+

**版本支持:**

3.0.0+

**示例:**

```
uexAlertView.onItemClick = function(jsonObj){
            var index = jsonObj.index;
            var name = jsonObj.name;
            alert(JSON.stringify(jsonObj));
}

```

>### cbShow 打开弹框回调

`uexAlertView.cbShow()`

**参数:**

|参数名称|参数类型|是否必选|说明|
|------|-----|-----|------|
|data|Number类型||打开进度条弹框后回调数字1，其他弹框回调数字0|


**平台支持:**

Android2.2+
iOS6.0+

**版本支持:**

3.0.0+

**示例:**

```
 uexAlertView.cbShow = function(data)
        {
           if (data == 1)
           {
               uexAlertView.close();
           }
        }
```

#3、更新历史

###iOS
API版本: `uexAlertView-3.0.0`
最近更新时间:`2016-5-16`

|历史版本|更新内容|
|------|-----|
|3.0.0|uexAlertView插件|

###Android
API版本: `uexAlertView-3.0.0`
最近更新时间:`2016-06-01`

|历史版本|更新内容|
|------|-----|
|3.0.0|uexAlertView插件|