#!/bin/bash
# ---------------------------------------------------------
# nginx 定时切割日志
# crontab : 0 0 * * * /root/cut_nginx_log.sh
# ---------------------------------------------------------
log_path=/www/nginx/logs
run_yesterday=$(date -d "yesterday" +%Y%m%d)
#按天切割日志
log_form=${log_path}/access.log
log_to=${log_path}/access_${run_yesterday}.log
cat ${log_form} >> ${log_to}
rm -f ${log_form}
#mv ${log_path}/access.log ${log_path}/access_${run_yesterday}.log
#向 Nginx 主进程发送 USR1 信号，重新打开日志文件，否则会继续往mv后的文件写内容，导致切割失败.
kill -USR1 `ps axu | grep "nginx: master process" | grep -v grep | awk '{print $2}'`
