# BigBang
## 说明
受锤子M1发布会功能演示的启发，正好看讯飞SDK时发现他们有和哈工大合作的[分词的SDK](http://www.ltp-cloud.com/)，何不借来一用，直接在普通手机上实现BigBang呢？

## 应用形态
模仿锤子M1新出的BigBang功能,利用Android Service在后台监听剪切板，当用户在系统任一地方长按文本点击复制的时候，后台自动弹出界面，把词炸开，选中想要的分词，可以复制成一句话。

## 开发进度
2016/10/22 23:27 init commit

2016/10/23 01:00 分词接口请求通过

2016/10/23 05:40 基本功能完成,主路径测试通过

## 最终效果
![锤子BigBang](http://www.lizhengxian.top/img/copy.gif)

（如果效果gif图裂请刷新）

云服务的免费接口有每日流量限制，如果遇服务器返回错误503，说明今日流量已用尽。希望大家仅做学习使用。
项目正在完善当中,如有疑问和bug欢迎交流,作者博客:[http://www.lizhengxian.top](http://www.lizhengxian.top)
