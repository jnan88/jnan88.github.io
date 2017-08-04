#!/bin/bash
#############################
#######Redis安装脚本##########
#Centos7
#############################
INSTALL_VERSION=3.2.0
INSTALL_PATH=/usr/local
DATA_PATH=/www/data/redis
BUILD_PATH=/opt/down
######wget make gcc tcl
yum install -y wget make gcc tcl
mkdir ${BUILD_PATH} -p
mkdir ${INSTALL_PATH} -p
cd ${BUILD_PATH}
rm -rf redis-${INSTALL_VERSION}
if [ ! -f redis-${INSTALL_VERSION}.tar.gz ];then
  wget http://download.redis.io/releases/redis-${INSTALL_VERSION}.tar.gz
fi
tar zxvf redis-${INSTALL_VERSION}.tar.gz
cd redis-${INSTALL_VERSION}
echo "执行：make distclean"
make distclean
echo "执行：make "
make
echo "执行：make test"
make test
make install
##
cp redis.conf /etc/
if [ "$DATA_PATH" != "/usr/local/share" ];then
    sed -i "s:dir ./:dir $DATA_PATH:g" /etc/redis.conf
fi
##bind 127.0.0.1
##port 6379
##pidfile /var/run/redis.pid
##dir ./
echo "Redis 安装完成！"
echo "启动Redis请运行[redis-server /etc/redis.conf &]"
