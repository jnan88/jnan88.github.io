## pom.xml
```
                <dependency>
			<groupId>org.slf4j</groupId>
			<artifactId>slf4j-api</artifactId>
			<version>1.7.25</version>
		</dependency>
		<!-- 代码直接调用common-logging会被桥接到slf4j -->
		<dependency>
			<groupId>org.slf4j</groupId>
			<artifactId>jcl-over-slf4j</artifactId>
			<version>1.7.25</version>
			<scope>runtime</scope>
		</dependency>
		<!-- 代码直接调用java.util.logging会被桥接到slf4j -->
		<dependency>
			<groupId>org.slf4j</groupId>
			<artifactId>jul-to-slf4j</artifactId>
			<version>1.7.25</version>
			<scope>runtime</scope>
		</dependency>
		
		<!--核心log4j2jar包 -->
		<dependency>
			<groupId>org.apache.logging.log4j</groupId>
			<artifactId>log4j-api</artifactId>
			<version>2.4.1</version>
		</dependency>
		<dependency>
			<groupId>org.apache.logging.log4j</groupId>
			<artifactId>log4j-core</artifactId>
			<version>2.4.1</version>
		</dependency>
		<!--用于与slf4j保持桥接 -->
		<dependency>
			<groupId>org.apache.logging.log4j</groupId>
			<artifactId>log4j-slf4j-impl</artifactId>
			<version>2.4.1</version>
		</dependency>
		<!--web工程需要包含log4j-web，非web工程不需要 -->
		<dependency>
			<groupId>org.apache.logging.log4j</groupId>
			<artifactId>log4j-web</artifactId>
			<version>2.4.1</version>
			<scope>runtime</scope>
		</dependency>

		<!--需要使用log4j2的AsyncLogger需要包含disruptor -->
		<dependency>
			<groupId>com.lmax</groupId>
			<artifactId>disruptor</artifactId>
			<version>3.2.0</version>
		</dependency>
```
## log4j2.xml
```
<?xml version="1.0" encoding="UTF-8"?>
<Configuration status="off" monitorInterval="1800">
	<properties>
		<property name="LOG_HOME">logs</property>
		<property name="ERROR_LOG_FILE_NAME">error</property>
	</properties>
	<Appenders>
		<Console name="Console" target="SYSTEM_OUT">
			<PatternLayout pattern="%d [%t] %-5p %C:%M:%L - %m%n" />
		</Console>
		<RollingRandomAccessFile name="ErrorLog"
			fileName="${LOG_HOME}/${ERROR_LOG_FILE_NAME}.log" filePattern="${LOG_HOME}/${ERROR_LOG_FILE_NAME}.log.%d{yyyy-MM-dd}.gz">
			<PatternLayout pattern="%d [%t] %-5p %C:%M - %m%n" />
			<Policies>
				<TimeBasedTriggeringPolicy />
				<SizeBasedTriggeringPolicy size="100 MB" />
			</Policies>
			<DefaultRolloverStrategy max="20" />
		</RollingRandomAccessFile>

	</Appenders>

	<Loggers>
		<logger name="org.springframework" level="info">
		</logger>
		<logger name="com.domain" level="info"
			includeLocation="true" additivity="false">
			<appender-ref ref="ErrorLog" />
			<appender-ref ref="Console" />
		</logger>
		<root level="info" includeLocation="true">
			<appender-ref ref="Console" />
		</root>
	</Loggers>
</Configuration>  
```
