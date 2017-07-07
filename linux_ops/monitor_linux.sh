#!/bin/bash
LOAD=$(uptime | awk '{print $10,$11,$12}')
CPU=$(top -b -n 1 | grep Cpu)
MEM1=$(free -m | grep Mem | awk '{print $4}')
MEM3=$(free -m | grep Swap | awk '{print $4}')
DATE=$(date)
echo "=====$DATE====="
echo "load:$LOAD"
echo "cpu:$CPU"
echo "mem:$MEM1 Swap:$MEM3"
