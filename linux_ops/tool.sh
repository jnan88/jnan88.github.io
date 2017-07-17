#!/bin/bash
PORTS=3306 6379 8080 8081 27017
for PORT in $PORTS
do
firewall-cmd --zone=public --permanent --add-port=${PORT}/tcp
done
firewall-cmd --reload
