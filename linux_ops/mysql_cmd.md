## 安装
### MariaDB

## 备份
> mysqldump -u root -p dbname > backup_dbname.sql   
 
## 还原
> mysql -u root -p dbname < backup_dbname.sql

或登录后使用
> source /backup/backup_dbname.sql  
