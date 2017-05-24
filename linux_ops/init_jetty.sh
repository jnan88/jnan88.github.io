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
      filePattern="logs/jetty-%d{MM-dd-yyyy}.log.gz"
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
