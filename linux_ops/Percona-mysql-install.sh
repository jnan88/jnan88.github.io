yum remove mariadb*
yum install -y jemalloc perl perl-Data-Dumper perl-JSON
wget Percona-Server-5.7.10-1rc1-rb13e470-el7-x86_64-bundle.tar
tar xvf Percona-Server-5.7.10-1rc1-rb13e470-el7-x86_64-bundle.tar
rpm -ivh *.rpm
# systemctl start mysqld
默认密码在：grep password /var/log/mysqld.log
首次登录需呀修改一次
set global validate_password_policy=0;
ALTER USER 'root'@'localhost' IDENTIFIED BY 'dev@mysql';
flush privileges;


create user dev identified by 'mysql@dev';
create database dev;
grant all on dev.* to dev
