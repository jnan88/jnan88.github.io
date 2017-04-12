#!/bin/bash
LOG_SIZE=20
git config core.pager cat #增加这个让命令正常退出继续执行下去，或直接加--global全局设置
git log --pretty=format:"%h - %an, %ad : %s" --date=local  --no-merges -n ${LOG_SIZE}
