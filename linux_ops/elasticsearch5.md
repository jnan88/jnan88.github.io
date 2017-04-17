## elasticsearch-head
```
git clone git://github.com/mobz/elasticsearch-head.git //下载
cd elasticsearch-head
npm install
//安装phantomjs-prebuilt报错
npm install phantomjs-prebuilt@2.1.14 --ignore-scripts

npm install -g grunt-cli(安装过请忽略)
/*
在elasticsearch-head目录下node_modules/grunt下如果没有grunt二进制程序，需要执行
cd elasticsearch-head
npm install grunt --save
*/

grunt server
open http://localhost:9100/
浏览器可以看到head提供的页面,但是连接不上elasticsearch.需要在elasticsearch5.2.2的配置里增加一下参数
http.cors.enabled: true
http.cors.allow-origin: "*"
```
