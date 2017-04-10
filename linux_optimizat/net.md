## 网络优化

```
#vi /etc/sysctl.conf
#加入下面几行
net.ipv6.conf.all.disable_ipv6 = 1
net.ipv6.conf.default.disable_ipv6= 1
net.ipv4.neigh.default.gc_stale_time=120
net.ipv4.conf.default.arp_announce = 2
net.ipv4.conf.all.arp_announce=2
vm.swappiness = 0
net.ipv4.tcp_max_tw_buckets = 5000
net.ipv4.tcp_max_syn_backlog = 1024
net.ipv4.tcp_synack_retries = 2
net.ipv4.conf.lo.arp_announce=2
net.ipv4.tcp_keepalive_time = 1800
net.ipv4.tcp_keepalive_probes = 3
net.ipv4.tcp_keepalive_intvl = 15
net.ipv4.tcp_syncookies = 1
net.ipv4.tcp_tw_reuse = 1
net.ipv4.tcp_tw_recycle = 1
net.ipv4.tcp_fin_timeout = 30
#执行命令生效:/sbin/sysctl -p
```

  
## 加大打开文件数的限制（open files）

此步骤需要重启机器生效，可以设置完后再重启
```
#vi /etc/security/limits.conf
#最后添加
* soft nofile 1024000
* hard nofile 1024000
hive   - nofile 1024000
hive   - nproc  1024000
```
## 用户进程限制
```
#vi /etc/security/limits.d/20-nproc.conf
#加大普通用户限制  也可以改为unlimited
*          soft    nproc     40960
root       soft    nproc     unlimited
```
