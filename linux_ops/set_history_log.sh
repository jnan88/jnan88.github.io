#!/bin/env bash
#################################
# linux增加操作记录记录到日志
# Author:jnan77@qq.com
# Create:2017-01-10
#################################

function update_history_log(){
    save_log=/opt/.ops
    file0=/etc/profile
    line_num=$(grep -n "export HISTSIZE=" $file0 | awk -F : '{print $1}')
    if [ $line_num ];then
       echo "delete line : ${line_num}"
       sed -i ${line_num}d $file0
    fi
    echo "export HISTSIZE=10000" >> $file0

    line_num=$(grep -n "export HISTTIMEFORMAT=" $file0 | awk -F : '{print $1}')
    if [ $line_num ];then
       echo "delete line : ${line_num}"
       sed -i ${line_num}d $file0
    fi

    echo "export HISTTIMEFORMAT=\" %Y-%m-%d %H:%M:%S - \`who am i 2>/dev/null | awk '{print \$NF}'|sed -e 's/[()]//g'\` - \`who -u am i |awk '{print \$1}'\` \"" >> $file0
    source $file0
    echo "#####update after######"
    tail -n 2 $file0
    echo "#######################"
    echo "update $file0 ok"
    # 需要用户主动退出才能保存 update ~/.bash_logout
    file1=~/.bash_logout
    line_num2=$(grep -n "history" $file1 | awk -F : '{print $1}')
    if [ $line_num2 ];then
       echo "delete line : ${line_num2}"
       sed -i ${line_num2}d $file1
    fi
    echo "history >> $save_log" >> $file1
    echo "#####update after######"
    tail -n 2 $file1
    echo "#######################"
    echo "update $file1 ok"
}
update_history_log
