#!/bin/bash
JAVA_HOME=/www/env/java
export JAVA_HOME
JETTY_HOME=/www/server/jetty
JETTY_BASE=$(cd $(dirname $0);pwd)
java -jar $JETTY_HOME/start.jar --create-startd
# EXT_MODULES=jmx,jmx-remote,session-store-mongo
JETTY_MODULES=ext,server,resources,http,deploy,slf4j-log4j2,jsp,jstl,servlet,jvm
java -jar $JETTY_HOME/start.jar --add-to-start=$JETTY_MODULES


# 更新日志配置
mkdir $JETTY_BASE/resources -p
touch $JETTY_BASE/resources/log4j2.xml
cat > $JETTY_BASE/resources/log4j2.xml <<END
<?xml version="1.0" encoding="UTF-8"?>
<Configuration status="warn" name="Jetty" >

  <Properties>
    <Property name="jetty.logging.dir" value="logs"/>
  </Properties>
  <Loggers>
    <Root level="error">
      <AppenderRef ref="STDOUT"/>
    </Root>
  </Loggers>

  <Appenders>
    <Console name="console" target="SYSTEM_ERR">
      <PatternLayout>
        <Pattern>%d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L - %m%n</Pattern>
      </PatternLayout>
    </Console>

    <RollingFile name="file"
      fileName="logs/jetty.log"
      filePattern="logs/jetty.log.%d{yyyy-MM-dd}"
      ignoreExceptions="false">
      <PatternLayout>
        <Pattern>%d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L - %m%n</Pattern>
      </PatternLayout>
      <SizeBasedTriggeringPolicy size="500MB"/>
      <DefaultRolloverStrategy max="160"/>
    </RollingFile>
  </Appenders>

  <Loggers>
    <Root level="info">
      <AppenderRef ref="console"/>
      <AppenderRef ref="file"/>
    </Root>
  </Loggers>

</Configuration>
END
## 更新jetty.sh
```
JAVA=/www/env/java/bin/java
JETTY_HOME=/www/server/jetty
JETTY_BASE=$(cd $(dirname $0);pwd)
NAME=$(basename $JETTY_BASE)
JETTY_PID=${JETTY_BASE}/${NAME}.pid
JETTY_STATE=${JETTY_BASE}/${NAME}.state
```
## jetty jvm参数配置start.d/jvm.ini
```
--module=jvm
--exec
-Xms1g
-Xmx2g
-XX:NewSize=256m
-XX:MaxNewSize=512m
-XX:MetaspaceSize=128m
-XX:MaxMetaspaceSize=512m
-XX:AutoBoxCacheMax=20000
-Xloggc:/www/log/gc-www.log
-XX:+HeapDumpOnOutOfMemoryError
-XX:+PrintGCDetails
-XX:+PrintGCDateStamps
-XX:+PrintHeapAtGC
-XX:+PrintTenuringDistribution
-XX:+PrintGCApplicationStoppedTime
-XX:+PrintPromotionFailure
-XX:+UseGCLogFileRotation
-XX:NumberOfGCLogFiles=120
-XX:GCLogFileSize=500M
-Dcom.sun.management.jmxremote
-Djava.rmi.server.hostname=ip
-Dcom.sun.management.jmxremote.port=50001
-Dcom.sun.management.jmxremote.authenticate=false
-Dcom.sun.management.jmxremote.ssl=false
-Dspring.profiles.active=test
```
