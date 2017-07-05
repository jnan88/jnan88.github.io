#!/bin/bash
MYSQL_USER=root
MYSQL_PASS=password
LOG_FILE=/www/log/mysql_processlist.log
###################
function log_mysql_process(){
echo "Date:$(date +'%Y-%m-%d %H:%M:%S')"
mysqladmin -u${MYSQL_USER} -p${MYSQL_PASS} processlist
}
log_mysql_process >> $LOG_FILE
