## emoji config
```
#SHOW VARIABLES WHERE Variable_name LIKE 'character\_set\_%' OR Variable_name LIKE 'collation%';
# vim /etc/mysql/my.cnf
[client]  
default-character-set = utf8mb4  
  
[mysql]  
default-character-set = utf8mb4  
  
[mysqld]  
character-set-client-handshake = FALSE  
character-set-server = utf8mb4  
collation-server = utf8mb4_unicode_ci

```
## create db
```
create database db_name CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```
