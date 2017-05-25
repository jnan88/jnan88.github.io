#!/bin/bash
# 用于对目录进行备份
BACKUP_HOME=/opt/backups
BACKUP_DIST=$1
NAME=$(basename $BACKUP_DIST)
BACKUP_NAME=$NAME-$(date +"%Y%m%d%H%m%S")
#########
if [ ! -d $BACKUP_DIST ];then
    echo "备份目录不存在:$BACKUP_DIST"
    exit 1
fi
echo "创建备份 $BACKUP_DIST 到 $BACKUP_HOME/$BACKUP_NAME"
while true; do
    read -p "确认备份?（y/n）" yn
    case $yn in
        [Yy]* )
                cp -r $BACKUP_DIST $BACKUP_HOME/$BACKUP_NAME
                echo "backup ok!"
                break;;
        [Nn]* ) exit;;
        * ) echo "Please answer yes or no.";;
    esac
done
