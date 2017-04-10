## 查看CPU、内存等信息
```
# cpu
grep "model name" /proc/cpuinfo 
cat /proc/cpuinfo | grep name | cut -f2 -d: | uniq -c
cat /proc/cpuinfo | grep physical | uniq -c
# cpu位数
echo $HOSTTYPE
# 内存
grep MemTotal /proc/meminfo 
# linux 版本
cat /etc/redhat-release
# linux 内核版本
uname -a 或uname -r

```

## linux连接状态查看
> netstat -n | awk '/^tcp/ {++S[$NF]} END {for(a in S) print a, S[a]}'

```
CLOSED  //无连接是活动的或正在进行
LISTEN  //服务器在等待进入呼叫
SYN_RECV  //一个连接请求已经到达，等待确认
SYN_SENT  //应用已经开始，打开一个连接
ESTABLISHED  //正常数据传输状态/当前并发连接数
FIN_WAIT1  //应用说它已经完成
FIN_WAIT2  //另一边已同意释放
ITMED_WAIT  //等待所有分组死掉
CLOSING  //两边同时尝试关闭
TIME_WAIT  //另一边已初始化一个释放
LAST_ACK  //等待所有分组死掉
```

## 创建ssh私钥
> ssh-keygen -t rsa -b 4096 -C "your@email.com"

## rsync同步文件
命令详解
```
rsync [OPTION] SRC... [DEST]
SRC：源文件
DEST：目标文件
option参数说明：    #一般使用-avz就可以
-a：归档模式，递归并保留对象属性，等同于 -rlptgoD
-r：递归模式，包含目录及子目录中所有文件
-l：对于符号链接文件仍然复制为符号链接文件
-p：保留文件的权限标记
-t：保留文件的时间标记
-g：保留文件的属组标记（仅超级用户使用）
-o：保留文件的属主标记（仅超级用户使用）
-D：保留设备文件及其他特殊文件
-v：显示同步过程的详细（verbose）信息
-d：仅仅同步目录权（不同步文件）
-z：在传输文件时进行压缩（compress）
-H：保留硬连接文件
-A：保留ACL属性信息
--delete：删除目标位置有而原始位置没有的文件
--checksum：根据对象的校验和来决定是否跳过文件
--include：包含文件
--exclude：排除文件
```
rsync同步
```
#从远程同步文件本地
rsync -zvrtopgl --progress --delete root@remote-ip:/www/remote/  /www/local/

#从本地同步文件到远程服务器
rsync -zvrtopgl --progress --delete /www/local/ root@remote-ip:/www/remote/
```
