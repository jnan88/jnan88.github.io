#!/bin/bash
## Centos7
#############################
#######supervisor安装脚本######
#Centos7
#############################
yum install python-setuptools
easy_install supervisor

#python setup.py install
#yum install supervisor

#生成默认配置文件 echo_supervisord_conf > /etc/supervisord.conf
echo "启动请运行:supervisord -c /etc/supervisord.conf"
echo "新服务请添加到：/etc/"
