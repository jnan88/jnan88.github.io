## 开放端口
```
firewall-cmd --zone=public --add-port=80/tcp --permanent
```
## 重启防火墙
```
firewall-cmd --reload
```
## 禁止ping
```
firewall-cmd --permanent --add-rich-rule='rule protocol value=icmp drop'  
```
## 允许192.168.122.0/24主机所有连接
```
firewall-cmd --add-rich-rule='rule family="ipv4" source address="192.168.122.0" accept'
```
