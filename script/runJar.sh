#!/bin/sh
_JAVA=/www/env/jdk1.8.0_91/bin/java
_APP=$(cd $(dirname $0); pwd)
JAR_NAME=springboot2-0.0.1
JAR_PARAMS="--server.port=9000 --spring.profiles.active=prod"
_PARAMS="-Xverify:none -server -Xms128m -Xmx256m -Xmn128m -XX:MaxPermSize=256m -Xss512K -XX:SurvivorRatio=8 -XX:+UseConcMarkSweepGC -XX:ParallelGCThreads=1 -XX:+DisableExplicitGC -XX:MaxTenuringThreshold=7 -XX:MaxGCPauseMillis=100 -XX:CMSInitiatingOccupancyFraction=80 -XX:-PrintGC -XX:-PrintGCDetails -Xloggc:$_APP/logs/gc.log -XX:ErrorFile=$_APP/logs/hs_error%p.log"
_LOG="$_APP/logs/log_start.log"
start() {
	[ -d $_APP ] || exit 5;
	echo "Server is starting..."
	cd $_APP
  echo "Jar Params :$JAR_PARAMS"
	nohup $_JAVA $_PARAMS -jar $_APP/${JAR_NAME}.jar $JAR_PARAMS > $_LOG 2>&1 &
	echo "Server is started..."
}
stop() {
	_kill=15
    _timeout=30
    if [[ "" -ne "$1" ]]; then
        _kill=$1
    fi
    if [[ "" -ne "$2" ]]; then
        _timeout=$2
    fi
    echo 'kill -'$_kill"..find Server process.";
    i=0
    while test $i -lt $_timeout; do
        pid=$(ps -ef|grep ${JAR_NAME} | grep -v grep | awk '{print $2}')
        if [ ! -n "$pid" ]; then
            if [ $i -eq 0 ]; then
                echo "Server process is not find."
            fi
            if [ $i -gt 0 ]; then
                echo 'kill -'$_kill'..stop...success.'
            fi
            return 1;
        else
            if [ $i -eq 0 ]; then
                echo  'kill -'$_kill"..Waitting server stop."
            fi
        fi
        echo  'kill -'$_kill'..stop...'$i'..'
        if [ $i -eq 0 ]; then
            for id in $pid
            do
                kill -$_kill $id;
            done
        fi
        i=$(expr $i + 1)
        sleep 1
    done;
    return 0;
}
restart() {
	if stop 15 30; then
		stop 9 30;
	fi
	start
}
status() {
	pid=$(ps -ef|grep ${JAR_NAME} | grep -v grep | awk '{print $2}')
	if [ ! -n "$pid" ]; then
		echo "Server process is not find."
	else
	    for id in $pid
        do
            echo "Server is run pid="$id;
        done
	fi
}
case "$1" in
	start)
		$1
		;;
	stop)
		$1
		;;
	restart)
		$1
		;;
	status)
		$1
		;;
   *)
    echo $"Useage: $0 {start|stop|status|restart}"
    exit 2
esac
