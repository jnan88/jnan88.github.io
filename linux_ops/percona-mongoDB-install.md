## env
```
yum install numactl -y
```

## percona-server-mongodb
```
rpm -ivh Percona-Server-MongoDB-34-mongos-* \
Percona-Server-MongoDB-34-shell-* \
Percona-Server-MongoDB-34-tools-* \
Percona-Server-MongoDB-34-mongos-* \
Percona-Server-MongoDB-34-server-*
```

## 初始化密码：
```
/usr/bin/percona-server-mongodb-enable-auth.sh
```

## uri链接
```
mongodb://userName:password@host:port/db?authSource=admin
```
