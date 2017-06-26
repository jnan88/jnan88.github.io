#!/bin/bash
#############################
#######openresty安装脚本######
#Centos7
#############################
build_path=/opt/install
install_path=/www/server/openresty
install_version=1.11.2.2
#版本
echo "openresty版本为：${install_version}"
echo "编译路径为：${build_path}"
echo "安装路径为：${install_path}"
#######################################
## install Openresty ##
#######################################
yum install -y wget make gcc readline-devel perl pcre-devel openssl-devel gc-c++ libreadline-dev libncurses5-dev libpcre3-dev  libssl-dev build-essential
#############################
mkdir -p ${install_path}
mkdir -p ${build_path}
#############################
cd ${build_path}
wget https://openresty.org/download/openresty-${install_version}.tar.gz
tar zxvf openresty-${install_version}.tar.gz
cd openresty-${install_version}

./configure --prefix=${install_path}
gmake
gmake install
echo "openresty-${install_version} 安装成功!"
echo "启动请运行 :[ ${install_path}/nginx/sbin/nginx ]"
cat > /usr/lib/systemd/system/nginx.service<<END
[Unit]
Description=nginx
After=network.target

[Service]
Type=forking
ExecStart=/www/server/openresty/nginx/sbin/nginx
ExecReload=/www/server/openresty/nginx/sbin/nginx -s reload
ExecStop=/bin/kill -s QUIT $MAINPID
PrivateTmp=true

[Install]
WantedBy=multi-user.target
END
systemctl enable nginx
