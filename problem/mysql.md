
## mysql binlog查看
```
mysqlbinlog --start-datetime='2017-11-12 00:00:00' --stop-datetime='2017-11-17 00:00:00' /www/logs/mysql/bin-file.00009* |grep 'jiuku_ywinename_sph' -B 6 -A 6 >>binlog.sql
筛选出数据库jiuku_ywinename_sph表在一个时间段内前后6条操作记录 
```

## mysql加速恢复
```
1 关闭binlog:不写入Binlog会大大的加快数据导入的速度
2 innodb_flush_log_at_trx_commit=0
```
