## elasticsearch5
```
# /etc/elasticsearch/elasticsearch.yml
#集群名（同一个集群，名称必须相同）
cluster.name: my-cluster-es
#服务节点名（每个服务节点不一样）
node.name: node-1
#数据存储路径
path.data: /opt/elastic/elasticsearch-5.6.3/data
#服务日志路径
path.logs: /opt/elastic/elasticsearch-5.6.3/logs
#服务ip地址
network.host: 0.0.0.0
#服务端口
http.port: 9200
#################
transport.tcp.port: 9300
transport.tcp.compress: true
http.cors.enabled: true
http.cors.allow-origin: "*"
```
## kibana配置
```
# disable-swap-files
sudo swapoff -a

# System Configuration
vi /etc/security/limits.conf
* soft nofile 65536
* hard nofile 131072
* soft nproc 2048
* hard nproc 4096
elasticsearch  -  nofile  65536

vi /etc/security/limits.d/90-nproc.conf 
修改如下内容：
* soft nproc 1024
#修改为
* soft nproc 2048

vi /etc/sysctl.conf
vm.max_map_count=655360
vm.swappiness=1
sysctl -p

# Enable bootstrap.memory_lock
vi config/elasticsearch.yml
```
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
